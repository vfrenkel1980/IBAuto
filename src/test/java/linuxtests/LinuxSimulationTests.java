package linuxtests;

import frameworkInfra.testbases.LinuxSimTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LinuxSimulationTests extends LinuxSimTestBase {
//---------------------------------------------------------------------------------------------------------------------------------------------
    @Test(testName = "Linux threads" )
    public void runIbConsoleAndVerifyResult() throws InterruptedException {
//        threads thread1 = new threads();
//        thread1.start();
//        threads thread2 = new threads();
//        thread2.start();



//       int exitCode = runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_APACHE_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" + StaticDataProvider.LinuxSimulation.BUILD,
//               StaticDataProvider.LinuxMachines.TEST_MACHINE);
//
//        Assert.assertTrue(exitCode  == 0 || exitCode == 2, "build failed");

/*        LinuxCL runCommand = new LinuxCL();

        String success = "//*[@id=\"build_history_contentholder_table\"]/tbody/tr[1]/td[2]/div/div[2]";
        String navigateCommand = "cd /disk2/projects/httpd-2.4.18";
        String executeCommand = "ib_console make -j32";
        String executeClean = "make clean";
        runCommand.linuxRunSSHCommand(navigateCommand + "; " + executeClean + "& "+ executeCommand + "& "+ executeClean + "& "+ executeCommand, "192.168.11.82");


        System.setProperty("webdriver.chrome.driver","C:\\Users\\Mark\\Downloads\\AutomationPOCtools\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        String baseUrl = "http://192.168.11.82:8080/incredibuild/";

        driver.get(baseUrl);
        Thread.sleep(10000);
        String result = driver.findElement(By.xpath(success)).getText();
        Assert.assertEquals(result, "Completed", "Result did not match expected");
        driver.close();*/
    }
//---------------------------------------------------------------------------------------------------------------------------------------------

    //@Test(testName = "MyName", invocationCount = 3)

    @Test(testName = "Sim Kernel")
    public void SimTestKernel(){
        int exitCode = runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_KERNEL_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD,"","Kernel", "", "32"), StaticDataProvider.LinuxMachines.VM_SIM_1A);


        Assert.assertEquals(1, 1, ":(");

        runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_KERNEL_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", StaticDataProvider.LinuxMachines.VM_SIM_1A);
    }

    @Test(testName = "Sim Apache")
    public void SimTestApache(){
        int exitCode = runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_APACHE_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD,"","Apache", "", "32"), StaticDataProvider.LinuxMachines.VM_SIM_1A);

        Assert.assertEquals(1, 1, ":(");

        runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_APACHE_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", StaticDataProvider.LinuxMachines.VM_SIM_1A);
    }

    @Test(testName = "Sim Samba")
    public void SimTestSamba(){
        int exitCode = runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_SAMBA_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD,"","Samba", "env JOBS=32", "32"), StaticDataProvider.LinuxMachines.VM_SIM_1A);


        Assert.assertEquals(1, 1, ":(");

        runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_SAMBA_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", StaticDataProvider.LinuxMachines.VM_SIM_1A);
    }

    @Test(testName = "Sim Cpp")
    public void SimTestCpp(){
        int exitCode = runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_CPP_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD,"","Cpp", "", "32"), StaticDataProvider.LinuxMachines.VM_SIM_1A);


        Assert.assertEquals(1, 1, ":(");

        runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_CPP_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", StaticDataProvider.LinuxMachines.VM_SIM_1A);
    }

    @Test(testName = "Sim MySQL")
    public void SimTestMySQL(){
        int exitCode = runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_MYSQL_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD,"","MySQL", "", "32"), StaticDataProvider.LinuxMachines.VM_SIM_1A);


        Assert.assertEquals(1, 1, ":(");

        runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_MYSQL_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", StaticDataProvider.LinuxMachines.VM_SIM_1A);
    }

    @Test(testName = "Sim Boost")
    public void SimTestBoost() {
        int exitCode = runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_BOOST_DIR + ";" + StaticDataProvider.LinuxSimulation.B2_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.B2_BUILD, "", "Boost", "", "32"), StaticDataProvider.LinuxMachines.VM_SIM_1A);


        Assert.assertEquals(1, 1, ":(");

        runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_BOOST_DIR + ";" + StaticDataProvider.LinuxSimulation.B2_CLEAN + ";", StaticDataProvider.LinuxMachines.VM_SIM_1A);
    }

    @Test(testName = "Sim Cmake")
    public void SimTestCmake(){
        int exitCode = runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_CMAKE_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD,"","Cmake", "", "32"), StaticDataProvider.LinuxMachines.VM_SIM_1A);


        Assert.assertEquals(1, 1, ":(");

        runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_CMAKE_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", StaticDataProvider.LinuxMachines.VM_SIM_1A);
    }

    @Test(testName = "Sim GDB")
    public void SimTestGDB(){
        int exitCode = runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_GDB_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD,"","GDB", "", "32"), StaticDataProvider.LinuxMachines.VM_SIM_1A);


        Assert.assertEquals(1, 1, ":(");

        runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_GDB_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", StaticDataProvider.LinuxMachines.VM_SIM_1A);
    }

    @Test(testName = "Sim Git")
    public void SimTestGit(){
        int exitCode = runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_GIT_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD,"","Git", "", "32"), StaticDataProvider.LinuxMachines.VM_SIM_1A);


        Assert.assertEquals(1, 1, ":(");

        runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_GIT_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", StaticDataProvider.LinuxMachines.VM_SIM_1A);
    }

    @Test(testName = "Sim QT")
    public void SimTestQT(){
        int exitCode = runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_QT_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD,"","QT", "", "32"), StaticDataProvider.LinuxMachines.VM_SIM_1A);


        Assert.assertEquals(1, 1, ":(");

        runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_QT_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";", StaticDataProvider.LinuxMachines.VM_SIM_1A);
    }

    @Test(testName = "Sim MongoDB")
    public void SimTestMongoDB(){
        int exitCode = runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_MONGODB_DIR + ";" + StaticDataProvider.LinuxSimulation.SCONS_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.SCONS_BUILD,"","MongoDB", "", "32"), StaticDataProvider.LinuxMachines.VM_SIM_1A);


        Assert.assertEquals(1, 1, ":(");

        runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_MONGODB_DIR + ";" + StaticDataProvider.LinuxSimulation.SCONS_CLEAN + ";", StaticDataProvider.LinuxMachines.VM_SIM_1A);
    }

    @Test(testName = "Sim Chromium")
    public void SimTestChromium(){
        int exitCode = runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_CHROMIUM_DIR + ";" + StaticDataProvider.LinuxSimulation.NINJA_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.NINJA_BUILD,"","Chromium", "env PATH=$PATH:/disk2/projects/chromium/depot_tools", "32"), StaticDataProvider.LinuxMachines.VM_SIM_1A);


        Assert.assertEquals(1, 1, ":(");

        runCommand.linuxRunSSHCommand(StaticDataProvider.LinuxSimulation.CD_CHROMIUM_DIR + ";" + StaticDataProvider.LinuxSimulation.NINJA_CLEAN + ";", StaticDataProvider.LinuxMachines.VM_SIM_1A);
    }

}


// public static final String MAKE_BUILD = "ib_console %s -c %s %s make -j%s";