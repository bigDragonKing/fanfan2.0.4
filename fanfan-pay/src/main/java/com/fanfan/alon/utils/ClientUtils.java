package com.fanfan.alon.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ClientUtils {

    private static final Logger logger = LoggerFactory.getLogger(ClientUtils.class);

    public static String post(String url, String xml) {
        logger.info("===============开始请求=============");
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        HttpEntity eitity;
        try {
            eitity = new ByteArrayEntity(xml.getBytes("utf-8"), ContentType.TEXT_XML);
            httppost.setEntity(eitity);
            logger.info("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String content = EntityUtils.toString(entity, "utf-8");
                    return content;
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            logger.error("发送post请求异常：", e);
            return e.getMessage();
        } catch (UnsupportedEncodingException e) {
            logger.error("发送post请求异常：", e);
            return e.getMessage();
        } catch (IOException e) {
            logger.error("发送post请求异常：", e);
            return e.getMessage();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("关闭post请求异常：", e);
            }
        }
        return ("发送post请求失败！");
    }
}
