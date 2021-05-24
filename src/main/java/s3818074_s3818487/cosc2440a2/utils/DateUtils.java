package s3818074_s3818487.cosc2440a2.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static Date parse(String s) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return fmt.parse(s);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String format(Date d) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        return fmt.format(d);
    }

    public static boolean isSameDay(Date d1, Date d2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        return fmt.format(d1).equals(fmt.format(d2));
    }

    public static boolean isBeforeInclusive(Date d1, Date d2) {
        return d1.before(d2) || isSameDay(d1, d2);
    }

    public static boolean isAfterInclusive(Date d1, Date d2) {
        return d1.after(d2) || isSameDay(d1, d2);
    }
}
