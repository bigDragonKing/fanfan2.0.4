package com.fanfan.alon.service;

import com.baomidou.mybatisplus.service.IService;
import com.fanfan.alon.models.SysUserRoleEntity;

import java.util.List;


/**
 * 功能描述:用户与角色对应关系
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/8/28   17:59
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {
	
	void saveOrUpdate(Long userId, List<Long> roleIdList);
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
