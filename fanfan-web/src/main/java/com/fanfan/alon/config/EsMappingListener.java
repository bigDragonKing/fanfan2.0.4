package com.fanfan.alon.config;


import com.fanfan.alon.core.EsEntity;
import com.fanfan.alon.utils.EsUtil;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Map;

@Configuration
public class EsMappingListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private TransportClient transportClient;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(EsEntity.class);
        EsEntity annotation = null;
        boolean exists = false;
        for(Object bean:beans.values()){
            annotation = bean.getClass().getAnnotation(EsEntity.class);
            String indexName = annotation.indexName();
            String typeName = annotation.typeName();
            exists = transportClient.admin().indices().prepareExists(indexName).execute().actionGet().isExists();
            if (!exists){
                EsUtil.createMapping(transportClient,indexName,typeName,bean.getClass());
            }

        }
    }
}
