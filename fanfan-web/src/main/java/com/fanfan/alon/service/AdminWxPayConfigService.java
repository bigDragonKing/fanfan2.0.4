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
    /**
     * 删除配置信息
     */
    public void deleteBatch(Long[] ids);

    public AdminWxpayConfig selectByplatformId(Integer platformId);
}
