package Native.windowstests;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.WindowsSimTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static frameworkInfra.Listeners.SuiteListener.test;

public class IBSamplesTests extends WindowsSimTestBase {

    @Test(testName = "Verify 7Z Sample1 Test")
    public void verify7ZSample1Test() {
        String result = "";
        winService.runCommandWaitForFinish(winService.changeCurDirTo(IBSamplesLocations.COMPRESSION_7Z_1)+".\\RunMe.bat");
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verify7ZSample1Test failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
        }
    }

    @Test(testName = "Verify 7Z Sample2 Test")
    public void verify7ZSample2Test() {
        String result = "";
        winService.runCommandWaitForFinish(winService.changeCurDirTo(IBSamplesLocations.COMPRESSION_7Z_2)+".\\RunMe.bat");
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verify7ZSample2Test failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
        }
    }

    @Test(testName = "Verify GZip Sample1 Test")
    public void verifyGZipSample1Test() {
        String result = "";
        winService.runCommandWaitForFinish(winService.changeCurDirTo(IBSamplesLocations.COMPRESSION_GZIP_1)+".\\RunMe.bat");
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyGZipSample1Test failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
        }
    }

    @Test(testName = "Verify GZip Sample2 Test")
    public void verifyGZipSample2Test() {
        String result = "";
        winService.runCommandWaitForFinish(winService.changeCurDirTo(IBSamplesLocations.COMPRESSION_GZIP_2)+".\\RunMe.bat");
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyGZipSample2Test failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
        }
    }

    @Test(testName = "Verify WinRAR Sample1 Test")
    public void verifyWinRARSample1Test() {
        String result = "";
        winService.runCommandWaitForFinish(winService.changeCurDirTo(IBSamplesLocations.COMPRESSION_WINRAR_1)+".\\RunMe.bat");
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyWinRARSample1Test failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
        }
    }

    @Test(testName = "Verify WinRAR Sample2 Test")
    public void verifyWinRARSample2Test() {
        String result = "";
        winService.runCommandWaitForFinish(winService.changeCurDirTo(IBSamplesLocations.COMPRESSION_WINRAR_2)+".\\RunMe.bat");
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyWinRARSample2Test failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
        }
    }

    @Test(testName = "Verify DevTools XGE Sample Test")
    public void verifyDevToolsXGESampleTest() {
        String result = "";
        winService.runCommandWaitForFinish(winService.changeCurDirTo(IBSamplesLocations.DEVTOOLS_XGE)+"\".\\run XGE.bat\"");
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyDevToolsXGESampleTest failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
        }
    }

    @Test(testName = "Verify DevTools Submission Sample Test")
    public void verifyDevToolsSubmissionSampleTest() {
        String result = "";
        winService.runCommandWaitForFinish(winService.changeCurDirTo(IBSamplesLocations.DEVTOOLS_SUBMISSION)+".\\BatchFileExecution.bat");
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyDevToolsSubmissionSampleTest failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
        }
    }

    @Test(testName = "Verify DevTools XML Sample Test")
    public void verifyDevToolsXMLSampleTest() {
        String result = "";
        winService.runCommandWaitForFinish(winService.changeCurDirTo(IBSamplesLocations.DEVTOOLS_XML)+".\\BatchFile.bat");
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyDevToolsXMLSampleTest failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
        }
    }

    @Test(testName = "Verify Nconvert Sample Test")
    public void verifyNconvertSampleTest() {
        String result = "";
        winService.runCommandWaitForFinish(winService.changeCurDirTo(IBSamplesLocations.NCONVERT)+".\\RunMe.bat");
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyNconvertSampleTest failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
        }
    }

    @Test(testName = "Verify Ffmpeg Sample Test")
    public void verifyFfmpegSampleTest() {
        String result = "";
        winService.runCommandWaitForFinish(winService.changeCurDirTo(IBSamplesLocations.FFMPEG)+".\\RunMe.bat");
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyFfmpegSampleTest failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
        }
    }


    @Test(testName = "Verify Jom Sample Test")
    public void verifyJomSampleTest() {
        String result = "";
        winService.runCommandWaitForFinish(winService.changeCurDirTo(IBSamplesLocations.JOM)+".\\RunJomSample.bat");
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyJomSampleTest failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.ERROR, "Test failed with the following error: " + e.getMessage());
        }
    }

    //make
    //Nmake
}
