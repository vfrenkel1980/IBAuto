package cloudInfra.IncrediCloud.metadata.Enums;

public enum CloudType {
    AZURE("azure"),
    AWS("aws");

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String _type) {
        type = _type;
    }

    CloudType(String type) {
        setType(type);
    }

}
