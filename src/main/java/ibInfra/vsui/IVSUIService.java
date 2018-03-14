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

    void performIbActionFromMenu(String action);

    void performIbActionFromPrjExplorer(String action,String type, String solutionName);

    void openVS2017instance(String vsinstallation);

    void uninstallIbExtension();

}
