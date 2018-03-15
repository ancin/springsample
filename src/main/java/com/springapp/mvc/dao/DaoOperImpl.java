package com.springapp.mvc.dao;

import java.util.UUID;

/**
 * dao oper
 *
 * @author songkejun
 * @create 2017-12-19 9:35
 **/
public class DaoOperImpl implements DaoOper {


    @Override
    public String getMsgName() {
        System.out.println("test get msg Name");
        String id = UUID.randomUUID().toString();
        try{
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "From test db get message name.token="+id;
    }
}
