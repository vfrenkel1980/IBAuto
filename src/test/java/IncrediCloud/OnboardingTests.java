package IncrediCloud;

import frameworkInfra.testbases.incrediCloud.OnboardingTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @brief Tests the onboarding webpage, field validations.
 * @details Requires an enterprise license
 *
 */

public class OnboardingTests extends OnboardingTestBase {

    @Test(testName = "Validate Tenant")
    public void validateTenant(){
        onboardingPageObject.clickTryIncredicloud();
        cloudRegistrationPageObject.selectUser(PROD_USER, ONBOARDING_TYPE);
        Assert.assertTrue(onboardingPageObject.validateTenant(), "Failed to locate Tenant error message");
    }

    @Test(testName = "Validate Subscription", dependsOnMethods = { "validateTenant"})
    public void validateSubscription(){
        Assert.assertTrue(onboardingPageObject.validateSubscription(), "Failed to locate Subscription error message");
    }

    @Test(testName = "Validate Region", dependsOnMethods = { "validateSubscription"})
    public void validateRegion(){
        Assert.assertTrue(onboardingPageObject.validateRegion(), "Failed to locate Region error message");
    }

    @Test(testName = "Validate First Name", dependsOnMethods = { "validateRegion"})
    public void validateFirstName(){
        onboardingPageObject.validateFirstName();
    }

    @Test(testName = "Validate Last Name", dependsOnMethods = { "validateFirstName"})
    public void validateLastName(){
        onboardingPageObject.validateLastName();
    }

    @Test(testName = "Validate Email", dependsOnMethods = { "validateLastName"})
    public void validateEmail(){
        onboardingPageObject.validateEmail();
    }

    @Test(testName = "Validate Company", dependsOnMethods = { "validateEmail"})
    public void validateCompany(){
        onboardingPageObject.validateCompany();
    }

    @Test(testName = "Validate Timeout", dependsOnMethods = { "validateCompany"})
    public void validateTimeout(){
        onboardingPageObject.validateTimeout();
    }

    @Test(testName = "Validate Cores Limit", dependsOnMethods = { "validateTimeout"})
    public void validateCoresLimit(){
        onboardingPageObject.validateCoresLimit();
    }

    @Test(testName = "Validate Pool Size", dependsOnMethods = { "validateCoresLimit"})
    public void validatePoolSize(){
        onboardingPageObject.validatePoolSize();
    }

    @Test(testName = "Validate Coord Port", dependsOnMethods = { "validatePoolSize"})
    public void validateCoordPort(){
        onboardingPageObject.validateCoordPort();
    }

    @Test(testName = "Validate VM Port", dependsOnMethods = { "validateCoordPort"})
    public void validateVmPort(){
        onboardingPageObject.validateVmPort();
    }

}
