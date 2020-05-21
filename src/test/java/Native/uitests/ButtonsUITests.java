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

/**
 * Create by tanya gaus
 */
public class ButtonsUITests extends UIValidationTestBase {

    @Test(testName = "Agent buttons" )
    public void clickOnAgentSetingsButons(){
        try {
            // Agent Settings Tabs
            screen.wait(IBSettings.TrayIcon.Green.similar((float) 0.9), 15).rightClick();
            screen.wait(IBSettings.TrayIcon.agentSettingsTray.similar((float) 0.9), 15).click();
            screen.exists(IBSettings.agent.similar((float) 0.9), 15);
            screen.exists(IBSettings.network.similar((float) 0.9), 15);
            screen.exists(IBSettings.InitiatorTab.similar((float) 0.9), 15);
            screen.exists(IBSettings.BuildMonitorTab.similar((float) 0.9), 15);
            screen.exists(IBSettings.VisualStudioBuilds.similar((float) 0.9), 15);
            screen.exists(IBSettings.visualStudioAddIn.similar((float) 0.9), 15);
            screen.exists(IBSettings.tryIconTab.similar((float) 0.9), 15);
           // Agent Options Tabs
            screen.wait(IBSettings.agent.similar((float) 0.9), 15).click();
            screen.exists(IBSettings.GeneralTab.similar((float) 0.9), 15);
            screen.exists(IBSettings.PreferenceTab.similar((float) 0.9), 15);
            screen.exists(IBSettings.CpuUtilTab.similar((float) 0.9), 15);
            //Network Options Tabs
            screen.exists(IBSettings.network.similar((float) 0.9), 15).click();
            screen.exists(IBSettings.networkcoordinator.similar((float) 0.9), 15);
            screen.exists(IBSettings.coordinatorNameTB.similar((float) 0.9), 15);
            // Build Monitor Options Tabs
            screen.exists(IBSettings.BuildMonitorTab.similar((float) 0.9), 15).click();
            screen.exists(IBSettings.GeneralTab.similar((float) 0.9), 15);
            screen.exists(IBSettings.OutputTab.similar((float) 0.9), 15);
            screen.exists(IBSettings.AdvancedTab.similar((float) 0.9), 15);
            // VS builds tabs
            screen.wait(IBSettings.VisualStudioBuilds.similar((float) 0.9), 15).click();
            screen.exists(IBSettings.GeneralTab.similar((float) 0.9), 15);
            screen.exists(IBSettings.AdvancedTab.similar((float)0.9),15);
            //VS Add-In options tabs
            screen.wait(IBSettings.visualStudioAddIn.similar((float) 0.9), 15).click();
            screen.exists(IBSettings.GeneralTab.similar((float) 0.9), 15);
            screen.exists(IBSettings.OKButton.similar((float) 0.9), 15);

            SystemActions.sleep(10);
        } catch (FindFailed findFailed) {
            test.log(Status.WARNING, "Failed to open agent settings with error: " + findFailed.getMessage());
            Assert.fail();
        }
    }
    @Test(testName = "Coordinator monitor buttons")
    public void clickOnCoordMonButtons(){
        try {
            screen.wait(IBSettings.TrayIcon.Green.similar((float) 0.9), 15).rightClick();
            screen.wait(IBSettings.TrayIcon.agentSettingsTray.similar((float) 0.9), 15).click();
        }catch(FindFailed findFailed){
            test.log(Status.WARNING, "Failed to open agent settings with error: " + findFailed.getMessage());
        }
    }


}
