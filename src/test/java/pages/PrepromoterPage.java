package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PrepromoterPage {
	
WebDriver driver;
	
	@FindBy(id = "kycDocs_stringPanID")
	WebElement panField;
	

	@FindBy(xpath = "//*[@id=\"continue\"]")	
	WebElement continueButton;
	
	@FindBy(css = ".error-message")
    WebElement errorMessage;
	
	public PrepromoterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
	public void enterPan(String panNumber) {
		panField.clear();
		panField.sendKeys(panNumber);
		System.out.println("Entered Pan");
	}
	
	
	public void fetchDetails() {
		continueButton.click();	
		 System.out.println("Entered submit");
	}
	
	public String getErrorMessage() {
        return errorMessage.getText();
    }
	
	public void submit(String panNumber ) {
		enterPan(panNumber);
		fetchDetails();
	}

}
