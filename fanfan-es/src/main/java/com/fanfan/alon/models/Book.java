package com.fanfan.alon.models;

import com.fanfan.alon.core.AnalyzerConstants;
import com.fanfan.alon.core.ESearchTypeColumn;
import com.fanfan.alon.core.EsBaseData;
import com.fanfan.alon.core.EsEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 功能描述:
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/9/3   13:46
 */
@EsEntity(indexName = "indexname",typeName = "typename")
@Component
public class Book implements EsBaseData {

    private String id;

    @ESearchTypeColumn(analyzer = AnalyzerConstants.IK_PINYIN_ANALYZER)
    private String name;

    private Integer sale;

    private Date publishDate;

    private String describe;

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
