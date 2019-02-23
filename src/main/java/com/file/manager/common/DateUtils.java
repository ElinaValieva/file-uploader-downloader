package com.file.manager.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    public static Date getTodayDateTime() throws ParseException {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return parseToDateTime(df.format(date));
    }


    public static boolean checkCurrentDay(Date date) {
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.isBeforeNow();
    }

    public static Date parseToDateTime(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(date);
    }
}
