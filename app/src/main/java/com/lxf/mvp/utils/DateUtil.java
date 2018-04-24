package com.lxf.mvp.utils;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by lixiaofan on 2017/7/26.
 */

public class DateUtil {
    private static final SimpleDateFormat sFormatToday = new SimpleDateFormat("今天 HH:mm");
    private static final SimpleDateFormat sFormatThisYear = new SimpleDateFormat("MM/dd HH:mm");
    private static final SimpleDateFormat sFormatOtherYear = new SimpleDateFormat("yy/MM/dd HH:mm");
    private static final SimpleDateFormat sFormatMessageToday = new SimpleDateFormat("今天");
    private static final SimpleDateFormat sFormatMessageThisYear = new SimpleDateFormat("MM/dd");
    private static final SimpleDateFormat sFormatMessageOtherYear = new SimpleDateFormat("yy/MM/dd");

    public static String dayToNow(long time) {
        return dayToNow(time, true);
    }

    public static String dayToNow(long time, boolean displayHour) {
        long nowMill = System.currentTimeMillis();

        long minute = (nowMill - time) / 60000;
        if (minute < 60) {
            if (minute <= 0) {
                return Math.max((nowMill - time) / 1000, 1) + "秒前"; // 由于手机时间的原因，有时候会为负，这时候显示1秒前
            } else {
                return minute + "分钟前";
            }
        }

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);
        int year = calendar.get(GregorianCalendar.YEAR);
        int month = calendar.get(GregorianCalendar.MONTH);
        int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);

        calendar.setTimeInMillis(nowMill);
        Long timeObject = new Long(time);
        if (calendar.get(GregorianCalendar.YEAR) != year) { // 不是今年
            SimpleDateFormat sFormatOtherYear = displayHour ? DateUtil.sFormatOtherYear : DateUtil.sFormatMessageOtherYear;
            return sFormatOtherYear.format(timeObject);
        } else if (calendar.get(GregorianCalendar.MONTH) != month
                || calendar.get(GregorianCalendar.DAY_OF_MONTH) != day) { // 今年
            SimpleDateFormat sFormatThisYear = displayHour ? DateUtil.sFormatThisYear : DateUtil.sFormatMessageThisYear;
            return sFormatThisYear.format(timeObject);
        } else { // 今天
            SimpleDateFormat sFormatToday = displayHour ? DateUtil.sFormatToday : DateUtil.sFormatMessageToday;
            return sFormatToday.format(timeObject);
        }
    }

}
