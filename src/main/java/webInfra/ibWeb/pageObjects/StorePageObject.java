package webInfra.ibWeb.pageObjects;

import com.google.common.collect.Iterables;
import frameworkInfra.utils.SystemActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import webInfra.ibWeb.pages.SignupPage;

import java.util.List;

import static frameworkInfra.testbases.web.ibSite.IbWebTestBase.mailAddress;

public class StorePageObject {

    private static final By ENTER_STORE = By.xpath("//a[contains(text(),'BUY NOW')]");
    private static final By SELECT_EXISTING_LICENSE_CB = By.xpath("//*[@id=\"has_existing_license\"]");
    private static final By CREATE_NEW_LICENSE_CB = By.xpath("//*[@id=\"new_license\"]");
    private static final By I_UNDERSTAND_BTN = By.xpath("//button[contains(text(),'I understand')]");
    private static final By EXISTING_LICENSES_DDL = By.xpath("//*[@id=\"existing-license\"]");
    private static final By NEW_LICENSE_NAME_TB = By.xpath("//*[@id=\"license_name\"]");
    private static final By NEW_LICENSE_MAIL_TB = By.xpath("//*[@id=\"licensee_mail\"]");
    private static final By CONTINUE_BTN = By.xpath("//button[contains(text(),'Continue')]");
    private static final By ADD_MORE_MACHINES_BTN = By.xpath("//button[contains(text(),'Add more machines')]");
    private static final By NUM_OF_DEV_MACHINES_TB = By.xpath("//*[@id=\"numberOfDeveloperMachines\"]");
    private static final By NUM_OF_CORES_DDL = By.xpath("//*[@id=\"numberOfCores\"]");
    private static final By SOLUTIONS_DDL = By.xpath("//*[@id=\"selectSolutions\"]");
    private static final String ADD_SOLUTIONS = "//*[@title=\"%s\"]/../descendant::*[@src=\"/ibonlinestore/images/solutionPlus.png\"]";
    private static final String REDUCE_SOLUTIONS = "//*[@title=\"%s\"]/../descendant::*[@src=\"/ibonlinestore/images/solutionMinus.png\"]";
    private static final By SOLUTIONS_PRICE = By.xpath("//*[@class=\"solutionPrice\"]");
    private static final String REMOVE_SOLUTION = "//*[@title=\"%s\"]/../descendant::*[@class=\"solutionRemove\"]";
    private static final By REMOVE_AGENTS = By.xpath("//*[@class=\"agentRemove\"]");
    private static final By CALCULATED_PRICE = By.xpath("//*[@class=\"calculatedPrice\"]");
    private static final By CONTINUE_TO_CHECKOUT_BTN = By.xpath("//button[contains(text(),'continue to checkout')]");
    private static final By ADD_AGENT_PACKAGE_BTN = By.xpath("//*[contains(text(),'Add an agent package')]");
    private static final By CONTINUE_TO_CHECKOUT_POPUP_BTN = By.xpath("//*[contains(text(),'Continue to checkout')]");
    private static final By VS_BUNDLE_BTN = By.xpath("//*[contains(text(),'IB400')]/following-sibling::button[@id=\"orderBundleButt\"]");
    private static final By DEV_TOOLS_BUNDLE_BTN = By.xpath("//*[contains(text(),'IB410')]/following-sibling::button[@id=\"orderBundleButt\"]");
    private static final By BUNDLE_POPUP_PURCHASE_BTN = By.xpath("//a[text() = 'Purchase']");
    private static final By BUNDLE_POPUP_CANCEL_BTN = By.xpath("//a[contains(text(),'Cancel')]");
    private static final By PROCEED_POPUP_BTN = By.xpath("//a[text() = 'Proceed']");
    private static final By GO_BACK_POPUP_BTN = By.xpath("//a[text() = 'Go back']");


    //available solutions
    public static String nintendo = "IncrediBuild for Nintendo Switch (annual)";
    public static String makeAndBuild = "IncrediBuild for Make & Build Tools (annual)";
    public static String csharp = "IncrediBuild for C# (annual)";
    public static String devTools = "IncrediBuild for Dev Tools (annual)";
    public static String wiiU = "IncrediBuild for Wii U (annual)";
    public static String nintendo3Ds = "IncrediBuild for 3DS (annual)";
    public static String playstation = "IncrediBuild for Sony Playstation4 and Vita (annual)";
    public static String xbox1 = "IncrediBuild for Xbox1 (annual)";
    public static String vsC = "IncrediBuild for Visual Studio C/C++ (annual)";
    public static String devToolsBundle = "FreeDev Premium - with Make and Build tools solutions & DEV tools solutions (annual)";
    public static String vsBundle = "FreeDev Premium - with Visual Studio C/C++ solutions & DEV tools solutions (annual)";

    //prices
    public static int nintendoPrice = 99;
    public static int makeAndBuildPrice = 99;
    public static int csharpPrice = 75;
    public static int devToolsPrice = 125;
    public static int wiiUPrice = 99;
    public static int nintendo3DsPrice = 99;
    public static int playstationPrice = 99;
    public static int xbox1Price = 99;
    public static int vsCPrice = 99;
    public static int bundlePrice = 1495;
    public static int _4Cores = 395;
    public static int _8Cores = 495;
    public static int _16Cores = 595;
    public static int _24Cores = 695;
    public static int _32Cores = 795;
    public static int _48Cores = 995;
    public static int _64Cores = 1495;


    private EventFiringWebDriver eventWebDriver;
    private WebDriverWait wait;

    public StorePageObject(EventFiringWebDriver eventWebDriver) {
        this.eventWebDriver = eventWebDriver;
        wait = new WebDriverWait(eventWebDriver, 60);
    }

    public void enterStoreExistingLicense(SignupPage sp){
        wait.until(ExpectedConditions.visibilityOfElementLocated(ENTER_STORE)).click();
        eventWebDriver.findElement(SELECT_EXISTING_LICENSE_CB).click();
        wait.until(ExpectedConditions.elementToBeClickable(I_UNDERSTAND_BTN)).click();
        Select license = new Select(eventWebDriver.findElement(EXISTING_LICENSES_DDL));
        license.selectByVisibleText(sp.getLicenseeName());
        SystemActions.sleep(1);
        wait.until(ExpectedConditions.elementToBeClickable(CONTINUE_BTN)).click();

    }

    public void enterStoreNoLicense(){
        eventWebDriver.findElement(ENTER_STORE).click();
        wait.until(ExpectedConditions.elementToBeClickable(NEW_LICENSE_MAIL_TB));
        eventWebDriver.findElement(NEW_LICENSE_NAME_TB).sendKeys("NewLic");
        eventWebDriver.findElement(NEW_LICENSE_MAIL_TB).sendKeys(mailAddress);
        eventWebDriver.findElement(CONTINUE_BTN).click();
    }

    public void addSolutionByName(String solName){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(ADD_SOLUTIONS, solName)))).click();
    }

    public void reduceSolutionByName(String solName){
        SystemActions.sleep(3);
        eventWebDriver.findElement(By.xpath(String.format(REDUCE_SOLUTIONS, solName))).click();
    }

    public void continueToCheckout(){
        wait.until(ExpectedConditions.elementToBeClickable(CONTINUE_TO_CHECKOUT_BTN)).click();
    }

    public void continueToCheckoutPopup(){
        SystemActions.sleep(1);
        wait.until(ExpectedConditions.elementToBeClickable(CONTINUE_TO_CHECKOUT_POPUP_BTN)).click();
    }

    public boolean verifyNoAgentsSelectedPopUP(){
        return eventWebDriver.findElement(ADD_AGENT_PACKAGE_BTN).isDisplayed();
    }

    public void clickAddAgentInPopUP(){
        eventWebDriver.findElement(ADD_AGENT_PACKAGE_BTN).click();
    }

    public void changeNumberOfDevMachines(String numOfAgents){
        eventWebDriver.findElement(NUM_OF_DEV_MACHINES_TB).clear();
        eventWebDriver.findElement(NUM_OF_DEV_MACHINES_TB).sendKeys(numOfAgents);
    }

    public void changeNumOfCores(String cores){
        Select numOfCores = new Select(eventWebDriver.findElement(NUM_OF_CORES_DDL));
        switch (cores){
            case "4":
                numOfCores.selectByIndex(0);
                break;
            case "8":
                numOfCores.selectByIndex(1);
                break;
            case "16":
                numOfCores.selectByIndex(2);
                break;
            case "32":
                numOfCores.selectByIndex(3);
                break;
            case "64":
                numOfCores.selectByIndex(4);
                break;
            case "12":
                numOfCores.selectByIndex(5);
                break;
            case "48":
                numOfCores.selectByIndex(6);
                break;
            case "24":
                numOfCores.selectByIndex(7);
                break;
        }
    }

    public void addMoreMachines(){
        clickAddMoreMachines();
        List<WebElement> agentMachines = eventWebDriver.findElements(NUM_OF_DEV_MACHINES_TB);
        List<WebElement> numOfCores = eventWebDriver.findElements(NUM_OF_CORES_DDL);
        agentMachines.get(1).clear();
        agentMachines.get(1).sendKeys("4");
        Select select2 = new Select(numOfCores.get(1));
        select2.selectByIndex(4);
    }

    public void removeAddedMachines(){
        List<WebElement> agentMachines = eventWebDriver.findElements(REMOVE_AGENTS);
        for (WebElement agentMachine : Iterables.skip(agentMachines,1)) {
            agentMachine.click();
        }
    }

    public void clickAddMoreMachines(){
        SystemActions.sleep(1);
        wait.until(ExpectedConditions.elementToBeClickable(ADD_MORE_MACHINES_BTN)).click();
    }

    public String verifySum(){
        return eventWebDriver.findElement(CALCULATED_PRICE).getText();
    }

    public void addSolutionsFromList(){
        Select solutions = new Select(eventWebDriver.findElement(SOLUTIONS_DDL));
        solutions.selectByVisibleText(xbox1);
        solutions.selectByVisibleText(playstation);
        solutions.selectByVisibleText(wiiU);
        solutions.selectByVisibleText(nintendo3Ds);
        solutions.selectByVisibleText(csharp);
    }

    public void removeSolutionFromList(String solName){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(REMOVE_SOLUTION, solName)))).click();
    }

    public void selectBundleByName(String bundle){
        SystemActions.sleep(1);
        switch (bundle){
            case "devtools" :
               eventWebDriver.findElement(DEV_TOOLS_BUNDLE_BTN).click();
                break;
            case "visualstudio" :
                eventWebDriver.findElement(VS_BUNDLE_BTN).click();
        }
    }

    public void  cancelBundlePopup(){
        SystemActions.sleep(1);
        wait.until(ExpectedConditions.elementToBeClickable(BUNDLE_POPUP_CANCEL_BTN)).click();
    }

    public void approveBundlePopup(){
        SystemActions.sleep(1);
        wait.until(ExpectedConditions.elementToBeClickable(BUNDLE_POPUP_PURCHASE_BTN)).click();
    }

    public boolean verifyStorePageLoaded(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(ADD_MORE_MACHINES_BTN));
        return eventWebDriver.findElement(ADD_MORE_MACHINES_BTN).isDisplayed();
    }

    public boolean verifyMoreAgentsThanSolutions(){
        return eventWebDriver.findElement(PROCEED_POPUP_BTN).isDisplayed();
    }

    public void clickGoBackPopupBtn(){
        SystemActions.sleep(3);
        eventWebDriver.findElement(GO_BACK_POPUP_BTN).click();
    }


}
