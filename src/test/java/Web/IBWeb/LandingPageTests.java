package Web.IBWeb;

import frameworkInfra.testbases.web.LandingPageTestBase;
import org.testng.annotations.Test;
import webInfra.ibWeb.downloadPage.LandingPage;

public class LandingPageTests extends LandingPageTestBase {

    @Test(testName = "Registration")
    public void registration(){
        LandingPage lp = new LandingPage("Landing", "Test", "automation+4@incredibuild.com", "4illumination", "123456", "israel",
                "cdf", "nonofyourbussines", "5", false);
        landingPageObject.register(lp);
    }
}
