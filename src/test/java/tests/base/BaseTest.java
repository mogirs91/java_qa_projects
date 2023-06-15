package tests.base;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import enums.TestTableEnums;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.data.ModelUtils;
import utils.database.DBUtils;
import utils.tables.Test;

import java.time.Duration;


public abstract class BaseTest {

    public static Browser browser;

    @BeforeMethod
    public void initialize() {
        browser = AqualityServices.getBrowser();
        browser.maximize();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        DBUtils.insert(ModelUtils.getNewSession());
        DBUtils.insert(ModelUtils.getNewProject());
        DBUtils.insert(ModelUtils.getNewAuthor());
        Test table = ModelUtils.getNewTest(result);
        DBUtils.insert(table);
        Assert.assertEquals(DBUtils.selectColumnValueById(
                Test.class, table.getId(), TestTableEnums.INSERT_COLUMN_INDEX.index), table.getName(), "Information has NOT been added.");
        browser.quit();
    }
}

