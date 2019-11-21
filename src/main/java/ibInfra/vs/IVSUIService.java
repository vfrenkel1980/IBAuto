package ibInfra.vs;

public interface IVSUIService  {

    void vsFirstActivation();

    void openProject(String projectPath);

    void createNewProject(String projectName, String version);

    void performIbActionFromMenu(String action);

    void performIbActionFromMenuDontWaitForFinish(String action);

    void performIbActionFromPrjExplorer(String action,String type, String solutionName);

    void openVSInstance(String vsinstallation, boolean isFirstActivation, String scenario);

    String getInstalledMSBuildVersion();

    void killDriver();

}
