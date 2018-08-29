package com.fanfan.alon.service;

import com.baomidou.mybatisplus.service.IService;
import com.fanfan.alon.models.SysRoleMenuEntity;

import java.util.List;


/**
 * 功能描述:角色与菜单对应关系
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/8/28   17:59
 */
public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {
	
	void saveOrUpdate(Long roleId, List<Long> menuIdList);
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
	
}
