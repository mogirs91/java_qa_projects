package utils.database;

import constants.PathConstants;
import constants.TestDataConstants;
import org.testng.Reporter;
import utils.data.ConfigUtils;
import utils.tables.BaseTable;
import utils.tables.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;

public class DBUtils {

    private static final Integer selectColumnIndex = 1;
    private static final String selectColumnName = "*";
    private static final String selectColumnId = "id ='%d'";
    private static final String deleteRandomId = "id = '%s'";

    public static void executeUpdate(String SQLexpression, BaseTable table) {
        try {
            Connection connection = DBconnection.getDBConnection();
            PreparedStatement pstm = connection.prepareStatement(SQLexpression);
            table.sendValues(pstm).executeUpdate();
        } catch (Exception e) {
            Reporter.log("failed to execute update", true);
        }
    }

    public static void executeUpdate(String SQLexpression) {
        try {
            Connection connection = DBconnection.getDBConnection();
            PreparedStatement pstm = connection.prepareStatement(SQLexpression);
            pstm.executeUpdate();
        } catch (Exception e) {
            Reporter.log("failed to execute update", true);
        }
    }

    public static ResultSet executeQuery(String SQLexpression) {
        try {
            Connection connection = DBconnection.getDBConnection();
            PreparedStatement pstm = connection.prepareStatement(SQLexpression);
            return pstm.executeQuery();
        } catch (Exception e) {
            Reporter.log("failed to execute query", true);
        }
        return null;
    }

    public static void insert(BaseTable table) {
        try {
            if (table != null) {
                DBUtils.executeUpdate(CRUDstrings.insert(table.getClass()), table);
            }
        } catch (Exception e) {
            Reporter.log("insert method failure", true);
        }
    }

    public static void update(BaseTable table) {
        try {
            if (table != null) {
                DBUtils.executeUpdate(CRUDstrings.update(table.getClass()), table);
            }
        } catch (Exception e) {
            Reporter.log("update method failure", true);
        }
    }

    public static void delete(BaseTable table, String condition) {
        try {
            if (table != null) {
                DBUtils.executeUpdate(CRUDstrings.delete(table.getClass(), condition, (String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.DATABASE_NAME)));
            }
        } catch (Exception e) {
            Reporter.log("delete method failure", true);
        }
    }

    public static <T> ArrayList<String> selectColumnValues(Class<T> tClass, String columnName) {
        try {
            Integer colunmIndex = null;
            ResultSet colunmIndexSet = DBUtils.executeQuery(CRUDstrings.selectColumnIndex(tClass, columnName));
            while (colunmIndexSet.next()) {
                colunmIndex = colunmIndexSet.getInt(selectColumnIndex);
            }
            ResultSet result = DBUtils.executeQuery(CRUDstrings.select(tClass, columnName, (String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.DATABASE_NAME)));
            ArrayList<String> results = new ArrayList<>();
            while (result.next()) {
                results.add(result.getString(selectColumnIndex));
            }
            return results;
        } catch (Exception e) {
            Reporter.log("failed to get the last id");
        }
        return null;
    }

    public static <T> Integer selectMaxValueInColumn(Class<T> tClass, String columnName) {
        try {
            return Collections.max(DBUtils.selectColumnValues(tClass, columnName)
                    .stream().map(Integer::parseInt).toList());
        } catch (Exception e) {
            Reporter.log("failed to get max value");
        }
        return null;
    }

    public static <T> String selectColumnValueById(Class<T> tClass, Integer id, Integer columnIndex) {
        try {
            ResultSet result = DBUtils.executeQuery(CRUDstrings.select(tClass, selectColumnName, String.format(selectColumnId,id), (String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.DATABASE_NAME)));
            while (result.next())
                return result.getString(columnIndex);
        } catch (Exception e) {
            Reporter.log("failed to get values by id");
        }
        return "";
    }

    public static <T> ArrayList<String> selectAllValuesById(Class<T> tClass, Integer id) {
        int len = tClass.getDeclaredFields().length;
        ArrayList<String> allFieldsById = new ArrayList<>();
        for (int i = 1; i <= len; i++) {
            allFieldsById.add(DBUtils.selectColumnValueById(tClass, id, i));
        }
        return allFieldsById;
    }

    public static void updateRandomIds(BaseTable table, ArrayList<Integer> idList) {
        for (Integer id : idList) {
            DBUtils.update(Test.getValues(DBUtils.selectAllValuesById(table.getClass(), id)));
        }
    }

    public static Boolean isColumnValueAsExpected(Test table, ArrayList<Integer> idList, Integer columnIndex, String expectedValue) {
        for (Integer id : idList) {
            if (!DBUtils.selectColumnValueById(Test.class, id, columnIndex).equals(expectedValue)) {
                return false;
            }
        }
        return true;
    }

    public static void deleteRandomIds(BaseTable table, ArrayList<Integer> idList) {
        for (Integer id : idList) {
            DBUtils.delete(table, String.format(deleteRandomId,id.toString()));
        }
    }
}
