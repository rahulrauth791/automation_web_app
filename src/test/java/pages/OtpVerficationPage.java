package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OtpVerficationPage {
WebDriver driver;
	
	@FindBy(id = "otp")
	WebElement otpField;
	

	@FindBy(xpath = "//*[@id=\"otpFormSubmitButton\"]")	
	WebElement otpFormSubmitButton;
	
	@FindBy(css = ".error-message")
    WebElement errorMessage;
	
	public OtpVerficationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
	protected void enterOTP(String otp) {
		otpField.clear();
		otpField.sendKeys(otp);
		 System.out.println("Entered OTP");
	}
	
	
	protected void verifyPhone() {
		otpFormSubmitButton.click();	
		System.out.println("Entered OTP Verfied");
	}
	
	protected String getErrorMessage() {
        return errorMessage.getText();
    }
	
	public void submit(String otp ) {
		enterOTP(otp);
		verifyPhone();
	}
}
