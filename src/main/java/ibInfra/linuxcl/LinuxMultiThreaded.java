package ibInfra.linuxcl;

import frameworkInfra.testbases.TestBase;

import java.util.concurrent.TimeUnit;

public class LinuxMultiThreaded extends TestBase implements Runnable{

    private String command;
    private String machine;
    private LinuxCL runCommand = new LinuxCL();

    public LinuxMultiThreaded(String command, String machine){
        this.command = command;
        this.machine = machine;
    }

    public void run(){
        runCommand.linuxRunSSHCommand(command, machine);
        try{
            TimeUnit.MILLISECONDS.sleep(200);
        }catch (InterruptedException e)
        {
            e.getMessage();
        }
    }
}
