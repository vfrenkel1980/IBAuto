package ibInfra.dataObjects.postgres;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

/**
 * this class is used to initialize an object for the coord_build table in the dashboard project
 */

public class CoordBuild {

    private String buildID;
    private int agentID;
    private int startTime;
    private int endTime;
    private String caption;
    private int totalLocalTime;
    private int totalRemoteTime;
    private int status;
    private int maxCoresUsed;
    private int numberOfTasks;
    private int avgReadyTasks;
    private int avgUsedCores;
    private int maxReadyTasks;
    private int biIssuer;
    private int ciIssuer;
    private int buildType;
    private String productName;
    private String userName;
    private String groupName;
    private int savedTime;
    private boolean avoidLocal;
    private String commandText;
    private boolean predicted;

    /**
     * initialize the object with given parameters
     * @param buildID
     * @param agentID
     * @param startTime
     * @param endTime
     * @param caption
     * @param totalLocalTime
     * @param totalRemoteTime
     * @param status
     * @param maxCoresUsed
     * @param numberOfTasks
     * @param avgReadyTasks
     * @param avgUsedCores
     * @param maxReadyTasks
     * @param biIssuer
     * @param ciIssuer
     * @param buildType
     * @param productName
     * @param userName
     * @param groupName
     * @param savedTime
     * @param avoidLocal
     * @param commandText
     * @param predicted
     */
    public CoordBuild(String buildID, int agentID, int startTime, int endTime, String caption, int totalLocalTime, int totalRemoteTime, int status, int maxCoresUsed,
                      int numberOfTasks, int avgReadyTasks, int avgUsedCores, int maxReadyTasks, int biIssuer, int ciIssuer, int buildType, String productName,
                      String userName, String groupName, int savedTime, boolean avoidLocal, String commandText, boolean predicted) {
        this.buildID = buildID;
        this.agentID = agentID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.caption = caption;
        this.totalLocalTime = totalLocalTime;
        this.totalRemoteTime = totalRemoteTime;
        this.status = status;
        this.maxCoresUsed = maxCoresUsed;
        this.numberOfTasks = numberOfTasks;
        this.avgReadyTasks = avgReadyTasks;
        this.avgUsedCores = avgUsedCores;
        this.maxReadyTasks = maxReadyTasks;
        this.biIssuer = biIssuer;
        this.ciIssuer = ciIssuer;
        this.buildType = buildType;
        this.productName = productName;
        this.userName = userName;
        this.groupName = groupName;
        this.savedTime = savedTime;
        this.avoidLocal = avoidLocal;
        this.commandText = commandText;
        this.predicted = predicted;
    }

    /**
     * initialize a random values object
     */
    public CoordBuild() {
        Random rand = new Random();
        Instant instant = Instant.now();

        this.buildID = UUID.randomUUID().toString();
        this.agentID = rand.nextInt((20 - 1) + 1) + 1;
        this.startTime = (int) instant.getEpochSecond();
        this.endTime = this.startTime + rand.nextInt((100 - 10) + 1) +10;
        this.caption = "TEST";
        this.totalLocalTime = this.endTime - this.startTime;
        this.totalRemoteTime = 0;
        this.status = rand.nextInt((1) + 1);
        this.maxCoresUsed = rand.nextInt((8 - 1) + 1) + 1;
        this.numberOfTasks = rand.nextInt((100 - 1) + 1) + 1;
        this.avgReadyTasks = 1;
        this.avgUsedCores = rand.nextInt((8 - 1) + 1) + 1;
        this.maxReadyTasks = 3;
        this.biIssuer = 1;
        this.ciIssuer = 1;
        this.buildType = rand.nextInt((3 - 1) + 1) + 1;
        this.productName = null;
        this.userName = null;
        this.groupName = null;
        this.savedTime = rand.nextInt((50 - 1) + 1) + 1;
        this.avoidLocal = false;
        this.commandText = "This is a test command";
        this.predicted = false;
    }

    public String getBuildID() {
        return buildID;

    }

    public void setBuildID(String buildID) {
        this.buildID = buildID;
    }

    public int getAgentID() {
        return agentID;
    }

    public void setAgentID(int agentID) {
        this.agentID = agentID;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getTotalLocalTime() {
        return totalLocalTime;
    }

    public void setTotalLocalTime(int totalLocalTime) {
        this.totalLocalTime = totalLocalTime;
    }

    public int getTotalRemoteTime() {
        return totalRemoteTime;
    }

    public void setTotalRemoteTime(int totalRemoteTime) {
        this.totalRemoteTime = totalRemoteTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMaxCoresUsed() {
        return maxCoresUsed;
    }

    public void setMaxCoresUsed(int maxCoresUsed) {
        this.maxCoresUsed = maxCoresUsed;
    }

    public int getNumberOfTasks() {
        return numberOfTasks;
    }

    public void setNumberOfTasks(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
    }

    public int getAvgReadyTasks() {
        return avgReadyTasks;
    }

    public void setAvgReadyTasks(int avgReadyTasks) {
        this.avgReadyTasks = avgReadyTasks;
    }

    public int getAvgUsedCores() {
        return avgUsedCores;
    }

    public void setAvgUsedCores(int avgUsedCores) {
        this.avgUsedCores = avgUsedCores;
    }

    public int getMaxReadyTasks() {
        return maxReadyTasks;
    }

    public void setMaxReadyTasks(int maxReadyTasks) {
        this.maxReadyTasks = maxReadyTasks;
    }

    public int getBiIssuer() {
        return biIssuer;
    }

    public void setBiIssuer(int biIssuer) {
        this.biIssuer = biIssuer;
    }

    public int getCiIssuer() {
        return ciIssuer;
    }

    public void setCiIssuer(int ciIssuer) {
        this.ciIssuer = ciIssuer;
    }

    public int getBuildType() {
        return buildType;
    }

    public void setBuildType(int buildType) {
        this.buildType = buildType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getSavedTime() {
        return savedTime;
    }

    public void setSavedTime(int savedTime) {
        this.savedTime = savedTime;
    }

    public boolean isAvoidLocal() {
        return avoidLocal;
    }

    public void setAvoidLocal(boolean avoidLocal) {
        this.avoidLocal = avoidLocal;
    }

    public String getCommandText() {
        return commandText;
    }

    public void setCommandText(String commandText) {
        this.commandText = commandText;
    }

    public boolean isPredicted() {
        return predicted;
    }

    public void setPredicted(boolean predicted) {
        this.predicted = predicted;
    }
}
