package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BusinessAddressPage {
WebDriver driver;
	

	@FindBy(xpath = "//*[@id=\"continue\"]")	
	WebElement continueField;
	
	
	@FindBy(css = ".error-message")
    WebElement errorMessage;
	
	public  BusinessAddressPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
	
	public void continueButton() {
		try {
			Thread.sleep(2000);
		   } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
		continueField.click();	
		System.out.println("Business Address page submit");
		try {
			Thread.sleep(5000);
		   } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }

	}
	
	public String getErrorMessage() {
        return errorMessage.getText();
    }
	
	public void submit() {
	
		continueButton();
	}

}
