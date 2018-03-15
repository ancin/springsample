package com.springapp.mvc.bizservice;

import com.springapp.mvc.model.Entity;
import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * EsServiceImpl
 *
 * @author songkejun
 * @create 2018-01-10 9:54
 **/
@Service
public class EsServiceImpl implements EsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsServiceImpl.class);

    private JestClient jestClient;

    @Override
    public void saveEntity(Entity entity) {
        Index index = new Index.Builder(entity).index(Entity.INDEX_NAME).type(Entity.TYPE).build();
        try {
            jestClient.execute(index);
            LOGGER.info("ES 插入完成");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void saveEntity(List<Entity> entityList) {
        Bulk.Builder bulk = new Bulk.Builder();
        for(Entity entity : entityList) {
            Index index = new Index.Builder(entity).index(Entity.INDEX_NAME).type(Entity.TYPE).build();
            bulk.addAction(index);
        }
        try {
            jestClient.execute(bulk.build());
            LOGGER.info("ES 插入完成");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public List<Entity> searchEntity(String searchContent) {
      /*  SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.query(QueryBuilders.queryStringQuery(searchContent));
        //searchSourceBuilder.field("name");
        searchSourceBuilder.query(QueryBuilders.matchQuery("name",searchContent));
        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(Entity.INDEX_NAME).addType(Entity.TYPE).build();
        try {
            JestResult result = jestClient.execute(search);
            return result.getSourceAsObjectList(Entity.class);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }*/
        return null;
    }
}
