package com.fanfan.alon.service;

import com.baomidou.mybatisplus.service.IService;
import com.fanfan.alon.models.AdminWxpayConfig;
import com.fanfan.alon.service.dto.AdminWxpayConfigDto;
import com.fanfan.alon.utils.PageUtils;

import java.util.Map;

public interface AdminWxPayConfigService extends IService<AdminWxpayConfig> {
    PageUtils queryPage(Map<String, Object> params);

    public void save(AdminWxpayConfigDto configDto);
    public void update(AdminWxpayConfigDto configDto);
}
