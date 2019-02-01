package frameworkInfra.utils.parsers;

import com.aventstack.extentreports.Status;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import org.apache.tools.ant.DirectoryScanner;
import org.testng.Assert;

import java.io.*;
import java.util.*;

import static frameworkInfra.Listeners.SuiteListener.test;

public class Parser {

    /**
     * Used in order to get data from file using key,value
     *
     * @param filePath the path of the file to search in
     * @param lookFor  the key that we search the file to retrieve the value
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

        while ((line = in.readLine()) != null) {
            for (Map.Entry<String, String> entry : lookFor.entrySet()) {
                String key = entry.getKey();
                if (line.contains(key)) {
                    pulledData = retrieveDataFromString(line, key);
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

    /**
     * Used in order to get data from string line using key
     *
     * @param line the string line to search in
     * @return value that we found using the key we sent
     * @throws IOException exception for not being able to read the file
     */
    public static String retrieveDataFromString(String line, String key) throws IOException {
        String pulledData = "";
        if (line.contains(key)) {
            pulledData = line.substring(line.lastIndexOf(key) + key.length(), line.length());
            //pulledData = StringUtils.substringBetween(line, entry.getValue(), System.getProperty("line.separator"));
            //pulledData = StringUtils.replaceAll(pulledData, "[+={}^']", "").trim();
            pulledData = pulledData.replaceAll("[+={}^':\"]", "").trim();
            if (pulledData.equals(""))
                pulledData = "null";
        }
        return pulledData;
    }


    public static boolean doesFileContainString(String filePath, String text) {
        test.log(Status.INFO, "Searching for " + text + " in " + filePath);
        try (Scanner sc = new Scanner(new File(filePath))) {
            while (sc.hasNextLine()) {
                final String lineFromFile = sc.nextLine().toLowerCase();
                if (lineFromFile.contains(text.toLowerCase())) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            test.log(Status.INFO, "Failed with error: " + e.getMessage());
        }
        test.log(Status.INFO, "Didn't find " + text + " in" + filePath);
        return false;
    }

    public static String getValueAccordingToString(String filePath, String text, String searchFor) {
        test.log(Status.INFO, "Searching for " + text + " in " + filePath);
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                final String lineFromFile = scanner.nextLine().toLowerCase();
                if (lineFromFile.contains(text.toLowerCase())) {
                    if (lineFromFile.contains(searchFor.toLowerCase())) {
                        return lineFromFile.substring(lineFromFile.lastIndexOf(searchFor.toLowerCase()) + searchFor.length() + 1, lineFromFile.length() - 1);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            test.log(Status.INFO, "Failed with error: " + e.getMessage());
        }
        test.log(Status.INFO, "Didn't find " + text + " in" + filePath);
        return "";
    }

    public static String getFileToParse(String path, String prefix) {
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
     *
     * @param filePath  full path to file
     * @param searchFor String to search for within the file
     * @return number of line String appears in
     */
    public static int getLastLineIndexForString(String filePath, String searchFor) {
        test.log(Status.INFO, "Starting to look for last appearance of " + searchFor + " in " + filePath);
        int line = 0;
        int finalLine = 0;
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                line++;
                final String lineFromFile = scanner.nextLine();
                if (searchFor.contains("Local") && !lineFromFile.contains(("LNK"))) {
                    if (lineFromFile.contains(searchFor)) {
                        finalLine = line;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
        return finalLine;
    }

    /**
     * get the last line that a string appears in
     *
     * @param filePath  full path to file
     * @param searchFor String to search for within the file
     * @return String where searchFor appears in
     */
    public static String getLastLineForString(String filePath, String searchFor) {
        test.log(Status.INFO, "Starting to look for last appearance of " + searchFor + " in " + filePath);
        String lastLineFromfile = "";
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                final String lineFromFile = scanner.nextLine();
                if (lineFromFile.contains(searchFor)) {
                    lastLineFromfile = lineFromFile;
                }
            }
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
        return lastLineFromfile;
    }

    /**
     * get the first line index that a string appears in
     *
     * @param filePath  full path to file
     * @param searchFor String to search for within the file
     * @return number of the line
     */
    public static int getFirstLineForString(String filePath, String searchFor) {
        if (test != null)
            test.log(Status.INFO, "Starting to look for first appearance of " + searchFor + " in " + filePath);
        int line = 0;
        int firstLine = 0;
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                line++;
                final String lineFromFile = scanner.nextLine();
                if (lineFromFile.contains(searchFor)) {
                    firstLine = line;
                    return firstLine;
                }
            }
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
        return firstLine;
    }

    public static int countOccurencesInFile(String filePath, String lookFor) {
        test.log(Status.INFO, "Looking for number of occurrences of " + lookFor + " in" + filePath);
        File file = new File(filePath);
        int count = 0;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                final String lineFromFile = scanner.nextLine().toLowerCase();
                if (lineFromFile.contains(lookFor.toLowerCase())) {
                    count++;
                }
            }
        } catch (FileNotFoundException e) {
            test.log(Status.INFO, "Failed with error: " + e.getMessage());
        }
        test.log(Status.INFO, "Didn't find " + lookFor + " in" + filePath);
        return count;
    }

    public static Set<String> getHelperCores(String filePath) {
        Set<String> agentsList = new HashSet<>();
        try (Scanner sc = new Scanner(new File(filePath))) {
            while (sc.hasNext()) {
                String line = sc.nextLine();
                int start = line.indexOf("(Agent '");
                if (start >= 0) {
                    line = line.substring(start + 8);
                    int end = line.indexOf("'");
                    agentsList.add(line.substring(0, end));
                }
            }
        } catch (FileNotFoundException e) {
            test.log(Status.INFO, "Failed with error: " + e.getMessage());
        }
        return agentsList;
    }
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

