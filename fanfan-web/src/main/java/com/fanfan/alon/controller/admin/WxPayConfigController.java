package com.fanfan.alon.controller.admin;

import com.fanfan.alon.annotation.SysLog;
import com.fanfan.alon.models.AdminWxpayConfig;
import com.fanfan.alon.models.SysConfigEntity;
import com.fanfan.alon.service.AdminWxPayConfigService;
import com.fanfan.alon.service.dto.AdminWxpayConfigDto;
import com.fanfan.alon.utils.JsonResult;
import com.fanfan.alon.utils.PageUtils;
import com.fanfan.alon.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/config")
public class WxPayConfigController {

    @Autowired
    private AdminWxPayConfigService adminWxPayConfigService;

    @GetMapping("/list")
    @RequiresPermissions("admin:config:list")
    public JsonResult list(@RequestParam Map<String, Object> params){
        PageUtils page = adminWxPayConfigService.queryPage(params);
        return JsonResult.ok().put("page", page);
    }

    /**
     * 保存配置
     */
    @SysLog("保存配置")
    @PostMapping("/save")
    public JsonResult save(@RequestBody AdminWxpayConfigDto config){
        ValidatorUtils.validateEntity(config);
        adminWxPayConfigService.save(config);
        return JsonResult.ok();
    }

    /**
     * 修改配置
     */
    @SysLog("修改配置")
    @RequestMapping("/update")
    public JsonResult update(@RequestBody AdminWxpayConfigDto config){
        ValidatorUtils.validateEntity(config);

        adminWxPayConfigService.update(config);

        return JsonResult.ok();
    }

    /**
     * 配置信息
     */
    @GetMapping("/info/{id}")
    public JsonResult info(@PathVariable("id") Long id){
        AdminWxpayConfig config = adminWxPayConfigService.selectById(id);

        return JsonResult.ok().put("config", config);
    }
    /**
     * 删除配置
     */
    @SysLog("删除配置")
    @PostMapping("/delete")
    public JsonResult delete(@RequestBody Long[] ids){
        adminWxPayConfigService.deleteBatch(ids);

        return JsonResult.ok();
    }
}
