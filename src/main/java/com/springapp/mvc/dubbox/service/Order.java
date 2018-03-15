package com.springapp.mvc.dubbox.service;

import java.io.Serializable;

/**
 * order
 *
 * @author songkejun
 * @create 2017-12-20 14:42
 **/
public class Order implements Serializable{
    private String id;
    private String name;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
