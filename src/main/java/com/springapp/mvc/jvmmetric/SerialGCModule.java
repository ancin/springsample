package com.springapp.mvc.jvmmetric;

import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

/**
 * @author songkejun
 * @create 2018-01-04 18:19
 **/
public class SerialGCModule extends GCModule {
    public SerialGCModule(List<GarbageCollectorMXBean> beans) {
        super(beans);
    }

    @Override protected String getOldGCName() {
        return "MarkSweepCompact";
    }

    @Override protected String getNewGCName() {
        return "Copy";
    }
}
