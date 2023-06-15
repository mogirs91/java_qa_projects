package forms;

import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class PersonalDetailsPage extends Form {
    public PersonalDetailsPage() {
        super(By.xpath("//div[@class='personal-details__form']"), "Personal Details Form");
    }
}
