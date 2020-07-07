package frameworkInfra.sikuli.sikulimapping.IBSettings;

import com.amazonaws.services.dynamodbv2.xspec.S;
import org.sikuli.script.*;


public class IBSettings {

    public static class TrayIcon {
        public static final Pattern Green = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\green_tray_icon.png");
        public static final Pattern Yellow = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\yellow_tray_icon.png");
        public static final Pattern Red = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\red_tray_icon.png");
        public static final Pattern monitorTray = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\monitor_tray.png");
        public static final Pattern historyTray = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\history_tray.png");
        public static final Pattern agentSettingsTray = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\agent_settings_tray.png");
        public static final Pattern coordMonitorTray = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\coord_mon_tray.png");
        public static final Pattern coordSettingsTry = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\CoordMonitor\\coord_settings_try.png");
        public static final Pattern enableDisableAsHelperDeniedTray = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\enable_disable_as_helper_denied.png");
        public static final Pattern enabledAsHelperTray = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\TrayIcon\\enabled_as_helper_tray.png");

    }

   public static final Pattern network = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\settingNetworkTab.png");
     public static final Pattern networkcoordinator = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\settingNetworkCoordinatorTab.png");
    public static final Pattern coordinatorNameTB = new Pattern("src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\CoordinatorNameTB.png");
    public static final Pattern OKButton = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\OKButton.png");
    public static final Pattern OKMessageBoxButton = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\OK_messagebox.png");
    public static final Pattern agent =new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\settingAgentTab.png");
    public static final Pattern agent_open = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\agent_open.png");
    public static final Pattern GeneralTab = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\general_tab.png");
    public static final Pattern MultiBuildTab = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\multiple_builds.png");
    public static final Pattern ClearHistoryBtn = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\clear_history.png");
    public static final Pattern ClearHistoryGrayBtn = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\clear_history_gray.png");
    public static final Pattern ConfirmationBtn = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\yes_button.png");
    public static final Pattern ConfirmationBtn2 = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\yes_button2.png");
    public static final Pattern CpuUtilTab = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\cpu_util_tab.png");
    public static final Pattern CpuUtilConfDdl = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\cpu_conf_ddl.png");
    public static final Pattern CpuUtilUserDefined = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\cpu_util_user_defined.png");
    public static final Pattern CpuUtilNumOfCoresTB = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\cpu_util_num_of_cores.png");
    public static final Pattern BuildMonitorTab = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\build_monitor_tab.png");
    public static final Pattern StartingPageProgressDdl = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\progress_ddl.png");
    public static final Pattern StartingPageProjectsDdl = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\projects_ddl.png");
    public static final Pattern SelectedProjectsTab = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\selected_projects_tab.png");
    public static final Pattern OutputTab = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\output_tab.png");
    public static final Pattern ShowAgentNameCB = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\show_agent_name.png");
    public static final Pattern ShowCommandLineCB = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\show_command_line.png");
    public static final Pattern InitiatorTab = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\initiator_tab.png");
    public static final Pattern AdvancedTab = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\advanced_tab.png");
    public static final Pattern StartServiceBtn = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\start_service.png");
    public static final Pattern StopServiceBtn = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\stop_service.png");
    public static final Pattern RestartServiceBtn = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\restart_service.png");
    public static final Pattern EnableLimitNumOFCoresPerBuildCB = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\enable_limit_cores_to_build_cb.png");
    public static final Pattern DisableLimitNumOFCoresPerBuildCB = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\disable_limit_cores_to_build_cb.png");
    public static final Pattern NumOfCoresPerBuild = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\cores_per_build_tb.png");
    public static final Pattern PreferenceTab = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\preference_tab.png");
    public static final Pattern EnableSchedulingCB = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\enable_agent_scheduling_cb.png");
    public static final Pattern isNotActiveScheduling = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\is_not_active_agent_scheduling.png");
    public static final Pattern DisabledTrayIcon = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\disable_try_icon.png");
    public static final Pattern DisableSchedulingCB = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\disableScheduling.png");
    public static final Pattern EnableFailOnlyLocally = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\enable_fail_only_locally.png");
    public static final Pattern DisableFailOnlyLocally = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\disable_fail_only_locally.png");
    public static final Pattern AllowAgentsToBuildGroups =new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\allow_agents_to_buildGroups.png");
    public static final Pattern ClearHistotyNew =new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\clear_history2.png");
    public static final Pattern VisualStudioBuilds = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\visual_studio_builds_tab.png");
    public static final Pattern EnhanceThroughputCheckBox = new Pattern(System.getProperty("user.dir") + "C:\\Users\\TanyaGaus\\IdeaProjects\\qa_automation\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\IBClientSettings\\enhance_throughput_check.png");
    public static final Pattern visualStudioAddIn = new Pattern(System.getProperty("user.dir") + "");
    public static final Pattern tryIconTab = new Pattern(System.getProperty("user.dir") + "");
}
