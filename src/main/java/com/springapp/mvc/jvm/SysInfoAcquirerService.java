package com.springapp.mvc.jvm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sun.management.OperatingSystemMXBean;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.*;

/**
 * SysInfoAcquirerService
 *
 * @author songkejun
 * @create 2017-12-14 13:47
 **/
public class SysInfoAcquirerService {

    private static final int CPUTIME = 5000;
    private static final int PERIOD_TIME = 1000 * 30;
    private static final int SLEEP_TIME = 1000 * 30 * 9;

    private static final int PERCENT = 100;

    private static final int FAULTLENGTH = 10;
    private String isWindowsOrLinux = isWindowsOrLinux();
    private String pid = "";
    private Timer sysInfoGetTimer = new Timer("sysInfoGet");

    public void init() {

        if (isWindowsOrLinux.equals("windows")) { // 判断操作系统类型是否为：windows

            getJvmPIDOnWindows();
        } else {
            getJvmPIDOnLinux();
        }
        sysInfoGetTimer.schedule(new SysInfoAcquirerTimerTask(), 10 * 1000, PERIOD_TIME);
    }

    /***
     *
     * @return system name.
     */
    public String isWindowsOrLinux() {
        String osName = System.getProperty("os.name");
        String sysName = "";
        if (osName.toLowerCase().startsWith("windows")) {
            sysName = "windows";
        } else if (osName.toLowerCase().startsWith("linux")) {
            sysName = "linux";
        }
        return sysName;
    }

    public String getCPURate() {
        String cpuRate = "";
        if (isWindowsOrLinux.equals("windows")) { // 判断操作系统类型是否为：windows
            cpuRate = getCPURateForWindows();
        } else {
            cpuRate = getCPURateForLinux();
        }
        System.out.println("cpuRate:" + cpuRate);
        return cpuRate;
    }

    public void getJvmPIDOnWindows() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        pid = runtime.getName().split("@")[0];
        System.out.println("PID of JVM:" + pid);
    }

    public void getJvmPIDOnLinux() {
        String command = "pidof java";
        BufferedReader in = null;
        Process pro = null;
        try {
            pro = Runtime.getRuntime().exec(new String[]{"sh", "-c", command});
            in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            StringTokenizer ts = new StringTokenizer(in.readLine());
            pid = ts.nextToken();
            System.out.print("PID of JVM:" + pid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMemoryRate() {
        String memRate = "";
        if (isWindowsOrLinux.equals("windows")) { // 判断操作系统类型是否为：windows
            memRate = getMemoryRateForWindows();// 查询windows系统的cpu占用率
        } else {
            memRate = getMemoryRateForLinux();// 查询linux系统的cpu占用率
        }
        System.out.println("memory rate:" + memRate);
        return memRate;

    }

    public int getThreadCount() {
        int threadCount = 0;
        if (isWindowsOrLinux.equals("windows")) { // 判断操作系统类型是否为：windows
            threadCount = getThreadCountForWindows();// 查询windows系统的线程数
        } else {
            threadCount = getThreadCountForLinux();// 查询linux系统的线程数
        }
        System.out.println("threadcount:" + threadCount);
        return threadCount;
    }

    public String getNetworkThroughput() {
        String throughput = "";
        if (isWindowsOrLinux.equals("windows")) { // 判断操作系统类型是否为：windows
            throughput = getNetworkThroughputForWindows(); // 查询windows系统的磁盘读写速率
        } else {
            throughput = getNetworkThroughputForLinux(); // 查询linux系统的磁盘读写速率
        }
        return throughput;
    }

    public String getNetworkThroughputForWindows() {
        Process pro1 = null;
        Process pro2 = null;
        Runtime r = Runtime.getRuntime();
        BufferedReader input = null;
        String rxPercent = "";
        String txPercent = "";
        JSONObject jsonObject = new JSONObject();

        try {
            String command = "netstat -e";
            pro1 = r.exec(command);
            input = new BufferedReader(new InputStreamReader(pro1.getInputStream()));
            String result1[] = readInLine(input, "windows");
            Thread.sleep(SLEEP_TIME);
            pro2 = r.exec(command);

            input = new BufferedReader(new InputStreamReader(pro2.getInputStream()));
            String result2[] = readInLine(input, "windows");

            rxPercent = formatNumber((Long.parseLong(result2[0]) - Long.parseLong(result1[0]))
                    / (float) (1024 * 1024 * (SLEEP_TIME / 1000))); // 上行速率(MB/s)
            txPercent = formatNumber((Long.parseLong(result2[1]) - Long.parseLong(result1[1]))
                    / (float) (1024 * 1024 * (SLEEP_TIME / 1000))); // 下行速率(MB/s)
            input.close();

            pro1.destroy();

            pro2.destroy();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        jsonObject.put("rxPercent", rxPercent); // 下行速率
        jsonObject.put("txPercent", txPercent); // 上行速率
        String netRate = JSON.toJSONStringWithDateFormat(jsonObject, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        System.out.println("netRate:" + netRate);
        return netRate;
    }

    public String getNetworkThroughputForLinux() {
        Process pro1 = null;
        Process pro2 = null;
        Runtime r = Runtime.getRuntime();
        BufferedReader input = null;
        String rxPercent = "";
        String txPercent = "";
        JSONObject jsonObject = new JSONObject();
        try {
            String command = "watch ifconfig";
            pro1 = r.exec(command);
            input = new BufferedReader(new InputStreamReader(pro1.getInputStream()));

            String result1[] = readInLine(input, "linux");

            Thread.sleep(SLEEP_TIME);
            pro2 = r.exec(command);
            input = new BufferedReader(new InputStreamReader(pro2.getInputStream()));
            String result2[] = readInLine(input, "linux");
            rxPercent = formatNumber((Long.parseLong(result2[0]) - Long.parseLong(result1[0]))

                    / (float) (1024 * 1024 * (SLEEP_TIME / 1000))); // 下行速率(MB/s)

            txPercent = formatNumber((Long.parseLong(result2[1]) - Long.parseLong(result1[1]))

                    / (float) (1024 * 1024 * (SLEEP_TIME / 1000))); // 上行速率(MB/s)

            input.close();
            pro1.destroy();
            pro2.destroy();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        jsonObject.put("rxPercent", rxPercent); // 下行速率
        jsonObject.put("txPercent", txPercent); // 上行速率
        return JSON.toJSONStringWithDateFormat(jsonObject, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
    }

    public String getCPURateForWindows() {
        System.out.println("get cpu rate windows");
        try {
            String procCmd = System.getenv("windir") + "\\system32\\wbem\\wmic.exe  process "
                    + "  get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
// 取进程信息
            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
            Thread.sleep(CPUTIME);
            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
            if (c0 != null && c1 != null) {
                long idletime = c1[0] - c0[0];
                long busytime = c1[1] - c0[1];
                long cpuRate = PERCENT * (busytime) / (busytime + idletime);
                if (cpuRate > 100) {
                    cpuRate = 100;
                } else if (cpuRate < 0) {
                    cpuRate = 0;
                }
                return String.valueOf(PERCENT * (busytime) / (busytime + idletime));
            } else {
                return "0.0";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "0.0";
        }
    }

    public String getCPURateForLinux() {

        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader brStat = null;
        StringTokenizer tokenStat = null;
        String user = "";
        String linuxVersion = System.getProperty("os.version");
        try {
            System.out.println("Linux版本: " + linuxVersion);
            Process process = Runtime.getRuntime().exec(new String[]{"sh", "-c", "top -b -p " + pid});
            try {
// top命令默认3秒动态更新结果信息，让线程睡眠5秒以便获取最新结果
                Thread.sleep(CPUTIME);
                is = process.getInputStream();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            isr = new InputStreamReader(is);

            brStat = new BufferedReader(isr);

            if (linuxVersion.equals("2.4")) {
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                tokenStat = new StringTokenizer(brStat.readLine());
                tokenStat.nextToken();

                tokenStat.nextToken();

                user = tokenStat.nextToken();

                tokenStat.nextToken();

                String system = tokenStat.nextToken();

                tokenStat.nextToken();
                String nice = tokenStat.nextToken();

                System.out.println(user + " , " + system + " , " + nice);

                user = user.substring(0, user.indexOf("%"));
                system = system.substring(0, system.indexOf("%"));
                nice = nice.substring(0, nice.indexOf("%"));

                float userUsage = new Float(user).floatValue();
                float systemUsage = new Float(system).floatValue();
                float niceUsage = new Float(nice).floatValue();
                return String.valueOf((userUsage + systemUsage + niceUsage) / 100);
            } else {
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();
                brStat.readLine();

                tokenStat = new StringTokenizer(brStat.readLine());

                tokenStat.nextToken();
                String userUsage = tokenStat.nextToken(); // 用户空间占用CPU百分比
                user = userUsage.substring(0, userUsage.indexOf("%"));
                process.destroy();

            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            freeResource(is, isr, brStat);
            return "100";
        } finally {
            freeResource(is, isr, brStat);
        }
        return user; // jvm cpu占用率
    }

    public String getMemoryRateForLinux() {
        Process pro = null;
        Runtime r = Runtime.getRuntime();
        String remCount = "";
        try {
            String command = "top -b  -n 1 -H -p" + pid;
            pro = r.exec(command);
            BufferedReader in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            in.readLine();
            in.readLine();

            in.readLine();
            in.readLine();
            in.readLine();
            in.readLine();
            in.readLine();
            StringTokenizer ts = new StringTokenizer(in.readLine());
            int i = 1;
            while (ts.hasMoreTokens()) {
                i++;
                ts.nextToken();
                if (i == 10) {
                    remCount = ts.nextToken();
                }
            }
            in.close();
            pro.destroy();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return remCount;
    }

    public String getMemoryRateForWindows() {
        String command = "TASKLIST /NH /FO CSV /FI \"PID EQ " + pid + " \"";
        String remCount = ""; // jvm物理内存占用量
        BufferedReader in = null;

        String result = "";

        try {
            Process pro = Runtime.getRuntime().exec(command);
            in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            StringTokenizer ts = new StringTokenizer(in.readLine(), "\"");
            int i = 1;
            while (ts.hasMoreTokens()) {
                i++;
                ts.nextToken();
                if (i == 9) {
                    remCount = ts.nextToken().replace(",", "").replace("K", "").trim();
                }

            }

            long physicalJvmMem = Long.parseLong(remCount) / 1024; // jvm物理内存占用量(MB)
            OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            long physicalTotal = osmxb.getTotalPhysicalMemorySize() / (1024 * 1024); // 获取服务器总物理内存(MB)
            result = formatNumber(physicalJvmMem / (float) physicalTotal);
            if (Float.parseFloat(result) > 1) { // 占用率最多只能是100%
                result = "1";
            } else if (Float.parseFloat(result) < 0) {
                result = "0";
            }
            in.close();
            pro.destroy();
        } catch (Exception e) {
            System.err.println("getThreadCountForWindows()报异常：" + e);
        }
        return String.valueOf((Float.parseFloat(result) * 100));

    }

    public String getLocale() {
        Process pro = null;
        Runtime r = Runtime.getRuntime();
        String command = "locale";
        BufferedReader in = null;
        StringTokenizer ts = null;
        try {
            pro = r.exec(command);
            in = new BufferedReader(new InputStreamReader(pro.getInputStream()));

            ts = new StringTokenizer(in.readLine());
            in.close();
            pro.destroy();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ts.nextToken();
    }

    public int getThreadCountForLinux() {
        Process pro = null;
        Runtime r = Runtime.getRuntime();
        String command = "top -b -n 1 -H -p " + pid;
        BufferedReader in = null;

        int result = 0;
        try {
            pro = r.exec(new String[]{"sh", "-c", command});

            in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            in.readLine();
            StringTokenizer ts = new StringTokenizer(in.readLine());
            ts.nextToken();
            result = Integer.parseInt(ts.nextToken());
            in.close();

            pro.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getThreadCountForWindows() {
        String command = "wmic process " + pid + "  list brief";
        int count = 0;
        BufferedReader in = null;
        try {
            Process pro = Runtime.getRuntime().exec(command);
            in = new BufferedReader(new InputStreamReader(pro.getInputStream()));

            in.readLine();
            in.readLine();

            StringTokenizer ts = new StringTokenizer(in.readLine());
            int i = 1;
            while (ts.hasMoreTokens()) {
                i++;
                ts.nextToken();
                if (i == 5) {
                    count = Integer.parseInt(ts.nextToken());
                }
            }
            in.close();
            pro.destroy();
        } catch (Exception e) {
            System.out.println("getThreadCountForWindows()报异常：" + e);
        }
        return count;

    }

    private void freeResource(InputStream is, InputStreamReader isr, BufferedReader br) {
        try {
            if (is != null) {
                is.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (br != null) {
                br.close();
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    private long[] readCpu(final Process proc) {
        long[] retn = new long[2];
        try {
            proc.getOutputStream().close();
            InputStreamReader ir = new InputStreamReader(proc.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line = input.readLine();
            System.out.println("src line=" + line);
            if (line == null || line.length() < FAULTLENGTH) {
                return null;
            }

            int capidx = line.indexOf("Caption");
            int cmdidx = line.indexOf("CommandLine");
            int rocidx = line.indexOf("ReadOperationCount");
            int umtidx = line.indexOf("UserModeTime");
            int kmtidx = line.indexOf("KernelModeTime");
            int wocidx = line.indexOf("WriteOperationCount");
// Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount
            long idletime = 0;
            long kneltime = 0;
            long usertime = 0;
            while ((line = input.readLine()) != null) {
                if (line.length() < wocidx) {
                    continue;
                }

// 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,

// ThreadCount,UserModeTime,WriteOperation
                String caption = this.substring(line, capidx, cmdidx - 1).trim();
                String cmd = this.substring(line, cmdidx, kmtidx - 1).trim();
                if (cmd.indexOf("javaw.exe") >= 0) {
                    continue;
                }


                if (caption.equals("System Idle Process") || caption.equals("System")) {
                    idletime += Long.valueOf(this.substring(line, kmtidx, rocidx - 1).trim()).longValue();
                    idletime += Long.valueOf(this.substring(line, umtidx, wocidx - 1).trim()).longValue();
                    continue;
                }
                String kl = this.substring(line, kmtidx, rocidx - 1).trim();
                if (kl == "" || kl.equalsIgnoreCase("")) {
                    kl = "0";
                }
                System.out.println("readCPU line:" + line);
                String ut = this.substring(line, umtidx, wocidx - 1).trim();
                if (ut == "" || ut.equalsIgnoreCase("")) {
                    ut = "0";
                }
                //System.out.println("readCPU:KL=" + kl + ":ut=" + ut);
                kneltime += Long.valueOf(kl).longValue();
                usertime += Long.valueOf(ut).longValue();
            }
            retn[0] = idletime;
            retn[1] = kneltime + usertime;
            return retn;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                proc.getInputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String[] readInLine(BufferedReader input, String osType) {
        String rxResult = "";
        String txResult = "";
        StringTokenizer tokenStat = null;
        try {

            if (osType.equals("linux")) { // 获取linux环境下的网口上下行速率
                String result[] = input.readLine().split(" ");
                int j = 0, k = 0;
                for (int i = 0; i < result.length; i++) {
                    if (result[i].indexOf("RX") != -1) {
                        j++;
                        if (j == 2) {
                            rxResult = result[i + 1].split(":")[1];
                        }
                    }
                    if (result[i].indexOf("TX") != -1) {
                        k++;
                        if (k == 2) {
                            txResult = result[i + 1].split(":")[1];
                            break;
                        }
                    }
                }


            } else { // 获取windows环境下的网口上下行速率

                input.readLine();

                input.readLine();

                input.readLine();

                input.readLine();

                tokenStat = new StringTokenizer(input.readLine());

                tokenStat.nextToken();

                rxResult = tokenStat.nextToken();

                txResult = tokenStat.nextToken();

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String arr[] = {rxResult, txResult};
        return arr;

    }

    /**
     * 由于String.subString对汉字处理存在问题（把一个汉字视为一个字节)，因此在 包含汉字的字符串时存在隐患，现调整如下：
     *
     * @param src       要截取的字符串
     * @param start_idx 开始坐标（包括该坐标)
     * @param end_idx   截止坐标（包括该坐标）
     * @return
     */
    private String substring(String src, int start_idx, int end_idx) {
        byte[] b = src.getBytes();
        String tgt = "";
        for (int i = start_idx; i <= end_idx; i++) {
            tgt += (char) b[i];
        }
        return tgt;
    }

    /**
     * 格式化浮点数(float 和 double)，保留两位小数
     *
     * @param obj
     * @return
     */

    private String formatNumber(Object obj) {
        String result = "";
        if (obj.getClass().getSimpleName().equals("Float")) {
            result = new Formatter().format("%.2f", Float.valueOf(obj.toString())).toString();
        } else if (obj.getClass().getSimpleName().equals("Double")) {
            result = new Formatter().format("%.2f", Double.valueOf(obj.toString())).toString();
        }
        return result;

    }

    public class SysInfoAcquirerTimerTask extends TimerTask {

        @Override
        public void run() {
            try {
                System.out.println("任务开始：");
                long startTime = System.currentTimeMillis();
                System.out.println("JVM  PID：" + pid);
                int threadCount = getThreadCount();
                System.out.println("JVM 线程数：" + threadCount);
                String cpuRate = getCPURate(); // CPU使用率
                System.out.println("CPU使用率：" + cpuRate + "%");
                String memoryRate = getMemoryRate(); // 内存占用率
                System.out.println("内存占用率：" + memoryRate + "%");
                JSONObject jsonObj = JSON.parseObject(getNetworkThroughput());
                String upSpeed = jsonObj.getString("txPercent");// 上行速度
                String downSpeed = jsonObj.getString("rxPercent"); // 下行速度


                System.out.println("上行速度：" + upSpeed + "MB/s 下行速度：" + downSpeed + "MB/s");

                //后续操作为将采集数据存放到数据库中，可自行设计
                SysPerformInfo sysPerformInfo = new SysPerformInfo();
                sysPerformInfo.setId(UUID.randomUUID().toString());
                sysPerformInfo.setCpuRate(Float.parseFloat(cpuRate));
                sysPerformInfo.setMemoryRate(Float.parseFloat(memoryRate));
                sysPerformInfo.setThreadCount(threadCount);
                sysPerformInfo.setUpSpeed(Float.parseFloat(upSpeed));
                sysPerformInfo.setDownSpeed(Float.parseFloat(downSpeed));
                // sysPerformInfoService.insertPerformInfo(sysPerformInfo); // 将采集到的数据插入数据库
                long endTime = System.currentTimeMillis();
                System.out.println("任务总耗时：" + (endTime - startTime) / (1000 * 60) + "分钟");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.toString());
            }
        }


    }


    public static void main(String[] args) {
        SysInfoAcquirerService service = new SysInfoAcquirerService();
        service.init();
    }


}
