package com.quancheng.achilles.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    // public static void main(String[] args) {
    // Date firstDayOfWeek = TimeUtil.getFirstDayOfWeek(new Date());
    // System.out.println(TimeUtil.formatDate("yyyy-MM-dd", firstDayOfWeek));
    // Date lastDayOfWeek = TimeUtil.getLastDayOfWeek(new Date());
    // System.out.println(TimeUtil.formatDate("yyyy-MM-dd", lastDayOfWeek));
    // System.out.println(TimeUtil.formatDate("yyyy-MM-dd", TimeUtil.getFirstDayOfMonth(new Date())));
    // System.out.println(TimeUtil.formatDate("yyyy-MM-dd", TimeUtil.getLastDayOfMonth(new Date())));
    // System.out.println(TimeUtil.formatDate("yyyy-MM-dd HH:mm:ss", TimeUtil.getFirstTimeOfDay(new Date())));
    // System.out.println(TimeUtil.formatDate("yyyy-MM-dd HH:mm:ss", TimeUtil.getLastTimeOfDay(new Date())));
    //
    // Date startTime = TimeUtil.addDay(new Date(), -1);
    // Date endTime = startTime;
    // System.err.println(TimeUtil.formatDate("yyyy-MM-dd", startTime));
    // System.err.println(TimeUtil.formatDate("yyyy-MM-dd", endTime));
    // System.err.println(TimeUtil.formatDate("yyyy-ww", new Date()));
    // }

    /** 日期格式化为字符串 */
    public static String formatDate(String pattern, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /** 日期字符串格式化为Date */
    public static Date parseDate(String pattern, String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(date);
    }

    /** 获取一周的第一天（星期一） */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.DAY_OF_MONTH, -1);
        instance.set(Calendar.DAY_OF_WEEK, 1);
        instance.add(Calendar.DAY_OF_MONTH, +1);
        return instance.getTime();
    }

    /** 获取一周的最后一天（星期日） */
    public static Date getLastDayOfWeek(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.DAY_OF_MONTH, -1);
        instance.set(Calendar.DAY_OF_WEEK, 7);
        instance.add(Calendar.DAY_OF_MONTH, +1);
        return instance.getTime();
    }

    /** 获取一月的第一天（1号） */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(Calendar.DAY_OF_MONTH, 1);
        return instance.getTime();
    }

    /** 获取一月的最后一天 */
    public static Date getLastDayOfMonth(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MONTH, +1);
        instance.set(Calendar.DAY_OF_MONTH, 1);
        instance.add(Calendar.DAY_OF_MONTH, -1);
        return instance.getTime();
    }

    /** 获取一天的开始（0时0分0秒） */
    public static Date getFirstTimeOfDay(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(Calendar.HOUR_OF_DAY, 0);
        instance.set(Calendar.MINUTE, 0);
        instance.set(Calendar.SECOND, 0);
        return instance.getTime();
    }

    /** 获取一天的结束（23时59分50秒） */
    public static Date getLastTimeOfDay(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(Calendar.HOUR_OF_DAY, 23);
        instance.set(Calendar.MINUTE, 59);
        instance.set(Calendar.SECOND, 59);
        return instance.getTime();
    }

    /** 添加年 */
    public static Date addYear(Date date, int number) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.YEAR, number);
        return instance.getTime();
    }

    /** 添加月 */
    public static Date addMonth(Date date, int number) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MONTH, number);
        return instance.getTime();
    }

    /** 添加周 */
    public static Date addWeekOfYear(Date date, int number) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.WEEK_OF_YEAR, number);
        return instance.getTime();
    }

    /** 添加天 */
    public static Date addDay(Date date, int number) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.DAY_OF_MONTH, number);
        return instance.getTime();
    }

    /** 添加小时 */
    public static Date addHour(Date date, int number) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.HOUR, number);
        return instance.getTime();
    }

    /** 添加分 */
    public static Date addMinute(Date date, int number) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MINUTE, number);
        return instance.getTime();
    }

    /** 添加秒 */
    public static Date addSecond(Date date, int number) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.SECOND, number);
        return instance.getTime();
    }
}
