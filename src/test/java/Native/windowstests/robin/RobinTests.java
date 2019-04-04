package Native.windowstests.robin;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.RobinTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static frameworkInfra.Listeners.SuiteListener.test;

public class RobinTests extends RobinTestBase {

    @Test(testName = "Chrome release - build" , groups = { "Build" })
    public void chromeReleaseBuild(){
        if (testName.equals("Minimal")){
            test.log(Status.SKIP, "Skipping Chrome test on Minimal logging");
            throw new SkipException("Skipped test");
        }
        winService.runCommandWaitForFinish(StaticDataProvider.ProjectsCommands.CHROME.CHROME_RELEASE_CLEAN);
        int returnCode = winService.runCommandWaitForFinish(StaticDataProvider.ProjectsCommands.CHROME.CHROME_RELEASE_BUILD);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    @Test(testName = "Audacity 2017 - Debug - build" , groups = { "Build" })
    public void audacityDebugBuild(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_ROBIN.AUDACITY_X32_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }
}
