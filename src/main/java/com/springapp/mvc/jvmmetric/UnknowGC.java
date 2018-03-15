package com.springapp.mvc.jvmmetric;

import java.util.LinkedList;
import java.util.List;

/**
 * @author songkejun
 * @create 2018-01-04 17:48
 **/
public class UnknowGC implements GCMetricAccessor {
    @Override
    public List<GC> getGCList() {
        List<GC> gcList = new LinkedList<GC>();
       // gcList.add(GC.newBuilder().setPhrase(GCPhrase.NEW).build());
       // gcList.add(GC.newBuilder().setPhrase(GCPhrase.OLD).build());
        return gcList;
    }
}
