package webInfra.rest.utils;

import io.restassured.RestAssured;
import webInfra.rest.utils.RestConstants;

public class RestUtils {

    public static  void initCertificateForBuildService()
    {
        System.setProperty(RestConstants.Certificates.BuildGroup.PROPERTY_TRUSTSTORE, RestConstants.Certificates.BuildGroup.PROPERTY_TRUSTSTORE_VALUE);
        System.setProperty(RestConstants.Certificates.BuildGroup.PROPERTY_TRUSTSTORE_PASSWORD,RestConstants.Certificates.BuildGroup.PROPERTY_TRUSTSTORE_PASSWORD_VALUE );
        RestAssured.keyStore(RestConstants.Certificates.BuildGroup.PROPERTY_TRUSTSTORE_VALUE, RestConstants.Certificates.BuildGroup.PROPERTY_TRUSTSTORE_PASSWORD_VALUE );
    }
}