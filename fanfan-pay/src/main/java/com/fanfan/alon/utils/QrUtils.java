package com.fanfan.alon.utils;

import com.fanfan.alon.constant.StringConstant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class QrUtils {

    // 特殊字符处理
    public static String UrlEncode(String src) {
        String url = null;
        try {
            url =  URLEncoder.encode(src, StringConstant.CHARSET_STRING).replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
}
