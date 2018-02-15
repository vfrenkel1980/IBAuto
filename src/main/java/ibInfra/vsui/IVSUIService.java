package ibInfra.vsui;

public interface IVSUIService  {

    void vsFirstActivation();

    void installVSWithIB();

    void installVSWithoutIB();

    void upgradeVSWithIB();

    void openProject(String projectPath);

    void executeBuild(String action);

    void uninstallIbExtension();

}
