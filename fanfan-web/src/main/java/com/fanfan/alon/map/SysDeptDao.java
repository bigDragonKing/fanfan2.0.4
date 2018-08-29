package com.fanfan.alon.map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fanfan.alon.models.SysDeptEntity;

import java.util.List;

/**
 * 功能描述:部门管理
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/8/28   15:34
 */
public interface SysDeptDao extends BaseMapper<SysDeptEntity> {

    /**
     * 查询子部门ID列表
     * @param parentId  上级部门ID
     */
    List<Long> queryDetpIdList(Long parentId);

}
