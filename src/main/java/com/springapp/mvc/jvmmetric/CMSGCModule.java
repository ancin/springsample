package com.springapp.mvc.jvmmetric;

import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

/**
 * @author songkejun
 * @create 2018-01-04 18:17
 **/
public class CMSGCModule extends GCModule  {
    public CMSGCModule(List<GarbageCollectorMXBean> beans) {
        super(beans);
    }

    @Override protected String getOldGCName() {
        return "ConcurrentMarkSweep";
    }

    @Override protected String getNewGCName() {
        return "ParNew";
    }
}
