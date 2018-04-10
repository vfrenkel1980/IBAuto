package UnitTests;

import frameworkInfra.sikuli.sikulimapping.IBMonitor.Monitor;
import frameworkInfra.utils.Parser;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.XmlParser;
import ibInfra.ibService.IbService;
import ibInfra.vsui.VSUIService;
import ibInfra.windowscl.WindowsService;
import org.apache.commons.lang3.StringUtils;
import org.sikuli.script.Pattern;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class UnitTests {

    @Test(testName = "Check Basic Agent Assignment")
    public void checkBasicAgentAssignment(){

        Pattern pattern = new Pattern();
        pattern = Monitor.Bars.VSRedBar;
        System.out.println("");


    }
}
