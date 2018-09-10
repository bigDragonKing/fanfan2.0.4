package com.fanfan.alon.controller;

import com.fanfan.alon.service.WxPayService;
import com.fanfan.alon.utils.QRUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Hashtable;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private WxPayService wxPayService;

    @RequestMapping(value = "/scanPay",method = RequestMethod.GET)
    @ResponseBody
    public void qrcode(HttpServletRequest request, HttpServletResponse response,
                       ModelMap modelMap) {
        try {
            String productId = request.getParameter("productId");
            String userId = "user01";
            String text = wxPayService.wxScanPay();
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

    @PostMapping("/notify")
    public void payNotify(HttpServletRequest request, HttpServletResponse response){
        System.out.println("===========================微信支付回调==================================");
        wxPayService.wxNotify(request,response);
    }
}
