package com.quancheng.achilles.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DateUtils {

    private static final Logger          LOGGER         = LoggerFactory.getLogger(DateUtils.class);

    /**
     * yyyy-MM
     */
    public static final SimpleDateFormat SDF_YEAR_MONTH = new SimpleDateFormat("yyyy-MM");
    /**
     * yyyy-ww
     */
    public static final SimpleDateFormat SDF_YEAR_WEEK  = new SimpleDateFormat("yyyy-ww");

    /**
     * yyyy-MM-dd
     */
    public static final SimpleDateFormat SDF_DATE       = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final SimpleDateFormat SDF_DATETIME   = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取X轴坐标集合 取结束时间前一个时间点
     *
     * @param begin 开始时间
     * @param end 结束时间
     * @param df 日期格式化
     * @param dateInterval x轴粒度
     * @return
     * @throws BaseException
     */
    public static List<Object> getDaily(Date begin, Date end, DateFormat df, int dateInterval) {
        Calendar cbegin = Calendar.getInstance();
        cbegin.setTime(begin);
        List<Object> list = new ArrayList<Object>();
        String currD = null;
        String endD = df.format(end);
        while (true) {
            currD = df.format(cbegin.getTime());
            // 如果当前坐标X值等于终点时跳出循环
            if (currD.equals(endD) || cbegin.after(end)) {
                break;
            }
            list.add(currD);
            // 增加一个粒度
            cbegin.add(dateInterval, 1);
        }
        // hadoop 周和calendar周的算法不一致 导致最后一周的数据为空 强制排除
        if (list.size() != 0 && Calendar.WEEK_OF_YEAR == dateInterval) {
            list.remove(list.size() - 1);
        }
        return list;
    }

    public static int getSizeOfDates(Date begin, Date end, DateFormat df, int dateInterval) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(begin);
        String currStr = null;
        String endStr = df.format(end);
        int i = 1;
        while (true) {
            currStr = df.format(cal.getTime());
            if (endStr.equals(currStr) || end.before(cal.getTime())) {
                break;
            }
            cal.add(dateInterval, 1);
            i++;
        }
        return i;
    }

    /**
     * 这一天的开始
     *
     * @param date
     * @return
     */
    public static Date getBeginningOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 这一天的结束
     *
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 当月第一天
     * 
     * @param date
     * @return
     */
    public static Date getBeginningOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        calendar.set(year, month, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 当月最后一天
     * 
     * @param date
     * @return
     */
    public static Date getEndOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        calendar.set(year, month, 1, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 昨天
     *
     * @return yyyy-MM-dd 格式的字符串日期
     */
    public static String getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date yesterday = calendar.getTime();
        return SDF_DATE.format(yesterday);
    }

    /**
     * 今天
     *
     * @return yyyy-MM-dd 格式的字符串日期
     */
    public static String getToday() {
        Calendar calendar = Calendar.getInstance();
        Date yesterday = calendar.getTime();
        return SDF_DATE.format(yesterday);
    }

    /**
     * 明天 yyyy-MM-dd
     * 
     * @throws ParseException
     */
    public static String getNextDay(Date today) throws ParseException {
        String formatToday = SDF_DATE.format(today);
        Calendar cal = Calendar.getInstance();
        cal.setTime(SDF_DATE.parse(formatToday));
        cal.add(Calendar.DAY_OF_YEAR, +1);
        String nextDate = SDF_DATE.format(cal.getTime());
        return nextDate;
    }

    /**
     * 字符串日期转日期对象
     * 
     * @param dateString
     * @return
     */
    public static Date toDate(String dateString, DateFormat format) {
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            LOGGER.error("failed to parse dateString", e);
        }
        return date;
    }

    public static String format(String dateString, DateFormat format) {

        Long dateLong = Long.valueOf(dateString);

        return format.format(new Date(dateLong * 1000L));
    }
    
    public static String format(Date date , DateFormat format) {


        return format.format(date);
    }
    
    public static String format(Date date  ) {
        return SDF_DATETIME.format(date);
    }
}
