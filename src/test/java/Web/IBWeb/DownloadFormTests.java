package Web.IBWeb;

import frameworkInfra.Listeners.TestListener;
import frameworkInfra.testbases.web.ibSite.DownloadPageTestBase;

import frameworkInfra.utils.MailService;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import webInfra.RestCalls.Get.GetIsMailRegistered;
import webInfra.ibWeb.pages.WindowsRegistrationForm;
import webInfra.ibWeb.pages.UpdateInfoForm;
import frameworkInfra.utils.StaticDataProvider.*;


@Listeners(TestListener.class)
public class DownloadFormTests extends DownloadPageTestBase{

    @Test(testName = "Linux Registration")
    public void linuxRegistration(){
        WindowsRegistrationForm rf = new WindowsRegistrationForm("linux", "User", "blah@blah.com", "1231231231",
                "Canada", "Alberta","IB","city", "other", "Brain", true, true,
                false,false, true, true, true, false, true,false,true,true,
                true,true,false,true,false,true,true,true,false,true,
                false,true,true);
        downloadPageObject.registerLinuxUser(rf);
    }

    @Test(testName = "Enterprise Registration")
    public void enterpriseRegistration(){
        WindowsRegistrationForm rf = new WindowsRegistrationForm("enterprise", "User", mailAddress2, "1231231231",
                "Israel", "","IB","city", "other", "ballz", true, true,false,
                false, false, false, false, true, true,true,true,true);
        downloadPageObject.registerEnterpriseUser(rf);
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

    @Test(testName = "Windows Registration", dependsOnMethods = { "validateMailing"} )
    public void windowsRegistration(){
        WindowsRegistrationForm rf = new WindowsRegistrationForm("Win", "User", mailAddressRandom, "4illumination",
                "5559540098","USA", "alaska", "IB", "MOHA", "other",
                "KING", false, true, false, true, true, false, false, true, false);
        downloadPageObject.createNewFreeDevWinAccount(rf);
        Assert.assertTrue(GetIsMailRegistered.isMailRegistered(mailAddressRandom));
        Assert.assertTrue(MailService.checkMailBySubject(host, mailAddress, password, "Sandbox: Your IncrediBuild Download and License File"));
        Assert.assertTrue(MailService.saveMessageAttachments(host, mailAddress, password, "Sandbox: Your IncrediBuild Download and License File", Locations.TRIAL_LICENSE_PATH));
        MailService.deleteMail(host, mailAddress, password);
    }

    @Test(testName = "Verify Change Details", dependsOnMethods = { "windowsRegistration"} )
    public void verifyChangeDetails(){
        UpdateInfoForm uif = new UpdateInfoForm("firstName", "lastName", "222222", "France",
                "newCom", "cityName");
        downloadPageObject.updateUserInfo(uif);
        downloadPageObject.verifyUpdatedUserInfo(uif);
    }

    @Test(testName = "Verify Existing User", dependsOnMethods = { "verifyChangeDetails"})
    public void verifyExistingUser(){
        downloadPageObject.clickLogout();
        downloadPageObject.clickDownloadButton();
        downloadPageObject.verifyExistingUserMessage(mailAddress);
    }

}
