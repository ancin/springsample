package com.springapp.mvc.model;

import java.io.Serializable;

/**
 * Entity
 *
 * @author songkejun
 * @create 2018-01-10 9:52
 **/
public class Entity implements Serializable {

    public static final String INDEX_NAME = "index_entity";

    public static final String TYPE = "tstype";

    private Long id;

    private String name;

    public Entity() {
        super();
    }

    public Entity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
