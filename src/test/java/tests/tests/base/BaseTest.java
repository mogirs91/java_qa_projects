package tests.tests.base;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import utils.data.DataUtils;

import java.time.Duration;

public abstract class BaseTest {

    public static Browser browser;

    @BeforeTest(alwaysRun = true)
    public static void setSettings() {
        DataUtils.setConfigTestData(DataUtils.pathToConfigData, "config");
        DataUtils.setConfigTestData(DataUtils.pathToTestData, "test");
    }

    @BeforeMethod
    public void initialize() {
        browser = AqualityServices.getBrowser();
        browser.maximize();
        browser.setImplicitWaitTimeout(Duration.ofSeconds(0));
    }

    @AfterMethod()
    public void close() {
        browser.quit();
    }
}

