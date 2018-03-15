package com.springapp.mvc.jvm;

import sun.util.resources.cldr.pa.CurrencyNames_pa;

/**
 * SysPerformInfo
 *
 * @author songkejun
 * @create 2017-12-14 14:40
 **/
public class SysPerformInfo {

    private String id;
    private float cpuRate;
    private float memoryRate;
    private int threadCount;
    private float upSpeed;
    private float downSpeed;


    public float getDownSpeed() {
        return downSpeed;
    }

    public void setDownSpeed(float downSpeed) {
        this.downSpeed = downSpeed;
    }

    public float getUpSpeed() {
        return upSpeed;
    }

    public void setUpSpeed(float upSpeed) {
        this.upSpeed = upSpeed;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public float getMemoryRate() {
        return memoryRate;
    }

    public void setMemoryRate(float memoryRate) {
        this.memoryRate = memoryRate;
    }

    public float getCpuRate() {
        return cpuRate;
    }

    public void setCpuRate(float cpuRate) {
        this.cpuRate = cpuRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{id="+id+",cpuRate="+ cpuRate+",memRate="+memoryRate+",threadCount="+threadCount+",upSpeed="+upSpeed+",downSpeed="+downSpeed+"}";
    }
}
