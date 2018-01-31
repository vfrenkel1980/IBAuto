package linuxtests;

import frameworkInfra.testbases.LinuxSimTestBase;
import frameworkInfra.utils.StaticDataProvider;
import ibInfra.linuxcl.LinuxMultiThreaded;
import org.testng.annotations.Test;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LinuxMultiTests extends LinuxSimTestBase {

/*    @Test(testName = "Multi")
    public static void Multi() throws InterruptedException {

        ExecutorService execService = Executors.newFixedThreadPool(2);

        execService.execute(new LinuxMultiThreaded(StaticDataProvider.LinuxSimulation.CD_APACHE_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                StaticDataProvider.LinuxSimulation.BUILD, StaticDataProvider.LinuxMachines.TEST_MACHINE));
        execService.execute(new LinuxMultiThreaded(StaticDataProvider.LinuxSimulation.CD_LINUX_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                StaticDataProvider.LinuxSimulation.BUILD, StaticDataProvider.LinuxMachines.TEST_MACHINE));

        execService.shutdown();
        execService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
    }*/

/*    public static void main(String[] args) {
        ExecutorService execService = Executors.newFixedThreadPool(2);

        execService.execute(new LinuxMultiThreaded(StaticDataProvider.LinuxSimulation.CD_APACHE_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                StaticDataProvider.LinuxSimulation.BUILD, StaticDataProvider.LinuxMachines.TEST_MACHINE));
        execService.execute(new LinuxMultiThreaded(StaticDataProvider.LinuxSimulation.CD_LINUX_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                StaticDataProvider.LinuxSimulation.BUILD, StaticDataProvider.LinuxMachines.TEST_MACHINE));

        execService.shutdown();

        TestNG testng = new TestNG();
        List<String> suites = Lists.newArrayList();
        suites.add("X:\\QA Automation\\IBAuto\\src\\test\\resources\\LinuxMulti.xml");
        testng.setTestSuites(suites);
        testng.run();
    }*/
}


