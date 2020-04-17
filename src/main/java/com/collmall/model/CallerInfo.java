package com.collmall.model;

import com.collmall.log.CustomLogger;
import com.collmall.log.LogFormatter;
import com.collmall.util.CacheUtil;

public class CallerInfo {
    public static final int STATE_TRUE = 0;
    public static final int STATE_FALSE = 1;
    public static final String TP_LOG_TEMPLATE;
    private long startTime;
    private String key;
    private String appName;
    private int processState;
    private long elapsedTime;
    private String remark;

    public CallerInfo(String key, String appName) {
        this.key = key;
        this.appName = appName;
        this.startTime = System.currentTimeMillis();
        this.elapsedTime = -1L;
        this.processState = 0;
    }

    public int getProcessState() {
        return this.processState;
    }

    public void error(String error) {
        this.processState = 1;
        if (error != null && error.length() > 255) {
            error = error.substring(0, 255);
        }

        this.remark = error;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public String getKey() {
        return this.key;
    }

    public String getAppName() {
        return this.appName;
    }

    public long getElapsedTime() {
        if (this.elapsedTime == -1L) {
            this.elapsedTime = System.currentTimeMillis() - this.startTime;
        }

        return this.elapsedTime;
    }

    public void stop() {
        CustomLogger.TpLogger.info(this.toString());
    }

    public String toString() {
        return LogFormatter.format(TP_LOG_TEMPLATE, new Object[]{CacheUtil.getNowTime(), this.key, this.appName, this.processState, this.getElapsedTime(), this.remark});
    }

    static {
        TP_LOG_TEMPLATE = "{\"t\":\"{}\",\"k\":\"{}\",\"a\":\"{}\",\"h\":\"" + CacheUtil.HOST_NAME + "\",\"s\":\"{}\",\"e\":\"{}\",\"r\":\"{}\"}";
    }
}
