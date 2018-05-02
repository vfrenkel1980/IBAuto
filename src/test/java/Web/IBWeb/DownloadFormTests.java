package Web.IBWeb;

import frameworkInfra.Listeners.TestListener;
import frameworkInfra.testbases.web.IbWebTestBase;

import frameworkInfra.utils.MailService;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import webInfra.RestCalls.Get.GetIsMailRegistered;
import webInfra.ibWeb.downloadPage.RegistrationForm;


@Listeners(TestListener.class)
public class DownloadFormTests extends IbWebTestBase{

    @Test(testName = "Windows Registration")
    public void windowsRegistration(){
        String mailSubject;
        RegistrationForm rf = new RegistrationForm("Win", "User", mailAddress, "4illumination",
                "555954","united states", "alaska", "IB", "MOHA", "other",
                "KING", false, true, false, true, false);
        downloadPage.createNewFreeDevWinAccount(rf);
        Assert.assertTrue(GetIsMailRegistered.isMailRegistered(mailAddress));
        mailSubject = MailService.checkMail(host, mailAddress, password);
        Assert.assertEquals(mailSubject, "Sandbox: Your IncrediBuild Download and License Files");
        MailService.deleteMail(host, mailAddress, password);
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
