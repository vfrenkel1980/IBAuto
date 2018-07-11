package Native.linuxtests;

import frameworkInfra.testbases.LinuxMultiInitiatorsTestBase;
import ibInfra.linuxcl.LinuxMultiThreaded;
import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LinuxMultiInitiatorTests  extends LinuxMultiInitiatorsTestBase {

    @Test(testName = "MultiInitiator")
    public static void multiInitiators() throws InterruptedException {

        ExecutorService execService = Executors.newFixedThreadPool(4);

        execService.execute(new LinuxMultiThreaded(ipList.get(1), 2));
        execService.execute(new LinuxMultiThreaded(ipList.get(2), 2));
        execService.execute(new LinuxMultiThreaded(ipList.get(3), 2));
        execService.execute(new LinuxMultiThreaded(ipList.get(4), 2));

        execService.shutdown();
        execService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
    }

}
