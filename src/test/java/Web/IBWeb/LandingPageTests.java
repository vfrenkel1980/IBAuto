package Web.IBWeb;

import frameworkInfra.testbases.web.LandingPageTestBase;
import org.testng.annotations.Test;
import webInfra.ibWeb.pages.LandingPage;

public class LandingPageTests extends LandingPageTestBase {

    @Test(testName = "Registration")
    public void registration(){
        LandingPage lp = new LandingPage("Landing", "Test", mailAddress3, "4illumination", "123456", "israel",
                "cdf", "nonofyourbussines", "5", false);
        landingPageObject.register(lp);
    }
}
