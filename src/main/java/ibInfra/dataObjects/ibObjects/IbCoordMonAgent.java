package ibInfra.dataObjects.ibObjects;

import ibInfra.ibExecs.IIBCoordMonitor;
import ibInfra.windowscl.WindowsService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class IbCoordMonAgent {

    private String Online;
    private String Version;
    private String VersionText;
    private String Host;
    private String Port;
    private String CPUSpeed;
    private String CPUCount;
    private String CPUAvailablePercent;
    private String CPUBrandString;
    private String Description;
    private String FileCacheSize;
    private String FileCacheUsed;
    private String OSVersion;
    private String LogLevel;
    private String IP;
    private String RoutingIP;
    private String PerformanceRating;
    private String UpTimeSec;
    private String ScreenSaverRunning;
    private String Enabled;
    private String Building;
    private String WorkingForAgents;
    private String TotalPhysical;
    private String TotalVirtual;
    private String AvailPhysical;
    private String AvailVirtual;
    private String Subscribed;
    private String BuildGroup;
    private String LastConnected;
    private String LastConnectedText;
    private String RemoteAdmin;
    private String TotalDiskSpace;
    private String AvailDiskSpace;
    private String MSVCVersion;
    private String NetFrameworkVersion;
    private String LoggedOnUsers;
    private String SchedulerEnabled;
    private String SchedulerStartTime;
    private String SchedulerStopTime;
    private String SchedulerRunning;

    public IbCoordMonAgent(String host) throws IOException, SAXException, ParserConfigurationException {
        Host = host;

        IIBCoordMonitor coordMonitor = new IIBCoordMonitor();
        WindowsService winService = new WindowsService();

        String timestamp = String.valueOf(System.currentTimeMillis());
        String absolutePathToFile = winService.getWindowsTEMPfolder() + timestamp + ".xml";
        Document docXML = coordMonitor.exportCoordMonitorDataToXML(winService.getWindowsTEMPfolder(), timestamp + ".xml");
        docXML.getDocumentElement().normalize();

        NodeList nList = docXML.getElementsByTagName("Agent");

        String version = "";
        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) node;
                if (host.toUpperCase().equals(eElement.getAttribute("Host"))){
                    Online = eElement.getAttribute("Online");
                    Version = eElement.getAttribute("Version");
                    VersionText = eElement.getAttribute("VersionText");
                    Host = eElement.getAttribute("Host");
                    Port = eElement.getAttribute("Port");
                    CPUSpeed = eElement.getAttribute("CPUSpeed");
                    CPUCount = eElement.getAttribute("CPUCount");
                    CPUAvailablePercent = eElement.getAttribute("CPUAvailablePercent");
                    CPUBrandString = eElement.getAttribute("CPUBrandString");
                    Description = eElement.getAttribute("Description");
                    FileCacheSize = eElement.getAttribute("FileCacheSize");
                    FileCacheUsed = eElement.getAttribute("FileCacheUsed");
                    OSVersion = eElement.getAttribute("OSVersion");
                    LogLevel = eElement.getAttribute("LogLevel");
                    IP = eElement.getAttribute("IP");
                    RoutingIP = eElement.getAttribute("RoutingIP");
                    PerformanceRating = eElement.getAttribute("PerformanceRating");
                    UpTimeSec = eElement.getAttribute("UpTimeSec");
                    ScreenSaverRunning = eElement.getAttribute("ScreenSaverRunning");
                    Enabled = eElement.getAttribute("Enabled");
                    Building = eElement.getAttribute("Building");
                    WorkingForAgents = eElement.getAttribute("WorkingForAgents");
                    TotalPhysical = eElement.getAttribute("TotalPhysical");
                    TotalVirtual = eElement.getAttribute("TotalVirtual");
                    AvailPhysical = eElement.getAttribute("AvailPhysical");
                    AvailVirtual = eElement.getAttribute("AvailVirtual");
                    Subscribed = eElement.getAttribute("Subscribed");
                    BuildGroup = eElement.getAttribute("BuildGroup");
                    LastConnected = eElement.getAttribute("LastConnected");
                    LastConnectedText = eElement.getAttribute("LastConnectedText");
                    RemoteAdmin = eElement.getAttribute("RemoteAdmin");
                    TotalDiskSpace = eElement.getAttribute("TotalDiskSpace");
                    AvailDiskSpace = eElement.getAttribute("AvailDiskSpace");
                    MSVCVersion = eElement.getAttribute("MSVCVersion");
                    NetFrameworkVersion = eElement.getAttribute("NetFrameworkVersion");
                    LoggedOnUsers = eElement.getAttribute("LoggedOnUsers");
                    SchedulerEnabled = eElement.getAttribute("SchedulerEnabled");
                    SchedulerStartTime = eElement.getAttribute("SchedulerStartTime");
                    SchedulerStopTime = eElement.getAttribute("SchedulerStopTime");
                    SchedulerRunning = eElement.getAttribute("SchedulerRunning");
                }
            }
        }
        File file = new File(absolutePathToFile);
        file.delete();
    }

    public String getOnline() {
        return Online;
    }

    public String getVersion() {
        return Version;
    }

    public String getVersionText() {
        return VersionText;
    }

    public String getHost() {
        return Host;
    }

    public String getPort() {
        return Port;
    }

    public String getCPUSpeed() {
        return CPUSpeed;
    }

    public String getCPUCount() {
        return CPUCount;
    }

    public String getCPUAvailablePercent() {
        return CPUAvailablePercent;
    }

    public String getCPUBrandString() {
        return CPUBrandString;
    }

    public String getDescription() {
        return Description;
    }

    public String getFileCacheSize() {
        return FileCacheSize;
    }

    public String getFileCacheUsed() {
        return FileCacheUsed;
    }

    public String getOSVersion() {
        return OSVersion;
    }

    public String getLogLevel() {
        return LogLevel;
    }

    public String getIP() {
        return IP;
    }

    public String getRoutingIP() {
        return RoutingIP;
    }

    public String getPerformanceRating() {
        return PerformanceRating;
    }

    public String getUpTimeSec() {
        return UpTimeSec;
    }

    public String getScreenSaverRunning() {
        return ScreenSaverRunning;
    }

    public String getEnabled() {
        return Enabled;
    }

    public String getBuilding() {
        return Building;
    }

    public String getWorkingForAgents() {
        return WorkingForAgents;
    }

    public String getTotalPhysical() {
        return TotalPhysical;
    }

    public String getTotalVirtual() {
        return TotalVirtual;
    }

    public String getAvailPhysical() {
        return AvailPhysical;
    }

    public String getAvailVirtual() {
        return AvailVirtual;
    }

    public String getSubscribed() {
        return Subscribed;
    }

    public String getBuildGroup() {
        return BuildGroup;
    }

    public String getLastConnected() {
        return LastConnected;
    }

    public String getLastConnectedText() {
        return LastConnectedText;
    }

    public String getRemoteAdmin() {
        return RemoteAdmin;
    }

    public String getTotalDiskSpace() {
        return TotalDiskSpace;
    }

    public String getAvailDiskSpace() {
        return AvailDiskSpace;
    }

    public String getMSVCVersion() {
        return MSVCVersion;
    }

    public String getNetFrameworkVersion() {
        return NetFrameworkVersion;
    }

    public String getLoggedOnUsers() {
        return LoggedOnUsers;
    }

    public String getSchedulerEnabled() {
        return SchedulerEnabled;
    }

    public String getSchedulerStartTime() {
        return SchedulerStartTime;
    }

    public String getSchedulerStopTime() {
        return SchedulerStopTime;
    }

    public String getSchedulerRunning() {
        return SchedulerRunning;
    }
}
