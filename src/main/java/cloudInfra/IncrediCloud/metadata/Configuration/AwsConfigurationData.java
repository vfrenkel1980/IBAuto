package cloudInfra.IncrediCloud.metadata.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AwsConfigurationData extends CommonConfigurationData {
    // Credentials
    @JsonProperty()
    private String accessKey;

    @JsonProperty()
    private String secretKey;

    // Low boarding
    @JsonProperty()
    private String roleARN;

    @JsonProperty()
    private String externalID;

    // Private network
    @JsonProperty()
    private String vpc;

    @JsonProperty()
    private String subnet;

    @JsonProperty()
    private String additionalSubnet;


    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRoleARN() {
        return roleARN;
    }

    public void setRoleARN(String roleARN) {
        this.roleARN = roleARN;
    }

    public String getExternalID() {
        return externalID;
    }

    public void setExternalID(String externalID) {
        this.externalID = externalID;
    }

    public String getVpc() {
        return vpc;
    }

    public void setVpc(String vpc) {
        this.vpc = vpc;
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
        return "AwsConfigurationData{" +
                "accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", roleARN='" + roleARN + '\'' +
                ", externalID='" + externalID + '\'' +
                ", vpc='" + vpc + '\'' +
                ", subnet='" + subnet + '\'' +
                ", additionalSubnet='" + additionalSubnet + '\'' +
                '}';
    }
}
