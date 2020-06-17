package cloudInfra.IncrediCloud.metadata.Enums;

public enum CloudStatusType {
    NONE(-1, "None"),
    IS_DEACTIVATED(0, "Coordinator does not exist"),
    CLOUD_IS_ENABLED(1, "OK"),
    CREATING_POOL(2, "Creating pool - please wait"),
    IS_DEACTIVATING( 3,"Coordinator is Deactivating");

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String _type) {
        type = _type;
    }

    CloudStatusType(int status, String type) {
        setType(type);
    }

}

