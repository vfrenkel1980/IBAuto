package ibInfra.linuxcl;

import frameworkInfra.testbases.LinuxSimTestBase;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class LinuxMultiThreaded extends LinuxSimTestBase implements Runnable{

    private String command;
    private String machine;

    public LinuxMultiThreaded(String command, String machine){
        this.command = command;
        this.machine = machine;
    }

    public void run(){
        int exitCode = runLinux.linuxRunSSHCommand(command, machine);
        Assert.assertEquals(exitCode, 1, "Build Failed");
        try{
            TimeUnit.MILLISECONDS.sleep(200);
        }catch (InterruptedException e)
        {
            e.getMessage();
        }
    }
}
