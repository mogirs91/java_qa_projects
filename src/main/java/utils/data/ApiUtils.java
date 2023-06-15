package utils.data;

import constants.ConfigDataConstants;
import constants.PathConstants;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ApiUtils {

    private HashMap<String, Object> params;

    public ApiUtils() {
        params = new HashMap<String, Object>();
        RestAssured.baseURI = (String) ConfigUtils.getData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.BASE_URI);
        RestAssured.urlEncodingEnabled = false;
    }

    public Response post(String path, HashMap<String, Object> params) {
        RestAssured.basePath = path;
        return given()
                .queryParams(params)
                .post();
    }
}
