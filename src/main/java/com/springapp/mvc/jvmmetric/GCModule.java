package com.springapp.mvc.jvmmetric;

import java.lang.management.GarbageCollectorMXBean;
import java.util.LinkedList;
import java.util.List;

public abstract class GCModule implements GCMetricAccessor{
    private List<GarbageCollectorMXBean> beans;

    public GCModule(List<GarbageCollectorMXBean> beans) {
        this.beans = beans;
    }

    @Override
    public List<GC> getGCList() {
        List<GC> gcList = new LinkedList<GC>();
        for (GarbageCollectorMXBean bean : beans) {
            String name = bean.getName();
            int phrase;
            if (name.equals(getNewGCName())) {
                phrase = GCPhrase.NEW;
            } else if (name.equals(getOldGCName())) {
                phrase = GCPhrase.OLD;
            } else {
                continue;
            }

            gcList.add(
                    GC.newBuilder()
            );
        }

        return gcList;
    }

    protected abstract String getOldGCName();

    protected abstract String getNewGCName();
}
