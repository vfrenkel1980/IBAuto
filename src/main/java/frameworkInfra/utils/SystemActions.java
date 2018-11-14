package frameworkInfra.utils;

import com.aventstack.extentreports.Status;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.tools.ant.DirectoryScanner;

import static frameworkInfra.Listeners.SuiteListener.test;
import static frameworkInfra.testbases.TestBase.log;

public class SystemActions {

    /**
     * delete all files in folder with a predefined prefix
     * @param path the path that we want to delete the files
     * @param filePrefix the prefix for the files to be deleted
     */
    public static void deleteFilesByPrefix(String path, String filePrefix){
        try (DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(Paths.get(path), filePrefix + "*")) {
            for (final Path newDirectoryStreamItem : newDirectoryStream) {
                Files.delete(newDirectoryStreamItem);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteFolder(File path){
        File[] allContents = path.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteFolder(file);
            }
        }
        path.delete();
    }

    /**
     * Copy all files with a predefined extension
     * @param source source location
     * @param destination destination to copy to
     * @param extension file extension to be copied
     * @param useTimeStamp should a timeStamp be created on the destination folder
     */
    public static void copyFilesByExtension(String source, String destination, String extension, boolean useTimeStamp){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

        File sourceLocation= new File(source);
        File targetLocation;
        if (useTimeStamp)
            targetLocation = new File(destination + formatter.format(calendar.getTime()));
        else
            targetLocation = new File(destination);
        try {
            FileUtils.copyDirectory(
                    FileUtils.getFile(sourceLocation), FileUtils.getFile(targetLocation), new SuffixFileFilter(extension));
            if (test != null)
                test.log(Status.INFO, source + " Folder copied successfully");
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static void killProcess(String process){
        try{
            //test.log(Status.INFO, "Killing process " + process);
            Runtime.getRuntime().exec("TASKKILL /F /IM " + process);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static void startProcess(String processPath){
        try{
            test.log(Status.INFO, "Starting process " + processPath);
            Runtime.getRuntime().exec(processPath);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    /**
     * delte a single file
     * @param filePath file to delete
     */
    public static void deleteFile(String filePath){
        try{
            File file = new File(filePath);
            if(file.delete()){
                log.info(filePath + " deleted successfully");
            }else{
                log.info("Failed to delete" + filePath);
            }
        }catch(Exception e){
            e.getMessage();
        }
    }

    public static void sleep(int seconds){
        long timeout = seconds * 1000;
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.getMessage();
        }
    }

    /**
     * List all file in the required directory
     * @param path dir to list the files
     * @return List of files in the directory
     */
    public static List<String> getAllFilesInDirectory(String path){
        List<String> results = new ArrayList<String>();
        File[] files = new File(path).listFiles();

        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }
        return results;
    }

    /**
     * List all file in the required directory
     * @param path dir to list the files
     * @return List of files in the directory
     */

    public static int countAllFilesInDirectory(String path){
        File file = new File(path);
        File[] files = file.listFiles();
        int count = 0;
        for (File f : files)
            if (f.isDirectory())
                count += countAllFilesInDirectory(f.getPath());
            else
                count++;

        return count;
    }

    /**
     * Add time to machine time
     * @param days days
     * @param months months
     * @param years years
     */
    public static void addPeriodToSystemTime(long days, long months, long years){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yy");
        String newDate = (now.plusDays(days).plusMonths(months).plusYears(years)).format(formatter);
        try {
            test.log(Status.INFO, "Changing machine time to: " + newDate);
            Runtime.getRuntime().exec("cmd /C date " + newDate);
        } catch (IOException e) {
            e.getMessage();
        }
        sleep(5);
    }

    /**
     * remove time from machine time
     * @param days days
     * @param months months
     * @param years years
     */
    public static void subtractPeriodFromSystemTime(long days, long months, long years) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        String newDate = (now.minusDays(days).minusMonths(months).minusYears(years)).format(formatter);
        try {
            test.log(Status.INFO, "Changing machine time to: " + newDate);
            Runtime.getRuntime().exec("cmd /C date " + newDate);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    /**
     * get local date
     * @return String date
     */
    public static String getLocalDateAsString(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yy");
        String newDate = now.format(formatter);
        test.log(Status.INFO, "Checking local time" + newDate);
        return newDate;
    }

    /**
     * set local date
     * @param date date to set on machine
     */
    public static void setLocalDateFromString(String date) {
        try {
            test.log(Status.INFO, "Changing machine time to: " + date);
            Runtime.getRuntime().exec("cmd /C date " + date);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    /**
     * checks if file exists in directory
     * @param filePath path to check
     * @return true/false
     */
    public static boolean doesFileExist(String filePath){
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * copy file from source to destination
     * @param src source
     * @param dest destination
     */
    public static void copyFile(String src, String dest){
        try {
            FileUtils.copyFile(new File(src), new File(dest));
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
    }

    /**
     * deletes all files older than
     * @param dir directory to the delete the files
     * @param days number of days defined to the delete the files
     */
    public static void deleteFilesOlderThanX(String dir, int days){
        try {
            File directory = new File(dir);
            if (directory.isDirectory()) {
                Collection<File> filesToDelete = FileUtils.listFiles(directory,
                        new AgeFileFilter(DateUtils.addDays(new Date(), -days)), TrueFileFilter.TRUE);
                for (File file : filesToDelete) {
                    boolean success = FileUtils.deleteQuietly(file);
                    if (!success) {
                        test.log(Status.WARNING,"Cannot delete old file at: " + file.getAbsolutePath());
                    }
                }
            }
        } catch (Exception e) {
            test.log(Status.WARNING, "unable to delete files. Failed with error: " + e.getMessage());
        }
    }

    public static String findFileInDirectoryRecursively(String directory, String fileName) {
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setIncludes(new String[]{"**/" + fileName});
        scanner.setBasedir(directory);
        scanner.setCaseSensitive(false);
        scanner.scan();
        String[] files = scanner.getIncludedFiles();
        return files[0].substring(files[0].lastIndexOf("\\") + 1);
    }

    public static void appendToFile (String file, String text) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(text);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            test.log(Status.INFO, "Failed with exception: " + e.getMessage());
        } finally {
            if (bw != null) try {
                bw.close();
            } catch (IOException e2) {
                test.log(Status.INFO, "Failed with exception: " + e2.getMessage());
            }
        }

    }
}
