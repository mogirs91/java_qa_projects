package utils.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Reporter;

public class DataUtils {

    public static String getJsonNode(Response response, String jsonPath) {
        JsonPath jsonPathEvaluator = response.jsonPath();
        return jsonPathEvaluator.get(jsonPath).toString();
    }

    public static <T> T responseToModel(Response response, Class<T> tClass) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.getBody().asString(), tClass);
        } catch (Exception e) {
            Reporter.log("Failed to deserialize response to a model", true);
        }
        return null;
    }
}
