package cloudInfra.CloudHelper;

import frameworkInfra.Listeners.SuiteListener;
import org.testng.annotations.Listeners;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class CloudHelper {

    //TODO: create list of azure vm classes
    //TODO: create list of aws vm classes

    protected static String NUMOFMACHINES = System.getProperty("numofmachines");

    public void init(){
        System.out.println(NUMOFMACHINES);
    }
}
