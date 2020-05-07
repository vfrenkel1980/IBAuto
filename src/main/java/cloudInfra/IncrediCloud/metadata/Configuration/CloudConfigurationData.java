package cloudInfra.IncrediCloud.metadata.Configuration;


import com.fasterxml.jackson.annotation.JsonProperty;

public class CloudConfigurationData {
    //public CommonConfigurationData commonConfigurationData;
    @JsonProperty()
    private AzureConfigurationData azureConfigurationData;

    @JsonProperty()
    private AwsConfigurationData awsConfigurationData;

    public AzureConfigurationData getAzureConfigurationData() {
        return azureConfigurationData;
    }

    public void setAzureConfigurationData(AzureConfigurationData azureConfigurationData) {
        this.azureConfigurationData = azureConfigurationData;
    }

    public AwsConfigurationData getAwsConfigurationData() {
        return awsConfigurationData;
    }

    public void setAwsConfigurationData(AwsConfigurationData awsConfigurationData) {
        this.awsConfigurationData = awsConfigurationData;
    }

    @Override
    public String toString() {
        return "CloudConfigurationData{" +
                "azureConfigurationData=" + azureConfigurationData +
                ", awsConfigurationData=" + awsConfigurationData +
                '}';
    }
}

