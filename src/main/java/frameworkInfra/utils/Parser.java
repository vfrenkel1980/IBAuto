package frameworkInfra.utils;

import org.apache.tools.ant.DirectoryScanner;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

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

    public static boolean doesFileContainString(String filePath, String text){
        File file = new File(filePath);
        final Scanner scanner;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                final String lineFromFile = scanner.nextLine();
                if(lineFromFile.contains(text)) {

                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
        return false;
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


    //usage example
     /*        Map<String, String> lookFor = new HashMap<String, String>();
        lookFor.put("version", "version");
        try {
            result = Parser.retrieveDataFromFile("C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\\Common7\\IDE\\Extensions\\IncredibuildExtension\\manifest.json", lookFor);
        } catch (IOException e) {
            e.getMessage();
        }
        result = result.substring(0,result.indexOf(","));*/

}
