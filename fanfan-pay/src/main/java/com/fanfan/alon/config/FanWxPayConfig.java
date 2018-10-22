package com.fanfan.alon.config;

/**
 * 功能描述:微信支付参数
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/9/3   16:47
 */
public class FanWxPayConfig {
    //微信号
    private String appId = "";
    //商户id
    private String mchId = "";
    //商户密钥
    private String key = "";
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getMchId() {
        return mchId;
    }
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
}
