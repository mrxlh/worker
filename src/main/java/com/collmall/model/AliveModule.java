package com.collmall.model;

import com.collmall.log.CustomLogger;
import com.collmall.util.CacheUtil;

import java.util.TimerTask;

public class AliveModule extends TimerTask {

    private String appName;
    private String key;

    public AliveModule(String appName, String key) {
        this.appName = appName;
        this.key = key;
    }

    @Override
    public void run() {
        try {
            CustomLogger.AliveLogger.info("{\"k\":\"" + this.key + "\"" + ",\"a\":\"" + this.appName + "\",\"h\":\"" + CacheUtil.HOST_NAME + "\"" + ",\"t\":" + "\"" + CacheUtil.getNowTime() + "\"}");
        } catch (Exception var2) {
            ;
        }

    }
}
