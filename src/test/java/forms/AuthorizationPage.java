package forms;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class AuthorizationPage extends Form {
    public AuthorizationPage() {
        super(By.xpath("//input[@name='password']"), "Authorization page");
    }

    private final ITextBox passwordField = getElementFactory().getTextBox(By.xpath("//input[@name='password']"), "Password field", ElementState.DISPLAYED);
    private final IButton submitButton = getElementFactory().getButton(By.xpath("//button[@type='submit']"), "Submit button", ElementState.DISPLAYED);

    public void enterPassword(String password) {
        passwordField.clearAndType(password);
    }

    public void submitButtonClick() {
        submitButton.click();
    }
}
