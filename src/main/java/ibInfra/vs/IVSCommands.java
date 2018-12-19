package ibInfra.vs;

public interface IVSCommands {

    void installVSWithIB(String installer);

    void installVSWithoutIB(String installer);

    void upgradeVSWithIB(String installer);

    void upgradeVS(String installer);

    void uninstallIbExtension();
}
