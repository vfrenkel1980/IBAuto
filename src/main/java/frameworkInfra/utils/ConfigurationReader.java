package frameworkInfra.utils;
import java.util.Properties;

public class ConfigurationReader {

    private Properties configFile;

    public ConfigurationReader()
    {
        configFile = new java.util.Properties();
        try {
            configFile.load(this.getClass().getClassLoader().
                    getResourceAsStream("SimulationConfiguration/Config.cfg"));
        }catch(Exception eta){
            eta.printStackTrace();
        }
    }

    public String getProperty(String key)
    {
        return this.configFile.getProperty(key);
    }
}
