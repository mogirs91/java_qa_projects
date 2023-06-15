package utils.tables;

import constants.PathConstants;
import constants.TestDataConstants;
import enums.TestTableEnums;
import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.testng.Reporter;
import utils.data.ConfigUtils;
import utils.database.DBUtils;
import utils.random.RandomUtils;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Test extends BaseTable {
    private Object authorId;
    private String browser;
    private Timestamp endTime;
    private String env;
    private String methodName;
    private String name;
    private Integer projectId;
    private Integer sessionId;
    private Timestamp startTime;
    private Integer statusId;
    private Integer id;

    @Override
    public PreparedStatement sendValues(PreparedStatement pstm) {
        try {
            pstm.setObject(TestTableEnums.AUTHOR_PARAMETER_INDEX.index, getAuthorId());
            pstm.setString(TestTableEnums.BROWSER_PARAMETER_INDEX.index, getBrowser());
            pstm.setTimestamp(TestTableEnums.END_TIME_PARAMETER_INDEX.index, getEndTime());
            pstm.setString(TestTableEnums.ENV_PARAMETER_INDEX.index, getEnv());
            pstm.setString(TestTableEnums.METHOD_NAME_PARAMETER_INDEX.index, getMethodName());
            pstm.setString(TestTableEnums.NAME_PARAMETER_INDEX.index, getName());
            pstm.setInt(TestTableEnums.PROJECT_ID_PARAMETER_INDEX.index, getProjectId());
            pstm.setInt(TestTableEnums.SESSION_ID_PARAMETER_INDEX.index, getSessionId());
            pstm.setTimestamp(TestTableEnums.START_TIME_PARAMETER_INDEX.index, getStartTime());
            pstm.setInt(TestTableEnums.STATUS_ID_PARAMETER_INDEX.index, getStatusId());
            pstm.setInt(TestTableEnums.ID_PARAMETER_INDEX.index, getId());
            return pstm;
        } catch (Exception e) {
            Reporter.log("failed to send values to Test instance", true);
        }
        return null;
    }

    public static Test getValues(ArrayList<String> allValuesById) {
        return new Test(
                DBUtils.selectMaxValueInColumn(Author.class, TestDataConstants.ID_COLUMN),
                allValuesById.get(TestTableEnums.BROWSER_NAME_COLUMN_INDEX.index),
                Timestamp.from(Instant.now()),
                allValuesById.get(TestTableEnums.ENV_COLUMN_INDEX.index),
                allValuesById.get(TestTableEnums.METHOD_NAME_COLUMN_INDEX.index),
                allValuesById.get(TestTableEnums.NAME_COLUMN_INDEX.index),
                DBUtils.selectMaxValueInColumn(Project.class, TestDataConstants.ID_COLUMN),
                Integer.parseInt(allValuesById.get(TestTableEnums.SESSION_COLUMN_INDEX.index)),
                Timestamp.from(Instant.now()),
                RandomUtils.getRandomInteger((Integer) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.TEST_STATUS_CODE_FROM),
                        (Integer) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.TEST_STATUS_CODE_TO)),
                Integer.parseInt(allValuesById.get(TestTableEnums.ID_COLUMN_INDEX.index))
        );
    }
}
