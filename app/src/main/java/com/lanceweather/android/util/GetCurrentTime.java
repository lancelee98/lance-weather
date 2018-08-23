package com.lanceweather.android.util;

import java.util.Calendar;
import java.util.TimeZone;

public class GetCurrentTime {

    public static Calendar cal;
    public static String hour;
    public static String minute;
    public static String second;
    public static String currentTime;

    public static String getCurrentTime()
    {
        cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        if (cal.get(Calendar.AM_PM) == 0)
            hour = String.valueOf(cal.get(Calendar.HOUR));
        else
            hour = String.valueOf(cal.get(Calendar.HOUR)+12);
        minute = String.valueOf(cal.get(Calendar.MINUTE));

        currentTime = hour + ":" + minute ;
        return currentTime;
    }
}
