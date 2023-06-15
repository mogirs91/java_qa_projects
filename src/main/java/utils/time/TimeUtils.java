package utils.time;

import java.sql.Timestamp;

public class TimeUtils {

    public static Timestamp millisecondsToTimestamp(long milliseconds) {
        return new Timestamp(milliseconds);
    }
}
