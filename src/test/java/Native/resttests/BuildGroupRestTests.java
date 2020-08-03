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

    @Test(testName = "GetGroupList",priority =1)
    public void getGroupList()  {
        get(RestConstants.Paths.Buildgroup.GROUP_LIST_PATH).
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK).
                body(containsString("AgentCount")).body(containsString("Default"));

    }

    @Test(testName = "AddHelperToBuildGroup",priority =2)
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

    @Test(testName = "VerifyErrorMassageWhenClickOnGenerateButton",priority =3)
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
    @Test(testName = "VerifyResponseIsBackAfterSupplyingApiKey",priority =4 )
    public void verifyResponseIsbackAfterSupplyingApiKey()  {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.REQUIREDAPIKEYFORACCESS, "1");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.GENERATEDAPIKEY, RestConstants.Headers.api_key);
        winService.restartService(StaticDataProvider.WindowsServices.COORD_SERVICE);

        given().
                header("api-key",RestConstants.Headers.api_key).
                when().
                get(RestConstants.Paths.Buildgroup.GROUP_LIST_PATH).
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK).body(containsString("AgentCount")).body(containsString("Default"));


        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.GENERATEDAPIKEY, RestConstants.Headers.api_key);
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.REQUIREDAPIKEYFORACCESS, "0");
        winService.restartService(StaticDataProvider.WindowsServices.COORD_SERVICE);

    }

//-- - -----------------------------Clean  Buils group tests-------------------------------------------------------
@Test(testName = "VerifyErrorMassageWhenClickOnGenerateButtonCleanGroup",priority =5)
public void verifyErrorMassageWhenClickOnGenerateButtonCleangroup()  {

    RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.REQUIREDAPIKEYFORACCESS, "1");
    RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.GENERATEDAPIKEY, RestConstants.Headers.api_key);
    winService.restartService(StaticDataProvider.WindowsServices.COORD_SERVICE);
    get(String.format(RestConstants.Paths.Buildgroup.CLEAR_EXISTED_GROUP,RestConstants.Body.BuildGroups.TestedBuildgroup)).
            then().
            assertThat().
            statusCode(HttpStatus.SC_FORBIDDEN).
            body(containsString("Fail to authenticate"));

    RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.GENERATEDAPIKEY, RestConstants.Headers.api_key);
    RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.REQUIREDAPIKEYFORACCESS, "0");
    winService.restartService(StaticDataProvider.WindowsServices.COORD_SERVICE);




}
    @Test(testName = "VerifyResponseIsBackAfterSupplyingApiKeyCleangroup",priority =6)
    public void verifyResponseIsbackAfterSupplyingApiKeyCleanGroup()  {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.REQUIREDAPIKEYFORACCESS, "1");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.GENERATEDAPIKEY, RestConstants.Headers.api_key);
        winService.restartService(StaticDataProvider.WindowsServices.COORD_SERVICE);

        given().
                header("api-key",RestConstants.Headers.api_key).
                when().
                get(String.format(RestConstants.Paths.Buildgroup.CLEAR_EXISTED_GROUP,RestConstants.Body.BuildGroups.TestedBuildgroup)).
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK).body(containsString(RestConstants.Body.BuildGroups.TestedBuildgroup)).body(containsString("cleaned"));


        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.GENERATEDAPIKEY, RestConstants.Headers.api_key);
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.REQUIREDAPIKEYFORACCESS, "0");
        winService.restartService(StaticDataProvider.WindowsServices.COORD_SERVICE);

    }

    @Test(testName = "ClearNonExistedGroup",priority =7)
    public void clearNonExistedGroup()  {
        get(String.format(RestConstants.Paths.Buildgroup.CLEAR_EXISTED_GROUP,RestConstants.Body.BuildGroups.TestedNonExistentBuildgroup)).
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK).
                body(containsString(RestConstants.Body.BuildGroups.TestedNonExistentBuildgroup)).body(containsString("Not found"));

    }

    //-----------------------------Add agents to group 'Name_Group_Name'---------------------------------------------
    @Test(testName = "VerifyErrorMassageaWhenAddMultipleHelpersToGroupName",priority =8)
    public void verifyErrorMassageaWhenAddMultipleHelpersToGroupName()  {

        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.REQUIREDAPIKEYFORACCESS, "1");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.GENERATEDAPIKEY, RestConstants.Headers.api_key);
        winService.restartService(StaticDataProvider.WindowsServices.COORD_SERVICE);
        given().
                contentType("application/json").
                body(RestConstants.Body.Buildgroup.BodyContentMultiplehelpers).
                when().
                post(String.format("https://%s:31100/Groups/%s/AddAgents",RestConstants.Body.CoordinatorIps.ip,RestConstants.Body.BuildGroups.TestedBuildgroup)).
                then().
                assertThat().
                statusCode(HttpStatus.SC_FORBIDDEN);

    }

    @Test(testName = "VerifyResponseIsBackAfterSupplyingApiKeyMultipleHelpers",priority =9)
    public void verifyResponseIsbackAfterSupplyingApiKeyCleanGroupMultipleHelpers()  {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.REQUIREDAPIKEYFORACCESS, "1");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.GENERATEDAPIKEY, RestConstants.Headers.api_key);
        winService.restartService(StaticDataProvider.WindowsServices.COORD_SERVICE);

        given().
                header("api-key",RestConstants.Headers.api_key).
                when().
                get(RestConstants.Paths.Buildgroup.DEFAULT_GROUP_LIST).
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK);

        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.GENERATEDAPIKEY, RestConstants.Headers.api_key);
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, StaticDataProvider.Locations.IB_REG_ROOT + "\\Coordinator", StaticDataProvider.RegistryKeys.REQUIREDAPIKEYFORACCESS, "0");
        winService.restartService(StaticDataProvider.WindowsServices.COORD_SERVICE);
    }

    @Test(testName = "AddMultipleHelpersToGroupName",priority =10)
    public void addMultipleHelpersToGroupName()  {
        given().
                contentType("application/json").
                body(RestConstants.Body.Buildgroup.BodyContentMultiplehelpers).
                when().
                post(String.format("https://%s:31100/Groups/%s/AddAgents",RestConstants.Body.CoordinatorIps.ip,RestConstants.Body.BuildGroups.TestedBuildgroup)).
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK);

        List <String> groups = get(RestConstants.Paths.Buildgroup.DEFAULT_GROUP_LIST).
                then().
                extract().
                path("Group");
        List <String> statuses= get(RestConstants.Paths.Buildgroup.DEFAULT_GROUP_LIST).
                then().
                extract().
                path("Status");
        Assert.assertTrue(groups.contains(RestConstants.Body.BuildGroups.DefaultBuildgroup) && statuses.contains("Not found"), "failed to add  all helpers  " + RestConstants.Body.HelpersNames.HelperName  + " and "  +RestConstants.Body.CoordinatorsNames.CoordinatorName + " to group " + RestConstants.Body.BuildGroups.TestedBuildgroup);

    }








}
