package frameworkInfra.sikuli.sikulimapping.IBSettings;

import org.sikuli.script.*;


public class IBSettings {

    public static class TrayIcon {
        public static final Pattern Green = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\green_tray_icon.png");
        public static final Pattern Yellow = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\sframeworkInfra\\ikuli\\testscreenshots\\TrayIcon\\yellow_tray_icon.png");
        public static final Pattern Red = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\red_tray_icon.png");
    }

    public static final Pattern network = new Pattern("src\\main\\java\\sikuli\\testscreenshots\\IBClientSettings\\settingNetworkTab.png");
    public static final Pattern networkcoordinator = new Pattern("src\\main\\java\\sikuli\\testscreenshots\\IBClientSettings\\settingNetworkCoordinatorTab.png");
    public static final Pattern coordinatorNameTB = new Pattern("src\\main\\java\\sikuli\\testscreenshots\\IBClientSettings\\CoordinatorNameTB.png");
    public static final Pattern OKButton = new Pattern("src\\main\\java\\sikuli\\testscreenshots\\IBClientSettings\\OKButton.png");
    public static final Pattern agent = new Pattern("src\\main\\java\\sikuli\\testscreenshots\\IBClientSettings\\settingAgentTab.png");

}
