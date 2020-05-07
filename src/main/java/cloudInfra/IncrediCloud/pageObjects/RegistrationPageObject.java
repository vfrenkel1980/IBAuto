package cloudInfra.IncrediCloud.pageObjects;

import cloudInfra.IncrediCloud.metadata.Configuration.CloudConfigurationData;
import cloudInfra.IncrediCloud.metadata.Enums.OnboardingType;

public class RegistrationPageObject {
    protected CloudConfigurationData cloudConfigurationData;

    public RegistrationPageObject() {

    }
    public RegistrationPageObject(CloudConfigurationData ccd) {
        cloudConfigurationData = ccd;
    }

    public void selectUser(String user, OnboardingType onboardingType) {

    }
}
