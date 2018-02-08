package frameworkInfra.utils;

import org.apache.tools.ant.DirectoryScanner;

import java.io.*;
import java.util.Map;

public class Parser{

    public static String retrieveDataFromFile(String filePath, Map<String, String> lookFor) throws IOException {
        BufferedReader in = null;
        String line;
        String pulledData = "";
        try {
            in = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.getMessage();
        }

        while((line = in.readLine()) != null)
        {
            for (Map.Entry<String, String> entry : lookFor.entrySet()) {
                if (line.contains(entry.getValue())){
                    pulledData = line.substring(line.lastIndexOf(entry.getValue())+ entry.getValue().length(), line.length());
                    //pulledData = StringUtils.substringBetween(line, entry.getValue(), System.getProperty("line.separator"));
                    //pulledData = StringUtils.replaceAll(pulledData, "[+={}^']", "").trim();
                    pulledData = pulledData.replaceAll("[+={}^':\"]", "").trim();
                    if (pulledData.equals(""))
                        pulledData = "null";
                    entry.setValue(pulledData);
                }
            }
        }
        in.close();

        return pulledData;

//        for (Map.Entry<String, String> entry : lookFor.entrySet()) {
//            System.out.println(entry.getKey() + " = " + entry.getValue());
//        }
    }

    public static String getFileToParse(String path, String prefix){
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setIncludes(new String[]{prefix});
        scanner.setBasedir(path);
        scanner.setCaseSensitive(false);
        scanner.scan();
        String[] files = scanner.getIncludedFiles();
        return files[0];
    }

}
