package forms;

import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class NewsFeedPage extends Form {
    public NewsFeedPage() {
        super(By.xpath("//div[@id='feed_recommends']"), "News feed page");
    }
}
