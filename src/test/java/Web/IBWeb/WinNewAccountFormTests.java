package Web.IBWeb;

import frameworkInfra.Listeners.TestListener;
import frameworkInfra.testbases.web.IbWebTestBase;
import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import webInfra.ibWeb.pageObjects.DownloadPage;

@Listeners(TestListener.class)
public class WinNewAccountFormTests extends IbWebTestBase{

    @Test(testName = "test")
    public void test(){
        downloadPage.createNewFreeDevWinAccount("Mark", "Zvu", "mark.zvuluni@incredibuild.com", "4illumination", "as",
                "united states", "IB", "alaska", "MOHA", "other", "KING", false, true, false, true, false);
        System.out.println("");

    }

}
