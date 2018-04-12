package Native.uitests;

import frameworkInfra.testbases.SetupTestBase;
import org.testng.annotations.Test;

public class SetupTests extends SetupTestBase {

    @Test(testName = "Test")
    public void test(){
        driver.findElementByName("Next >").click();
        driver.findElementByName("Next >").click();
        driver.findElementByName("I accept the terms of the license agreement").click();
        driver.findElementByName("Next >").click();
        driver.findElementByName("Install new Coordinator component").click();
        driver.findElementByName("Next >").click();
        driver.findElementByName("Next >").click();
        driver.findElementByName("Next >").click();
        driver.findElementByName("Next >").click();
        driver.findElementByName("Next >").click();
        driver.findElementByName("Next >").click();
        driver.findElementByName("Next >").click();
        driver.findElementByName("I didn't receive a license to my mailbox").click();
        driver.findElementByName("Next >").click();
        driver.findElementByName("Display release notes").click();
        driver.findElementByName("Finish").click();
    }
}
