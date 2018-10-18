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
        winService.runCommandWaitForFinish(StaticDataProvider.Processes.XGCOORDCONSOLE + "/exportstatus=\"" + absoluteFilePath +"\"");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document;
        document = builder.parse(file);

        return document;
    }

    @Override
    public String getAgentVersion(String agentName) throws IOException, SAXException, ParserConfigurationException {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String absolutePathToFile = winService.getWindowsTEMPfolder() + timestamp + ".xml";
        Document docXML = exportCoordMonitorDataToXML(winService.getWindowsTEMPfolder(), timestamp + ".xml");
        docXML.getDocumentElement().normalize();

        NodeList nList = docXML.getElementsByTagName("Agent");

        String version = "";
        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) node;
                if (agentName.toUpperCase().equals(eElement.getAttribute("Host"))){
                    version = eElement.getAttribute("Version");
                }
            }
        }
        winService.deleteFile(absolutePathToFile);
        return version;
    }

    @Override
    public String getAgentStatus (String agentName) throws IOException, SAXException, ParserConfigurationException {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String absolutePathToFile = winService.getWindowsTEMPfolder() + timestamp + ".xml";
        Document docXML = exportCoordMonitorDataToXML(winService.getWindowsTEMPfolder(), timestamp + ".xml");
        docXML.getDocumentElement().normalize();

        NodeList nList = docXML.getElementsByTagName("Agent");

        String status = "";
        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) node;
                if (agentName.toUpperCase().equals(eElement.getAttribute("Host"))){
                    status = eElement.getAttribute("Online");
                }
            }
        }
        winService.deleteFile(absolutePathToFile);
        return status;
    }

    @Override
    public void waitForAgentIsUpdated(String agentName) throws ParserConfigurationException, SAXException, IOException {
        String coordAgent = winService.getHostName();
        SystemActions.sleep(10);
        String localVer = getAgentVersion(coordAgent);
        String remoteVer = getAgentVersion(agentName);
        String remoteStatus = getAgentStatus(agentName);
        System.out.println("LocalVer: " + localVer + "; RemoteVer: " + remoteVer + " RemoteStatus: " + remoteStatus);
        if (!localVer.equals(remoteVer) | !remoteStatus.equals("True")){
            waitForAgentIsUpdated(agentName);
        }
    }
}
