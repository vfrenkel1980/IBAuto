package Native.uitests;

import com.aventstack.extentreports.Status;
import frameworkInfra.sikuli.sikulimapping.IBSettings.IBSettings;
import frameworkInfra.testbases.TestBase;
import frameworkInfra.testbases.UIValidationTestBase;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.annotations.Test;

import static frameworkInfra.Listeners.SuiteListener.test;

public class ButtonsUITests extends UIValidationTestBase {

    @Test(testName = "Agent buttons" )
    public void clickOnAgentSetingsButons(){
        try {
            // Agent tabs
            screen.wait(IBSettings.TrayIcon.Green.similar((float) 0.9), 15).rightClick();
            screen.wait(IBSettings.TrayIcon.agentSettingsTray.similar((float) 0.9), 15).click();
            screen.exists(IBSettings.agent.similar((float) 0.9), 15);
            screen.exists(IBSettings.GeneralTab.similar((float) 0.9), 15);
            screen.exists(IBSettings.PreferenceTab.similar((float) 0.9), 15);
            screen.exists(IBSettings.CpuUtilTab.similar((float) 0.9), 15);
            //Network tabs
            screen.exists(IBSettings.network.similar((float) 0.9), 15).click();
            screen.exists(IBSettings.networkcoordinator.similar((float) 0.9), 15);
            screen.exists(IBSettings.coordinatorNameTB.similar((float) 0.9), 15);
            // Build tabs
            screen.exists(IBSettings.BuildMonitorTab.similar((float) 0.9), 15).click();
            screen.exists(IBSettings.GeneralTab.similar((float) 0.9), 15);
            screen.exists(IBSettings.OutputTab.similar((float) 0.9), 15);
            screen.exists(IBSettings.AdvancedTab.similar((float) 0.9), 15);
            // VS builds tabs

            SystemActions.sleep(10);
        } catch (FindFailed findFailed) {
            test.log(Status.WARNING, "Failed to open agent settings with error: " + findFailed.getMessage());
            Assert.fail();
        }
    }
    @Test(testName = "Network buttons")
    public void networkButtonsIfExist(){
        try{
            screen.wait(IBSettings.TrayIcon.Green.similar((float) 0.9), 15).rightClick();
            screen.wait(IBSettings.TrayIcon.agentSettingsTray.similar((float) 0.9), 15).click();
        }catch(FindFailed findFailed){
            test.log(Status.WARNING, "failed to found networks buttons: " + findFailed.getMessage());
            Assert.fail();
        }
    }

}
