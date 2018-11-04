package Native.UnitTests;

import com.aventstack.extentreports.Status;
import com.jcraft.jsch.JSchException;
import frameworkInfra.utils.*;
import frameworkInfra.utils.databases.PostgresJDBC;
import frameworkInfra.utils.databases.SQLiteJDBC;
import frameworkInfra.utils.parsers.CustomJsonParser;
import frameworkInfra.utils.parsers.HtmlParser;
import frameworkInfra.utils.parsers.Parser;
import ibInfra.dataObjects.postgres.CoordBuild;
import ibInfra.ibService.IbService;
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
        WindowsService winService = new WindowsService();
        winService.runCommandDontWaitForTermination(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_BATMAN.AUDACITY_X32_DEBUG, StaticDataProvider.ProjectsCommands.REBUILD));
        winService.runCommandDontWaitForTermination(StaticDataProvider.Processes.PSEXEC + " \\\\" + StaticDataProvider.WindowsMachines.SECOND_INITIATOR + " -u Administrator -p 4illumination -i 1 " +
                "\"C:\\Program Files (x86)\\IncrediBuild\\buildconsole\" C:\\QA\\Simulation\\VC11\\ACE_VC11\\ACE_vc2012.sln /rebuild /cfg=\"debug|win32\" /title=\"ACE 2012 - Debug\" " +
                "/out=\"C:\\QA\\simulation\\buildlog.txt\" /showagent /showcmd /showtime");

        winService.waitForProcessToFinishOnRemoteMachine(StaticDataProvider.WindowsMachines.SECOND_INITIATOR, "Administrator" , "4illumination", "buildsystem");
        winService.runCommandWaitForFinish("xcopy \"r:\\QA\\Simulation\\buildLog.txt\" " + StaticDataProvider.Locations.SECOND_INITIATOR_LOG_PATH );
        Assert.assertTrue(SystemActions.doesFileExist(StaticDataProvider.Locations.SECOND_INITIATOR_LOG_PATH + "buildLog.txt"));

        boolean isPresent = Parser.doesFileContainString(StaticDataProvider.Locations.SECOND_INITIATOR_LOG_PATH + "buildlog.txt", StaticDataProvider.LogOutput.AGENT);
        if (isPresent){
            SystemActions.copyFile(StaticDataProvider.Locations.SECOND_INITIATOR_LOG_PATH + "buildlog.txt", StaticDataProvider.Locations.QA_ROOT + "\\logs\\for_investigation");
        }
        SystemActions.deleteFile(StaticDataProvider.Locations.SECOND_INITIATOR_LOG_PATH + "buildlog.txt");
        winService.waitForProcessToFinish(StaticDataProvider.Processes.BUILDSYSTEM);
        Assert.assertTrue(isPresent, "No agent assigned to build");
    }

    @Test(testName = "test2")
    public void test2() {
        System.out.println("this is a test");
        Assert.assertEquals(0,0);
    }


    @Test(testName = "test3")
    public void test3 () {
        final String username = "incrediautomation@gmail.com";
        final String password = "4illumination";

        // setting gmail smtp properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // check the authentication
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("incrediautomation@gmail.com"));

            // recipients email address
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("mark.zvuluni@incredibuild.com, vlad.levishev@incredibuild.com"));

            // add the Subject of email
            message.setSubject("MS VS Integrated tests report");

            Multipart multipart = new MimeMultipart();

            // add the body message
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText("This email has an attachment. Please find the attach file. Thank You");
            multipart.addBodyPart(bodyPart);

            // attach the file
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.attachFile(new File("C:\\Reports\\vsreport.html"));
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Email Sent Successfully");

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    }
