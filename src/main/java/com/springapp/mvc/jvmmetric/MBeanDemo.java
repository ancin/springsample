package com.springapp.mvc.jvmmetric;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.*;
import java.lang.management.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author songkejun
 * @create 2018-01-04 19:08
 **/
public class MBeanDemo {

    /**
     * Java 虚拟机的运行时系统
     */
    public static void showJvmInfo() {
        RuntimeMXBean mxbean = ManagementFactory.getRuntimeMXBean();
        String vendor = mxbean.getVmVendor();
        System.out.println("jvm name:" + mxbean.getVmName());
        System.out.println("jvm version:" + mxbean.getVmVersion());
        System.out.println("jvm bootClassPath:" + mxbean.getBootClassPath());
        System.out.println("jvm start time:" + mxbean.getStartTime());
    }

    /**
     * Java 虚拟机的内存系统
     */
    public static void showMemoryInfo() {
        MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = mem.getHeapMemoryUsage();
        long noHeapMax =mem.getNonHeapMemoryUsage().getMax();
        System.out.println("Heap committed:" + heap.getCommitted() + " ,init:" + heap.getInit() + " ,max:"
                + heap.getMax() + " ,used:" + heap.getUsed()+",noHeapMax:"+noHeapMax);
    }

    /**
     * Java 虚拟机在其上运行的操作系统
     */
    public static void showSystem() {
        OperatingSystemMXBean op = ManagementFactory.getOperatingSystemMXBean();
        System.out.println("Architecture: " + op.getArch());
        System.out.println("Processors: " + op.getAvailableProcessors());
        System.out.println("System name: " + op.getName());
        System.out.println("System version: " + op.getVersion());
        System.out.println("Last minute load: " + op.getSystemLoadAverage());
    }

    /**
     * Java 虚拟机的类加载系统
     */
    public static void showClassLoading() {
        ClassLoadingMXBean cl = ManagementFactory.getClassLoadingMXBean();
        System.out.println("TotalLoadedClassCount: " + cl.getTotalLoadedClassCount());
        System.out.println("LoadedClassCount" + cl.getLoadedClassCount());
        System.out.println("UnloadedClassCount:" + cl.getUnloadedClassCount());
    }

    /**
     * Java 虚拟机的编译系统
     */
    public static void showCompilation() {
        CompilationMXBean com = ManagementFactory.getCompilationMXBean();
        System.out.println("TotalCompilationTime:" + com.getTotalCompilationTime());
        System.out.println("name:" + com.getName());
    }

    /**
     * Java 虚拟机的线程系统
     */
    public static void showThread() {
        ThreadMXBean thread = ManagementFactory.getThreadMXBean();
        System.out.println("ThreadCount" + thread.getThreadCount());
        System.out.println("AllThreadIds:" + thread.getAllThreadIds());
        System.out.println("CurrentThreadUserTime" + thread.getCurrentThreadUserTime());
        //......还有其他很多信息
    }

    /**
     * Java 虚拟机中的垃圾回收器。
     */
    public static void showGarbageCollector() {
        List<GarbageCollectorMXBean> gc = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean GarbageCollectorMXBean : gc) {
            System.out.println("name:" + GarbageCollectorMXBean.getName());
            System.out.println("CollectionCount:" + GarbageCollectorMXBean.getCollectionCount());
            System.out.println("CollectionTime" + GarbageCollectorMXBean.getCollectionTime());
        }
    }

    /**
     * Java 虚拟机中的内存管理器
     */
    public static void showMemoryManager() {
        List<MemoryManagerMXBean> mm = ManagementFactory.getMemoryManagerMXBeans();
        for (MemoryManagerMXBean eachmm : mm) {
            System.out.println("name:" + eachmm.getName());
            System.out.println("MemoryPoolNames:" + eachmm.getMemoryPoolNames().toString());
        }
    }

    /**
     * Java 虚拟机中的内存池
     */
    public static void showMemoryPool() {
        List<MemoryPoolMXBean> mps = ManagementFactory.getMemoryPoolMXBeans();
        System.out.println(ManagementFactory.getPlatformMBeanServer().getMBeanCount());
        for (MemoryPoolMXBean mp : mps) {
            System.out.println("name:" + mp.getName());
            System.out.println("CollectionUsage:" + mp.getCollectionUsage());
            System.out.println("getUsage:" + mp.getUsage().getUsed());
            System.out.println("type:" + mp.getType());
        }
    }

    public long getUsableMemoryForCaching() {
        long youngGenSize = 0;
        long oldGenSize = 0;

        List<MemoryPoolMXBean> mpools = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean mpool : mpools) {
            MemoryUsage usage = mpool.getUsage();
            if (usage != null) {
                String name = mpool.getName();
                if (name.equalsIgnoreCase("PS Eden Space")) {
                    // Parallel.
                    youngGenSize = usage.getMax();
                    System.out.println("PS Eden Space,youngGensize:" + youngGenSize);
                } else if (name.equalsIgnoreCase("PS Old Gen")) {
                    // Parallel.
                    oldGenSize = usage.getMax();
                    System.out.println("PS Old Gen,oldGenSize:" + oldGenSize);
                } else if (name.equalsIgnoreCase("Par Eden Space")) {
                    // CMS.
                    youngGenSize = usage.getMax();
                    System.out.println("Par Eden Space:" + youngGenSize);
                } else if (name.equalsIgnoreCase("CMS Old Gen")) {
                    // CMS.
                    oldGenSize = usage.getMax();
                    System.out.println("CMS Old Gen:" + oldGenSize);
                }
            }
        }

        if (youngGenSize > 0 && oldGenSize > youngGenSize) {
            // We can calculate available memory based on GC info.
            System.out.println("available memory:" + (oldGenSize - youngGenSize));
            return oldGenSize - youngGenSize;
        } else if (oldGenSize > 0) {
            // Small old gen. It is going to be difficult to avoid full GCs if the
            // young gen is bigger.
            return oldGenSize * 40 / 100;
        } else {
            // Unknown GC (G1, JRocket, etc).
            Runtime runTime = Runtime.getRuntime();
            runTime.gc();
            runTime.gc();
            return (runTime.freeMemory() + (runTime.maxMemory() - runTime
                    .totalMemory())) * 40 / 100;
        }
    }


    public String getSystemCpuLoad() throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
        AttributeList list = mbs.getAttributes(name, new String[]{"SystemCpuLoad"});

        if (list.isEmpty()) {
            return "0%";
        }

        Attribute att = (Attribute) list.get(0);
        Double value = (Double) att.getValue();
        DecimalFormat df =  new DecimalFormat("#.00");

        return df.format(value*100)+"%";
    }

    public void runTask() {

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("=======JVM base info=======");
                showJvmInfo();
                System.out.println("=======memory base info=======");
                showMemoryInfo();
                System.out.println("=======system base info=======");
                showSystem();
                System.out.println("=======class load info=======");
                showClassLoading();
                System.out.println("=======compilation info=======");
                showCompilation();
                System.out.println("=======thread base info=======");
                showThread();
                System.out.println("=======garbage collector base info=======");
                showGarbageCollector();
                System.out.println("=======memory manager info=======");
                showMemoryManager();
                System.out.println("=======memory pool info=======");
                showMemoryPool();
                System.out.println("=======Usable Memory info=======");
                getUsableMemoryForCaching();
                try {
                    System.out.println("=======CPU usage info=======");
                    System.out.println(getSystemCpuLoad());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 2, 3, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        System.out.println("available CPU:"+Runtime.getRuntime());
        MBeanDemo demo = new MBeanDemo();
        demo.runTask();
    }

}
