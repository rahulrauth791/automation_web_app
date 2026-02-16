package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.*;
import utils.BaseClass;

public class OnlineTest extends BaseClass{
    
    LoanApply loanApply;
    OtpVerficationPage otpPage;
    PrepromoterPage prepromoterPage;
    PromoterPage promoterPage;
    BusinessDetailsPage businessPage;
    OfferPage offerPage;
    ResidenceAddress residenceAddressPage;
    BusinessAddressPage businessAddressPage;
    Kyc kyc;
    
    
     @BeforeClass
        public void setup() {
            initializeDriver();
            // use ThreadLocal driver via getDriver()
            loanApply = new LoanApply(getDriver());
            otpPage = new OtpVerficationPage(getDriver());
            prepromoterPage = new PrepromoterPage(getDriver());
            promoterPage = new PromoterPage(getDriver());
            businessPage = new BusinessDetailsPage(getDriver());
            offerPage = new OfferPage(getDriver());
            residenceAddressPage = new ResidenceAddress(getDriver());
            businessAddressPage = new BusinessAddressPage(getDriver());
            kyc = new Kyc(getDriver());
            
        }
     
     @Test
         public void onlineJourney() {
         loanApply.applynow("50 - 1Cr","Rahul Routh", "9000090001", "rahulrauth@indifi.com");
         otpPage.submit("1234");
         prepromoterPage.submit("BEVPS0897B");
         promoterPage.submit("12-09-1991","male","121001");
         businessPage.submit();	
         offerPage.submit();
         residenceAddressPage.submit("hghghgh", "hgftttdtdt", "rented");
         businessAddressPage.submit();
         String filePath = "/Users/rahulrouth/rahulrauth791/automation_web_app/src/test/java/resources/pan_dummy.png";
         kyc.submit(filePath, filePath);
         
     }
}
