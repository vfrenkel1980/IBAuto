package frameworkInfra.testbases.web.dashboard;


import org.testng.annotations.BeforeClass;

public class BuildsTabTestBase extends DashboardTestBase {

    @BeforeClass
    public void beforeClassBuilds(){
        buildPageObject.goToBuildsPage();
    }
}
