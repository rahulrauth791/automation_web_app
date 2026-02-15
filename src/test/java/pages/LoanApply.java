package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class LoanApply {
	
WebDriver driver;
	
	@FindBy(id = "turnover")
	WebElement turnoverField;
	
	@FindBy(id = "name")
	WebElement nameField;
	
	@FindBy(id = "mobile")
	WebElement phoneNumberField;
	
	@FindBy(id = "email")
	WebElement emailField;
	
	@FindBy(xpath = "//*[@id=\"leadFormSubmitButton\"]")	
	WebElement applynowButton;
	
	@FindBy(css = ".error-message")
    WebElement errorMessage;
	
	
	public LoanApply(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void enterTurnover(String turnoverDropdown) {
	
        Select select = new Select(turnoverField);
        select.selectByValue(turnoverDropdown);
        System.out.println("Entered Turnover");
	}
	
	public void enterName(String name) {
		nameField.clear();
		nameField.sendKeys(name);
		System.out.println("Entered Name");
	}
	
	public void enterPhoneNumber(String phone) {
		phoneNumberField.clear();
		phoneNumberField.sendKeys(phone);
		System.out.println("Entered Phone");
	}
	
	public void enterEmail(String email) {
		emailField.clear();
		emailField.sendKeys(email);
		System.out.println("Entered Email");
	}
	
	public void clickAppluNowButton() {
		applynowButton.click();	
		System.out.println("clicked ApplyNow Button");
	}
	
	public void applynow(String turnoverDropdown , String name,String phone,String email) {
		
		enterTurnover(turnoverDropdown);
		enterName(name);
		enterPhoneNumber(phone);
		enterEmail(email);
		clickAppluNowButton();
		
	}
	
	
	
	

}
