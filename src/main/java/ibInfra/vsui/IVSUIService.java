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

    void executeBuildFromMenu(String action);

    void executeBuildFromPrjExplorer(String action, String solutionName);

    void openVS2017instance(String vsinstallation);

    void uninstallIbExtension();

}
