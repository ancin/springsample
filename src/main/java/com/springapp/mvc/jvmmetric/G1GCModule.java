package com.springapp.mvc.jvmmetric;

import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

/**
 * G1GCModule
 *
 * @author songkejun
 * @create 2018-01-04 18:18
 **/
public class G1GCModule extends GCModule{
    public G1GCModule(List<GarbageCollectorMXBean> beans) {
        super(beans);
    }

    @Override protected String getOldGCName() {
        return "G1 Old Generation";
    }

    @Override protected String getNewGCName() {
        return "G1 Young Generation";
    }
}
