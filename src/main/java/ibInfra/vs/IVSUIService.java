package ibInfra.vs;

public interface IVSUIService  {

    void vsFirstActivation();

    void openProject(String projectPath);

    void createNewProject(String projectName);

    void performIbActionFromMenu(String action);

    void performIbActionFromMenuDontWaitForFinish(String action);

    void performIbActionFromPrjExplorer(String action,String type, String solutionName);

    void openVSInstance(String vsinstallation, boolean isFirstActivation);

    String getInstalledMSBuildVersion();

    void killDriver();

}
