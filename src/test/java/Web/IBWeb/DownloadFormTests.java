package Web.IBWeb;

import frameworkInfra.Listeners.TestListener;
import frameworkInfra.testbases.web.DownloadPageTestBase;

import frameworkInfra.utils.MailService;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import webInfra.RestCalls.Get.GetIsMailRegistered;
import webInfra.ibWeb.pages.RegistrationForm;


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

    @Test(testName = "Verify Existing User", dependsOnMethods = { "windowsRegistration"})
    public void verifyExistingUser(){
        downloadPageObject.clickDownloadButton();
        downloadPageObject.verifyExistingUserMessage(mailAddress);
    }

    @Test(testName = "Validate First Name")
    public void validateFirstName(){
        downloadPageObject.clickDownloadButton();
        downloadPageObject.validateFirstName();
    }

    @Test(testName = "Validate Last Name", dependsOnMethods = { "validateFirstName"})
    public void validateLastName(){
        downloadPageObject.validateLastName();
    }

    @Test(testName = "Validate Email", dependsOnMethods = { "validateLastName"})
    public void validateEmail(){
        downloadPageObject.validateEmail();
    }

    @Test(testName = "Validate Password", dependsOnMethods = { "validateEmail"})
    public void validatePassword(){
        downloadPageObject.validatePassword();
    }

    @Test(testName = "Validate Phone", dependsOnMethods = { "validatePassword"})
    public void validatePhone(){
        downloadPageObject.validatePhone();
    }

    @Test(testName = "Validate Terms", dependsOnMethods = { "validatePhone"})
    public void validateTerms(){
        downloadPageObject.validateTerms();
        downloadPageObject.clickSubmitFirstForm();
    }

    @Test(testName = "Validate Country", dependsOnMethods = { "validateTerms"})
    public void validateCountry(){
        downloadPageObject.validateCountry();
    }

    @Test(testName = "Validate Company", dependsOnMethods = { "validateCountry"})
    public void validateCompany(){
        downloadPageObject.validateCompany();
    }

    @Test(testName = "Validate City", dependsOnMethods = { "validateCompany"})
    public void validateCity(){
        downloadPageObject.validateCity();
    }

    @Test(testName = "Validate How Did You", dependsOnMethods = { "validateCity"})
    public void validateHowDidYou(){
        downloadPageObject.validateHowDidYou();
    }

    @Test(testName = "Validate Job", dependsOnMethods = { "validateHowDidYou"})
    public void validateJob(){
        downloadPageObject.validateJob();
    }

    @Test(testName = "Validate Mailing", dependsOnMethods = { "validateJob"})
    public void validateMailing(){
        downloadPageObject.validateMailingListNotChecked();
    }


}
