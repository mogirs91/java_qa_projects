package utils.data;

import constants.ApiMethodsKeysConstants;
import constants.ConfigDataConstants;
import constants.PathConstants;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ApiUtils {


    private HashMap<String, Object> params;

    public ApiUtils() {
        params = commonParams();
        RestAssured.baseURI = (String) ConfigUtils.getData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.BASE_URI);
        RestAssured.urlEncodingEnabled = false;
    }

    public HashMap<String, Object> commonParams() {
        params = new HashMap<String, Object>();
        params.put(ApiMethodsKeysConstants.OWNER_ID,
                ConfigUtils.getData(PathConstants.PATH_TO_API_METHODS_KEYS, ApiMethodsKeysConstants.OWNER_ID));
        params.put(ApiMethodsKeysConstants.ACCESS_TOKEN,
                ConfigUtils.getData(PathConstants.PATH_TO_API_METHODS_KEYS, ApiMethodsKeysConstants.ACCESS_TOKEN));
        params.put(ApiMethodsKeysConstants.API_VERSION,
                ConfigUtils.getData(PathConstants.PATH_TO_API_METHODS_KEYS, ApiMethodsKeysConstants.API_VERSION));
        return params;
    }

    public Response post(String path, HashMap<String, Object> params) {
        RestAssured.basePath = path;
        return given()
                .queryParams(params)
                .post();
    }

    public Response upload(String multipartExpression, String multipartType, String uploadUrl, String pathName) {
        return given()
                .multiPart(multipartExpression, new File(pathName), multipartType)
                .post(uploadUrl);
    }
}
