package cloudInfra.IncrediCloud.metadata.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AzureConfigurationData extends CommonConfigurationData {
    // Credentials
    @JsonProperty()
    private String user;

    @JsonProperty()
    private String password;

    // Low boarding
    @JsonProperty()
    private String tenantID;

    @JsonProperty()
    private String applicationID;

    @JsonProperty()
    private String clientSecret;

    //Private network
    @JsonProperty()
    private String resourceGroupName;

    @JsonProperty()
    private String virtualNetwork;

    @JsonProperty()
    private String subnet;

    @JsonProperty()
    private String additionalSubnet;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getResourceGroupName() {
        return resourceGroupName;
    }

    public void setResourceGroupName(String resourceGroupName) {
        this.resourceGroupName = resourceGroupName;
    }

    public String getVirtualNetwork() {
        return virtualNetwork;
    }

    public void setVirtualNetwork(String virtualNetwork) {
        this.virtualNetwork = virtualNetwork;
    }

    public String getSubnet() {
        return subnet;
    }

    public void setSubnet(String subnet) {
        this.subnet = subnet;
    }

    public String getAdditionalSubnet() {
        return additionalSubnet;
    }

    public void setAdditionalSubnet(String additionalSubnet) {
        this.additionalSubnet = additionalSubnet;
    }

    @Override
    public String toString() {
        return "AzureConfigurationData{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", tenantID='" + tenantID + '\'' +
                ", applicationID='" + applicationID + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", resourceGroupName='" + resourceGroupName + '\'' +
                ", virtualNetwork='" + virtualNetwork + '\'' +
                ", subnet='" + subnet + '\'' +
                ", additionalSubnet='" + additionalSubnet + '\'' +
                '}';
    }
}
