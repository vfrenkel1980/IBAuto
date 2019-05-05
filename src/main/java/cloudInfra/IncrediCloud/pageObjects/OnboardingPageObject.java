package cloudInfra.IncrediCloud.pageObjects;

import cloudInfra.IncrediCloud.Pages.OnboardingPage;
import frameworkInfra.utils.SystemActions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OnboardingPageObject {

    //MAPPING
    private static final By TRY_INCREDICLOUD_BUTTON = By.xpath("//button[@class='login_azure col-xl-3 col-sm-4 buttonTag']");
    private static final By REGION_SELECT = By.xpath("//mat-select[@placeholder='Azure Region']");
    private static final String REGIONS = "//span[text()='%s']";
    private static final By TENANTID_TB = By.xpath("//*[@placeholder='Tenant ID']");
    private static final By FIRST_NAME_TB = By.xpath("//*[@placeholder='First Name']");
    private static final By LAST_NAME_TB = By.xpath("//*[@placeholder='Last Name']");
    private static final By EMAIL_TB = By.xpath("//*[@placeholder='Email']");
    private static final By COMPANY_TB = By.xpath("//*[@placeholder='Company Name']");
    private static final By VMS_PANEL = By.xpath("//mat-panel-title[text()=' VMs ']");
    private static final By TIMEOUT_TB = By.xpath("//*[@placeholder='VM Idle Timeout (seconds)']");
    private static final By CORES_LIMIT_TB = By.xpath("//*[@placeholder='Total Cores Limit']");
    private static final By POOL_SIZE_TB = By.xpath("//*[@placeholder='No. of VMs in Pool']");
    private static final By NETWORK_PANEL = By.xpath("//mat-panel-title[text()='Network']");
    private static final By SAVE_BTN = By.xpath("//button[@class='save_text save_button buttonTag']");
    private static final By ACTIVATE_BTN = By.xpath("//button[text()='Approve and Activate']");


    private static EventFiringWebDriver eventWebDriver;
    private WebDriverWait wait;

    public OnboardingPageObject(EventFiringWebDriver driver) {
        eventWebDriver = driver;
        wait = new WebDriverWait(eventWebDriver, 10);
    }

    public void clickTryIncredicloud(){
        eventWebDriver.findElement(TRY_INCREDICLOUD_BUTTON).click();
    }

    public void performOnboarding(OnboardingPage onboardingPage){
        selectRegion(onboardingPage);
        enterName(onboardingPage);
        enterMail(onboardingPage);
        enterCompany(onboardingPage);
        enterVMDetails(onboardingPage);
        clickSave();
    }

    public void performUpdate(OnboardingPage onboardingPage){
        enterVMDetails(onboardingPage);
        clickSave();
    }

    private void selectRegion(OnboardingPage onboardingPage){
        SystemActions.sleep(10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(REGION_SELECT)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(REGIONS, onboardingPage.getRegion())))).click();
    }

    private void enterName(OnboardingPage onboardingPage){
        eventWebDriver.findElement(FIRST_NAME_TB).sendKeys(onboardingPage.getFirstName());
        eventWebDriver.findElement(LAST_NAME_TB).sendKeys(onboardingPage.getLastName());
    }

    private void enterMail(OnboardingPage onboardingPage){
        eventWebDriver.findElement(EMAIL_TB).sendKeys(onboardingPage.getEmail());
    }

    private void enterCompany(OnboardingPage onboardingPage){
        eventWebDriver.findElement(COMPANY_TB).sendKeys(onboardingPage.getCompany());
    }

    private void enterVMDetails(OnboardingPage onboardingPage){
        eventWebDriver.findElement(VMS_PANEL).click();
        eventWebDriver.findElement(TIMEOUT_TB).clear();
        eventWebDriver.findElement(TIMEOUT_TB).sendKeys(String.valueOf(onboardingPage.getTimeout()));
        eventWebDriver.findElement(CORES_LIMIT_TB).clear();
        eventWebDriver.findElement(CORES_LIMIT_TB).sendKeys(String.valueOf(onboardingPage.getCoresLimit()));
        eventWebDriver.findElement(POOL_SIZE_TB).clear();
        eventWebDriver.findElement(POOL_SIZE_TB).sendKeys(String.valueOf(onboardingPage.getPoolSize()));
    }

    private void clickSave(){
        eventWebDriver.findElement(SAVE_BTN).click();
        eventWebDriver.findElement(ACTIVATE_BTN).click();
    }



}
