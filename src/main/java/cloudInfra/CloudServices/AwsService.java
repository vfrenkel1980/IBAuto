package cloudInfra.CloudServices;

import com.amazonaws.services.dynamodbv2.xspec.NULL;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import com.aventstack.extentreports.Status;
import com.microsoft.azure.management.compute.VirtualMachineSizeTypes;
import frameworkInfra.utils.StaticDataProvider;

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

    protected String instance_id_coord = "i-0a415b21b10fe3db0";
    protected String ami_id_helper = "ami-00183381177c699e5";
    protected String ami_id_perf = "ami-0179809c723b639f4";
    protected String ami_id_coord = "ami-0969a81aaa01786e6";
    protected String helper_type = "t2.xlarge";
    protected String initiator_type = "c4.4xlarge";
//  private String test_type = "t1.micro";

    protected static String and5_inst_id = "i-09b17d03429b1a25d";
    protected static String and6_inst_id = "i-0e4c64ed1a75fe883";
    protected static String and7_inst_id = "i-06befc808b5caa7f1";
    protected static String and8_inst_id = "i-04bbfcaafb96bde38";
    protected static String and9_inst_id = "i-0a55ca9b6c3819b85";
    protected static String coord_publicIP_AWS = "63.34.106.126";

    protected  int vmCount;
    protected  Vector<String> initiators_vec = new Vector<String>();
    protected  InstanceType instanceType;
    protected Vector<String> helper_ids_vec = new Vector<String>();
    protected Vector<String> initiator_ids_vec = new Vector<String>();
    protected static HashMap<String, String> init_inst_id_Map = new HashMap<>();

    static {
//            initiatoraMap = new HashMap<>();
        init_inst_id_Map.put("1", "i-034a1758bf2a100f1");
        init_inst_id_Map.put("2", "");
        init_inst_id_Map.put("3", "");
        init_inst_id_Map.put("5", "i-09b17d03429b1a25d");
        init_inst_id_Map.put("6", "Intro to Map");
        init_inst_id_Map.put("7", "Some article");
        init_inst_id_Map.put("8", "i-09b17d03429b1a25d");
        init_inst_id_Map.put("9", "i-0a55ca9b6c3819b85");
    }

    public AwsService(String cpu, String memory, String vmCount, String initiators) {

       // test.log(Status.INFO, "AwsService construtor");
        log.info("starting AwsService construtor");
        this.vmCount = Integer.parseInt(vmCount);
        String[] init_arr = initiators.split(", ");

        for(String initiator: init_arr) {

            initiators_vec.add(initiator);
        }

       instanceType = InstanceType.T2Xlarge; //default helper

       if (cpu.equals("2") && memory.equals("4"))
           this.instanceType = InstanceType.C1Medium.T2Medium;
       else if (cpu.equals("16") && memory.equals("30"))
           this.instanceType = InstanceType.C44xlarge;

   }

    public void create() {

        test.log(Status.INFO, "create");
        for (int i = 0; i < vmCount; ++i) {
            RunInstancesRequest run_request = new RunInstancesRequest()
                    .withImageId(ami_id_helper)
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

    private void startCoord()
    {
        StartInstancesRequest stert_request = new StartInstancesRequest().withInstanceIds(instance_id_coord);
        StartInstancesResult startInstancesResult =  ec2.startInstances(stert_request);

        boolean isInstUp = false;
        DescribeInstanceStatusResult status_result;
        do {
            status_result = ec2.describeInstanceStatus(new DescribeInstanceStatusRequest().withInstanceIds(instance_id_coord));
            isInstUp = (status_result.getInstanceStatuses().size() > 0);
        } while (!isInstUp);
    }

    private void startInitiators()
    {
        String curr_initiator = null;

        for(String curr_init_num: initiators_vec) {

            curr_initiator = init_inst_id_Map.get(curr_init_num);
            if (  curr_initiator != null) {
                StartInstancesRequest stert_request = new StartInstancesRequest().withInstanceIds(curr_initiator);
                StartInstancesResult startInstancesResult = ec2.startInstances(stert_request);

                boolean isInstUp = false;
                DescribeInstanceStatusResult status_result;
                do {
                    status_result = ec2.describeInstanceStatus(new DescribeInstanceStatusRequest().withInstanceIds(curr_initiator));
                    isInstUp = (status_result.getInstanceStatuses().size() > 0);
                } while (!isInstUp);
            }
        }
    }

    public void startVm() {
        startCoord();
        startInitiators();
    }

    public void stopVm(){
//        test.log(Status.INFO, "stopVm");
        log.info("stopVm");

        StopInstancesRequest request = new StopInstancesRequest().withInstanceIds(instance_id_coord);
        ec2.stopInstances(request);

        //TODO initiator???
//        if(!initiator_ids_vec.isEmpty()) {
//            request = new StopInstancesRequest().withInstanceIds(initiator_ids_vec);
//            ec2.stopInstances(request);
//        }
    }

    public void deleteVmAWS(){

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

}
