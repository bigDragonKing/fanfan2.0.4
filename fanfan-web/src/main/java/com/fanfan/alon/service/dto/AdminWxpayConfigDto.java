package com.fanfan.alon.service.dto;


import javax.validation.constraints.NotBlank;

public class AdminWxpayConfigDto {
    //主键id
    public Long id;
    //所属平台(1:微信;2:支付宝;3:银联)
    public Integer platformId;
    //微信号
    @NotBlank(message="参数名不能为空")
    public String appId;
    //商户id
    @NotBlank(message="参数名不能为空")
    public String mchId;
    //商户密钥
    @NotBlank(message="参数名不能为空")
    public String appKey;
    //配置说明
    public String remark;
    //启用状态(0:启用;1:禁用)
    public Integer enableStatus;
    //创建时间
    public String createDate;
    //更新时间
    public String updateDate;
}
