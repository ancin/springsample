package com.springapp.mvc.design;

/**
 * @author songkejun
 * @create 2018-01-08 9:38
 **/
public class ObserverTest {

    public static void main(String[] args){
        Subject sub = new MySubject();
        sub.add(new Observer1());
        sub.add(new Observer2());
        sub.operation();
    }
}
