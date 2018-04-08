package frameworkInfra.testbases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import ibInfra.ibService.IIBService;
import ibInfra.ibService.IbService;
import ibInfra.windowscl.WindowsService;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReleaseTestBase extends TestBase{

    public WindowsService winService = new WindowsService();
    public IbService ibService = new IbService();

    static {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/src/main/java/frameworkInfra/reports/TestOutput" + formatter.format(calendar.getTime()) + " - License Tests" + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

}
