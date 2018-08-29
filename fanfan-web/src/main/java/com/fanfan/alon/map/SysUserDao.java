package com.fanfan.alon.map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fanfan.alon.models.SysUserEntity;

import java.util.List;

/**
 * 功能描述:系统用户
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/8/28   15:36
 */
public interface SysUserDao extends BaseMapper<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

}
