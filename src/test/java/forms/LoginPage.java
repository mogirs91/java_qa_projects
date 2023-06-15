package forms;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ICheckBox;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import java.util.List;

public class LoginPage extends Form {

    public LoginPage() {
        super(By.xpath("//div[@class='login-form']"), "Login Form");
    }

    private ITextBox passwordField = getElementFactory().getTextBox(By.xpath("//input[@placeholder='Choose Password']"), "Password field", ElementState.DISPLAYED);

    private ITextBox emailField = getElementFactory().getTextBox(By.xpath("//input[@placeholder='Your email']"), "Email field", ElementState.DISPLAYED);

    private ITextBox domainField = getElementFactory().getTextBox(By.xpath("//input[@placeholder='Domain']"), "Domain field", ElementState.DISPLAYED);

    private ILabel domainExtensionLablel = getElementFactory().getLabel(By.xpath("//div[@class='dropdown dropdown--gray']"), "Domain label dropdown", ElementState.DISPLAYED);

    private By domainExtensionsList = By.xpath("//div[contains(@class, 'dropdown__list-item')]");

    private ICheckBox termsNotAcceptedBox = getElementFactory().getCheckBox(By.xpath("//span[@class='checkbox__box']"), "Terms checkbox", ElementState.DISPLAYED);

    private IButton nextButton = getElementFactory().getButton(By.xpath("//a[contains(@class, 'button') and contains(text(), 'Next')]"), "Next button", ElementState.DISPLAYED);

    private IButton hideButton = getElementFactory().getButton(By.xpath("//button[contains(@class, 'send-to-bottom-button')]"), "Hide button", ElementState.DISPLAYED);

    private ILabel helpForm = getElementFactory().getLabel(By.xpath("//div[@class='help-form is-hidden']"), "Help form hidden", ElementState.DISPLAYED);

    private IButton acceptCookiesButton = getElementFactory().getButton(By.xpath("//button[contains(@class, 'solid button') and text()='Not really, no']"), "Accept cookies", ElementState.DISPLAYED);

    private ILabel cookiesMessageLabel = getElementFactory().getLabel(By.xpath("//p[@class='cookies__message']"), "Cookies message", ElementState.EXISTS_IN_ANY_STATE);

    private ILabel timerMessageLabel = getElementFactory().getLabel(By.xpath("//div[contains(@class, 'timer')]"), "Timer message", ElementState.DISPLAYED);

    private void clickOtherDomain() {
        for (ILabel i : getDomainExtensions()) {
            if (i.getText().equals("other")) {
                i.click();
            }
        }
    }

    private List<ILabel> getDomainExtensions() {
        domainExtensionLablel.click();
        return getElementFactory().findElements(domainExtensionsList, ElementType.LABEL, ElementsCount.MORE_THAN_ZERO, ElementState.EXISTS_IN_ANY_STATE);
    }

    public void enterPassword(String password) {
        passwordField.clearAndType(password);
    }

    public void enterEmailAndDomain(String useremail) {
        boolean domainExtensionFlag = false;
        emailField.clearAndType(useremail.split("@")[0]);
        domainField.clearAndType(useremail.split("@")[1].split("\\.")[0]);
        for (ILabel i : getDomainExtensions()) {
            if (i.getText().substring(1).equals(useremail.split("@")[1].split("\\.")[1])) {
                i.click();
                domainExtensionFlag = true;
                break;
            }
        }
        if (!domainExtensionFlag)
            clickOtherDomain();
    }

    public void uncheckTermsNotAcceptedBox() {
        termsNotAcceptedBox.check();
    }

    public void clickNextButton() {
        nextButton.click();
    }

    public void clickHideButton() {
        hideButton.click();
    }

    public Boolean isHelpFormHidden() {
        return helpForm.state().isExist();
    }

    public void clickAcceptCookies() {
        acceptCookiesButton.click();
    }

    public Boolean isCookiesMessageHidden() {
        return !cookiesMessageLabel.state().isExist();
    }

    public String timerValue() {
        return timerMessageLabel.getText();
    }
}
