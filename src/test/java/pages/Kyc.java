package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.io.File;
import org.apache.commons.io.FileUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Kyc {
	
	private WebDriver driver;

	@FindBy(xpath = "/html/body/section/div/div/div/div/div[1]/div[1]/form/section/div/div[2]/div/div[2]/div[2]/button")	
	WebElement selectUploadField;
	
	@FindBy(xpath = "//*[@id=\"upload_docs_promoterphotograph\"]")	
	WebElement photographField;
	
	@FindBy(xpath = "//*[@id=\"collapseOne_promoter_dropdownphotograph\"]")	
	WebElement photoDropDownField;
	
	@FindBy(xpath = "//*[@id=\"upload_docs_promoterovd\"]")	
	WebElement nationalDField;
	
	@FindBy(xpath = "//*[@id=\"collapseOne_promoter_dropdownovd\"]")	
	WebElement ovdDropDownField;
	
	@FindBy(xpath = "//*[@id=\"control_172_true\"]")	
	WebElement currenAddressField;
	
	@FindBy(css = ".error-message")
    WebElement errorMessage;
	
	public  Kyc(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

	// public void uploadPhotograph(String photofilepath) {
	// 	try {
	// 		Thread.sleep(2000);
	// 	} catch (InterruptedException e) {
	// 		e.printStackTrace();
	// 	}
	// 	//photoDropDownField.click();
	// 	photographField.click();
	// 	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	// 	WebElement fileInput = wait.until(
	// 		    ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']"))
	// 		);
	// 	fileInput.sendKeys(photofilepath);	
		
	// 	System.out.println("uploadPhotograph");
	// }
    public void uploadPhotograph(String photofilepath) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    
        WebElement fileInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']"))
        );
    
        // If input is hidden (very common), make it visible
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].style.display='block';", fileInput);
    
        fileInput.sendKeys(photofilepath);
    
        System.out.println("Photograph uploaded successfully");
    }

    public void pressEscapeMac() {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "osascript",
                    "-e",
                    "tell application \"System Events\" to key code 53"
            );
    
            Process process = pb.start();
            process.waitFor();
    
            System.out.println("ESC key pressed using osascript");
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
	
    private void takeScreenshot(String stepName) {
        try {
            // Create timestamp for unique filename
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = "screenshot_" + stepName + "_" + timestamp + ".png";
            
            // Take screenshot
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            
            // Save to project directory
            File destFile = new File("test-output/screenshots/" + fileName);
            FileUtils.copyFile(scrFile, destFile);
            
            System.out.println("📸 Screenshot saved: " + destFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("❌ Failed to take screenshot: " + e.getMessage());
        }
    }

	// public void uploadPhotograph(String photofilepath) throws Exception {
    //     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
    //     // Create screenshots directory if it doesn't exist
    //     new File("test-output/screenshots").mkdirs();
        
    //     // Take screenshot before starting
    //     takeScreenshot("before_upload");
    //     // No need to click or wait for photoDropDownField, it is open by default
        
    //         // 1. Select document type from dropdown if present
    //         try {
    //             WebElement docTypeDropdown = driver.findElement(By.id("select_photograph"));
    //             docTypeDropdown.click();
    //             WebElement option = driver.findElement(By.xpath("//option[contains(text(),'Photograph (jpg/png)')]"));
    //             option.click();
    //         } catch (Exception e) {
    //             System.out.println("Dropdown for document type not found or already selected.");
    //         }

    //         // 2. Set required radio button (address confirmation)
    //         try {
    //             WebElement addressRadio = driver.findElement(By.id("control_112_true"));
    //             if (!addressRadio.isSelected()) {
    //                 addressRadio.click();
    //             }
    //         } catch (Exception e) {
    //             System.out.println("Address confirmation radio button not found or already selected.");
    //         }

    //         // Target the hidden file input by id
    //         WebElement fileInput = driver.findElement(By.id("documentation_stage_file_upload"));
    //         if (driver instanceof JavascriptExecutor) {
    //             JavascriptExecutor js = (JavascriptExecutor) driver;
    //             js.executeScript(
    //                 "arguments[0].style.display = 'block'; " +
    //                 "arguments[0].style.visibility = 'visible'; " +
    //                 "arguments[0].removeAttribute('disabled'); " +
    //                 "arguments[0].removeAttribute('readonly'); ",
    //                 fileInput
    //             );
    //             js.executeScript("arguments[0].scrollIntoView(true);", fileInput);
    //             takeScreenshot("after_prepare_input");
    //         }
    //         Thread.sleep(500);
    //         try {
    //             System.out.println("🔍 Attempting to upload file: " + photofilepath);
    //             System.out.println("📁 File exists: " + new File(photofilepath).exists());
    //             fileInput.sendKeys(photofilepath);
    //             takeScreenshot("after_sendkeys");
    //             // Click the upload button after attaching the file
    //             WebElement uploadButton = driver.findElement(By.id("upload_docs_promoterphotograph"));
    //             ((JavascriptExecutor) driver).executeScript("arguments[0].click();", uploadButton);
    //             Thread.sleep(2000);
    //             takeScreenshot("after_wait");
    //             System.out.println("✅ File upload completed: " + photofilepath);
    //         } catch (Exception e) {
    //             takeScreenshot("upload_error");
    //             System.out.println("❌ File upload failed: " + e.getMessage());
    //             throw e;
    //         }
	// }



	
//	public void uploadNationalId(String ovdeFilepath) {
//		System.out.println("uploadNationalId");
//		ovdDropDownField.click();
//		currenAddressField.click();
//		nationalDField.sendKeys(ovdeFilepath);	
//	}
	
	public String getErrorMessage() {
        return errorMessage.getText();
    }
	
	public void submit(String photofilepath,String ovdFilePath) {
		//selectManualUpload();
		try {
			uploadPhotograph(photofilepath);
            pressEscapeMac();

		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void selectManualUpload() {
		selectUploadField.click();
		
	}

}
