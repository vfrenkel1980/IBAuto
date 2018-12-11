package Web.IBWeb;

import frameworkInfra.testbases.web.ibSite.StoreTestBase;
import frameworkInfra.utils.MailService;
import frameworkInfra.utils.SystemActions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static webInfra.ibWeb.pageObjects.StorePageObject.*;

public class StoreExistingUserTests extends StoreTestBase {

    @Test(testName = "Login")
    public void login(){
        signupPageObject.logIn(mailAddress, password);
    }

    @Test(testName = "Enter Store", dependsOnMethods = {"login"})
    public void enterStoreNoLicense(){
        storePageObject.enterStoreNoLicense();
    }

    @Test(testName = "Add Agents And Solutions And Check Pricing", dependsOnMethods = {"enterStoreNoLicense"})
    public void addAgentsAndSolutionsAndCheckPricing(){
        storePageObject.changeNumberOfDevMachines("5");
        storePageObject.changeNumOfCores("8");
        storePageObject.addMoreMachines();
        storePageObject.addSolutionsFromList();
        storePageObject.addSolutionByName(devTools);
        storePageObject.addSolutionByName(csharp);
        storePageObject.addSolutionByName(nintendo3Ds);
        storePageObject.addSolutionByName(xbox1);
    }

    @Test(testName = "Verify Returned Store Price", dependsOnMethods = {"addAgentsAndSolutionsAndCheckPricing"})
    public void verifyReturnedStorePrice(){
        int sum = 5 * _8Cores + 4 * _64Cores + devToolsPrice + csharpPrice + nintendo3DsPrice + xbox1Price;
        Assert.assertEquals(storePageObject.verifySum(), "$" + Integer.toString(sum) + ".00",  "Total store price does not match expected");
    }

    @Test(testName = "Remove Solutions And Agents", dependsOnMethods = {"verifyReturnedStorePrice"})
    public void removeSolutionsAndAgents(){
        storePageObject.removeSolutionFromList(csharp);
        storePageObject.removeSolutionFromList(nintendo3Ds);
        storePageObject.reduceSolutionByName(devTools);
        storePageObject.removeAddedMachines();
        storePageObject.changeNumberOfDevMachines("0");
    }

    @Test(testName = "Select Solution And Continue To Payment", dependsOnMethods = {"removeSolutionsAndAgents"})
    public void selectSolutionAndContinueToPayment(){
        storePageObject.continueToCheckout();
        storePageObject.continueToCheckoutPopup();
        String sum = "$" + Integer.toString(xbox1Price) + ".00";
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
        Assert.assertTrue(MailService.checkMailBySubject(host, mailAddress3, password,"Sandbox: Incredibuild License File"));
        MailService.deleteMail(host, mailAddress3, password);
    }

}
