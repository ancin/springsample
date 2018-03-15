package com.springapp.mvc.bizservice;

import com.springapp.mvc.model.Entity;

import java.util.List;

public interface EsService {
    void saveEntity(Entity entity);

    void saveEntity(List<Entity> entityList);

    List<Entity> searchEntity(String searchContent);
}
