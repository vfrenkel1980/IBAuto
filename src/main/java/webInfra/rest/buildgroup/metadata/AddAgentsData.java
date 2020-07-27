package webInfra.rest.buildgroup.metadata;

public class AddAgentsData {
    private String IP;
    private String NAME;

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getName() {
        return NAME;
    }

    public void setName(String name) {
        NAME = name;
    }

    @Override
    public String toString() {
        return "AddAgents{" +
                "IP='" + IP + '\'' +
                ", Name='" + NAME + '\'' +
                '}';
    }
}
