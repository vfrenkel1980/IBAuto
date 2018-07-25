package Native.UnitTests;

import com.jcraft.jsch.JSchException;
import frameworkInfra.utils.*;
import ibInfra.dataObjects.postgres.CoordBuild;
import ibInfra.ibService.IbService;
import ibInfra.linuxcl.LinuxDBService;
import ibInfra.linuxcl.LinuxService;
import ibInfra.vs.VSUIService;
import ibInfra.windowscl.WindowsService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class UnitTests {

    @Test
    public void test() {
        VSUIService vsService = new VSUIService();
        IbService ibService = new IbService();
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Builder", StaticDataProvider.RegistryKeys.SAVE_BUILD_PACKET, "1");
        vsService.openVSInstance("15", false);
        vsService.createNewProject("custom");
        vsService.performIbActionFromPrjExplorer(StaticDataProvider.VsActions.REBUILD_SOLUTION, "solution", "custom");
        String result;
        try {
            result = ibService.findValueInPacketLog("ExitCode ");
            Assert.assertTrue(result.equals("0"));
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Test(testName = "test2")
    public void test2() throws JSchException {
        List<CoordBuild> cars= new ArrayList<CoordBuild>();
        for (int i= 0; i < 5; i++)
            cars.add(new CoordBuild());
        System.out.println("");
    }


    @Test(testName = "test3")
    public void test3 () {
        CoordBuild coordBuild = new CoordBuild("2222", 4, 1235, 12344, "test",
                10, 0, 1, 1, 4, 1, 1,
                2, 1, 1, 3, null, null, null, 0,
                false, "this is the command", false);
        PostgresJDBC.runFunctionOnCoordBuildTable("localhost", "ib", "ib", "coordinatordb",
                "public.sp_insert_coord_build", coordBuild);
        //System.out.println(PostgresJDBC.getAllBuildsWhere("localhost", "ib", "ib", "coordinatordb",
        //        "*", "public.coord_build", "status=0"));
    }

}