package webInfra.rest.utils;

import com.github.jaiimageio.impl.common.PackageUtil;
import frameworkInfra.utils.RegistryService;
import frameworkInfra.utils.StaticDataProvider;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class RestConstants {

    public static final class Paths {
        public static final class Buildgroup {
            public static final String ROOT_PATH = "https://%s:31100/"; //"https://192.168.10.233:31100/"
            public static final String GROUP_LIST_PATH = "Groups/list";
            public static final String GROUP_DEFAULT_LIST_PATH = "Groups/list";
        }



    }
    public static final class Body {
        public static final class Buildgroup {
            public static final String BodyContent = "[{\"IP\": \"192.168.10.243\",\"Name\": \"WINDOWS-QA-2\"}]";
            public static final String GROUP_LIST_PATH = "Groups/list";
            public static final String GROUP_DEFAULT_LIST_PATH = "Groups/list";
        }



    }



    public static final class ResponseKeys {
        public static final class Buildgroup {
            public static final String key_group = "https://%s:31100/"; //"https://192.168.10.233:31100/"
            public static final String GROUP_LIST_PATH = "Groups/list";
            public static final String GROUP_DEFAULT_LIST_PATH = "Groups/list";
        }

    }

    public static final class ReturnCodes {
        public static final int SUCCESS = 200;

    }

    public static final class Certificates {
        public static final class BuildGroup {
            public static final String PROPERTY_TRUSTSTORE = "javax.net.ssl.trustStore";
            public static final String PROPERTY_TRUSTSTORE_PASSWORD = "javax.net.ssl.trustStorePassword";
            public static final String PROPERTY_TRUSTSTORE_VALUE = "C:\\Users\\VladFrenkel\\.keystore";
            public static final String PROPERTY_TRUSTSTORE_PASSWORD_VALUE = "incredibuild";

        }
    }

    public static final class ErrorMessages {
        public static final class Rest {
            public static final String INCORRECT_RETURNED_CODE = "The returned code is not as expected! Expected: %s, Actual: %s";
        }
    }




}
