package cn.xygu.accounting;

import android.icu.util.Calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Acyco on 2019-06-11.
 */

public class DateUtil {
    public static String getFormattedTime(long timeStamp){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(new Date(timeStamp));
    }
    public static String getFormattedDate(long timeStamp){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date(timeStamp));
    }
    public static String getFormattedDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }

    private static Date str2Date(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();

    }
    public static String getWeekDay(String date){
        String[] weekDays = {"SUNDAY","MONDAY","TUESDAY","WEDENDAY","THURSDAY","FRIDAY","SATURDAY"};
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(str2Date(date));
        int index = calendar.get(Calendar.DAY_OF_WEEK)-1;
        return weekDays[index];
    }

    public static String getDateTitle(String date){
        String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(str2Date(date));
        int monthIndex = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return months[monthIndex] + " " + String.valueOf(day);
    }


}
