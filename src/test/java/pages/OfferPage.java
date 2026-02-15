package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OfferPage {
	
WebDriver driver;
	

	@FindBy(xpath = "//*[@id=\"continue_offer_btn\"]")	
	WebElement continueField;
	
	
	@FindBy(css = ".error-message")
    WebElement errorMessage;
	
	public  OfferPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
	
	public void continueButton() {
		continueField.click();	
		System.out.println("Offer Page submitted");
	}
	
	public String getErrorMessage() {
        return errorMessage.getText();
    }
	
	public void submit( ) {
		continueButton();
	}

}
