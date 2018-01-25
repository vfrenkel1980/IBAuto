package windowstests;

import frameworkInfra.testbases.TestBase;

public class WindowsCLTests extends TestBase {

/*    @Test
    public void runBuildConsoleAndVerifyUsingUI() throws FindFailed {
        Screen screen = new Screen();
        WindowsDriver session = null;
        String command = "buildconsole.exe \\\\192.168.10.15\\Share\\Mark\\ConsoleApplication3\\ConsoleApplication3.sln " +
                "/rebuild /cfg=\"debug|x64\"";
        int returnCode = CommandLineRunner.runCommandWaitForFinish(command);
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);

        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            test.log(Status.INFO, "Opening ibmonitor");
            capabilities.setCapability("app", "C:\\Program Files (x86)\\Xoreax\\IncrediBuild\\BuildMonitor.exe");
            session = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            session.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            test.log(Status.INFO, "ibmonitor opened successfully");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        screen.wait(IBMonitor.consoleApp3Rebuild.similar((float) 0.5),2).click(IBMonitor.consoleApp3Rebuild);

    }*/




}
