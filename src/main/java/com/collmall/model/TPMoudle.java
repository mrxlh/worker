
package com.collmall.model;

import com.collmall.log.CustomLogger;
import com.collmall.log.LogFormatter;
import com.collmall.util.CacheUtil;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class TPMoudle {

    public static final long MAX_TP_COUNT_ELAPSED_TIME = 400L;
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final TPMoudle instance = new TPMoudle();
    private ConcurrentHashMap<String, ConcurrentHashMultiSet<Integer>> tpCountMap = new ConcurrentHashMap();
    private static final String TP_LOG_TEMPLATE;

    private TPMoudle() {
        long lastTimePoint = (new Date()).getTime() / 5000L * 5000L;
        Date firstWriteTime = new Date(lastTimePoint + 5000L);
        Timer writeTPLogTimer = new Timer("UMP-WriteTPLogThread", true);
        writeTPLogTimer.scheduleAtFixedRate(new TPMoudle.WriteTPLogTask(), firstWriteTime, 5000L);
    }

    public static TPMoudle getInstance() {
        return instance;
    }

    public void count(CallerInfo callerInfo, long elapsedTime) {
        String countMapKey = this.getCountMapKey(callerInfo);
        ConcurrentHashMultiSet<Integer> elapsedTimeCounter = (ConcurrentHashMultiSet)this.tpCountMap.get(countMapKey);
        if (elapsedTimeCounter == null) {
            ConcurrentHashMultiSet<Integer> newElapsedTimeCounter = new ConcurrentHashMultiSet();
            elapsedTimeCounter = (ConcurrentHashMultiSet)this.tpCountMap.putIfAbsent(countMapKey, newElapsedTimeCounter);
            if (elapsedTimeCounter == null) {
                elapsedTimeCounter = newElapsedTimeCounter;
            }
        }

        elapsedTimeCounter.add((int)elapsedTime);
    }

    private String getCountMapKey(CallerInfo callerInfo) {
        return null != callerInfo.getAppName() && !"".equals(callerInfo.getAppName()) ? callerInfo.getKey() + "###" + callerInfo.getAppName() + "###" + System.currentTimeMillis() / 5000L * 5000L : callerInfo.getKey() + "###" + System.currentTimeMillis() / 5000L * 5000L;
    }

    static {
        TP_LOG_TEMPLATE = "{\"t\":\"{}\",\"k\":\"{}\",\"a\":\"{}\",\"h\":\"" + CacheUtil.HOST_NAME + "\",\"s\":" + "\"" + 0 + "\",\"e\":\"{}\",\"c\":\"{}\"}";
    }

    private class WriteTPLogTask extends TimerTask {
        private WriteTPLogTask() {
        }

        public void run() {
            try {
                ConcurrentHashMap<String, ConcurrentHashMultiSet<Integer>> writeCountMap = TPMoudle.this.tpCountMap;
                this.writeTPLog(writeCountMap);
            } catch (Throwable var2) {
                ;
            }

        }

        private void writeTPLog(Map<String, ConcurrentHashMultiSet<Integer>> writeCountMap) throws InterruptedException {
            if (writeCountMap != null) {
                Thread.sleep(1000L);
                StringBuilder logs = new StringBuilder(1024);
                Iterator var3 = writeCountMap.entrySet().iterator();

                while(true) {
                    while(var3.hasNext()) {
                        Entry<String, ConcurrentHashMultiSet<Integer>> entry = (Entry)var3.next();
                        String[] keyTime = ((String)entry.getKey()).split("###");
                        String key;
                        String appName;
                        Integer elapsedTime;
                        if (keyTime != null && keyTime.length == 2) {
                            key = keyTime[0].trim();
                            appName = CacheUtil.changeLongToDate(Long.valueOf(keyTime[1].trim()));
                            ConcurrentHashMultiSet<Integer> elapsedTimeCounter = (ConcurrentHashMultiSet)entry.getValue();
                            boolean needSetLineSep = false;
                            Iterator var17 = elapsedTimeCounter.elementSet().iterator();

                            while(var17.hasNext()) {
                                Integer elapsedTimex = (Integer)var17.next();
                                if (needSetLineSep) {
                                    logs.append(TPMoudle.LINE_SEP);
                                } else {
                                    needSetLineSep = true;
                                }

                                elapsedTime = elapsedTimeCounter.count(elapsedTimex);
                                String logx = LogFormatter.format(TPMoudle.TP_LOG_TEMPLATE, new Object[]{appName, key, elapsedTimex, elapsedTime});
                                logs.append(logx);
                            }

                            int length = logs.length();
                            if (length > 0) {
                                CustomLogger.TpLogger.info(logs.toString());
                                logs.setLength(0);
                            }
                        } else if (keyTime != null && keyTime.length == 3) {
                            key = keyTime[0].trim();
                            appName = keyTime[1].trim();
                            String time = CacheUtil.changeLongToDate(Long.valueOf(keyTime[2].trim()));
                            ConcurrentHashMultiSet<Integer> elapsedTimeCounterx = (ConcurrentHashMultiSet)entry.getValue();
                            boolean needSetLineSepx = false;
                            Iterator var11 = elapsedTimeCounterx.elementSet().iterator();

                            while(var11.hasNext()) {
                                elapsedTime = (Integer)var11.next();
                                if (needSetLineSepx) {
                                    logs.append(TPMoudle.LINE_SEP);
                                } else {
                                    needSetLineSepx = true;
                                }

                                Integer count = elapsedTimeCounterx.count(elapsedTime);
                                String log = LogFormatter.format(TPMoudle.TP_LOG_TEMPLATE, new Object[]{time, key, appName, elapsedTime, count});
                                logs.append(log);
                            }

                            int lengthx = logs.length();
                            if (lengthx > 0) {
                                CustomLogger.TpLogger.info(logs.toString());
                                logs.setLength(0);
                            }
                        }
                    }

                    return;
                }
            }
        }
    }
}
