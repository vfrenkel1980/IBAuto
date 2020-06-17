package cloudInfra.IncrediCloud.incrediCloudService;

import cloudInfra.IncrediCloud.metadata.VirtualMachineInfo;
import com.aventstack.extentreports.Status;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider.Locations;
import frameworkInfra.utils.StaticDataProvider.RegistryKeys;
import frameworkInfra.utils.SystemActions;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.util.*;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;
import static frameworkInfra.testbases.incrediCloud.ICEngineTestBase.ENV;
import static io.restassured.RestAssured.given;


public class IncrediCloudService implements IIncrediCloudService {

    //    private String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJBY2Nlc3NLZXlJZCI6IkFTSUE0QkhJTzJKQkxUVDM3SlhHIiwiU2VjcmV0QWNjZXNzS2V5IjoiY3BMdzVJTDRVUUZYNERKVTNsTGxvV1kwMTE5aGZ2NVpnUENBMEVXMCIsIlNlc3Npb25Ub2tlbiI6IkZ3b0daWEl2WVhkekVKUC8vLy8vLy8vLy93RWFEQ2NZWjFLR3lDUDIxbXo1SHlLd0FUbHlwS1pXVEx4SGZ2ajJXU01rWG5hSGdSNmpaV2YrNFpnQ2o2dnNPOFY3YUhJNUluRmdBQzl2bGpKWFVpRDVHV0lDeFhzc0hHZk5WUHg4bVlTaGpBTFo1T05UOFhvK01jSUNTSFhGZ3hycjQrZ3V3TGRzbEQ2RkxrNDlJWGphTUM5UzQ2bFJJZjhHejRqeUN6ZlJicHBnOTc5UjEvRVF0bCtIYmhUb3UvOFVaZ2JhQWRQa3BYRlN2R1FQRkJCdmFUdmRDeXowOHM3N0ZlZEJSRWlmWFVpakJkQ09JTVdzcFY5bDlUZ2ZqNlNES0lQUXB2UUZNaTJ3K2xpWnhUd2Z1b2Jwd0dSc1ZubGZRZWNOWTZsbkRXYkpRMWZyVjhvRmladHFGRmJWaUc1STBYUUFvZ2c9IiwiRXhwaXJhdGlvbiI6IjIwMjAtMDQtMDVUMTA6NDI6MjcuMDAwWiIsIkV4dGVybmFsSWQiOiI3YmY1YTg5MC03NzFmLTExZWEtYWJmNy1lM2IzZjI5OGRiZTMiLCJpYXQiOjE1ODYwNzk3NDd9.VGQPF9YlriBkaRC6wSDFdU3dBU0V9n2XoG-IJEAv0a8";
//    private String coordId = "Automation";
//    private String secret = "ZjhiNmZlZDktZmFjYi00MDcxLTk5YTQtMWFmYzcyMzRhZGFm";
    private String token;
    private String coordId;
    private String secret;

    public IncrediCloudService(String coordId) {
        this.coordId = coordId;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public void initRest() {
        test.log(Status.INFO, "Initializing REST headers");
        switch (ENV) {
            case "prod":
                RestAssured.baseURI = "https://incredicloudapim-prod.azure-api.net";
                break;
            case "uat":
                RestAssured.baseURI = "https://incredicloudapigwtest.azure-api.net";
                break;
            case "aws":
                RestAssured.baseURI = "https://incredicloudapim-aws.azure-api.net";
                break;
            case "dev":
                RestAssured.baseURI = "https://incredicloudapigwdev.azure-api.net";
                break;
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Content-Ocp-Apim-Trace", "true");
        headers.put("Ocp-Apim-Subscription-Key", "1ba49e574ae44ce289d22542c07ff190");
        RestAssured.requestSpecification = new RequestSpecBuilder().addHeaders(headers).build();
        test.log(Status.INFO, "Initializing REST headers COMPLETE");
    }

    @Override
    public void loginToCloud() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        initRest();
        test.log(Status.INFO, "Performing REST login to cloud");

        jsonAsMap.put("coordId", coordId);
        jsonAsMap.put("secret", secret);
        System.out.println("---------- Secret: ----------");
        System.out.println(secret);
        System.out.println("-----------------------------");
        token = given().
                body(jsonAsMap).
                when().
                post("/auth/login").
                peek().
                then().
                statusCode(200).
                extract().
                path("token");
        test.log(Status.INFO, "REST login to cloud successful");
    }

    @Override
    public void refreshToken() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        test.log(Status.INFO, "Refreshing cloud Token");
        jsonAsMap.put("coordId", coordId);
        jsonAsMap.put("token", token);

        token = given().
                body(jsonAsMap).
                when().
                post("/auth/refreshToken").
                peek().
                then().
                statusCode(200).
                extract().
                path("token");
        test.log(Status.INFO, "Cloud Token refreshed successfully");
    }

    //true - get number of delivered machines
    //false - get all undelivered
    @Override
    public int getStatusQueue(boolean getDelivered) {
        int count = 0;
        List<Boolean> delivered = null;
        test.log(Status.INFO, "Running GetStatusQueue");

        Response response = given().
                header("Authorization", "bearer " + token).
                when().
                get("/provision/getStatusQueue/" + coordId + "/true").
                peek().
                then().
                extract().
                response();
        try {
            delivered = response.path("resourceDetails.isDelivered");
        } catch (Exception e) {
            delivered = response.path("resourceDetails.isDelivered");
        }
        if (getDelivered) {
            for (boolean del : delivered) {
                if (del)
                    count++;
            }
        } else {
            count = delivered.size();
        }
        test.log(Status.INFO, "GetStatusQueue finished successfully with " + count + " machines");
        return count;
    }

    @Override
    public boolean waitForDeliveredMachines(int numOfMachines) {
        int time = 0;
        int wait = 900;
        refreshToken();
        while (time != wait) {
            if (getStatusQueue(true) == numOfMachines)
                return true;
            time += 10;
            SystemActions.sleep(10);
        }
        test.log(Status.ERROR, "Number of delivered machines not met. Expected: " + numOfMachines + ", Actual: " + getStatusQueue(true));
        return false;
    }

    @Override
    public void deactivateCloud() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        test.log(Status.INFO, "Performing REST Deactivate to cloud");

        jsonAsMap.put("coordId", coordId);
        given().
                header("Authorization", "bearer " + token).
                body(jsonAsMap).
                when().
                delete("/provision/deleteAllResources").
                peek().
                then().
                statusCode(200);
        test.log(Status.INFO, "REST deactivate to cloud successful");
    }

    @Override
    public String getCloudStatus() {
        return given().
                header("Authorization", "bearer " + token).
                when().
                get("/provision/getStatusQueue/" + coordId + "/true").
                peek().
                then().
                extract().
                path("resultMessage");
    }

    @Override
    public boolean waitForCloudStatus(String status) {
        int time = 0;
        int wait = 500;
//        try {
//            refreshToken();
//        } catch (Exception e) {
//            return true;
//        }

        while (time != wait) {
            if (getCloudStatus().equals(status)) {
                test.log(Status.INFO, "Cloud is in " + status + " status");
                return true;
            }
            time += 10;
            SystemActions.sleep(10);
        }
        test.log(Status.INFO, "After waiting 15 minutes, failed to get " + status + " status");
        return false;
    }

    @Override
    public void setSecretInRegistry() {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.INCREDICLOUDSECRET, secret);
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.INCREDICLOUDSTATE, "1");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.INCREDICLOUDCOORDID, coordId);
    }

    @Override
    public VirtualMachineInfo getVirtualMachineInformation(String machineName) {
        Response response = given().
                header("Authorization", "bearer " + token).
                when().
                get(String.format("/provision/getMachineForTest/%s/%s", coordId, machineName)).
                peek().
                then().
                extract().
                response();
        VirtualMachineInfo vmi = new VirtualMachineInfo();

        // Hardware Profile
        HashMap hardwareProfile = response.path("virtualMachine.hardwareProfile");
        vmi.setVmSize(hardwareProfile.get("vmSize").toString());

        // Tags
        HashMap tags = response.path("virtualMachine.tags");
        Iterator iterator = tags.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iterator.next();
            String key = (String) mapEntry.getKey();
            if (key.equals("machine_delivered") ||
                    key.equals("agent_type") ||
                    key.equals("index")) {
                continue;
            }
            vmi.getTags().put(key, (String) mapEntry.getValue());
        }
        return vmi;
    }

    @Override
    public ArrayList<String> getVirtualMachinesNames() {
        ArrayList<String> vmNames = null;
        test.log(Status.INFO, "Calling getCoordMachineNames request");

        Response response = given().
                header("Authorization", "bearer " + token).
                when().
                get("/provision/getCoordMachineNames/" + coordId).
                peek().
                then().
                extract().
                response();
        try {
            vmNames = response.path("machineNames");
        } catch (Exception e) {
            vmNames = response.path("machineNames");
        }
        test.log(Status.INFO, "getVirtualMachinesNames done.");
        return vmNames;
    }

//    @Override
//    public ArrayList<String> getVirtualMachinesNames() {
//        ArrayList<String> vmNames = null;
//        test.log(Status.INFO, "Calling GetStatusQueue request");
//
//        Response response = given().
//                header("Authorization", "bearer " + token).
//                when().
//                get("/provision/getStatusQueue/" + coordId + "/true").
//                peek().
//                then().
//                extract().
//                response();
//        try {
//            vmNames = response.path("resourceDetails.name");
//        } catch (Exception e) {
//            vmNames = response.path("resourceDetails.name");
//        }
//        test.log(Status.INFO, "getVirtualMachinesNames done.");
//        return vmNames;
//    }
}
