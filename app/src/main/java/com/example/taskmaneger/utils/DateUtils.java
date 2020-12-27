package com.example.taskmaneger.utils;

import java.text.DateFormat;
import java.util.Date;

public class DateUtils {
    private static DateFormat sDATE_FORMAT =
            DateFormat.getDateInstance(DateFormat.SHORT);
    private static DateFormat sTIME_FORMAT =
            DateFormat.getTimeInstance(DateFormat.SHORT);

    public static String getShortDateFormat(Date date){
        return sDATE_FORMAT.format(date);
    }

    public static String getShortTimeFormat(Date date){
        return sTIME_FORMAT.format(date);
    }
}
