package cloudInfra.IncrediCloud.metadata.Enums;

public enum CloudStatusType {
    COORDINATOR_IS_DEACTIVATING("Coordinator is Deactivating"),
    COORDINATOR_IS_DEACTIVATED("Coordinator does not exist");
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String _type) {
        type = _type;
    }

    CloudStatusType(String type) {
        setType(type);
    }

}

