package com.fanfan.alon.service;

import com.baomidou.mybatisplus.service.IService;
import com.fanfan.alon.models.SysDictEntity;
import com.fanfan.alon.utils.PageUtils;

import java.util.Map;

/**
 * 功能描述:数据字典
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/8/28   17:34
 */
public interface SysDictService extends IService<SysDictEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

