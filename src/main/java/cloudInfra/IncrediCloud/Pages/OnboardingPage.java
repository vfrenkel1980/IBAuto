package cloudInfra.IncrediCloud.Pages;

public class OnboardingPage {

    private String region;
    private String firstName;
    private String lastName;
    private String email;
    private String company;
    private String machineType;
    private int timeout;
    private int coresLimit;
    private int poolSize;
    private int coordPort;
    private int vmPort;

    public OnboardingPage(String region, String firstName, String lastName, String email, String company,String machineType, int timeout, int coresLimit, int poolSize, int coordPort, int vmPort) {
        this.region = region;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.company = company;
        this.machineType = machineType;
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

    public String getMachineType() {
        return machineType;
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
}


