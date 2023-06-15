package forms;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ICheckBox;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import utils.waitings.WaitingUtils;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class InterestsPage extends Form {
    public InterestsPage() {
        super(By.xpath("//div[@class='avatar-and-interests-page']"), "Interests Form");
    }

    private static final Random rand = new SecureRandom();

    private ICheckBox unselectAllBox = getElementFactory().getCheckBox(By.xpath("//label[@for='interest_unselectall']/span[@class='checkbox__box']"), "Terms checkbox", ElementState.DISPLAYED);

    private ILabel uploadWindowLabel = getElementFactory().getLabel(By.xpath("//a[@class='avatar-and-interests__upload-button']"), "Upload window", ElementState.DISPLAYED);

    private By interestsBoxList = By.xpath("//label[not(contains(@for, 'selectall'))]/span[@class='checkbox__box']");

    private IButton nextButton = getElementFactory().getButton(By.xpath("//button[contains(text(), 'Next')]"), "Next button", ElementState.DISPLAYED);

    public void uncheckUnselectAllBox() {
        unselectAllBox.check();
    }

    public void uploadClick() {
        uploadWindowLabel.click();
    }

    public void selectRandomInterests(int numberOfElements) {
        Random rand = new Random();
        List<ICheckBox> givenList = getElementFactory().findElements(interestsBoxList, ElementType.CHECKBOX, ElementsCount.MORE_THAN_ZERO, ElementState.EXISTS_IN_ANY_STATE);
        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(givenList.size());
            ICheckBox randomElement = givenList.get(randomIndex);
            randomElement.check();
            givenList.remove(randomIndex);
        }
    }

    public void clickNextButton() {
        WaitingUtils.waitUntilElementIsVisible("//div[@class='avatar-and-interests__avatar-image']");
        nextButton.click();
    }
}