package com.sinocall.phonerecordera.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间戳换算
 */
public class TimeUtils {
    /**
     * 通过时间戳来返回合适的日期格式
     *
     * @param timeTemp 时间戳
     * @param format   日期格式
     * @return
     */
    public static String getFormatDate(long timeTemp, String format) {

        String date = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            date = simpleDateFormat.format(new Date(
                    timeTemp));
            try {
                if (IsToday(date)) {
                    date = "今天" + date.substring(10);
                } else if (IsYesterday(date)) {
                    date = "昨天" + date.substring(10);
                } else if (IsBeforeYesterday(date)) {
                    date = "前天" + date.substring(10);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * @param timeTemp
     * @param format
     * @return
     */
    public static Date getFormatDate(String timeTemp, String format) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(timeTemp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static boolean IsToday(String day) throws ParseException {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);
            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean IsYesterday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == -1) {
                return true;
            }
        }
        return false;
    }

    public static boolean IsBeforeYesterday(String day) throws ParseException {

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == -2) {
                return true;
            }
        }
        return false;
    }

    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA));
        }
        return DateLocal.get();
    }

    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();

    public static Date getFormatData(long time) {
        Date date = null;
        try {
            date = new Date(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getFormatDateString(String str, String format) {
        Date formatDate = getFormatDate(str, format);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String date = simpleDateFormat.format(formatDate);
        return date;
    }

    public static String getFormatDateString(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String strDate = simpleDateFormat.format(date);
        return strDate;
    }

    public static long getLongTime(String date, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date parse = simpleDateFormat.parse(date);
        return parse.getTime();
    }


    /**
     * 得到今天的日期
     *
     * @param format
     * @return
     */
    public static String getToday(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);//设置日期格式
        return df.format(new Date());
    }


    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }


    public static boolean isBefore(String small, String big) {
        Date smallDate = getFormatDate(small, "yyyy-MM-dd");
        Date bigDate = getFormatDate(big, "yyyy-MM-dd");
        if (smallDate.before(bigDate)) {
            return true;
        } else {
            return false;
        }
    }

    public static String secondToTime(String longs) {
        long time = Long.parseLong(longs);
        if (time < 60) {
            return longs + "秒";
        }
        long minute = time / 60;
        long second = time % 60;
        if (minute < 60) {
            return minute + "分" + second + "秒";
        } else {
            long hour = minute / 60;
            minute = minute % 60;
            return hour + "小时" + minute + "分" + second + "秒";
        }
    }
}
