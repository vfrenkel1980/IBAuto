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

        execService.execute(new LinuxMultiThreaded(ipList.get(1), 250));
        execService.execute(new LinuxMultiThreaded(ipList.get(2), 250));
        execService.execute(new LinuxMultiThreaded(ipList.get(3), 250));
        execService.execute(new LinuxMultiThreaded(ipList.get(4), 250));

        execService.shutdown();
        execService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
    }

}
