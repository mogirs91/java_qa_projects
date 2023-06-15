package utils.waitings;

import aquality.selenium.browser.AqualityServices;
import constants.ConfigDataConstants;
import constants.PathConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.data.ConfigUtils;

import java.time.Duration;

public class WaitingUtils {

    public static void waitUntilElementIsVisible(String xpath) {
        WebDriverWait wait = new WebDriverWait(AqualityServices.getBrowser().getDriver(), Duration.ofSeconds((Integer) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.EXPLICIT_WAIT)));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }
}
