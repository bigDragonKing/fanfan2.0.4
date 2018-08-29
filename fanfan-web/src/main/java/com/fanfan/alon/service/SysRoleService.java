package com.fanfan.alon.service;


import com.baomidou.mybatisplus.service.IService;
import com.fanfan.alon.models.SysRoleEntity;
import com.fanfan.alon.utils.PageUtils;

import java.util.Map;


/**
 * 功能描述:角色
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/8/28   17:42
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	PageUtils queryPage(Map<String, Object> params);

	void save(SysRoleEntity role);

	void update(SysRoleEntity role);
	
	void deleteBatch(Long[] roleIds);

}
