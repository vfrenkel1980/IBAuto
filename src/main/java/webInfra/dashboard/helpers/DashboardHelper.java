package webInfra.dashboard.helpers;

import frameworkInfra.utils.databases.PostgresJDBC;

import java.util.LinkedHashMap;

/**
 * Helper class that contains helper functions for the dahsboard projects
 */
public class DashboardHelper {

    private PostgresJDBC postgresJDBC = new PostgresJDBC();

    public int getAllBuilds(String status) {
        return postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "COUNT(*) ", "coord_build ", "status IN (" + status + ")");
    }

    public long getBuildsDuration(String status) {
        return postgresJDBC.getLongFromQuery("localhost", "ib", "ib", "coordinatordb", "SUM (end_time -start_time) ", "coord_build ", "status IN (" + status + ")");
    }

    public int getBuildsDurationCoreHours(String status) {
        return postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "SUM (total_local_time + total_remote_time) ", "coord_build ","status IN (" + status + ")");
    }

    public long getTotalSavedTime() {
        return postgresJDBC.getLongFromQuery("localhost", "ib", "ib", "coordinatordb", "SUM (saved_time) ", "coord_build ");
    }

    public int getBuildsDistributedCoreHours(String status) {
        return postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "total_remote_time ","coord_build ", "status IN (" + status + ")");
    }

    public long getAvgBuildDuration(){
        return getBuildsDuration("0,1,2") / getAllBuilds("0,1,2") / 1000;
    }

    public int convertStringTimeToEpoch(String time){
        int epoch = 0;

        if (time.contains("h")){
            String hours = time.substring(0,time.indexOf("h"));
            String minutes = time.substring(time.indexOf(" "), time.indexOf("m")).replaceAll(" ","");
            epoch = Integer.parseInt(hours) * 3600 + Integer.parseInt(minutes)*60;
        }
        else{
            String minutes = time.substring(0, time.indexOf("m"));
            String seconds = time.substring(time.indexOf(" "), time.indexOf("s")).replaceAll(" ","");
            epoch = Integer.parseInt(minutes) * 60 + Integer.parseInt(seconds);
        }
        return epoch;
    }

    public long calculateCostSaved(){
        long costSaved = getTotalSavedTime() * 40/3600000;
        if (costSaved < 1)
            return 0;
        else
            return costSaved;
    }

    public LinkedHashMap<String, String> getAllBuildsTopInitiatorAgentsTime(int limit) {
        return postgresJDBC.getLinkedHashMapFromQuery("localhost", "ib", "ib", "coordinatordb", "SUM(c.end_time - c.start_time)AS t , a.name", "coord_build as c", "agent as a ON c.agent_id = a.id", "name", "t  DESC", limit);
    }
}
