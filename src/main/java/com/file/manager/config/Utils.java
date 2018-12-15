package com.file.manager.config;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * Utils for working with dates
 */
public class Utils {
    /**
     * transform string to date
     * @param date
     * @return date in dateFormat
     * @throws ParseException
     */
    public static Date parseToDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(date);
    }

    /**
     * @return current date time with Europe timezone
     * @throws ParseException
     */
    public static Date getTodayDateTime() throws ParseException {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return parseToDateTime(df.format(date));
    }

    /**
     * using to check date for old files
     *
     * @param date
     * @return
     */
    public static boolean checkCurrentDay(Date date) {
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.isBeforeNow();
    }

    /**
     * transform string to date
     *
     * @param date
     * @return date in dateFormat
     * @throws ParseException
     */
    public static Date parseToDateTime(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.parse(date);
    }
}
