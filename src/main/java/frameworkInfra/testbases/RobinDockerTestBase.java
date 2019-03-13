package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.Listeners.SuiteListener;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import java.io.IOException;

import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class RobinDockerTestBase extends RobinTestBase {

    @BeforeClass
    @Parameters({"logLevel"})
    public void startDocker(String logLevel) {
        winService.runCommandWaitForFinish("powershell.exe Start-Service docker");
        test.log(Status.INFO,"Starting service");
        winService.runCommandDontWaitForTermination("powershell.exe " + DockerCommands.DOCKER_START_CONTAINER + DockerCommands.WIN10_DOC_CONTAINER);
        SystemActions.sleep(30);
        test.log(Status.INFO,"Starting container");
        winService.runCommandWaitForFinish(DockerCommands.DOCKER_EXEC + DockerCommands.WIN10_DOC_CONTAINER + "net start \"IncrediBuild Agent\" ");
    }
}
