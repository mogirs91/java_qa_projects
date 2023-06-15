package utils.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Reporter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DataUtils {

    public static HashMap<String, Object> configData;
    public static HashMap<String, Object> testData;

    public static final String pathToConfigData = System.getProperty("user.dir") + "\\src\\main\\resources\\ConfigData.json";
    public static final String pathToTestData = System.getProperty("user.dir") + "\\src\\main\\resources\\TestData.json";

    public static HashMap<String, Object> getNewMapperHashStringObject(String path) {
        try {
            return new ObjectMapper()
                    .readValue(new File(path), new TypeReference<HashMap<String, Object>>() {
                    });
        } catch (Exception e) {
            Reporter.log("Failed to map a dictionary (key: string, value: object)", true);
            return null;
        }
    }

    public static void setConfigTestData(String path, String type) {
        try {
            switch (type) {
                case "config":
                    configData = getNewMapperHashStringObject(path);
                case "test":
                    testData = getNewMapperHashStringObject(path);
            }
        } catch (Exception e) {
            Reporter.log("Failed to set config or test data", true);
        }
    }

    public static Object getConfigTestData(Map<String, Object> map, String parameterName) {
        for (Map.Entry<String, Object> p : map.entrySet()) {
            if (p.getKey().equals(parameterName)) {
                return p.getValue();
            }
        }
        return null;
    }

    public static void uploadImage() {
        try {
            Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\src\\test\\resources\\Upload_file.exe");
        } catch (Exception e) {
            Reporter.log("Cannot execute AutoIt script");
        }
    }
}
