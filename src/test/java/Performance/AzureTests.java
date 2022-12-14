package Performance;

import cloudInfra.CloudHelper.PerformanceTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AzureTests extends PerformanceTestBase {

    @Test(testName = "Chromium Performance")
    public void chromiumPerformance() {
        projectName = "Chromium";
        winService.runCommandWaitForFinish(StaticDataProvider.ProjectsCommands.CHROME.CHROME_RELEASE_CLEAN_PERFORMANCE);
        int returnCode = winService.runCommandWaitForFinish(StaticDataProvider.ProjectsCommands.CHROME.CHROME_RELEASE_BUILD_PERFORMANCE);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

}
