package utils.database;

import constants.ConfigDataConstants;
import constants.PathConstants;
import org.testng.Reporter;
import utils.data.ConfigUtils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnection {

    private static Connection connection = null;

    public static Connection getDBConnection() {
        try {
            if (connection == null) {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(
                        (String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.DB_CONNECTION_URL),
                        (String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.DB_USER),
                        (String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.DB_PASSWORD));
            }
        } catch (Exception e) {
            Reporter.log("Failed to create a connection to the DB", true);
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            Reporter.log("Failed to close a connection to the DB", true);
        }
    }
}
