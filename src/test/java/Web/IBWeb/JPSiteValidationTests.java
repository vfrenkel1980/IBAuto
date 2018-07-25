package Web.IBWeb;

import frameworkInfra.testbases.web.ibSite.JPSiteValidationsTestBase;
import org.testng.annotations.Test;

public class JPSiteValidationTests extends JPSiteValidationsTestBase {

/*    @Test(testName = "Verify Missing Log In")
    public void verifyMissingLogIn(){
        downloadPageObject.verifyMissingLoginButton();
    }*/

    @Test(testName = "Verify Missing Log In")
    public void verifyMissingPurchaseOnlineLink(){
        downloadPageObject.verifyMissingStoreLink();
    }
}
