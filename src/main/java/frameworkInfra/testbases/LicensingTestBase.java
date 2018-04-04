package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import com.sun.jna.platform.win32.WinReg;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.SystemActions;
import org.testng.annotations.*;

public class LicensingTestBase extends ReleaseTestBase{

    private String SCENARIO = System.getProperty("scenario");

    @BeforeSuite
    public void beforeSuite(){
        ibService.installIB("Latest");
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.getMessage();
        }
    }

    @BeforeClass
    public void beforeClass(String licenseTests){
        RegistryService.setRegistryKey(WinReg.HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Builder", StaticDataProvider.RegistryKeys.STANDALONE_MODE, "0");
        RegistryService.setRegistryKey(WinReg.HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Builder", StaticDataProvider.RegistryKeys.AVOID_LOCAL, "1");

        ibService.unloadIbLicense();

        switch (SCENARIO){
            case ("1"):
                //Clean installation - No IB license loaded
            case ("2"):
                //No packages aside from agent package
        }
    }

    @BeforeMethod
    public void beforeMethod(){
        test = extent.createTest("BEFORE METHOD");
        test.assignCategory("BEFORE METHOD");
        test.log(Status.INFO, "BEFORE METHOD started");

    }

    @AfterMethod
    public void afterMethod(){
        SystemActions.deleteFile(StaticDataProvider.Locations.OUTPUT_LOG_FILE);
    }

    @AfterClass
    public void afterClass(){
    }

    @AfterSuite
    public void afterSuite(){
        ibService.uninstallIB("Latest");
    }

}
