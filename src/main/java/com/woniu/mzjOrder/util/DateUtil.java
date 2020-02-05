package com.woniu.mzjOrder.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {

    static Calendar cal = Calendar.getInstance();

    static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static DateFormat dateformat_nor = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
    static DateFormat dateformat_nor_nonitor = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
    static DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    static DateFormat format2 = new SimpleDateFormat("yyyy.MM.dd");
    static DateFormat format3 = new SimpleDateFormat("yyyy年MM月dd日");
    static DateFormat format4 = new SimpleDateFormat("yyyy/MM/dd");
    static DateFormat format5 = new SimpleDateFormat("[yyyy-MM-dd]");
    static DateFormat format6 = new SimpleDateFormat("MM-dd");


    /**
     * long时间类型转化为String
     *
     * @param ts
     * @return
     */
    public static String longToString(long ts) {
        return dateformat.format(new Timestamp(ts));
    }

    public static String longToStringNormatization(long ts) {
        return dateformat_nor.format(new Timestamp(ts));
    }

    public static String longToStringNormatizationMonitor(long ts) {
        return dateformat_nor_nonitor.format(new Timestamp(ts));
    }

    public static String timestampToString(Timestamp time) {
        if (time == null) {
            return null;
        }
        return sdf.format(time);
    }

    public static Date longToDate(long ts) {
        Date time = null;
        try {
            time = (Date) dateformat.parse(longToString(ts));
        } catch (Exception e) {
            e.getStackTrace();
        }
        return time;
    }

    public static long StringToLong(String ts) throws Exception {
        Date time = (Date) dateformat.parse(ts);
        return time.getTime();
    }

    public static long timestampToLong(Timestamp timestamp) throws Exception {
        return DateUtil.StringToLong(DateUtil.timestampToString(timestamp));
    }

    public static String DateToString(Date ts) {
        if (ts == null) {
            return "";
        }
        return format1.format(ts);
    }

    public static Timestamp StringToTimestamp(String timeStr) {
        return Timestamp.valueOf(timeStr);
    }

    public static Date StringToDate(String timeStr) {
        Date time = null;
        try {
            if (timeStr.matches("[0-9]{4}-[0-1]{0,1}[0-9]-[0-3]{0,1}[0-9]")) {
                time = format1.parse(timeStr);
            } else if (timeStr.matches("[0-9]{4}\\.[0-1]{0,1}[0-9]\\.[0-3]{0,1}[0-9]")) {
                time = format2.parse(timeStr);
            } else if (timeStr.matches("[0-9]{4}年[0-1]{0,1}[0-9]月[0-3]{0,1}[0-9]日")) {
                time = format3.parse(timeStr);
            } else if (timeStr.matches("[0-9]{4}/[0-1]{0,1}[0-9]/[0-3]{0,1}[0-9]")) {
                time = format4.parse(timeStr);
            } else if (timeStr.matches("\\[[0-9]{4}-[0-1]{0,1}[0-9]-[0-3]{0,1}[0-9]\\]")) {
                time = format5.parse(timeStr);
            } else if (timeStr.matches("[0-1]{0,1}[0-9]-[0-3]{0,1}[0-9]{2}")) {
                //String year = new SimpleDateFormat("yyyy").format(new Date());
                String currentTimeStr = new SimpleDateFormat("MM-dd").format(new Date());
                int year = cal.get(Calendar.YEAR);
                if (timeStr.compareTo(currentTimeStr) > 0){
                    year = year - 1;
                }

                timeStr = year + "-" + timeStr;
                time = format1.parse(timeStr);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return time;
    }


}
