package frameworkInfra.testbases.web.dashboard;

import frameworkInfra.Listeners.SuiteListener;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;


import static frameworkInfra.Listeners.SuiteListener.extent;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class upgradeEntTestBase extends DashboardTestBase {

    @BeforeClass
    public void beforeClassUpgradeEnt(){

    }

    @AfterClass
    public void afterClass(){

    }
}
