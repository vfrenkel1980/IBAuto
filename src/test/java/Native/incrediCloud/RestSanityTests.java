package Native.incrediCloud;

import frameworkInfra.testbases.incrediCloud.RestSanityTestBase;
import static io.restassured.RestAssured.*;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;


public class RestSanityTests extends RestSanityTestBase {

    @Test(testName = "Create Policy")
    public void createPolicy() {
        Map<String, Object> jsonAsMap = new HashMap<>();

        jsonAsMap.put("cordId", cordId);
        jsonAsMap.put("cloudToken", "124342314");
        jsonAsMap.put("cloudType", "Azure");
        jsonAsMap.put("subscriptionId", "82231c1b-436c-4e24-95d7-9092bf797856");
        jsonAsMap.put("tenantId", "bde8b775-ae5e-4043-bd01-ab0b17249045");
        jsonAsMap.put("location", "West Europe");
        jsonAsMap.put("timeOut", 140);
        jsonAsMap.put("vmType", "Standard_D2_v2");
        jsonAsMap.put("ibVer", 1);
        jsonAsMap.put("ports", 34001);
        jsonAsMap.put("poolSize", 1);
        given().
                body(jsonAsMap).
        when().
                post("/policy/create").
                peek().
        then().
                statusCode(200);
    }

    @Test(testName = "Set Provisioning")
    public void setProvisioning() {
        Map<String, Object> jsonAsMap = new HashMap<>();

        jsonAsMap.put("cordId", cordId);
        jsonAsMap.put("coreReq", 4);
        jsonAsMap.put("VMAgentType", 2);
        given().
                body(jsonAsMap).
        when().
                post("/provision/setProvisioning/").
                peek().
        then().
                statusCode(200);
    }

    @Test(testName = "Set Machine delivered")
    public void setMachineDelivered() {
        Map<String, Object> jsonAsMap = new HashMap<>();

        jsonAsMap.put("cordId", cordId);
        jsonAsMap.put("vmName", cordId + "incredibuild0");
        given().
                body(jsonAsMap).
        when().
                post("/provision/setProvisioningMachineDelivered/").
                peek().
        then().
                statusCode(200);
    }

    @Test(testName = "Get Status Queue All")
    public void getStatusQueueAll() {
        when().
                get("/provision/getStatusQueue/123/" + cordId + "/true").
                peek().
        then().
                statusCode(200);
    }

    @Test(testName = "Get Status Queue")
    public void getStatusQueue() {
        when().
                get("/provision/getStatusQueue/123/" + cordId + "/false").
                peek().
        then().
                statusCode(200);
    }

    @Test(testName = "Set Helper Release")
    public void setHelperRelease() {
        Map<String, Object> jsonAsMap = new HashMap<>();

        jsonAsMap.put("cordId", cordId);
        jsonAsMap.put("resourceName", cordId + "incredibuild0");
        jsonAsMap.put("releaseReason", 0);
        given().
                body(jsonAsMap).
                when().
        post("/provision/setHelperRelease/").
                peek().
        then().
                statusCode(200);
    }

    @Test(testName = "Delete All Resources")
    public void deleteAllResources() {
        Map<String, Object> jsonAsMap = new HashMap<>();

        jsonAsMap.put("cordId", cordId);
        given().
                body(jsonAsMap).
        when().
                delete("/provision/deleteAllResources").
                peek().
        then().
                statusCode(200);
    }

}
