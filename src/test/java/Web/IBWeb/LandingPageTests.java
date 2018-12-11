package Web.IBWeb;

import frameworkInfra.testbases.web.ibSite.LandingPageTestBase;
import org.testng.annotations.Test;
import webInfra.ibWeb.pages.LandingPage;

public class LandingPageTests extends LandingPageTestBase {

    @Test(testName = "Landing Page Registration")
    public void landingPageRegistration(){
        LandingPage lp = new LandingPage("Landing", "Test", mailAddress4, "4illumination", "123456", "israel",
                "cdf", "nonofyourbussines", "5", false);
        landingPageObject.register(lp);
    }
}
