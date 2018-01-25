package UnitTests;

import frameworkInfra.testbases.UnitTestBase;
import frameworkInfra.utils.StaticDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UnitTests extends UnitTestBase {

    @Test
    public void test(){
        int returnCode = UnitTestBase.cleanAndBuild(String.format(StaticDataProvider.TEST, "%s"));
        Assert.assertTrue(returnCode == 0 || returnCode == 2, "Build failed with return code " + returnCode);
    }

}
