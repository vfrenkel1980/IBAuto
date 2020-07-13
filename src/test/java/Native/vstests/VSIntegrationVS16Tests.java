package Native.vstests;

import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.testbases.VSIntegrationTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(SuiteListener.class)
public class VSIntegrationVS16Tests  extends VSIntegrationTestBase {

    /**
     * @test Ps5 Prospero -2019Sample1-DebugBuild<br>
     * @pre{ visual studio 2019 version 15 should be installed on VM/machine}
     * @steps { - Run the VsIntegration test  "tutorial_texture_streaming"
     * in Debug Build mode with visualStudio 2019 ,version 16}
     * @result{ - Build is succeeded,no errors in logs}
     */
    @Test(testName = "Ps5-2019Sample1-DebugBuild" )
    public void ps52019Sample1DebugBuild(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_BATMAN.PS5_PROSPERO_SAMPLE6_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Ps5 Prospero -2019Sample2-ReleaseBuild<br>
     * @pre  { visual studio 2019 vesrion 16 should be installed on VM/machine}
     * @steps { - Run the VsIntegration test  "tutorial_texture_streaming" in ReleaseBuild mode
     * with visualStudio 2019 ,version 16}
     * @result{ - Build is succeeded,no errors in logs}
     */


    @Test(testName = "Ps5-2019Sample2-ReleaseBuild" )
    public void ps52019Sample2ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_BATMAN.PS5_PROSPERO_SAMPLE7_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }


    /**
     * @test Ps5 Prospero -2019Sample3-DebugBuild<br>
     * @pre{ visual studio 2019 vesrion 16 should be installed on VM/machine}
     * @steps{ - Run the VsIntegration test  "api_font" in ReleaseBuild mode
     * with visualStudio 2019 ,version 16}
     * @result{ - Build is succeeded,no errors in logs}
     */
    @Test(testName = "Ps5-2019Sample3-DebugBuild" )
    public void ps52019Sample3DebugBuild(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_BATMAN.PS5_PROSPERO_SAMPLE8_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Ps5 Prospero-2019Sample4-ReleaseBuild<br>
     * @pre{ visual studio 2019 vesrion 16 should be installed on VM/machine}
     * @steps{ - Run the VsIntegration test  "api_font" in ReleaseBuild mode
     * with visualStudio 2019 ,version 16}
     * @result{ - Build is succeeded,no errors in logs }
     */
    @Test(testName = "Ps5-2019Sample4-ReleaseBuild" )
    public void ps52019Sample4ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_BATMAN.PS5_PROSPERO_SAMPLE9_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }


    /**
     * @test Ps5 Prospero-2019Sample5-ProfileBuild<br>
     * @pre{ visual studio 2019 vesrion 16 should be installed on VM/machine}
     * @steps{ - Run the VsIntegration test  "api_edge_animation" in ProfileBuild mode
     * with visualStudio 2019 ,version 16}
     * @result{ - Build is succeeded,no errors in logs }
     */
    @Test(testName = "Ps5-2019Sample5-ProfileBuild" )
    public void ps52019Sample5ProfileBuild(){
        int returnCode = ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.VC15_BATMAN.PS5_PROSPERO_SAMPLE10_PROFILE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }







}
