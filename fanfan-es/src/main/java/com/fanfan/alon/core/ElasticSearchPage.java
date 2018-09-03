package com.fanfan.alon.core;

import java.util.List;


/**
 * 功能描述:
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/9/3   11:48
 */
public class ElasticSearchPage<T> {

    private long total;

    private int pageSize;

    public int pageNum;

    private List<T> retList;

    public List<T> getRetList() {
        return retList;
    }

    public void setRetList(List<T>retList) {
        this.retList =retList;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total){
        this.total =total;
    }

    public int getPageSize() {
            return pageSize;
    }

    public void setPageSize(int pageSize){
        this.pageSize =pageSize;
    }

    public int getPageNum() {
        if(pageNum <=0){
            return 0;
        }else{
            return pageNum -= 1;
        }
    }

    public void setPageNum(int pageNum){
        this.pageNum =pageNum;
    }

    @Override
    public String toString() {
        return "ElasticSearchPage{" +
                "total=" + total +
                ", pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", retList=" + retList +
                '}';
    }
}
