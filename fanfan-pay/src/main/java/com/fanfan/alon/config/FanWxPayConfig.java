package com.fanfan.alon.config;

/**
 * 功能描述:微信支付参数
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/9/3   16:47
 */
public class FanWxPayConfig {
    //微信号 wx0bb8cf1c2d8693d6
    private String appId;
    //商户id,如：1498420732
    private String partnerId;
    //商户密钥 如：c9f0954adfa6481a3fd3d7d76
    private String partnerKey;
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getPartnerId() {
        return partnerId;
    }
    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
    public String getPartnerKey() {
        return partnerKey;
    }
    public void setPartnerKey(String partnerKey) {
        this.partnerKey = partnerKey;
    }
}
