package Native.UnitTests;

import cloudInfra.IncrediCloud.incrediCloudService.IncrediCloudService;
import cloudInfra.IncrediCloud.webServer.WebServer;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.databases.PostgresJDBC;
import ibInfra.ibExecs.IIBCoordMonitor;
import ibInfra.ibService.IbService;

import ibInfra.windowscl.WindowsService;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.apache.http.protocol.HttpRequestHandler;
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

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;


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



    @Test(testName = "test1")
    public void test() {
        System.out.println(ibService.getNumberOfMachinesParticipateInBuild("Incredi-markz"));
    }

}


