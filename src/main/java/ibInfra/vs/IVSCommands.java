package ibInfra.vs;

public interface IVSCommands {

    void installVSWithIB();

    void installVSWithoutIB();

    void upgradeVSWithIB();

    void upgradeVS();

    void installVSPreviewWithIB();

    void installVSPreviewWithoutIB();

    void upgradeVSPreviewWithIB();

    void upgradeVSPreview();

    void uninstallIbExtension();
}
