package com.scxd.toolkit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 11:22 2018/11/26
 * @Modified By:
 */
public class DateUtil {

    /**
     * 获取上月的开始时间
     *如现在是2018/10/29 则得到 的是2018/09/01 00:00:00
     * @return
     */
    public static Date getLastMonth() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date m = c.getTime();
        return m;
    }
    /**
     * 获取上一年上月的开始时间
     *如现在是2018/10/29 则得到 的是2017/09/01 00:00:00
     * @return
     */
    public static Date getLastYearLastMonthStart() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        c.setTime(new Date());
        c.add(Calendar.YEAR,-1);
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date m = c.getTime();

        String ddd=shortSdf.format(m);
        return m;
    }
    /**
     * 获取上一年上月的结束时间
     *如现在是2018/10/29 则得到 的是2017/09/01 00:00:00
     * @return
     */
    public static Date getLastYearLastMonthEnd() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR,-1);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date m = c.getTime();
//        String mon = format.format(m);
//        System.out.println("过去一个月："+mon);
        return m;
    }
    /**
     * 获取上一年 如现在是2018年  则 得到2017-01-01 00:00:00
     *
     * @return
     */
    public static Date getLastYear() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_YEAR, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date m3 = c.getTime();
//        String mon3 = format.format(m3);
//        System.out.println("过去三个月："+mon3);
        return m3;
    }

    /**
     * 获取年
     * year 正数往后+year年  如现在是2018年 year=1  则 得到2019-01-01 00:00:00
     * year 正数往前-year年  如现在是2018年 year=-1  则 得到2017-01-01 00:00:00
     *
     * @return
     */
    public static Date getLastYear(int year) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, year);
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_YEAR, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date m3 = c.getTime();
//        String mon3 = format.format(m3);
//        System.out.println("过去三个月："+mon3);
        return m3;
    }

    /**
     * 获取上一季度  如现在是2018/10/29 则得到的是2018/07/01 00:00：00
     *
     * @return
     */
    public static Date getLastQuarter() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -3);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date y = c.getTime();
//        String year = format.format(y);
//        System.out.println("过去一年："+year);
        return y;
    }

    /**
     * 获取上一年的上一季度开始时间 如现在是2018/10/29 则得到的是2017/07/01 00:00：00
     *
     * @return
     */
    public static Date getLastYearQuarterStart() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        c.add(Calendar.MONTH, -3);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date y = c.getTime();
//        String year = format.format(y);
//        System.out.println("过去一年："+year);
        return y;
    }

    /**
     * 获取上一年的上一季度结束时间
     *如现在是2018/10/29 则得到的是2017/10/01 00:00：00
     * @return
     */
    public static Date getLastYearQuarterEnd() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date date = getFirstDateOfMonth(c.getTime());
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date y = c.getTime();
//        String year = format.format(y);
//        System.out.println("过去一年："+year);
        return y;
    }

    /**
     * 获得本月第一天的开始时间，即2012-01-01 00:00:00
     *
     * @return
     */
    public static Date getCurrentDayStartTime() {
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = getFirstDateOfMonth(new Date());
        try {
            now = shortSdf.parse(shortSdf.format(now));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 获取当前月份 如现在是2018/11/29  则得到11
     *
     * @return
     */
    public static int getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        return currentMonth;
    }

    /**
     * 获取考核时间
     *
     * @param kssj
     * @param jssj
     * @return
     */
    public static String getKHSJ(Date kssj, Date jssj) {
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy/MM/dd");
        String k = shortSdf.format(kssj);
        String j = shortSdf.format(jssj);
        return k + "~" + j;
    }

    /**
     * 获取考核时间
     *
     * @param kssj
     * @return
     */
    public static String getKHBTSJ(Date kssj) {
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy年MM月");
        String k = shortSdf.format(kssj);
        return k;
    }

    /**
     * 获取年度
     * @param kssj
     * @return
     */

    public static String getKHBTND(Date kssj) {
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy年");
        String k = shortSdf.format(kssj);
        return k;
    }

    /**
     * 取得月第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得月最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     *
     * @param date
     * @return
     */
    public static String getKHBTJD(Date date) {
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy年");
        String k = shortSdf.format(date);
        String season = "";

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = "第一季度";
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = "第二季度";
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = "第三季度";
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = "第四季度";
                break;
            default:
                break;
        }
        return k + season;
    }


    public static String getStringYYYYMMDDByDate(Date date) {
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        String k = shortSdf.format(date);
        return k;
    }

    public static String getYYMM(Date date) {
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy年MM月");
        String k = shortSdf.format(date);
        return k;
    }
    public static String getYYMM2(Date date) {
        date.setMonth(date.getMonth() - 1);
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy年MM月");
        String k = shortSdf.format(date);
        return k;
    }
    public static String getYY(Date date) {
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy年");
        String k = shortSdf.format(date);
        return k;
    }

    public static Date formmatDateYYYYMMDD(String str) {
        SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date =null;
        try {
             date=shortSdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
