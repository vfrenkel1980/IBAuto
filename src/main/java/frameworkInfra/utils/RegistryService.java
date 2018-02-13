package frameworkInfra.utils;

import com.aventstack.extentreports.Status;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg.HKEY;
import frameworkInfra.testbases.TestBase;

public class RegistryService extends TestBase{

    public static void setRegistryKey(HKEY rootKey, String keyPath, String keyName, String value){
        test.log(Status.INFO, "Setting " + keyName + " value to " + value);
        try {
            Advapi32Util.registrySetStringValue(rootKey, keyPath, keyName,value);
        } catch (Exception ex){
            test.log(Status.ERROR, "Unable to set " + keyName + " with value " + value);
            ex.getMessage();
        }
    }

    public static String getRegistryKey(HKEY rootKey, String keyPath, String keyName){
        try {
            return Advapi32Util.registryGetStringValue(rootKey, keyPath, keyName);
        } catch (Exception ex){
            test.log(Status.ERROR, "Failed to get value for " + keyName);
            ex.getMessage();
            return "";
        }
    }
}
