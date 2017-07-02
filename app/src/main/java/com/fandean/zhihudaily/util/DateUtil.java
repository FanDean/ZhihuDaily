package com.fandean.zhihudaily.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Date类在设计上存在缺陷，提倡使用Calendar类(抽象类)来处理日期和时间
 * Calendar.MONTH字段代表月份，而月份是从0开始的
 * Calendar涉及到TimeZone、Locale
 * Created by fan on 17-6-18.
 * 格式化：
 * "yyyy-MM-dd HH:mm:ss:SSS"
 * "yyyyMMddHHmmss"
 *
 * GregorianCalendar.getInstance() 获取实例
 */

public class DateUtil {
    public static final String CREATTIME_FORMAT = "yyyyMMddHHmmss";
    public static final String ZHIHU_DATE_FORMAT = "yyyyMMdd";


    /*
     * 一个GregorianCalendar对象的toString()输出
     * java.util.GregorianCalendar
     * [time=1498114962272,areFieldsSet=true,areAllFieldsSet=true,lenient=true,
     * zone=libcore.util.ZoneInfo[id="Asia/Shanghai",mRawOffset=28800000,mEarliestRawOffset=28800000,mUseDst=false,mDstSavings=0,transitions=16],
     * firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,
     * YEAR=2017,MONTH=5,WEEK_OF_YEAR=25,WEEK_OF_MONTH=4,DAY_OF_MONTH=22,DAY_OF_YEAR=173,
     * DAY_OF_WEEK=5,DAY_OF_WEEK_IN_MONTH=4,AM_PM=1,HOUR=3,HOUR_OF_DAY=15,MINUTE=2,
     * SECOND=42,MILLISECOND=272,ZONE_OFFSET=28800000,DST_OFFSET=0]
     */


    public static String getCurrentTimeString(String formatStr){
        //获得当前时间
        GregorianCalendar calendar = new GregorianCalendar();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
        //format()方法将Date转换为指定的格式；可用parse()方法将指定形式的字符串转换为Date。
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String calendarToStr(GregorianCalendar calendar, String formatStr){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 将字符串转换为GregorianCalendar
     * @param dateStr 要转换的时间串
     * @param formatStr 时间字符串表示的时间格式，比如"yyyyMMddHHmmss"
     * @return
     */
    public static GregorianCalendar stringToCalendar(String dateStr, String formatStr){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
        try {
            Date date = simpleDateFormat.parse(dateStr);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将传入的表示日期的字符串中的日期值加一并返回加一后的值，该日期格式为ZHIHU_DATE_FORMAT
     * @param dateStr 表示ZHIHU_DATE_FORMAT形式的字符串
     * @return 加一后的日期字符串
     */
    public static String dateOnePlus(String dateStr){
        GregorianCalendar calendar = stringToCalendar(dateStr,ZHIHU_DATE_FORMAT);
        calendar.add(Calendar.DAY_OF_MONTH,1);
        return calendarToStr(calendar,ZHIHU_DATE_FORMAT);
    }

}
