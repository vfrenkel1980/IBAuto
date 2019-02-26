package frameworkInfra.utils;

import com.aventstack.extentreports.Status;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinReg.HKEY;
import frameworkInfra.testbases.TestBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;


import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import static frameworkInfra.Listeners.SuiteListener.test;

/**
 * Static class that is all about performing registry actions
 */

public class RegistryService extends TestBase {

    public static void setRegistryKey(HKEY rootKey, String keyPath, String keyName, String value) {
        if (test != null)
            test.log(Status.INFO, "Setting " + keyName + " value to " + value);
        try {
            Advapi32Util.registrySetStringValue(rootKey, keyPath, keyName, value);
        } catch (Exception ex) {
            test.log(Status.ERROR, "Unable to set " + keyName + " with value " + value);
            ex.getMessage();
        }
    }

    public static String getRegistryKey(HKEY rootKey, String keyPath, String keyName) {
        try {
            return Advapi32Util.registryGetStringValue(rootKey, keyPath, keyName);
        } catch (Exception ex) {
            test.log(Status.ERROR, "Failed to get value for " + keyName + "with error: " + ex.getMessage());
            return "";
        }
    }

    public static void createRegValue(HKEY rootKey, String keyPath, String keyName, String value) {
        if (test != null)
            test.log(Status.INFO, "Creating " + keyName + ". Setting value to " + value);
        try {
            Advapi32Util.registrySetStringValue(rootKey, keyPath, keyName, value);
        } catch (Exception ex) {
            test.log(Status.ERROR, "Failed to create new registry key with error: " + ex.getMessage());
            ex.getMessage();
        }
    }

    public static void addRegKeyValue(HKEY rootKey, String keyPath, String keyName, String required) {
        if (test != null)
            test.log(Status.INFO, "Adding " + required + "value to " + keyName);
        String values = getRegistryKey(rootKey, keyPath, keyName);
        if (!values.contains(required)) {
            values = values.isEmpty() ? required : values + "," + required;
            setRegistryKey(rootKey, keyPath, keyName, values);
        }

    }

    public static void deleteRegKey(HKEY rootKey, String keyPath, String keyName) {
        if (rootKey == null || keyPath == null || keyName == null) {
            return;
        }
        if (test != null) {
            test.log(Status.INFO, "Deleting " + keyName);
        }
        try {
            String newKeyPath = keyPath + "\\" + keyName;
            String[] subKeys = Advapi32Util.registryGetKeys(rootKey, newKeyPath);
            if (subKeys.length != 0) {
                for (String subKey : subKeys) {
                    deleteRegKey(rootKey, newKeyPath, subKey);
                }
            }
            Advapi32Util.registryDeleteKey(rootKey, keyPath, keyName);
            test.log(Status.INFO, "RegKey is deleted");

        } catch (RuntimeException ex) {
            test.log(Status.ERROR, "Failed to delete registry key with error: " + ex.getMessage());
        }
    }

    public static void removeRegKeyValue(HKEY rootKey, String keyPath, String keyName, String required) {
        if (test != null)
            test.log(Status.INFO, "Removing " + required + " value from " + keyName);
        String values = getRegistryKey(rootKey, keyPath, keyName);
        List<String> res = new ArrayList(Arrays.asList(values.split(",")));
        if (res.remove(required)) {
            StringJoiner joiner = new StringJoiner(",");
            res.forEach(joiner::add);
            setRegistryKey(rootKey, keyPath, keyName, joiner.toString());
        }
    }

    public static boolean doesValueExist(HKEY rootKey, String keyPath, String keyName) {
        return Advapi32Util.registryValueExists(rootKey, keyPath, keyName);
    }

    public static boolean doesKeyExist(HKEY rootKey, String keyPath) {
        return Advapi32Util.registryKeyExists(rootKey, keyPath);
    }

    public static void createRootRegistryFolder(HKEY rootKey, String path) {
        if (!doesKeyExist(HKEY_LOCAL_MACHINE, path)) {
            try {
                Advapi32Util.registryCreateKey(rootKey, path);
            } catch (Win32Exception e) {
                e.getMessage();
                test.log(Status.ERROR, "Failed to create new key folder in " + path);
            }
        }
    }
}
