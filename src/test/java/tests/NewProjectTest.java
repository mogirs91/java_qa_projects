package tests;

import constants.ConfigDataConstants;
import constants.PathConstants;
import constants.TestDataConstants;
import forms.NewProjectPage;
import forms.ProjectsPage;
import io.restassured.response.Response;
import models.ProjectTestsModel;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.basetest.BaseTest;
import utils.data.ConfigUtils;
import utils.data.ModelUtils;
import utils.data.TimeUtils;

import java.util.Comparator;
import java.util.List;

public class NewProjectTest extends BaseTest {

    @Test
    public void NewProjectApiUITest() {
        ProjectsPage projectsPage = new ProjectsPage();
        NewProjectPage newProjectPage = new NewProjectPage();
        String token = apiMethods.generateToken((String) ConfigUtils.getData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.VARIANT)).asString();
        Assert.assertFalse(token.isEmpty(), "Token has NOT been generated");
        browser.goTo(String.format(MAIN_URL_AUTHORIZED,
                ConfigUtils.getData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.LOGIN),
                ConfigUtils.getData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.PASSWORD),
                ConfigUtils.getData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.LOCALHOST)));
        browser.getDriver().manage().addCookie(new Cookie(TOKEN, token));
        browser.refresh();
        Assert.assertTrue(projectsPage.state().isDisplayed(), "Projects page is NOT displayed");
        Assert.assertEquals(projectsPage.getVariantLabel(), (String) ConfigUtils.getData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.VARIANT),
                "Variant number is NOT correct");
        Response responseList = apiMethods.getTestList(projectsPage.getProjectId((String) ConfigUtils.getData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.PROJECT_NAME)), JSON);
        List<ProjectTestsModel> apiList = ModelUtils.readResponseAsModel(responseList.asString()).stream().sorted(Comparator.comparingLong(s -> TimeUtils.getStartTimeAsLong((ProjectTestsModel) s)).reversed()).toList();
        List<ProjectTestsModel> APIFirstPageList = apiList.stream().limit(FIRST_PAGE_RAWS).toList();
        projectsPage.projectNameClick((String) ConfigUtils.getData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.PROJECT_NAME));
        projectTestsPage.state().waitForDisplayed();
        List<ProjectTestsModel> UIFirstPageList = ModelUtils.setTestRecords(projectTestsPage.getTestRecords());
        Assert.assertTrue(APIFirstPageList.toString().equalsIgnoreCase(UIFirstPageList.toString()),
                "Tests on the first Page NOT sorted in descending order by date OR NOT equal tests in API response");
        projectTestsPage.homeLabelClick();
        projectsPage.state().waitForDisplayed();
        projectsPage.addProjectButtonLink();
        browser.getDriver().switchTo().window((String) (browser.getDriver().getWindowHandles().toArray()[SECOND_TAB]));
        projectsPage.enterPojectName((String) ConfigUtils.getData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.NEW_PROJECT_NAME));
        projectsPage.submitProjectButtonClick();
        Assert.assertTrue(newProjectPage.isProjectSaved(), "Success save message has NOT appeared");
        browser.getDriver().close();
        browser.getDriver().switchTo().window((String) (browser.getDriver().getWindowHandles().toArray()[FIRST_TAB]));
        browser.refresh();
        Assert.assertTrue(projectsPage.doesNewProjectExist((String) ConfigUtils.getData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.NEW_PROJECT_NAME)),
                "Project has NOT appeared in the List");
        projectsPage.projectNameClick((String) ConfigUtils.getData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.NEW_PROJECT_NAME));
        SCREENSHOT = browser.getDriver().getScreenshotAs(OutputType.BASE64);
    }
}
