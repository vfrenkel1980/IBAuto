package Native.windowstests;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.WindowsTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static frameworkInfra.Listeners.SuiteListener.test;

public class IBSamplesTests extends WindowsTestBase {

    @Test(testName = "Verify 7Z Sample1 Test")
    public void verify7ZSample1Test() {
        String result = "";
        winService.runCommandWaitForFinish(StaticDataProvider.IBSamplesLocations.COMPRESSION_7Z_1);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verify7ZSample1Test failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
    }

    @Test(testName = "Verify 7Z Sample2 Test")
    public void verify7ZSample2Test() {
        String result = "";
        winService.runCommandWaitForFinish(StaticDataProvider.IBSamplesLocations.COMPRESSION_7Z_2);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verify7ZSample2Test failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
    }

    @Test(testName = "Verify GZip Sample1 Test")
    public void verifyGZipSample1Test() {
        String result = "";
        winService.runCommandWaitForFinish(StaticDataProvider.IBSamplesLocations.COMPRESSION_GZIP_1);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyGZipSample1Test failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
    }

    @Test(testName = "Verify GZip Sample2 Test")
    public void verifyGZipSample2Test() {
        String result = "";
        winService.runCommandWaitForFinish(StaticDataProvider.IBSamplesLocations.COMPRESSION_GZIP_2);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyGZipSample2Test failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
    }

    @Test(testName = "Verify WinRAR Sample1 Test")
    public void verifyWinRARSample1Test() {
        String result = "";
        winService.runCommandWaitForFinish(StaticDataProvider.IBSamplesLocations.COMPRESSION_WINRAR_1);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyWinRARSample1Test failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
    }

    @Test(testName = "Verify WinRAR Sample2 Test")
    public void verifyWinRARSample2Test() {
        String result = "";
        winService.runCommandWaitForFinish(StaticDataProvider.IBSamplesLocations.COMPRESSION_WINRAR_2);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyWinRARSample2Test failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
    }

    @Test(testName = "Verify DevTools XGE Sample Test")
    public void verifyDevToolsXGESampleTest() {
        String result = "";
        winService.runCommandWaitForFinish(StaticDataProvider.IBSamplesLocations.DEVTOOLS_XGE);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyDevToolsXGESampleTest failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
    }

    @Test(testName = "Verify DevTools Submission Sample Test")
    public void verifyDevToolsSubmissionSampleTest() {
        String result = "";
        winService.runCommandWaitForFinish(StaticDataProvider.IBSamplesLocations.DEVTOOLS_SUBMISSION);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyDevToolsSubmissionSampleTest failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
    }

    @Test(testName = "Verify DevTools XML Sample Test")
    public void verifyDevToolsXMLSampleTest() {
        String result = "";
        winService.runCommandWaitForFinish(StaticDataProvider.IBSamplesLocations.DEVTOOLS_XML);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyDevToolsXMLSampleTest failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
    }

    @Test(testName = "Verify Nconvert Sample Test")
    public void verifyNconvertSampleTest() {
        String result = "";
        winService.runCommandWaitForFinish(StaticDataProvider.IBSamplesLocations.NCONVERT);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyNconvertSampleTest failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
    }

    @Test(testName = "Verify Ffmpeg Sample Test")
    public void verifyFfmpegSampleTest() {

        String result = "";
        winService.runCommandWaitForFinish(StaticDataProvider.IBSamplesLocations.FFMPEG);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyFfmpegSampleTest failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
    }


    @Test(testName = "Verify Jom Sample Test", enabled = false)
    public void verifyJomSampleTest() {
        String result = "";
        winService.runCommandWaitForFinish("cmd /c c: && cd C:\\QA\\Simulation\\projects\\Samples\\Make And Build Tools\\Jom\\ && " + StaticDataProvider.IBSamplesLocations.JOM);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "verifyJomSampleTest failed with exit code " + result);
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
    }

    //make
    //Nmake
}
