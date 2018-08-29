package com.fanfan.alon.map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fanfan.alon.models.SysRoleDeptEntity;

import java.util.List;

/**
 * 功能描述:角色与部门对应关系
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/8/28   15:33
 */
public interface SysRoleDeptDao extends BaseMapper<SysRoleDeptEntity> {
	
	/**
	 * 根据角色ID，获取部门ID列表
	 */
	List<Long> queryDeptIdList(Long[] roleIds);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
