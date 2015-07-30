package pl.chiqvito.sowieso.utils;

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

}
