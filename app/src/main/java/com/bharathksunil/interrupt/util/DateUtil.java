package com.bharathksunil.interrupt.util;

import com.github.thunder413.datetimeutils.DateTimeUnits;
import com.github.thunder413.datetimeutils.DateTimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A Utility for maintaining uniformity od date time storage
 *
 * @author Bharath on 24-02-2018.
 */

public class DateUtil {
    private static final String PATTERN = "hh:mm a, dd MMMM yyyy";

    public static String getCurrentDateTimeAsString() {
        String now;
        now = DateTimeUtils.formatWithPattern(Calendar.getInstance().getTime(), PATTERN);
        return now;
    }

    public static boolean isTimePast(String time) {
        Date now, then;
        long diff;
        try {
            now = new SimpleDateFormat(PATTERN, Locale.getDefault()).parse(getCurrentDateTimeAsString());
            then = new SimpleDateFormat(PATTERN, Locale.getDefault()).parse(time);
            diff = DateTimeUtils.getDateDiff(now, then, DateTimeUnits.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
            diff = 0;
        }
        return diff > 0;
    }

    public static Long getTimestampFromDate(String date){
        Date thisDate;
        try {
            thisDate = new SimpleDateFormat(PATTERN, Locale.getDefault()).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return thisDate.getTime();
    }

}
