package com.springapp.mvc.jvmmetric;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Properties;

/**
 * @author songkejun
 * @create 2018-01-04 19:38
 **/
public class JVMInfoUtils {
    static private RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
    static private ClassLoadingMXBean classLoad = ManagementFactory.getClassLoadingMXBean();
    static private CompilationMXBean compilation = ManagementFactory.getCompilationMXBean();
    static private Properties properties = System.getProperties();
    // JVM规范名称
    static public String getJVMSpecName() {
        return runtime.getSpecName();
    }

    // JVM规范运营商
    static public String getJVMSpecVendor() {
        return runtime.getSpecVendor();
    }

    // JVM规范版本
    static public String getJVMSpecVersion() {
        return runtime.getSpecVersion();
    }

    // JVM名称
    static public String getJVMName() {
        return runtime.getVmName();
    }

    // JVM运营商
    static public String getJVMVendor() {
        return runtime.getVmVendor();
    }

    // JVM实现版本
    static public String getJVMVersion() {
        return runtime.getVmVersion();
    }

    // JVM启动时间
    static public long getJVMStartTimeMs() {
        return runtime.getStartTime();
    }

    // JVM运行时间
    static public long getJVMUpTimeMs() {
        return runtime.getUptime();
    }

    // JVM当前加载类总量
    static public long getJVMLoadedClassCount() {
        return classLoad.getLoadedClassCount();
    }

    // JVM已卸载类总量
    static public long getJVMUnLoadedClassCount() {
        return classLoad.getUnloadedClassCount();
    }

    // JVM从启动到现在加载类总量
    static public long getJVMTotalLoadedClassCount() {
        return classLoad.getTotalLoadedClassCount();
    }

    // JIT编译器名称
    static public String getJITName() {
        return compilation.getName();
    }

    // JIT总编译时间
    static public long getJITTimeMs() {
        if (compilation.isCompilationTimeMonitoringSupported()) {
            return compilation.getTotalCompilationTime();
        }
        return -1;
    }

    // 获取指定key的属性值
    static public String getSystemProperty(String key) {
        return properties.getProperty(key);
    }

    static public Properties getSystemProperty() {
        return properties;
    }

}
