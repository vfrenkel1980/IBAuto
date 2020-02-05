package ibInfra.ibExecs.metadata;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Build {
    @JacksonXmlProperty(isAttribute = true)
    private String BuildTitle;

    @JacksonXmlProperty(isAttribute = true)
    private String BuildProgressPercent;

    @JacksonXmlProperty(isAttribute = true)
    private String BuildAssignedCPUCount;

    @JacksonXmlProperty(isAttribute = true)
    private String WorkPowerMHz;

    @JacksonXmlProperty(isAttribute = true)
    private String WorkPowerCPUs;

    public Build() {
        BuildTitle = "";
        BuildProgressPercent = "";
        BuildAssignedCPUCount = "";
        WorkPowerMHz = "";
        WorkPowerCPUs = "";
    }

    public String getBuildTitle() {
        return BuildTitle;
    }

    public void setBuildTitle(String buildTitle) {
        BuildTitle = buildTitle;
    }

    public String getBuildProgressPercent() {
        return BuildProgressPercent;
    }

    public void setBuildProgressPercent(String buildProgressPercent) {
        BuildProgressPercent = buildProgressPercent;
    }

    public String getBuildAssignedCPUCount() {
        return BuildAssignedCPUCount;
    }

    public void setBuildAssignedCPUCount(String buildAssignedCPUCount) {
        BuildAssignedCPUCount = buildAssignedCPUCount;
    }

    public String getWorkPowerMHz() {
        return WorkPowerMHz;
    }

    public void setWorkPowerMHz(String workPowerMHz) {
        WorkPowerMHz = workPowerMHz;
    }

    public String getWorkPowerCPUs() {
        return WorkPowerCPUs;
    }

    public void setWorkPowerCPUs(String workPowerCPUs) {
        WorkPowerCPUs = workPowerCPUs;
    }

    @Override
    public String toString() {
        return "Build{" +
                "BuildTitle='" + BuildTitle + '\'' +
                ", BuildProgressPercent='" + BuildProgressPercent + '\'' +
                ", BuildAssignedCPUCount='" + BuildAssignedCPUCount + '\'' +
                ", WorkPowerMHz='" + WorkPowerMHz + '\'' +
                ", WorkPowerCPUs='" + WorkPowerCPUs + '\'' +
                '}';
    }
}
