package com.fanfan.alon.utils;

import com.fanfan.alon.exception.GlobalExceptionHandler;
import com.github.pagehelper.StringUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.File;
import java.io.IOException;

public class QiniuUtils {

    public static final String QINIU_DOMAIN = "pe7tygflr.bkt.clouddn.com";
    public static final String BUCKET = "alom-z";

    public static Auth auth = null;

    static {
        String access_key = "WaE8MA6D7pM8sF148gpiaM4tnen9GDfuFkoyvMu7";
        String secret_key = "qMKE4NDOpd2eBQtvvLLSkEeOZJXqxlCh0p0NcsVW";
        auth = Auth.create(access_key, secret_key);
    }

    /**
     * @param file 		待上传的文件
     * @param destFileName 七牛上显示的文件名
     * @param bucket 七牛上的存储空间
     *
     * @return 七牛上显示的文件名
     */
    public static String upload(File file, String destFileName, String bucket) {
        if (!file.exists()) {
//            throw new ServiceException("文件不存在");
            return "文件不存在";
        }
        bucket= StringUtil.isEmpty(bucket) ? BUCKET: bucket;
        String token = auth.uploadToken(bucket);//获取上传凭证token
        if (StringUtil.isEmpty(token)) {
//            throw new ServiceException("token获取异常");
            return "token获取异常";
        }
        try {
            Zone z = Zone.zone0();
            Configuration c = new Configuration(z);
            //创建上传对象
            UploadManager manger = new UploadManager(c);
            Response res = manger.put(file, destFileName, token);//上传文件并获取回执
            if (res.isOK()) {
                Ret ret = res.jsonToObject(Ret.class);
                System.err.println("----" + ret.key);
                return ret.key;
            }
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class Ret {
    public long fsize;
    public String key;
    public String hash;
    public int width;
    public int height;
}