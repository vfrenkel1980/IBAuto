package cloudInfra.IncrediCloud.incrediCloudService;

import frameworkInfra.testbases.incrediCloud.ICEngineTestBase;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;
import frameworkInfra.utils.StaticDataProvider.*;
import frameworkInfra.utils.SystemActions;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class IncrediCloudService implements IIncrediCloudService{

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
        RestAssured.baseURI = "https://incredicloudapim-prod.azure-api.net";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Content-Ocp-Apim-Trace", "true");
        headers.put("Ocp-Apim-Subscription-Key", "1ba49e574ae44ce289d22542c07ff190");
        RestAssured.requestSpecification = new RequestSpecBuilder().addHeaders(headers).build();
    }

    @Override
    public void loginToCloud() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        initRest();

        jsonAsMap.put("coordId", coordId);
        jsonAsMap.put("secret", secret);
        token = given().
                body(jsonAsMap).
        when().
                post("/auth/login").
                peek().
        then().
                statusCode(200).
        extract().
                path("token");
    }

    @Override
    public void refreshToken() {
        Map<String, Object> jsonAsMap = new HashMap<>();

        jsonAsMap.put("cordId", coordId);
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
    }

    //true - get number of delivered machines
    //false - get all undelivered
    @Override
    public int getStatusQueue(boolean getDelivered) {
        int count = 0;
        refreshToken();
        Response response = given().
                header("Authorization", "bearer " + token).
                when().
                get("/provision/getStatusQueue/" + coordId + "/true").
                peek().
                then().
                statusCode(200).
                extract().
                response();
        List<Boolean> delivered = response.path("resourceDetails.isDelivered");
        if(getDelivered) {
            for (boolean del : delivered) {
                if (del)
                    count++;
            }
        }
        else{
            count = delivered.size();
        }
        return count;
    }

    @Override
    public boolean waitForDeliveredMachines(int numOfMachines) {
        int time = 0;
        int wait = 900;
        while (time != wait){
            if (getStatusQueue(true) == numOfMachines)
                return true;
            time += 10;
            SystemActions.sleep(10);
        }
        return false;
    }

    @Override
    public void setSecretInRegistry() {
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.INCREDICLOUDSECRET, secret);
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.INCREDICLOUDSTATE, "1");
        RegistryService.setRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\Coordinator", RegistryKeys.INCREDICLOUDCOORDID, coordId);
    }

}
