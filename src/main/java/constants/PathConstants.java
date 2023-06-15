package constants;

import java.io.File;

public class PathConstants {
    public static final String JAVA_RESOURCES = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator;
    public static final String PATH_TO_TEST_DATA = String.format("%sTestData.json", JAVA_RESOURCES);
    public static final String PATH_TO_CONFIG_DATA = String.format("%sConfigData.json", JAVA_RESOURCES);
}
