package utils.data;

import constants.PathConstants;
import constants.TestDataConstants;
import enums.AuthorTableEnums;
import enums.ProjectTableEnums;
import enums.SessionTableEnums;
import org.testng.ITestResult;
import org.testng.Reporter;
import utils.database.CRUDstrings;
import utils.database.DBUtils;
import utils.random.RandomUtils;
import utils.tables.Author;
import utils.tables.Project;
import utils.tables.Session;
import utils.tables.Test;
import utils.time.TimeUtils;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

public class ModelUtils {

    private static Integer newSessionId = null;
    private static Integer newProjectId = null;
    private static Integer newAuthorId = null;
    private static Integer newTestId;
    private static final String idColumnNane = "id";
    private static final Integer buildNumber = 700;
    private static final String projectName = "projectX";
    private static final String authorEmail = "mymail78@mail.ru";
    private static final String authorLogin = "Serg91";
    private static final String authorName = "Sergei";
    private static final String testEnv = "test_env";
    private static final Integer randomIdFrom = 1;
    private static final Integer randomIdTo = 9;
    private static final String testNameEmulation = "ProcessingOfTestDataTest";
    private static final String testMethodNameEmulation = "ProcessingOfTestDataTestName";
    private static final Integer testStatusEmulation = 1;
    private static final String selectRandomColumnName = "*";
    private static final String selectRandomIds = "id LIKE '%%%d%d%%'";

    public static Session getNewSession() {
        if (!DBUtils.selectColumnValueById(Session.class, DBUtils.selectMaxValueInColumn(Session.class, idColumnNane), SessionTableEnums.SESSION_ID_PARAMETER_INDEX.index)
                .equals(buildNumber.toString())) {
            Reporter.log("Session instance is crated", true);
            newSessionId = DBUtils.selectMaxValueInColumn(Session.class, idColumnNane) + 1;
            return new Session(
                    buildNumber,
                    Timestamp.from(Instant.now()),
                    Timestamp.from(Instant.now()).toString().trim(),
                    newSessionId);
        } else {
            newSessionId = DBUtils.selectMaxValueInColumn(Session.class, idColumnNane);
            return null;
        }
    }

    public static Project getNewProject() {
        if (!DBUtils.selectColumnValueById(Project.class, DBUtils.selectMaxValueInColumn(Project.class, idColumnNane), ProjectTableEnums.ID_PARAMETER_INDEX.index)
                .equals(projectName)) {
            Reporter.log("Project instance is crated", true);
            newProjectId = DBUtils.selectMaxValueInColumn(Project.class, idColumnNane) + 1;
            return new Project(
                    projectName,
                    newProjectId);
        } else {
            newProjectId = DBUtils.selectMaxValueInColumn(Project.class, idColumnNane);
            return null;
        }
    }

    public static Author getNewAuthor() {
        if (!DBUtils.selectColumnValueById(Author.class, DBUtils.selectMaxValueInColumn(Author.class, idColumnNane), AuthorTableEnums.AUTHOR_LOGIN_PARAMETER_INDEX.index)
                .equals(authorName)) {
            Reporter.log("Author instance is crated", true);
            newAuthorId = DBUtils.selectMaxValueInColumn(Author.class, idColumnNane) + 1;
            return new Author(
                    authorEmail,
                    authorLogin,
                    authorName,
                    newAuthorId);
        } else {
            newAuthorId = DBUtils.selectMaxValueInColumn(Author.class, idColumnNane);
            return null;
        }
    }

    public static Test getNewTest(ITestResult result) {

        newTestId = DBUtils.selectMaxValueInColumn(Test.class, idColumnNane) + 1;

        return new Test(
                ModelUtils.newAuthorId,
                (String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_AQUALITY_SETTINGS, TestDataConstants.BROWSER_NAME),
                TimeUtils.millisecondsToTimestamp(result.getEndMillis()),
                testEnv,
                result.getMethod().getMethodName(),
                result.getName(),
                ModelUtils.newProjectId,
                ModelUtils.newSessionId,
                TimeUtils.millisecondsToTimestamp(result.getStartMillis()),
                result.getStatus(),
                ModelUtils.newTestId
        );
    }

    public static Test getNewTestEmulated() {

        newTestId = DBUtils.selectMaxValueInColumn(Test.class, idColumnNane) + 1;

        return new Test(
                ModelUtils.newAuthorId,
                (String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_AQUALITY_SETTINGS, TestDataConstants.BROWSER_NAME),
                TimeUtils.millisecondsToTimestamp(System.currentTimeMillis()),
                testEnv,
                testMethodNameEmulation,
                testNameEmulation,
                ModelUtils.newProjectId,
                ModelUtils.newSessionId,
                TimeUtils.millisecondsToTimestamp(System.currentTimeMillis()),
                testStatusEmulation,
                ModelUtils.newTestId
        );
    }

    public static <T> ArrayList<Integer> getRandomIds(Class<T> tClass, Integer columnIndex) {
        try {
            Integer number = RandomUtils.getRandomInteger(randomIdFrom, randomIdTo);
            ArrayList<Integer> results = new ArrayList<>();
            ResultSet result = DBUtils.executeQuery(CRUDstrings.select(tClass, selectRandomColumnName, String.format(selectRandomIds, number, number), (String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.DATABASE_NAME)));
            while (result.next())
                results.add(result.getInt(columnIndex));
            return results;
        } catch (Exception e) {
            Reporter.log("failed to get random ids", true);
        }
        return null;
    }
}

