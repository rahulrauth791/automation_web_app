package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	WebDriver driver;
	
	@FindBy(id = "username")
	WebElement usernameField;
	
	@FindBy(id = "password")
	WebElement passowrdField;
	
	@FindBy(xpath = "//*[@id=\"submit\"]")	
	WebElement loginButton;
	
	@FindBy(css = ".error-message")
    WebElement errorMessage;
	
	public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
	public void enterUserName(String username) {
		usernameField.clear();
		usernameField.sendKeys(username);
	}
	
	public void enterPassword(String password) {
		passowrdField.clear();
		passowrdField.sendKeys(password);
	}
	
	public void clickLogin() {
		loginButton.click();	
	}
	
	public String getErrorMessage() {
        return errorMessage.getText();
    }
	
	public void login(String username , String password) {
		enterUserName(username);
		enterPassword(password);
		clickLogin();
	}
	

}
