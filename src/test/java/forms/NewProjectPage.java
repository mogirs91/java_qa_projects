package forms;

import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class NewProjectPage extends Form {

    public NewProjectPage() {
        super(By.xpath("//form[@id='addProjectForm']"), "New project page");
    }

    private final ILabel projectSavedLabel = getElementFactory().getLabel(By.xpath("//div[@class='alert alert-success']"), "Variant label");

    public Boolean isProjectSaved() {
        return projectSavedLabel.getText().contains("saved");
    }

}

