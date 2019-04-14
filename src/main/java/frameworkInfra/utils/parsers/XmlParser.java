package frameworkInfra.utils.parsers;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

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

    public static List<String> getAllMachinesFromMonitor(org.w3c.dom.Document document, String key, String coord){
        List<String> machines = new ArrayList<String>();
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("Agent");
        for (int i = 0; i < nList.getLength(); i++){
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                org.w3c.dom.Element eElement = (org.w3c.dom.Element) node;
                if (!eElement.getAttribute(key).equals(coord))
                    machines.add(eElement.getAttribute(key));
            }
        }
        return machines;
    }


}
