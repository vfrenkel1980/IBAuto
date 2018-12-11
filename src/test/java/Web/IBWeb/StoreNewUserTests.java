package Web.IBWeb;

import frameworkInfra.testbases.web.ibSite.StoreTestBase;
import frameworkInfra.utils.MailService;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static webInfra.ibWeb.pageObjects.StorePageObject.*;

public class StoreNewUserTests extends StoreTestBase {

    @Test(testName = "Login")
    public void login(){
        signupPageObject.logIn(mailAddress, password);
    }

/*    @Test(testName = "Sign New User")
    public void signNewUser(){
        signupPageObject.signUpNewUser(sp);
    }*/

    @Test(testName = "Enter Store", dependsOnMethods = {"login"})
    public void enterStore(){
        storePageObject.enterStoreExistingLicense(sp);
    }

    @Test(testName = "Verify Missing Agent Pop Up", dependsOnMethods = {"enterStore"})
    public void verifyMissingAgentPopUp(){
        storePageObject.addSolutionByName(devTools);
        storePageObject.addSolutionByName(vsC);
        storePageObject.continueToCheckout();
        Assert.assertTrue(storePageObject.verifyNoAgentsSelectedPopUP(), "The \"No Agents Selected\" popup wasn't displayed");
        storePageObject.clickAddAgentInPopUP();
    }

    @Test(testName = "Add Agents And Solutions And Check Pricing", dependsOnMethods = {"verifyMissingAgentPopUp"})
    public void addAgentsAndSolutionsAndCheckPricing(){
        storePageObject.changeNumberOfDevMachines("3");
        storePageObject.changeNumOfCores();
        storePageObject.addMoreMachines();
        storePageObject.addSolutionsFromList();
        storePageObject.addSolutionByName(wiiU);
        storePageObject.addSolutionByName(wiiU);
        storePageObject.addSolutionByName(playstation);
        storePageObject.addSolutionByName(xbox1);
    }

    @Test(testName = "Verify Returned Store Price", dependsOnMethods = {"addAgentsAndSolutionsAndCheckPricing"})
    public void verifyReturnedStorePrice(){
        int sum = 3 * _16Cores + 4 * _64Cores + 2 * wiiUPrice + playstationPrice + xbox1Price + devToolsPrice + vsCPrice;
        Assert.assertEquals(storePageObject.verifySum(), "$" + Integer.toString(sum) + ".00",  "Total store price does not match expected");
    }

    @Test(testName = "Remove Solutions And Agents", dependsOnMethods = {"verifyReturnedStorePrice"})
    public void removeSolutionsAndAgents(){
        storePageObject.removeSolutionFromList(wiiU);
        storePageObject.removeSolutionFromList(playstation);
        storePageObject.removeSolutionFromList(xbox1);
        storePageObject.removeAddedMachines();
        storePageObject.changeNumberOfDevMachines("1");
    }

    @Test(testName = "Select Bundle And Verify Cart Page", dependsOnMethods = {"removeSolutionsAndAgents"})
    public void selectBundleAndVerifyCartPage(){
        storePageObject.selectBundleByName("visualstudio");
        storePageObject.cancelBundlePopup();
        storePageObject.selectBundleByName("devtools");
        storePageObject.approveBundlePopup();
        Assert.assertTrue(cartPageObject.verifyCartPageLoaded(), "Cart page is not loaded");
    }

    @Test(testName = "Verify Bundle Cart Page Functionality", dependsOnMethods = {"selectBundleAndVerifyCartPage"})
    public void verifyBundleCartPageFunctionality(){
        cartPageObject.addSolutionByNameCartPage(devToolsBundle);
        Assert.assertEquals(cartPageObject.getTotalSum(), "$" + Integer.toString(bundlePrice) + ".00", "Bundle price did not match expected, Was there a change in bundle?");
    }

    @Test(testName = "Select Solutions And Remove Them From Cart", dependsOnMethods = {"verifyBundleCartPageFunctionality"})
    public void selectSolutionsAndRemoveThemFromCart(){
        cartPageObject.navigateBack();
        storePageObject.addSolutionByName(vsC);
        storePageObject.addSolutionByName(vsC);
        storePageObject.addSolutionByName(makeAndBuild);
        storePageObject.addSolutionByName(makeAndBuild);
        storePageObject.addSolutionByName(devTools);
        storePageObject.addSolutionByName(devTools);
        storePageObject.continueToCheckout();
        storePageObject.continueToCheckoutPopup();
        String sum = "$" + Integer.toString(2 * vsCPrice + 2 * makeAndBuildPrice + 2 * devToolsPrice) + ".00";
        Assert.assertEquals(cartPageObject.getTotalSum(), sum, "Cart sum did not match expected sum.");
        cartPageObject.removeItemFromCart(vsC);
        sum = "$" + Integer.toString(2 * makeAndBuildPrice + 2 * devToolsPrice) + ".00";
        Assert.assertEquals(cartPageObject.getTotalSum(), sum, "Cart sum did not match expected sum.");
        cartPageObject.reduceSolutionByNameCartPage(makeAndBuild);
        cartPageObject.reduceSolutionByNameCartPage(makeAndBuild);
        sum = "$" + Integer.toString(2 * devToolsPrice) + ".00";
        Assert.assertEquals(cartPageObject.getTotalSum(), sum, "Cart sum did not match expected sum.");
        cartPageObject.removeItemFromCart(devTools);
        storePageObject.verifyStorePageLoaded();
    }

    @Test(testName = "Select Solution And Continue To Payment", dependsOnMethods = {"selectSolutionsAndRemoveThemFromCart"})
    public void selectSolutionAndContinueToPayment(){
        storePageObject.addSolutionByName(vsC);
        storePageObject.continueToCheckout();
        storePageObject.continueToCheckoutPopup();
        String sum = "$" + Integer.toString(vsCPrice) + ".00";
        Assert.assertEquals(cartPageObject.getTotalSum(), sum, "Cart sum did not match expected sum.");
    }

    @Test(testName = "Verify Eula And Privacy", dependsOnMethods = {"selectSolutionAndContinueToPayment"})
    public void verifyEulaAndPrivacy(){
        cartPageObject.clickContinueToPayment();
        //TODO: verify eula and privacy messages
        cartPageObject.clickEulaCb();
        cartPageObject.clickPrivacyCb();
        //TODO: verify eula and privacy messages
        cartPageObject.clickContinueToPayment();
    }

    @Test(testName = "Verify Payment Success", dependsOnMethods = {"verifyEulaAndPrivacy"})
    public void verifyPaymentSuccess(){
        tranzilaPageObject.enterCompany();
        tranzilaPageObject.enterName();
        tranzilaPageObject.enterCardNumber();
        tranzilaPageObject.selectCardExpDate();
        tranzilaPageObject.enterSecurityCode();
        Assert.assertTrue(tranzilaPageObject.clickPayButtonAndVerifyOrderComplete(), "Order confirmation page did not load");
    }

    @Test(testName = "Verify License IN Mail", dependsOnMethods = {"verifyPaymentSuccess"})
    public void verifyLicenseInMail(){
        SystemActions.sleep(60);
        Assert.assertTrue(MailService.checkMailBySubject(host, mailAddress, password,"Sandbox: Incredibuild License File"));
        MailService.deleteMail(host, mailAddress, password);
    }



}
