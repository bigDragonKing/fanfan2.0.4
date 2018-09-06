package com.fanfan.alon.service.impl;

import com.fanfan.alon.config.FanWxPayConfig;
import com.fanfan.alon.constant.StringConstant;
import com.fanfan.alon.service.WxPayService;
import com.fanfan.alon.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

@Service
public class WxPayServiceImpl implements WxPayService {

    private static final Logger logger = LoggerFactory.getLogger(WxPayServiceImpl.class);
    private static String unifiedorder = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    private static String orderquery = "https://api.mch.weixin.qq.com/pay/orderquery";
    FanWxPayConfig fanWxPayConfig = new FanWxPayConfig();
    /**
     * 功能描述:扫码支付
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/9/6   15:36
     */
    @Override
    public String wxScanPay() {
        String appId = fanWxPayConfig.getAppId();
        String mchId = fanWxPayConfig.getMchId();
        String key = fanWxPayConfig.getKey();
        String nonceStr = NumberUtil.getRandomString(6);

        BigDecimal orderFee = BigDecimal.valueOf(0.01); // 价格 单位是元
        String body = "Alon扫码支付";   // 商品名称
        String outTradeNo = "11338"; // 订单号
        // 获取发起电脑 ip
        String spbillCreateIp = "127.0.0.1";
        // 回调接口
        String notifyUrl = "zoujiulong.dev.swiftpass.cn/wxpay/notify";
        String tradeType = "NATIVE";

        Map<String, String> payParams = new HashMap<String, String>();
        payParams.put("appid", appId);
        payParams.put("mch_id", mchId);
        payParams.put("nonce_str", nonceStr);
        payParams.put("body", body);
        payParams.put("out_trade_no", outTradeNo);
        payParams.put("total_fee", String.valueOf(orderFee));
        payParams.put("spbill_create_ip", spbillCreateIp);
        payParams.put("notify_url", notifyUrl);
        payParams.put("trade_type", tradeType);
        String sign = SignUtils.sign(key,payParams);
        payParams.put("sign",sign);
        String xml = XmlUtils.toXml(payParams, true);
        logger.info("請求參數：" + xml);
        String resXml = HttpClientUtil.postData(unifiedorder, xml);

        Map map = XMLUtil.doXMLParse(resXml);
        String urlCode = (String) map.get("code_url");
        return urlCode;
    }

    /**
     * 功能描述:生成支付二维码
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/9/6   15:36
     */
    public static String QRfromGoogle(String chl){
        int widhtHeight = 300;
        String EC_level = "L";
        int margin = 0;
        chl = QrUtils.UrlEncode(chl);
        String QRfromGoogle = "http://chart.apis.google.com/chart?chs=" + widhtHeight + "x" + widhtHeight
                + "&cht=qr&chld=" + EC_level + "|" + margin + "&chl=" + chl;

        return QRfromGoogle;
    }
    /**
     * 功能描述:回调
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/9/6   15:48
     */
    @Override
    public void wxNotify(HttpServletRequest request, HttpServletResponse response){
        //读取参数
        InputStream inputStream = null;
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        try {
            inputStream = request.getInputStream();
            String s ;
            in = new BufferedReader(new InputStreamReader(inputStream, StringConstant.CHARSET_STRING));
            while ((s = in.readLine()) != null){
                sb.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("io异常：" + e);
        }finally {
            try {
                in.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.info("关闭流失败：" + e);
            }
        }
        //解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        m = XMLUtil.doXMLParse(sb.toString());
        //过滤空 设置 TreeMap
        Map<Object,Object> packageParams = new TreeMap<Object,Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);
            String v = "";
            if(null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        // 账号信息
        String key = fanWxPayConfig.getKey(); // key
        logger.info("解析结果：" + packageParams);
        //判断签名是否正确
        if(SignUtils.isTenpaySign("UTF-8", packageParams,key)) {
            //------------------------------
            //处理业务开始
            //------------------------------
            String resXml = "";
            if("SUCCESS".equals((String)packageParams.get("result_code"))){
                // 这里是支付成功
                //////////执行自己的业务逻辑////////////////
                String mchId = (String)packageParams.get("mch_id");
                String openId = (String)packageParams.get("openid");
                String isSubscribe = (String)packageParams.get("is_subscribe");
                String outTradeNo = (String)packageParams.get("out_trade_no");
                String totalFee = (String)packageParams.get("total_fee");
                logger.info("mch_id:"+mchId);
                logger.info("openid:"+openId);
                logger.info("is_subscribe:"+isSubscribe);
                logger.info("out_trade_no:"+outTradeNo);
                logger.info("total_fee:"+totalFee);
                //////////执行自己的业务逻辑////////////////
                logger.info("支付成功");
                //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            } else {
                logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            //------------------------------
            //处理业务完毕
            //------------------------------
            BufferedOutputStream out = null;
            try {
                out = new BufferedOutputStream(
                        response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    out.write(resXml.getBytes());
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.info("流关闭失败：" + e);
                }
            }
        } else{
            logger.info("通知签名验证失败");
        }
    }
}
