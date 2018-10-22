package com.fanfan.alon.controller;

import com.fanfan.alon.enums.BusinessMsgEnum;
import com.fanfan.alon.exception.BusinessErrorException;
import com.fanfan.alon.models.FanPersion;
import com.fanfan.alon.service.FanPersionService;
import com.fanfan.alon.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private FanPersionService persionService;

    @GetMapping("/test")
    public String test(){
        return "map";
    }

    @PostMapping("/test")
    public JsonResult test1(@RequestParam("name") String name, @RequestParam("pass") String pass){
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

    /**
     * 功能描述:事务测试
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/8/23   19:42
     */
    @PostMapping("/adduser")
    public String addUser(@RequestBody FanPersion fanPersion) throws Exception {
        if (null != fanPersion) {
            persionService.isertUser(fanPersion);
            return "success";
        } else {
            return "false";
        }
    }

    /**
     * 功能描述:监听器  Application缓存（缓存在内存中） 获取缓存用户信息
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/8/23   19:43
     */
    @GetMapping("/user")
    public FanPersion getUser(HttpServletRequest request) {
        ServletContext application = request.getServletContext();
        return (FanPersion) application.getAttribute("user");
    }

    /**
     * 功能描述:获取当前在线人数
     * @param: request
     * @return:
     * @auther: zoujiulong
     * @date: 2018/8/23   19:51
     */
    @GetMapping("/total")
    public String getTotalUser(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie;
        try {
            // 把sessionId记录在浏览器中
            cookie = new Cookie("JSESSIONID", URLEncoder.encode(request.getSession().getId(), "utf-8"));
            cookie.setPath("/");
            //设置cookie有效期为2天，设置长一点
            cookie.setMaxAge( 48*60 * 60);
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Integer count = (Integer) request.getSession().getServletContext().getAttribute("count");
        return "当前在线人数：" + count;
    }

    /**
     * 功能描述:监听器   获取缓存用户信息
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/8/23   20:26
     */
    @GetMapping("/request")
    public String getRequestInfo(HttpServletRequest request) {
        System.out.println("requestListener中的初始化的name数据：" + request.getAttribute("name"));
        return "success";
    }

    public static void main(String[] args) {
//        //连接本地的 Redis 服务
//        Jedis jedis = new Jedis("localhost");
//        System.out.println("连接成功"+jedis);
//        //存储数据到列表中
//        jedis.lpush("site-list", "Runoob");
//        jedis.lpush("site-list", "Google");
//        jedis.lpush("site-list", "Taobao");
//        // 获取存储的数据并输出
//        List<String> list = jedis.lrange("site-list", 0 ,3);
//        for(int i=0; i<list.size(); i++) {
//            System.out.println("列表项为: "+list.get(i));
//        }
//
//        // 获取数据并输出
//        Set<String> keys = jedis.keys("*");
//        Iterator<String> it=keys.iterator() ;
//        while(it.hasNext()){
//            String key = it.next();
//            System.out.println(key);
//        }

        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");

        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            /// ... do stuff here ... for example
            jedis.set("foo", "bar");
            String foobar = jedis.get("foo");
            jedis.zadd("sose", 0, "car");
            jedis.zadd("sose", 0, "bike");
            Set<String> sose = jedis.zrange("sose", 0, -1);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
/// ... when closing your application:
        pool.destroy();
    }

}
