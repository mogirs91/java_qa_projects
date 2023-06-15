package tests;

import constants.PathConstants;
import constants.TestDataConstants;
import enums.TestTableEnums;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.data.ConfigUtils;
import utils.data.ModelUtils;
import utils.database.DBUtils;

import java.util.ArrayList;

public class ProcessingOfDataTest {

    @Test
    public void ProcessingOfTestDataTest() {
        DBUtils.insert(ModelUtils.getNewSession());
        DBUtils.insert(ModelUtils.getNewProject());
        DBUtils.insert(ModelUtils.getNewAuthor());
        utils.tables.Test table = ModelUtils.getNewTestEmulated();
        ArrayList<Integer> randomIds = ModelUtils.getRandomIds(table.getClass(),
                TestTableEnums.TEST_TABLE_ID_INDEX.index);
        DBUtils.updateRandomIds(table, randomIds);
        Assert.assertTrue(DBUtils.isColumnValueAsExpected(table, randomIds, TestTableEnums.UPDATE_COLUMN_INDEX.index, table.getAuthorId().toString()), "information has NOT been updated");
        DBUtils.deleteRandomIds(table, randomIds);
        Assert.assertTrue(DBUtils.isColumnValueAsExpected(table, randomIds, TestTableEnums.TEST_TABLE_ID_INDEX.index, (String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.DELETE_EXPECTED_VALUE)), "The records have NOT been deleted.");
    }
}
