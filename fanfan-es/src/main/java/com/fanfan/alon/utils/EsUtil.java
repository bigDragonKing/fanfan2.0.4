package com.fanfan.alon.utils;

import com.fanfan.alon.core.ESearchTypeColumn;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EsUtil {

    public static String createMapping(TransportClient transportClient,String indexName,String typeName,Class clazz) {
        String analyzer = "";
        Boolean synonym = false;
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject().startObject("properties");
            List<Field> fieldList = new ArrayList<Field>();
            Class tempClass = clazz;
            while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
                fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
                tempClass = tempClass.getSuperclass();// 得到父类,然后赋给自己
            }
            for (Field field : fieldList) {
                builder.startObject(field.getName())//字段名
                        .field("type", filedType(field.getType().getName()));//设置数据类型
                if (field.isAnnotationPresent(ESearchTypeColumn.class)) {
                    analyzer = field.getAnnotation(ESearchTypeColumn.class).analyzer();
                    builder.field("analyzer", analyzer);
                }
                builder.endObject();
            }
            builder.endObject();
            builder.endObject();
            if("ik_pinyin_analyzer".equals(analyzer) && !synonym){
                createPinYinIndex(transportClient,indexName);
            }else if ("ik_pinyin_analyzer".equals(analyzer) && synonym){
                createPinYinIndexWithSynonym(transportClient,indexName);
            }else{
                createIndex(transportClient,indexName);
            }
            PutMappingRequest mapping = Requests.putMappingRequest(indexName).type(typeName).source(builder);
            transportClient.admin().indices().putMapping(mapping).actionGet();
            return "success";
        } catch (Exception e) {
            System.err.println(e);
            return "error";
        }
    }

    public static String filedType(String varType){
        String es = "";
        switch (varType) {
            case "java.util.Date":
                es = "date";
                break;
            case "java.lang.Double":
                es = "double";
                break;
            case "java.lang.Long":
                es = "long";
                break;
            case "java.lang.Integer":
                es = "integer";
                break;
            default:
                es = "string";
                break;
        }
        return es;
    }

    public static void createPinYinIndex(TransportClient transportClient,String indexName){
        try {
            transportClient.admin().indices()
                    .prepareCreate(indexName).setSettings("{\"index\" : "
                    + "{\"analysis\" : "
                    + "{\"analyzer\" : "
                    + "{\"ik_pinyin_analyzer\" : "
                    + "{\"tokenizer\" : \"ik_max_word\", "
                    + "\"filter\" : [\"my_pinyin\",\"word_delimiter\"]}},"
                    + "\"filter\" : {\"my_pinyin\" : "
                    + "{\"type\" : \"pinyin\", \"first_letter\": \"prefix\",\"keep_first_letter\" : false,\"padding_char\" : \" \" }}}}}").get(); //keep_first_letter设为false排除掉首字母分词，如“中”分词zhong,z
            System.out.println("索引创建成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("索引创建失败");
        }
    }

    public static void createPinYinIndexWithSynonym(TransportClient transportClient, String indexName){
        try {
            transportClient.admin().indices()
                    .prepareCreate(indexName).setSettings("{\"index\" : "
                    + "{\"analysis\" : "
                    + "{\"analyzer\" : "
                    + "{\"ik_pinyin_analyzer\" : "
                    + "{\"tokenizer\" : \"ik_max_word\", "
                    + "\"filter\" : [\"by_sfr\",\"my_pinyin\",\"word_delimiter\"]}},"
                    + "\"filter\" : {\"my_pinyin\" : "
                    + "{\"type\" : \"pinyin\", \"first_letter\" : \"prefix\",\"keep_first_letter\" : false,\"padding_char\" : \" \" },"
                    + "\"by_sfr\": {\"type\": \"synonym\",\"synonyms_path\": \"analysis/synonyms.txt\"}}}}}").get();
            System.out.println("索引创建成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("索引创建失败");
        }
    }

    public static void createIndex(TransportClient transportClient,String index) {
        CreateIndexRequest request = new CreateIndexRequest(index);
        transportClient.admin().indices().create(request).actionGet();
    }
}
