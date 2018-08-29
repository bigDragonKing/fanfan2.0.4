package com.fanfan.alon.map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fanfan.alon.models.SysRoleMenuEntity;

import java.util.List;

/**
 * 功能描述:角色与菜单对应关系
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/8/28   15:35
 */
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenuEntity> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
