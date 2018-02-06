package ibInfra.vsui;

import frameworkInfra.testbases.TestBase;
import static frameworkInfra.testbases.VSTestBase.driver;

public class VSUIService extends TestBase implements IVSUIService {

    public void vsFirstActivation(){
        driver.findElementByName("Not now, maybe later.").click();
        driver.findElementByName("Start Visual Studio").click();
    }

    @Override
    public void installVSWithIB() {

    }

    @Override
    public void installVSWithoutIB() {

    }

    @Override
    public void upgradeVSWithIB() {

    }

}
