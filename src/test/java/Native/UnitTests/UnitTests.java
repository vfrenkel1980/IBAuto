package Native.UnitTests;

import com.aventstack.extentreports.Status;
import com.jcraft.jsch.JSchException;
import frameworkInfra.sikuli.sikulimapping.IBSettings.IBSettings;
import frameworkInfra.utils.*;
import frameworkInfra.utils.databases.PostgresJDBC;
import frameworkInfra.utils.databases.SQLiteJDBC;
import frameworkInfra.utils.parsers.CustomJsonParser;
import frameworkInfra.utils.parsers.HtmlParser;
import frameworkInfra.utils.parsers.Parser;
import ibInfra.dataObjects.postgres.CoordBuild;
import ibInfra.ibService.IbService;
import ibInfra.ibUIService.IBUIService;
import ibInfra.vs.VSUIService;
import ibInfra.windowscl.WindowsService;
import javafx.geometry.Pos;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.velocity.runtime.directive.Parse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.*;
import java.text.SimpleDateFormat;
import java.util.*;
import static com.sun.jna.platform.win32.WinReg.*;
import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;

public class UnitTests {
    WindowsService winService = new WindowsService();
    IbService ibService = new IbService();

    @Test(testName = "test1")
    public void finder() {
        int exit = -1;
        String dirName = StaticDataProvider.IbLocations.IB_ROOT;
        File dir = new File(dirName);
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".exe") || filename.endsWith(".dll");
            }
        });

        for (File file : files) {
            if (!file.getName().contains("WindowsInstaller-KB893803-v2-x86") && !file.getName().contains("vc2017_redist.x86")
                    && !file.getName().contains("vc9crt_x86")) {
                exit = winService.runCommandWaitForFinish("C:\\Users\\Aleksandra\\Downloads\\SignTool\\signtool.exe verify /pa /ds 0 \"" + file.getAbsolutePath() + "\"");
                Assert.assertTrue(exit == 0, file.getName() + " is not signed with sha1 signature");
                exit = winService.runCommandWaitForFinish("C:\\Users\\Aleksandra\\Downloads\\SignTool\\signtool.exe verify /pa /ds 1 \"" + file.getAbsolutePath() + "\"");
                Assert.assertTrue(exit == 0, file.getName() + " is not signed with sha256 signature");
            }
        }
    }


    @Test(testName = "test1")
    public void testAttachment() {
        RegistryService.deleteRegKey(HKEY_CLASSES_ROOT,"WOW6432Node\\Interface","{8CA4C95D-CBE4-474A-AB9E-3F8C9313D740}");


    }
}


