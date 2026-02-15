package tests;

import pages.LoginPage;

import utils.BaseClass;

import org.openqa.selenium.By;
import org.testng.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class LoginTest extends BaseClass {
    
    LoginPage loginpage;
    
     @BeforeClass
        public void setup() {
            initializeDriver();
            // use ThreadLocal driver via BaseClass.getDriver()
            loginpage = new LoginPage(BaseClass.getDriver());
        }
     
     @Test
     	public void verifyValidLogin() throws InterruptedException {
         loginpage.login("student", "Password123");
         String currentURL = BaseClass.getDriver().getCurrentUrl();
         Assert.assertTrue(currentURL.contains("https://practicetestautomation.com/logged-in-successfully/"),"URL does not contain expected text! Actual URL:" + currentURL);
         Thread.sleep(1000);
         String successMessage = BaseClass.getDriver().findElement(By.id("successMsg")).getText();
         Assert.assertTrue(
             successMessage.contains("Congratulations") || successMessage.contains("successfully logged in"),
             "Expected message not displayed!"
         );
     }
     
     @Test
     	public void verifyInvalidUserName() {
         loginpage.login("wrongusername", "wrongpassword");
         String message = loginpage.getErrorMessage();
         Assert.assertEquals(message,"Invalid credentials", "Error message mismatch!"); 
     }
     
     @Test
     	public void verifyInvalidPassword() {
         loginpage.login("wrongusername", "wrongpassword");
         String message = loginpage.getErrorMessage();
         Assert.assertEquals(message,"Invalid credentials", "Error message mismatch!"); 
     }
     
     @AfterClass
        public void tearDown() {
            quitDriver();
        }

}
