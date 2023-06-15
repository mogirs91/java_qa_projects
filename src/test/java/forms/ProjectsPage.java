package forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class ProjectsPage extends Form {

    public ProjectsPage() {
        super(By.xpath("//div[@class='list-group']"), "Projects page");
    }

    private final ILabel variantLabel = getElementFactory().getLabel(By.xpath("//footer[@class='footer']//span"), "Variant label");
    private final ILabel addProjectButton = getElementFactory().getLabel(By.xpath("//div[@class='panel-heading']/a"), "add Project button");
    private final ITextBox projectNameTextBox = getElementFactory().getTextBox(By.xpath("//form[@id='addProjectForm']//input[@id='projectName']"), "Project name text field");
    private final IButton submitProjectButton = getElementFactory().getButton(By.xpath("//button[@type='submit']"), "Submit project button");

    private final String newProjectLocator = "//div[@class='list-group']/a[text()='%s']";
    private final String projectLinkSplitExpression = "=";
    private final String variantSplit = " ";

    public String getVariantLabel() {
        return variantLabel.getText().split(variantSplit)[1];
    }

    public void projectNameClick(String projectName) {
        getElementFactory().getLabel(By.xpath(String.format(newProjectLocator, projectName)), "New project label").click();
    }

    public String getProjectId(String projectName) {
        return getElementFactory().getLink(By.xpath(String.format(newProjectLocator, projectName)), "New project label").getHref().split(projectLinkSplitExpression)[1];
    }

    public void addProjectButtonLink() {
        addProjectButton.getMouseActions().click();
    }

    public void enterPojectName(String projectName) {
        projectNameTextBox.clearAndType(projectName);
    }

    public void submitProjectButtonClick() {
        submitProjectButton.click();
    }

    public Boolean doesNewProjectExist(String projectName) {
        return getElementFactory().getLabel(By.xpath(String.format(newProjectLocator, projectName)), "New project label").state().isExist();
    }
}
