package frameworkInfra.utils;

import com.aventstack.extentreports.Status;

import java.io.File;
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

import static frameworkInfra.Listeners.SuiteListener.test;
import static frameworkInfra.testbases.TestBase.log;

public class SystemActions {

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
            test.log(Status.INFO, "Killing process " + process);
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

    public static String getLocalDateAsString(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yy");
        String newDate = now.format(formatter);
        test.log(Status.INFO, "Checking local time" + newDate);
        return newDate;
    }

    public static void setLocalDateFromString(String date) {
        try {
            test.log(Status.INFO, "Changing machine time to: " + date);
            Runtime.getRuntime().exec("cmd /C date " + date);
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static boolean doesFileExist(String filePath){
        File file = new File(filePath);
        return file.exists();
    }

    public static void copyFile(String src, String dest){
        try {
            FileUtils.copyFile(new File(src), new File(dest));
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
    }

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
}
