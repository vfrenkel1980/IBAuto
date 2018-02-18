package frameworkInfra.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class CustomJsonParser {

    public static String getValueFromKey(String filePath, String key){
        JSONParser parser = new JSONParser();
        String value = "";
        try {

            Object obj = parser.parse(new FileReader(filePath));

            JSONObject jsonObject = (JSONObject) obj;

            value = (String) jsonObject.get(key);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}