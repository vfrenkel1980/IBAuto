package webInfra.RestCalls.Get;

import com.aventstack.extentreports.Status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static frameworkInfra.Listeners.SuiteListener.test;

public class GetIsMailRegistered {

    /**
     * Static method for verifying if the mail is registered to IB
     * @param email mail address to verify
     * @return true/false
     */
    public static boolean isMailRegistered(String email){

        try {
            URL url = new URL("https://en-site.incredibuild.com/ibonlinestore/authentication/verifyUniqueEmail/" + email);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

            if (conn.getResponseCode() != 200) {
                test.log(Status.ERROR, "Fail to connect with error code " + conn.getResponseCode());
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                if (output.equals("true")) {
                    test.log(Status.INFO, "Mail is registered");
                    conn.disconnect();
                    return true;
                }
            }

            test.log(Status.INFO, "Mail is not registered");
            conn.disconnect();
            return false;
        } catch (IOException e) {
            e.getMessage();
            return false;
        }
    }
}
