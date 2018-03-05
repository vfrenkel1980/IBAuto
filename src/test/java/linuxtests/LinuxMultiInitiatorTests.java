package linuxtests;

import frameworkInfra.testbases.LinuxMultiBuildTestBase;
import frameworkInfra.testbases.LinuxMultiInitiatorsTestBase;
import frameworkInfra.utils.StaticDataProvider;
import ibInfra.linuxcl.LinuxMultiThreaded;
import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LinuxMultiInitiatorTests  extends LinuxMultiInitiatorsTestBase {

    @Test(testName = "MultiInitiator")
    public static void multiInitiators() throws InterruptedException {

        ExecutorService execService = Executors.newFixedThreadPool(4);

        execService.execute(new LinuxMultiThreaded(StaticDataProvider.LinuxSimulation.CD_KERNEL_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD,"--ib-crash -d2 --f","Kernel", "", "32"), ipList.get(1), 300));

        execService.execute(new LinuxMultiThreaded(StaticDataProvider.LinuxSimulation.CD_GPSD_DIR + ";" + StaticDataProvider.LinuxSimulation.SCONS_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.SCONS_BUILD,"--ib-crash -d2 --f","GPSD", "", "32"), ipList.get(2), 1600));

        execService.execute(new LinuxMultiThreaded(StaticDataProvider.LinuxSimulation.CD_CMAKE_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD,"--ib-crash -d2 --f","Cmake", "", "32"), ipList.get(3), 400));

        execService.execute(new LinuxMultiThreaded(StaticDataProvider.LinuxSimulation.CD_APACHE_DIR + ";" + StaticDataProvider.LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(StaticDataProvider.LinuxSimulation.MAKE_BUILD,"--ib-crash -d2 --f","Apache", "", "32"), ipList.get(4), 800));

        execService.shutdown();
        execService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);

    }

}
