package frameworkInfra.utils;
import java.util.Properties;

@Deprecated
public class ConfigurationReader {

    private Properties configFile;

    public ConfigurationReader()
    {
        configFile = new java.util.Properties();
        try {
            configFile.load(this.getClass().getClassLoader().
                    getResourceAsStream("Configuration/Config.cfg"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getProperty(String key)
    {
        return this.configFile.getProperty(key);
    }
}
