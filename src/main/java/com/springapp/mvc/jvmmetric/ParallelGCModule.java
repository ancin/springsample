package com.springapp.mvc.jvmmetric;

import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

/**
 * @author songkejun
 * @create 2018-01-04 17:56
 **/
public class ParallelGCModule extends GCModule {
    public ParallelGCModule(List<GarbageCollectorMXBean> beans) {
        super(beans);
    }

    @Override
   protected String getOldGCName() {
        return "PS MarkSweep";
    }

    @Override
    protected String getNewGCName() {
        return "PS Scavenge";
    }
}
