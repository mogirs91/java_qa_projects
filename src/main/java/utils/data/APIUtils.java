package utils.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import constants.ConfigDataConstants;
import constants.PathConstants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Post;
import org.testng.Reporter;

import static io.restassured.RestAssured.given;

public class APIUtils {

    public static Response getMethodResponse(String endpoint) {
        return given()
                .get(endpoint);
    }

    public static Boolean isResponseBodyJson(Response response) {
        return response.getContentType().contains(
                (CharSequence) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.JSON_CONTENT));
    }

    public static Response postMethodRequest(String endPoint, Post data) {
        ObjectMapper mapper = new ObjectMapper();
        RequestSpecification request = RestAssured.given()
                .header((String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.HEADER_CONTENT_TYPE), ConfigUtils.getConfigTestData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.JSON_CONTENT));
        try {
            request.body(mapper.writeValueAsString(data));
            return request.post(endPoint);
        } catch (Exception e) {
            Reporter.log("post method failed", true);
        }
        return null;
    }
}
