package com.fanfan.alon.controller;

import com.fanfan.alon.config.FanWxPayConfig;
import com.fanfan.alon.models.AdminWxpayConfig;
import com.fanfan.alon.service.AdminWxPayConfigService;
import com.fanfan.alon.service.WxPayService;
import com.fanfan.alon.utils.QRUtil;
import com.fanfan.alon.webSocket.WebSocketUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import java.util.Hashtable;
import java.util.Map;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private AdminWxPayConfigService configService;

    @RequestMapping(value = "/scanPay",method = RequestMethod.GET)
    public void qrcode(HttpServletRequest request, HttpServletResponse response,
                       ModelMap modelMap) {
        try {
            String productId = request.getParameter("productId");
            String userId = "user01";
            AdminWxpayConfig config = configService.selectByplatformId(1);//后台获取微信相关配置
            FanWxPayConfig wxConfig = new FanWxPayConfig();
            wxConfig.appId = config.getAppId();
            wxConfig.mchId = config.getMchId();
            wxConfig.key = config.getAppKey();
            String text = wxPayService.wxScanPay(wxConfig);
            //根据url来生成生成二维码
            int width = 300;
            int height = 300;
            //二维码的图片格式
            String format = "gif";
            Hashtable hints = new Hashtable();
            //内容所使用编码
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix;
            try {
                bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
                QRUtil.writeToStream(bitMatrix, format, response.getOutputStream());
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
        }
    }

    @RequestMapping(value = "/notifyResult")
    public void notifyResult(HttpServletRequest request, HttpServletResponse response){
        System.out.println("===========================微信支付回调==================================");
        Map<String,Object> result = wxPayService.wxNotify(request,response);
//        if(result){
//            Session session = WebSocketUtil.getAllClient().get("out_trade_no");
//            if(null !=session){
//                session.getAsyncRemote().sendText("支付成功");
//            }
//        }else {
//            return;
//        }
        String outTradeNo = result.get("outTradeNo").toString();
        if(StringUtils.isNotEmpty(outTradeNo)){
            WebSocketUtil.sendMessage(outTradeNo,"支付成功");
        }else{
            WebSocketUtil.sendMessage("","支付失败");
        }
    }
}
