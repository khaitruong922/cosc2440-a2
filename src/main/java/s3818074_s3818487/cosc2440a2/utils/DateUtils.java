package s3818074_s3818487.cosc2440a2.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static boolean isSameDay(Date d1, Date d2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(d1).equals(fmt.format(d2));
    }

    public static boolean isBeforeInclusive(Date d1, Date d2) {
        return d1.before(d2) || isSameDay(d1, d2);
    }

    public static boolean isAfterInclusive(Date d1, Date d2) {
        return d1.after(d2) || isSameDay(d1, d2);
    }
}
