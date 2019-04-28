package ibInfra.ibExecs;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface ibCoordMonitor {

    Document exportCoordMonitorDataToXML (String path, String filename) throws ParserConfigurationException, IOException, SAXException;

    String getAgentVersion (String agentName) throws IOException, SAXException, ParserConfigurationException;

    String getAgentStatus (String agentName) throws IOException, SAXException, ParserConfigurationException;

    boolean getAgentSubscribeStatus(String agentName) throws IOException, SAXException, ParserConfigurationException;

    String getAgentOSVersion(String agentName)throws IOException, SAXException, ParserConfigurationException;

    void waitForAgentIsUpdated(String agentName) throws ParserConfigurationException, SAXException, IOException;

    boolean checkIfAgentIsHelper (String initiatorName, String agentName) throws IOException, SAXException, ParserConfigurationException;

    String getAgentAttribute(String agentName, String attribute) throws IOException, SAXException, ParserConfigurationException;

}
