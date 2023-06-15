package utils.data;

import aquality.selenium.elements.interfaces.ILabel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import enums.ProjectTestsModelEnums;
import models.ProjectTestsModel;
import org.testng.Reporter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ModelUtils {

    private static final String emptyTimeValue = "";
    private static final String nullValue = "null";

    public static ArrayList<ProjectTestsModel> readResponseAsModel(String responseBody) {
        try {
            Gson gson = new Gson();
            Type userListType = new TypeToken<ArrayList<ProjectTestsModel>>() {
            }.getType();
            return gson.fromJson(responseBody, userListType);
        } catch (Exception e) {
            Reporter.log("failed to sort the response", true);
        }
        return null;
    }

    public static List<ProjectTestsModel> setTestRecords(List<List<ILabel>> tableRaws) {
        List<ProjectTestsModel> UItestResults = new ArrayList<>();
        for (int i = 0; i < tableRaws.size(); i++) {
            ProjectTestsModel rawModel = new ProjectTestsModel();
            rawModel.setName(tableRaws.get(i).get(ProjectTestsModelEnums.NAME.index).getText());
            rawModel.setMethod(tableRaws.get(i).get(ProjectTestsModelEnums.METHOD.index).getText());
            rawModel.setStatus(tableRaws.get(i).get(ProjectTestsModelEnums.STATUS.index).getText());
            rawModel.setStartTime(tableRaws.get(i).get(ProjectTestsModelEnums.START_TIME.index).getText());
            if (tableRaws.get(i).get(ProjectTestsModelEnums.END_TIME.index).getText().equals(emptyTimeValue)) {
                rawModel.setEndTime(nullValue);
            } else {
                rawModel.setEndTime(tableRaws.get(i).get(ProjectTestsModelEnums.END_TIME.index).getText());
            }
            rawModel.setDuration(tableRaws.get(i).get(ProjectTestsModelEnums.DURATION.index).getText());
            UItestResults.add(rawModel);
        }
        return UItestResults;
    }
}
