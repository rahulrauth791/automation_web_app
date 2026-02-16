
package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PromoterPage {
WebDriver driver;
	
	@FindBy(id = "dob")
	WebElement dobField;
	
	@FindBy(id = "promoter_numberPostalCode")
	WebElement postalCodeField;
	

	@FindBy(xpath = "//*[@id=\"coApplicant_enumGender_formField\"]/ul/li/div[1]/span[1]/input")	
	WebElement genderField;
	
	@FindBy(xpath = "//*[@id=\"continue\"]")	
	WebElement continueField;
	
	
	@FindBy(css = ".error-message")
    WebElement errorMessage;
	
	public PromoterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
	
	public void enterDob(String dob) {
		dobField.clear();
		dobField.sendKeys(dob);
		System.out.println("Entered Dob");
	}
	
	public void enterGender(String gender) {
		genderField.click();
		System.out.println("Entered gender");
	}
	
	public void enterPostalCode(String postalCode) {
		postalCodeField.clear();
		postalCodeField.sendKeys(postalCode);
		System.out.println("Entered postalcode");
	}
	
	public void continueButton() {
		continueField.click();	
		System.out.println("Promoter Page submitted");
	}
	
	public String getErrorMessage() {
        return errorMessage.getText();
    }
	
	public void submit(String dob,String gender,String postalcode ) {
		enterDob(dob);
		enterGender(gender);
		enterPostalCode(postalcode);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		continueButton();
	}

}
