package Web.IBWeb;

import frameworkInfra.Listeners.TestListener;
import frameworkInfra.testbases.web.IbWebTestBase;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import webInfra.RestCalls.Get.GetIsMailRegistered;
import webInfra.ibWeb.downloadPage.RegistrationForm;

@Listeners(TestListener.class)
public class DownloadFormTests extends IbWebTestBase{

    @Test(testName = "Windows Registration")
    public void windowsRegistration(){
        RegistrationForm rf = new RegistrationForm("Win", "User", "a.ad3@a.com", "4illumination",
                "555954","united states", "alaska", "IB", "MOHA", "other",
                "KING", false, true, false, true, false);
        downloadPage.createNewFreeDevWinAccount(rf);
        Assert.assertTrue(GetIsMailRegistered.isMailRegistered("a.ad2@a.com"));
    }

    @Test(testName = "Linux Registration")
    public void linuxRegistration(){
        RegistrationForm rf = new RegistrationForm("linux", "User", "a@aa.com", "123123",
                "canada", "Alberta","IB","city", "other", "Brain", true, true,
                false,false, true,true,true,true,true,true,true,
                true,true,true,true,true,true,true,true,
                true,true);
        downloadPage.registerLinuxUser(rf);
    }

    @Test(testName = "Enterprise Registration")
    public void enterpriseRegistration(){
        RegistrationForm rf = new RegistrationForm("enterprise", "User", "b@bb.com", "123123",
                "Israel", "","IB","city", "other", "ballz", true, true,
                false,false, true,true,true,true);
        downloadPage.registerEnterpriseUser(rf);
    }


}
