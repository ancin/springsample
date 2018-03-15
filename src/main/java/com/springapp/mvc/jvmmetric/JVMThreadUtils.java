package com.springapp.mvc.jvmmetric;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * @author songkejun
 * @create 2018-01-04 19:41
 **/
public class JVMThreadUtils {
    static private ThreadMXBean threadMXBean;

    static {
        threadMXBean = ManagementFactory.getThreadMXBean();
    }

    //Daemon线程总量
    static public int getDaemonThreadCount() {
        return threadMXBean.getDaemonThreadCount();
    }

    //当前线程总量
    static public int getThreadCount() {
        return threadMXBean.getThreadCount();
    }

    //死锁线程总量
    static public int getDeadLockedThreadCount() {
        try {
            long[] deadLockedThreadIds = threadMXBean.findDeadlockedThreads();
            if (deadLockedThreadIds == null) {
                return 0;
            }
            return deadLockedThreadIds.length;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
