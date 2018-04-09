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

        execService.execute(new LinuxMultiThreaded(ipList.get(1), 250));
        execService.execute(new LinuxMultiThreaded(ipList.get(1), 250));
        execService.execute(new LinuxMultiThreaded(ipList.get(1), 250));
        execService.execute(new LinuxMultiThreaded(ipList.get(1), 250));

        execService.shutdown();
        execService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);

    }
}


