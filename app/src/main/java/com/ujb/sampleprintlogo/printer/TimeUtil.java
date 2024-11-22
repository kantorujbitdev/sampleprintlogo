package com.ujb.sampleprintlogo.printer;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class TimeUtil {
    public static final SimpleDateFormat DEFAULT_FORMAT =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale());

    public static String currentTime() {
        Date date = new Date();
        return DEFAULT_FORMAT.format(date);
    }

    public static String getCurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale());
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        return sdf.format(new Date());
    }

    public static Locale locale() {
        return new Locale("in", "ID");
    }

}
