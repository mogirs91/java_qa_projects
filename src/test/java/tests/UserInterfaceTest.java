package tests;

import constants.ConfigDataConstants;
import constants.PathConstants;
import constants.TestDataConstants;
import forms.InterestsPage;
import forms.LoginPage;
import forms.MainPage;
import forms.PersonalDetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.base.BaseTest;
import utils.data.ConfigUtils;
import utils.random.RandomUtils;

public class UserInterfaceTest extends BaseTest {

    @Test
    public void LoginPageTest() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = new LoginPage();
        InterestsPage interestsPage = new InterestsPage();
        PersonalDetailsPage personalDetailsPage = new PersonalDetailsPage();
        browser.goTo((String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.MAIN_PAGE_URL));
        Assert.assertTrue(mainPage.state().waitForDisplayed(), "Welcome page is NOT open.");
        mainPage.clickNextPageLink();
        Assert.assertTrue(loginPage.state().waitForDisplayed(), "The '1' card is NOT open.");
        loginPage.enterPassword(RandomUtils.getPassword(
                (String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.USER_EMAIL)));
        loginPage.enterEmailAndDomain((String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.USER_EMAIL));
        loginPage.uncheckTermsNotAcceptedBox();
        loginPage.clickNextButton();
        Assert.assertTrue(interestsPage.state().waitForDisplayed(), "The '2' card is NOT open.");
        interestsPage.uncheckUnselectAllBox();
        interestsPage.selectRandomInterests((Integer) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.NUMBER_OF_INTERESTS));
        interestsPage.uploadClick();
        ConfigUtils.uploadImage();
        interestsPage.clickNextButton();
        Assert.assertTrue(personalDetailsPage.state().waitForDisplayed(), "The '3' card is NOT open.");
    }

    @Test
    public void HideHelpFormTest() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = new LoginPage();
        browser.goTo((String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.MAIN_PAGE_URL));
        Assert.assertTrue(mainPage.state().waitForDisplayed(), "Welcome page is NOT open.");
        mainPage.clickNextPageLink();
        loginPage.clickHideButton();
        Assert.assertTrue(loginPage.isHelpFormHidden(), "Form content is NOT hidden.");
    }

    @Test
    public void CloseCookiesFormTest() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = new LoginPage();
        browser.goTo((String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.MAIN_PAGE_URL));
        mainPage.clickNextPageLink();
        loginPage.clickAcceptCookies();
        Assert.assertTrue(loginPage.isCookiesMessageHidden(), "Cookies form is NOT closed.");
    }

    @Test
    public void TimerValidationTest() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = new LoginPage();
        browser.goTo((String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.MAIN_PAGE_URL));
        mainPage.clickNextPageLink();
        Assert.assertEquals(loginPage.timerValue(), ConfigUtils.getConfigTestData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.TIMER_VALUE), "timer does NOT start from '00:00'");
    }
}
