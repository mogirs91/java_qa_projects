package utils.data;

import enums.ApiMethodsEnums;
import io.restassured.response.Response;

import java.util.HashMap;

public class ApiMethods {

    private static final String VARIANT = "variant";
    private static final String PROJECT_ID = "projectId";
    private static final String SESSION_ID = "SID";
    private static final String PROJECT_NAME = "projectName";
    private static final String TEST_NAME = "testName";
    private static final String METHOD_NAME = "methodName";
    private static final String ENV = "env";
    private static final String TEST_ID = "testId";
    private static final String CONTENT = "content";
    private static final String CONTENT_TYPE = "contentType";

    private static final String TEST_LIST_STRING = "%s%s";
    private final ApiUtils apiUtils = new ApiUtils();

    public Response generateToken(String variant) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(VARIANT, variant);
        return apiUtils.post(ApiMethodsEnums.TOKEN_GET.value, params);
    }

    public Response getTestList(String projectId, String type) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(PROJECT_ID, projectId);
        return apiUtils.post(String.format(TEST_LIST_STRING, ApiMethodsEnums.TEST_GET.value, type), params);
    }

    public Response makeTestRecord(String sessionId, String projectName, String testName, String methodName, String env) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(SESSION_ID, sessionId);
        params.put(PROJECT_NAME, projectName);
        params.put(TEST_NAME, testName);
        params.put(METHOD_NAME, methodName);
        params.put(ENV, env);
        return apiUtils.post(ApiMethodsEnums.TEST_PUT.value, params);
    }

    public Response sendLog(String testId, String content) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(TEST_ID, testId);
        params.put(CONTENT, content);
        return apiUtils.post(ApiMethodsEnums.TEST_PUT_LOG.value, params);
    }

    public Response sendAttachment(String testId, String content, String contentType) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(TEST_ID, testId);
        params.put(CONTENT, content);
        params.put(CONTENT_TYPE, contentType);
        return apiUtils.post(ApiMethodsEnums.TEST_PUT_ATTACHMENT.value, params);
    }
}
