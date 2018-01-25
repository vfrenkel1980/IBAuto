package frameworkInfra.utils;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XmlParser {

    public static List<String> getIpList(){

        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(System.getProperty("user.dir") + "/src/main/resources/Simulation IP list.xml");

        try {
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("ip");

/*            for (int i = 0; i < list.size(); i++) {

                Element node = (Element) list.get(i);

                System.out.println(node.getContent(0).getValue());
            }*/

            return list;

        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
            return null;
        }
    }
}
