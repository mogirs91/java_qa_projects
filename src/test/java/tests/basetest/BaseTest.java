package tests.basetest;

import constants.ConfigDataConstants;
import constants.PathConstants;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import utils.data.ConfigUtils;

public abstract class BaseTest {

    @BeforeMethod
    public void configureRestAssured() {
        RestAssured.baseURI = (String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.BASE_URI);
    }
}
