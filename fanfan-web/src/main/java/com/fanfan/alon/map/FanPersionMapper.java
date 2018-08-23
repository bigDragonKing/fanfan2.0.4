package com.fanfan.alon.map;

import com.fanfan.alon.models.FanPersion;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Service;

@Service
public interface FanPersionMapper {
    @Insert("insert into fan_persion (p_name, p_author) values (#{pName}, #{pAuthor})")
    Integer insertUser(FanPersion fanPersion);
}
