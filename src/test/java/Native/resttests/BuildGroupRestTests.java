package Native.resttests;

import frameworkInfra.testbases.rest.BuildGroupRestTestsBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import webInfra.rest.utils.RestConstants;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class BuildGroupRestTests  extends BuildGroupRestTestsBase {

    /**
     * @test Build Groups. REST calls 1. Get Groups list<br>
     * @pre{ you should  have machine with coordinator  and "n" agents as helpers }
     * @steps { - run api  in order to receive  count of agents  and  group for example ("https://192.168.10.233:31100/Groups/list") }
     * @result{ - counts of agents ="n"",group ="Default"}
     */

    @Test(testName = "GetGroupList",priority =0)
    public void getGroupList()  {
        get(RestConstants.Paths.Buildgroup.GROUP_LIST_PATH).
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK).
                body(containsString("AgentCount")).body(containsString("Default"));

    }

    @Test(testName = "AddHelperToBuildGroup",priority =1)
    public void addHelperToBuildGroup()  {
        given().
                contentType("application/json").
                body(RestConstants.Body.Buildgroup.BodyContent).
                when().
                post(String.format("https://%s:31100/Groups/%s/AddAgents",RestConstants.Body.CoordinatorIps.ip,RestConstants.Body.BuildGroups.TestedBuildgroup)).
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK);

         List <String> groups = get(RestConstants.Paths.Buildgroup.GROUP_LIST_PATH).then().extract().path("Group");
         Assert.assertTrue(groups.contains(RestConstants.Body.BuildGroups.TestedBuildgroup), "failed to add " + RestConstants.Body.HelpersNames.HelperName + " to group " + RestConstants.Body.BuildGroups.TestedBuildgroup);

    }

    @Test(testName = "VerifyErrorMassageWhenClickOnGenerateButton")
    public void verifyErrorMassageWhenClickOnGenerateButton()  {

        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.REQUIREDAPIKEYFORACCESS, "1");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.GENERATEDAPIKEY, RestConstants.Headers.api_key);
        winService.restartService(StaticDataProvider.WindowsServices.COORD_SERVICE);
        get(RestConstants.Paths.Buildgroup.GROUP_LIST_PATH).
                then().
                assertThat().
                statusCode(HttpStatus.SC_FORBIDDEN).
                body(containsString("Fail to authenticate"));

        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.GENERATEDAPIKEY, RestConstants.Headers.api_key);
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.REQUIREDAPIKEYFORACCESS, "0");
        winService.restartService(StaticDataProvider.WindowsServices.COORD_SERVICE);

    }
    @Test(testName = "VerifyResponseIsBackAfterSupplyingApiKey")
    public void verifyResponseIsbackAfterSupplyingApiKey()  {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.REQUIREDAPIKEYFORACCESS, "1");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.GENERATEDAPIKEY, RestConstants.Headers.api_key);

        given().
                header("api-key",RestConstants.Headers.api_key).
                when().
                get(RestConstants.Paths.Buildgroup.GROUP_LIST_PATH).
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK).body(containsString("AgentCount")).body(containsString("Default"));
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.REQUIREDAPIKEYFORACCESS, "0");
        winService.restartService(StaticDataProvider.WindowsServices.COORD_SERVICE);


    }

}
