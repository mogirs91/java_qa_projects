package forms;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import constants.ConfigDataConstants;
import constants.PathConstants;
import org.openqa.selenium.By;
import utils.data.ConfigUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ProjectTestsPage extends Form {

    public ProjectTestsPage() {
        super(By.xpath("//ol[@class='breadcrumb']//li[text()='Nexage']"), "Nexage breadcrumb");
    }

    private final By testResultsTableRaws = By.xpath("//table[@class='table']//tr");
    private final ILabel homeLabel = getElementFactory().getLabel(By.xpath("//ol[@class='breadcrumb']//a[text()='Home']"), "Home label");
    private final By testResultsTableRecords = By.xpath("/td");
    final String testIdLink = "//table[@class='table']//td/a[contains(@href, 'testInfo?testId=%s')]";

    public List<List<ILabel>> getTestRecords() {
        List<ILabel> tableRaws = getElementFactory().findElements(testResultsTableRaws, ElementType.LABEL, ElementsCount.MORE_THAN_ZERO, ElementState.EXISTS_IN_ANY_STATE);
        List<List<ILabel>> allRawsRecords = new ArrayList<>();
        for (int i = 1; i < tableRaws.size(); i++) {
            allRawsRecords.add(tableRaws.get(i).findChildElements(testResultsTableRecords, ElementType.LABEL));
        }
        return allRawsRecords;
    }

    public void homeLabelClick() {
        homeLabel.click();
    }

    public Boolean testIsDisplayed(String testId) {
        ILabel testIdLabel = getElementFactory().getLabel(By.xpath(String.format(testIdLink, testId)), "Test Id Link");
        testIdLabel.state().waitForExist(Duration.ofSeconds((Integer) ConfigUtils.getData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.EXPLICIT_WAIT)));
        return testIdLabel.state().isExist();
    }
}
