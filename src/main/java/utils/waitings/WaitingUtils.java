package utils.waitings;

import aquality.selenium.browser.AqualityServices;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.data.DataUtils;

import java.time.Duration;

public class WaitingUtils {

    public static void waitUntilElementIsVisible(String xpath) {
        WebDriverWait wait = new WebDriverWait(AqualityServices.getBrowser().getDriver(), Duration.ofSeconds((Integer) DataUtils.getConfigTestData(DataUtils.configData, "EXPLICIT_WAIT")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }
}
