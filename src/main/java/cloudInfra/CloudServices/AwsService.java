package cloudInfra.CloudServices;

import com.amazonaws.services.dynamodbv2.xspec.NULL;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import com.aventstack.extentreports.Status;
import com.microsoft.azure.management.compute.VirtualMachineSizeTypes;
import frameworkInfra.utils.StaticDataProvider;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import static frameworkInfra.Listeners.SuiteListener.test;

public class AwsService extends CloudService{

    static protected AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

    protected static String security_group = "sg-0d0aa3f9935fe35d2";
    protected static String subnet = "subnet-54243122";
    protected static String keyName = "Q/A";

//    protected String instance_id_coord = "i-0a415b21b10fe3db0";
//    protected String ami_id_helper = "ami-00183381177c699e5";
//    protected String ami_id_perf = "ami-0179809c723b639f4";
//    protected String ami_id_coord = "ami-0969a81aaa01786e6";
//    protected String helper_type = "t2.xlarge";
//    protected String initiator_type = "c4.4xlarge";
//  private String test_type = "t1.micro";

//    protected static String and5_inst_id = "i-09b17d03429b1a25d";
//    protected static String and6_inst_id = "i-0e4c64ed1a75fe883";
//    protected static String and7_inst_id = "i-06befc808b5caa7f1";
//    protected static String and8_inst_id = "i-04bbfcaafb96bde38";
//    protected static String and9_inst_id = "i-0a55ca9b6c3819b85";
//    protected static String coord_publicIP_AWS = "63.34.106.126";

    protected  int vmCount;
    protected  Vector<String> initiators_vec = new Vector<String>();
    protected  InstanceType instanceType;
    protected Vector<String> helper_ids_vec = new Vector<String>();
//    protected Vector<String> initiator_ids_vec = new Vector<String>();
    protected static HashMap<String, String> init_inst_id_Map = new HashMap<>();

    static {
//        init_ip_Map.put("0", StaticDataProvider.LinuxAWS.coord_publicIP_AWS);
//        init_ip_Map.put("1", StaticDataProvider.LinuxAWS.init_perf_publicIP_AWS);
//        init_ip_Map.put("2", "i-034a1758bf2a100f");
//        init_ip_Map.put("3", "i-034a1758bf2a100f");
//        init_ip_Map.put("5", StaticDataProvider.LinuxAWS.and5_publicIP_AWS);
//        init_ip_Map.put("6", StaticDataProvider.LinuxAWS.and6_publicIP_AWS);
//        init_ip_Map.put("7", StaticDataProvider.LinuxAWS.and7_publicIP_AWS);
//        init_ip_Map.put("8", StaticDataProvider.LinuxAWS.and8_publicIP_AWS);
//        init_ip_Map.put("9", StaticDataProvider.LinuxAWS.and9_publicIP_AWS);

        init_inst_id_Map.put("0", "i-0a415b21b10fe3db0"); //coord inst id
        init_inst_id_Map.put("1", "i-034a1758bf2a100f1");
        init_inst_id_Map.put("2", "i-034a1758bf2a100f1");
        init_inst_id_Map.put("3", "i-034a1758bf2a100f1");
        init_inst_id_Map.put("5", "i-09b17d03429b1a25d");
        init_inst_id_Map.put("6", "i-0e4c64ed1a75fe883");
        init_inst_id_Map.put("7", "i-06befc808b5caa7f1");
        init_inst_id_Map.put("8", "i-09b17d03429b1a25d");
        init_inst_id_Map.put("9", "i-0a55ca9b6c3819b85");
    }

    public AwsService(String cpu, String memory, String vmCount, String initiators) {

       // test.log(Status.INFO, "AwsService construtor");
        log.info("starting AwsService construtor");
        this.vmCount = Integer.parseInt(vmCount);
        String[] init_arr = initiators.split(", ");

        for(String initiator: init_arr)
            initiators_vec.add(initiator);

       instanceType = InstanceType.T2Xlarge; //default helper

       if (cpu.equals("2") && memory.equals("4"))
           this.instanceType = InstanceType.C1Medium.T2Medium;
       else if (cpu.equals("16") && memory.equals("30"))
           this.instanceType = InstanceType.C44xlarge;
   }

    @Override
    public void create() {
        log.info("create");
        for (int i = 0; i < vmCount; ++i) {
            RunInstancesRequest run_request = new RunInstancesRequest()
                    .withImageId(StaticDataProvider.LinuxAWS.ami_id_helper)
                    .withInstanceType(instanceType)
                    .withMinCount(1)
                    .withMaxCount(1)
                    .withKeyName(keyName)
                    .withSubnetId(subnet)
                    .withSecurityGroupIds(security_group);

            RunInstancesResult run_response = ec2.runInstances(run_request);
  //          String reservation_id = run_response.getReservation().getInstances().get(0).getInstanceId();
            helper_ids_vec.add(run_response.getReservation().getInstances().get(0).getInstanceId());

            boolean isInstUp = false;
            DescribeInstanceStatusResult status_result;
            do {
                status_result = ec2.describeInstanceStatus(new DescribeInstanceStatusRequest().withInstanceIds(helper_ids_vec.lastElement()));
                isInstUp = (status_result.getInstanceStatuses().size() > 0);
            } while (!isInstUp);
        }
    }

    private void startVm(String inst_id)
    {
        StartInstancesRequest stert_request = new StartInstancesRequest().withInstanceIds(inst_id);
        StartInstancesResult startInstancesResult =  ec2.startInstances(stert_request);

        boolean isInstUp = false;
        DescribeInstanceStatusResult status_result;
        do {
            status_result = ec2.describeInstanceStatus(new DescribeInstanceStatusRequest().withInstanceIds(inst_id));
            isInstUp = (status_result.getInstanceStatuses().size() > 0);
        } while (!isInstUp);
    }

    private void startInitiators()
    {
        String curr_inst_id = null;

        for(String curr_init_num: initiators_vec) {

            curr_inst_id = init_inst_id_Map.get(curr_init_num);
            if ( curr_inst_id != null)
               startVm(curr_inst_id);
        }
    }

    @Override
    public void startVm() {
        log.info("startVm");
        startVm(init_inst_id_Map.get(0));
        startInitiators();
    }

    private void stopVm(String inst_id)
    {
        StopInstancesRequest request = new StopInstancesRequest().withInstanceIds(inst_id);
        ec2.stopInstances(request);
    }

    @Override
    public void stopVm(){
        log.info("stopVm");

        stopVm(init_inst_id_Map.get(0));

        for (String currInit:initiators_vec)
            stopVm(init_inst_id_Map.get(currInit));
    }

    @Override
    public void deleteVm(){

//        test.log(Status.INFO, "deleteVmAWS");
        log.info("deleteVmAWS");
        if(!helper_ids_vec.isEmpty()) {
            TerminateInstancesRequest turm_Request = new TerminateInstancesRequest(helper_ids_vec);
            ec2.terminateInstances(turm_Request);
        }
    }

    public static String getIPVmAWS(String instance_id) {

        DescribeInstancesRequest describe_request = new DescribeInstancesRequest().withInstanceIds(instance_id);
        DescribeInstancesResult result = ec2.describeInstances(describe_request);
        String publicIP = result.getReservations().get(0).getInstances().get(0).getPublicIpAddress();
        return publicIP;
    }

    public static String getIPVfromNamemAWS(String name) {

        DescribeInstancesRequest describe_request = new DescribeInstancesRequest();
        List<String> valuess = new ArrayList<String>();
        valuess.add(name);

        Filter filter = new Filter("tag:Name", valuess);
        DescribeInstancesResult result = ec2.describeInstances(describe_request.withFilters(filter));
        List<Reservation> reservations = result.getReservations();

        String publicIP = result.getReservations().get(0).getInstances().get(0).getPublicIpAddress();
        return publicIP;
    }

    @Override
    public void saveCloudIdsToJSON(){
        JSONObject obj = new JSONObject();
        JSONArray helpersIds = new JSONArray();
        for (String currHelperID:helper_ids_vec)
            helpersIds.add(currHelperID);

        obj.put("helpers ID's", helpersIds);

        JSONArray initiator_nums = new JSONArray();
        for (String currInit:initiators_vec)
            initiator_nums.add(currInit);

        obj.put("initiators", initiator_nums);

        try (FileWriter file = new FileWriter(StaticDataProvider.LinuxAWS.AWS_SET_UP_JSON)) {
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Override
    public void getCloudIdsFromJSON(){
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(StaticDataProvider.LinuxAWS.AWS_SET_UP_JSON));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray helpersIds = (JSONArray) jsonObject.get("helpers ID's");
            helper_ids_vec.addAll(helpersIds);

            JSONArray initiator_nums = (JSONArray) jsonObject.get("initiators");
            initiators_vec.addAll(initiator_nums);

        } catch (ParseException | IOException e) {
            e.getMessage();
        }
    }

}
