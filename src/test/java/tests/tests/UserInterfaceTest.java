package tests.tests;

import forms.InterestsPage;
import forms.LoginPage;
import forms.MainPage;
import forms.PersonalDetailsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.tests.base.BaseTest;
import utils.data.DataUtils;
import utils.random.RandomUtils;

public class UserInterfaceTest extends BaseTest {

    @Test
    public void LoginPageTest() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = new LoginPage();
        InterestsPage interestsPage = new InterestsPage();
        PersonalDetailsPage personalDetailsPage = new PersonalDetailsPage();
        browser.goTo((String) DataUtils.getConfigTestData(DataUtils.configData, "MAIN_PAGE_URL"));
        Assert.assertTrue(mainPage.state().waitForDisplayed(), "Welcome page is NOT open.");
        mainPage.clickNextPageLink();
        Assert.assertTrue(loginPage.state().waitForDisplayed(), "The '1' card is NOT open.");
        loginPage.enterPassword(RandomUtils.getPassword((String) DataUtils.getConfigTestData(DataUtils.testData, "USER_EMAIL"), (Integer) DataUtils.getConfigTestData(DataUtils.testData, "NUMBER_OF_CHARACTERS_IN_PASSWORD")));
        loginPage.enterEmailAndDomain((String) DataUtils.getConfigTestData(DataUtils.testData, "USER_EMAIL"));
        loginPage.uncheckTermsNotAcceptedBox();
        loginPage.clickNextButton();
        Assert.assertTrue(interestsPage.state().waitForDisplayed(), "The '2' card is NOT open.");
        interestsPage.uncheckUnselectAllBox();
        interestsPage.selectRandomInterests((Integer) DataUtils.getConfigTestData(DataUtils.testData, "NUMBER_OF_INTERESTS"));
        interestsPage.uploadClick();
        DataUtils.uploadImage();
        interestsPage.clickNextButton();
        Assert.assertTrue(personalDetailsPage.state().waitForDisplayed(), "The '3' card is NOT open.");
    }

    @Test
    public void HideHelpFormTest() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = new LoginPage();
        browser.goTo((String) DataUtils.getConfigTestData(DataUtils.configData, "MAIN_PAGE_URL"));
        Assert.assertTrue(mainPage.state().waitForDisplayed(), "Welcome page is NOT open.");
        mainPage.clickNextPageLink();
        loginPage.clickHideButton();
        Assert.assertTrue(loginPage.isHelpFormHidden(), "Form content is NOT hidden.");
    }

    @Test
    public void CloseCookiesFormTest() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = new LoginPage();
        browser.goTo((String) DataUtils.getConfigTestData(DataUtils.configData, "MAIN_PAGE_URL"));
        mainPage.clickNextPageLink();
        loginPage.clickAcceptCookies();
        Assert.assertTrue(loginPage.isCookiesMessageHidden(), "Cookies form is NOT closed.");
    }

    @Test
    public void TimerValidationTest() {
        MainPage mainPage = new MainPage();
        LoginPage loginPage = new LoginPage();
        browser.goTo((String) DataUtils.getConfigTestData(DataUtils.configData, "MAIN_PAGE_URL"));
        mainPage.clickNextPageLink();
        Assert.assertEquals(loginPage.timerValue(), DataUtils.getConfigTestData(DataUtils.testData, "TIMER_VALUE"), "timer does NOT start from \"00:00\"");
    }
}
