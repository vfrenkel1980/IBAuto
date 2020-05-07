package cloudInfra.IncrediCloud.metadata.Configuration;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class CommonConfigurationData {
    @JsonProperty()
    public String firstName;

    public String lastName;
    public String email;
    private String company;
    private int coresLimit;
    private int poolSize;
    private int coordPort;
    private int vmPort;
    private HashMap<String,String> customTags;

    private String region;
    private String machineType;
    private int timeout;



}