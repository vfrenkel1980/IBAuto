package Native.windowstests;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.BatmanBCTestBase;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.StaticDataProvider.Locations;
import frameworkInfra.utils.StaticDataProvider.ProjectsCommands;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static frameworkInfra.Listeners.SuiteListener.test;

public class BatmanMiscProjTests extends BatmanBCTestBase {

    @Test(testName = "Ruby2.4 SyncPrivateAssemblies")
    public void ruby24SyncPrivateAssemblies() {
        String result = "";
        int agentCount = 0;
        winService.runCommandWaitForFinish(ProjectsCommands.MISC_PROJECTS.RUBY_SYNC_PRIVATE_ASSEMBLIES);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"), "Build failed");
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
        for (String machine : batmanMachineList) {
            if (Parser.doesFileContainString(Locations.OUTPUT_LOG_FILE, "Agent '" + machine)) {
                agentCount++;
            }
        }
        Assert.assertTrue(agentCount > 0, "No agents were assigned to the build");
    }
}
