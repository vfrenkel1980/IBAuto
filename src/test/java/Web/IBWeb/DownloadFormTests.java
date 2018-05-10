package Web.IBWeb;

import frameworkInfra.Listeners.TestListener;
import frameworkInfra.testbases.web.DownloadPageTestBase;

import frameworkInfra.utils.MailService;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import webInfra.RestCalls.Get.GetIsMailRegistered;
import webInfra.ibWeb.downloadPage.RegistrationForm;


@Listeners(TestListener.class)
public class DownloadFormTests extends DownloadPageTestBase{

    @Test(testName = "Windows Registration")
    public void windowsRegistration(){
        String mailSubject;
        RegistrationForm rf = new RegistrationForm("Win", "User", mailAddress, "4illumination",
                "555954","united states", "alaska", "IB", "MOHA", "other",
                "KING", false, true, false, true, false);
        downloadPageObject.createNewFreeDevWinAccount(rf);
        Assert.assertTrue(GetIsMailRegistered.isMailRegistered(mailAddress));
        mailSubject = MailService.checkMail(host, mailAddress, password);
        Assert.assertEquals(mailSubject, "Sandbox: Your IncrediBuild Download and License Files");
        MailService.deleteMail(host, mailAddress, password);
    }

    @Test(testName = "Linux Registration")
    public void linuxRegistration(){
        RegistrationForm rf = new RegistrationForm("linux", "User", mailAddress2, "123123",
                "canada", "Alberta","IB","city", "other", "Brain", true, true,
                false,false, true,false,true,true,true,true,false,
                true,false,true,true,true,false,true,false,
                true,true);
        downloadPageObject.registerLinuxUser(rf);
    }

    @Test(testName = "Enterprise Registration")
    public void enterpriseRegistration(){
        RegistrationForm rf = new RegistrationForm("enterprise", "User", mailAddress2, "123123",
                "Israel", "","IB","city", "other", "ballz", true, true,
                false,false, true,true,true,true);
        downloadPageObject.registerEnterpriseUser(rf);
    }


}
