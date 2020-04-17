//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.collmall.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class CacheUtil {
    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");
    public static final long UPDATETIME = 43200000L;
    public static final int ALIVETIME = 20000;
    public static final int JVMTIME_R = 10000;
    public static final int JVMTIME_E = 14400000;
    public static Boolean SYSTEM_HEART_INIT = false;
    public static Boolean JVM_MONITOR_INIT = false;
    public static final String QUOTATION = "\"";
    public static final String COLON = ":";
    public static final String COMMA = ",";
    public static final String EXTENSIVE = "1";
    public static final String NONEXTENSIVE = "0";
    public static final Map<String, Long> FUNC_HB = new HashMap();
    public static final String HOST_NAME = getHostName();
    public static final String HOST_IP = getHostIP();

    public CacheUtil() {
    }

    private static String getHostName() {
        String host = "** HOST ERROR DETECTED **";

        try {
            try {
                InetAddress localAddress = InetAddress.getLocalHost();
                host = localAddress.getHostName();
            } catch (Throwable var3) {
                InetAddress localAddress = getLocalAddress();
                if (localAddress != null) {
                    host = localAddress.getHostName();
                } else {
                    host = "** HOST ERROR DETECTED **";
                }
            }
        } catch (Throwable var4) {
            ;
        }

        return host;
    }

    public static String getHostIP() {
        String ip = "** IP ERROR DETECTED **";

        try {
            if (getLocalAddress() != null) {
                ip = getLocalAddress().getHostAddress();
            } else {
                ip = "** IP ERROR DETECTED **";
            }
        } catch (Throwable var2) {
            ;
        }

        return ip;
    }

    public static String getNowTime() {
        return changeLongToDate(0L);
    }

    public static String changeLongToDate(long time) {
        String nowTime;
        try {
            Date date = time == 0L ? new Date() : new Date(time);
            TimeZone localTimeZone = TimeZone.getTimeZone("GMT+8");
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            df.setTimeZone(localTimeZone);
            nowTime = df.format(date);
        } catch (Exception var6) {
            nowTime = "";
        }

        return nowTime;
    }

    public static String getLocalIP() {
        InetAddress address = getLocalAddress();
        return address == null ? null : address.getHostAddress();
    }

    private static InetAddress getLocalAddress() {
        InetAddress localAddress = null;

        try {
            localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable var6) {
            ;
        }

        try {
            Enumeration<?> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while(interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = (NetworkInterface)interfaces.nextElement();
                        Enumeration addresses = network.getInetAddresses();

                        while(addresses.hasMoreElements()) {
                            try {
                                InetAddress address = (InetAddress)addresses.nextElement();
                                if (isValidAddress(address)) {
                                    return address;
                                }
                            } catch (Throwable var5) {
                                ;
                            }
                        }
                    } catch (Throwable var7) {
                        ;
                    }
                }
            }
        } catch (Throwable var8) {
            ;
        }

        return localAddress;
    }

    private static boolean isValidAddress(InetAddress address) {
        if (address != null && !address.isLoopbackAddress()) {
            String ip = address.getHostAddress();
            return ip != null && !"0.0.0.0".equals(ip) && !"127.0.0.1".equals(ip) && IP_PATTERN.matcher(ip).matches();
        } else {
            return false;
        }
    }

    public static Throwable getTargetException(Throwable e) {
        while(e.getCause() != null) {
            e = e.getCause();
        }

        return e;
    }
}
