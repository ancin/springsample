package com.springapp.mvc.design;

/**
 * Observer1
 *
 * @author songkejun
 * @create 2018-01-08 9:34
 **/
public class Observer1 implements Observer {
    @Override
    public void update() {
        System.out.println("observer1 has received!");
    }
}
