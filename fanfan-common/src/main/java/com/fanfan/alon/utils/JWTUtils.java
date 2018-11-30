package com.fanfan.alon.utils;

import com.fanfan.alon.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTUtils<main> {

    private static final String SUBJECT = "alon";
    private static  long EXPIRE = 1000*60*60*24*7;//一周
    public static final String APPSECRET = "fan-fan";//密钥
    public static String genJsonWebToken(User user){
        if(user == null){
            return null;
        }else{
            String token = Jwts.builder().setSubject(SUBJECT)
                    .claim("id",user.getId())
                    .claim("name",user.getName())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis()+ EXPIRE))
                    .signWith(SignatureAlgorithm.HS256,APPSECRET).compact();
            return token;
        }
    }

    /**
     * 功能描述:校验token
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/11/29   17:50
     */
    public static Claims checkJwt(String token){
        try{
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET).
                    parseClaimsJws(token).getBody();
            return claims;
        }catch (Exception e){
            System.out.println("==========" + e.getMessage());
        }
        return null;
    }
    public static void main(String[] args){
        User user = new User();
        user.setId(Long.valueOf(1));
        user.setName("alon");
        String token = genJsonWebToken(user);
        System.out.println(token);

        Claims claims = checkJwt(token);
        Integer id = (Integer) claims.get("id");
        String name = (String) claims.get("name");
        System.out.println(id);
        System.out.println(name);
    }
}
