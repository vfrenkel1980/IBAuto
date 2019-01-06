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
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class UnitTests {

    @Test
    public void test() {
        IbService ibService = new IbService();
        String process = ibService.getIbConsoleInstallation("Latest");
        System.out.println(process.substring(process.lastIndexOf("\\")+1));
    }

    @Test(testName = "test2")
    public void test2() {
        WindowsService winService = new WindowsService();
        IbService ibService = new IbService();
        boolean isRunning = false;
        String process = ibService.getIbConsoleInstallation("Latest");
        winService.runCommandDontWaitForTermination(String.format(StaticDataProvider.WindowsCommands.IB_INSTALL_COMMAND + " /vs_integrated", process));
        while (winService.isProcessRunning(process.substring(process.lastIndexOf("\\") + 1)))
            if (winService.isProcessRunning("vsixinstaller.exe"))
                isRunning = true;

        Assert.assertFalse(isRunning, "VSIXIstaller running, shouldn't be");
    }

}
