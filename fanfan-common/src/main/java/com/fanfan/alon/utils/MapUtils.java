package com.fanfan.alon.utils;

import java.util.HashMap;


/**
 * 功能描述:Map工具类
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/8/28   17:23
 */
public class MapUtils extends HashMap<String, Object> {

    @Override
    public MapUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
