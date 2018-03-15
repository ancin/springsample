package com.springapp.mvc.design;

/**
 * MySubject
 *
 * @author songkejun
 * @create 2018-01-08 9:37
 **/
public class MySubject extends AbstractSubject {
    @Override
    public void operation() {
        System.out.println("update self!");
        notifyObservers();
    }
}
