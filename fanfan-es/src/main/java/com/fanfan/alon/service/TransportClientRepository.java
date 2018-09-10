package com.fanfan.alon.service;

import com.alibaba.fastjson.JSONObject;
import com.fanfan.alon.core.ESearchTypeColumn;
import com.fanfan.alon.core.ElasticSearchPage;
import com.fanfan.alon.core.ElasticSearchQuery;
import com.fanfan.alon.core.EsBaseData;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.FieldSortBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 功能描述:
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/9/3   11:52
 */
public abstract class TransportClientRepository {

    private TransportClient transportClient;

    public TransportClientRepository(TransportClient transportClient) {
        this.transportClient = transportClient;
    }

    public abstract String indexName();

    public abstract String typeName();


    /**
     * 保存document记录数据
     *
     * @param id  索引id
     * @param doc
     * @return
     */
    public String saveDoc(String id, Object doc) {


        try {
            // java对象转为json字符串
            String builder = JSONObject.toJSONString(doc);
            // WriteRequest.RefreshPolicy.IMMEDIATE 为设置对数据进行操作后马上刷新ES
            IndexResponse response = transportClient.prepareIndex(indexName(), typeName()).setId(id).
                    setSource(builder).setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE).get();
            return response.getId();
        } catch (Exception e) {
            return null;
        }

    }

    /**
     *  保存document记录数据
     *
     * @param id  索引id
     * @param doc
     * @return
     * @Param pid
     */
    public String saveDoc(String id, Object doc, String pid) {

        try {
            String o = JSONObject.toJSONString(doc);
            IndexResponse response = transportClient.prepareIndex(indexName(), typeName()).setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE).setId(id).setSource(o).setParent(pid).get();
            return response.getId();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 更新文档
     *
     * @param doc
     * @return
     */
    public String updateDoc(EsBaseData doc) {

        try {
            String builder = JSONObject.toJSONString(doc);
            UpdateResponse response = transportClient.prepareUpdate(indexName(), typeName(), doc.getId()).setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE)
                    .setId(doc.getId()).setDoc(builder).get();
            return response.getId();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 更新文档
     *
     * @param doc
     * @return
     * @Param pid
     */
    public String updateDoc(EsBaseData doc, String pid) {
        try {
            String builder = JSONObject.toJSONString(doc);
            UpdateResponse response = transportClient.prepareUpdate(indexName(), typeName(), doc.getId()).setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE)
                    .setId(doc.getId()).setParent(pid).setDoc(builder).get();
            return response.getId();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 批量插入
     *
     * @param docs
     * @return
     */
    public Boolean bulkSaveDoc(List<? extends EsBaseData> docs) {

        try {
            BulkRequestBuilder bulkRequest = transportClient.prepareBulk().setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
            for (int i = 0, b = docs.size(); i < b; i++) {
                String id = docs.get(i).getId();
                String o = JSONObject.toJSONString(docs.get(i));
                bulkRequest.add(transportClient.prepareIndex(indexName(), typeName()).setId(id).setSource(o));
            }
            bulkRequest.execute().actionGet();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * 批量更新
     *
     * @param docs
     * @return
     */
    public Boolean bulkUpdateDoc(List<? extends EsBaseData> docs) {

        try {
            BulkRequestBuilder bulkRequest = transportClient.prepareBulk().setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
            for (int i = 0, b = docs.size(); i < b; i++) {
                bulkRequest.add(transportClient.prepareUpdate(indexName(), typeName(), docs.get(i).getId()).setDoc(JSONObject.toJSONString(docs.get(i))));

            }
            BulkResponse responses = bulkRequest.execute().actionGet();
            return !responses.hasFailures();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 删除document
     *
     * @param id
     * @return
     */
    public String deleteById(String id) {

        DeleteRequestBuilder requestBuilder = transportClient.prepareDelete(indexName(), typeName(), id).setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        DeleteResponse response = requestBuilder.get();
        return response.getId();
    }

    /**
     * 批量删除doc记录
     * @param ids
     * @return
     */
    public boolean bulkDeleteDoc(List<String> ids) {

        try {
            BulkRequestBuilder bulkRequestdel = transportClient.prepareBulk().setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
            for (int i = 0, b = ids.size(); i < b; i++) {
                String id = ids.get(i);
                bulkRequestdel.add(transportClient.prepareDelete(indexName(), typeName(), id));
            }
            bulkRequestdel.execute().actionGet(); // 删除
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 获取索引对应的存储内容
     *
     * @param id
     * @return
     */
    public String getIdx(String id) {
        GetResponse response = transportClient.prepareGet(indexName(), typeName(), id).get();
        if (response.isExists()) {
            return response.getSourceAsString();
        }
        return null;
    }


    /**
     * 先删除在添加数据
     *
     * @param docs
     * @return
     */
    public boolean insertAndUpdateDoc(List<? extends EsBaseData> docs) {

        try {
            BulkRequestBuilder bulkRequest = transportClient.prepareBulk().setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
            BulkRequestBuilder bulkRequestdel = transportClient.prepareBulk().setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
            for (int i = 0, b = docs.size(); i < b; i++) {

                String id = docs.get(i).getId();

                String o = JSONObject.toJSONString(docs.get(i));

                System.out.println(o);

                bulkRequestdel.add(transportClient.prepareDelete(indexName(), typeName(), id));
                bulkRequest.add(transportClient.prepareIndex(indexName(), typeName()).setId(id).setSource(o));

            }
            bulkRequestdel.execute().actionGet(); // 删除
            bulkRequest.execute().actionGet();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    /**
     * 使用原生查询语句查询
     *
     * @param page
     * @param builder
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> ElasticSearchPage<T> originSearch(ElasticSearchPage page,
                                                 QueryBuilder builder, Class<T> tClass) {
        // 设置起始页码
        int start = page.getPageNum() > 2 ? page.getPageNum() * page.getPageSize() : 0;
        SearchResponse response = transportClient.prepareSearch(indexName()).setTypes(typeName())
                .setQuery(builder).setSearchType(SearchType.QUERY_THEN_FETCH)
                .setFrom(start)
                .setSize(page.getPageSize()).get();
        List<T> result = new ArrayList<T>();
        SearchHits searchHits = response.getHits();
        //遍历搜索结果放入result中
        for (SearchHit st : searchHits.getHits())
            try {
                T object = JSONObject.parseObject(st.getSourceAsString(),tClass);

                result.add(object);
            } catch (Exception e) {

            }

        page.setRetList(result);

        page.setTotal(response.getHits().getTotalHits());

        return page;
    }

    /**
     * 使用原生查询
     * @param page
     * @param query
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> ElasticSearchPage<T> PageSearch(ElasticSearchPage page,
                                               ElasticSearchQuery query, Class<T> tClass) {

        int start = page.getPageNum() > 2 ? page.getPageNum() * page.getPageSize() : 0;
        SearchResponse response = null;
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(indexName()).setTypes(typeName())
                .setQuery(query.getQueryBuilder()).setSearchType(SearchType.QUERY_THEN_FETCH)
                .setFrom(start)
                .setSize(page.getPageSize());
        //当排序条件存在时设置排序条件
        if (query.getSortBuilders().size()>0){
            for (FieldSortBuilder fieldSortBuilder : query.getSortBuilders()) {
                searchRequestBuilder.addSort(fieldSortBuilder);
            }
        }
        response = searchRequestBuilder.get();
        List<T> result = new ArrayList<T>();
        SearchHits searchHits = response.getHits();
        for (SearchHit st : searchHits.getHits())
            try {
                T object = JSONObject.parseObject(st.getSourceAsString(),tClass);
                result.add(object);
            } catch (Exception e) {

            }

        page.setRetList(result);

        page.setTotal(response.getHits().getTotalHits());

        return page;
    }

    /**
     * 自行创建index的mapping映射（未使用ES默认分词器时必须调用）
     * @param clazz
     * @return
     */
    public String createMapping(Class clazz) {
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
            if("ik_pinyin_analyzer".equals(analyzer) && !isIndexExist(indexName()) && !synonym){
                createPinYinIndex(indexName());
            }else if (synonym){
                createPinYinIndexWithSynonym(indexName());
            }else if (!isIndexExist(indexName())){
                createIndex(indexName());
            }
            PutMappingRequest mapping = Requests.putMappingRequest(indexName()).type(typeName()).source(builder);
            transportClient.admin().indices().putMapping(mapping).actionGet();
            return "success";
        } catch (Exception e) {
            System.err.println(e);
            return "error";
        }
    }


    /**
     * 判断index是否存在
     * @param index
     * @return
     */
    public boolean isIndexExist(String index) {
        return transportClient.admin().indices().prepareExists(index).execute().actionGet().isExists();
    }

    public void createIndex(String index) {
        CreateIndexRequest request = new CreateIndexRequest(index);
        transportClient.admin().indices().create(request);
    }


    /**
     * 功能描述：删除索引
     * @param index 索引名
     */
    public void deleteIndex(String index) {
        try {
            if (isIndexExist(index)) {
                DeleteIndexResponse dResponse = transportClient.admin().indices().prepareDelete(index)
                        .execute().actionGet();
                if (!dResponse.isAcknowledged()) {
                    System.out.println("failed to delete index " + index + "!");

                }else {
                    System.out.println("delete index " + index + " successfully!");
                }
            } else {
                System.out.println("the index " + index + " not exists!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建名称为ik_pinyin_analyzer的自定义索引（支持ik和拼音）
     * @param indexName	索引名
     */
    public void createPinYinIndex(String indexName){
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


    /**
     * 创建名称为ik_pinyin_analyzer的自定义索引（支持ik和拼音及同义词）
     * 当ES中未配置同义词配置文件时会报错，调用请确认已定义同义词配置文件（analysis/synonyms.txt）
     * @param indexName	索引名
     */
    public void createPinYinIndexWithSynonym(String indexName){
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
    /**
     * 将java对象的属性类型转化为ES可以识别的
     * @param varType
     * @return
     */
    public String filedType(String varType){
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
}
