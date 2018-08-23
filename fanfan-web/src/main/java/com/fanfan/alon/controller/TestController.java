package com.fanfan.alon.controller;

import com.fanfan.alon.enums.BusinessMsgEnum;
import com.fanfan.alon.exception.BusinessErrorException;
import com.fanfan.alon.result.JsonResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "hello world!";
    }

    @PostMapping("/test")
    public JsonResult test(@RequestParam("name") String name,@RequestParam("pass") String pass){
        return new JsonResult();
    }

    /**
     * 功能描述:测试自定义异常
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/8/23   16:25
     */
    @GetMapping("/business")
    public JsonResult testException() {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
        }
        return new JsonResult();
    }

    @GetMapping("/{name}")
    public String testAop(@PathVariable String name) {
        return "Hello " + name;
    }
}
