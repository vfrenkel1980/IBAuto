package cloudInfra.IncrediCloud.pageObjects;

import cloudInfra.IncrediCloud.Pages.OnboardingPage;
import cloudInfra.IncrediCloud.metadata.Enums.OnboardingType;
import frameworkInfra.utils.SystemActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static frameworkInfra.testbases.incrediCloud.ICEngineTestBase.CLOUD;
import static frameworkInfra.testbases.incrediCloud.ICEngineTestBase.ONBOARDING_TYPE;

public class OnboardingPageObject {

    //MAPPING
    private static final By TRY_INCREDICLOUD_BUTTON = By.xpath("//button[@class='login_azure  col-sm-4 buttonTag']");
    private static final By AZURE_CLOUD_BUTTON = By.xpath("//*[@class='icon-btn icon_azure']");
    private static final By AZURE_CLOUD_YES_I_DO_BUTTON = By.xpath("//*[@id='loginAzureAdminSelection']");
    private static final By AZURE_CLOUD_ADMIN_LOGIN_BUTTON = By.xpath("//*[@id='loginAzureAdmin']");
    private static final By AZURE_CLOUD_NO_I_DONT_BUTTON = By.xpath("//*[@id='loginAzureLowSelection']");
    private static final By AZURE_CLOUD_LOW_PERMISSIONS_LOGIN_BUTTON = By.xpath("//*[@id='loginAzureLow']");
    private static final By AWS_CLOUD_YES_I_DO_BUTTON = By.xpath("//*[@id='loginAWSAdminSelection']");
    private static final By AWS_CLOUD_AM_NOT_SURE_BUTTON = By.xpath("//*[@id='loginAWSLowSelection']");
    private static final By AWS_CLOUD_BUTTON = By.xpath("//*[@class='icon-btn icon_amazon']");
    private static final By AWS_CLOUD_ADMIN_LOGIN_BUTTON = By.xpath("//*[@id='loginAWSAdmin']");
    private static final By AWS_CLOUD_LOW_PERMISSIONS_LOGIN_BUTTON = By.xpath("//*[@id='loginAWSLow']");

    private static final By REGION_SELECT = By.xpath("//mat-select[@placeholder='Cloud Region']");
    private static final By TENANT_SELECT = By.xpath("//mat-select[@placeholder='Tenant ID']");
    private static final By SUBSCRIPTION_SELECT = By.xpath("//mat-select[@placeholder='Subscription']");
    //Custom tags
    private static final By CUSTOM_TAG_KEY = By.id("tagNameInput");
    private static final By CUSTOM_TAG_VALUE = By.id("tagValueInput");
    private static final By CUSTOM_TAG_ADD_BTN = By.id("addTagBtn");
    private static final By CUSTOM_TAG_REMOVE_BTN = By.className("ic-tag-chip-remove-btn");
    private static final By CUSTOM_TAG_ADDED_TAG = By.className("ic-tag-chip");
    private static final By CUSTOM_TAG_NO_TAG_UPDATE_MESSAGE = By.className("noTagUpdateBtn");
    private static final By MACHINE_TYPE_SELECT = By.xpath("//mat-select[@placeholder='VM Type']");
    private static final String SELECTION_LIST = "//span[contains(text(),'%s')]";
//    private static final String REGION_SELECTION_LIST =
//            "//span[@class='mat-select-value-text ng-tns-c20-28 ng-star-inserted']";
    private static final String MACHINE_SELECTION_LIST = "//span[contains(text(),'%s')][@class='mat-option-text']";
    private static final By FIRST_NAME_TB = By.xpath("//*[@placeholder='First Name']");
    private static final By LAST_NAME_TB = By.xpath("//*[@placeholder='Last Name']");
    private static final By EMAIL_TB = By.xpath("//*[@placeholder='Email']");
    private static final By COMPANY_TB = By.xpath("//*[@placeholder='Company Name']");
    private static final By VMS_PANEL = By.xpath("//mat-panel-title[text()=' VMs ']");

    // Private network
    private static final By PRIVATE_NETWORK_BTN = By.id("privateIpToggle");
    private static final By AZURE_RESOURCE_GROUP_SELECTION_LIST = By.xpath("//mat-select[@placeholder='Resource Group']");
    private static final By AZURE_VIRTUAL_NETWORK_SELECTION_LIST = By.xpath("//mat-select[@placeholder='Virtual Network']");
    private static final By SUBNET_SELECTION_LIST = By.xpath("//mat-select[@placeholder='Subnet']");
    private static final By AWS_VPC_SELECTION_LIST = By.xpath("//mat-select[@placeholder='VPC']");

    private static final By TIMEOUT_TB = By.xpath("//*[@placeholder='VM Idle Timeout (seconds)']");
    private static final By CORES_LIMIT_TB = By.xpath("//*[@placeholder='Total Cores Limit']");
    private static final By POOL_SIZE_TB = By.xpath("//*[@placeholder='No. of VMs in Pool']");
    private static final By COORD_PORT_TB = By.xpath("//*[@placeholder='Coordinator Port No.']");
    private static final By VM_PORT_TB = By.xpath("//*[@placeholder='VM Port Range']");
    private static final By NETWORK_PANEL = By.xpath("//mat-panel-title[text()=' Network ']");
    private static final By SAVE_BTN = By.xpath("//*[@id='savePolicyButton']");
    private static final By CANCEL_BTN = By.xpath("//*[@id='cancelOnboardingButton']");
    private static final By ACTIVATE_BTN = By.xpath("//span[text()='Approve and Activate']");
    private static final By LICENSE_AGREEMENT = By.xpath("//*[@id='mat-checkbox-1']");
    private static final By SETTINGS_TAB = By.xpath("//*[text()='IncrediBuild Cloud Settings']");
    private static final By DOWNLOAD_BTN = By.xpath("//*[@id='download_button']");
    //private static final By DOWNLOAD_BTN = By.xpath("//*[contains(text(),'IncrediBuild Cloud for Microsoft Azure')]");

    //ERROR MESSAGES
    private static final By TENNANT_ERROR_LBL = By.xpath("//*[text()='The Tenant id field is required']");
    private static final By SUBSCRIPTION_ERROR_LBL = By.xpath("//*[text()=' The Subscription field is required']");
    private static final By REGION_ERROR_LBL = By.xpath("//*[text()='The Cloud Region field is required']");
    private static final By FNAME_ERROR_LBL = By.xpath("//*[text()=' The first name field is required']");
    private static final By LNAME_ERROR_LBL = By.xpath("//*[text()='The last name field is required']");
    private static final By EMAIL_ERROR_LBL = By.xpath("//*[text()='The email field is required']");
    private static final By INVALID_EMAIL_ERROR_LBL = By.xpath("//*[text()='The email address is invalid']");
    private static final By COMPANY_ERROR_LBL = By.xpath("//*[text()=' The Company Name field is required']");
    private static final By TIMEOUT_ERROR_LBL = By.xpath("//*[text()='The VM Idle timeout field is required']");
    private static final By CORES_LIMIT_ERROR_LBL = By.xpath("//*[text()='Number of cores must be multiplied by 2 then number of machines']");
    private static final By POOL_SIZE_ERROR_LBL = By.xpath("//*[text()='Number of machines must be at least 1 and not greater than number of cores divided by 2 ']");
    private static final By COORD_PORT_ERROR_LBL = By.xpath("//*[text()=' The Coordinator Port No. field is required']");
    private static final By VM_PORT_ERROR_LBL = By.xpath("//*[text()=' The VM Port Range field is required']");
    private static final By QUOTA_LIMIT_MESSAGE = By.xpath("//*[contains(text(),'Operation results in exceeding quota limits of Core')]");
    private static final By DIFFERENT_USER_UPDATE_MESSAGE = By.xpath("//*[contains(text(),'please refer to the user to do any change in coordinator settings')]");


    private static EventFiringWebDriver eventWebDriver;
    private WebDriverWait wait;

    public OnboardingPageObject(EventFiringWebDriver driver) {
        eventWebDriver = driver;
        wait = new WebDriverWait(eventWebDriver, 40);
    }

    public void scaleToCloud() {
        eventWebDriver.findElement(TRY_INCREDICLOUD_BUTTON).click();
    }

    public void clickTryIncredicloud() {
        scaleToCloud();
        switch (CLOUD) {
            case "azure":
                wait.until(ExpectedConditions.visibilityOfElementLocated(AZURE_CLOUD_BUTTON));
                eventWebDriver.findElement(AZURE_CLOUD_BUTTON).click();
                if (ONBOARDING_TYPE.equals(OnboardingType.LOW_ONBOARDING)) {
                    eventWebDriver.findElement(AZURE_CLOUD_NO_I_DONT_BUTTON).click();
                    eventWebDriver.findElement(AZURE_CLOUD_LOW_PERMISSIONS_LOGIN_BUTTON).click();
                } else {
                    eventWebDriver.findElement(AZURE_CLOUD_YES_I_DO_BUTTON).click();
                    eventWebDriver.findElement(AZURE_CLOUD_ADMIN_LOGIN_BUTTON).click();
                }

                break;
            case "aws":
                wait.until(ExpectedConditions.visibilityOfElementLocated(AWS_CLOUD_BUTTON));
                eventWebDriver.findElement(AWS_CLOUD_BUTTON).click();
                if (ONBOARDING_TYPE.equals(OnboardingType.LOW_ONBOARDING)) {
                    eventWebDriver.findElement(AWS_CLOUD_AM_NOT_SURE_BUTTON).click();
                    eventWebDriver.findElement(AWS_CLOUD_LOW_PERMISSIONS_LOGIN_BUTTON).click();
                } else {
                    eventWebDriver.findElement(AWS_CLOUD_YES_I_DO_BUTTON).click();
                    eventWebDriver.findElement(AWS_CLOUD_ADMIN_LOGIN_BUTTON).click();
                }
                break;
        }
    }

    public void performOnboarding(OnboardingPage onboardingPage) {
        setGeneralSection(onboardingPage);
        setResourceManagmentSection(onboardingPage);
        setVMsSection(onboardingPage);
        clickSave(false);

//        selectSubscription();
//        selectRegion(onboardingPage);
//        enterName(onboardingPage);
//        enterMail(onboardingPage);
//        enterCompany(onboardingPage);
//        setVMsSection(onboardingPage);
//        addCustomTags(onboardingPage.getCustomTags());

    }

    public void performUpdate(OnboardingPage onboardingPage) {
        updateVmDetails(onboardingPage);
        clickSave(true);
        // waitForUpdateToFinish();
    }

    private void waitForUpdateToFinish() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(DOWNLOAD_BTN));
    }

    private void setGeneralSection(OnboardingPage onboardingPage) {
        enterName(onboardingPage);
        enterMail(onboardingPage);
        enterCompany(onboardingPage);
    }

    private void setResourceManagmentSection(OnboardingPage onboardingPage) {
        if (CLOUD.equals("azure")) {
            selectSubscription();
        }
        //selectRegion(onboardingPage);

        if (onboardingPage.getIsPrivateNetwork()) {
            selectPrivateNetwork(onboardingPage);
        }

        addCustomTags(onboardingPage.getCustomTags());
    }

    private void selectSubscription() {
        SystemActions.sleep(10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(SUBSCRIPTION_SELECT)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(SELECTION_LIST, "System Test"))));

        WebElement sub = eventWebDriver.findElement(By.xpath(String.format(SELECTION_LIST, "System Test")));
        Actions actions = new Actions(eventWebDriver);
        actions.moveToElement(sub).click().perform();
//        eventWebDriver.findElement(By.xpath(String.format(SELECTION_LIST, "System Test"))).click();
    }

    //    private void selectRegion(OnboardingPage onboardingPage){
//        SystemActions.sleep(10);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(REGION_SELECT)).click();
//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(SELECTION_LIST, onboardingPage.getRegion())))).click();
//    }

    private void selectPrivateNetwork(OnboardingPage onboardingPage) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PRIVATE_NETWORK_BTN)).click();

        if (CLOUD.equals("azure")) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(AZURE_RESOURCE_GROUP_SELECTION_LIST)).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(SELECTION_LIST, onboardingPage.getResourceGroupName()))));
            eventWebDriver.findElement(By.xpath(String.format(SELECTION_LIST, onboardingPage.getResourceGroupName()))).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(AZURE_VIRTUAL_NETWORK_SELECTION_LIST)).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(SELECTION_LIST, onboardingPage.getVirtualNetwork()))));
            eventWebDriver.findElement(By.xpath(String.format(SELECTION_LIST, onboardingPage.getVirtualNetwork()))).click();
        } else if (CLOUD.equals("aws")) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(AWS_VPC_SELECTION_LIST)).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(SELECTION_LIST, onboardingPage.getPrivateNetworkVpc()))));
            SystemActions.sleep(1);
            eventWebDriver.findElement(By.xpath(String.format(SELECTION_LIST, onboardingPage.getPrivateNetworkVpc()))).click();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(SUBNET_SELECTION_LIST)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(SELECTION_LIST, onboardingPage.getPrivateNetworkSubnet()))));
        eventWebDriver.findElement(By.xpath(String.format(SELECTION_LIST, onboardingPage.getPrivateNetworkSubnet()))).click();
        SystemActions.sleep(1);

    }

    private void selectRegion(OnboardingPage onboardingPage) {
        //        SystemActions.sleep(10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(REGION_SELECT)).click();
//        Actions actions = new Actions(eventWebDriver);
//        actions.moveToElement(eventWebDriver.findElement(By.xpath(String.format(SELECTION_LIST, onboardingPage.getRegion())))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(SELECTION_LIST, onboardingPage.getRegion()))));
        eventWebDriver.findElement(By.xpath(String.format(SELECTION_LIST, onboardingPage.getRegion()))).click();
    }

    private void enterName(OnboardingPage onboardingPage) {
        wait.until(ExpectedConditions.presenceOfElementLocated(FIRST_NAME_TB));
        eventWebDriver.findElement(FIRST_NAME_TB).sendKeys(onboardingPage.getFirstName());
        eventWebDriver.findElement(LAST_NAME_TB).sendKeys(onboardingPage.getLastName());
    }

    private void enterMail(OnboardingPage onboardingPage) {
        eventWebDriver.findElement(EMAIL_TB).sendKeys(onboardingPage.getEmail());
    }

    private void enterCompany(OnboardingPage onboardingPage) {
        eventWebDriver.findElement(COMPANY_TB).sendKeys(onboardingPage.getCompany());
    }

    private void setVMsSection(OnboardingPage onboardingPage) {
//        wait.until(ExpectedConditions.elementToBeClickable(MACHINE_TYPE_SELECT)).click();
//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(MACHINE_SELECTION_LIST, onboardingPage.getMachineType())))).click();
        onboardingPage.setMachineTypeInUI(eventWebDriver.findElement(MACHINE_TYPE_SELECT).getText());
        eventWebDriver.findElement(TIMEOUT_TB).clear();
        eventWebDriver.findElement(TIMEOUT_TB).sendKeys(String.valueOf(onboardingPage.getTimeout()));
        eventWebDriver.findElement(CORES_LIMIT_TB).clear();
        eventWebDriver.findElement(CORES_LIMIT_TB).sendKeys(String.valueOf(onboardingPage.getCoresLimit()));
        eventWebDriver.findElement(POOL_SIZE_TB).clear();
        eventWebDriver.findElement(POOL_SIZE_TB).sendKeys(String.valueOf(onboardingPage.getPoolSize()));
    }

    private void addCustomTags(HashMap<String, String> customTags) {
        Iterator it = customTags.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            addCustomTag(pair.getKey().toString(), pair.getValue().toString());
        }
    }

    private void addCustomTag(String key, String value) {
        eventWebDriver.findElement(CUSTOM_TAG_KEY).sendKeys(key);
        eventWebDriver.findElement(CUSTOM_TAG_VALUE).sendKeys(value);
        wait.until(ExpectedConditions.elementToBeClickable(CUSTOM_TAG_ADD_BTN)).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(CUSTOM_TAG_ADDED_TAG));
    }

    private void updateVmDetails(OnboardingPage onboardingPage) {
        wait.until(ExpectedConditions.elementToBeClickable(TIMEOUT_TB)).clear();
        eventWebDriver.findElement(TIMEOUT_TB).sendKeys(String.valueOf(onboardingPage.getTimeout()));
        eventWebDriver.findElement(CORES_LIMIT_TB).clear();
        eventWebDriver.findElement(CORES_LIMIT_TB).sendKeys(String.valueOf(onboardingPage.getCoresLimit()));
        eventWebDriver.findElement(POOL_SIZE_TB).clear();
        eventWebDriver.findElement(POOL_SIZE_TB).sendKeys(String.valueOf(onboardingPage.getPoolSize()));
    }

    private void clickSave(Boolean isOnBoardingUpdate) {
        eventWebDriver.findElement(SAVE_BTN).click();
        if (!isOnBoardingUpdate) {
            eventWebDriver.findElement(LICENSE_AGREEMENT).click();
        }
        eventWebDriver.findElement(ACTIVATE_BTN).click();
    }


    public boolean validateTenant() {
        SystemActions.sleep(10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(TENANT_SELECT)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(SELECTION_LIST, "None")))).click();
        return eventWebDriver.findElement(TENNANT_ERROR_LBL).isDisplayed();
    }

    public boolean validateSubscription() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(SUBSCRIPTION_SELECT)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(SELECTION_LIST, "None")))).click();
        return eventWebDriver.findElement(SUBSCRIPTION_ERROR_LBL).isDisplayed();
    }

    public boolean validateRegion() {
/*        switch (ENV){
            case "prod":*/
//                wait.until(ExpectedConditions.visibilityOfElementLocated(TENANT_SELECT)).click();
//                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(SELECTION_LIST, "8f26139b-cd59-4045-9294-9da3caa4bfd4")))).click();
//
//                wait.until(ExpectedConditions.visibilityOfElementLocated(SUBSCRIPTION_SELECT)).click();
//                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(SELECTION_LIST, " Pay-As-You-Go")))).click();
//                break;
//            case "uat":
        wait.until(ExpectedConditions.visibilityOfElementLocated(TENANT_SELECT)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(SELECTION_LIST, "bde8b775-ae5e-4043-bd01-ab0b17249045")))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(SUBSCRIPTION_SELECT)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(SELECTION_LIST, "System Test")))).click();
//                break;
//        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(REGION_SELECT)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(SELECTION_LIST, "None")))).click();
        return eventWebDriver.findElement(REGION_ERROR_LBL).isDisplayed();
    }

    public void validateFirstName() {
        eventWebDriver.findElement(FIRST_NAME_TB).sendKeys("1234");
        Assert.assertTrue(eventWebDriver.findElement(FIRST_NAME_TB).getText().isEmpty(), "Digits are not allowed in the First name field");
        eventWebDriver.findElement(SETTINGS_TAB).click();
        Assert.assertTrue(eventWebDriver.findElement(FNAME_ERROR_LBL).isDisplayed(), "Failed to locate First name error message");
    }

    public void validateLastName() {
        eventWebDriver.findElement(LAST_NAME_TB).sendKeys("1234");
        Assert.assertTrue(eventWebDriver.findElement(LAST_NAME_TB).getText().isEmpty(), "Digits are not allowed in the First name field");
        eventWebDriver.findElement(SETTINGS_TAB).click();
        Assert.assertTrue(eventWebDriver.findElement(LNAME_ERROR_LBL).isDisplayed(), "Failed to locate Last name error message");
    }

    public void validateEmail() {
        eventWebDriver.findElement(EMAIL_TB).click();
        eventWebDriver.findElement(SETTINGS_TAB).click();
        Assert.assertTrue(eventWebDriver.findElement(EMAIL_ERROR_LBL).isDisplayed(), "Failed to locate missing Email error message");

        eventWebDriver.findElement(EMAIL_TB).sendKeys("abc");
        Assert.assertTrue(eventWebDriver.findElement(INVALID_EMAIL_ERROR_LBL).isDisplayed(), "Failed to Invalid Email error message");
    }

    public void validateCompany() {
        eventWebDriver.findElement(COMPANY_TB).click();
        eventWebDriver.findElement(SETTINGS_TAB).click();
        Assert.assertTrue(eventWebDriver.findElement(COMPANY_ERROR_LBL).isDisplayed(), "Failed to locate missing Company error message");
    }

    public void validateTimeout() {
        //eventWebDriver.findElement(VMS_PANEL).click();
        eventWebDriver.findElement(TIMEOUT_TB).clear();
        eventWebDriver.findElement(SETTINGS_TAB).click();
        Assert.assertTrue(eventWebDriver.findElement(TIMEOUT_ERROR_LBL).isDisplayed(), "Failed to locate missing Timeout error message");

        eventWebDriver.findElement(TIMEOUT_TB).sendKeys("abc");
        Assert.assertTrue(eventWebDriver.findElement(TIMEOUT_TB).getText().isEmpty(), "Letters are not allowed in the Timeout field");
    }

    public void validateCoresLimit() {
        eventWebDriver.findElement(CORES_LIMIT_TB).clear();
        eventWebDriver.findElement(CORES_LIMIT_TB).sendKeys("1");
        eventWebDriver.findElement(SETTINGS_TAB).click();
        Assert.assertTrue(eventWebDriver.findElement(CORES_LIMIT_ERROR_LBL).isDisplayed(), "Failed to locate  Cores Limit limitation error message");

        eventWebDriver.findElement(TIMEOUT_TB).sendKeys("abc");
        Assert.assertTrue(eventWebDriver.findElement(CORES_LIMIT_TB).getText().isEmpty(), "Letters are not allowed in the Cores Limit field");
    }

    public void validatePoolSize() {
        eventWebDriver.findElement(POOL_SIZE_TB).clear();
        eventWebDriver.findElement(POOL_SIZE_TB).sendKeys("1");
        eventWebDriver.findElement(SETTINGS_TAB).click();
        Assert.assertTrue(eventWebDriver.findElement(POOL_SIZE_ERROR_LBL).isDisplayed(), "Failed to locate  Pool Size limitation error message");

        eventWebDriver.findElement(POOL_SIZE_TB).sendKeys("abc");
        Assert.assertTrue(eventWebDriver.findElement(POOL_SIZE_TB).getText().isEmpty(), "Letters are not allowed in the Cores Limit field");
    }

    public void validateCoordPort() {
        //eventWebDriver.findElement(NETWORK_PANEL).click();
        eventWebDriver.findElement(COORD_PORT_TB).clear();
        eventWebDriver.findElement(SETTINGS_TAB).click();
        Assert.assertTrue(eventWebDriver.findElement(COORD_PORT_ERROR_LBL).isDisplayed(), "Failed to locate missing Coord port error message");

        eventWebDriver.findElement(COORD_PORT_TB).sendKeys("abc");
        Assert.assertTrue(eventWebDriver.findElement(COORD_PORT_TB).getText().isEmpty(), "Letters are not allowed in the Coord Port field");
    }

    public void validateVmPort() {
        eventWebDriver.findElement(VM_PORT_TB).clear();
        eventWebDriver.findElement(SETTINGS_TAB).click();
        Assert.assertTrue(eventWebDriver.findElement(VM_PORT_ERROR_LBL).isDisplayed(), "Failed to locate missing VM port error message");

        eventWebDriver.findElement(VM_PORT_TB).sendKeys("abc");
        Assert.assertTrue(eventWebDriver.findElement(VM_PORT_TB).getText().isEmpty(), "Letters are not allowed in the VM Port field");
    }

    public boolean verifyQuotaLimitMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(QUOTA_LIMIT_MESSAGE));
        return eventWebDriver.findElement(QUOTA_LIMIT_MESSAGE).isDisplayed();
    }

    public boolean verifyDifferentUserUpdateMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(DIFFERENT_USER_UPDATE_MESSAGE));
        return eventWebDriver.findElement(DIFFERENT_USER_UPDATE_MESSAGE).isDisplayed();
    }


}
