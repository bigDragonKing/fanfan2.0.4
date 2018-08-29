package com.fanfan.alon.service;

import com.baomidou.mybatisplus.service.IService;
import com.fanfan.alon.models.SysRoleDeptEntity;

import java.util.List;


/**
 * 功能描述:角色与部门对应关系
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/8/28   17:59
 */
public interface SysRoleDeptService extends IService<SysRoleDeptEntity> {
	
	void saveOrUpdate(Long roleId, List<Long> deptIdList);
	
	/**
	 * 根据角色ID，获取部门ID列表
	 */
	List<Long> queryDeptIdList(Long[] roleIds) ;

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
