package forms;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class LoginForm extends Form {
    public LoginForm() {
        super(By.xpath("//div[@class='VkIdForm']/form"), "Login Form");
    }

    private final ITextBox loginField = getElementFactory().getTextBox(By.xpath("//input[@name='login']"), "Login field", ElementState.DISPLAYED);
    private final IButton signInButton = getElementFactory().getButton(By.xpath("//div[@class='VkIdForm']/form//button[@type='submit']"), "Sign in button", ElementState.DISPLAYED);

    public void enterLogin(String login) {
        loginField.clearAndType(login);
    }

    public void signInButtonClick() {
        signInButton.click();
    }

}
