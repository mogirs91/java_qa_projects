package forms;

import aquality.selenium.core.elements.ElementState;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class MainPage extends Form {

    public MainPage() {
        super(By.xpath("//p[@class='start__paragraph']"), "Start Paragraph");
    }

    private ILink nextPageLink = getElementFactory().getLink(By.xpath("//a[@class='start__link']"), "Next page link", ElementState.DISPLAYED);

    public void clickNextPageLink() {
        nextPageLink.click();
    }
}
