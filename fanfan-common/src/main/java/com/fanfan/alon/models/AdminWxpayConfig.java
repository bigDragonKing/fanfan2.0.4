package com.fanfan.alon.models;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 功能描述:微信支付参数
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/9/3   16:47
 */
@TableName("admin_wxpay_config")
public class AdminWxpayConfig {
    //主键id
    @TableId
    private Long id;
    //所属平台(1:微信;2:支付宝;3:银联)
    private Integer platformId;
    //微信号
    private String appId;
    //商户id
    private String mchId;
    //商户密钥
    private String appKey;
    //配置说明
    private String remark;
    //启用状态(0:启用;1:禁用)
    private Integer enableStatus;
    //创建时间
    @NotBlank(message="参数名不能为空")
    private Date createDate;
    //更新时间
    @NotBlank(message="参数名不能为空")
    private Date updateDate;
    //版本号
    @Version
    private Long updateVersion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

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

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateVersion() {
        return updateVersion;
    }

    public void setUpdateVersion(Long updateVersion) {
        this.updateVersion = updateVersion;
    }
}
