package com.springapp.mvc.jvmmetric;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.util.List;

/**
 * JVMMemoryUtils
 *
 * @author songkejun
 * @create 2018-01-04 19:39
 **/
public class JVMMemoryUtils {
    static private MemoryMXBean memoryMXBean;
    static private MemoryPoolMXBean permGenMxBean;
    static private MemoryPoolMXBean oldGenMxBean;
    static private MemoryPoolMXBean edenSpaceMxBean;
    static private MemoryPoolMXBean pSSurvivorSpaceMxBean;

    static {
        memoryMXBean = ManagementFactory.getMemoryMXBean();
        List<MemoryPoolMXBean> list = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean item : list) {
            if ("CMS Perm Gen".equals(item.getName()) //
                    || "Perm Gen".equals(item.getName()) //
                    || "PS Perm Gen".equals(item.getName()) //
                    || "G1 Perm Gen".equals(item.getName()) //
                    ) {
                permGenMxBean = item;
            } else if ("CMS Old Gen".equals(item.getName()) //
                    || "Tenured Gen".equals(item.getName()) //
                    || "PS Old Gen".equals(item.getName()) //
                    || "G1 Old Gen".equals(item.getName()) //
                    ) {
                oldGenMxBean = item;
            } else if ("Par Eden Space".equals(item.getName()) //
                    || "Eden Space".equals(item.getName()) //
                    || "PS Eden Space".equals(item.getName()) //
                    || "G1 Eden".equals(item.getName()) //
                    ) {
                edenSpaceMxBean = item;
            } else if ("Par Survivor Space".equals(item.getName()) //
                    || "Survivor Space".equals(item.getName()) //
                    || "PS Survivor Space".equals(item.getName()) //
                    || "G1 Survivor".equals(item.getName()) //
                    ) {
                pSSurvivorSpaceMxBean = item;
            }
        }
    }// static

    // 堆内存-已使用
    static public long getHeapMemoryUsed() {
        return memoryMXBean.getHeapMemoryUsage().getUsed();
    }

    // 堆内存-最大值
    static public long getHeapMemoryMax() {
        return memoryMXBean.getHeapMemoryUsage().getMax();
    }

    // 堆外内存-已使用
    static public long getNonHeapMemoryUsed() {
        return memoryMXBean.getNonHeapMemoryUsage().getUsed();
    }

    // 堆外内存-最大值
    static public long getNonHeapMemoryMax() {
        return memoryMXBean.getNonHeapMemoryUsage().getMax();
    }

    // 持久代-已使用
    static public long getPermGenUsed() {
        return null == permGenMxBean ? 0 : permGenMxBean.getUsage().getUsed();
    }

    // 持久代-最大值
    static public long getPermGenMax() {
        return null == permGenMxBean ? 0 : permGenMxBean.getUsage().getMax();
    }

    // 老年代-已使用
    static public long getOldGenUsed() {
        return null == oldGenMxBean ? 0 : oldGenMxBean.getUsage().getUsed();
    }

    // 老年代-最大值
    static public long getOldGenMax() {
        return null == oldGenMxBean ? 0 : oldGenMxBean.getUsage().getMax();
    }

    // Eden-已使用
    static public long getEdenGenUsed() {
        return null == edenSpaceMxBean ? 0 : edenSpaceMxBean.getUsage().getUsed();
    }

    // Eden-最大值
    static public long getEdenGenMax() {
        return null == edenSpaceMxBean ? 0 : edenSpaceMxBean.getUsage().getMax();
    }

    // Survivor-已使用
    static public long getSurvivorUsed() {
        return null == pSSurvivorSpaceMxBean ? 0 : pSSurvivorSpaceMxBean.getUsage().getUsed();
    }

    // Survivor-最大值
    static public long getSurvivorMax() {
        return null == pSSurvivorSpaceMxBean ? 0 : pSSurvivorSpaceMxBean.getUsage().getMax();
    }

    public static void main(String[] args) {
        System.out.println(JVMMemoryUtils.getHeapMemoryMax());
        System.out.println(JVMMemoryUtils.getHeapMemoryUsed());
        System.out.println(JVMMemoryUtils.getNonHeapMemoryMax());
        System.out.println(JVMMemoryUtils.getNonHeapMemoryUsed());
        System.out.println("OldGenMax:"+JVMMemoryUtils.getOldGenMax());
        System.out.println("OldGenUsed:"+JVMMemoryUtils.getOldGenUsed());
        System.out.println("PermGenMax:"+JVMMemoryUtils.getPermGenMax());
        System.out.println("PermGenUsed:"+JVMMemoryUtils.getPermGenUsed());
        System.out.println("EdenGenMax:"+JVMMemoryUtils.getEdenGenMax());
        System.out.println("EdenGenUsed:"+JVMMemoryUtils.getEdenGenUsed());
        System.out.println("SurvivorMax:"+JVMMemoryUtils.getSurvivorMax());
        System.out.println("SurvivorUsed:"+JVMMemoryUtils.getSurvivorUsed());
    }
}
