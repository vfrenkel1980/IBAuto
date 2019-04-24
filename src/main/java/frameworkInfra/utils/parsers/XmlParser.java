package frameworkInfra.utils.parsers;

import com.aventstack.extentreports.Status;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.*;

import static frameworkInfra.Listeners.SuiteListener.test;

public class XmlParser {

    public static List getIpList(String listFile){

        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(System.getProperty("user.dir") + "/src/main/resources/Machines/" + listFile);

        try {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            return rootNode.getChildren("ip");

        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
            return null;
        }
    }

    public static List<String> breakDownIPList(List ipList) {
        List<String> newIpList = new ArrayList<String>();
        for (Object anIpList : ipList) {
            Element node = (Element) anIpList;
            newIpList.add(node.getContent(0).getValue().trim());
        }
        return newIpList;
    }

    //this method is for parsing the exportstatus.xml
    //returns a list of values matching a specified key
    public static List<String> getListOfValuesUsingKey(org.w3c.dom.Document document, String key, String value){
        test.log(Status.INFO, "Reading Status XML for Agent information");
        List<String> machines = new ArrayList<String>();
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("Agent");
        for (int i = 0; i < nList.getLength(); i++){
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                org.w3c.dom.Element eElement = (org.w3c.dom.Element) node;
                if (eElement.getAttribute(key).equals(value))
                    machines.add(eElement.getAttribute(key));
            }
        }
        test.log(Status.INFO, "Finished parsing Coord Status export");
        return machines;
    }


}
