package com.fanfan.alon.service;

import com.fanfan.alon.config.FanWxPayConfig;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Service
public interface WxPayService {
    /**
     * 功能描述:扫码支付
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/9/6   16:14
     */
    String wxScanPay(FanWxPayConfig config);
    /**
     * 功能描述:扫码回调
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/9/6   16:14
     */
    Map<String,Object> wxNotify(HttpServletRequest request, HttpServletResponse response);
}
