package Native.UnitTests;

import com.aventstack.extentreports.ExtentReports;
import frameworkInfra.sikuli.sikulimapping.IBMonitor.Monitor;
import frameworkInfra.utils.ConfigurationReader;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.StaticDataProvider;
import ibInfra.vsui.VSUIService;
import ibInfra.windowscl.WindowsService;
import org.apache.commons.io.FileUtils;
import org.sikuli.script.Pattern;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

public class UnitTests {

    @Test(testName = "Check Basic Agent Assignment")
    public void checkBasicAgentAssignment() throws IOException {
        VSUIService vsService = new VSUIService();
        ConfigurationReader confReader = new ConfigurationReader();
        String actual = vsService.getInstalledMSBuildVersion();
        String expected = confReader.getProperty("MSBuildVersion");
        Assert.assertEquals(actual, expected, "Installed MSBuild version does not match expected");


    }
}
