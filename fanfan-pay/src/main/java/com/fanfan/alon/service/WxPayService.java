package com.fanfan.alon.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WxPayService {
    /**
     * 功能描述:扫码支付
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/9/6   16:14
     */
    public String wxScanPay();
    /**
     * 功能描述:扫码回调
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/9/6   16:14
     */
    public void wxNotify(HttpServletRequest request, HttpServletResponse response);
}
