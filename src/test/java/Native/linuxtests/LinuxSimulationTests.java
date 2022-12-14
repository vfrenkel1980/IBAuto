package Native.linuxtests;

import frameworkInfra.testbases.LinuxSimTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import static frameworkInfra.utils.StaticDataProvider.*;

public class LinuxSimulationTests extends LinuxSimTestBase {

    @Test(testName = "Sim Kernel")
    public void SimTestKernel(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_KERNEL_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,LINUXCLFLAGS,"Kernel", "", "32"), ipList.get(simClassType.ordinal()));
//                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash","Kernel", "", "32"), ipList.get(simClassType.ordinal()));

        Assert.assertTrue(exitCode <= 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_KERNEL_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(simClassType.ordinal()));
    }

    @Test(testName = "Sim Apache")
    public void SimTestApache(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_APACHE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
           String.format(LinuxSimulation.MAKE_BUILD,LINUXCLFLAGS,"Apache", "", "32"), ipList.get(simClassType.ordinal()));
//             String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash","Apache", "", "32"), ipList.get(simClassType.ordinal()));

        Assert.assertTrue(exitCode <= 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_APACHE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(simClassType.ordinal()));
    }

    @Test(testName = "Sim Samba")
    public void SimTestSamba(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_SAMBA_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,LINUXCLFLAGS,"Samba", "env JOBS=32", "32"), ipList.get(simClassType.ordinal()));
//                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash","Samba", "env JOBS=32", "32"), ipList.get(simClassType.ordinal()));

        Assert.assertTrue(exitCode <= 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_SAMBA_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(simClassType.ordinal()));
    }

    @Test(testName = "Sim Cpp")
    public void SimTestCpp(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_CPP_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,LINUXCLFLAGS,"Cpp", "", "32"), ipList.get(simClassType.ordinal()));
//                 String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash","Cpp", "", "32"), ipList.get(simClassType.ordinal()));

        Assert.assertTrue(exitCode <= 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_CPP_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(simClassType.ordinal()));
    }

    @Test(testName = "Sim MySQL")
    public void SimTestMySQL(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_MYSQL_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,LINUXCLFLAGS,"MySQL", "", "32"), ipList.get(simClassType.ordinal()));
//                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash","MySQL", "", "32"), ipList.get(simClassType.ordinal()));

        Assert.assertTrue(exitCode <= 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_MYSQL_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";",ipList.get(simClassType.ordinal()));
    }

    @Test(testName = "Sim Boost")
    public void SimTestBoost() {
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_BOOST_DIR + ";" + LinuxSimulation.B2_CLEAN + ";" +
                String.format(LinuxSimulation.B2_BUILD, LINUXCLFLAGS, "Boost", "", "32"), ipList.get(simClassType.ordinal()));
//                String.format(LinuxSimulation.B2_BUILD, "--ib-crash", "Boost", "", "32"), ipList.get(simClassType.ordinal()));

        Assert.assertTrue(exitCode <= 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_BOOST_DIR + ";" + LinuxSimulation.B2_CLEAN + ";", ipList.get(simClassType.ordinal()));
    }

    @Test(testName = "Sim Cmake")
    public void SimTestCmake(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_CMAKE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,LINUXCLFLAGS,"Cmake", "", "32"), ipList.get(simClassType.ordinal()));
//                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash","Cmake", "", "32"), ipList.get(simClassType.ordinal()));

        Assert.assertTrue(exitCode <= 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_CMAKE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(simClassType.ordinal()));
    }

    @Test(testName = "Sim GDB")
    public void SimTestGDB(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_GDB_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,LINUXCLFLAGS,"GDB", "", "32"), ipList.get(simClassType.ordinal()));
//                 String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash","GDB", "", "32"), ipList.get(simClassType.ordinal()));

        Assert.assertTrue(exitCode <= 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_GDB_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(simClassType.ordinal()));
    }

    @Test(testName = "Sim Git")
    public void SimTestGit(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_GIT_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,LINUXCLFLAGS,"Git", "", "32"), ipList.get(simClassType.ordinal()));
//                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash","Git", "", "32"), ipList.get(simClassType.ordinal()));

        Assert.assertTrue(exitCode <= 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_GIT_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(simClassType.ordinal()));
    }

    @Test(testName = "Sim QT")
    public void SimTestQT(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_QT_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,LINUXCLFLAGS,"QT", "", "32"), ipList.get(simClassType.ordinal()));
//                  String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash","QT", "", "32"), ipList.get(simClassType.ordinal()));

        Assert.assertTrue(exitCode <= 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_QT_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(simClassType.ordinal()));
    }

    @Test(testName = "Sim MongoDB")
    public void SimTestMongoDB(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_MONGODB_DIR + ";" + LinuxSimulation.SCONS_CLEAN + ";" +
                String.format(LinuxSimulation.SCONS_BUILD,LINUXCLFLAGS,"MongoDB", "", "32"), ipList.get(simClassType.ordinal()));
//                String.format(LinuxSimulation.SCONS_BUILD,"--ib-crash","MongoDB", "", "32"), ipList.get(simClassType.ordinal()));

        Assert.assertTrue(exitCode <= 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_MONGODB_DIR + ";" + LinuxSimulation.SCONS_CLEAN + ";", ipList.get(simClassType.ordinal()));
    }

    @Test(testName = "Sim Chromium")
    public void SimTestChromium(){
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_CHROMIUM_DIR + ";" + LinuxSimulation.NINJA_CLEAN + ";" +
                String.format(LinuxSimulation.NINJA_BUILD,LINUXCLFLAGS,"Chromium", "env PATH=$PATH:/disk2/projects/chromium/depot_tools", "32"), ipList.get(simClassType.ordinal()));
//                String.format(LinuxSimulation.NINJA_BUILD,"--ib-crash","Chromium", "env PATH=$PATH:/disk2/projects/chromium/depot_tools", "32"), ipList.get(simClassType.ordinal()));

        Assert.assertTrue(exitCode <= 0, "Test failed with Exit code " + exitCode);

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_CHROMIUM_DIR + ";" + LinuxSimulation.NINJA_CLEAN + ";", ipList.get(simClassType.ordinal()));
    }
}
