package webInfra.ibWeb.pageObjects;

import com.aventstack.extentreports.Status;
import frameworkInfra.utils.SystemActions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import webInfra.ibWeb.pages.RegistrationForm;

import static frameworkInfra.Listeners.SuiteListener.test;

public class DownloadPageObject {

    /*-------------------MAPPING-------------------*/
    //First stage

    public static final By DOWNLOAD_BTN = By.xpath("//*[@id=\"download_button\"]");
    public static final By WINDOWS_REG = By.xpath("//*[@id=\"windows\"]");
    public static final By LINUX_REG = By.xpath("//*[@id=\"linux\"]");
    public static final By ENTERPRISE_REG = By.xpath("//*[@id=\"enterprise\"]");
    public static final By FIRST_NAME_TB = By.xpath("//*[@id=\"name\"]");
    public static final By LAST_NAME_TB = By.xpath("//*[@id=\"lname\"]");
    public static final By EMAIL_TB = By.xpath("//*[@id=\"email\"]");
    public static final By PASSWORD_TB = By.xpath("//*[@id=\"password\"]");
    public static final By PASSWORD_CONFIRMATION_TB = By.xpath("//*[@id=\"password_confirmation\"]");
    public static final By PHONE_TB = By.xpath("//*[@id=\"phone\"]");
    public static final By TERMS_CB = By.xpath("//*[@id=\"terms\"]");
    public static final By SUBMIT_FIRST_FORM_BTN = By.xpath("//*[@id=\"first-step-submit\"]");

    public static final By LOGIN_BTN = By.xpath("//a[contains(text(),'Log in')]");
    public static final By PURCHASE_ONLINE = By.xpath("//a[contains(text(),'Purchase online')]");

    //Second stage
    public static final By COUNTRY_SELECTION_DDL = By.xpath("//*[@id=\"countriesSelection\"]");
    public static final By COMPANY_TB = By.xpath("//*[@id=\"company\"]");
    public static final By US_STATE_DDL = By.xpath("//*[@id=\"statesSelectionUSA\"]");
    public static final By CANADA_STATE_DDL = By.xpath("//*[@id=\"statesSelectionCanada\"]");
    public static final By CITY_TB = By.xpath("//*[@id=\"city\"]");
    public static final By HOW_DID_YOU_HEAR_DDL = By.xpath("//*[@id=\"howDidYouSelection\"]");
    public static final By JOB_TITLE_TB = By.xpath("//*[@id=\"jobtitle\"]");

    public static final By CPP_CB = By.xpath("//*[@value=\"C++ Build\"]");
    public static final By CSSHORT_CB = By.xpath("//*[@value=\"C#, < 5min compilation time\"]");
    public static final By CSLONG_CB = By.xpath("//*[@value=\"C#, long compilation time\"]");
    public static final By JAVA_CB = By.xpath("//*[@value=\"Java Build\"]");
    public static final By UNREAL_CB = By.xpath("//*[@id=\"Unreal\"]");
    public static final By UNITY_CB = By.xpath("//*[@id=\"C#Unity\"]");
    public static final By TFS_CB = By.xpath("//*[@id=\"TFS\"]");
    public static final By JENKINS_CB = By.xpath("//*[@id=\"Jenkins\"]");
    public static final By MAILING_LIST_CB = By.xpath("//*[@id=\"mailinglist\"]");
    public static final By PRIVACY_AGREEMENT_CB = By.xpath("//*[@id=\"privacy\"]");
    public static final By FREE_DEV_SUBMIT_BTN = By.xpath("//*[@id=\"free-dev-submit\"]");
    public static final By SUBMIT_BTN = By.xpath("//Button[@value=\"Submit\"]");
    public static final By PREVIOUS_BTN = By.xpath("//*[@value=\"prev\"]");

    //Linux CheckBoxes
    public static final By MULTICORE_RB = By.xpath("//*[@id=\"one\"]");
    public static final By AIX_CB = By.xpath("//*[@id=\"AIX\"]");
    public static final By X32BIT_CB = By.xpath("//*[@id=\"32bit\"]");
    public static final By MAVEN_CB = By.xpath("//*[@id=\"Maven\"]");
    public static final By SOLARIS_CB = By.xpath("//*[@id=\"Solaris\"]");
    public static final By GRADLE_CB = By.xpath("//*[@id=\"Gradle\"]");
    public static final By ANT_CB = By.xpath("//*[@id=\"Ant\"]");
    public static final By SCIENTIFIC_CB = By.xpath("//*[@id=\"Scientific_Linux_5.5\"]");
    public static final By YOCTO_CB = By.xpath("//*[@id=\"Yocto\"]");
    public static final By CLEARCASE_CB = By.xpath("//*[@id=\"ClearCase\"]");
    public static final By DOCKER_CB = By.xpath("//*[@id=\"Docker_Containers\"]");
    public static final By ANDROID_CB = By.xpath("//*[@id=\"Android\"]");
    public static final By INTEL_CB = By.xpath("//*[@id=\"Intel_Compiler\"]");
    public static final By ARM_CB = By.xpath("//*[@id=\"ARM\"]");
    public static final By CENTOS_CB = By.xpath("//*[@id=\"Centos_6_helper\"]");
    public static final By EARLIER_CB = By.xpath("//*[@id=\"Linux_Early\"]");

    //Enterprise CB
    public static final By PARALLEL_BUILDS_CB = By.xpath("//*[@id=\"Paralel_Builds\"]");
    public static final By UNLIMITED_INITIATORS_CB = By.xpath("//*[@id=\"Unlimited_Initiators\"]");
    public static final By ADVANCED_REPORTING_CB = By.xpath("//*[@id=\"Advanced_Reporting\"]");


    public static final By AWESOME_LBL = By.xpath("//*[contains(text(),'AWESOME!')]");
    public static final By LOGOUT_BTN = By.xpath("//*[contains(text(),'Logout')]");


    public static final By firstnamedigits = By.xpath("//*[contains(text(),'The First Name field may only contain alphabetic characters as well as spaces.')]");
    public static final By firstnameempty = By.xpath("//*[contains(text(),'The First Name field is required.')]");
    public static final By firstnameonechar = By.xpath("//*[contains(text(),'The First Name field must be at least 2 characters.')]");
    public static final By lastnamedigits = By.xpath("//*[contains(text(),'The Last Name field may only contain alphabetic characters as well as spaces.')]");
    public static final By lastnameempty = By.xpath("//*[contains(text(),'The Last Name field is required.')]");
    public static final By lastnameonechar = By.xpath("//*[contains(text(),'The Last Name field must be at least 2 characters.')]");
    public static final By invalidemail = By.xpath("//*[contains(text(),'Please insert a valid email address')]");
    public static final By existingemail = By.xpath("//*[contains(text(),'The email you entered is already registered in the system.')]");
    public static final By emailempty = By.xpath("//*[contains(text(),'The Email field is required.')]");
    public static final By passwordempty = By.xpath("//*[contains(text(),'The Password field is required.')]");
    public static final By passwordconfirmationempty = By.xpath("//*[contains(text(),'The Password Confirmation field is required.')]");
    public static final By shortpassword = By.xpath("//*[contains(text(),'The Password field must be at least 6 characters.')]");
    public static final By nonmatchingpassword = By.xpath("//*[contains(text(),'The passwords don't match, please type the same password.')]");
    public static final By invalidphone = By.xpath("//*[contains(text(),'The Phone Number field must be at least 6 characters.')]");
    public static final By phonewithchars = By.xpath("//*[contains(text(),'The Phone Number field may only contain numeric characters.')]");
    public static final By phoneempty = By.xpath("//*[contains(text(),'The Phone Number field is required.')]");
    public static final By terms = By.xpath("//*[contains(text(),'Please confirm our term and conditions.')]");
    public static final By countryempty = By.xpath("//*[contains(text(),'The Country field is required.')]");
    public static final By companyempty = By.xpath("//*[contains(text(),'The Company field is required.')]");
    public static final By companyshort = By.xpath("//*[contains(text(),'The Company field must be at least 2 characters.')]");
    public static final By cityempty = By.xpath("//*[contains(text(),'The City field is required.')]");
    public static final By cityshort = By.xpath("//*[contains(text(),'The City field must be at least 2 characters.')]");
    public static final By citynumeric = By.xpath("//*[contains(text(),'The City field may only contain alphabetic characters as well as spaces.')]");
    public static final By howdidyouhearempty = By.xpath("//*[contains(text(),'Please choose how you heard about incredibuild.')]");
    public static final By jobempty = By.xpath("//*[contains(text(),'The Job Title field is required.')]");
    public static final By jobshort = By.xpath("//*[contains(text(),'The Job Title field must be at least 2 characters.')]");
    public static final By jobnumeric = By.xpath("//*[contains(text(),'The Job Title field may only contain alphabetic characters as well as spaces.')]");


    private EventFiringWebDriver eventWebDriver;

    public DownloadPageObject(EventFiringWebDriver driver){
        this.eventWebDriver = driver;
    }

    public void createNewFreeDevWinAccount(RegistrationForm rf){
        eventWebDriver.findElement(DOWNLOAD_BTN).click();
        WebDriverWait wait = new WebDriverWait(eventWebDriver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME_TB)).sendKeys(rf.getName());
        eventWebDriver.findElement(LAST_NAME_TB).sendKeys(rf.getLname());
        eventWebDriver.findElement(EMAIL_TB).sendKeys(rf.getEmail());
        eventWebDriver.findElement(PASSWORD_TB).sendKeys(rf.getPass());
        eventWebDriver.findElement(PASSWORD_CONFIRMATION_TB).sendKeys(rf.getPass());
        eventWebDriver.findElement(PHONE_TB).sendKeys(rf.getPhone());
        eventWebDriver.findElement(TERMS_CB).click();
        eventWebDriver.findElement(SUBMIT_FIRST_FORM_BTN).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(COUNTRY_SELECTION_DDL)).sendKeys(rf.getCountry());
        eventWebDriver.findElement(COMPANY_TB).sendKeys(rf.getCompany());
        switch (rf.getCountry()){
            case "united states":
                eventWebDriver.findElement(US_STATE_DDL).sendKeys(rf.getState());
                break;
            case "canada":
                eventWebDriver.findElement(CANADA_STATE_DDL).sendKeys(rf.getState());
                break;
        }
        eventWebDriver.findElement(CITY_TB).sendKeys(rf.getCity());
        eventWebDriver.findElement(HOW_DID_YOU_HEAR_DDL).sendKeys(rf.getHow());
        eventWebDriver.findElement(JOB_TITLE_TB).sendKeys(rf.getJob());
        if (rf.isCpp())
            eventWebDriver.findElement(CPP_CB).click();
        if (rf.isCshort())
            eventWebDriver.findElement(CSSHORT_CB).click();
        if (rf.isCslong())
            eventWebDriver.findElement(CSLONG_CB).click();
        if (rf.isJava())
            eventWebDriver.findElement(JAVA_CB).click();
        if (rf.isUnreal())
            eventWebDriver.findElement(UNREAL_CB).click();
        if (rf.isUnity())
            eventWebDriver.findElement(UNITY_CB).click();
        if (rf.isTfs())
            eventWebDriver.findElement(TFS_CB).click();
        if (rf.isJenkins())
            eventWebDriver.findElement(JENKINS_CB).click();
        if (rf.isMailing())
            eventWebDriver.findElement(MAILING_LIST_CB).click();
        eventWebDriver.findElement(PRIVACY_AGREEMENT_CB).click();
        eventWebDriver.findElement(FREE_DEV_SUBMIT_BTN).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AWESOME_LBL));
    }

    public void registerLinuxUser(RegistrationForm rf){
        eventWebDriver.findElement(DOWNLOAD_BTN).click();
        eventWebDriver.findElement(LINUX_REG).click();
        WebDriverWait wait = new WebDriverWait(eventWebDriver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME_TB)).sendKeys(rf.getName());
        eventWebDriver.findElement(LAST_NAME_TB).sendKeys(rf.getLname());
        eventWebDriver.findElement(EMAIL_TB).sendKeys(rf.getEmail());
        eventWebDriver.findElement(PHONE_TB).sendKeys(rf.getPhone());
        eventWebDriver.findElement(TERMS_CB).click();
        eventWebDriver.findElement(SUBMIT_FIRST_FORM_BTN).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(COUNTRY_SELECTION_DDL)).sendKeys(rf.getCountry());
        eventWebDriver.findElement(COMPANY_TB).sendKeys(rf.getCompany());
        switch (rf.getCountry()){
            case "united states":
                eventWebDriver.findElement(US_STATE_DDL).sendKeys(rf.getState());
                break;
            case "canada":
                eventWebDriver.findElement(CANADA_STATE_DDL).sendKeys(rf.getState());
                break;
        }
        eventWebDriver.findElement(CITY_TB).sendKeys(rf.getCity());
        eventWebDriver.findElement(HOW_DID_YOU_HEAR_DDL).sendKeys(rf.getHow());
        eventWebDriver.findElement(JOB_TITLE_TB).sendKeys(rf.getJob());
        if (rf.isCpp())
            eventWebDriver.findElement(CPP_CB).click();
        if (rf.isCshort())
            eventWebDriver.findElement(CSSHORT_CB).click();
        if (rf.isCslong())
            eventWebDriver.findElement(CSLONG_CB).click();
        if (rf.isJava())
            eventWebDriver.findElement(JAVA_CB).click();
        if (rf.isUnreal())
            eventWebDriver.findElement(UNREAL_CB).click();
        if (rf.isUnity())
            eventWebDriver.findElement(UNITY_CB).click();
        if (rf.isTfs())
            eventWebDriver.findElement(TFS_CB).click();
        if (rf.isJenkins())
            eventWebDriver.findElement(JENKINS_CB).click();
        if (rf.isMulticore())
            eventWebDriver.findElement(MULTICORE_RB).click();
        if (rf.isAix())
            eventWebDriver.findElement(AIX_CB).click();
        if (rf.isX32bit())
            eventWebDriver.findElement(X32BIT_CB).click();
        if (rf.isMaven())
            eventWebDriver.findElement(MAVEN_CB).click();
        if (rf.isSolaris())
            eventWebDriver.findElement(SOLARIS_CB).click();
        if (rf.isGradle())
            eventWebDriver.findElement(GRADLE_CB).click();
        if (rf.isAnt())
            eventWebDriver.findElement(ANT_CB).click();
        if (rf.isScientific())
            eventWebDriver.findElement(SCIENTIFIC_CB).click();
        if (rf.isYocto())
            eventWebDriver.findElement(YOCTO_CB).click();
        if (rf.isClearcase())
            eventWebDriver.findElement(CLEARCASE_CB).click();
        if (rf.isDocker())
            eventWebDriver.findElement(DOCKER_CB).click();
        if (rf.isAndroid())
            eventWebDriver.findElement(ANDROID_CB).click();
        if (rf.isIntel())
            eventWebDriver.findElement(INTEL_CB).click();
        if (rf.isArm())
            eventWebDriver.findElement(ARM_CB).click();
        if (rf.isCentos())
            eventWebDriver.findElement(CENTOS_CB).click();
        if (rf.isEarlier())
            eventWebDriver.findElement(EARLIER_CB).click();
        if (rf.isMailing())
            eventWebDriver.findElement(MAILING_LIST_CB).click();
        eventWebDriver.findElement(PRIVACY_AGREEMENT_CB).click();
        eventWebDriver.findElement(SUBMIT_BTN).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AWESOME_LBL));
    }

    public void registerEnterpriseUser(RegistrationForm rf){
        eventWebDriver.findElement(DOWNLOAD_BTN).click();
        eventWebDriver.findElement(ENTERPRISE_REG).click();
        WebDriverWait wait = new WebDriverWait(eventWebDriver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME_TB)).sendKeys(rf.getName());
        eventWebDriver.findElement(LAST_NAME_TB).sendKeys(rf.getLname());
        eventWebDriver.findElement(EMAIL_TB).sendKeys(rf.getEmail());
        eventWebDriver.findElement(PHONE_TB).sendKeys(rf.getPhone());
        eventWebDriver.findElement(TERMS_CB).click();
        eventWebDriver.findElement(SUBMIT_FIRST_FORM_BTN).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(COUNTRY_SELECTION_DDL)).sendKeys(rf.getCountry());
        eventWebDriver.findElement(COMPANY_TB).sendKeys(rf.getCompany());
        switch (rf.getCountry()){
            case "united states":
                eventWebDriver.findElement(US_STATE_DDL).sendKeys(rf.getState());
                break;
            case "canada":
                eventWebDriver.findElement(CANADA_STATE_DDL).sendKeys(rf.getState());
                break;
        }
        eventWebDriver.findElement(CITY_TB).sendKeys(rf.getCity());
        eventWebDriver.findElement(HOW_DID_YOU_HEAR_DDL).sendKeys(rf.getHow());
        eventWebDriver.findElement(JOB_TITLE_TB).sendKeys(rf.getJob());
        if (rf.isCpp())
            eventWebDriver.findElement(CPP_CB).click();
        if (rf.isCshort())
            eventWebDriver.findElement(CSSHORT_CB).click();
        if (rf.isCslong())
            eventWebDriver.findElement(CSLONG_CB).click();
        if (rf.isJava())
            eventWebDriver.findElement(JAVA_CB).click();
        if (rf.isUnreal())
            eventWebDriver.findElement(UNREAL_CB).click();
        if (rf.isUnity())
            eventWebDriver.findElement(UNITY_CB).click();
        if (rf.isTfs())
            eventWebDriver.findElement(TFS_CB).click();
        if (rf.isJenkins())
            eventWebDriver.findElement(JENKINS_CB).click();
        if (rf.isParallelBuilds())
            eventWebDriver.findElement(PARALLEL_BUILDS_CB).click();
        if (rf.isUnlimitedInitiators())
            eventWebDriver.findElement(UNLIMITED_INITIATORS_CB).click();
        if (rf.isAdvancedReporting())
            eventWebDriver.findElement(ADVANCED_REPORTING_CB).click();
        if (rf.isMailing())
            eventWebDriver.findElement(MAILING_LIST_CB).click();
        eventWebDriver.findElement(PRIVACY_AGREEMENT_CB).click();
        eventWebDriver.findElement(SUBMIT_BTN).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AWESOME_LBL));
    }

    public void validateFirstName(){
        eventWebDriver.findElement(FIRST_NAME_TB).sendKeys("11");
        Assert.assertTrue(eventWebDriver.findElement(firstnamedigits).isDisplayed());

        eventWebDriver.findElement(FIRST_NAME_TB).clear();
        eventWebDriver.findElement(FIRST_NAME_TB).sendKeys(" ");
        Assert.assertTrue(eventWebDriver.findElement(firstnameempty).isDisplayed());

        eventWebDriver.findElement(FIRST_NAME_TB).clear();
        eventWebDriver.findElement(FIRST_NAME_TB).sendKeys("a");
        Assert.assertTrue(eventWebDriver.findElement(firstnameonechar).isDisplayed());
        eventWebDriver.findElement(FIRST_NAME_TB).clear();
        eventWebDriver.findElement(FIRST_NAME_TB).sendKeys("Test");
        Assert.assertTrue(eventWebDriver.findElements(firstnameonechar).isEmpty());
    }

    public void validateLastName(){
        eventWebDriver.findElement(LAST_NAME_TB).sendKeys("11");
        Assert.assertTrue(eventWebDriver.findElement(lastnamedigits).isDisplayed());

        eventWebDriver.findElement(LAST_NAME_TB).clear();
        eventWebDriver.findElement(LAST_NAME_TB).sendKeys(" ");
        Assert.assertTrue(eventWebDriver.findElement(lastnameempty).isDisplayed());

        eventWebDriver.findElement(LAST_NAME_TB).clear();
        eventWebDriver.findElement(LAST_NAME_TB).sendKeys("a");
        Assert.assertTrue(eventWebDriver.findElement(lastnameonechar).isDisplayed());
        eventWebDriver.findElement(LAST_NAME_TB).clear();
        eventWebDriver.findElement(LAST_NAME_TB).sendKeys("Test");
        Assert.assertTrue(eventWebDriver.findElements(lastnameonechar).isEmpty());
    }

    public void validateEmail(){
        eventWebDriver.findElement(EMAIL_TB).sendKeys("testme");
        Assert.assertTrue(eventWebDriver.findElement(invalidemail).isDisplayed());
        eventWebDriver.findElement(EMAIL_TB).clear();
        eventWebDriver.findElement(EMAIL_TB).sendKeys(" ");
        Assert.assertTrue(eventWebDriver.findElement(emailempty).isDisplayed());
        eventWebDriver.findElement(EMAIL_TB).clear();
        eventWebDriver.findElement(EMAIL_TB).sendKeys("Test@test.com");
        Assert.assertTrue(eventWebDriver.findElements(emailempty).isEmpty());
    }

    public void verifyExistingUserMessage(String email) {
        eventWebDriver.findElement(EMAIL_TB).sendKeys(email);
        Assert.assertTrue(eventWebDriver.findElement(existingemail).isDisplayed());
    }

    public void validatePassword() {
        eventWebDriver.findElement(PASSWORD_TB).sendKeys("aaa");
        Assert.assertTrue(eventWebDriver.findElement(shortpassword).isDisplayed());
        eventWebDriver.findElement(PASSWORD_TB).clear();
        eventWebDriver.findElement(PASSWORD_TB).sendKeys(" ");
        Assert.assertTrue(eventWebDriver.findElement(passwordempty).isDisplayed());
        eventWebDriver.findElement(PASSWORD_TB).clear();
        eventWebDriver.findElement(PASSWORD_TB).sendKeys("111111");
        Assert.assertTrue(eventWebDriver.findElements(passwordempty).isEmpty());
        eventWebDriver.findElement(PASSWORD_CONFIRMATION_TB).sendKeys("aaa");
        //Assert.assertTrue(eventWebDriver.findElement(nonmatchingpassword).isDisplayed());
        eventWebDriver.findElement(PASSWORD_CONFIRMATION_TB).clear();
        eventWebDriver.findElement(PASSWORD_CONFIRMATION_TB).sendKeys(" ");
        Assert.assertTrue(eventWebDriver.findElement(passwordconfirmationempty).isDisplayed());


        eventWebDriver.findElement(PASSWORD_CONFIRMATION_TB).clear();
        eventWebDriver.findElement(PASSWORD_CONFIRMATION_TB).sendKeys("111111");
        Assert.assertTrue(eventWebDriver.findElements(passwordconfirmationempty).isEmpty());
    }

    public void validatePhone(){
        eventWebDriver.findElement(PHONE_TB).sendKeys("11111");
        Assert.assertTrue(eventWebDriver.findElement(invalidphone).isDisplayed());
        eventWebDriver.findElement(PHONE_TB).sendKeys("aa");
        Assert.assertTrue(eventWebDriver.findElement(phonewithchars).isDisplayed());
        eventWebDriver.findElement(PHONE_TB).clear();
        eventWebDriver.findElement(PHONE_TB).sendKeys(" ");
        Assert.assertTrue(eventWebDriver.findElement(phoneempty).isDisplayed());
        eventWebDriver.findElement(PHONE_TB).clear();
        eventWebDriver.findElement(PHONE_TB).sendKeys("111111");
        Assert.assertTrue(eventWebDriver.findElements(phoneempty).isEmpty());
    }

    public void validateTerms(){
        eventWebDriver.findElement(SUBMIT_FIRST_FORM_BTN).click();
        SystemActions.sleep(1);
        Assert.assertTrue(eventWebDriver.findElement(terms).isDisplayed());
        eventWebDriver.findElement(TERMS_CB).click();
        Assert.assertFalse(eventWebDriver.findElement(terms).isDisplayed());
    }

    public void validateCountry(){
        WebDriverWait wait = new WebDriverWait(eventWebDriver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(COUNTRY_SELECTION_DDL)).click();
        eventWebDriver.findElement(COMPANY_TB).click();
        Assert.assertTrue(eventWebDriver.findElement(countryempty).isDisplayed());

        eventWebDriver.findElement(COUNTRY_SELECTION_DDL).sendKeys("united states");
        Assert.assertTrue(eventWebDriver.findElement(US_STATE_DDL).isDisplayed());
        SystemActions.sleep(1);
        eventWebDriver.findElement(COUNTRY_SELECTION_DDL).sendKeys("canada");
        Assert.assertTrue(eventWebDriver.findElement(CANADA_STATE_DDL).isDisplayed());
        eventWebDriver.findElement(COUNTRY_SELECTION_DDL).sendKeys("israel");
        Assert.assertTrue(eventWebDriver.findElements(countryempty).isEmpty());
    }

    public void validateCompany(){
        eventWebDriver.findElement(COMPANY_TB).sendKeys("a");
        Assert.assertTrue(eventWebDriver.findElement(companyshort).isDisplayed());
        eventWebDriver.findElement(COMPANY_TB).clear();
        eventWebDriver.findElement(COMPANY_TB).sendKeys(" ");
        Assert.assertTrue(eventWebDriver.findElement(companyempty).isDisplayed());
        eventWebDriver.findElement(COMPANY_TB).clear();
        eventWebDriver.findElement(COMPANY_TB).sendKeys("IB");
        Assert.assertTrue(eventWebDriver.findElements(companyempty).isEmpty());
    }

    public void validateCity(){
        eventWebDriver.findElement(CITY_TB).sendKeys("a");
        Assert.assertTrue(eventWebDriver.findElement(cityshort).isDisplayed());
        eventWebDriver.findElement(CITY_TB).sendKeys("a213");
        Assert.assertTrue(eventWebDriver.findElement(citynumeric).isDisplayed());
        eventWebDriver.findElement(CITY_TB).clear();
        eventWebDriver.findElement(CITY_TB).sendKeys(" ");
        Assert.assertTrue(eventWebDriver.findElement(cityempty).isDisplayed());
        eventWebDriver.findElement(CITY_TB).clear();
        eventWebDriver.findElement(CITY_TB).sendKeys("Tel Aviv");
        Assert.assertTrue(eventWebDriver.findElements(cityempty).isEmpty());
    }

    public void validateHowDidYou(){
        eventWebDriver.findElement(HOW_DID_YOU_HEAR_DDL).click();
        eventWebDriver.findElement(JOB_TITLE_TB).click();
        Assert.assertTrue(eventWebDriver.findElement(howdidyouhearempty).isDisplayed());
        eventWebDriver.findElement(HOW_DID_YOU_HEAR_DDL).sendKeys("other");
        Assert.assertFalse(eventWebDriver.findElement(howdidyouhearempty).isDisplayed());
    }

    public void validateJob(){
        eventWebDriver.findElement(JOB_TITLE_TB).sendKeys("a");
        Assert.assertTrue(eventWebDriver.findElement(jobshort).isDisplayed());
        eventWebDriver.findElement(JOB_TITLE_TB).sendKeys("a213");
        Assert.assertTrue(eventWebDriver.findElement(jobnumeric).isDisplayed());
        eventWebDriver.findElement(JOB_TITLE_TB).clear();
        eventWebDriver.findElement(JOB_TITLE_TB).sendKeys(" ");
        Assert.assertTrue(eventWebDriver.findElement(jobempty).isDisplayed());
        eventWebDriver.findElement(JOB_TITLE_TB).clear();
        eventWebDriver.findElement(JOB_TITLE_TB).sendKeys("BOSS");
        Assert.assertTrue(eventWebDriver.findElements(jobempty).isEmpty());
    }

    public void validateMailingListNotChecked(){
        Assert.assertFalse(eventWebDriver.findElement(MAILING_LIST_CB).isSelected());
    }

    public void clickDownloadButton(){
        eventWebDriver.findElement(DOWNLOAD_BTN).click();
    }

    public void clickSubmitFirstForm(){
        eventWebDriver.findElement(SUBMIT_FIRST_FORM_BTN).click();
    }

    public void verifyMissingLoginButton(){
        WebDriverWait wait = new WebDriverWait(eventWebDriver,10);
        if(wait.until(ExpectedConditions.invisibilityOfElementLocated((LOGIN_BTN))))
            test.log(Status.INFO,"Login button is not present on the \"CREATE NEW ACCOUNT\" page");
        else
            test.log(Status.INFO,"Login button FOUND on the \"CREATE NEW ACCOUNT\" page");
    }

    public void verifyMissingStoreLink(){
        WebDriverWait wait = new WebDriverWait(eventWebDriver,10);
        if(wait.until(ExpectedConditions.invisibilityOfElementLocated((PURCHASE_ONLINE))))
            test.log(Status.INFO,"Purchase online link is not present on the \"CREATE NEW ACCOUNT\" page");
        else
            test.log(Status.INFO,"Purchase online link FOUND on the \"CREATE NEW ACCOUNT\" page");
    }

    public void clickLogout(){
        eventWebDriver.findElement(LOGOUT_BTN).click();
    }

}
