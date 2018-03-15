package com.springapp.mvc.controller;

import com.springapp.mvc.bizservice.EsService;
import com.springapp.mvc.model.Entity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * elastic search controller
 *  use: http://localhost:6325/entityController/save?id=2&name=中国南京师范大学
 * @author songkejun
 * @create 2018-01-10 10:00
 **/
/*@Controller
@RequestMapping("/es")*/
public class EsController {
    private static Logger logger = LoggerFactory.getLogger(EsController.class);
    @Autowired
    EsService esService;

    @RequestMapping(value="/save", method= RequestMethod.GET)
    public String save(long id, String name) {
        System.out.println("save 接口");
        if(id>0 && StringUtils.isNotEmpty(name)) {
            Entity newEntity = new Entity(id,name);
            List<Entity> addList = new ArrayList<Entity>();
            addList.add(newEntity);
            esService.saveEntity(addList);
            return "OK";
        }else {
            return "Bad input value";
        }
    }

    @RequestMapping(value="/search", method=RequestMethod.GET)
    public List<Entity> save(String name) {
        List<Entity> entityList = null;
        if(StringUtils.isNotEmpty(name)) {
            entityList = esService.searchEntity(name);
        }
        return entityList;
    }
}
