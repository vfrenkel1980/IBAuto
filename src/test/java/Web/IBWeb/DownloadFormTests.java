package Web.IBWeb;

import frameworkInfra.Listeners.TestListener;
import frameworkInfra.testbases.web.ibSite.DownloadPageTestBase;

import frameworkInfra.utils.MailService;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import webInfra.RestCalls.Get.GetIsMailRegistered;
import webInfra.ibWeb.pages.WindowsRegistrationForm;
import webInfra.ibWeb.pages.UpdateInfoForm;
import frameworkInfra.utils.StaticDataProvider.*;

/**
 * @brief<b> <b>Download Form Tests</b>
 * @details vm: VM-WEB-CLIENT
 */
@Listeners(TestListener.class)
public class DownloadFormTests extends DownloadPageTestBase{
    /**
     * @test Linux Registration
     * @pre{ }
     * @steps{ }
     * @result{ }
     */
    @Test(testName = "Linux Registration")
    public void linuxRegistration(){
        WindowsRegistrationForm rf = new WindowsRegistrationForm("linux", "User", "blah@blah.com", "1231231231",
                "Canada", "Alberta","IncrediBuild","city", "other", "Brain", true, true,
                false,false, true, true, true, false, true,false,true,true,
                true,true,false,true,false,true,true,true,false,true,
                false,true,true);
        downloadPageObject.registerLinuxUser(rf);
    }
    /**
     * @test Enterprise Registration
     * @pre{ }
     * @steps{ }
     * @result{ }
     */
    @Test(testName = "Enterprise Registration")
    public void enterpriseRegistration(){
        WindowsRegistrationForm rf = new WindowsRegistrationForm("enterprise", "User", mailAddress2, "1231231231",
                "Israel", "","IncrediBuild","city", "other", "ballz", true, true,false,
                false, false, false, false, true, true,true,true,true);
        downloadPageObject.registerEnterpriseUser(rf);
    }
    /**
     * @test Validate First Name
     * @pre{ }
     * @steps{ }
     * @result{ }
     */
    @Test(testName = "Validate First Name")
    public void validateFirstName(){
        downloadPageObject.clickDownloadButton();
        downloadPageObject.validateFirstName();
    }
    /**
     * @test Validate Last Name
     * @pre{ Depends On Methods "validateFirstName"
     *  }
     * @steps{ }
     * @result{ }
     */
    @Test(testName = "Validate Last Name", dependsOnMethods = { "validateFirstName"})
    public void validateLastName(){
        downloadPageObject.validateLastName();
    }
    /**
     * @test Validate Email
     * @pre{ Depends On Methods "validateLastName"
     * }
     * @steps{ }
     * @result{ }
     */
    @Test(testName = "Validate Email", dependsOnMethods = { "validateLastName"})
    public void validateEmail(){
        downloadPageObject.validateEmail();
    }
    /**
     * @test Validate Password
     * @pre{ Depends On Methods "validateEmail"
     * }
     * @steps{ }
     * @result{ }
     */
    @Test(testName = "Validate Password", dependsOnMethods = { "validateEmail"})
    public void validatePassword(){
        downloadPageObject.validatePassword();
    }
    /**
     * @test Validate Phone
     * @pre{ Depends On Methods "validatePassword"
     * }
     * @steps{ }
     * @result{ }
     */
    @Test(testName = "Validate Phone", dependsOnMethods = { "validatePassword"})
    public void validatePhone(){
        downloadPageObject.validatePhone();
    }
    /**
     * @test Validate Terms
     * @pre{ Depends On Methods "validatePhone"
     * }
     * @steps{ }
     * @result{ }
     */
    @Test(testName = "Validate Terms", dependsOnMethods = { "validatePhone"})
    public void validateTerms(){
        downloadPageObject.validateTerms();
        downloadPageObject.clickSubmitFirstForm();
    }
    /**
     * @test Validate Country
     * @pre{ Depends On Methods "validateTerms"
     * }
     * @steps{ }
     * @result{ }
     */
    @Test(testName = "Validate Country", dependsOnMethods = { "validateTerms"})
    public void validateCountry(){
        downloadPageObject.validateCountry();
    }
    /**
     * @test Validate Company
     * @pre{ Depends On Methods "validateCountry"
     * }
     * @steps{ }
     * @result{ }
     */
    @Test(testName = "Validate Company", dependsOnMethods = { "validateCountry"})
    public void validateCompany(){
        downloadPageObject.validateCompany();
    }
    /**
     * @test Validate City
     * @pre{ Depends On Methods  "validateCompany"
     * }
     * @steps{ }
     * @result{ }
     */
    @Test(testName = "Validate City", dependsOnMethods = { "validateCompany"})
    public void validateCity(){
        downloadPageObject.validateCity();
    }
    /**
     * @test Validate How Did You
     * @pre{ Depends On Methods "validateCity"
     * }
     * @steps{ }
     * @result{ }
     */
    @Test(testName = "Validate How Did You", dependsOnMethods = { "validateCity"})
    public void validateHowDidYou(){
        downloadPageObject.validateHowDidYou();
    }
    /**
     * @test Validate Job
     * @pre{ Depends On Methods "validateHowDidYou"
     * }
     * @steps{ }
     * @result{ }
     */
    @Test(testName = "Validate Job", dependsOnMethods = { "validateHowDidYou"})
    public void validateJob(){
        downloadPageObject.validateJob();
    }
    /**
     * @test Validate Mailing
     * @pre{ Depends On Methods "validateJob"
     * }
     * @steps{ }
     * @result{ }
     */
    @Test(testName = "Validate Mailing", dependsOnMethods = { "validateJob"})
    public void validateMailing(){
        downloadPageObject.validateMailingListNotChecked();
    }
    /**
     * @test Windows Registration
     * @pre{ Depends On Methods "validateMailing"
     * }
     * @steps{ }
     * @result{ }
     */
    @Test(testName = "Windows Registration", dependsOnMethods = { "validateMailing"} )
    public void windowsRegistration(){
        WindowsRegistrationForm rf = new WindowsRegistrationForm("Win", "User", mailAddressRandom, "4illumination",
                "5559540098","USA", "alaska", "IncrediBuild", "MOHA", "other",
                "KING", false, true, false, true, true, false, false, true, false);
        downloadPageObject.createNewFreeDevWinAccount(rf);
        try {
            Assert.assertTrue(GetIsMailRegistered.isMailRegistered(mailAddressRandom));
            Assert.assertTrue(MailService.checkMailBySubject(host, mailAddress, password, "Sandbox: Your IncrediBuild Download and License Files"));
            SystemActions.deleteFilesOlderThanX(Locations.TRIAL_LICENSE_PATH, 0);
            Assert.assertTrue(MailService.saveMessageAttachments(host, mailAddress, password, "Sandbox: Your IncrediBuild Download and License Files", Locations.TRIAL_LICENSE_PATH));
        }catch(Exception e){
            e.getMessage();
        } finally {
            MailService.deleteMail(host, mailAddress, password);
        }
    }
    /**
     * @test Verify Change Details
     * @pre{ Depends On Methods "windowsRegistration"
     * }
     * @steps{ }
     * @result{ }
     */
    @Test(testName = "Verify Change Details", dependsOnMethods = { "windowsRegistration"} )
    public void verifyChangeDetails(){
        UpdateInfoForm uif = new UpdateInfoForm("firstName", "lastName", "222222", "France",
                "newCom", "cityName");
        downloadPageObject.updateUserInfo(uif);
        downloadPageObject.verifyUpdatedUserInfo(uif);
    }
    /**
     * @test Verify Existing User
     * @pre{ Depends On Methods  "verifyChangeDetails"
     * }
     * @steps{ }
     * @result{ }
     */
    @Test(testName = "Verify Existing User", dependsOnMethods = { "verifyChangeDetails"})
    public void verifyExistingUser(){
        downloadPageObject.clickLogout();
        downloadPageObject.clickDownloadButton();
        downloadPageObject.verifyExistingUserMessage(mailAddress);
    }

}
