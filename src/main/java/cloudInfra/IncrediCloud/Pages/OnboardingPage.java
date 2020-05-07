package cloudInfra.IncrediCloud.Pages;

import java.util.HashMap;

public class OnboardingPage {

    private String region;
    private String firstName;
    private String lastName;
    private String email;
    private String company;
    private String resourceGroupName="";
    private String machineType;
    private String machineTypeInUI;
    private int timeout;
    private int coresLimit;
    private int poolSize;
    private int coordPort;
    private int vmPort;
    private HashMap<String, String> customTags;
    private Boolean isPrivateNetwork = false;
    private String virtualNetwork ="";
    private String privateNetworkVpc = "";
    private String privateNetworkSubnet = "";


    public OnboardingPage(String region, String firstName, String lastName, String email, String company, String machineType, int timeout, int coresLimit, int poolSize, int coordPort, int vmPort) {
        this.region = region;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.company = company;
        this.machineType = machineType;
        this.machineTypeInUI = "";
        this.timeout = timeout;
        this.coresLimit = coresLimit;
        this.poolSize = poolSize;
        this.coordPort = coordPort;
        this.vmPort = vmPort;
    }

    public String getRegion() {
        return region;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getResourceGroupName() {
        return resourceGroupName;
    }

    public void setResourceGroupName(String resourceGroupName) {
        this.resourceGroupName = resourceGroupName;
    }

    public String getMachineType() {
        return machineType;
    }

    public String getMachineTypeInUI() {
        return machineTypeInUI;
    }

    public String getCompany() {
        return company;
    }

    public int getTimeout() {
        return timeout;
    }

    public int getCoresLimit() {
        return coresLimit;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public int getCoordPort() {
        return coordPort;
    }

    public int getVmPort() {
        return vmPort;
    }

    public void setMachineTypeInUI(String machineTypeInUI) {
        this.machineTypeInUI = machineTypeInUI;
    }

    public HashMap<String, String> getCustomTags() {
        return customTags;
    }

    public void setCustomTags(HashMap<String, String> customTags) {
        this.customTags = customTags;
    }

    public Boolean getIsPrivateNetwork() {
        return isPrivateNetwork;
    }

    public void setIsPrivateNetwork(Boolean privateNetwork) {
        isPrivateNetwork = privateNetwork;
    }

    public String getVirtualNetwork() {
        return virtualNetwork;
    }

    public void setVirtualNetwork(String virtualNetwork) {
        this.virtualNetwork = virtualNetwork;
    }

    public String getPrivateNetworkVpc() {
        return privateNetworkVpc;
    }

    public void setPrivateNetworkVpc(String privateNetworkVpc) {
        this.privateNetworkVpc = privateNetworkVpc;
    }

    public String getPrivateNetworkSubnet() {
        return privateNetworkSubnet;
    }

    public void setPrivateNetworkSubnet(String privateNetworkSubnet) {
        this.privateNetworkSubnet = privateNetworkSubnet;
    }

    @Override
    public String toString() {
        return "OnboardingPage{" +
                "region='" + region + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", machineType='" + machineType + '\'' +
                ", machineTypeInUI='" + machineTypeInUI + '\'' +
                ", timeout=" + timeout +
                ", coresLimit=" + coresLimit +
                ", poolSize=" + poolSize +
                ", coordPort=" + coordPort +
                ", vmPort=" + vmPort +
                ", customTags=" + customTags +
                '}';
    }
}


