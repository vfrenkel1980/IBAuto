package ibInfra.vsui;

public interface IVSUIService  {

    void vsFirstActivation();

    void installVSWithIB();

    void installVSWithoutIB();

    void upgradeVSWithIB();

    void upgradeVS();

    void installVSPreviewWithIB();

    void installVSPreviewWithoutIB();

    void upgradeVSPreviewWithIB();

    void upgradeVSPreview();

    void openProject(String projectPath);

    void createNewProject(String projectName);

    void performIbActionFromMenu(String action);

    void performIbActionFromMenuDontWaitForFinish(String action);

    void performIbActionFromPrjExplorer(String action,String type, String solutionName);

    void openVSInstance(String vsinstallation, boolean isFirstActivation);

    void uninstallIbExtension();

    String getInstalledMSBuildVersion();

}
