package ibInfra.ibExecs.metadata;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;

public class Agent {

    @JacksonXmlProperty(isAttribute = true)
    private boolean Online;

    @JacksonXmlProperty(isAttribute = true)
    private String Version;

    @JacksonXmlProperty(isAttribute = true)
    private String VersionText;

    @JacksonXmlProperty(isAttribute = true)
    private String Host;

    @JacksonXmlProperty(isAttribute = true)
    private String Port;

    @JacksonXmlProperty(isAttribute = true)
    private String CPUSpeed;

    @JacksonXmlProperty(isAttribute = true)
    private String CPUCount;

    @JacksonXmlProperty(isAttribute = true)
    private String CPUAvailablePercent;

    @JacksonXmlProperty(isAttribute = true)
    private String CPUBrandString;

    @JacksonXmlProperty(isAttribute = true)
    private String Description;

    @JacksonXmlProperty(isAttribute = true)
    private String FileCacheSize;

    @JacksonXmlProperty(isAttribute = true)
    private String FileCacheUsed;

    @JacksonXmlProperty(isAttribute = true)
    private String OSVersion;

    @JacksonXmlProperty(isAttribute = true)
    private String LogLevel;

    @JacksonXmlProperty(isAttribute = true)
    private String IP;

    @JacksonXmlProperty(isAttribute = true)
    private String RoutingIP;

    @JacksonXmlProperty(isAttribute = true)
    private String PerformanceRating;

    @JacksonXmlProperty(isAttribute = true)
    private String UpTimeSec;

    @JacksonXmlProperty(isAttribute = true)
    private String ScreenSaverRunning;

    @JacksonXmlProperty(isAttribute = true)
    private boolean Enabled;

    @JacksonXmlProperty(isAttribute = true)
    private boolean Building;

    @JacksonXmlProperty(isAttribute = true)
    private String WorkingForAgents;

    @JacksonXmlProperty(isAttribute = true)
    private String TotalPhysical;

    @JacksonXmlProperty(isAttribute = true)
    private String TotalVirtual;

    @JacksonXmlProperty(isAttribute = true)
    private String AvailPhysical;

    @JacksonXmlProperty(isAttribute = true)
    private String AvailVirtual;

    @JacksonXmlProperty(isAttribute = true)
    private boolean Subscribed;

    @JacksonXmlProperty(isAttribute = true)
    private String BuildGroup;

    @JacksonXmlProperty(isAttribute = true)
    private String LastConnected;

    @JacksonXmlProperty(isAttribute = true)
    private String LastConnectedText;

    @JacksonXmlProperty(isAttribute = true)
    private boolean RemoteAdmin;

    @JacksonXmlProperty(isAttribute = true)
    private String TotalDiskSpace;

    @JacksonXmlProperty(isAttribute = true)
    private String AvailDiskSpace;

    @JacksonXmlProperty(isAttribute = true)
    private String MSVCVersion;

    @JacksonXmlProperty(isAttribute = true)
    private String NetFrameworkVersion;

    @JacksonXmlProperty(isAttribute = true)
    private String LoggedOnUsers;

    @JacksonXmlProperty(isAttribute = true)
    private boolean SchedulerEnabled;

    @JacksonXmlProperty(isAttribute = true)
    private String SchedulerStartTime;

    @JacksonXmlProperty(isAttribute = true)
    private String SchedulerStopTime;

    @JacksonXmlProperty(isAttribute = true)
    private boolean SchedulerRunning;

    @JacksonXmlProperty(isAttribute = false)
    private ArrayList<Build> Builds;

    public Agent() {
        Online = false;
        Version = "";
        VersionText = "";
        Host = "";
        Port = "";
        CPUSpeed = "";
        CPUCount = "";
        CPUAvailablePercent = "";
        CPUBrandString = "";
        Description = "";
        FileCacheSize = "";
        FileCacheUsed = "";
        OSVersion = "";
        LogLevel = "";
        IP = "";
        RoutingIP = "";
        PerformanceRating = "";
        UpTimeSec = "";
        ScreenSaverRunning = "";
        Enabled = false;
        Building = false;
        WorkingForAgents = "";
        TotalPhysical = "";
        TotalVirtual = "";
        AvailPhysical = "";
        AvailVirtual = "";
        Subscribed = false;
        BuildGroup = "";
        LastConnected = "";
        LastConnectedText = "";
        RemoteAdmin = false;
        TotalDiskSpace = "";
        AvailDiskSpace = "";
        MSVCVersion = "";
        NetFrameworkVersion = "";
        LoggedOnUsers = "";
        SchedulerEnabled = false;
        SchedulerStartTime = "";
        SchedulerStopTime = "";
        SchedulerRunning = false;
        Builds = new ArrayList<>();
    }

    public boolean isOnline() {
        return Online;
    }

    public void setOnline(boolean online) {
        Online = online;
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

    public String getCPUSpeed() {
        return CPUSpeed;
    }

    public void setCPUSpeed(String CPUSpeed) {
        this.CPUSpeed = CPUSpeed;
    }

    public String getCPUCount() {
        return CPUCount;
    }

    public void setCPUCount(String CPUCount) {
        this.CPUCount = CPUCount;
    }

    public String getCPUAvailablePercent() {
        return CPUAvailablePercent;
    }

    public void setCPUAvailablePercent(String CPUAvailablePercent) {
        this.CPUAvailablePercent = CPUAvailablePercent;
    }

    public String getCPUBrandString() {
        return CPUBrandString;
    }

    public void setCPUBrandString(String CPUBrandString) {
        this.CPUBrandString = CPUBrandString;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getFileCacheSize() {
        return FileCacheSize;
    }

    public void setFileCacheSize(String fileCacheSize) {
        FileCacheSize = fileCacheSize;
    }

    public String getFileCacheUsed() {
        return FileCacheUsed;
    }

    public void setFileCacheUsed(String fileCacheUsed) {
        FileCacheUsed = fileCacheUsed;
    }

    public String getOSVersion() {
        return OSVersion;
    }

    public void setOSVersion(String OSVersion) {
        this.OSVersion = OSVersion;
    }

    public String getLogLevel() {
        return LogLevel;
    }

    public void setLogLevel(String logLevel) {
        LogLevel = logLevel;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getRoutingIP() {
        return RoutingIP;
    }

    public void setRoutingIP(String routingIP) {
        RoutingIP = routingIP;
    }

    public String getPerformanceRating() {
        return PerformanceRating;
    }

    public void setPerformanceRating(String performanceRating) {
        PerformanceRating = performanceRating;
    }

    public String getUpTimeSec() {
        return UpTimeSec;
    }

    public void setUpTimeSec(String upTimeSec) {
        UpTimeSec = upTimeSec;
    }

    public String getScreenSaverRunning() {
        return ScreenSaverRunning;
    }

    public void setScreenSaverRunning(String screenSaverRunning) {
        ScreenSaverRunning = screenSaverRunning;
    }

    public boolean isEnabled() {
        return Enabled;
    }

    public void setEnabled(boolean enabled) {
        Enabled = enabled;
    }

    public boolean isBuilding() {
        return Building;
    }

    public void setBuilding(boolean building) {
        Building = building;
    }

    public String getWorkingForAgents() {
        return WorkingForAgents;
    }

    public void setWorkingForAgents(String workingForAgents) {
        this.WorkingForAgents = workingForAgents;
    }

    public String getTotalPhysical() {
        return TotalPhysical;
    }

    public void setTotalPhysical(String totalPhysical) {
        TotalPhysical = totalPhysical;
    }

    public String getTotalVirtual() {
        return TotalVirtual;
    }

    public void setTotalVirtual(String totalVirtual) {
        TotalVirtual = totalVirtual;
    }

    public String getAvailPhysical() {
        return AvailPhysical;
    }

    public void setAvailPhysical(String availPhysical) {
        AvailPhysical = availPhysical;
    }

    public String getAvailVirtual() {
        return AvailVirtual;
    }

    public void setAvailVirtual(String availVirtual) {
        AvailVirtual = availVirtual;
    }

    public boolean isSubscribed() {
        return Subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        Subscribed = subscribed;
    }

    public String getBuildGroup() {
        return BuildGroup;
    }

    public void setBuildGroup(String buildGroup) {
        BuildGroup = buildGroup;
    }

    public String getLastConnected() {
        return LastConnected;
    }

    public void setLastConnected(String lastConnected) {
        LastConnected = lastConnected;
    }

    public String getLastConnectedText() {
        return LastConnectedText;
    }

    public void setLastConnectedText(String lastConnectedText) {
        LastConnectedText = lastConnectedText;
    }

    public boolean isRemoteAdmin() {
        return RemoteAdmin;
    }

    public void setRemoteAdmin(boolean remoteAdmin) {
        RemoteAdmin = remoteAdmin;
    }

    public String getTotalDiskSpace() {
        return TotalDiskSpace;
    }

    public void setTotalDiskSpace(String totalDiskSpace) {
        TotalDiskSpace = totalDiskSpace;
    }

    public String getAvailDiskSpace() {
        return AvailDiskSpace;
    }

    public void setAvailDiskSpace(String availDiskSpace) {
        AvailDiskSpace = availDiskSpace;
    }

    public String getMSVCVersion() {
        return MSVCVersion;
    }

    public void setMSVCVersion(String MSVCVersion) {
        this.MSVCVersion = MSVCVersion;
    }

    public String getNetFrameworkVersion() {
        return NetFrameworkVersion;
    }

    public void setNetFrameworkVersion(String netFrameworkVersion) {
        NetFrameworkVersion = netFrameworkVersion;
    }

    public String getLoggedOnUsers() {
        return LoggedOnUsers;
    }

    public void setLoggedOnUsers(String loggedOnUsers) {
        LoggedOnUsers = loggedOnUsers;
    }

    public boolean isSchedulerEnabled() {
        return SchedulerEnabled;
    }

    public void setSchedulerEnabled(boolean schedulerEnabled) {
        SchedulerEnabled = schedulerEnabled;
    }

    public String getSchedulerStartTime() {
        return SchedulerStartTime;
    }

    public void setSchedulerStartTime(String schedulerStartTime) {
        SchedulerStartTime = schedulerStartTime;
    }

    public String getSchedulerStopTime() {
        return SchedulerStopTime;
    }

    public void setSchedulerStopTime(String schedulerStopTime) {
        SchedulerStopTime = schedulerStopTime;
    }

    public boolean isSchedulerRunning() {
        return SchedulerRunning;
    }

    public void setSchedulerRunning(boolean schedulerRunning) {
        SchedulerRunning = schedulerRunning;
    }

    public ArrayList<Build> getBuilds() {
        return Builds;
    }

    public void setBuilds(ArrayList<Build> builds) {
        Builds = builds;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "Online=" + Online +
                ", Version='" + Version + '\'' +
                ", VersionText='" + VersionText + '\'' +
                ", Host='" + Host + '\'' +
                ", Port='" + Port + '\'' +
                ", CPUSpeed='" + CPUSpeed + '\'' +
                ", CPUCount='" + CPUCount + '\'' +
                ", CPUAvailablePercent='" + CPUAvailablePercent + '\'' +
                ", CPUBrandString='" + CPUBrandString + '\'' +
                ", Description='" + Description + '\'' +
                ", FileCacheSize='" + FileCacheSize + '\'' +
                ", FileCacheUsed='" + FileCacheUsed + '\'' +
                ", OSVersion='" + OSVersion + '\'' +
                ", LogLevel='" + LogLevel + '\'' +
                ", IP='" + IP + '\'' +
                ", RoutingIP='" + RoutingIP + '\'' +
                ", PerformanceRating='" + PerformanceRating + '\'' +
                ", UpTimeSec='" + UpTimeSec + '\'' +
                ", ScreenSaverRunning='" + ScreenSaverRunning + '\'' +
                ", Enabled=" + Enabled +
                ", Building=" + Building +
                ", WorkingForAgents='" + WorkingForAgents + '\'' +
                ", TotalPhysical='" + TotalPhysical + '\'' +
                ", TotalVirtual='" + TotalVirtual + '\'' +
                ", AvailPhysical='" + AvailPhysical + '\'' +
                ", AvailVirtual='" + AvailVirtual + '\'' +
                ", Subscribed=" + Subscribed +
                ", BuildGroup='" + BuildGroup + '\'' +
                ", LastConnected='" + LastConnected + '\'' +
                ", LastConnectedText='" + LastConnectedText + '\'' +
                ", RemoteAdmin=" + RemoteAdmin +
                ", TotalDiskSpace='" + TotalDiskSpace + '\'' +
                ", AvailDiskSpace='" + AvailDiskSpace + '\'' +
                ", MSVCVersion='" + MSVCVersion + '\'' +
                ", NetFrameworkVersion='" + NetFrameworkVersion + '\'' +
                ", LoggedOnUsers='" + LoggedOnUsers + '\'' +
                ", SchedulerEnabled=" + SchedulerEnabled +
                ", SchedulerStartTime='" + SchedulerStartTime + '\'' +
                ", SchedulerStopTime='" + SchedulerStopTime + '\'' +
                ", SchedulerRunning=" + SchedulerRunning +
                ", Builds='" + Builds + '\'' +
                '}';
    }
}
