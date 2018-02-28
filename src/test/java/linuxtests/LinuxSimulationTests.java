package linuxtests;

import frameworkInfra.testbases.LinuxSimTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import static frameworkInfra.utils.StaticDataProvider.*;

public class LinuxSimulationTests extends LinuxSimTestBase {
//---------------------------------------------------------------------------------------------------------------------------------------------
/*    @Test(testName = "Linux threads" )
    public void runIbConsoleAndVerifyResult() throws InterruptedException {
        threads thread1 = new threads();
        thread1.start();
        threads thread2 = new threads();
        thread2.start();



       int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_APACHE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" + LinuxSimulation.BUILD,
               LinuxMachines.TEST_MACHINE);

        Assert.assertTrue(exitCode  == 0 || exitCode == 2, "build failed");

        LinuxService linuxService = new LinuxService();

        String success = "//*[@id=\"build_history_contentholder_table\"]/tbody/tr[1]/td[2]/div/div[2]";
        String navigateCommand = "cd /disk2/projects/httpd-2.4.18";
        String executeCommand = "ib_console make -j32";
        String executeClean = "make clean";
        linuxService.linuxRunSSHCommand(navigateCommand + "; " + executeClean + "& "+ executeCommand + "& "+ executeClean + "& "+ executeCommand, "192.168.11.82");


        System.setProperty("webdriver.chrome.driver","C:\\Users\\Mark\\Downloads\\AutomationPOCtools\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        String baseUrl = "http://192.168.11.82:8080/incredibuild/";

        driver.get(baseUrl);
        Thread.sleep(10000);
        String result = driver.findElement(By.xpath(success)).getText();
        Assert.assertEquals(result, "Completed", "Result did not match expected");
        driver.close();
    }*/
//---------------------------------------------------------------------------------------------------------------------------------------------


    @Test(testName = "Sim Kernel")
    public void SimTestKernel(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_KERNEL_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 --f","Kernel", "", "32"), ipList.get(1));


        Assert.assertEquals(exitCode, 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_KERNEL_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(1));
    }

    @Test(testName = "Sim Apache")
    public void SimTestApache(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_APACHE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 --f","Apache", "", "32"), ipList.get(1));

        Assert.assertEquals(exitCode, 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_APACHE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(1));
    }

    @Test(testName = "Sim Samba")
    public void SimTestSamba(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_SAMBA_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 --f","Samba", "env JOBS=32", "32"), ipList.get(1));


        Assert.assertEquals(exitCode, 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_SAMBA_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(1));
    }

    @Test(testName = "Sim Cpp")
    public void SimTestCpp(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_CPP_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 --f","Cpp", "", "32"), ipList.get(1));


        Assert.assertEquals(exitCode, 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_CPP_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(1));
    }

    @Test(testName = "Sim MySQL")
    public void SimTestMySQL(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_MYSQL_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 --f","MySQL", "", "32"), ipList.get(1));


        Assert.assertEquals(exitCode, 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_MYSQL_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";",ipList.get(1));
    }

    @Test(testName = "Sim Boost")
    public void SimTestBoost() {
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_BOOST_DIR + ";" + LinuxSimulation.B2_CLEAN + ";" +
                String.format(LinuxSimulation.B2_BUILD, "--ib-crash -d1 --f", "Boost", "", "32"), ipList.get(1));


        Assert.assertEquals(exitCode, 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_BOOST_DIR + ";" + LinuxSimulation.B2_CLEAN + ";", ipList.get(1));
    }

    @Test(testName = "Sim Cmake")
    public void SimTestCmake(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_CMAKE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 --f","Cmake", "", "32"), ipList.get(1));


        Assert.assertEquals(exitCode, 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_CMAKE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(1));
    }

    @Test(testName = "Sim GDB")
    public void SimTestGDB(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_GDB_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 --f","GDB", "", "32"), ipList.get(1));


        Assert.assertEquals(exitCode, 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_GDB_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(1));
    }

    @Test(testName = "Sim Git")
    public void SimTestGit(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_GIT_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 --f","Git", "", "32"), ipList.get(1));


        Assert.assertEquals(exitCode, 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_GIT_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(1));
    }

    @Test(testName = "Sim QT")
    public void SimTestQT(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_QT_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 --f","QT", "", "32"), ipList.get(1));


        Assert.assertEquals(exitCode, 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_QT_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(1));
    }

    @Test(testName = "Sim MongoDB")
    public void SimTestMongoDB(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_MONGODB_DIR + ";" + LinuxSimulation.SCONS_CLEAN + ";" +
                String.format(LinuxSimulation.SCONS_BUILD,"--ib-crash -d1 --f","MongoDB", "", "32"), ipList.get(1));


        Assert.assertEquals(exitCode, 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_MONGODB_DIR + ";" + LinuxSimulation.SCONS_CLEAN + ";", ipList.get(1));
    }

    @Test(testName = "Sim Chromium")
    public void SimTestChromium(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_CHROMIUM_DIR + ";" + LinuxSimulation.NINJA_CLEAN + ";" +
                String.format(LinuxSimulation.NINJA_BUILD,"--ib-crash -d1 --f","Chromium", "env PATH=$PATH:/disk2/projects/chromium/depot_tools", "32"), ipList.get(1));


        Assert.assertEquals(exitCode, 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_CHROMIUM_DIR + ";" + LinuxSimulation.NINJA_CLEAN + ";", ipList.get(1));
    }

}
