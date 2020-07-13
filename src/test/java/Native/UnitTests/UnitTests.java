package Native.UnitTests;

import cloudInfra.IncrediCloud.incrediCloudService.IncrediCloudService;
import cloudInfra.IncrediCloud.webServer.WebServer;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import frameworkInfra.Listeners.EventHandler;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.databases.PostgresJDBC;
import frameworkInfra.utils.parsers.Parser;
import ibInfra.ibExecs.IIBCoordMonitor;
import ibInfra.ibService.IbService;

import ibInfra.ibUIService.IBUIService;
import ibInfra.windowscl.WindowsService;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.apache.http.protocol.HttpRequestHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.utils.StaticDataProvider.RegistryKeys.pathLenCPU_UTIL_CORE;


public class UnitTests {
    WindowsService winService = new WindowsService();
    IbService ibService = new IbService();
    PostgresJDBC postgresJDBC = new PostgresJDBC();
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
    String projectName = "test";
    String INITIATOR = "testinit";
    private Date startTime;
    private Date endTime;
    String requestedCores = "15";
    IIBCoordMonitor coordMonitor = new IIBCoordMonitor();
    IncrediCloudService incrediCloudService = new IncrediCloudService("bla");
    public static WebDriver webDriver = null;
    public EventFiringWebDriver eventWebDriver;
    protected EventHandler handler = new EventHandler();
    private IBUIService ibuiService = new IBUIService();
    protected IBUIService.Client client = ibuiService.new Client();


    @Test(testName = "test2")
    public void test() {
        winService.runCommandDontWaitForTermination(StaticDataProvider.Processes.AGENTSETTINGS);
        client.changeCpuUtilCores(pathLenCPU_UTIL_CORE);
        ibService.cleanAndBuild(StaticDataProvider.IbLocations.BUILD_CONSOLE + String.format(StaticDataProvider.ProjectsCommands.AGENT_SETTINGS.AUDACITY_X32_DEBUG, "%s"));
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Builder", StaticDataProvider.RegistryKeys.FORCE_CPU_INITIATOR, "0");
        ibService.agentServiceStop();
        ibService.agentServiceStart();

        Assert.assertFalse(Parser.doesFileContainString(StaticDataProvider.Locations.OUTPUT_LOG_FILE, "CPU 2"));
    }




}


