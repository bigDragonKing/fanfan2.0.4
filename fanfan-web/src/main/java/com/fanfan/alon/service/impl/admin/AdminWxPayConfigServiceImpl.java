package com.fanfan.alon.service.impl.admin;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fanfan.alon.map.admin.AdminWxpayConfigDao;
import com.fanfan.alon.models.AdminWxpayConfig;
import com.fanfan.alon.service.AdminWxPayConfigService;
import com.fanfan.alon.service.dto.AdminWxpayConfigDto;
import com.fanfan.alon.utils.PageUtils;
import com.fanfan.alon.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service("adminWxPayConfigService")
public class AdminWxPayConfigServiceImpl extends ServiceImpl<AdminWxpayConfigDao, AdminWxpayConfig> implements AdminWxPayConfigService {
    @Autowired
    private AdminWxpayConfigDao configDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String paramKey = (String)params.get("paramKey");
        Page<AdminWxpayConfig> page = this.selectPage(
                new Query<AdminWxpayConfig>(params).getPage(),
                new EntityWrapper<AdminWxpayConfig>()
                        .like(StringUtils.isNotBlank(paramKey),"mch_id", paramKey)
        );
        return new PageUtils(page);
    }

    @Override
    public void save(AdminWxpayConfigDto configDto) {
        AdminWxpayConfig config = new AdminWxpayConfig();
        config.setPlatformId(configDto.platformId);
        config.setAppId(configDto.appId);
        config.setMchId(configDto.mchId);
        config.setAppKey(configDto.appKey);
        config.setRemark(configDto.remark);
        config.setEnableStatus(configDto.enableStatus);
        config.setCreateDate(new Date());
        config.setUpdateDate(new Date());
        this.insert(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AdminWxpayConfigDto configDto) {
        AdminWxpayConfig config = new AdminWxpayConfig();
        config.setPlatformId(configDto.platformId);
        config.setAppId(configDto.appId);
        config.setMchId(configDto.mchId);
        config.setAppKey(configDto.appKey);
        config.setRemark(configDto.remark);
        config.setEnableStatus(configDto.enableStatus);
        config.setUpdateDate(new Date());
        this.updateAllColumnById(config);
    }
}
