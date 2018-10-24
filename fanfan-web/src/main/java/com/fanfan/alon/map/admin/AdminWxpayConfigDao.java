package com.fanfan.alon.map.admin;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fanfan.alon.models.AdminWxpayConfig;

public interface AdminWxpayConfigDao extends BaseMapper<AdminWxpayConfig> {
    AdminWxpayConfig selectByplatformId(Integer platformId);
}
