package linuxtests;

import frameworkInfra.testbases.LinuxSimTestBase;
import ibInfra.linuxcl.LinuxMultiThreaded;
import org.testng.annotations.Test;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static frameworkInfra.utils.StaticDataProvider.*;

public class LinuxMultiTests extends LinuxSimTestBase {

    @Test(testName = "Multi")
    public static void Multi() throws InterruptedException {

        ExecutorService execService = Executors.newFixedThreadPool(4);

            execService.execute(new LinuxMultiThreaded(LinuxSimulation.CD_APACHE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                    String.format(LinuxSimulation.MAKE_BUILD,"","Apache", "", "32"), LinuxMachines.VM_SIM_1A, 2));


            execService.execute(new LinuxMultiThreaded(LinuxSimulation.CD_GIT_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                    String.format(LinuxSimulation.MAKE_BUILD,"","Git", "", "32"), LinuxMachines.VM_SIM_1A, 2));


        execService.shutdown();
        execService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
    }

/*    public static void main(String[] args) {
        ExecutorService execService = Executors.newFixedThreadPool(2);

        execService.execute(new LinuxMultiThreaded(LinuxSimulation.CD_APACHE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                LinuxSimulation.BUILD, LinuxMachines.TEST_MACHINE));
        execService.execute(new LinuxMultiThreaded(LinuxSimulation.CD_LINUX_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                LinuxSimulation.BUILD, LinuxMachines.TEST_MACHINE));

        execService.shutdown();

        TestNG testng = new TestNG();
        List<String> suites = Lists.newArrayList();
        suites.add("X:\\QA Automation\\IBAuto\\src\\test\\resources\\LinuxMulti.xml");
        testng.setTestSuites(suites);
        testng.run();
    }*/
}


