package ibInfra.ibExecs;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import ibInfra.ibExecs.metadata.CoordinatorStatus;
import ibInfra.windowscl.IWindowsService;
import ibInfra.windowscl.WindowsService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

import ibInfra.ibExecs.metadata.Agent;

public class IIBCoordMonitor {
    private static final long DELAY_FOR_AGENT_UPDATE_MIN = 10L;
    IWindowsService winService = new WindowsService();

    public CoordinatorStatus retrieveCoordMonitorDataFromXmlFile(String coordinatorXmlFilePath) throws Exception {
        int errorCode = winService.runCommandWaitForFinish(StaticDataProvider.IbLocations.XGCOORDCONSOLE + "/exportstatus=\"" + coordinatorXmlFilePath + "\"");
        if (errorCode != 0) {
            throw new Exception("Failed to export Coordinator Monitor XML with xgCoordConsole command");
        }

        XmlMapper xmlMapper;
        String readContent;
        try {
            xmlMapper = new XmlMapper();
            readContent = new String(Files.readAllBytes(Paths.get(coordinatorXmlFilePath)));
        } catch (IOException e) {
            throw new Exception("Failed to read from Xml coordinator file! Error: " + e.getMessage());
        }

        CoordinatorStatus coordinatorMetadata;
        try {
            coordinatorMetadata = xmlMapper.readValue(readContent, CoordinatorStatus.class);
        } catch (IOException e) {
            throw new Exception("Failed to extract Xml coordinator file to an object! Error: " + e.getMessage());
        }

        return coordinatorMetadata;
    }

    public String getBuildGroup(CoordinatorStatus coordinatorMonitorData, String hostName) throws Exception {
        Iterator it = coordinatorMonitorData.getAgents().iterator();
        while (it.hasNext()) {
            Agent agent = (Agent) it.next();
            if ((agent).getHost().toLowerCase().equals(hostName.toLowerCase()))
                return agent.getBuildGroup();
        }
        throw new Exception(String.format("Host '%s': No data could not be retrieved from Coordinator monitor!", hostName));
    }

    public ArrayList<Agent> getEnabledAgentsByBuildGroup(CoordinatorStatus coordinatorMonitorData, String buildGroupName) {
        ArrayList<Agent> agents = new ArrayList<>();
        Iterator it = coordinatorMonitorData.getAgents().iterator();
        while (it.hasNext()) {
            Agent agent = (Agent) it.next();
            if ((agent).getBuildGroup().toLowerCase().equals(buildGroupName.toLowerCase()) &&
                 agent.isEnabled())
                agents.add(agent);
        }
        return agents;
    }

    public Document exportCoordMonitorDataToXML(String pathToFile, String filename) throws ParserConfigurationException, IOException, SAXException {
        String absoluteFilePath = pathToFile + filename;
        File file = new File(absoluteFilePath);
        winService.runCommandWaitForFinish(StaticDataProvider.IbLocations.XGCOORDCONSOLE + "/exportstatus=\"" + absoluteFilePath + "\"");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document;
        document = builder.parse(file);

        return document;
    }

    public String getAgentVersion(String agentName) throws IOException, SAXException, ParserConfigurationException {
        return getAgentAttribute(agentName, "Version");
    }

    public String getAgentStatus(String agentName) throws IOException, SAXException, ParserConfigurationException {
        return getAgentAttribute(agentName, "Online");
    }

    public boolean getAgentSubscribeStatus(String agentName) throws IOException, SAXException, ParserConfigurationException {
        String subscribeStatus = getAgentAttribute(agentName, "Subscribed");
        boolean subscribed = false;
        if (subscribeStatus.equals("True")) subscribed = true;
        return subscribed;
    }

    public String getAgentOSVersion(String agentName) throws IOException, SAXException, ParserConfigurationException {
        String osVersion = getAgentAttribute(agentName, "OSVersion").toLowerCase();
        if (osVersion.contains("windows 10") || osVersion.contains("windows server 2016")) {
            osVersion = "10";
        } else if (osVersion.contains("windows 8.1") || osVersion.contains("windows server 2012 R2")) {
            osVersion = "8.1";
        } else if (osVersion.contains("windows 8 ") || osVersion.contains("windows server 2012")) {
            osVersion = "8";
        } else if (osVersion.contains("windows 7") || osVersion.contains("windows server 2008 R2")) {
            osVersion = "7";
        } else if (osVersion.contains("windows server 2008")) {
            osVersion = "2008";
        } else if (osVersion.contains("windows vista") || osVersion.contains("windows server 2008")) {
            osVersion = "vista";
        } else if (osVersion.contains("windows server 2003")) {
            osVersion = "2003";
        } else if (osVersion.contains("windows xp")) {
            osVersion = "xp";
        } else {
            osVersion = "IsNotRecognised";
        }
        return osVersion;
    }

    public String getAgentAttribute(String agentName, String attribute) throws IOException, SAXException, ParserConfigurationException {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String absolutePathToFile = winService.getWindowsTEMPfolder() + timestamp + ".xml";
        Document docXML = exportCoordMonitorDataToXML(winService.getWindowsTEMPfolder(), timestamp + ".xml");
        docXML.getDocumentElement().normalize();

        NodeList nList = docXML.getElementsByTagName("Agent");

        String res = "";
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                if (agentName.toUpperCase().equals(eElement.getAttribute("Host"))) {
                    res = eElement.getAttribute(attribute);
                }
            }
        }
        winService.deleteFile(absolutePathToFile);
        return res;
    }

    public void waitForAgentIsUpdated(String agentName) throws ParserConfigurationException, SAXException, IOException {
        final LocalTime start = LocalTime.now();
        final String coordAgent = winService.getHostName();
        final String localVer = getAgentVersion(coordAgent);
        String remoteVer;
        String remoteStatus;
        do {
            SystemActions.sleep(10);
            remoteVer = getAgentVersion(agentName);
            remoteStatus = getAgentStatus(agentName);
        } while (start.plusMinutes(DELAY_FOR_AGENT_UPDATE_MIN).isAfter(LocalTime.now()) &&
                (!localVer.equals(remoteVer) | !remoteStatus.equals("True")));
    }

    public boolean checkIfAgentIsHelper(String initiatorName, String agentName) throws IOException, SAXException, ParserConfigurationException {
        SystemActions.sleep(1);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String absolutePathToFile = winService.getWindowsTEMPfolder() + timestamp + ".xml";
        Document docXML = exportCoordMonitorDataToXML(winService.getWindowsTEMPfolder(), timestamp + ".xml");
        docXML.getDocumentElement().normalize();

        NodeList nList = docXML.getElementsByTagName("Agent");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                if (eElement.getAttribute("Host").equals(agentName.toUpperCase()) && eElement.getAttribute("WorkingForAgents").equals(initiatorName.substring(0, 1).toUpperCase() + initiatorName.substring(1))) {
                    winService.deleteFile(absolutePathToFile);
                    return true;
                }
            }
        }
        winService.deleteFile(absolutePathToFile);
        return false;
    }
}
