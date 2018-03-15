package com.springapp.mvc.jvmmetric;

/**
 * @author songkejun
 * @create 2018-01-04 17:41
 **/
public class GC {

    public int getPhrase() {
        return phrase;
    }

    public void setPhrase(int phrase) {
        this.phrase = phrase;
    }

    int phrase = GCPhrase.NEW;
    int count = 2;
    int time = 3;

    public static GC newBuilder(){
        return new GC();
    }
}
