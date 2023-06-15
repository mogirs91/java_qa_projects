package tests.basetest;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import constants.PathConstants;
import constants.TestDataConstants;
import forms.ProjectTestsPage;
import io.restassured.response.Response;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.logging.LogType;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utils.data.ApiMethods;
import utils.data.CommonUtils;
import utils.data.ConfigUtils;

public abstract class BaseTest {
    protected static Browser browser;
    protected final ApiMethods apiMethods = new ApiMethods();
    protected final ProjectTestsPage projectTestsPage = new ProjectTestsPage();
    protected static String SCREENSHOT;
    protected static final String MAIN_URL_AUTHORIZED = "http://%s:%s@%s";
    protected static final String TOKEN = "token";
    protected static final String JSON = "json";
    protected static final Integer FIRST_PAGE_RAWS = 20;
    protected static final Integer FIRST_TAB = 0;
    protected static final Integer SECOND_TAB = 1;
    protected static String SESSION_ID;
    protected static final String ENV = "user.name";
    protected static final String CONTENT_TYPE = "image/png";

    protected BaseTest() {
    }

    @BeforeClass
    public void generateSessionId() {
        SESSION_ID = String.valueOf(System.currentTimeMillis());
    }

    @BeforeMethod
    public void initialize() {
        browser = AqualityServices.getBrowser();
    }

    @AfterMethod()
    public void AfterMethod(ITestResult result) {
        Response response = apiMethods.makeTestRecord(SESSION_ID, (String) ConfigUtils.getData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.NEW_PROJECT_NAME), result.getName(), result.getMethod().getMethodName(), System.getProperty(ENV));
        String browserLogs = CommonUtils.cleanStringForFilePath(browser.getLogs(LogType.BROWSER).getAll().toString());
        apiMethods.sendLog(response.asString(), browserLogs);
        apiMethods.sendAttachment(response.asString(), SCREENSHOT, CONTENT_TYPE);
        Assert.assertTrue(projectTestsPage.testIsDisplayed(response.asString()), "Test has NOT appeared");
    }

    @AfterMethod
    public void CloseBrowser() {
        browser.quit();
    }
}
