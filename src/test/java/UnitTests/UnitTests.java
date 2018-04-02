package UnitTests;

import frameworkInfra.utils.Parser;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.XmlParser;
import ibInfra.ibService.IbService;
import ibInfra.vsui.VSUIService;
import ibInfra.windowscl.WindowsService;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class UnitTests {

    @Test(testName = "Check Basic Agent Assignment")
    public void checkBasicAgentAssignment(){
        WindowsService winService = new WindowsService();
        String output = winService.runCommandGetOutput(String.format(StaticDataProvider.WindowsCommands.GET_MEMORY_USAGE, "20000"));
        StringUtils.containsIgnoreCase(output, "CoordService.exe");
        StringUtils.containsIgnoreCase(output, "BuildSystem.exe");
        StringUtils.containsIgnoreCase(output, "BuildService.exe");


    }
}
