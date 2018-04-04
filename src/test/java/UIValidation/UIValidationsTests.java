package UIValidation;

import com.aventstack.extentreports.Status;
import frameworkInfra.sikuli.sikulimapping.IBMonitor.Monitor;
import frameworkInfra.sikuli.sikulimapping.IBSettings.IBSettings;
import frameworkInfra.testbases.UIValidationTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class UIValidationsTests extends UIValidationTestBase {


    @Test(testName = "Verify VS Monitor Bar")
    public void verifyVSMonitorBar(){
        batchProjects.forEach(item->{
            if(project.equals(item)){
                test.log(Status.SKIP, "Skipping test as this is a Batch project");
                throw new SkipException("Skipped test");
            }
        });

        vsService.openVSInstance("15");
        vsService.openProject(projectLocation);
        vsService.performIbActionFromMenu(VsActions.REBUILD_SOLUTION);
        try {
            screen.wait(pattern.similar((float) 0.9),2);
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
            Assert.fail("Failed to find VS Bar on screen");
        }

    }
}
