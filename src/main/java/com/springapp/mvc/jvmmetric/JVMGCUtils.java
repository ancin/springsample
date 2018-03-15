package com.springapp.mvc.jvmmetric;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ListIterator;

/**
 * @author songkejun
 * @create 2018-01-04 19:40
 **/
public class JVMGCUtils {
    static private GarbageCollectorMXBean fullGC;
    static private GarbageCollectorMXBean youngGC;

    static{
        for (ListIterator<GarbageCollectorMXBean> iter = ManagementFactory.getGarbageCollectorMXBeans().listIterator(); iter.hasNext();) {
            GarbageCollectorMXBean item = iter.next();
            if ("ConcurrentMarkSweep".equals(item.getName()) //
                    || "MarkSweepCompact".equals(item.getName()) //
                    || "PS MarkSweep".equals(item.getName()) //
                    || "G1 Old Generation".equals(item.getName()) //
                    || "Garbage collection optimized for short pausetimes Old Collector".equals(item.getName()) //
                    || "Garbage collection optimized for throughput Old Collector".equals(item.getName()) //
                    || "Garbage collection optimized for deterministic pausetimes Old Collector".equals(item.getName()) //
                    ) {
                fullGC = item;
            } else if ("ParNew".equals(item.getName()) //
                    || "Copy".equals(item.getName()) //
                    || "PS Scavenge".equals(item.getName()) //
                    || "G1 Young Generation".equals(item.getName()) //
                    || "Garbage collection optimized for short pausetimes Young Collector".equals(item.getName()) //
                    || "Garbage collection optimized for throughput Young Collector".equals(item.getName()) //
                    || "Garbage collection optimized for deterministic pausetimes Young Collector".equals(item.getName()) //
                    ) {
                youngGC = item;
            }
        }
    }//static

    //YGC总次数
    static public long getYoungGCCollectionCount() {
        return youngGC == null ? 0 : youngGC.getCollectionCount();
    }

    //YGC总时间
    static public long getYoungGCCollectionTime() {
        return youngGC == null ? 0 : youngGC.getCollectionTime();
    }

    //FGC总次数
    public long getFullGCCollectionCount() {
        return fullGC == null ? 0 : fullGC.getCollectionCount();
    }

    //FGC总次数
    public long getFullGCCollectionTime() {
        return fullGC == null ? 0 : fullGC.getCollectionTime();
    }
}
