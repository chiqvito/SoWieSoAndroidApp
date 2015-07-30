package pl.chiqvito.sowieso.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static String dateFormat(int year, int month, int day) {
        StringBuilder sb = new StringBuilder();
        sb.append(year);
        sb.append("-");
        sb.append(month + 1);
        sb.append("-");
        sb.append(day);
        return sb.toString();
    }

    public static Date date(String date) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
            return sf.parse(date);
        } catch (ParseException e) {
            Log.e(DateUtil.class.getName(), e.getMessage(), e);
        }
        return null;
    }

    public static String date(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        return sf.format(date);
    }

}
