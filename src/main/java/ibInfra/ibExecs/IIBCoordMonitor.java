package ibInfra.ibExecs;

import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
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

public class IIBCoordMonitor implements ibCoordMonitor {

    IWindowsService winService = new WindowsService();

    @Override
    public Document exportCoordMonitorDataToXML (String pathToFile, String filename) throws ParserConfigurationException, IOException, SAXException {

        String absoluteFilePath = pathToFile + filename;
        File file = new File(absoluteFilePath);
        winService.runCommandWaitForFinish(StaticDataProvider.IbLocations.XGCOORDCONSOLE + "/exportstatus=\"" + absoluteFilePath +"\"");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document;
        document = builder.parse(file);

        return document;
    }

    @Override
    public String getAgentVersion(String agentName) throws IOException, SAXException, ParserConfigurationException {
        return getAgentAttribute(agentName, "Version");
    }
    @Override
    public String getAgentStatus (String agentName) throws IOException, SAXException, ParserConfigurationException {
        return getAgentAttribute(agentName, "Online");
    }

    @Override
    public boolean getAgentSubscribeStatus(String agentName) throws IOException, SAXException, ParserConfigurationException {
        String subscribeStatus = getAgentAttribute(agentName, "Subscribed");
        boolean subscribed= false;
        if(subscribeStatus.equals("True"))subscribed=true;
        return subscribed;
    }


    @Override
    public String getAgentAttribute(String agentName, String attribute) throws IOException, SAXException, ParserConfigurationException {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String absolutePathToFile = winService.getWindowsTEMPfolder() + timestamp + ".xml";
        Document docXML = exportCoordMonitorDataToXML(winService.getWindowsTEMPfolder(), timestamp + ".xml");
        docXML.getDocumentElement().normalize();

        NodeList nList = docXML.getElementsByTagName("Agent");

        String res = "";
        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) node;
                if (agentName.toUpperCase().equals(eElement.getAttribute("Host"))){
                    res = eElement.getAttribute(attribute);
                }
            }
        }
        winService.deleteFile(absolutePathToFile);
        return res;
    }

    @Override
    public void waitForAgentIsUpdated(String agentName) throws ParserConfigurationException, SAXException, IOException {
        String coordAgent = winService.getHostName();
        SystemActions.sleep(10);
        String localVer = getAgentVersion(coordAgent);
        String remoteVer = getAgentVersion(agentName);
        String remoteStatus = getAgentStatus(agentName);
        if (!localVer.equals(remoteVer) | !remoteStatus.equals("True")){
            waitForAgentIsUpdated(agentName);
        }
    }

    @Override
    public boolean checkIfAgentIsHelper (String initiatorName, String agentName) throws IOException, SAXException, ParserConfigurationException {
        SystemActions.sleep(1);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String absolutePathToFile = winService.getWindowsTEMPfolder() + timestamp + ".xml";
        Document docXML = exportCoordMonitorDataToXML(winService.getWindowsTEMPfolder(), timestamp + ".xml");
        docXML.getDocumentElement().normalize();

        NodeList nList = docXML.getElementsByTagName("Agent");

        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) node;
                if (eElement.getAttribute("Host").equals(agentName.toUpperCase()) && eElement.getAttribute("WorkingForAgents").equals(initiatorName.substring(0, 1).toUpperCase() + initiatorName.substring(1))){
                    winService.deleteFile(absolutePathToFile);
                    return true;
                }
            }
        }
        winService.deleteFile(absolutePathToFile);
        return false;
    }
}
