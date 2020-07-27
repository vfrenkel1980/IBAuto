package Native.resttests;

import frameworkInfra.testbases.rest.BuildGroupRestTestsBase;
import webInfra.rest.buildgroup.metadata.AddAgentsData;
import webInfra.rest.utils.RestConstants;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

//import static io.restassured.RestAssured.when;

public class BuildGroupRestTests  extends BuildGroupRestTestsBase {

    /**
     * @test Build Groups. REST calls 1. Get Groups list<br>
     * @pre{ you should  have machine with coordinator  and "n" agents as helpers }
     * @steps { - run api  in order to receive  count of agents  and  group for example ("https://192.168.10.233:31100/Groups/list") }
     * @result{ - counts of agents ="n"",group ="Default"}
     */

    @Test(testName = "GetGroupList")
    public void getGroupList()  {

        //verify that returned status os 200 !!!
        get(RestConstants.Paths.Buildgroup.GROUP_LIST_PATH).
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK);

        // get(RestConstants.Paths.Buildgroup.GROUP_LIST_PATH).then().assertThat().statusCode(HttpStatus.SC_OK).body("AgentCount",equalTo("4")).body("Group",equalTo("Default"));


        String  responseString = get(RestConstants.Paths.Buildgroup.GROUP_LIST_PATH).then().assertThat().statusCode(HttpStatus.SC_OK).extract().asString();
        //fetch AgentCount
        String  expectedAgentCount ="\"AgentCount\":2";
        Assert.assertTrue(responseString.contains(expectedAgentCount),"Agent count is not equal to  " + expectedAgentCount);
        //fetch BuildGroup
        String expectedBuildGroup = "\"Group\":\"Default\"";
        Assert.assertTrue(responseString.contains(expectedBuildGroup),"Build group is not equal to   " + expectedBuildGroup);


        String expectedBuildGroupvlad = "\"Group\":\"Default\"";

    }

    @Test(testName = "AddHelperToBuildGroup")
    public void addHelperToBuildGroup()  {

        AddAgentsData aad = new AddAgentsData();
        aad.setIP("192.168.10.243");
        aad.setName("WINDOWS-QA-2");//WINDOWS-QA-2

        AddAgentsData[] arr = new AddAgentsData[1];
        arr[0]= aad;

       // String data [] = {"\"IP\": \"192.168.10.243\"","\"Name\": \"WINDOWS-QA-2\""};

//        given().
//                contentType("application/json").
//                body("[{\"IP\": \"192.168.10.243\",\"Name\": \"WINDOWS-QA-2\"}]").
//                when().
//                post("https://10.10.10.9:31100/Groups/Vlad_group/AddAgents").
//                then().
//                assertThat().
//                statusCode(HttpStatus.SC_OK);


        given().
                contentType("application/json").
                body(RestConstants.Body.Buildgroup.BodyContent).
                when().
                post("https://10.10.10.9:31100/Groups/Vlad_group/AddAgents").
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK);


      String  responseString = get(RestConstants.Paths.Buildgroup.GROUP_LIST_PATH).then().assertThat().statusCode(HttpStatus.SC_OK).extract().asString();


    }





}
