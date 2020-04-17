package com.collmall.log;

import com.collmall.util.CacheUtil;
import org.apache.log4j.*;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.RootLogger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class CustomLogFactory {

    private static LoggerRepository h;
    private static final String DEFAULTPATH;
    private static String MaxFileSize;
    private static int MaxBackupIndex;
    private static String logPath;
    private static String TIMESTAMP;
    private static int PID;
    private static int RANDOM_CODE;
    private static long CHECK_FILE_REMOVED_PERIOD;
    private static int tpLoggerFileRemovedCount;
    private static int aliveLoggerFileRemovedCount;
    private static int businessLoggerFileRemovedCount;
    private static int bizLoggerFileRemovedCount;
    private static int jvmLoggerFileRemovedCount;
    private static int commonLoggerFileRemovedCount;

    public CustomLogFactory() {
    }

    private static String getDefaultLogPath() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("windows") ? "D:\\export\\app\\ump-monitor" : "../logs/ump-monitor";
    }

    private static int getPid() {
        try {
            RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
            String name = runtime.getName();
            return Integer.parseInt(name.substring(0, name.indexOf(64)));
        } catch (Throwable var2) {
            return (new Random()).nextInt(50000) + 9900000;
        }
    }

    private static int getRandomCode() {
        return (new Random()).nextInt(1000000);
    }

    public static Logger getLogger(String loggerName) {
        return h.getLogger(loggerName);
    }

    private static Properties InitLog4jProperties(String path) {
        Properties properties = new Properties();
        String tpLogFile = createFileName("tp.log");
        String aliveLogFile = createFileName("alive.log");
        String businessLogFile = createFileName("business.log");
        String bizLogFile = createFileName("biz.log");
        String jvmLogFile = createFileName("jvm.log");
        String commonLogFile = createFileName("common.log");
        setProperties(properties, "tpLogger", "A1", tpLogFile);
        setProperties(properties, "aliveLogger", "A2", aliveLogFile);
        setProperties(properties, "businessLogger", "A3", businessLogFile);
        setProperties(properties, "bizLogger", "A4", bizLogFile);
        setProperties(properties, "jvmLogger", "A5", jvmLogFile);
        setProperties(properties, "commonLogger", "A6", commonLogFile);
        return properties;
    }

    private static void setProperties(Properties properties, String loggerName, String appenderName, String fileName) {
        properties.setProperty(String.format("log4j.logger.%s", loggerName), String.format("INFO,%s", appenderName));
        properties.setProperty(String.format("log4j.appender.%s", appenderName), RollingFileAppender.class.getName());
        properties.setProperty(String.format("log4j.appender.%s.File", appenderName), fileName);
        properties.setProperty(String.format("log4j.appender.%s.MaxFileSize", appenderName), MaxFileSize);
        properties.setProperty(String.format("log4j.appender.%s.MaxBackupIndex", appenderName), String.valueOf(MaxBackupIndex));
        properties.setProperty(String.format("log4j.appender.%s.layout", appenderName), SimpleLayout.class.getName());
        properties.setProperty(String.format("log4j.appender.%s.encoding", appenderName), "UTF-8");
    }

    private static String createFileName(String name) {
        return logPath + TIMESTAMP + "_" + PID + "_" + RANDOM_CODE + "_" + name;
    }

    static {
        h = new Hierarchy(new RootLogger(Level.INFO));
        DEFAULTPATH = getDefaultLogPath();
        MaxFileSize = "50MB";
        MaxBackupIndex = 3;
        logPath = null;
        TIMESTAMP = CacheUtil.getNowTime();
        PID = getPid();
        RANDOM_CODE = getRandomCode();
        CHECK_FILE_REMOVED_PERIOD = 2000L;
        Properties conf = new Properties();
        Properties props = null;
        InputStream is = null;

        try {
            is = CacheUtil.class.getResourceAsStream("/application.properties");
            if (is != null) {
                conf.load(is);
                logPath = conf.getProperty("ump.logPath", DEFAULTPATH);
                if (logPath.equals("")) {
                    logPath = DEFAULTPATH;
                }
            } else {
                logPath = DEFAULTPATH;
            }

            logPath = logPath + File.separator + "logs" + File.separator;
            File ump_root_path = new File(logPath);
            if (ump_root_path.exists() && ump_root_path.isDirectory()) {
                props = InitLog4jProperties(logPath);
            } else {
                ump_root_path.mkdir();
                props = InitLog4jProperties(logPath);
            }

            (new PropertyConfigurator()).doConfigure(props, h);
            Timer checkFileRemovedTimer = new Timer("UMP-CheckFileRemovedThread", true);
            checkFileRemovedTimer.schedule(new CustomLogFactory.CheckFileRemovedTask(), CHECK_FILE_REMOVED_PERIOD, CHECK_FILE_REMOVED_PERIOD);
        } catch (Throwable var13) {
            ;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Throwable var12) {
                    ;
                }
            }

        }

        tpLoggerFileRemovedCount = 0;
        aliveLoggerFileRemovedCount = 0;
        businessLoggerFileRemovedCount = 0;
        bizLoggerFileRemovedCount = 0;
        jvmLoggerFileRemovedCount = 0;
        commonLoggerFileRemovedCount = 0;
    }

    static class CheckFileRemovedTask extends TimerTask {
        CheckFileRemovedTask() {
        }

        public void run() {
            try {
                this.checkFileRemoved(CustomLogger.TpLogger.getLogger(), "tp.log", "A1");
                this.checkFileRemoved(CustomLogger.AliveLogger.getLogger(), "alive.log", "A2");
                this.checkFileRemoved(CustomLogger.BusinessLogger.getLogger(), "business.log", "A3");
                this.checkFileRemoved(CustomLogger.BizLogger.getLogger(), "biz.log", "A4");
                this.checkFileRemoved(CustomLogger.JVMLogger.getLogger(), "jvm.log", "A5");
                this.checkFileRemoved(CustomLogger.CommonLogger.getLogger(), "common.log", "A6");
            } catch (Throwable var2) {
                ;
            }

        }

        private void checkFileRemoved(Logger logger, String logName, String appenderName) throws IOException {
            RollingFileAppender appender = (RollingFileAppender)logger.getAppender(appenderName);
            if (appender != null) {
                String oldFile = appender.getFile();
                File file = new File(oldFile);
                if (file.exists()) {
                    this.setCount2Zero(logName);
                } else {
                    this.incrCount(logName);
                }

                if (this.getFileRemovedCount(logName) >= 2) {
                    String newFile = CustomLogFactory.createFileName(logName);
                    SimpleLayout layout = new SimpleLayout();
                    RollingFileAppender newAppender = new RollingFileAppender(layout, newFile);
                    newAppender.setName(appenderName);
                    newAppender.setMaxFileSize(CustomLogFactory.MaxFileSize);
                    newAppender.setMaxBackupIndex(CustomLogFactory.MaxBackupIndex);
                    newAppender.setEncoding("UTF-8");
                    logger.removeAppender(appenderName);
                    logger.addAppender(newAppender);
                    this.setCount2Zero(logName);
                }
            }

        }

        private int getFileRemovedCount(String logName) {
            if ("tp.log".equals(logName)) {
                return CustomLogFactory.tpLoggerFileRemovedCount;
            } else if ("alive.log".equals(logName)) {
                return CustomLogFactory.aliveLoggerFileRemovedCount;
            } else if ("business.log".equals(logName)) {
                return CustomLogFactory.businessLoggerFileRemovedCount;
            } else if ("biz.log".equals(logName)) {
                return CustomLogFactory.bizLoggerFileRemovedCount;
            } else if ("jvm.log".equals(logName)) {
                return CustomLogFactory.jvmLoggerFileRemovedCount;
            } else {
                return "common.log".equals(logName) ? CustomLogFactory.commonLoggerFileRemovedCount : 0;
            }
        }

        private void setCount2Zero(String logName) {
            if ("tp.log".equals(logName)) {
                CustomLogFactory.tpLoggerFileRemovedCount = 0;
            }

            if ("alive.log".equals(logName)) {
                CustomLogFactory.aliveLoggerFileRemovedCount = 0;
            }

            if ("business.log".equals(logName)) {
                CustomLogFactory.businessLoggerFileRemovedCount = 0;
            }

            if ("biz.log".equals(logName)) {
                CustomLogFactory.bizLoggerFileRemovedCount = 0;
            }

            if ("jvm.log".equals(logName)) {
                CustomLogFactory.jvmLoggerFileRemovedCount = 0;
            }

            if ("common.log".equals(logName)) {
                CustomLogFactory.commonLoggerFileRemovedCount = 0;
            }

        }

        private void incrCount(String logName) {
            if ("tp.log".equals(logName)) {
                CustomLogFactory.tpLoggerFileRemovedCount++;
            }

            if ("alive.log".equals(logName)) {
                CustomLogFactory.aliveLoggerFileRemovedCount++;
            }

            if ("business.log".equals(logName)) {
                CustomLogFactory.businessLoggerFileRemovedCount++;
            }

            if ("biz.log".equals(logName)) {
                CustomLogFactory.bizLoggerFileRemovedCount++;
            }

            if ("jvm.log".equals(logName)) {
                CustomLogFactory.jvmLoggerFileRemovedCount++;
            }

            if ("common.log".equals(logName)) {
                CustomLogFactory.commonLoggerFileRemovedCount++;
            }

        }
    }
}
