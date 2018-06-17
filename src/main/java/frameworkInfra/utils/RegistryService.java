package frameworkInfra.utils;

import com.aventstack.extentreports.Status;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg.HKEY;
import frameworkInfra.testbases.TestBase;

import static frameworkInfra.Listeners.SuiteListener.test;

public class RegistryService extends TestBase{

    public static void setRegistryKey(HKEY rootKey, String keyPath, String keyName, String value){
        if (test != null)
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

    public static void createRegKey(HKEY rootKey, String keyPath, String keyName, String value){
        if (test != null)
            test.log(Status.INFO, "Creating " + keyName + ". Setting value to " + value);
        try{
            Advapi32Util.registryCreateKey(rootKey, keyPath, keyName);
            Advapi32Util.registrySetStringValue(rootKey, keyPath, keyName,value);
        }catch (Exception ex){
            test.log(Status.ERROR, "Failed to create new registry key with error: " + ex.getMessage());
            ex.getMessage();
        }
    }

    public static boolean doesValueExist(HKEY rootKey, String keyPath, String keyName){
        return Advapi32Util.registryValueExists(rootKey, keyPath, keyName);
    }
}
