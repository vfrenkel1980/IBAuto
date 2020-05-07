package cloudInfra.IncrediCloud.metadata;

import java.util.HashMap;

public class VirtualMachineInfo {
    private HashMap<String, String> tags;
    private String vmSize;

    public VirtualMachineInfo() {
        tags = new HashMap<>();
        vmSize = "";
    }

    public HashMap<String, String> getTags() {
        return tags;
    }

    public void setTags(HashMap<String, String> tags) {
        this.tags = tags;
    }

    public String getVmSize() {
        return vmSize;
    }

    public void setVmSize(String vmSize) {
        this.vmSize = vmSize;
    }

    @Override
    public String toString() {
        return "VirtualMachineInfo{" +
                "tags=" + tags +
                ", vmSize='" + vmSize + '\'' +
                '}';
    }
}
