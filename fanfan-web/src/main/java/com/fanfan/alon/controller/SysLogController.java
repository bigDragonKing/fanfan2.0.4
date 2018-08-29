package com.fanfan.alon.controller;

import com.fanfan.alon.service.SysLogService;
import com.fanfan.alon.utils.JsonResult;
import com.fanfan.alon.utils.PageUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 * 功能描述:系统日志
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/8/28   17:37
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("sys:log:list")
	public JsonResult list(@RequestParam Map<String, Object> params){
		PageUtils page = sysLogService.queryPage(params);

		return JsonResult.ok().put("page", page);
	}
	
}
