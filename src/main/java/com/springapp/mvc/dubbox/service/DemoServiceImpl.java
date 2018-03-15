package com.springapp.mvc.dubbox.service;

/**
 * Created by zdsoft on 14-10-27.
 */
public class DemoServiceImpl implements DemoService {


    public String sayHello() {
        System.out.println("#########我是dubbo provider生产者");
        return "hello dubbo provider return";
    }
}
