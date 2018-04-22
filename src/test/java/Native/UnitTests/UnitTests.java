package Native.UnitTests;

import ibInfra.ibService.IbService;
import org.testng.annotations.Test;

public class UnitTests {

    @Test
    public void test(){
        IbService ibService = new IbService();
        ibService.unloadIbLicense();
    }
}
