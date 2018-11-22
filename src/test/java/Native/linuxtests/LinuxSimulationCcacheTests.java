package Native.linuxtests;

//import frameworkInfra.testbases.linux.LinuxSimTestBase;
import frameworkInfra.testbases.LinuxSimTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import static frameworkInfra.utils.StaticDataProvider.*;


public class LinuxSimulationCcacheTests extends LinuxSimTestBase {

    private  String env = "export PATH=/usr/lib/ccache:$PATH; ";
    private static int KernelCcacheSize = 519268;
    private static int QTernelCcacheSize = 669948;

    @Test(testName = "Sim ccache kenrel4 ln")
    public void SimTestCcacheKernel4ln() {

        log.info("starting ccache kenrel4 ln class");
        linuxService.linuxRunSSHCommand("cd ~/.ccache && ccache -C", ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand(env + LinuxSimulation.CD_KERNEL4_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" + "make -j16", ipList.get(simClassType.ordinal()));
        String noIBsize = linuxService.linuxRunSSHCommandOutputString(LinuxCommands.DU_TOTAL_ONLY, ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand(env + LinuxSimulation.CD_KERNEL4_DIR + "/kernel; touch kmod.c kprobes.c ksysfs.c kthread.c; cd ..; make -j16", ipList.get(simClassType.ordinal()));

        String inc_noIBsize = linuxService.linuxRunSSHCommandOutputString(LinuxCommands.DU_TOTAL_ONLY, ipList.get(simClassType.ordinal()));
        log.info("1) inc_noIBsize = " + inc_noIBsize);
        linuxService.linuxRunSSHCommand("cd ~/.ccache && ccache -C", ipList.get(simClassType.ordinal()));

        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_KERNEL4_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD, "--ib-crash -d1 --f", "SimTestCcacheKernel4ln", "env PATH=/usr/lib/ccache:$PATH", "32"), ipList.get(simClassType.ordinal()));

        Assert.assertTrue(exitCode <=0, "Test " + testName + "failed with Exit code " + exitCode);

        String withIBsize = linuxService.linuxRunSSHCommandOutputString(LinuxCommands.DU_TOTAL_ONLY, ipList.get(simClassType.ordinal()));

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_KERNEL4_DIR + "/kernel" + ";" + "touch kmod.c kprobes.c ksysfs.c kthread.c; cd ..; " +
                String.format(LinuxSimulation.MAKE_BUILD, "--ib-crash -d1 --f", "Inc_SimTestCcacheKernel4ln", "env PATH=/usr/lib/ccache:$PATH", "32"), ipList.get(simClassType.ordinal()));
        String inc_withIBsize = linuxService.linuxRunSSHCommandOutputString(LinuxCommands.DU_TOTAL_ONLY, ipList.get(simClassType.ordinal()));

        String newer = linuxService.linuxRunSSHCommandOutputString(LinuxSimulation.CD_KERNEL4_DIR + "/kernel; find -name kmod.c -newer kmod.o;", ipList.get(simClassType.ordinal()));
        newer += linuxService.linuxRunSSHCommandOutputString(LinuxSimulation.CD_KERNEL4_DIR + "/kernel; find -name kprobes.c -newer kprobes.o;", ipList.get(simClassType.ordinal()));
        newer += linuxService.linuxRunSSHCommandOutputString(LinuxSimulation.CD_KERNEL4_DIR + "/kernel; find -name ksysfs.c -newer ksysfs.o;", ipList.get(simClassType.ordinal()));
        newer += linuxService.linuxRunSSHCommandOutputString(LinuxSimulation.CD_KERNEL4_DIR + "/kernel; find -name tracepoint.o -newer ksysfs.o;", ipList.get(simClassType.ordinal()));

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_KERNEL4_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(simClassType.ordinal()));

        if (!newer.isEmpty())
            Assert.assertEquals(1, 0, "Test failed - incremental build files");

        if (!noIBsize.equals((withIBsize)))
            Assert.assertEquals(1, 0, "Test failed - ccache size changed");

        if (!inc_noIBsize.equals((inc_withIBsize)))
            Assert.assertEquals(1, 0, "Test failed - ccache size changed - incremental ");
    }

    @Test(testName = "Sim ccache kenrel4 prefix")
    public void SimTestccacheKernel4prefix(){

        linuxService.linuxRunSSHCommand("cd ~/.ccache && ccache -C", ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand( LinuxSimulation.CD_KERNEL4_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" + "make -j16", ipList.get(simClassType.ordinal()));
        String noIBsize = linuxService.linuxRunSSHCommandOutputString(LinuxCommands.DU_TOTAL_ONLY, ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_KERNEL4_DIR + "/kernel; touch kmod.c kprobes.c ksysfs.c kthread.c; cd ..; make -j16", ipList.get(simClassType.ordinal()));


        String inc_noIBsize = linuxService.linuxRunSSHCommandOutputString(LinuxCommands.DU_TOTAL_ONLY, ipList.get(simClassType.ordinal()));
        log.info("2) inc_noIBsize = " + inc_noIBsize);
        linuxService.linuxRunSSHCommand("cd ~/.ccache && ccache -C", ipList.get(simClassType.ordinal()));

        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_KERNEL4_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 --f","SimTestccacheKernel4prefix", "", "32"), ipList.get(simClassType.ordinal()));

        Assert.assertTrue(exitCode <= 0, "Test "+ testName + "failed with Exit code " + exitCode);

        String withIBsize = linuxService.linuxRunSSHCommandOutputString(LinuxCommands.DU_TOTAL_ONLY, ipList.get(simClassType.ordinal()));
        // linuxService.linuxRunSSHCommand(LinuxSimulation.CD_KERNEL4_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(simClassType.ordinal());
        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_KERNEL4_DIR + "/kernel" + ";" + "touch kmod.c kprobes.c ksysfs.c kthread.c; cd ..; " +
                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 --f","Inc_SimTestccacheKernel4prefix", "", "32"), ipList.get(simClassType.ordinal()));
        String inc_withIBsize = linuxService.linuxRunSSHCommandOutputString(LinuxCommands.DU_TOTAL_ONLY, ipList.get(simClassType.ordinal()));

        String newer = linuxService.linuxRunSSHCommandOutputString(LinuxSimulation.CD_KERNEL4_DIR + "/kernel; find -name kmod.c -newer kmod.o;", ipList.get(simClassType.ordinal()));
        newer += linuxService.linuxRunSSHCommandOutputString(LinuxSimulation.CD_KERNEL4_DIR + "/kernel; find -name kprobes.c -newer kprobes.o;", ipList.get(simClassType.ordinal()));
        newer += linuxService.linuxRunSSHCommandOutputString(LinuxSimulation.CD_KERNEL4_DIR + "/kernel; find -name ksysfs.c -newer ksysfs.o;", ipList.get(simClassType.ordinal()));

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_KERNEL4_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(simClassType.ordinal()));

        if(!newer.isEmpty())
            Assert.assertEquals(1, 0, "Test failed - incremental build files" );

        if(!noIBsize.equals((withIBsize)))
            Assert.assertEquals(1, 0, "Test failed - ccache size changed" );

        if(!inc_noIBsize.equals((inc_withIBsize)))
            Assert.assertEquals(1, 0, "Test failed - ccache size changed - incremental " );

    }

    @Test(testName = "Sim ccache QT ln")
    public void SimTestccacheQTln(){

        linuxService.linuxRunSSHCommand("cd ~/.ccache && ccache -C", ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand(env + LinuxSimulation.CD_QT_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" + "make -j32", ipList.get(simClassType.ordinal()));
        String noIBsize = linuxService.linuxRunSSHCommandOutputString(LinuxCommands.DU_TOTAL_ONLY, ipList.get(simClassType.ordinal()));
        log.info("3) noIBsize = " + noIBsize);
        linuxService.linuxRunSSHCommand(env + LinuxSimulation.CD_QT_DIR + "/src/sql/kernel/" + ";" +
                "touch qsqlquery.cpp qsqlrecord.cpp qsqlfield.cpp qsqlerror.cpp; " + "cd ../../..; make -j32", ipList.get(simClassType.ordinal()));

        String inc_noIBsize = linuxService.linuxRunSSHCommandOutputString(LinuxCommands.DU_TOTAL_ONLY, ipList.get(simClassType.ordinal()));
        linuxService.linuxRunSSHCommand("cd ~/.ccache && ccache -C", ipList.get(simClassType.ordinal()));
        log.info("4)QT: inc_noIBsize = " + inc_noIBsize);
        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_QT_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 --f","SimTestccacheQTln", "env PATH=/usr/lib/ccache:$PATH", "32"), ipList.get(simClassType.ordinal()));

        Assert.assertTrue(exitCode <= 0, "Test "+ testName + "failed with Exit code " + exitCode);

        String withIBsize = linuxService.linuxRunSSHCommandOutputString(LinuxCommands.DU_TOTAL_ONLY, ipList.get(simClassType.ordinal()));

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_QT_DIR + "/src/sql/kernel;"  +
                "touch qsqlquery.cpp qsqlrecord.cpp qsqlfield.cpp qsqlerror.cpp; " + "cd ../../..;" +
                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 --f","Inc_SimTestccacheQTln", "env PATH=/usr/lib/ccache:$PATH", "32"), ipList.get(simClassType.ordinal()));

        String inc_withIBsize = linuxService.linuxRunSSHCommandOutputString(LinuxCommands.DU_TOTAL_ONLY, ipList.get(simClassType.ordinal()));

        String newer = linuxService.linuxRunSSHCommandOutputString(LinuxSimulation.CD_KERNEL4_DIR + "/src/sql/kernel; find -name qsqlquery.cpp -newer ../../../lib/libQtSql.so.4.8.6;", ipList.get(simClassType.ordinal()));
        newer += linuxService.linuxRunSSHCommandOutputString(LinuxSimulation.CD_KERNEL4_DIR + "/lib; find -name libQtScript.so.4.8.6 -newer libQtSql.so.4.8.6;", ipList.get(simClassType.ordinal()));

        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_QT_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(simClassType.ordinal()));

        if(!newer.isEmpty())
            Assert.assertEquals(1, 0, "Test failed - incremental build files" );

        if(!noIBsize.equals((withIBsize)))
            Assert.assertEquals(1, 0, "Test failed - ccache size changed" );

        if(!inc_noIBsize.equals((inc_withIBsize)))
            Assert.assertEquals(1, 0, "Test failed - ccache size changed - incremental " );

    }

//-----------------------------------------------------------------------------------------------------------------------------------------
//    @Test(testName = "Sim ccache QT prefix")
//    public void SimTestccacheQTprefix(){
//
//        linuxService.linuxRunSSHCommand("cd ~/.ccache && ccache -C", ipList.get(2));
//        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_QT_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" + "make -j32", ipList.get(2));
//        String noIBsize = linuxService.linuxRunSSHCommandOutputString(LinuxCommands.DU_TOTAL_ONLY, ipList.get(2));
//
//        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_QT_DIR + "/src/sql/kernel/" + ";" +
//                "touch qsqlquery.cpp qsqlrecord.cpp qsqlfield.cpp qsqlerror.cpp; " + "cd ../../..; make -j32", ipList.get(2));
//
//        String inc_noIBsize = linuxService.linuxRunSSHCommandOutputString(LinuxCommands.DU_TOTAL_ONLY, ipList.get(2));
//        linuxService.linuxRunSSHCommand("cd ~/.ccache && ccache -C", ipList.get(2));
//
//        int exitCode = linuxService.linuxRunSSHCommand(LinuxSimulation.CD_QT_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";" +
//                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 --f","SimTestccacheQTprefix", "", "32"), ipList.get(2));
//
////        Assert.assertEquals(exitCode, 0, "Test "+ testName + "failed with Exit code " + exitCode);
//
//        String withIBsize = linuxService.linuxRunSSHCommandOutputString(LinuxCommands.DU_TOTAL_ONLY, ipList.get(2));
//
//        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_QT_DIR + "/src/sql/kernel;"  +
//                "touch qsqlquery.cpp qsqlrecord.cpp qsqlfield.cpp qsqlerror.cpp; " + "cd ../../..;" +
//                String.format(LinuxSimulation.MAKE_BUILD,"--ib-crash -d1 --f","SimTestccacheQTprefix", "", "32"), ipList.get(2));
//
//        String inc_withIBsize = linuxService.linuxRunSSHCommandOutputString(LinuxCommands.DU_TOTAL_ONLY, ipList.get(2));
//
//        String newer = linuxService.linuxRunSSHCommandOutputString(LinuxSimulation.CD_KERNEL4_DIR + "/src/sql/kernel; find -name qsqlquery.cpp -newer ../../../lib/libQtSql.so.4.8.6;", ipList.get(2));
//        newer += linuxService.linuxRunSSHCommandOutputString(LinuxSimulation.CD_KERNEL4_DIR + "/lib; find -name libQtScript.so.4.8.6 -newer libQtSql.so.4.8.6;", ipList.get(2));
//
//        linuxService.linuxRunSSHCommand(LinuxSimulation.CD_QT_DIR + ";" + LinuxSimulation.MAKE_CLEAN + ";", ipList.get(2));
//
//        if(!newer.isEmpty())
//            Assert.assertEquals(1, 0, "Test failed - incremental build files" );
//
//
//        if(!noIBsize.equals((withIBsize)))
//            Assert.assertEquals(1, 0, "Test failed - ccache size changed" );
//
//        if(!inc_noIBsize.equals((inc_withIBsize)))
//            Assert.assertEquals(1, 0, "Test failed - ccache size changed - incremental " );
//
//        log.info("finished LinuxSimulationCcacheTests: ccashe size: with IB = " + withIBsize + " without IB = " +
//                noIBsize + "incremental with IB = " + inc_withIBsize+ " without IB = " + inc_noIBsize);
//    }
}
