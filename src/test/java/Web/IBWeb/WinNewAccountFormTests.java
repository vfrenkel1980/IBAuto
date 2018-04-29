package Web.IBWeb;

import frameworkInfra.Listeners.TestListener;
import frameworkInfra.testbases.web.IbWebTestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import webInfra.RestCalls.Get.GetIsMailRegistered;
import webInfra.ibWeb.pageObjects.DownloadPage;

@Listeners(TestListener.class)
public class WinNewAccountFormTests extends IbWebTestBase{

    @Test(testName = "test")
    public void test(){
        downloadPage.createNewFreeDevWinAccount("Mark", "Zvu", "a.ad2@a.com", "4illumination", "555954",
                "united states", "IB", "alaska", "MOHA", "other", "KING", false, true, false, true, false);
        Assert.assertTrue(GetIsMailRegistered.isMailRegistered("a.ad2@a.com"));

    }

}
