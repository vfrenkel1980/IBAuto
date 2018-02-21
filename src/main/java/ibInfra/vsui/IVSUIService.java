package ibInfra.vsui;

public interface IVSUIService  {

    void vsFirstActivation();

    void installVSWithIB();

    void installVSWithoutIB();

    void upgradeVSWithIB();

    void installVSPreviewWithIB();

    void installVSPreviewWithoutIB();

    void upgradeVSPreviewWithIB();

    void openProject(String projectPath);

    void executeBuildFromMenu(String action);

    void executeBuildFromPrjExplorer(String action, String solutionName);

    void openVS2017instance(String vsinstallation);

    void uninstallIbExtension();

}
