package frameworkInfra.utils;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {

    public static List<String> getIpList(String listFile){

        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(System.getProperty("user.dir") + "/src/main/resources/Machines/" + listFile);

        try {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("ip");

            return list;

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
}
