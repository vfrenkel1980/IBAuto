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
        try{
            test.log(Status.INFO, "Starting service");
            Runtime.getRuntime().exec("powershell.exe Start-Service docker");
            test.log(Status.INFO, "Starting container");
            Runtime.getRuntime().exec("docker start -ai affectionate_swartz");
            test.log(Status.INFO,"Container started");
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
