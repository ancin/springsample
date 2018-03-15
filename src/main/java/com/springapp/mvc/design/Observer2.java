package com.springapp.mvc.design;

/**
 * @author songkejun
 * @create 2018-01-08 9:35
 **/
public class Observer2 implements Observer {
    @Override
    public void update() {
        System.out.println("observer2 has received!");
    }
}
