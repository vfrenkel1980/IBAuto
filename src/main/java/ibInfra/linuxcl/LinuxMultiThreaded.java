package ibInfra.linuxcl;
import java.util.Random;

import com.aventstack.extentreports.Status;
import frameworkInfra.testbases.LinuxSimTestBase;
import frameworkInfra.utils.StaticDataProvider.*;
import org.testng.asserts.SoftAssert;

import java.util.concurrent.TimeUnit;

import static frameworkInfra.Listeners.SuiteListener.test;

public class LinuxMultiThreaded extends LinuxSimTestBase implements Runnable{

    //private String command;
    private String machine;
    private int cycles;
    private static int activeProjects[]= new int[9];
    private static Random rand = new Random();
    private int randIndex =-1;

    public LinuxMultiThreaded ( String machine, int cycles){//(String command, String machine, int cycles){
        this.machine = machine;
        this.cycles = cycles;
    }

    public void run(){
        SoftAssert softAssert = new SoftAssert();
        int exitCode;
        for (int i = 0 ; i < cycles ; i++) {
            test.log(Status.INFO, "starting cycle " +i );
            String command = "";

            if(linuxService.isLinuxOSUbuntu(machine)) {

                if (testNum == TestNum.MultiBuild) {
                    synchronized (this) {
                        do {
                            randIndex = rand.nextInt(9);
                        }
                        while (activeProjects[randIndex] == 1);

                        activeProjects[randIndex] = 1;
                    }
                }
                else if (testNum == TestNum.MultiIn)
                    randIndex = rand.nextInt(9);

                switch (randIndex) {
                    case 0:
                        command = LinuxSimulation.CD_KERNEL_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                                String.format(LinuxSimulation.MAKE_BUILD, "--ib-crash -d1 ", "Kernel", "", "32");
                        break;
                    case 1:
                        command = LinuxSimulation.CD_APACHE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                                String.format(LinuxSimulation.MAKE_BUILD, "--ib-crash -d1 ", "Apache", "", "32");
                        break;
                    case 2:
                        command = LinuxSimulation.CD_CMAKE_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                                String.format(LinuxSimulation.MAKE_BUILD, "--ib-crash -d1 ", "Cmake", "", "32");
                        break;
                    case 3:
                        command = LinuxSimulation.CD_SAMBA_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                                String.format(LinuxSimulation.MAKE_BUILD, "--ib-crash -d1 ", "Samba", "env JOBS=100", "32");
                        break;
                    case 4:
                        command = LinuxSimulation.CD_CPP_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                                String.format(LinuxSimulation.MAKE_BUILD, "--ib-crash -d1 ", "CPP", "", "32");
                        break;
                    case 5:
                        command = LinuxSimulation.CD_MYSQL_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                                String.format(LinuxSimulation.MAKE_BUILD, "--ib-crash -d1 ", "MySQL", "", "32");
                        break;
                    case 6:
                        command = LinuxSimulation.CD_GIT_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                                String.format(LinuxSimulation.MAKE_BUILD, "--ib-crash -d1 ", "Git", "", "32");
                        break;
                    case 7:
                        command = LinuxSimulation.CD_GDB_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                                String.format(LinuxSimulation.MAKE_BUILD, "--ib-crash -d1 ", "GDB", "", "32");
                        break;
                    case 8:
                        command = LinuxSimulation.CD_BOOST_DIR + ";" + LinuxSimulation.B2_CLEAN + ";" +
                                String.format(LinuxSimulation.B2_BUILD, "--ib-crash -d1 ", "Boost", "", "32");
                        break;
                }
            }
            else //CentOS
            {
                randIndex = rand.nextInt(4);
                switch (randIndex) {
                    case 0:
                        command = LinuxSimulation.CD_KERNEL_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                                String.format(LinuxSimulation.MAKE_BUILD, "--ib-crash -d1 ", "Kernel", "", "32");
                        break;
                    case 1:
                        command = LinuxSimulation.CD_GPSD_DIR + ";" + LinuxSimulation.SCONS_CLEAN + ";" +
                                String.format(LinuxSimulation.SCONS_BUILD, "--ib-crash -d1 ", "GPSD", "", "32");
                        break;
                    case 2:
                        command = LinuxSimulation.CD_SAMBA2_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                                String.format(LinuxSimulation.MAKE_BUILD, "--ib-crash -d1 ", "Samba", "env JOBS=100", "32");
                        break;
                    case 3:
                        command = LinuxSimulation.CD_MYSQL2_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                                String.format(LinuxSimulation.MAKE_BUILD, "--ib-crash -d1 ", "MySQL", "", "32");
                        break;
                }
            }
            test.log(Status.INFO, "IP " + machine + ": command = " +command );
            exitCode = linuxService.linuxRunSSHCommand(command, machine);
            softAssert.assertEquals(exitCode, 0, "Build Failed");

            if (testNum == TestNum.MultiBuild) {
                synchronized (this) {
                    activeProjects[randIndex] = 0;
                }
            }
        }

        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            test.log(Status.WARNING, e.getMessage());
        }
    }
}
