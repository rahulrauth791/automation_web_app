package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResidenceAddress {
	
WebDriver driver;
	
	@FindBy(xpath = "//*[@id=\"promoter_stringAddress2\"]")	
	WebElement addressField2;

	@FindBy(xpath = "//*[@id=\"promoter_stringAddress3\"]")	
	WebElement addressField3;
	
	@FindBy(xpath = "//*[@id=\"promoter_numberPropertyType_control_indicator\"]")	
	WebElement addressownertype;
	
	@FindBy(xpath = "//*[@id=\"continue\"]")	
	WebElement continueButtonField;
	
	@FindBy(css = ".error-message")
    WebElement errorMessage;
	
	public ResidenceAddress(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        
    }
	
	public void enterAddress2(String address2) {
		
		addressField2.clear();
		addressField2.sendKeys(address2);
		System.out.println("Enter Address2");
		
	}
	
    public void enterAddress3(String address3) {
		
		addressField3.clear();
		addressField3.sendKeys(address3);
		System.out.println("Enter Address3");

	}
    
    public void enterOwnerType() {
  	
    	addressownertype.click();
		System.out.println("Enter Address ownertype");

  	}
	
	public void continueButton() {
		continueButtonField.click();	
		System.out.println("Residence Address page submit");

		
	}
	
	public String getErrorMessage() {
        return errorMessage.getText();
    }
	
	public void submit(String address2,String address3,String ownerType ) {
		enterAddress2(address2);
		enterAddress3(address3);
		enterOwnerType();
		continueButton();
	}

}
