package ibInfra.ibExecs.metadata;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;

public class CoordinatorStatus {

    @JacksonXmlProperty(isAttribute = true)
    private String Role;

    @JacksonXmlProperty(isAttribute = true)
    private String Host;

    @JacksonXmlProperty(isAttribute = true)
    private String Port;

    @JacksonXmlProperty(isAttribute = true)
    private String AgentCount;

    @JacksonXmlProperty(isAttribute = true)
    private String ExportTime;

    @JacksonXmlProperty(isAttribute = true)
    private String ExportTimeText;

    @JacksonXmlProperty(isAttribute = true)
    private String Version;

    @JacksonXmlProperty(isAttribute = true)
    private String VersionText;

    @JacksonXmlProperty(isAttribute = true)
    private String PrimaryCoordinatorOnline;

    @JacksonXmlProperty(isAttribute = true)
    private String BackupCoordinatorOnline;

    @JacksonXmlProperty(isAttribute = true)
    private String BackupCoordinatorHost;

    @JacksonXmlProperty(isAttribute = true)
    private String MinRequiredCPU;

    @JacksonXmlProperty(isAttribute = true)
    private String MinRequiredDiskSpce;

    @JacksonXmlProperty(isAttribute = true)
    private String MinRequiredVirtualMem;

    @JacksonXmlProperty(isAttribute = true)
    private String MinRequiredPhysMem;

    @JacksonXmlProperty(isAttribute = true)
    private String MaxHelpers;

    @JacksonXmlProperty(isAttribute = true)
    private String LicenseInfo;

    @JacksonXmlProperty(isAttribute = true)
    private String LicensedPackages;

    @JacksonXmlProperty(isAttribute = true)
    private ArrayList<Agent> Agents;

    public CoordinatorStatus() {
        String Role = "";
        String Host = "";
        String Port = "";
        String AgentCount = "";
        String ExportTime = "";
        String ExportTimeText = "";
        String Version = "";
        String VersionText = "";
        String PrimaryCoordinatorOnline = "";
        String BackupCoordinatorOnline = "";
        String BackupCoordinatorHost = "";
        String MinRequiredCPU = "";
        String MinRequiredDiskSpce = "";
        String MinRequiredVirtualMem = "";
        String MinRequiredPhysMem = "";
        String MaxHelpers = "";
        String LicenseInfo = "";
        String LicensedPackages = "";
        ArrayList<Agent> Agents = new ArrayList<>();
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        this.Role = role;
    }

    public String getHost() {
        return Host;
    }

    public void setHost(String host) {
        Host = host;
    }

    public String getPort() {
        return Port;
    }

    public void setPort(String port) {
        Port = port;
    }

    public String getAgentCount() {
        return AgentCount;
    }

    public void setAgentCount(String agentCount) {
        AgentCount = agentCount;
    }

    public String getExportTime() {
        return ExportTime;
    }

    public void setExportTime(String exportTime) {
        ExportTime = exportTime;
    }

    public String getExportTimeText() {
        return ExportTimeText;
    }

    public void setExportTimeText(String exportTimeText) {
        ExportTimeText = exportTimeText;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getVersionText() {
        return VersionText;
    }

    public void setVersionText(String versionText) {
        VersionText = versionText;
    }

    public String getPrimaryCoordinatorOnline() {
        return PrimaryCoordinatorOnline;
    }

    public void setPrimaryCoordinatorOnline(String primaryCoordinatorOnline) {
        PrimaryCoordinatorOnline = primaryCoordinatorOnline;
    }

    public String getBackupCoordinatorOnline() {
        return BackupCoordinatorOnline;
    }

    public void setBackupCoordinatorOnline(String backupCoordinatorOnline) {
        BackupCoordinatorOnline = backupCoordinatorOnline;
    }

    public String getBackupCoordinatorHost() {
        return BackupCoordinatorHost;
    }

    public void setBackupCoordinatorHost(String backupCoordinatorHost) {
        BackupCoordinatorHost = backupCoordinatorHost;
    }

    public String getMinRequiredCPU() {
        return MinRequiredCPU;
    }

    public void setMinRequiredCPU(String minRequiredCPU) {
        MinRequiredCPU = minRequiredCPU;
    }

    public String getMinRequiredDiskSpce() {
        return MinRequiredDiskSpce;
    }

    public void setMinRequiredDiskSpce(String minRequiredDiskSpce) {
        MinRequiredDiskSpce = minRequiredDiskSpce;
    }

    public String getMinRequiredVirtualMem() {
        return MinRequiredVirtualMem;
    }

    public void setMinRequiredVirtualMem(String minRequiredVirtualMem) {
        MinRequiredVirtualMem = minRequiredVirtualMem;
    }

    public String getMinRequiredPhysMem() {
        return MinRequiredPhysMem;
    }

    public void setMinRequiredPhysMem(String minRequiredPhysMem) {
        MinRequiredPhysMem = minRequiredPhysMem;
    }

    public String getMaxHelpers() {
        return MaxHelpers;
    }

    public void setMaxHelpers(String maxHelpers) {
        MaxHelpers = maxHelpers;
    }

    public String getLicenseInfo() {
        return LicenseInfo;
    }

    public void setLicenseInfo(String licenseInfo) {
        LicenseInfo = licenseInfo;
    }

    public String getLicensedPackages() {
        return LicensedPackages;
    }

    public void setLicensedPackages(String licensedPackages) {
        LicensedPackages = licensedPackages;
    }

    public ArrayList<Agent> getAgents() {
        return Agents;
    }

    public void setAgents(ArrayList<Agent> agents) {
        Agents = agents;
    }

    @Override
    public String toString() {
        return "CoordinatorStatus{" +
                "Role='" + Role + '\'' +
                ", Host='" + Host + '\'' +
                ", Port='" + Port + '\'' +
                ", AgentCount='" + AgentCount + '\'' +
                ", ExportTime='" + ExportTime + '\'' +
                ", ExportTimeText='" + ExportTimeText + '\'' +
                ", Version='" + Version + '\'' +
                ", VersionText='" + VersionText + '\'' +
                ", PrimaryCoordinatorOnline='" + PrimaryCoordinatorOnline + '\'' +
                ", BackupCoordinatorOnline='" + BackupCoordinatorOnline + '\'' +
                ", BackupCoordinatorHost='" + BackupCoordinatorHost + '\'' +
                ", MinRequiredCPU='" + MinRequiredCPU + '\'' +
                ", MinRequiredDiskSpce='" + MinRequiredDiskSpce + '\'' +
                ", MinRequiredVirtualMem='" + MinRequiredVirtualMem + '\'' +
                ", MinRequiredPhysMem='" + MinRequiredPhysMem + '\'' +
                ", MaxHelpers='" + MaxHelpers + '\'' +
                ", LicenseInfo='" + LicenseInfo + '\'' +
                ", LicensedPackages='" + LicensedPackages + '\'' +
                ", Agents=" + Agents +
                '}';
    }
}
