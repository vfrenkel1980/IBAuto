package UnitTests;

import frameworkInfra.utils.Parser;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.XmlParser;
import ibInfra.ibService.IbService;
import ibInfra.vsui.VSUIService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class UnitTests {

    @Test(testName = "Check Basic Agent Assignment")
    public void checkBasicAgentAssignment(){
        List rawList;
        List<String> gridMachineList;
        rawList = XmlParser.getIpList("BatmanGrid.xml");
        gridMachineList = XmlParser.breakDownIPList(rawList);

        IbService ibService = new IbService();
        ibService.cleanAndBuild(StaticDataProvider.Processes.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.ConsoleAppProj.CONSOLE_APP_SUCCESS, "%s"));
        for (String machine : gridMachineList ) {
            Assert.assertTrue(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "Agent '" + machine));
        }
    }
}
