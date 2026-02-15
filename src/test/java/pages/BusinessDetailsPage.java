package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class BusinessDetailsPage {
WebDriver driver;
    
    @FindBy(xpath = "//*[@id=\"continue\"]")	
    WebElement continueField;
    
    
    @FindBy(css = ".error-message")
    WebElement errorMessage;
    
    public BusinessDetailsPage(WebDriver driver) {
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
        System.out.println("Business Page submitted");
    }
    
    public String getErrorMessage() {
        return errorMessage.getText();
    }
    
    public void submit( ) {
        Map<String, WebElement> empty = checkBusinessDetailsPresence();
		System.out.println("Empty fields found: " + empty.keySet());
        // caller can now fill these before continuing if desired
		// For demo, we will just print them and
		
        continueButton();
    }

    // Added method: checks for prefilled values for inputs/textareas/selects in the same form as continueField
    // Returns a map of label -> WebElement for fields that are empty so caller can set values.
    public Map<String, WebElement> checkBusinessDetailsPresence() {
        Map<String, WebElement> emptyFields = new HashMap<>();
        try {
            List<WebElement> fields;
            // try to find ancestor form of continue button
            WebElement form = null;
            try {
                form = continueField.findElement(By.xpath("ancestor::form"));
            } catch (Exception e) {
                form = null;
            }
            if (form != null) {
                fields = form.findElements(By.xpath(".//input|.//textarea|.//select"));
            } else {
                System.out.println("Ancestor form not found; scanning whole page for fields.");
                fields = driver.findElements(By.xpath("//input|//textarea|//select"));
            }
    
            if (fields == null || fields.isEmpty()) {
                System.out.println("No form fields found to inspect.");
                return emptyFields;
            }
    
            for (WebElement f : fields) {
                String tag = f.getTagName() != null ? f.getTagName() : "unknown";
                String id = f.getAttribute("id") != null ? f.getAttribute("id") : "";
                String name = f.getAttribute("name") != null ? f.getAttribute("name") : "";
                String placeholder = f.getAttribute("placeholder") != null ? f.getAttribute("placeholder") : "";
                String label = !id.isEmpty() ? id : (!name.isEmpty() ? name : (!placeholder.isEmpty() ? placeholder : "unnamed"));
    
                String value = "";
                if ("select".equalsIgnoreCase(tag)) {
                    try {
                        WebElement selected = f.findElement(By.xpath(".//option[@selected]"));
                        value = selected.getText();
                    } catch (Exception ex) {
                        value = f.getAttribute("value") != null ? f.getAttribute("value") : "";
                    }
                } else {
                    value = f.getAttribute("value") != null ? f.getAttribute("value") : "";
                }
    
                if (value.trim().isEmpty()) {
                    System.out.println("Field '" + label + "' (" + tag + ") is empty.");
                    emptyFields.put(label, f);
                } else {
                    System.out.println("Field '" + label + "' (" + tag + ") is prefilled with: " + value);
                }
            }
        } catch (Exception ex) {
            System.out.println("checkBusinessDetailsPresence encountered error: " + ex.getMessage());
        }
        return emptyFields;
    }

}
