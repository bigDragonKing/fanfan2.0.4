package com.fanfan.alon.core;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.util.ArrayList;
import java.util.List;


/**
 * 功能描述:
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/9/3   11:48
 */
public class ElasticSearchQuery {

    private BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

    private List<FieldSortBuilder> sortBuilders = new ArrayList<FieldSortBuilder>();



    /**
     * 功能描述:关键字模糊匹配
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/9/3   11:48
     */
    public ElasticSearchQuery match(String field, String value, SearchType type) {
        if (SearchType.and.equals(type)) {
            queryBuilder.must(QueryBuilders.matchQuery(field, value));
        } else if (SearchType.or.equals(type)) {
            queryBuilder.should(QueryBuilders.matchQuery(field, value));
        } else {
            queryBuilder.mustNot(QueryBuilders.matchQuery(field, value));
        }
        return this;
    }

    /**
     * 功能描述:短语完全匹配
     * @param: field 属性名称 value 属性值 type 匹配类型
     * @return:
     * @auther: zoujiulong
     * @date: 2018/9/3   11:49
     */
    public ElasticSearchQuery contain(String field, String value, SearchType type) {
        if (SearchType.and.equals(type)) {
            queryBuilder.filter(QueryBuilders.matchPhraseQuery(field, value));
        } else if (SearchType.or.equals(type)) {
            queryBuilder.should(QueryBuilders.matchPhraseQuery(field, value));
        } else {
            queryBuilder.mustNot(QueryBuilders.matchPhraseQuery(field, value));
        }
        return this;
    }

    /**
     * 功能描述:范围匹配
     * @param field 属性名称
     * @param start 范围起始值
     * @param end 范围结束值
     * @param type 匹配类型
     * @return:
     * @auther: zoujiulong
     * @date: 2018/9/3   11:49
     */
    public ElasticSearchQuery range(String field, Object start, Object end, SearchType type) {
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(field);
        if (start!=null){
            rangeQueryBuilder.from(start);
        }
        if (end!=null){
            rangeQueryBuilder.to(end);
        }
        if (SearchType.and.equals(type)) {
            queryBuilder.filter(rangeQueryBuilder);
        } else if (SearchType.or.equals(type)) {
            queryBuilder.should(rangeQueryBuilder);
        } else {
            queryBuilder.mustNot(rangeQueryBuilder);
        }
        return this;
    }

    /**
     * 功能描述: 排序
     * @param: filed 属性值 order 排序方式
     * @return:
     * @auther: zoujiulong
     * @date: 2018/9/3   11:50
     */
    public ElasticSearchQuery sort(String filed, SortOrder order){
        sortBuilders.add(SortBuilders.fieldSort(filed).order(order));
        return this;
    }

    /**
     * 功能描述:查询全部数据
     * @param:
     * @return:
     * @auther: zoujiulong
     * @date: 2018/9/3   11:50
     */
    public ElasticSearchQuery matchAll(){
        queryBuilder.filter(QueryBuilders.matchAllQuery());
        return this;
    }

    public BoolQueryBuilder getQueryBuilder() {
        return queryBuilder;
    }

    public List<FieldSortBuilder> getSortBuilders() {
        return sortBuilders;
    }
}
