package Native.unitTesting;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.UnitTestingTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.parsers.Parser;
import ibInfra.ibExecs.IIBCoordMonitor;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import static frameworkInfra.Listeners.SuiteListener.test;

/**
 * @brief Unit tests execution with IbTestConsole
 * - NUnit2
 * - Nunit3
 * @details Requires Unit Tests license solution
 */
public class IBTCNunitTests extends UnitTestingTestBase {
    IIBCoordMonitor coordMonitor = new IIBCoordMonitor();

    /**
     * @test IbTestConsole "HELP" with /help flag test<br>
     * @pre{ }
     * @steps{ - Run the IbTestConsole.exe with the /help flag}
     * @result{ - The "HELP" ("Usage:\n  IbTestConsole.exe [options] <command>") is displayed in the console.}
     */
    @Test(testName = "IBTC Help Test")
    public void iBTCHelpTest() {
        String out = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + " /help");
        Assert.assertTrue(out.contains("Usage:\n  IbTestConsole.exe [options] <command>"));
    }

    /**
     * @test IbTestConsole "HELP" without parameters test. <a href="http://redmine.incredibuild.local/issues/10200">Bug #10200</a><br>
     * @pre{ }
     * @steps{ - Run the IbTestConsole.exe without parameters}
     * @result{ - The "HELP" ("Usage:\n  IbTestConsole.exe [options] <command>") is displayed in the console.}
     */
    @Test(testName = "IBTC No Help Test")
    public void iBTCNoHelpTest() {
        String out = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE);
        Assert.assertTrue(out.contains("Usage:\n  IbTestConsole.exe [options] <command>"));
    }

    //NUNIT2
    /**
     * @test NUnit2 /logfile & /loglevel flag test.<br>
     * @pre{ }
     * @steps{ - Run the nunit-console-master tests with the /logfile="c:\path\to\log.txt" and /loglevel=info flag.}
     * @result{ - Build is succeeded;
     * - The log file is created.}
     */
    @Test(testName = "nunit2 LogFile Test")
    public void nunit2logFileTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_LOGFILE_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        Assert.assertTrue(new File(Locations.OUTPUT_LOG_FILE).exists(), "LogFile is not found");
    }

    /**
     * @test NUnit2 Path with spaces support test <br>
     * @pre{ }
     * @steps{ - Run the nunit slow sample tests where the path to dll contains spaces.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit2 Path With Spaces Test")
    public void nunit2PathWithSpacesTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_1DLL_WITH_SPACE_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit2 Assembly Level support test.<br>
     * @pre{ }
     * @steps{ - Run the nunit framework tests.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit2 Assembly Level Test")
    public void nunit2AssemblyLevelTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit2 Test Level support test.<br>
     * @pre{ }
     * @steps{ - Run the nunit framework tests with the /testlevel=10 flag.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit2 Test Level Test")
    public void nunit2TestLevelTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_TESTLEVEL_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit2 testlevel=deep test. (Every testcase runs in the separate task bar).<br>
     * @pre{ }
     * @steps{ - Run the nunit framework tests with the /testlevel=deep flag.}
     * @result{ - Build is succeeded.}
     */
//    @Test(testName = "NUnit2 Testlevel Deep Test")
//    public void nunit2TestLevelDeepTest() {
//        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_TESTLEVEL_DEEP_TEST);
//        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
//    }

    /**
     * @test NUnit2 /result=testresult.xml is created in the deep testlevel mode test. <a href="http://redmine.incredibuild.local/issues/9943">Bug #9943</a>.<br>
     * @pre{ }
     * @steps{ - Run the nunit framework tests with the /result=C:\path\to\res.xml flag.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit2 Result Testlevel Deep Test")
    public void nunit2ResultTestLevelDeepTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_TESTLEVEL_DEEP_TEST + " /result=\"" + Locations.QA_ROOT + "\\nunitres.xml\"");
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        File f = new File(Locations.QA_ROOT + "\\nunitres.xml");
        Assert.assertTrue(f.isFile(), "The test result file is not created for " + testName);
        f.delete();
    }

    /**
     * @test NUnit2 IBTC /targetdir flag test (the dll is written with relative (not absolute) path)<br>
     * @pre{ }
     * @steps{ - Run the nunit framework tests with the /targetdir="c:\path\to\dll\dir" flag.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit2 Targetdir Test")
    public void nunit2TargetdirTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_TARGETDIR_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit2 Test with filter /run=[namespaces]<br>
     * @pre{ }
     * @steps{ - Run the nunit2 framework tests with filter /run=[namespaces].}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit2 with filter /run namespaces test")
    public void nunit2FilterRunNamespacesTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_WITH_FILTER_RUN_NAMESPACES_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit2 Test with filter /run=[fixture]<br>
     * @pre{ }
     * @steps{ - Run the nunit2 framework tests with filter /run=[fixture].}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit2 with filter /run fixture test")
    public void nunit2FilterRunFixtureTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_WITH_FILTER_RUN_FIXTURES_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit2 Test with filter /run=[tests]<br>
     * @pre{ }
     * @steps{ - Run the nunit2 framework tests with filter /run=[tests].}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit2 with filter /run tests test")
    public void nunit2FilterRunTestsTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_WITH_FILTER_RUN_TESTS_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit2 Test with filter /runlist<br>
     * @pre{ }
     * @steps{ - Run the nunit2 framework tests with filter /runlist.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit2 with filter runlist test")
    public void nunit2FilterRunListTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_WITH_FILTER_RUNlIST_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit2 Test with filter /config<br>
     * @pre{ }
     * @steps{ - Run the nunit2 framework tests with filter /config.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit2 with filter config test")
    public void nunit2FilterConfigTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_WITH_FILTER_CONFIG_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit2 Test with filter /framework<br>
     * @pre{ }
     * @steps{ - Run the nunit2 framework tests with filter /framework.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit2 with filter framework test")
    public void nunit2FilterFrameworkTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_WITH_FILTER_FRAMEWORK_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit2 Test with filter /process<br>
     * @pre{ }
     * @steps{ - Run the nunit2 framework tests with filter /process.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit2 with filter process test")
    public void nunit2FilterProcessTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_WITH_FILTER__PROCESS_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit2 /xml=testresult.xml is created in the deep testlevel mode test. <a href="http://redmine.incredibuild.local/issues/9943">Bug #9943</a>.<br>
     * @pre{ }
     * @steps{ - Run the nunit framework tests with /xml=C:\path\to\res.xml flag.}
     * @result{ - Build is succeeded;
     * - The result.xml file is created}
     */
    @Test(testName = "NUnit2 Xml Testlevel Deep Test")
    public void nunit2XmlTestLevelDeepTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_DEEP_XML_RESULT_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        File f = new File(Locations.QA_ROOT + "\\nunitres.xml");
        Assert.assertTrue(f.isFile(), "The test result file is not created for " + testName);
        f.delete();
    }

    /**
     * @test NUnit2 testresult.xml isn't created in the deep testlevel mode with /noresult flag test.<br>
     * @pre{ }
     * @steps{ - Run the nunit framework tests with the /noresult flag.}
     * @result{ - Build is succeeded;
     * - The result.xml file is created}
     */
    @Test(testName = "NUnit2 NoResult Testlevel Deep Test")
    public void nunit2NoResultTestLevelDeepTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_TESTLEVEL_DEEP_TEST + " /noresult");
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        Assert.assertFalse(new File(System.getProperty("user.dir") + "\\TestResult.xml").isFile(), "The test result file is created for " + testName);
    }

    /**
     * @test NUnit2 testresult.xml isn't created in the deep testlevel mode with the /noxml flag test.<br>
     * @pre{ }
     * @steps{ - Run the nunit framework tests with the /noxml flag.}
     * @result{ - Build is succeeded.
     * - The result.xml file isn't created}
     */
    @Test(testName = "NUnit2 No Xml Testlevel Deep Test")
    public void nunit2NoXmlTestLevelDeepTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_TESTLEVEL_DEEP_TEST + " /noxml");
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        Assert.assertFalse(new File(System.getProperty("user.dir") + "\\TestResult.xml").isFile(), "The test result file is created for " + testName);
    }

    /**
     * @test NUnit2 SameOS flag test.<br>
     * @pre{ }
     * @steps{ - Run the nunit2 framework tests with /sameos flag}
     * @result{ - Build is succeeded;
     * - All assigned to the build agents have the same OS version: windows 10 or windows server 2016.}
     */
    @Test(testName = "NUnit2 SameOS Flag Test")
    public void nunit2SameOSFlagTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_TESTLEVEL_TEST + " /sameos");
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        Set<String> helpers = Parser.getHelpers(Locations.OUTPUT_LOG_FILE);
        for (String helper : helpers) {
            try {
                Assert.assertTrue(coordMonitor.getAgentOSVersion(helper).equals("10"), helper + " os version isn't equal to 10.");
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @todo add html parser to test
     * @test NUnit2  <a href="https://github.com/extent-framework/extentreports-dotnet-cli">Extent HTML Reporting Framework</a> support with Assembly Level test.<br>
     * @pre{ }
     * @steps{ - Run the nunit framework tests;
     * - Run extent html reporting tool.}
     * @result{ - 2 file are created in the "reports" folder. }
     */
    @Test(testName = "NUnit2 Extent Report Assembly Level Test")
    public void nunit2ExtentReportAssemblyLevelTest() {
        winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_ASSEMBLY_XML_RESULT_TEST);
        int exitCode = winService.runCommandWaitForFinish("extent -i " + System.getProperty("user.dir") + "\\nunitres.xml -o reports/");
        Assert.assertTrue(exitCode == 0, "The test reporter execution failed with the exitcode " + exitCode);
        File index = new File(System.getProperty("user.dir") + "\\reports\\index.html");
        Assert.assertTrue(index.isFile(), "The test result index file is not created");
        index.delete();
        File dashboard = new File(System.getProperty("user.dir") + "\\reports\\dasboard.html");
        Assert.assertTrue(dashboard.isFile(), "The test result dashboard file is not created");
        dashboard.delete();
    }

    /**
     * @todo add html parser to test
     * @test NUnit2  <a href="https://github.com/extent-framework/extentreports-dotnet-cli">Extent HTML Reporting Framework</a> support with Test Level test.<br>
     * @pre{ }
     * @steps{ - Run the nunit framework tests with /testlevel and /xml flags;
     * - Run extent html reporting tool.}
     * @result{ - 2 file are created in the "reports" folder. }
     */
    @Test(testName = "NUnit2 Extent Report Test Level Test")
    public void nunit2ExtentReportTestLevelTest() {
        winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_ASSEMBLY_XML_RESULT_TEST + " /testlevel=11");
        int exitCode = winService.runCommandWaitForFinish("extent -i " + System.getProperty("user.dir") + "\\nunitres.xml -o reports/");
        Assert.assertTrue(exitCode == 0, "The test reporter execution failed with the exitcode " + exitCode);
        File index = new File(System.getProperty("user.dir") + "\\reports\\index.html");
        Assert.assertTrue(index.isFile(), "The test result index file is not created");
        index.delete();
        File dashboard = new File(System.getProperty("user.dir") + "\\reports\\dasboard.html");
        Assert.assertTrue(dashboard.isFile(), "The test result dashboard file is not created");
        dashboard.delete();
    }

    //Negative flow
    /**
     * @test NUnit2 Negative flow Not supported flag /nologo test.<br>
     * @pre{ }
     * @steps{ - Run the nunit framework tests with the /nologo flag.}
     * @result{ - Build fails with return code -4.
    */
    @Test(testName = "NUnit2 Negative Flow Filter /nologo test")
    public void nunit2NegativeFlowFilterNoLogoTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT2_FRAMEWORK_1DLL_TEST + " /nologo");
        Assert.assertTrue(exitCode == -4, "The test execution failed with the exitcode " + exitCode);
    }

//NUNIT3
    /**
     * @test NUnit3 /logfile & /loglevel flag test.<br>
     * @pre{ }
     * @steps{ - Run the nunit-console-master tests with the /logfile="c:\path\to\log.txt" and /loglevel=info flag.}
     * @result{ - Build is succeeded;
     * - The log file is created.}
     */
    @Test(testName = "nunit3 LogFile Test")
    public void nunit3LogFileTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_LOGFILE_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        Assert.assertTrue(new File(Locations.OUTPUT_LOG_FILE).exists(), "LogFile is not found");
    }

    /**
     * @test NUnit3 Assembly Level support test.<br>
     * @pre{ }
     * @steps{ - Run the nunit-console-master tests.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Assembly Level Test")
    public void nunit3AssemblyLevelTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 Test Level support test.<br>
     * @pre{ }
     * @steps{ - Run the nunit-console-master tests with the /testlevel=10 flag.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Test Level Test")
    public void nunit3TestLevelTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_TESTLEVEL_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 testlevel=deep test. (Every testcase runs in the separate task bar).<br>
     * @pre{ }
     * @steps{ - Run the nunit-console-master tests with the /testlevel=deep flag.}
     * @result{ - Build is succeeded.}
     */
//    @Test(testName = "NUnit3 Testlevel Deep Test")
//    public void nunit3TestLevelDeepTest() {
//        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_TESTLEVEL_DEEP_TEST);
//        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
//    }

    /**
     * @test NUnit3 IBTC /targetdir flag test (the dll is written with relative (not absolute) path)<br>
     * @pre{ }
     * @steps{ - Run the nunit-console-master tests with the /targetdir="c:\path\to\dll\dir" flag.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Targetdir Test")
    public void nunit3TargetdirTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_TARGETDIR_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 Test with filter --skipnontestassemblies<br>
     * @pre{ }
     * @steps{ - Run the nunit3 framework tests with filter --skipnontestassemblies.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit3 with filter --skipnontestassemblies test")
    public void nunit3WithFilterSkipnontestassembliesTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_SKIPNONSTOPASSEMBLIES_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 Test with filter --shadowcopy<br>
     * @pre{ }
     * @steps{ - Run the nunit3 framework tests with filter --shadowcopy.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit3 with filter --shadowcopy test")
    public void nunit3WithFilterShadowcopyTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_SHADOWCOPY_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 Test with filter --noheader<br>
     * @pre{ }
     * @steps{ - Run the nunit3 framework tests with filter --noheader.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit3 with filter --noheader test")
    public void nunit3WithFilterNoheaderTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_NOHEADER_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 Test with filter --noh<br>
     * @pre{ }
     * @steps{ - Run the nunit3 framework tests with filter --noh.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit3 with filter --noh test")
    public void nunit3WithFilterNohTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_NOH_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 Test with filter --process<br>
     * @pre{ }
     * @steps{ - Run the nunit3 framework tests with filter --process.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit3 with filter --process test")
    public void nunit3WithFilterProcessTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_PROCESS_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 Test with filter --inProcess<br>
     * @pre{ }
     * @steps{ - Run the nunit3 framework tests with filter --inProcess.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit3 with filter --inProcess test")
    public void nunit3WithFilterInProcessTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_INPROCESS_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 Test with filter --config<br>
     * @pre{ }
     * @steps{ - Run the nunit3 framework tests with filter --config.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit3 with filter --config test")
    public void nunit3WithFilterConfigTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_CONFIG_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 Test with filter --framework<br>
     * @pre{ }
     * @steps{ - Run the nunit3 framework tests with filter --framework.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit3 with filter --framework test")
    public void nunit3WithFilterFrameworkTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_FRAMEWORK_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 Test with filter --params<br>
     * @pre{ }
     * @steps{ - Run the nunit3 framework tests with filter --params.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit3 with filter --params test")
    public void nunit3WithFilterParamsTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_PARAMS_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }


    /**
     * @test NUnit3 SameOS flag test.<br>
     * @pre{ }
     * @steps{ - Run the nunit3 framework tests with /sameos flag}
     * @result{ - Build is succeeded;
     * - All assigned to the build agents have the same OS version: windows 10 or windows server 2016.}
     */
    @Test(testName = "NUnit3 SameOS Flag Test")
    public void nunit3SameOSFlagTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_TESTLEVEL_TEST + " /sameos");
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        Set<String> helpers = Parser.getHelpers(Locations.OUTPUT_LOG_FILE);
        for (String helper : helpers) {
            try {
                Assert.assertTrue(coordMonitor.getAgentOSVersion(helper).equals("10"), helper + " os version isn't equal to 10.");
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @test NUnit3 /silent test.<br>
     * @pre{ }
     * @steps{ - Run the nunit-console-master tests with the /silent flag.}
     * @result{ - Build is succeeded.
     * - There is no output in the command line.}
     */
    @Test(testName = "NUnit3 Silent Test")
    public void nunit3SilentTest() {
        String output = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_TESTLEVEL_TEST + " /silent");
        Assert.assertTrue(output.equals(""), "The test output is not suppressed.  Output: " + output);
    }

    /**
     * @test NUnit3 Path with spaces support test. <a href="http://redmine.incredibuild.local/issues/9856">Bug #9856</a>.<br>
     * @pre{ }
     * @steps{ - Run the nunit slow sample tests where the path to dll contains spaces.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Path With Spaces Test")
    public void nunit3PathWithSpacesTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_SLOW_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

      /**
     * @test NUnit3 testresult file is created with relative path.<br>
     * @pre{ }
     * @steps{ - Run the nunit-console-master failed tests with the "--result=result.xml" flag.}
     * @result{ - Build is succeeded.
     * - The result.xml file is created.}
     */
    @Test(testName = "NUnit3 Test Result Test")
    public void nunit3TestResultTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_TEST + " --result=result.xml");
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        File f = new File(System.getProperty("user.dir") + "\\result.xml");
        Assert.assertTrue(f.isFile(), "The test result file is not created");
        f.delete();
    }

    /**
     * @test NUnit3 testresult file is created with the absolute path.<br>
     * @pre{ }
     * @steps{ - Run the nunit-console-master failed tests with the "--result=c:\path\to\result.xml" flag.}
     * @result{ - Build is succeeded.
     * - The result.xml file is created.}
     */
    @Test(testName = "NUnit3 Absolute Path Test Result Test")
    public void nunit3AbsolutePathTestResultTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_RESULT_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        File f = new File(Locations.QA_ROOT + "\\nunitres.xml");
        Assert.assertTrue(f.isFile(), "The test result file is not created");
        f.delete();
    }

    /**
     * @test NUnit3 testresult is created when the tests fail. <a href="http://redmine.incredibuild.local/issues/9857">Bug #9857</a>.<br>
     * @pre{ }
     * @steps{ - Run the nunit framework with failed tests and "--result=c:\path\to\res.xml" flag.}
     * @result{ - Build failed.
     * - The result.xml file is created.}
     */
    @Test(testName = "NUnit3 Failed Test Result Test")
    public void nunit3FailedTestResultTest() {
        String result = "";
        winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_FAILED_RESULT_TEST);
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertFalse(result.equals("0"), "Build isn't failed");
        } catch (IOException e) {
            test.log(Status.WARNING, e.getMessage());
        }
        File f = new File(Locations.QA_ROOT + "\\nunitres.xml");
        Assert.assertTrue(f.isFile(), "The test result file is not created");
        f.delete();
    }

    /**
     * @test NUnit3 testresult path with spaces test. <a href="http://redmine.incredibuild.local/issues/9855">Bug #9855</a>.<br>
     * @pre{ }
     * @steps{ - Run the nunit framework slow tests where the path to the resultfile contains spaces.}
     * @result{ - Build succeeded.
     * - The result.xml file is created.}
     */
    @Test(testName = "NUnit3 Path With Spaces Test Result Test")
    public void nunit3PathWithSpacesTestResultTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_SLOW_TEST + " --result=\"" + Locations.QA_ROOT + "\\Testing\\Nunit3 TestExample\\nunitres.xml\"");
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
        File f = new File(Locations.QA_ROOT + "\\Testing\\Nunit3 TestExample\\nunitres.xml");
        Assert.assertTrue(f.isFile(), "The test result file is not created");
        f.delete();
    }


    /**
     * @test NUnit3 --where class filter test.<br>
     * @pre{ }
     * @steps{ - Run the nunit-console-master tests with the --where "class == 'TestClassName'" filter.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Class Filter Test")
    public void nunit3ClassFilterTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_WHERE_FILTER_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 SEED flag test.<br>
     * @pre{ }
     * @steps{ - Run the nunit-console-master tests with the --seed=12354 flag.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Seed Flag Test")
    public void nunit3SeedFlagTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_SEED_FLAG_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 Timeout flag test.<br>
     * @pre{ }
     * @steps{ - Run the nunit-console-master tests with the --timeout=100000 flag.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Timeout Flag Test")
    public void nunit3TimeoutFlagTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_TIMEOUT_FLAG_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 Testlist flag with absolute path test.<br>
     * @pre{ }
     * @steps{ - Run the nunit slow sample tests with the --testlist=c:\path\to\testlist.txt flag.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Absolute PathTestlist Flag Test")
    public void nunit3AbsolutePathTestlistFlagTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_SLOW_TESTLIST_FLAG_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 Testlist flag with relative path test. <a href="http://redmine.incredibuild.local/issues/9846">Bug #9846</a>.<br>
     * @pre{ }
     * @steps{ - Run the nunit slow sample tests with the --testlist=testlist.txt flag.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit3 Relative Path Testlist Flag Test")
    public void nunit3RelativePathTestlistFlagTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_SLOW_TESTLIST_FLAG_TARGETDIR_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @test NUnit3 @FILE flag test.<br>
     * @pre{ }
     * @steps{ - Run the nunit slow sample tests with the @fileWithArguments.txt flag.}
     * @result{ - Build is succeeded.}
     */
    @Test(testName = "NUnit3 File Flag Test")
    public void nunit3FileFlagTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_SLOW_FILE_FLAG_TEST);
        Assert.assertTrue(exitCode == 0, "The test execution failed with the exitcode " + exitCode);
    }

    /**
     * @todo add html parser to test
     * @test NUnit3  <a href="https://github.com/extent-framework/extentreports-dotnet-cli">Extent HTML Reporting Framework</a> support with Assembly Level test.<br>
     * @pre{ }
     * @steps{ - Run the nunit3 framework tests with /result flag;
     * - Run extent html reporting tool.}
     * @result{ - 2 file are created in the "reports" folder. }
     */
    @Test(testName = "NUnit3 Extent Report Assembly Level Test")
    public void nunit3ExtentReportAssemblyLevelTest() {
        winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_RESULT_TEST);
        int exitCode = winService.runCommandWaitForFinish("extent -i " + Locations.QA_ROOT + "\\nunitres.xml -o " + Locations.QA_ROOT + "\\reports\\");
        Assert.assertTrue(exitCode == 0, "The test reporter execution failed with the exitcode " + exitCode);
        File index = new File(Locations.QA_ROOT + "\\reports\\index.html");
        Assert.assertTrue(index.isFile(), "The test result index file is not created");
        index.delete();
        File dashboard = new File(Locations.QA_ROOT + "\\reports\\dashboard.html");
        Assert.assertTrue(dashboard.isFile(), "The test result dashboard file is not created");
        dashboard.delete();
    }

    /**
     * @todo add html parser to test
     * @test NUnit3  <a href="https://github.com/extent-framework/extentreports-dotnet-cli">Extent HTML Reporting Framework</a> support with Test Level test.<br>
     * @pre{ }
     * @steps{ - Run the nunit3 framework tests with /testlevel and --result flag;
     * - Run extent html reporting tool.}
     * @result{ - 2 file are created in the "reports" folder. }
     */
    @Test(testName = "NUnit3 Extent Report Test Level Test")
    public void nunit3ExtentReportTestLevelTest() {
        winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_RESULT_TEST + " /testlevel=5");
        int exitCode = winService.runCommandWaitForFinish("extent -i " + Locations.QA_ROOT + "\\nunitres.xml -o " + Locations.QA_ROOT + "\\reports\\");
        Assert.assertTrue(exitCode == 0, "The test reporter execution failed with the exitcode " + exitCode);
        File index = new File(Locations.QA_ROOT + "\\reports\\index.html");
        Assert.assertTrue(index.isFile(), "The test result index file is not created");
        index.delete();
        File dashboard = new File(Locations.QA_ROOT + "\\reports\\dashboard.html");
        Assert.assertTrue(dashboard.isFile(), "The test result dashboard file is not created");
        dashboard.delete();
    }
    //Negative flow
    /**
     * @test NUnit3 Negative flow Not supported flag /nocolor test.<br>
     * @pre{ }
     * @steps{ - Run the nunit framework tests with the /nocolor flag.}
     * @result{ - Build fails with return code -4.
     */
    @Test(testName = "NUnit3 Negative Flow Filter /nocolor test")
    public void nunit3NegativeFlowFilterNoColorTest() {
        int exitCode = winService.runCommandWaitForFinish(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.NUNIT3_CONSOLE_1DLL_TEST + " --nocolor");
        Assert.assertTrue(exitCode == -4, "The test execution failed with the exitcode " + exitCode);
    }
}
