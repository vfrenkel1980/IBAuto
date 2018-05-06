package webInfra.ibWeb.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import webInfra.ibWeb.downloadPage.RegistrationForm;

public class DownloadPageObject {

    /*-------------------MAPPING-------------------*/
    //First stage

    public static final By DOWNLOAD_BTN = By.xpath("//*[@id=\"myDIV\"]/ul/li[7]/a");
    public static final By WINDOWS_REG = By.xpath("//*[@id=\"windows\"]/a/a");
    public static final By LINUX_REG = By.xpath("//*[@id=\"linux\"]/a/a");
    public static final By ENTERPISE_REG = By.xpath("//*[@id=\"enterprise\"]/a/a");
    public static final By FIRST_NAME_TB = By.xpath("//*[@id=\"name\"]");
    public static final By LAST_NAME_TB = By.xpath("//*[@id=\"lname\"]");
    public static final By EMAIL_TB = By.xpath("//*[@id=\"email\"]");
    public static final By PASSWORD_TB = By.xpath("//*[@id=\"password\"]");
    public static final By PASSWORD_CONFIRMATION_TB = By.xpath("//*[@id=\"password_confirmation\"]");
    public static final By PHONE_TB = By.xpath("//*[@id=\"phone\"]");
    public static final By TERMS_CB = By.xpath("//*[@id=\"terms\"]");
    public static final By SUBMIT_FIRST_WIN_FORM_BTN = By.xpath("//*[@id=\"first-step-submit\"]");
    public static final By SUBMIT_FIRST_LINUX_FORM_BTN = By.xpath("//Button[@value=\"Next\"]");

    //Second stage
    public static final By COUNTRY_SELECTION_DDL = By.xpath("//*[@id=\"countriesSelection\"]");
    public static final By COMPANY_TB = By.xpath("//*[@id=\"company\"]");
    public static final By STATE_DDL = By.xpath("//*[@id=\"statesSelection\"]");
    public static final By CITY_TB = By.xpath("//*[@id=\"city\"]");
    public static final By HOW_DID_YOU_HEAR_DDL = By.xpath("//*[@id=\"howDidYouSelection\"]");
    public static final By JOB_TITLE_TB = By.xpath("//*[@id=\"jobtitle\"]");

    public static final By CPP_CB = By.xpath("//*[@id=\"C++\"]");
    public static final By CSSHORT_CB = By.xpath("//*[@id=\"C#short\"]");
    public static final By CSLONG_CB = By.xpath("//*[@id=\"C#long compilation time\"]");
    public static final By JAVA_CB = By.xpath("//*[@id=\"Java Build\"]");
    public static final By MAILING_LIST_CB = By.xpath("//*[@id=\"mailinglist\"]");
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
    public static final By DOCKER_CB = By.xpath("//*[@id=\"Docker_Constrains\"]");
    public static final By ANDROID_CB = By.xpath("//*[@id=\"Android\"]");
    public static final By INTEL_CB = By.xpath("//*[@id=\"Intel_Compiler\"]");
    public static final By ARM_CB = By.xpath("//*[@id=\"ARM\"]");
    public static final By CENTOS_CB = By.xpath("//*[@id=\"Centos_6_helper\"]");
    public static final By EARLIER_CB = By.xpath("//*[@id=\"Linux_Early\"]");

    //Enterprise CB
    public static final By PARALEL_BUILDS_CB = By.xpath("//*[@id=\"Paralel_Builds\"]");
    public static final By UNLIMITED_INITIATORE_CB = By.xpath("//*[@id=\"Unlimited_Initiators\"]");
    public static final By ADVANCED_REPORTING_CB = By.xpath("//*[@id=\"Advanced_Reporting\"]");



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
        eventWebDriver.findElement(SUBMIT_FIRST_WIN_FORM_BTN).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(COUNTRY_SELECTION_DDL)).sendKeys(rf.getCountry());
        eventWebDriver.findElement(COMPANY_TB).sendKeys(rf.getCompany());
        if (rf.getCountry().equals("united states") || rf.getCountry().equals("canada") )
            eventWebDriver.findElement(STATE_DDL).sendKeys(rf.getState());
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
        if (rf.isMailing())
            eventWebDriver.findElement(MAILING_LIST_CB).click();
        eventWebDriver.findElement(FREE_DEV_SUBMIT_BTN).click();
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
        eventWebDriver.findElement(SUBMIT_FIRST_LINUX_FORM_BTN).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(COUNTRY_SELECTION_DDL)).sendKeys(rf.getCountry());
        eventWebDriver.findElement(COMPANY_TB).sendKeys(rf.getCompany());
        if (rf.getCountry().equals("united states") || rf.getCountry().equals("canada") )
            eventWebDriver.findElement(STATE_DDL).sendKeys(rf.getState());
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
        eventWebDriver.findElement(SUBMIT_BTN).click();
    }

    public void registerEnterpriseUser(RegistrationForm rf){
        eventWebDriver.findElement(DOWNLOAD_BTN).click();
        eventWebDriver.findElement(ENTERPISE_REG).click();
        WebDriverWait wait = new WebDriverWait(eventWebDriver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME_TB)).sendKeys(rf.getName());
        eventWebDriver.findElement(LAST_NAME_TB).sendKeys(rf.getLname());
        eventWebDriver.findElement(EMAIL_TB).sendKeys(rf.getEmail());
        eventWebDriver.findElement(PHONE_TB).sendKeys(rf.getPhone());
        eventWebDriver.findElement(TERMS_CB).click();
        eventWebDriver.findElement(SUBMIT_FIRST_LINUX_FORM_BTN).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(COUNTRY_SELECTION_DDL)).sendKeys(rf.getCountry());
        eventWebDriver.findElement(COMPANY_TB).sendKeys(rf.getCompany());
        if (rf.getCountry().equals("united states") || rf.getCountry().equals("canada") )
            eventWebDriver.findElement(STATE_DDL).sendKeys(rf.getState());
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
        if (rf.isParallelBuilds())
            eventWebDriver.findElement(PARALEL_BUILDS_CB).click();
        if (rf.isUnlimitedInitiators())
            eventWebDriver.findElement(UNLIMITED_INITIATORE_CB).click();
        if (rf.isAdvancedReporting())
            eventWebDriver.findElement(ADVANCED_REPORTING_CB).click();
        if (rf.isMailing())
            eventWebDriver.findElement(MAILING_LIST_CB).click();
        eventWebDriver.findElement(SUBMIT_BTN).click();
    }



}
