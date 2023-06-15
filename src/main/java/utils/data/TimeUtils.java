package utils.data;

import models.ProjectTestsModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeUtils {
    public static Long getStartTimeAsLong(ProjectTestsModel projectTestsModel) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            return Long.valueOf(dateFormat.parse(projectTestsModel.getStartTime()).getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
