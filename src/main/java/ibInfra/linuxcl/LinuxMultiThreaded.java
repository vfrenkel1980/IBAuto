package ibInfra.linuxcl;

import frameworkInfra.testbases.LinuxSimTestBase;
import org.testng.asserts.SoftAssert;

import java.util.concurrent.TimeUnit;

public class LinuxMultiThreaded extends LinuxSimTestBase implements Runnable{

    private String command;
    private String machine;
    private int cycles;

    public LinuxMultiThreaded(String command, String machine, int cycles){
        this.command = command;
        this.machine = machine;
        this.cycles = cycles;
    }

    public void run(){
        SoftAssert softAssert = new SoftAssert();
        int exitCode;
        for (int i = 0 ; i < cycles ; i++) {
            //test = extent.createTest("Test No. " + i);
            exitCode = linuxService.linuxRunSSHCommand(command, machine);
            softAssert.assertEquals(exitCode, 0, "Build Failed");
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.getMessage();
            }
        }
    }
}
