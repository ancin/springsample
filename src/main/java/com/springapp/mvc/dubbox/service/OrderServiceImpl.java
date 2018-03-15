package com.springapp.mvc.dubbox.service;

import java.util.ArrayList;
import java.util.List;

/**
 * order service implments
 *
 * @author songkejun
 * @create 2017-12-20 14:44
 **/
public class OrderServiceImpl implements OrderService {
    @Override
    public List<Order> query() {
        List orderList = new ArrayList();
        for(int i=0;i<5;i++){
            Order order = new Order();
            order.setCode("code"+i);
            order.setId(i+"");
            order.setName("name"+i);
            orderList.add(order);
        }
        return orderList;
    }
}
