package Native.vstests;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.testbases.VSIntegrationTestBase;
import frameworkInfra.utils.parsers.Parser;
import frameworkInfra.utils.StaticDataProvider.*;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;



@Listeners(SuiteListener.class)
public class VSIntegrationVS15Tests extends VSIntegrationTestBase{

    /**
     * @test Ps5-2017Sample1-DebugBuild<br>
     * @pre{ visual studio 2017 vesrion 15 should be installed on VM/machine}
     * @steps{ - Run the VsIntegration test  "tutorial_texture_streaming"
     *in DebugBuild mode with visualStudio 2017 ,version 15}
     * @result{ - Build is succeeded,no errors in logs}
     */
    @Test(testName = "Ps5-2017Sample1-DebugBuild" )
    public void ps52017Sample1DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.PS5_PROSPERO_SAMPLE1_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Ps5-2017Sample2-ReleaseBuild<br>
     * @pre{ visual studio 2017 vesrion 15 should be installed on VM/machine}
     * @steps{ - Run the VsIntegration test  "tutorial_texture_streaming" in ReleaseBuild mode
     * with visualStudio 2017 ,version 15}
     * @result{ - Build is succeeded,no errors in logs}
     */


    @Test(testName = "Ps5-2017Sample2-ReleaseBuild" )
    public void ps52017Sample2ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.PS5_PROSPERO_SAMPLE2_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }


    /**
     * @test Ps5-2017Sample3-DebugBuild<br>
     * @pre{ visual studio 2017 vesrion 15 should be installed on VM/machine}
     * @steps{ - Run the VsIntegration test  "api_font" in ReleaseBuild mode
     * with visualStudio 2017 ,version 15}
     * @result{ - Build is succeeded,no errors in logs}
     */
    @Test(testName = "Ps5-2017Sample3-DebugBuild" )
    public void ps52017Sample3DebugBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.PS5_PROSPERO_SAMPLE3_DEBUG, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

    /**
     * @test Ps5-2017Sample4-ReleaseBuild<br>
     * @pre{ visual studio 2017 vesrion 15 should be installed on VM/machine}
     * @steps{ - Run the VsIntegration test  "api_font" in ReleaseBuild mode
     * with visualStudio 2017 ,version 15}
     * @result{ - Build is succeeded,no errors in logs }
     */
    @Test(testName = "Ps5-2017Sample4-ReleaseBuild" )
    public void ps52017Sample4ReleaseBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.PS5_PROSPERO_SAMPLE4_RELEASE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }


    /**
     * @test Ps5-2017Sample5-ProfileBuild<br>
     * @pre{ visual studio 2017 vesrion 15 should be installed on VM/machine}
     * @steps{ - Run the VsIntegration test  "api_edge_animation" in ProfileBuild mode
     * with visualStudio 2017 ,version 15}
     * @result{ - Build is succeeded,no errors in logs }
     */
    @Test(testName = "Ps5-2017Sample5-ProfileBuild" )
    public void ps52017Sample5ProfileBuild(){
        int returnCode = ibService.cleanAndBuild(IbLocations.BUILD_CONSOLE + String.format(ProjectsCommands.VC15_BATMAN.PS5_PROSPERO_SAMPLE5_PROFILE, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }


}
