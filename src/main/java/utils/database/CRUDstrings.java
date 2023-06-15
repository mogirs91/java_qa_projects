package utils.database;

import constants.PathConstants;
import constants.TestDataConstants;
import enums.TestTableEnums;
import org.apache.commons.lang3.StringUtils;
import org.testng.Reporter;
import utils.data.ConfigUtils;

import java.sql.ResultSet;

public class CRUDstrings {

    private static final String select = "SELECT";
    private static final String insertInto = "INSERT INTO";
    private static final String update = "UPDATE";
    private static final String delete = "DELETE";
    private static final String from = "FROM";
    private static final String where = "WHERE";
    private static final String columnNameSQL = "COLUMN_NAME";
    private static final String informationSchemaColumns = "INFORMATION_SCHEMA.COLUMNS";
    private static final String tableNameSQL = "TABLE_NAME";
    private static final String and = "and";
    private static final String tableSchema = "TABLE_SCHEMA";
    private static final String ordinalPosition = "Ordinal_Position";
    private static final String values = "values";
    private static final String set = "SET";
    private static final String id = "id = ?";
    private static final String tableNameSplitSeparator = "tables.";
    private static final String insertFirstExpressionTarget = " id, ";
    private static final String insertFirstExpressionReplace = " ";
    private static final String insertFirstExpressionAdd = ", id";
    private static final String insertMatchSubstring = "id";
    private static final String insertMatchContains = "_id";
    private static final String insertMatchTarget = "id, ";
    private static final String insertMatchReplacement = "";
    private static final String insertSecondExpressionRepeat = "?,";
    private static final String updateMatchSubstring = "id";
    private static final String updateMatchContains = "_id";
    private static final String updateFirstRequestTarget = "id = ?,";
    private static final String updateFirstRequestReplace = "";
    private static final String updateSecondRequestTarget = " id = ?,";
    private static final String updateSecondRequestReplace = "";
    private static final String deleteString = "%s %s %s.%s %s %s";
    private static final String selectString = "%s %s %s %s.%s %s %s";
    private static final String selectNoConditionString = "%s %s %s %s.%s";
    private static final String selectColumnNamesString = "%s %s %s %s %s %s = '%s' %s %s = '%s'";
    private static final String selectColumnIndexString = "%s %s %s %s %s %s ='%s' %s %s = '%s'";
    private static final String insertRequestInitialExp = "";
    private static final String insertRequestExp = "%s %s, ";
    private static final String insertString = "%s %s (%s) %s (%s)";
    private static final String updateRequestInitialExp = "";
    private static final String updateRequestExp = "%s %s = ?, ";
    private static final String updateString = "%s %s %s %s %s %s";

    public static <T> String delete(Class<T> tClass, String condition, String database) {
        try {
            String tablename = tClass.getName()
                    .split(tableNameSplitSeparator)[1].toLowerCase();
            return String.format(deleteString, delete, from, database, tablename, where, condition);
        } catch (Exception e) {
            Reporter.log("failed to generate 'delete' string");
        }
        return null;
    }

    public static <T> String select(Class<T> tClass, String columnName, String condition, String database) {
        try {
            String tablename = tClass.getName()
                    .split(tableNameSplitSeparator)[1].toLowerCase();
            return String.format(selectString, select, columnName, from, database, tablename, where, condition);
        } catch (Exception e) {
            Reporter.log("failed to generate 'select' string");
        }
        return null;
    }

    public static <T> String select(Class<T> tClass, String columnName, String database) {
        try {
            String tablename = tClass.getName()
                    .split(tableNameSplitSeparator)[1].toLowerCase();
            return String.format(selectNoConditionString, select, columnName, from, database, tablename);
        } catch (Exception e) {
            Reporter.log("failed to generate 'select' string");
        }
        return null;
    }

    public static <T> String selectColumnNames(Class<T> tClass, String database) {
        try {
            String tablename = tClass.getName()
                    .split(tableNameSplitSeparator)[1].toLowerCase();
            return String.format(selectColumnNamesString, select, columnNameSQL, from, informationSchemaColumns, where, tableNameSQL, tablename, and, tableSchema, database);
        } catch (Exception e) {
            Reporter.log("failed to get column names");
        }
        return null;
    }

    public static <T> String selectColumnIndex(Class<T> tClass, String columnName) {
        try {
            String tablename = tClass.getName()
                    .split(tableNameSplitSeparator)[1].toLowerCase();
            return String.format(selectColumnIndexString, select, ordinalPosition, from, informationSchemaColumns, where, tableNameSQL, tablename, and, columnNameSQL, columnName);
        } catch (Exception e) {
            Reporter.log("failed to get column names");
        }
        return null;
    }

    public static <T> String insert(Class<T> tClass) {
        try {
            ResultSet result = DBUtils.executeQuery(CRUDstrings.selectColumnNames(tClass,
                    (String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.DATABASE_NAME)));
            int len = tClass.getDeclaredFields().length;
            String request = insertRequestInitialExp;
            while (result.next()) {
                request = String.format(insertRequestExp, request, result.getString(TestTableEnums.TEST_TABLE_ID_INDEX.index));
            }
            String tablename = tClass.getName().split(tableNameSplitSeparator)[1].toLowerCase();
            String expression = StringUtils.chop(StringUtils.chop(request))
                    .replace(insertFirstExpressionTarget, insertFirstExpressionReplace) + insertFirstExpressionAdd;
            if (StringUtils.countMatches(expression, insertMatchSubstring) > 1 && !expression
                    .contains(insertMatchContains)) {
                expression = expression
                        .replace(insertMatchTarget, insertMatchReplacement);
            }
            String secondExpression = StringUtils.chop(StringUtils.repeat(insertSecondExpressionRepeat, len));
            return String.format(insertString, insertInto, tablename, expression, values, secondExpression);
        } catch (Exception e) {
            Reporter.log("failed to generate 'insert' string");
        }
        return null;
    }

    public static <T> String update(Class<T> tClass) {
        try {
            ResultSet result = DBUtils.executeQuery(CRUDstrings.selectColumnNames(tClass, (String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.DATABASE_NAME)));
            String request = updateRequestInitialExp;
            while (result.next()) {
                request = String.format(updateRequestExp, request, result.getString(TestTableEnums.TEST_TABLE_ID_INDEX.index));
            }
            String tablename = tClass.getName().split(tableNameSplitSeparator)[1].toLowerCase();
            if (StringUtils.countMatches(request, updateMatchSubstring) > 0 && !request
                    .contains(updateMatchContains)) {
                request = StringUtils.chop(StringUtils.chop(request))
                        .replace(updateFirstRequestTarget, updateFirstRequestReplace);
            } else {
                request = StringUtils.chop(StringUtils.chop(request))
                        .replace(updateSecondRequestTarget, updateSecondRequestReplace);
            }
            return String.format(updateString, update, tablename, set, request, where, id);
        } catch (Exception e) {
            Reporter.log("failed to generate 'update' string");
        }
        return null;
    }

}
