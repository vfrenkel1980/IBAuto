package cloudInfra.IncrediCloud.Utils;

import cloudInfra.IncrediCloud.metadata.Configuration.CloudConfigurationData;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CloudUtil {
    private static final String CONFIGURATION_DATA_FILEPATH = "C:\\qa_automation\\src\\test\\resources\\IC\\Configuration\\CloudConfiguration.json";

    /**
     * @return
     * @throws Exception
     */
    public static CloudConfigurationData getCloudConfigurationData() throws Exception {
        ObjectMapper mapper;
        String readContent;
        try {
            mapper = new ObjectMapper();
            readContent = new String(Files.readAllBytes(Paths.get(CONFIGURATION_DATA_FILEPATH)));
        } catch (IOException e) {
            throw new Exception("Failed to read Cloud configuration data file! Error: " + e);
        }

        CloudConfigurationData configurationMetadata;
        try {
            configurationMetadata = mapper.readValue(readContent, CloudConfigurationData.class);
        } catch (IOException e) {
            throw new Exception("Failed to extract Cloud configuration data to an object! Error: " + e);
        }

        return configurationMetadata;
    }
}
