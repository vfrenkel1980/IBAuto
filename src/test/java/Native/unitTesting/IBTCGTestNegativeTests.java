package Native.unitTesting;


import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.UnitTestingTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.StaticDataProvider.IbLocations;
import frameworkInfra.utils.StaticDataProvider.Locations;
import frameworkInfra.utils.StaticDataProvider.ProjectsCommands;
import frameworkInfra.utils.SystemActions;
import frameworkInfra.utils.parsers.Parser;
import ibInfra.ibExecs.IIBCoordMonitor;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;

/**
 * @brief Unit tests execution with IbTestConsole
 * <p>
 * - GTest
 * @details Requires Unit Tests license solution
 */
public class IBTCGTestNegativeTests extends UnitTestingTestBase {

    private final String ERROR_MESSAGE_INTERNAL_ERROR = "[error]: Internal error: Unsupported gtest option '%s'";

    private IIBCoordMonitor coordMonitor = new IIBCoordMonitor();

    /**
     * @test gTest General Error: Invalid Unitest Framework
     * @pre{ }
     * @steps {- Run command from IBTestConsole with flag: /test=unix }
     * @result { - Command fails with error message: Exception message group 1}
     */
    @Test(testName ="gTest General Error Invalid Unitest Framework")
    public void gtestGeneralErrorInvalidUnitestFramwork(){
        String expectedErrorMessage=StaticDataProvider.ErrorMessages.GeneralErrors.INVALID_UNITEST_FRAMEWORK_UNIX;
        String gTestErrors = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.INCORRECTGTESTCOMMANDS.GTEST_CPPSORTER_TEST_TEST_FLAG_EQUAL_UNIX);
        Assert.assertTrue(gTestErrors.contains(expectedErrorMessage), "Failed to find gTest error in output");

    }
    /**
     * @test gtest General Error: Invalid Missing Unitest Framework And Invalid Executable
     * @pre{ }
     * @steps {- Run command from IBTestConsole: InputFiles\GoodInputFile.txt}
     * @result { - Command fails with error message: Exception message group 1:
     *  The requested framework cannot be detected.
     *  Use the /test=<framework> option, to specify the framework you want to execute.}
     */
    @Test(testName ="gtest General Error Invalid Missing Unitest Framework And Invalid Executable")
    public void gtestGeneralErrorInvalidMissingUnitestFrameworkAndInvalidExecutable(){
        String expectedErrorMessage=StaticDataProvider.ErrorMessages.GeneralErrors.INVALID_FRAMEWORK;
        String gTestErrors = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_EXECUTABLES_TXT_FILE);
        Assert.assertTrue(gTestErrors.contains(expectedErrorMessage), "Failed to find gTest error in output");
    }
    /**
     * @test gtest Internal Error: Invalid Output Filename
     * @pre{ }
     * @steps {- Run command from IBTestConsole with invalid file}
     * @result {- Command fails with error message: exception message group 2}
     */
    @Test(testName ="gtest Internal Error Invalid Output Filename")
    public void gtestGeneralInternalErrorInvalidOutputFilename(){
        String expectedErrorMessage=StaticDataProvider.ErrorMessages.GeneralErrors.INVALID_OUTPUT_FILE;
        String gTestErrors = winService.runCommandGetOutput(IbLocations.IBCONSOLE + ProjectsCommands.TESTING_ROBIN.INCORRECTGTESTCOMMANDS.GTEST_INVALID_OUTPUT_FILE);
        Assert.assertTrue(gTestErrors.contains(expectedErrorMessage), "Failed to find gTest error in output");
    }
    /**
     * @test nUnit3 General Error: Invalid Value For At File Flag
     * @pre{ }
     * @steps {- Run command from IBTestConsole with NUnit.Tests1.dll @file=}
     * @result {- Command fails with error message: Invalid value for the 'file' option.}
     */
    @Test(testName ="nUnit3 General Error Invalid Value For At File Flag")
    public void nUnitGeneralErrorInvalidValueForAtFileFlag(){
        String expectedErrorMessage=StaticDataProvider.ErrorMessages.GeneralErrors.INVALID_VALUE_FILE_FLAG;
        String gTestErrors = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.INCORRECTGTESTCOMMANDS.NUNIT_INVALID_VALUE_FLAG);
        Assert.assertTrue(gTestErrors.contains(expectedErrorMessage), "Failed to find gTest error in output");
    }
    /**
     * @test gTest General Error: Sameos And MinMaxWinVer Flags Used Together
     * @pre{ }
     * @steps {- Run command from IBTestConsole with flag: /MinWinVer /MaxWinVer}
     * @result {- Command fails with error message: exception message group 1: The sameos option cannot be used together with the MinWinVer or MaxWinVer options.}
     */
    @Test(testName ="gTest General Error Sameos And MinMaxWinVer Flags Used Together")
    public void gTestGeneralErrorSameosAndMinMaxWinVerFlagsUsedTogether(){
        String expectedErrorMessage=StaticDataProvider.ErrorMessages.GeneralErrors.SAMEOS_OPTION;
        String gTestErrors = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.INCORRECTGTESTCOMMANDS.GTEST_SAMEOS_OPTIONS);
        Assert.assertTrue(gTestErrors.contains(expectedErrorMessage), "Failed to find gTest error in output");
    }
    /**
     * @test gTest General Error: Missing Executable File
     * @pre{ }
     * @steps {- Run command from IBTestConsole with flag: testlevel=auto openmonitor}
     * @result {- Command fails with error message: exception message group 1: The execution command is incomplete}
     */
    @Test(testName ="gTest General Error Missing Executable File")
    public void gTestGeneralErrorMissingExecutableFile(){
        String expectedErrorMessage=StaticDataProvider.ErrorMessages.GeneralErrors.EXECUTION_COMMAND_INCOMPLETE;
        String gTestErrors = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.INCORRECTGTESTCOMMANDS.GTEST_MISSING_EXECUTABLE_FILE);
        Assert.assertTrue(gTestErrors.contains(expectedErrorMessage), "Failed to find gTest error in output");
    }

    /**
     * @test gTest General Error: Test level Flag Invalid String
     * @pre{ }
     * @steps {- Run command from IBTestConsole with flag: testlevel=info}
     * @result {- Command fails with error message: exception message group 1: Invalid optionName, option value"}
     */
    @Test(testName ="gTest General Error Test level Flag Invalid String")
    public void gTestGeneralErrorTestlevelFlagInvalidString(){
        String expectedErrorMessage=StaticDataProvider.ErrorMessages.GeneralErrors.INVALID_STRING;
        String gTestErrors = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.INCORRECTGTESTCOMMANDS.GTEST_INVALID_STRING);
        Assert.assertTrue(gTestErrors.contains(expectedErrorMessage), "Failed to find gTest error in output");
    }
    /**
     * @test gTest General Error: Test level Flag Invalid String
     * @pre{ }
     * @steps {- Run command from IBTestConsole with flag: testlevel=info}
     * @result { - Command fails with error message: exception message group 1: The value of the testlevel option is either invalid or out of range.}
     */
    @Test(testName ="gTest General Error Test level Flag Invalid String")
    public void gTestGeneralErrorTestlevelFlagValueIsOutOfRange(){
        String expectedErrorMessage=StaticDataProvider.ErrorMessages.GeneralErrors.INVALID_STRING;
        String gTestErrors = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.INCORRECTGTESTCOMMANDS.GTEST_INVALID_STRING);
        Assert.assertTrue(gTestErrors.contains(expectedErrorMessage), "Failed to find gTest error in output");
    }

    /**
     * @test gTest General Error: Threshold Testlevel Flag Value Is Out Of Range
     * @pre{ }
     * @steps {- Run command from IBTestConsole with flag: thresholdTestlevel=111}
     * @result {- Command fails with error message: exception message group 1: Internal}
     */
    @Test(testName ="gTest General Error Threshold Test level Flag Invalid String")
    public void gTestGeneralErrorThresholdTestlevelFlagValueIsOutOfRange(){
        String expectedErrorMessage=StaticDataProvider.ErrorMessages.GeneralErrors.INVALID_TRESHOLD_STRING;
        String gTestErrors = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.INCORRECTGTESTCOMMANDS.GTEST_INVALID_TRASHOLD_STRING);
        Assert.assertTrue(gTestErrors.contains(expectedErrorMessage), "Failed to find gTest error in output");
    }

    /**
     * @test gTest General Error: Flag Value For Option
     * @pre{ }
     * @steps {- Run command from IBTestConsole with flag: test=ATest}
     * @result {- Command fails with error message: Invalid value 'ATest' for the 'test' option. }
     */
    @Test(testName ="gTest General Error Flag Value For Option")
    public void gTestGeneralErrorFlagValueForOption(){
        String expectedErrorMessage=StaticDataProvider.ErrorMessages.GeneralErrors.INVALID_VALUE_FOR_OPTION;
        String gTestErrors = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.INCORRECTGTESTCOMMANDS.GTEST_INALID_VALUE_FOR_OPTION);
        Assert.assertTrue(gTestErrors.contains(expectedErrorMessage), "Failed to find gTest error in output");
    }
    /**
     * @test gTest Generall Error: Out Flag With Silent Not Used
     * @pre{ }
     * @steps {- Run command from IBTestConsole with flag: out-sarit.xml silent}
     * @result {- Command fails with error message: exception message group 1: Invalid value optionValue for the optionName option. }
     */
    @Test(testName ="gTest General Error Out Flag With Silent Not Used")
    public void gTestGeneralErrorOutFlagWithSilentNotUsed(){
        String expectedErrorMessage=StaticDataProvider.ErrorMessages.GeneralErrors.OUT_OPTION_FLAG;
        String gTestErrors = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.INCORRECTGTESTCOMMANDS.GTETS_OUT_OPTION);
        Assert.assertTrue(gTestErrors.contains(expectedErrorMessage), "Failed to find gTest error in output");
    }
    /**
     * @test gTest General Error: Unittest Framework Empty
     * @pre{ }
     * @steps {- Run command from IBTestConsole framework is empty}
     * @result { - Command fails with error message: exception message group 2: Internal}
     */
    @Test(testName ="gTest General Error Unittest Framework Empty")
    public void gTestGeneralErrorUnittestFrameworkEmpty(){
        String expectedErrorMessage=StaticDataProvider.ErrorMessages.GeneralErrors.FRAMEWORK_EMPTY_ERROR;
        String gTestErrors = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_FRAMEWORK_EMPTY);
        Assert.assertTrue(gTestErrors.contains(expectedErrorMessage), "Failed to find gTest error in output");
    }
    /**
     * @test gTest General Error: Execute Task
     * @pre{ }
     * @steps {- Run command from IBConsole: command/=IbConsole.exe}
     * @result {- Command fails with error message: exception  message group 2: Internal}
     */
    @Test(testName ="gTest General Error Execute Task")
    public void gTestGeneralErrorExecuteTask(){
        String expectedErrorMessage=StaticDataProvider.ErrorMessages.GeneralErrors.IBCONSOLE_TASK_ERROR;
        String gTestErrors = winService.runCommandGetOutput(IbLocations.IBCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_CPPSORTER_TEST);
        Assert.assertTrue(gTestErrors.contains(expectedErrorMessage), "Failed to find gTest error in output");
    }

    /**
     * @test gTest General Error: Invalid Value Of MinMaxWin VerFlags
     * @pre{ }
     * @steps {- Run command from IBTestConsole: MaxWinVer=12}
     * @result {- Command fails with error message: exception message group 2: Internal}
     */
    @Test(testName ="gTest General Error Invalid Value Of MinMaxWin VerFlags")
    public void gTestGeneralErrorInvalidValueOfMinMaxWinVerFlags(){
        String expectedErrorMessage=StaticDataProvider.ErrorMessages.GeneralErrors.INVALID_VALUE_MINMAXWINVER;
        String gTestErrors = winService.runCommandGetOutput(IbLocations.IBTESTCONSOLE + ProjectsCommands.TESTING_ROBIN.GTEST_INVALID_VALUE_MINMAXWINVER);
        Assert.assertTrue(gTestErrors.contains(expectedErrorMessage), "Failed to find gTest error in output");
    }
}//end of class

