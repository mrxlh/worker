package com.collmall.util;

import com.collmall.log.CustomLogger;
import com.collmall.model.AliveModule;
import com.collmall.model.CallerInfo;
import com.collmall.model.JvmModule;
import com.collmall.model.TPMoudle;

import java.util.Map;
import java.util.Timer;

public class ProfilerUtil {

    private static final TPMoudle tpCounter = TPMoudle.getInstance();

    public ProfilerUtil() {
    }

    public static CallerInfo scopeStart(String appName, String key, boolean enableHeart, boolean enableTP) {
        CallerInfo callerInfo = null;

        try {
            if (enableTP) {
                callerInfo = new CallerInfo(key, appName);
            }

            if (enableHeart) {
                if (!CacheUtil.FUNC_HB.containsKey(key)) {
                    Map var5 = CacheUtil.FUNC_HB;
                    synchronized(CacheUtil.FUNC_HB) {
                        if (!CacheUtil.FUNC_HB.containsKey(key)) {
                            CacheUtil.FUNC_HB.put(key, System.currentTimeMillis());
                            CustomLogger.AliveLogger.info("{\"a\":\"" + appName + "\",\"k\":\"" + key + "\",\"h\":\"" + CacheUtil.HOST_NAME + "\",\"t\":\"" + CacheUtil.getNowTime() + "\"}");
                        }
                    }
                } else {
                    Long hbPoint = (Long)CacheUtil.FUNC_HB.get(key);
                    if (System.currentTimeMillis() - hbPoint >= 20000L) {
                        Map var6 = CacheUtil.FUNC_HB;
                        synchronized(CacheUtil.FUNC_HB) {
                            if (System.currentTimeMillis() - (Long)CacheUtil.FUNC_HB.get(key) >= 20000L) {
                                CacheUtil.FUNC_HB.put(key, System.currentTimeMillis());
                                CustomLogger.AliveLogger.info("{\"a\":\"" + appName + "\",\"k\":\"" + key + "\",\"h\":\"" + CacheUtil.HOST_NAME + "\",\"t\":\"" + CacheUtil.getNowTime() + "\"}");
                            }
                        }
                    }
                }
            }
        } catch (Throwable var10) {
            ;
        }

        return callerInfo;
    }

    public static void scopeEnd(CallerInfo callerInfo) {
        try {
            if (callerInfo != null) {
                if (callerInfo.getProcessState() == 1) {
                    callerInfo.stop();
                } else {
                    long elapsedTime = callerInfo.getElapsedTime();
                    if (elapsedTime >= 400L) {
                        callerInfo.stop();
                    } else {
                        tpCounter.count(callerInfo, elapsedTime);
                    }
                }
            }
        } catch (Throwable var3) {
            ;
        }

    }

    public static void scopeFunctionError(CallerInfo callerInfo, Throwable t) {
        try {
            if (callerInfo != null) {
                callerInfo.error(t != null ? t.getMessage() : null);
            }
        } catch (Throwable var3) {
            ;
        }

    }

    public static synchronized void scopeAlive(String appName, String key) {
        try {
            if (!CacheUtil.SYSTEM_HEART_INIT) {
                Timer timer = new Timer("UMP-AliveThread", true);
                timer.scheduleAtFixedRate(new AliveModule(appName, key), 1000L, 20000L);
                CacheUtil.SYSTEM_HEART_INIT = true;
            }
        } catch (Throwable var3) {
            ;
        }

    }

    public static synchronized void scopeJvm(String appName, String key) {
        try {
            if (!CacheUtil.JVM_MONITOR_INIT) {
                Timer timer = new Timer("UMP-CollectJvmInfoThread", true);
                timer.scheduleAtFixedRate(new JvmModule(appName, key), 1000L, 10000L);
                CacheUtil.JVM_MONITOR_INIT = true;
            }
        } catch (Throwable var3) {
            ;
        }

    }
}
