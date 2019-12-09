package frameworkInfra.sikuli.sikulimapping.IBSettings;

import org.sikuli.script.*;


public class IBSettings {

    public static class TrayIcon {
        public static final Pattern Green = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\tray_icon_green.png");
        public static final Pattern Yellow = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\yellow_tray_icon.png");
        public static final Pattern Red = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\red_tray_icon.png");
        public static final Pattern monitorTray = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\monitor_tray.png");
        public static final Pattern historyTray = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\history_tray.png");
        public static final Pattern agentSettingsTray = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\agent_settings_tray.png");
        public static final Pattern coordMonitorTray = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\coord_mon_tray.png");
        public static final Pattern enableDisableAsHelperDeniedTray = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\enable_disable_as_helper_denied.png");
        public static final Pattern enabledAsHelperTray = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\enabled_as_helper_tray.png");
    }

/*    public static final Pattern network = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\settingNetworkTab.png");
    public static final Pattern networkcoordinator = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\settingNetworkCoordinatorTab.png");
    public static final Pattern coordinatorNameTB = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\CoordinatorNameTB.png");*/
    public static final Pattern OKButton = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\OKButton.png");
    public static final Pattern OKMessageBoxButton = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\OK_messagebox.png");
    public static final Pattern agent = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\settingAgentTab.png");
    public static final Pattern GeneralTab = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\general_tab.png");
    public static final Pattern MultiBuildTab = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\multiple_builds.png");
    public static final Pattern ClearHistoryBtn = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\clear_history.png");
    public static final Pattern ConfirmationBtn = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\yes_button.png");
    public static final Pattern ConfirmationBtn2 = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\yes_button2.png");
    public static final Pattern CpuUtilTab = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\cpu_util_tab.png");
    public static final Pattern CpuUtilConfDdl = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\cpu_conf_ddl.png");
    public static final Pattern CpuUtilUserDefined = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\cpu_util_user_defined.png");
    public static final Pattern CpuUtilNumOfCoresTB = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\cpu_util_num_of_cores.png");
    public static final Pattern BuildMonitorTab = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\build_monitor_tab.png");
    public static final Pattern StartingPageProgressDdl = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\progress_ddl.png");
    public static final Pattern StartingPageProjectsDdl = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\projects_ddl.png");
    public static final Pattern SelectedProjectsTab = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\selected_projects_tab.png");
    public static final Pattern OutputTab = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\output_tab.png");
    public static final Pattern ShowAgentNameCB = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\show_agent_name.png");
    public static final Pattern ShowCommandLineCB = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\show_command_line.png");
    public static final Pattern InitiatorTab = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\initiator_tab.png");
    public static final Pattern AdvancedTab = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\advanced_tab.png");
    public static final Pattern StartServiceBtn = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\start_service.png");
    public static final Pattern StopServiceBtn = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\stop_service.png");
    public static final Pattern RestartServiceBtn = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\restart_service.png");
    public static final Pattern EnableLimitNumOFCoresPerBuildCB = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\enable_limit_cores_to_build_cb.png");
    public static final Pattern DisableLimitNumOFCoresPerBuildCB = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\disable_limit_cores_to_build_cb.png");
    public static final Pattern NumOfCoresPerBuild = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\cores_per_build_tb.png");
    public static final Pattern PreferenceTab = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\preference_tab.png");
    public static final Pattern EnableSchedulingCB = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\enable_agent_scheduling_cb.png");
    public static final Pattern isNotActiveScheduling = new Pattern("\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\is_not_active_agent_scheduling.png");
    public static final Pattern DisabledTrayIcon = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\disabledTrayIcon.png");
    public static final Pattern DisableSchedulingCB = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\disableScheduling.png");
    public static final Pattern EnableFailOnlyLocally = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\enable_fail_only_locally.png");
    public static final Pattern DisableFailOnlyLocally = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\disable_fail_only_locally.png");


}
