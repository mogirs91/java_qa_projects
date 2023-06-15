package tests.basetest;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    public static Browser browser;

    @BeforeMethod
    public void initialize() {
        browser = AqualityServices.getBrowser();
        browser.maximize();
    }

    @AfterMethod()
    public void close() {
        browser.quit();
    }
}
