package com.collmall.model;

import com.collmall.log.CustomLogger;
import com.collmall.util.CacheUtil;


import java.util.TimerTask;

public class JvmModule extends TimerTask {
    private String appName;
    private String key;
    private static LocalJvmInfoPicker localJvm = LocalJvmInfoPicker.getInstance();
    private static String instanceCode;
    private static String logType;

    public JvmModule(String appName, String key) {
        this.appName = appName;
        this.key = key;
    }

    @Override
    public void run() {
        try {
            CustomLogger.JVMLogger.info("{\"logtype\":\"" + logType + "\"" + ",\"k\":" + "\"" + this.key + "\"" + ",\"a\":\"" + this.appName + "\",\"h\":" + "\"" + CacheUtil.HOST_NAME + "\"" + ",\"t\":" + "\"" + CacheUtil.getNowTime() + "\"" + ",\"datatype\":" + "\"" + "2" + "\"" + ",\"instancecode\":" + "\"" + instanceCode + "\"" + ",\"userdata\":" + localJvm.pickJvmRumtimeInfo() + "}");
        } catch (Throwable var2) {
            ;
        }

    }

    static {
        instanceCode = localJvm.getJvmInstanceCode();
        logType = "JVM";
    }
}
