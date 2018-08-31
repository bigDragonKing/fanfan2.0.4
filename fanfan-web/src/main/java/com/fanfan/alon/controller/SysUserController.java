package com.fanfan.alon.controller;


import com.fanfan.alon.annotation.SysLog;
import com.fanfan.alon.models.SysUserEntity;
import com.fanfan.alon.service.SysUserRoleService;
import com.fanfan.alon.service.SysUserService;
import com.fanfan.alon.utils.JsonResult;
import com.fanfan.alon.utils.PageUtils;
import com.fanfan.alon.utils.ShiroUtils;
import com.fanfan.alon.validator.Assert;
import com.fanfan.alon.validator.ValidatorUtils;
import com.fanfan.alon.validator.group.AddGroup;
import com.fanfan.alon.validator.group.UpdateGroup;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public JsonResult list(@RequestParam Map<String, Object> params){
		PageUtils page = sysUserService.queryPage(params);

		return JsonResult.ok().put("page", page);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public JsonResult info(){
		return JsonResult.ok().put("user", getUser());
	}
	
	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@RequestMapping("/password")
	public JsonResult password(String password, String newPassword){
		Assert.isBlank(newPassword, "新密码不为能空");

		//原密码
		password = ShiroUtils.sha256(password, getUser().getSalt());
		//新密码
		newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());
				
		//更新密码
		boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(!flag){
			return JsonResult.error("原密码不正确");
		}
		
		return JsonResult.ok();
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public JsonResult info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.selectById(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return JsonResult.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public JsonResult save(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, AddGroup.class);
		
		sysUserService.save(user);
		
		return JsonResult.ok();
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public JsonResult update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		sysUserService.update(user);
		
		return JsonResult.ok();
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public JsonResult delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return JsonResult.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return JsonResult.error("当前用户不能删除");
		}

		sysUserService.deleteBatchIds(Arrays.asList(userIds));
		
		return JsonResult.ok();
	}
}
