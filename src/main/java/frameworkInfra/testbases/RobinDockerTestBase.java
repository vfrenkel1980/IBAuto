package frameworkInfra.testbases;

import com.aventstack.extentreports.Status;
import frameworkInfra.Listeners.SuiteListener;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import java.io.IOException;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class RobinDockerTestBase extends RobinTestBase{

    @BeforeClass
    @Parameters({ "logLevel"})
    public void startDocker(String logLevel){
        executeCMD("powershell.exe Start-Service docker","Starting service");
        executeCMD("docker start -ai affectionate_swartz","Starting container");
    }

    private void executeCMD(String command, String logMessage) {
        try {
            test.log(Status.INFO, logMessage);
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
