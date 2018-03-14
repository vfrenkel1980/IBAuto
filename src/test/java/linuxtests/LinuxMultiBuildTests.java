package linuxtests;

import frameworkInfra.testbases.LinuxMultiBuildTestBase;
import frameworkInfra.testbases.LinuxSimTestBase;
import ibInfra.linuxcl.LinuxMultiThreaded;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static frameworkInfra.utils.StaticDataProvider.*;

public class LinuxMultiBuildTests extends LinuxMultiBuildTestBase {

    @Test(testName = "MultiBuild")
    public static void multiBuild() throws InterruptedException {

        ExecutorService execService = Executors.newFixedThreadPool(4);

        execService.execute(new LinuxMultiThreaded(LinuxSimulation.CD_KERNEL_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 ","Kernel", "", "32"), ipList.get(1), 300));

        execService.execute(new LinuxMultiThreaded(LinuxSimulation.CD_APACHE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 ","Apache", "", "32"), ipList.get(1), 800));

        execService.execute(new LinuxMultiThreaded(LinuxSimulation.CD_GIT_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 ","Git", "", "32"), ipList.get(1), 800));

        execService.execute(new LinuxMultiThreaded(LinuxSimulation.CD_CMAKE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 ","Cmake", "", "32"), ipList.get(1), 400));


        execService.shutdown();
        execService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
    }

//    int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_APACHE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
//            String.format(LinuxSimulation.MAKE_BUILD,"","Apache", "", "32"), ipList.get(1));
//
//        Assert.assertEquals(0, exitCode, "Test failed with Exit code " + exitCode);
//
//        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_APACHE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(1));


/*    public static void main(String[] args) {
        ExecutorService execService = Executors.newFixedThreadPool(2);

        execService.execute(new LinuxMultiThreaded(LinuxSimulation.CD_APACHE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                LinuxSimulation.BUILD, LinuxMachines.TEST_MACHINE));
        execService.execute(new LinuxMultiThreaded(LinuxSimulation.CD_LINUX_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                LinuxSimulation.BUILD, LinuxMachines.TEST_MACHINE));

        execService.shutdown();

        TestNG testng = new TestNG();
        List<String> suites = Lists.newArrayList();
        suites.add("X:\\QA Automation\\IBAuto\\src\\test\\resources\\LinuxMultiBuild.xml");
        testng.setTestSuites(suites);
        testng.run();
    }*/
}


