package com.fanfan.alon.service;

import com.fanfan.alon.core.ElasticSearchPage;
import com.fanfan.alon.core.ElasticSearchQuery;
import com.fanfan.alon.core.SearchType;
import com.fanfan.alon.models.Book;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能描述:
 * @param:
 * @return:
 * @auther: zoujiulong
 * @date: 2018/9/3   13:47
 */
@Service
public class BookService extends TransportClientRepository{

    @Autowired
    public BookService(TransportClient transportClient) {
        super(transportClient);
    }

    public String indexName() {
        return "book_index_7";
    }

    public String typeName() {
        return "book_type_7";
    }

    public ElasticSearchPage<Book> getBooksByWords(String keyword){
        ElasticSearchQuery query = new ElasticSearchQuery();
        query.contain("name",keyword, SearchType.and);
        /*query.range("publishDate",1535332157,1535418557,SearchType.and);*/
        ElasticSearchPage<Book> page = new ElasticSearchPage<>();
        page.setPageNum(1);
        page.setPageSize(10);
        ElasticSearchPage<Book> result = PageSearch(page, query, Book.class);
        return result;
    }
}
