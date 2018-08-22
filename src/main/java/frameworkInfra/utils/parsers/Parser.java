package frameworkInfra.utils.parsers;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.TestBase;
import org.apache.tools.ant.DirectoryScanner;
import java.io.*;
import java.util.Map;
import java.util.Scanner;

import static frameworkInfra.Listeners.SuiteListener.test;

public class Parser extends TestBase{

    /**
     * Used in order to get data from file using key,value
     * @param filePath the path of the file to search in
     * @param lookFor the key that we search the file to retrieve the value
     * @return value that we found using the key we sent
     * @throws IOException exception for not being able to read the file
     */
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
                if (line.contains(entry.getKey())){
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
        test.log(Status.INFO, "Searching for " + text + " in " + filePath);
        File file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                final String lineFromFile = scanner.nextLine().toLowerCase();
                if(lineFromFile.contains(text.toLowerCase())) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            test.log(Status.INFO, "Failed with error: " + e.getMessage());
        }finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        test.log(Status.INFO, "Didn't find " + text + " in" + filePath);
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

    /**
     * get the last line index that a string appears in
     * @param filePath full path to file
     * @param searchFor String to search for within the file
     * @return number of line String appears in
     */
    public static int getLastLineForString(String filePath, String searchFor){
        test.log(Status.INFO, "Starting to look for last appearance of " + searchFor + " in " + filePath);
        int line = 0;
        int finalLine = 0;
        File file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line ++;
                final String lineFromFile = scanner.nextLine();
                if (searchFor.contains("Local") && !lineFromFile.contains(("LNK"))) {
                    if (lineFromFile.contains(searchFor)) {
                        finalLine = line;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.getMessage();
        }finally {
            scanner.close();
        }
        return finalLine;
    }

    /**
     * get the first line index that a string appears in
     * @param filePath full path to file
     * @param searchFor String to search for within the file
     * @return number of the line
     */
    public static int getFirstLineForString(String filePath, String searchFor){
        if (test != null)
            test.log(Status.INFO, "Starting to look for first appearance of " + searchFor + " in " + filePath);
        int line = 0;
        int firstLine = 0;
        File file = new File(filePath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line ++;
                final String lineFromFile = scanner.nextLine();
                if(lineFromFile.contains(searchFor)) {
                    firstLine = line;
                    return firstLine;
                }
            }
        } catch (FileNotFoundException e) {
            e.getMessage();
        }finally {
            scanner.close();
        }
        return firstLine;
    }


    /*example

              Map<String, String> lookFor = new HashMap<String, String>();
              lookFor.put("version", "version");
              try {
                  result = Parser.retrieveDataFromFile("C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\\Common7\\IDE\\Extensions\\IncredibuildExtension\\manifest.json", lookFor);
              } catch (IOException e) {
                  e.getMessage();
              }
              result = result.substring(0,result.indexOf(","));*/
}
