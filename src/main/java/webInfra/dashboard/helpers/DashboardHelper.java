package webInfra.dashboard.helpers;

import frameworkInfra.utils.databases.PostgresJDBC;
import ibInfra.windowscl.WindowsService;

import java.util.LinkedHashMap;

/**
 * Helper class that contains helper functions for the dashboard projects
 */
public class DashboardHelper {
    public WindowsService winService = new WindowsService();
    private PostgresJDBC postgresJDBC = new PostgresJDBC();

    public int getBuilds(String status, String period) {
        String between = between("start_time", period);
        return postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "COUNT(*) ", "coord_build ", "status IN (" + status + ")" + between);
    }

    public long getBuildsDuration(String status, String period) {
        String between = between("start_time", period);
        return postgresJDBC.getLongFromQuery("localhost", "ib", "ib", "coordinatordb", "SUM(end_time-start_time) ", "coord_build ", "status IN (" + status + ")" + between);
    }

    public int getBuildsDurationCoreHours(String status, String period) {
        String between = between("start_time", period);
        return postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "ROUND(AVG(total_local_time + total_remote_time),2) ", "coord_build ", "status IN (" + status + ")" + between);
    }

    public long getTotalSavedTime() {
        return postgresJDBC.getLongFromQuery("localhost", "ib", "ib", "coordinatordb", "SUM(saved_time)) ", "coord_build ");
    }

    public int getBuildsDistributedCoreHours(String status, String period) {
        String between = between("start_time", period);
        return postgresJDBC.getIntFromQuery("localhost", "ib", "ib", "coordinatordb", "total_remote_time ", "coord_build ", "status IN (" + status + ")" + between);
    }

    public long getAvgBuildDuration(String status, String period) {
        String between = between("start_time", period);
        return postgresJDBC.getLongFromQuery("localhost", "ib", "ib", "coordinatordb", "ROUND(AVG(end_time-start_time),2) ", "coord_build ", "status IN (" + status + ")" + between)/1000;
    }

    public int convertStringTimeToEpoch(String time) {
        int epoch = 0;

        if (time.contains("h")) {
            String hours = time.substring(0, time.indexOf("h"));
            String minutes = time.substring(time.indexOf(" "), time.indexOf("m")).replaceAll(" ", "");
            epoch = Integer.parseInt(hours) * 3600 + Integer.parseInt(minutes) * 60;
        } else {
            String minutes = time.substring(0, time.indexOf("m"));
            String seconds = time.substring(time.indexOf(" "), time.indexOf("s")).replaceAll(" ", "");
            epoch = Integer.parseInt(minutes) * 60 + Integer.parseInt(seconds);
        }
        return epoch;
    }

    public long calculateCostSaved() {
        long costSaved = getTotalSavedTime() * 40 / 3600000;
        if (costSaved < 1)
            return 0;
        else
            return costSaved;
    }

    public LinkedHashMap<String, String> getAllBuildsTopInitiatorAgentsTime(int limit, String period) {
        String between = between("start_time",period);
        return postgresJDBC.getLinkedHashMapFromQuery("localhost", "ib", "ib", "coordinatordb", "SUM(c.end_time - c.start_time)AS t , a.name", "coord_build as c", "agent as a ON c.agent_id = a.id", "name", "t  DESC", limit);
    }

    public String between(String column, String period) {
        String between = " AND " + column + " BETWEEN ";
        long now = winService.getNowWOSeconds();
        switch (period) {
            case "All":
                between = "";
                break;
            case "Today":
                between += winService.getTodayMidnight() + " AND " + now;
                break;
            case "H12":
                long curTMinus12H = now - 12 * 3600 * 1000;
                between += curTMinus12H + " AND " + now;
                break;
            case "H24":
                long curTMinus24H = now - 24 * 3600 * 1000;
                between += curTMinus24H + " AND " + now;
                break;
            default:
                return "Invalid period";
        }
        return between;
    }
}
