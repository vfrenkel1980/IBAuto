package frameworkInfra.utils;

import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;
import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

public class StaticDataProvider {

    public static class IbLocations {
        public static String IB_ROOT = RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, Locations.IB_REG_ROOT + "\\builder", "Folder");
        public static final String ENTERPRISE_DIRECTORY = IB_ROOT + " Statistics";
        public static final String BUILD_CONSOLE = "\"" + IbLocations.IB_ROOT + "\\buildconsole.exe" + "\"" + " ";
        public static final String LOGS_ROOT = IbLocations.IB_ROOT + "\\Logs";
        public static final String XGCONSOLE = "\"" + IbLocations.IB_ROOT + "\\xgconsole.exe" + "\"" + " ";
        public static final String IBCONSOLE = "\"" + IbLocations.IB_ROOT + "\\ibconsole.exe" + "\"" + " ";
        public static final String BUILDSYSTEM = "\"" + IbLocations.IB_ROOT + "\\BuildSystem.exe" + "\"" + " ";
        public static final String BUILDMONITOR = "\"" + IbLocations.IB_ROOT + "\\BuildMonitor.exe" + "\"" + " ";
        public static final String BUILDHISTORY = "\"" + IbLocations.IB_ROOT + "\\BuildHistory.exe" + "\"" + " ";
        public static final String XGCOORDCONSOLE = "\"" + IbLocations.IB_ROOT + "\\xgCoordConsole.exe" + "\"" + " ";
        public static final String XLICPROC = "\"" + IbLocations.IB_ROOT + "\\XLicProc.exe" + "\"" + " ";
        public static final String BUILDSETTINGS = "\"" + IbLocations.IB_ROOT + "\\buildsettings.exe" + "\"" + " ";
        public static final String IB_SHORTCUTS = "C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\IncrediBuild";
    }

    public static class IBSamplesLocations {
        public static final String COMPRESSION_7Z_1 = "\"" + Locations.QA_ROOT + "\\projects\\Samples\\Dev Tools Acceleration\\Compression Acceleration\\7z\\Sample1\\" + "\"" + " ";
        public static final String COMPRESSION_7Z_2 = "\"" + Locations.QA_ROOT + "\\projects\\Samples\\Dev Tools Acceleration\\Compression Acceleration\\7z\\Sample2\\" + "\"" + " ";
        public static final String COMPRESSION_GZIP_1 = "\"" + Locations.QA_ROOT + "\\projects\\Samples\\Dev Tools Acceleration\\Compression Acceleration\\GZip\\Sample1\\" + "\"" + " ";
        public static final String COMPRESSION_GZIP_2 = "\"" + Locations.QA_ROOT + "\\projects\\Samples\\Dev Tools Acceleration\\Compression Acceleration\\GZip\\Sample2\\" + "\"" + " ";
        public static final String COMPRESSION_WINRAR_1 = "\"" + Locations.QA_ROOT + "\\projects\\Samples\\Dev Tools Acceleration\\Compression Acceleration\\WinRar\\Sample1\\" + "\"" + " ";
        public static final String COMPRESSION_WINRAR_2 = "\"" + Locations.QA_ROOT + "\\projects\\Samples\\Dev Tools Acceleration\\Compression Acceleration\\WinRar\\Sample2\\" + "\"" + " ";
        public static final String DEVTOOLS_XGE = "\"" + Locations.QA_ROOT + "\\projects\\Samples\\Dev Tools Acceleration\\DevTools Interfaces Usage Samples\\Interception Interface\\" + "\"" + " ";
        public static final String DEVTOOLS_SUBMISSION = "\"" + Locations.QA_ROOT + "\\projects\\Samples\\Dev Tools Acceleration\\DevTools Interfaces Usage Samples\\Submission Interface\\" + "\"" + " ";
        public static final String DEVTOOLS_XML = "\"" + Locations.QA_ROOT + "\\projects\\Samples\\Dev Tools Acceleration\\DevTools Interfaces Usage Samples\\XML Interface\\" + "\"" + " ";
        public static final String NCONVERT = "\"" + Locations.QA_ROOT + "\\projects\\Samples\\Dev Tools Acceleration\\Image Processing\\Nconvert\\" + "\"" + " ";
        public static final String FFMPEG = "\"" + Locations.QA_ROOT + "\\projects\\Samples\\Dev Tools Acceleration\\Video Conversion\\Ffmpeg\\" + "\"" + " ";
        public static final String JOM = "\"" + Locations.QA_ROOT + "\\projects\\Samples\\Make And Build Tools\\Jom\\" + "\"" + " ";
    }

    public static class InitMSBuild {
        public static final String MSBUILD = "\"" + RegistryService.getRegistryKey(HKEY_LOCAL_MACHINE, "Software\\WOW6432Node\\Xoreax\\IncrediBuild\\VSDirs\\15.0", "VSProductDir") + "\\MSBuild\\15.0\\Bin\\msbuild.exe\"";
    }

    //TODO: remove this section when latest version hist VS
    public static class InitOLDMSBuild {
        public static final String OLD_MSBUILD = "\"" + RegistryService.getRegistryKey(HKEY_CURRENT_USER, "Software\\Xoreax\\IncrediBuild\\VSDirs\\15.0", "VSProductDir") + "\\MSBuild\\15.0\\Bin\\msbuild.exe\"";
    }

    //locations
    public static class Locations {
        public static final String IB_REG_ROOT = "SOFTWARE\\WOW6432Node\\Xoreax\\IncrediBuild";
        public static final String IB_HKCU_REG_ROOT = "SOFTWARE\\Xoreax";
        public static final String QA_ROOT = "c:\\QA\\Simulation";
        public static final String VS_INSTALL_DIR = "c:\\QA\\Simulation\\VSintallation";
        public static final String WORKSPACE_REPORTS = System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\reports";
        public static final String SYSTEM_APPDATA_TEMP_FOLDER = System.getProperty("java.io.tmpdir");
        public static final String OUTPUT_LOG_FILE = QA_ROOT + "\\buildLog.txt";
        public static final String LICENSE_TEST_PROJECTS = "C:\\LicenseTests_projects";
        public static final String NETWORK_IB_INSTALLATIONS = "\\\\192.168.10.15\\Share\\1-IB_Builds\\";
        public static final String NETWORK_REPORTS_FOLDER = "\\\\192.168.10.15\\share\\Automation\\Reports\\";
        public static final String DIFFERENT_INSTALLATION_DIRECTORY = "c:\\Incredibuild";
        public static final String DIFFERENT_ENT_INSTALLATION_DIRECTORY = "C:\\IncrediBuild Statistics";
        public static final String SECOND_INITIATOR_LOG_PATH = Locations.QA_ROOT + "\\second_initiator_output\\";
        public static final String LINUX_SCRIPT_OUTPUT = Locations.QA_ROOT + "\\Script_Output\\";
        public static final String ENT_INSTALLER_PATH = "c:\\installer\\";
        public static final String VS_CUSTOM_IB_INSTALLER = "SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Image File Execution Options\\";
    }

    public static class URL {
        public static final String VS_PREVIEW_URL = "https://download.visualstudio.microsoft.com/download/pr/11796490/da370bf146a3a1e91ec0ace29e623fdb/vs_Professional.exe";
        public static final String VS_RELEASE_URL = "https://aka.ms/vs/15/release/vs_professional.exe ";
    }

    //processes
    public static class Processes {
        public static final String BUILD_CONSOLE = "buildconsole.exe ";
        public static final String TRAY_ICON = "xgTrayIcon.exe ";
        public static final String XGCONSOLE = "xgconsole.exe ";
        public static final String BUILDSYSTEM = "BuildSystem.exe ";
        public static final String BUILDMONITOR = "BuildMonitor.exe ";
        public static final String BUILDHISTORY = "BuildHistory.exe ";
        public static final String BUILDSETTINGS = "BuildSettings.exe ";
        public static String XLICPROC = "\"" + IbLocations.IB_ROOT + "\\xlicproc" + "\" " + "/LicenseFile=";
        public static final String NOTHING = Locations.QA_ROOT + "\\Tools\\nothing.exe";
        public static final String PSEXEC = Locations.QA_ROOT + "\\Tools\\PStools\\PsExec.exe";
        public static final String PSLIST = Locations.QA_ROOT + "\\Tools\\PStools\\PsList.exe";
        public static final String SQLITE_CONVERTION_TOOL_NEW = Locations.QA_ROOT + "\\Tools\\ConvertEncryptedDbNew.exe";
        public static final String SQLITE_CONVERTION_TOOL_OLD = Locations.QA_ROOT + "\\Tools\\ConvertEncryptedDbOld.exe";
    }

    public static class WindowsServices {
        public static final String AGENT_SERVICE = "IncrediBuild_Agent";
        public static final String COORD_SERVICE = "IncrediBuild_Coordinator";
        public static final String ENTERPRISE_SERVICE = "IncredibuildDashboardServer";
    }

    public static class LogOutput {
        public static final String BUILD_SUCCEEDED = "Build succeeded";
        public static final String ERROR = "Error";
        public static final String XDTASKID = "xdTaskID";
        public static final String LOCAL = "Local CPU";
        public static final String AGENT = "Agent '";
        public static final String XDSPECULATIVETASKID = "xdSpeculativeTaskID";
        public static final String PREDICTED_DISABLED = "IncrediBuild's Predictive Execution feature has been disabled:";
        public static final String[] ERROR_LIST = {"EAccessViolation", "EWin32Error", "EReadError"};
        public static final String PDB_ERROR = ".pdb' is corrupted";
        public static String PDB_ERROR_TESTS = "";
        public static final String TERMINATION_MESSAGE = "Build terminated at user request";
        public static final String MAX_ALLOWED_BUILDS = "Maximum number of concurrent builds reached.";
    }

    public static class VsActions {
        public static final String BUILD_SOLUTION = "Build Solution";
        public static final String REBUILD_SOLUTION = "Rebuild Solution";
        public static final String CLEAN_SOLUTION = "Clean Solution";
        public static final String BUILD_PROJECT = "Build Project";
        public static final String REBUILD_PROJECT = "Rebuild Project";
        public static final String CLEAN_PROJECT = "Clean Project";
        public static final String STOP_BUILD = "Stop Build";
    }

    public static class VsDevenvInstallPath {
        public static final String VS2017_RELEASE = "C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\\Common7\\IDE";
        public static final String VS2017_PREVIEW = "C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\\Common7\\IDE";
    }

    //projects
    public static class ProjectsCommands {
        public static final String CLEAN = "clean ";
        public static final String BUILD = "build ";
        public static final String REBUILD = "rebuild ";

        public static class MISC_PROJECTS {
            public static final String RUBY_SYNC_PRIVATE_ASSEMBLIES = Locations.QA_ROOT + "\\projects\\Misc\\ruby_2.4\\run.bat";
            public static final String XG_CONSOLE_SAMPLE = IbLocations.XGCONSOLE + " /command=\"\"" + Locations.QA_ROOT + "\\projects\\Misc\\xgConsoleSample\\MainProcess.exe\"  " + Locations.QA_ROOT + "\"\\projects\\Misc\\xgConsoleSample\\DummySubProcess.exe\" 30 2000 2000\" /profile=\"" + Locations.QA_ROOT + "\\projects\\Misc\\xgConsoleSample\\profile.xml\" /title=\"XG Console Sample\"";
            public static final String XG_CONSOLE_SAMPLE_LONG = IbLocations.XGCONSOLE + " /command=\"\"" + Locations.QA_ROOT + "\\projects\\Misc\\xgConsoleSample\\MainProcess.exe\"  " + Locations.QA_ROOT + "\"\\projects\\Misc\\xgConsoleSample\\DummySubProcess.exe\" 300 2000 2000\" /profile=\"" + Locations.QA_ROOT + "\\projects\\Misc\\xgConsoleSample\\profile.xml\" /title=\"XG Console Sample Long\"";
            public static final String IB_CONSOLE_FAILEDBUILD = IbLocations.IBCONSOLE + " /command=\"" + Locations.QA_ROOT + "\\projects\\Misc\\xgConsoleSample\\MainProcess.exe 30 2000 2000\" /profile=\"" + Locations.QA_ROOT + "\\projects\\Misc\\xgConsoleSample\\prof.xml\" /title=\"IB Console Failed build\"";
            public static final String PROJECTVC15_CUSTOMSTEP_FAIL = IbLocations.BUILD_CONSOLE + "\"C:\\QA\\Simulation\\projects\\ProjectVC15CustomStepFailed\\ProjectVC15CustomStepFailed.sln\"  /cfg=\"debug|x86\" /title=\"Project VC15 CustomStep Failed\" /profile=\"C:\\QA\\Simulation\\projects\\ProjectVC15CustomStepFailed\\eFWCompile.ib_profile.xml\"  /rebuild";
            public static final String PROJECTVC10_CUSTOMSTEP_SUCCESS = IbLocations.BUILD_CONSOLE + "\"C:\\QA\\Simulation\\projects\\ProjectVC10CustomStepSuccess\\ProjectVC10CustomStepSuccess.sln\"  /cfg=\"debug|win32\" /title=\"Project VC10 CustomStep Success\" /profile=\"C:\\QA\\Simulation\\projects\\ProjectVC10CustomStepSuccess\\eFWCompile.ib_profile.xml\"  /rebuild";
            public static final String XG_SAMPLE_WITH_RESPONSE_FILE = IbLocations.XGCONSOLE + " @"+Locations.QA_ROOT + "\\projects\\Misc\\xgConsoleSample\\responseXGSample.opt /showagent";
        }

        public static class EXITCODEBASE {
            public static final String PROJECTVC15_RELEASE_X64 = IbLocations.BUILD_CONSOLE + TestProjects.VC15PROJECT + " /rebuild /cfg=\"release|x64\" /nowait  /exitcodebase=1000000000000";
            public static final String PROJECTVC10_DEBUG_WIN32 = IbLocations.BUILD_CONSOLE + TestProjects.VC10PROJECT + " /rebuild /cfg=\"debug|win32\" /nowait /exitcodebase=-8";
            public static final String IB_CONSOLE_FAILEDBUILD = MISC_PROJECTS.IB_CONSOLE_FAILEDBUILD + " /exitcodebase=100";
            public static final String PROJECTVC15_DEBUG_X64 = IbLocations.BUILD_CONSOLE + TestProjects.VC15PROJECT + " /rebuild /cfg=\"release|x64\" /exitcodebase";
            public static final String FAILEDPROJECT_X64_DEBUG = IbLocations.BUILD_CONSOLE + "\"C:\\QA\\Simulation\\projects\\ProjectVC15Failed\\ProjectVC15Failed.sln\"  /cfg=\"debug|x64\"  /rebuild /title=\"Failed VC15 Project - Exit1\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\" /VSversion=15 /exitcodebase";
            public static final String XG_CONSOLE_SAMPLE = ProjectsCommands.MISC_PROJECTS.XG_CONSOLE_SAMPLE + " /exitcodebase=10";
        }


        public static class AGENT_SETTINGS {
            public static final String AUDACITY_X32_DEBUG = "\"C:\\QA\\Simulation\\projects\\Audacity\\Audacity 2.1.0 src\\win\\audacity.sln\" /%s /cfg=\"debug|win32\" /title=\"Audacity 2017 - Debug\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\"";
        }

        public static class VC15Preview_BATMAN {
            public static final String AUDACITY_X32_DEBUG = "\"C:\\QA\\Simulation\\VC15\\Audacity\\Audacity 2.1.0 src\\win\\audacity.sln\" /%s /cfg=\"debug|win32\" /title=\"Audacity 2017 Preview - Debug\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\" /VSversion=15";
            public static final String ACE_X32_DEBUG = "\"C:\\QA\\Simulation\\VC15\\ACE-6.4.0-2015\\ACE_vc14.sln\" /%s /cfg=\"debug|win32\" /title=\"ACE 2017 Preview - Debug\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\" /VSversion=15";
            public static final String BIGPROJECT_X32_DEBUG = "\"C:\\QA\\Simulation\\VC15\\BigProject2\\BigProject2.sln\" /%s /cfg=\"debug|win32\" /title=\"Big Project 2017 Preview - Debug\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\" /VSversion=15";
            public static final String BIGPROJECT_X32_RELEASE = "\"C:\\QA\\Simulation\\VC15\\BigProject2\\BigProject2.sln\" /%s /cfg=\"release|win32\" /title=\"Big Project 2017 Preview - Release\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\" /VSversion=15";

        }

        public static class VC15_BATMAN {
            public static final String AUDACITY_X32_DEBUG = "\"C:\\QA\\Simulation\\VC15\\Audacity\\Audacity 2.1.0 src\\win\\audacity.sln\" /%s /cfg=\"debug|win32\" /title=\"Audacity 2017 - Debug\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\" /VSversion=15";
            public static final String ACE_X32_DEBUG = "\"C:\\QA\\Simulation\\VC15\\ACE-6.4.0-2015\\ACE_vc14.sln\" /%s /cfg=\"debug|win32\" /title=\"ACE 2017 - Debug\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\" /VSversion=15";
            public static final String BIGPROJECT_X32_DEBUG = "\"C:\\QA\\Simulation\\VC15\\BigProject2\\BigProject2.sln\" /%s /cfg=\"debug|win32\" /title=\"Big Project 2017 - Debug\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\" /VSversion=15";
            public static final String BIGPROJECT_X32_RELEASE = "\"C:\\QA\\Simulation\\VC15\\BigProject2\\BigProject2.sln\" /%s /cfg=\"release|win32\" /title=\"Big Project 2017 - Release\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\" /VSversion=15";
            public static final String ANDROIDCPP_ARM64_DEBUG = "\"C:\\QA\\Simulation\\VC15\\Android\\AndroidCPP1\\AndroidCPP1.sln\" /%s /cfg=\"Debug|ARM64\" /title=\"Android CPP -Debug ARM64\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\" /VSversion=15";
            public static final String ANDROIDCS_ANYCPU_DEBUG = "\"C:\\QA\\Simulation\\VC15\\Android\\AndroidAppCS\\AndroidAppCS.sln\" /%s /cfg=\"Debug|Any CPU\" /title=\"Android CS -Debug\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\" /VSversion=15";
        }

        public static class VC14_BATMAN {
            public static final String BLENDER_X64_RELEASE = "\"C:\\QA\\Simulation\\VC14\\Blender-2015New\\build_windows_Full_x64_vc14_Release\\Blender.sln\" /%s /cfg=\"Release|x64\" /title=\"Blender 2015 - Release\"";
            public static final String ACE_X32_DEBUG = "\"C:\\QA\\Simulation\\VC14\\ACE-6.4.0-2015\\ACE_vc14.sln\" /%s /cfg=\"debug|win32\" /title=\"ACE 2015 - Debug\"";
            public static final String MONO_X32_DEBUG = "\"C:\\QA\\Simulation\\VC14\\mono-master\\msvc\\mono.sln\" /%s /cfg=\"debug|win32\" /title=\"Mono 2015 - Debug Win32\"";
            public static final String MONO_X64_DEBUG = "\"C:\\QA\\Simulation\\VC14\\mono-master\\msvc\\mono.sln\" /%s /cfg=\"debug|x64\" /title=\"Mono 2015 - Debug x64\"";
            public static final String MONO_X32_RELEASE = "\"C:\\QA\\Simulation\\VC14\\mono-master\\msvc\\mono.sln\" /%s /cfg=\"release|win32\" /title=\"Mono 2015 - Release Win32\"";
            public static final String MONO_X64_RELEASE = "\"C:\\QA\\Simulation\\VC14\\mono-master\\msvc\\mono.sln\" /%s /cfg=\"release|x64\" /title=\"Mono 2015 - Release x64\"";
            public static final String NINTENDO_AAA_NX32_DEBUG = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\AccountApplicationAuthorization\\AccountApplicationAuthorization-spec.NX.vs2015.sln\" /%s /cfg=\"debug|NX32\"";
            public static final String NINTENDO_AAA_NX64_DEBUG = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\AccountApplicationAuthorization\\AccountApplicationAuthorization-spec.NX.vs2015.sln\" /%s /cfg=\"debug|NX64\"";
            public static final String NINTENDO_AAA_NX32_RELEASE = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\AccountApplicationAuthorization\\AccountApplicationAuthorization-spec.NX.vs2015.sln\" /%s /cfg=\"release|NX32\"";
            public static final String NINTENDO_AAA_NX64_RELEASE = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\AccountApplicationAuthorization\\AccountApplicationAuthorization-spec.NX.vs2015.sln\" /%s /cfg=\"release|NX64\"";
            public static final String NINTENDO_AAA_X32_DEBUG = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\AccountApplicationAuthorization\\AccountApplicationAuthorization-spec.NX.vs2015.sln\" /%s /cfg=\"release|x64\"";
            public static final String NINTENDO_AAA_X64_RELEASE = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\AccountApplicationAuthorization\\AccountApplicationAuthorization-spec.NX.vs2015.sln\" /%s /cfg=\"debug|win32\"";
            public static final String NVNTUTORIAL_NX32_DEBUG = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\NvnTutorial06FrameBuffering\\NvnTutorial06-spec.NX.vs2015.sln\" /%s /cfg=\"debug|NX32\"";
            public static final String NVNTUTORIAL_NX64_DEBUG = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\NvnTutorial06FrameBuffering\\NvnTutorial06-spec.NX.vs2015.sln\" /%s /cfg=\"debug|NX64\"";
            public static final String NVNTUTORIAL_NX32_RELEASE = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\NvnTutorial06FrameBuffering\\NvnTutorial06-spec.NX.vs2015.sln\" /%s /cfg=\"release|NX32\"";
            public static final String NVNTUTORIAL_NX64_RELEASE = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\NvnTutorial06FrameBuffering\\NvnTutorial06-spec.NX.vs2015.sln\" /%s /cfg=\"release|NX64\"";
            public static final String NVNTUTORIAL_X32_DEBUG = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\NvnTutorial06FrameBuffering\\NvnTutorial06-spec.NX.vs2015.sln\" /%s /cfg=\"debug|win32\"";
            public static final String NVNTUTORIAL_X64_RELEASE = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\NvnTutorial06FrameBuffering\\NvnTutorial06-spec.NX.vs2015.sln\" /%s /cfg=\"release|x64\"";
            public static final String SHADOWMAP_DEBUG_DURANGO = "\"C:\\QA\\Simulation\\VC14\\Durango_Samples\\ShadowMap\\graphics\\ShadowMap\\ShadowMap140.sln\" /%s /cfg=\"debug|Durango\"";
            public static final String SHADOWMAP_RELEASE_DURANGO = "\"C:\\QA\\Simulation\\VC14\\Durango_Samples\\ShadowMap\\graphics\\ShadowMap\\ShadowMap140.sln\" /%s /cfg=\"release|Durango\"";
            public static final String XBOX_ONE_MIX_DEBUG_DURANGO = "\"C:\\QA\\Simulation\\VC14\\Durango_Samples\\Xbox_One_Mix\\Samples\\IntroGraphics\\Xbox_One_Mix.sln\" /%s /cfg=\"debug|Durango\"";
            public static final String XBOX_ONE_MIX_RELEASE_DURANGO = "\"C:\\QA\\Simulation\\VC14\\Durango_Samples\\Xbox_One_Mix\\Samples\\IntroGraphics\\Xbox_One_Mix.sln\" /%s /cfg=\"release|Durango\"";
            public static final String BIGPROJECT_X32_DEBUG = "\"C:\\QA\\Simulation\\VC14\\BigProject2\\BigProject2.sln\" /%s /cfg=\"debug|win32\" /title=\"Big Project 2015 - Debug\" /VsVersion=14";
            public static final String BIGPROJECT_X32_RELEASE = "\"C:\\QA\\Simulation\\VC14\\BigProject2\\BigProject2.sln\" /%s /cfg=\"release|win32\" /title=\"Big Project 2015 - Release\" /VsVersion=14";
        }

        public static class VC12_BATMAN {
            public static final String ACE_X32_DEBUG = "\"C:\\QA\\Simulation\\VC12\\ACE_VC12\\ACE_2013.sln\" /%s /cfg=\"debug|win32\" /title=\"ACE 2013 - Debug\" ";
            public static final String ACE_X32_RELEASE = "\"C:\\QA\\Simulation\\VC12\\ACE_VC12\\ACE_2013.sln\" /%s /cfg=\"release|win32\" /title=\"ACE 2013 - Release\" ";
            public static final String BLENDER_X32_DEBUG = "\"C:\\QA\\Simulation\\VC12\\Blender-VS2013\\2013\\Blender.sln\" /%s /cfg=\"debug|win32\" /title=\"Blender 2013 - Debug\"";
            public static final String BLENDER_X32_RELEASE = "\"C:\\QA\\Simulation\\VC12\\Blender-VS2013\\2013\\Blender.sln\" /%s /cfg=\"release|win32\" /title=\"Blender 2013 - Release\"";
            public static final String OPENCOLLADA_X32_DEBUG = "\"C:\\QA\\Simulation\\VC12\\Opencollada-2013\\Build\\OPENCOLLADA.sln\" /%s /cfg=\"debug|win32\" /title=\"OpenCOLLADA 2013 - Debug\"";
            public static final String OPENCOLLADA_X32_RELEASE = "\"C:\\QA\\Simulation\\VC12\\Opencollada-2013\\Build\\OPENCOLLADA.sln\" /%s /cfg=\"release|win32\" /title=\"OpenCOLLADA 2013 - Release\" ";
            public static final String LLVM_X32_DEBUG = "\"C:\\QA\\Simulation\\VC12\\LLVM3.5.0-2013\\build\\LLVM.sln\" /%s /cfg=\"debug|win32\" /title=\"LLVM 2013 - Debug\"";
            public static final String LLVM_X32_RELEASE = "\"C:\\QA\\Simulation\\VC12\\LLVM3.5.0-2013\\build\\LLVM.sln\" /%s /cfg=\"release|win32\" /title=\"LLVM 2013 - Release\"";
            public static final String NINTENDO_AAA_NX32_DEBUG = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\AccountApplicationAuthorization\\AccountApplicationAuthorization-spec.NX.vs2013.sln\" /%s /cfg=\"debug|NX32\"";
            public static final String NINTENDO_AAA_NX64_DEBUG = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\AccountApplicationAuthorization\\AccountApplicationAuthorization-spec.NX.vs2013.sln\" /%s /cfg=\"debug|NX64\"";
            public static final String NINTENDO_AAA_NX32_RELEASE = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\AccountApplicationAuthorization\\AccountApplicationAuthorization-spec.NX.vs2013.sln\" /%s /cfg=\"release|NX32\"";
            public static final String NINTENDO_AAA_NX64_RELEASE = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\AccountApplicationAuthorization\\AccountApplicationAuthorization-spec.NX.vs2013.sln\" /%s /cfg=\"release|NX64\"";
            public static final String NINTENDO_AAA_X32_DEBUG = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\AccountApplicationAuthorization\\AccountApplicationAuthorization-spec.NX.vs2013.sln\" /%s /cfg=\"release|x64\"";
            public static final String NINTENDO_AAA_X64_RELEASE = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\AccountApplicationAuthorization\\AccountApplicationAuthorization-spec.NX.vs2013.sln\" /%s /cfg=\"debug|win32\"";
            public static final String NVNTUTORIAL_NX32_DEBUG = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\NvnTutorial06FrameBuffering\\NvnTutorial06-spec.NX.vs2013.sln\" /%s /cfg=\"debug|NX32\"";
            public static final String NVNTUTORIAL_NX64_DEBUG = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\NvnTutorial06FrameBuffering\\NvnTutorial06-spec.NX.vs2013.sln\" /%s /cfg=\"debug|NX64\"";
            public static final String NVNTUTORIAL_NX32_RELEASE = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\NvnTutorial06FrameBuffering\\NvnTutorial06-spec.NX.vs2013.sln\" /%s /cfg=\"release|NX32\"";
            public static final String NVNTUTORIAL_NX64_RELEASE = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\NvnTutorial06FrameBuffering\\NvnTutorial06-spec.NX.vs2013.sln\" /%s /cfg=\"release|NX64\"";
            public static final String NVNTUTORIAL_X32_DEBUG = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\NvnTutorial06FrameBuffering\\NvnTutorial06-spec.NX.vs2013.sln\" /%s /cfg=\"debug|win32\"";
            public static final String NVNTUTORIAL_X64_RELEASE = "\"C:\\Nintendo\\SDK_FOR_SIMULATION\\NintendoSDK\\Samples\\Sources\\Applications\\NvnTutorial06FrameBuffering\\NvnTutorial06-spec.NX.vs2013.sln\" /%s /cfg=\"release|x64\"";
            public static final String PS4_ORBIS_DEBUG = "\"C:\\Program Files (x86)\\SCE\\ORBIS SDKs\\4.000\\target\\samples\\sample_code\\graphics\\api_subdiv\\api_subdiv.sln\" /%s /cfg=\"debug|orbis\" /minwinver=\"7\"";
            public static final String PS4_ORBIS_RELEASE = "\"C:\\Program Files (x86)\\SCE\\ORBIS SDKs\\4.000\\target\\samples\\sample_code\\graphics\\api_subdiv\\api_subdiv.sln\" /%s /cfg=\"release|orbis\" /minwinver=\"7\"";
        }

        public static class VC11_BATMAN {
            public static final String ACE_X32_RELEASE = "\"C:\\QA\\Simulation\\VC11\\ACE_VC11\\ACE_vc2012.sln\" /%s /cfg=\"release|win32\" /title=\"ACE 2012 - Release\"";
            public static final String PS4_EDGE_ORBIS_DEBUG = "\"C:\\Program Files (x86)\\SCE\\ORBIS SDKs\\4.000\\target\\samples\\sample_code\\engines\\api_edge_animation\\api_edge_animation_orbis.sln\" /%s /cfg=\"debug|orbis\" /minwinver=\"7\"";
            public static final String PS4_EDGE_ORBIS_RELEASE = "\"C:\\Program Files (x86)\\SCE\\ORBIS SDKs\\4.000\\target\\samples\\sample_code\\engines\\api_edge_animation\\api_edge_animation_orbis.sln\" /%s /cfg=\"release|orbis\" /minwinver=\"7\"";
            public static final String PS4_EDGE_ORBIS_PROFILE = "\"C:\\Program Files (x86)\\SCE\\ORBIS SDKs\\4.000\\target\\samples\\sample_code\\engines\\api_edge_animation\\api_edge_animation_orbis.sln\" /%s /cfg=\"profile|orbis\" /minwinver=\"7\"";
            public static final String PS4_GNM_ORBIS_DEBUG = "\"C:\\Program Files (x86)\\SCE\\ORBIS SDKs\\4.000\\target\\samples\\sample_code\\graphics\\api_gnm\\api_gnm.sln\" /%s /cfg=\"debug|orbis\" /minwinver=\"7\"";
            public static final String PS4_GNM_ORBIS_RELEASE = "\"C:\\Program Files (x86)\\SCE\\ORBIS SDKs\\4.000\\target\\samples\\sample_code\\graphics\\api_gnm\\api_gnm.sln\" /%s /cfg=\"release|orbis\" /minwinver=\"7\"";
            public static final String SHADOWMAP_DEBUG_DURANGO = "\"C:\\QA\\Simulation\\VC14\\Durango_Samples\\ShadowMap\\graphics\\ShadowMap\\ShadowMap110.sln\" /%s /cfg=\"debug|Durango\"";
            public static final String SHADOWMAP_RELEASE_DURANGO = "\"C:\\QA\\Simulation\\VC14\\Durango_Samples\\ShadowMap\\graphics\\ShadowMap\\ShadowMap110.sln\" /%s /cfg=\"release|Durango\"";
        }

        public static class VC7_VMSIM {
            public static final String BIG_LIB_DEBUG_RELEASE = "\"C:\\QA\\Simulation\\vc7\\BigLib\\BigLib.sln\" /%s /cfg=\"debug|win32,release|win32\" /prj=biglib";
            public static final String QUAKE2 = "C:\\QA\\Simulation\\vc7\\quake2-3.21\\quake2.sln /%s /preset=mypreset";
            public static final String ABIWORD = "C:\\QA\\Simulation\\vc7\\abiword-2.4.6\\MSVC71\\AbiWord.sln /preset=SafeBuild /%s";
            public static final String SHAREAZA_DEBUG = "C:\\QA\\Simulation\\vc7\\shareaza\\Shareaza.sln /%s /cfg=\"debug|win32\"";
            public static final String VSCAP_RELEASE = "C:\\QA\\Simulation\\vc7\\vscap20s\\vscap.sln /%s /cfg=\"release|win32\"";
        }

        public static class VC6_VMSIM {
            public static final String NEWS_DEBUG = "C:\\QA\\Simulation\\VC6\\gravity\\news.dsw /%s /cfg=\"win32 debug\" ";
            public static final String HEXEDIT = "C:\\QA\\Simulation\\VC6\\HexEd\\Hexedit.dsw /%s";
            public static final String IFPROJECTS = "C:\\QA\\Simulation\\vc6\\ItemField\\Dev\\Projects\\IFprojects.dsw /%s /prj=contentconsole";
            public static final String NOLF = "C:\\QA\\Simulation\\vc6\\nlfe\\NOLF\\NOLF.dsw /%s /prj=clientres";
            public static final String SCILEXER = "C:\\QA\\Simulation\\VC8\\NotePadPP\\scintilla\\vcbuild\\SciLexer.dsp /%s";
            public static final String APACHE_DEBUG = "C:\\QA\\Simulation\\VC6\\apache_1.3.22\\src\\apache.dsw /%s /cfg=\"win32 debug\"";

        }

        public static class VC8_VMSIN {
            public static final String NONTEPAD_PLUS_DEBUG = "C:\\QA\\Simulation\\vc8\\NotePadPP\\PowerEditor\\visual.net\\notepadPlus.vcproj /%s /cfg=\"debug|win32\"";
            public static final String VC8_TEST_DEBUG = "\"C:\\QA\\Simulation\\vc8\\VC8 Test Proj\\VC8 Test Proj.sln\" /%s /cfg=\"debug|mixed platforms\"";
            public static final String MIDL_INTERFACES_DEBUG = "\"C:\\QA\\Simulation\\vc8\\Interfaces\\Interfaces.vcproj\" /%s /cfg=\"debug|win32\"";
            public static final String VC8_MULTUCONFIG = "\"C:\\QA\\Simulation\\VC8\\VC8_Multi_Config_Proj\\VC8MultiConfig.sln\" /%s  /preset=multiconfig";
            public static final String VC8_OGRE_DEBUG = "\"C:\\QA\\Simulation\\VC8\\ogre_VC8\\ogre_vc8.sln\" /%s /cfg=\"debug|win32\"";
            public static final String LOADER_TEST_1_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\LoaderTests\\Loader_test_solution_01_VC8.sln\"  /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_TEST_2_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\LoaderTests\\Loader_test_solution_02_VC8.sln\"  /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_TEST_3_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\LoaderTests\\Loader_test_solution_03_VC8.sln\"  /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_TEST_4_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\LoaderTests\\Loader_test_solution_04_VC8.sln\"  /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_TEST_5_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\LoaderTests\\Loader_test_solution_05_VC8.sln\"  /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_TEST_6_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\LoaderTests\\Loader_test_solution_06_VC8.sln\"  /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_TEST_8_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\LoaderTests\\Loader_test_solution_08_VC8.sln\"  /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_TEST_9_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\LoaderTests\\Loader_test_solution_09_VC8.sln\"  /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_TEST_10_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\LoaderTests\\Loader_test_solution_10_VC8.sln\"  /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_TEST_11_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\LoaderTests\\Loader_test_solution_11_VC8.sln\"  /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_TEST_12_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\LoaderTests\\Loader_test_solution_12_VC8.sln\"  /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_TEST_13_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\LoaderTests\\Loader_test_solution_13_VC8.sln\"  /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_TEST_14_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\LoaderTests\\Loader_test_solution_14_VC8.sln\"  /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_TEST_15_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\LoaderTests\\Loader_test_solution_15_VC8.sln\"  /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_TEST_16_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\LoaderTests\\Loader_test_solution_16_VC8.sln\"  /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_TEST_18_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\LoaderTests\\Loader_test_solution_18_VC8.sln\"  /%s /cfg=\"Debug|Win32\"";
            public static final String PROP_TEST_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\prop_test\\prop_test_VC8.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String ENV_VARS_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\envvars1\\EnvVars1.sln\" /%s /cfg=\"Debug|Win32\" ";
            public static final String PROP_INHERITANCE_2_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\PropInheritance2\\PropInheritance2.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String PROP_INHERITANCE_4_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\PropInheritance4\\PropInheritance4.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String PROP_INHERITANCE_5_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\PropInheritance5\\PropInheritance5.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String REFERENCES_MACRO_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\ReferencesMacro\\ReferencesMacro.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String INPUT_PATH_BUG_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Miscellanious\\InputPathBug\\InputPathBug.sln\" /%s /cfg=\"debug|win32\"";
            public static final String INVALID_DEFINE_SYNTAX_DEBUG = "\"C:\\QA\\Simulation\\VC8\\Invalid_#define_directive_syntax\\Makefiles\\TestIncrediBuild_Win32_msvc8.sln\" /%s /cfg=\"MXD_TFW_CONFIG000_Debug|win32\" /title=\"Invalid #define directive syntax\"";
            public static final String LOADER_TEST_1_RELEASE = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\LoaderTests\\Loader_test_solution_01_VC8.sln\" /%s /cfg=\"release|Win32\"";
            public static final String LOADER_TEST_2_RELEASE = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\LoaderTests\\Loader_test_solution_02_VC8.sln\" /%s /cfg=\"release|Win32\"";
            public static final String LOADER_TEST_3_RELEASE = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\LoaderTests\\Loader_test_solution_03_VC8.sln\" /%s /cfg=\"release|Win32\"";
            public static final String PROP_TEST_RELEASE = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\prop_test\\prop_test_VC8.sln\" /%s /cfg=\"Release|Win32\"";
            public static final String ENV_VARS_RELEASE = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\envvars1\\EnvVars1.sln\" /%s /cfg=\"Release|Win32\" ";
            public static final String PROP_INHERITANCE_1_BATCH = "C:\\QA\\Simulation\\VC8\\Loader_Tests\\PropInheritance1\\AutomiseRun.bat ";
            public static final String PROP_INHERITANCE_2_RELEASE = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\PropInheritance2\\PropInheritance2.sln\" /%s /cfg=\"Release|Win32\"";
            public static final String PROP_INHERITANCE_3_BATCH = "C:\\QA\\Simulation\\VC8\\Loader_Tests\\PropInheritance3\\AutomiseRun.bat ";
            public static final String PROP_INHERITANCE_4_BATCH = "C:\\QA\\Simulation\\VC8\\Loader_Tests\\PropInheritance4\\AutomiseRun.bat ";
            public static final String REFERENCES_MACRO_RELEASE = "\"C:\\QA\\Simulation\\VC8\\Loader_Tests\\ReferencesMacro\\ReferencesMacro.sln\" /%s /cfg=\"Release|Win32\"";
            public static final String ACE_BATCH = "C:\\QA\\simulation\\VC8\\ACE_VC8\\ACE_VC8.bat ";
        }

        public static class VC9_VMSIM {
            public static final String ACE_X32_DEBUG = "\"C:\\QA\\Simulation\\VC9\\ACE_VC9\\ACE_vc9.sln\" /%s /cfg=\"debug|win32\"";
            public static final String SCINTILLA = "\"C:\\QA\\Simulation\\Mixed\\scintilla\\vcbuild\\SciLexer.sln\" /%s /cfg=\"debug|win32\"";
            public static final String VC9_ALL = "\"C:\\QA\\Simulation\\VC9\\VC9-all1\\VC9-all1.sln\" /%s /preset=\"ALL_Configurations\" ";
            public static final String CL_NO_PROPERTIES = "\"C:\\QA\\Simulation\\VC9\\CL_with_no_properties\\CL_with_no_properties.sln\" /%s /cfg=\"debug|win32\"";
            public static final String QUOTES = "\"C:\\QA\\Simulation\\VC9\\Quotes_In_intermidiate_dir_bug\\Quotes_In_intermidiate_dir_bug.sln\" /%s /cfg=\"debug|win32\"";
            public static final String ASSEMBLY = "\"C:\\QA\\Simulation\\VC9\\AssemblyOutputPath\\ConsoleForRef.sln\" /%s /cfg=\"debug|any cpu\" /title=\"Unit_Test_Assembly_Output_Path\"";
            public static final String COPY_LOCAL_FALSE = "\"C:\\QA\\Simulation\\VC9\\CopyLocalFalse\\MixedModeTest.sln\" /%s /cfg=\"debug|win32\" /title=\"Unit_Test_Copy_Local_False\"";
            public static final String REF_OUTPUT_CHANGED_CSHARP = "\"C:\\QA\\Simulation\\VC9\\RefOutputCsharpChanged\\Bridge.sln\" /%s /cfg=\"debug|mixed platforms\" /title=\"Unit_Test_Ref_Output_Csharp_Changed\"";
            public static final String REF_OUTPUT_DIR_CHANGED = "\"C:\\QA\\Simulation\\VC9\\RefOutputDirChanged\\MixedModeTest.sln\" /%s /cfg=\"debug|win32\" /title=\"Unit_Test_Ref_Output_Dir_Changed\"";
            public static final String UTILITY = "\"C:\\QA\\Simulation\\VC9\\Utility2008\\Utility2008.sln\" /%s /cfg=\"debug|win32\" ";
            public static final String PROP_SHEET_OUT_DIR = "\"C:\\QA\\Simulation\\VC9\\Prop_sheet_out_dir\\xoreax.sln\" /%s /cfg=\"debug|win32\"  /title=\"Prop sheet out dir\"";
            public static final String PCH = "\"C:\\QA\\Simulation\\VC9\\PCH_REPRO\\PCH_REPRO.sln\" /%s /cfg=\"debug exporter|win32\"  /title=\"PCH test\"";
            public static final String USING_BUG = "\"C:\\QA\\Simulation\\VC9\\UsingRepro\\UsingRepro.sln\" /%s /cfg=\"debug|win32\"  /title=\"PCH test\"";
            public static final String NOLINK = "\"C:\\QA\\Simulation\\VC9\\nolink_runs_post_build_step\\ConsoleBuildTest.sln\" /%s /prj=\"ConsoleBuildTest\" /cfg=\"Debug|Win32\" /NOLINK /COMPILE=\"C:\\QA\\Simulation\\VC9\\nolink_runs_post_build_step\\ConsoleBuildTest\\stdafx.cpp\"";
            public static final String LOADER_1 = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\LoaderTests\\Loader_test_solution_01.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_2 = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\LoaderTests\\Loader_test_solution_02.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_3 = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\LoaderTests\\Loader_test_solution_03.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_4 = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\LoaderTests\\Loader_test_solution_04.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_5 = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\LoaderTests\\Loader_test_solution_05.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_6 = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\LoaderTests\\Loader_test_solution_06.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_8 = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\LoaderTests\\Loader_test_solution_08.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_9 = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\LoaderTests\\Loader_test_solution_09.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_10 = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\LoaderTests\\Loader_test_solution_10.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_11 = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\LoaderTests\\Loader_test_solution_11.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_12 = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\LoaderTests\\Loader_test_solution_12.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_13 = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\LoaderTests\\Loader_test_solution_13.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_14 = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\LoaderTests\\Loader_test_solution_14.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_15 = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\LoaderTests\\Loader_test_solution_15.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_16 = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\LoaderTests\\Loader_test_solution_16.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String LOADER_18 = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\LoaderTests\\Loader_test_solution_18.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String PROP = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\prop_test\\prop_test.sln\" /%s /cfg=\"Debug|Win32\"";
            public static final String PROPERTY_SHEET_2 = "\"C:\\QA\\Simulation\\VC9\\OutDirRepro\\IncrediBuild360_bug.sln\" /%s /cfg=\"Debug|Win32\" /title=\"Property Sheet 2\"";
            public static final String PROPERTY_SHEET_3 = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\Wrong_Project_Type\\sample.sln\" /%s /cfg=\"Debug|Win32\" /title=\"Property Sheet 3\"";
            public static final String PROPERTY_SHEET_4 = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\ib_problem\\ib_problem.sln\" /%s /cfg=\"Debug|Win32\" /title=\"Property Sheet 4\"";
            public static final String PARENT_NAME_MACRO = "\"C:\\QA\\Simulation\\VC9\\Loader_Tests\\ParentNameMacroBug\\xoreax.sln\" /%s /cfg=\"Debug|Win32\" /title=\"ParentName macro bug.\"";
            public static final String DEP_EVAL_1 = "\"C:\\QA\\Simulation\\VC9\\DepEval\\DepEval_Test_1\\RunMe.bat\"";
            public static final String DEP_EVAL_3 = "\"C:\\QA\\Simulation\\VC9\\DepEval\\DepEval_Test_3\\DepEval_Test_3.sln\" /%s /cfg=\"debug|win32\"";
            public static final String DEP_EVAL_4 = "\"C:\\QA\\Simulation\\VC9\\DepEval\\DepEval_Test_4\\DepEval_Test_4\\DepEval_Test_4.sln\" /%s /cfg=\"debug|win32\"";
            public static final String DEP_EVAL_5 = "\"C:\\QA\\Simulation\\VC9\\DepEval\\DepEval_Test_5\\DepEval_Test_5.sln\" /%s /cfg=\"debug|win32\"";
            public static final String DEP_EVAL_6 = "\"C:\\QA\\Simulation\\VC9\\DepEval\\DepEval_Test_6_VC9\\DepEval_Test_6_VC9.sln\" /%s /cfg=\"debug|win32\"";
            public static final String DEP_EVAL_7 = "\"C:\\QA\\Simulation\\VC9\\DepEval\\Dep_Eval_Test_7_VC9\\Dep_Eval_Test_7_VC9.sln\" /%s /cfg=\"debug|win32\"";
            public static final String DEP_EVAL_9 = "\"C:\\QA\\Simulation\\VC9\\DepEval\\DepEval_Test_9_VC9\\DepEval_Test_9_VC9.sln\" /%s /cfg=\"debug|win32\"";
            public static final String DEP_EVAL_10 = "\"C:\\QA\\Simulation\\VC9\\DepEval\\DepEval_Test_10_VC9\\DepEval_Test_10_VC9.sln\" /%s /cfg=\"debug|win32\"";
            public static final String DEP_EVAL_11 = "\"C:\\QA\\Simulation\\VC9\\DepEval\\DepEval_Test_11_VC9\\DepEval_Test_11_VC9.sln\" /%s /cfg=\"debug|win32\"";
            public static final String DEP_EVAL_12 = "\"C:\\QA\\Simulation\\VC9\\DepEval\\DepEval_Test_12_VC9\\IncrediBuild_PreprocessorBug.sln\" /%s /cfg=\"debug|win32\" /title=\"DepEval 12 (division by zero 1)\"";
            public static final String ROOT_INCLUDE = "\"C:\\QA\\Simulation\\VC9\\DepEval\\rootincludebug\\rootincludebug.sln\" /%s /cfg=\"debug|win32\"";
            public static final String SPACE_IN_COMPILE_SWITCH_BUG = "\"C:\\QA\\Simulation\\vc9\\SpaceSample\\runme.bat";
            public static final String DEP_EVAL_SELF_TEST = "\"C:\\QA\\Simulation\\VC9\\DepEval\\IncTests\\IncTests.sln\" /%s /cfg=\"debug|win32\"";
            public static final String BUILD_SYSTEM_SELF_1_TEST = "/selftest:depeval=c:\\qa\\simulation\\VC9\\DepEval\\Inctests\\inctests\\*.cpp";
            public static final String BUILD_SYSTEM_SELF_2_TEST = "C:\\QA\\Simulation\\VC9\\DepEval\\Inctests\\inctests\\runme.bat";
        }

        public static class VC10_VMSIM {
            public static final String XMBC_X32_DEBUG = "\"C:\\QA\\Simulation\\VC10\\xbmc-2010\\project\\VS2010Express\\XBMC for Windows.sln\" /%s /cfg=\"debug (directX)|win32\" /title=\"XBMC 2010 Debug (directX)\"";
            public static final String XMBC_X32_RELEASE = "\"C:\\QA\\Simulation\\VC10\\xbmc-2010\\project\\VS2010Express\\XBMC for Windows.sln\" /%s  /cfg=\"release (directX)|win32\" /title=\"XBMC 2010 Release (directX)\"";
            public static final String BLENDER_X32_RELEASE = "C:\\BlenderSVN\\2010\\Blender.sln /%s  /cfg=\"release|win32\" /title=\"Blender 2010 Release\"";
            public static final String OAD_X32_DEBUG = "C:\\QA\\Simulation\\vc10\\0AD\\build\\workspaces\\vc2010\\pyrogenesis.sln /%s /cfg=\"debug|win32\" /title=\"0AD - 2010 Debug\"";
            public static final String OAD_X32_RELEASE = "C:\\QA\\Simulation\\vc10\\0AD\\build\\workspaces\\vc2010\\pyrogenesis.sln /%s /cfg=\"debug|win32\" /title=\"0AD - 2010 Debug\"";
            public static final String CL_NO_PROPERTIES = "\"C:\\QA\\Simulation\\VC10\\CL_no_porperties\\CL_no_porperties.sln\" /%s /cfg=\"debug|win32\"";
            public static final String DLL_DATA = "\"C:\\QA\\Simulation\\VC10\\DllDataTest\\DllDataTest.sln\" /%s /cfg=\"debug|win32\"";
            public static final String FUCL = "\"C:\\QA\\Simulation\\VC10\\FuInCommandLine\\Base.sln\" /%s /cfg=\"debug|mixed platforms\"";
            public static final String BATCH_BUILD_BUG = "\"C:\\QA\\Simulation\\VC10\\Batch Build Test\\Batch Build Test.sln\" /%s /preset=\"Batch_Build_Bug\"";
            public static final String NO_DEBUG_CONFIGURATION = "\"C:\\QA\\Simulation\\VC10\\DebugU\\NoDebugConfig.sln\" /%s /cfg=\"debugu|win32\"";
            public static final String ACTIVE_DOC = "\"C:\\QA\\Simulation\\VC10\\ActiveDoc\\ActiveDoc.sln\" /%s /cfg=\"debug|win32\"";
            public static final String ALL_IN_ONE = "\"C:\\QA\\Simulation\\VC10\\All-In-One Code Framework\\Visual Studio 2010\\All-In-One Diagnostics Samples.sln\" /%s /cfg=\"debug|mixed platforms\" /minwinver=8";
            public static final String INTERFACES = "\"C:\\QA\\Simulation\\VC10\\Interfaces\\Interfaces.sln\" /%s /cfg=\"debug|win32\"";
            public static final String VC_2010_TEST = "\"C:\\QA\\Simulation\\VC10\\VS2010_Test_1\\VS2010_Test_1.sln\" /%s /cfg=\"debug|win32\"";
            public static final String DEP_EVAL_4 = "\"C:\\QA\\Simulation\\VC10\\depeval\\DepEval_Test_4\\DepEval_Test_4\\DepEval_Test_4.sln\" /%s /cfg=\"debug|win32\"";
            public static final String DEP_EVAL_5 = "\"C:\\QA\\Simulation\\VC10\\depeval\\DepEval_Test_5\\DepEval_Test_5.sln\" /%s /cfg=\"debug|win32\"";
            public static final String DEP_EVAL_6 = "\"C:\\QA\\Simulation\\VC10\\depeval\\DepEval_Test_6\\DepEval_Test_6_VC9.sln\" /%s /cfg=\"debug|win32\"";
            public static final String DEP_EVAL_7 = "\"C:\\QA\\Simulation\\VC10\\depeval\\DepEval_Test_7\\Dep_Eval_Test_7_VC9.sln\" /%s /cfg=\"debug|win32\"";
            public static final String CONFIGURATION_TEST_1 = "\"C:\\QA\\Simulation\\VC10\\Configuration_Tests\\ConfigurationTest1\\ConfigurationTest1.sln\" /%s /cfg=\"debug|win32\"";
            public static final String CONFIGURATION_TEST_1_NO_UNNEEDED = "\"C:\\QA\\Simulation\\VC10\\Configuration_Tests\\ConfigurationTest1\\ConfigurationTest1.sln\" /%s /preset=\"No_useme_preset\" /title=\"Configuration test 1 Batch Build No unneeded Project marked\"";
            public static final String CONFIGURATION_TEST_1_UNNEEDED = "\"C:\\QA\\Simulation\\VC10\\Configuration_Tests\\ConfigurationTest1\\ConfigurationTest1.sln\" /%s /preset=\"useme_marked\" /title=\"Configuration test 1 Batch Build - Unneeded Project marked\"";
            public static final String CONFIGURATION_TEST_2 = "\"C:\\QA\\Simulation\\VC10\\Configuration_Tests\\ConfigurationTest2\\ConfigurationTest2.sln\" /%s /cfg=\"debug|win32\"";
            public static final String CONFIGURATION_TEST_2_CUSTOM_CONF = "\"C:\\QA\\Simulation\\VC10\\Configuration_Tests\\ConfigurationTest2\\ConfigurationTest2.sln\" /%s /cfg=\"Meow2|win32\"";
            public static final String CONFIGURATION_TEST_2_SEVERAL_CONF = "\"C:\\QA\\Simulation\\VC10\\Configuration_Tests\\ConfigurationTest2\\ConfigurationTest2.sln\" /%s /cfg=\"Release|Win32,Meow1|Win32,Meow2|Win32\" ";
            public static final String CONFIGURATION_TEST_3 = "\"C:\\QA\\Simulation\\VC10\\Configuration_Tests\\ConfigurationTest3\\ConfigurationTest3.sln\" /%s /cfg=\"mouser|win32\"";
            public static final String CONFIGURATION_TEST_3_BATCH_BUILD = "\"C:\\QA\\Simulation\\VC10\\Configuration_Tests\\ConfigurationTest3\\ConfigurationTest3.sln\" /%s /preset=\"ConfigurationTest3_batch_build\" /title=\"Configuration test 3 Batch Build\"";
            public static final String CONFIGURATION_TEST_4 = "\"C:\\QA\\Simulation\\VC10\\Configuration_Tests\\ConfigurationTest4\\ConfigurationTest4.sln\" /%s /cfg=\"debug|win32\"";
            public static final String CONFIGURATION_TEST_4_BATCH_BUILD = "\"C:\\QA\\Simulation\\VC10\\Configuration_Tests\\ConfigurationTest4\\ConfigurationTest4.sln\" /%s /preset=\"Different_name_of_project_and_vcproj\" /title=\"Configuration test 4 Batch Build \"";
            public static final String CONFIGURATION_TEST_5_BATCH_BUILD = "\"C:\\QA\\Simulation\\VC10\\Configuration_Tests\\ConfigurationTest5\\ConfigurationTest5.sln\" /%s /preset=\"Build_Projects_not_present_in_configuration\" /title=\"Configuration test 5 Batch Build \"";
            public static final String CONFIGURATION_TEST_6 = "\"C:\\QA\\Simulation\\VC10\\Configuration_Tests\\ConfigurationTest6\\ConfigurationTest6.sln\" /%s /cfg=\"debug|win32\"";
            public static final String CONFIGURATION_TEST_7 = "\"C:\\QA\\Simulation\\VC10\\Configuration_Tests\\ConfigurationTest7\\rage\\ConfigurationTest7.sln\" /%s /cfg=\"debug|mixed platforms\" /title=\"Configuration test 7 (using .sln and no /prj)\"";
            public static final String CONFIGURATION_TEST_8 = "\"C:\\QA\\Simulation\\VC10\\Configuration_Tests\\ConfigurationTest8\\Proj1.sln\" /%s /all /cfg=\"Debug|Win32,Release|Win32\" /prj=\"*\" /title=\"Configuration Test 8\"";
            public static final String CONFIGURATION_TEST_8_CHECK = "/command=\"c:\\qa\\simulation\\VC10\\Configuration_Tests\\ConfigurationTest8\\proj1\\check.bat\" /title=\"Configuration Test 8 check\"";
            public static final String CONFIGURATION_TEST_9 = "\"C:\\QA\\Simulation\\VC10\\Configuration_Tests\\ConfigurationTest9\\Proj1.sln\" /%s /cfg=\"Debug|Win32\" /prj=\"Proj2\" /title=\"Configuration Test 9\"";
            public static final String CONFIGURATION_TEST_10 = "\"C:\\QA\\Simulation\\VC10\\Configuration_Tests\\ConfigurationTest10\\app\\app.sln\" /project app /projectconfig \"Release|Win32\" /%s  /title=\"Configuration Test 10\"";
            public static final String SINGLE_INVERTED_COMMA = "C:\\QA\\simulation\\VC10\\single_inverted_comma_in_path\\AutomiseRun.bat /%s";
            public static final String DEPENDENCY_REFERENCE_TEST = "C:\\QA\\simulation\\VC10\\Dependancy_And_Refference_checker\\run_me.bat";
        }

        public static class VC11_VMSIM {
            public static final String ACE_X32_DEBUG = "C:\\QA\\Simulation\\VC11\\ACE_VC11\\ACE_vc2012.sln /%s /cfg=\"debug|win32\" /title=\"ACE 2012 - Debug\"";
            public static final String ACE_X32_RELEASE = "C:\\QA\\Simulation\\VC11\\ACE_VC11\\ACE_vc2012.sln /%s /cfg=\"debug|win32\" /title=\"ACE 2012 - Debug\"";
            public static final String XMBC_X32_DEBUG = "\"C:\\QA\\Simulation\\VC11\\xbmc-vs2012\\project\\VS2010Express\\XBMC for Windows.sln\" /%s /cfg=\"debug (directx)|win32\" /title=\"XBMC 2012 - Debug (DirectX)\" /vsversion=\"vc11\"";
            public static final String XMBC_X32_RELEASE = "\"C:\\QA\\Simulation\\VC11\\xbmc-vs2012\\project\\VS2010Express\\XBMC for Windows.sln\" /%s /cfg=\"debug (directx)|win32\" /title=\"XBMC 2012 - Debug (DirectX)\" /vsversion=\"vc11\"";

        }

        public static class VC15_VMSIM {
            public static final String AUDACITY_X32_DEBUG = "\"C:\\QA\\Simulation\\VC15\\Audacity\\Audacity 2.1.0 src\\win\\audacity.sln\" /%s /cfg=\"debug|win32\" /title=\"Audacity 2017 - Debug\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\" /VSversion=15";
            public static final String BIGPROJECT_X32_DEBUG = "\"C:\\QA\\Simulation\\VC15\\BigProject2\\BigProject2.sln\" /%s /cfg=\"debug|win32\" /title=\"Big Project 2017 - Debug\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\" /VSversion=15";
            public static final String BIGPROJECT_X32_RELEASE = "\"C:\\QA\\Simulation\\VC15\\BigProject2\\BigProject2.sln\" /%s /cfg=\"release|win32\" /title=\"Big Project 2017 - Release\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\" /VSversion=15";
            public static final String BLENDER_X64_RELEASE = "\"C:\\QA\\Simulation\\VC15\\Blender\\build_windows_Full_x64_vc15_Release\\Blender.sln\" /%s /cfg=\"Release|x64\" /title=\"Blender 2017 - Release\"";
        }

        public static class VC15Preview_VMSIM {
            public static final String AUDACITY_X32_DEBUG = "\"C:\\QA\\Simulation\\VC15\\Audacity\\Audacity 2.1.0 src\\win\\audacity.sln\" /%s /cfg=\"debug|win32\" /title=\"Audacity 2017 Preview - Debug\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\" /VSversion=15";
            public static final String BIGPROJECT_X32_DEBUG = "\"C:\\QA\\Simulation\\VC15\\BigProject2\\BigProject2.sln\" /%s /cfg=\"debug|win32\" /title=\"Big Project 2017 Preview - Debug\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\" /VSversion=15";
            public static final String BIGPROJECT_X32_RELEASE = "\"C:\\QA\\Simulation\\VC15\\BigProject2\\BigProject2.sln\" /%s /cfg=\"release|win32\" /title=\"Big Project 2017 Preview - Release\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\" /VSversion=15";
            public static final String BLENDER_X64_RELEASE = "\"C:\\QA\\Simulation\\VC15\\Blender\\build_windows_Full_x64_vc15_Release\\Blender.sln\" /%s /cfg=\"Release|x64\" /title=\"Blender 2017 Preview - Release\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\" /VSversion=15";
        }

        public static class VC15_ALFRED {
            public static final String AUDACITY_X32_DEBUG = "\"C:\\QA\\Simulation\\VC15\\Audacity\\Audacity 2.1.0 src\\win\\audacity.sln\" /%s /cfg=\"debug|win32\" /title=\"Audacity 2017 - Debug\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\" /VSversion=15";
            public static final String BLENDER_X64_DEBUG = "\"C:\\QA\\Simulation\\VC15\\Blender\\build_windows_Full_x64_vc15_Release\\Blender.sln\" /%s /cfg=\"Debug|x64\" /title=\"Blender 2017 - Debug\"";
            public static final String MONO_X64_RELEASE = "\"C:\\QA\\Simulation\\VC15\\Mono\\msvc\\mono.sln\" /%s /cfg=\"release|x64\" /title=\"Mono 2017 - Release x64\"";
        }

        public static class UIVALIDATIONS {
            public static final String GREEN01 = "\"C:\\QA\\Simulation\\IB_ColorMarking_test\\Green\\Green01\\Green01.sln\" /rebuild /cfg=\"debug|x86\" /title=Green01";
            public static final String GREEN02 = "\"C:\\QA\\Simulation\\IB_ColorMarking_test\\Green\\Green02\\RunMe.bat\"";
            public static final String GREEN03 = "\"C:\\QA\\Simulation\\IB_ColorMarking_test\\Green\\Green03\\RunMe.bat\"";
            public static final String GREEN04 = "\"C:\\QA\\Simulation\\IB_ColorMarking_test\\Green\\Green04\\RunMe.bat\"";
            public static final String GREEN05 = "\"C:\\QA\\Simulation\\IB_ColorMarking_test\\Green\\Green05\\RunMe.bat\"";
            public static final String YELLOW01 = "\"C:\\QA\\Simulation\\IB_ColorMarking_test\\Yellow\\Yellow01\\Yellow01.sln\" /rebuild /cfg=\"debug|x86\" /title=Yellow01";
            public static final String RED01 = "\"C:\\QA\\Simulation\\IB_ColorMarking_test\\Red\\Red01\\Red01.sln\" /rebuild /cfg=\"debug|x86\" /title=Red01";
            public static final String RED02 = "\"C:\\QA\\Simulation\\IB_ColorMarking_test\\Red\\Red02\\Red02.sln\" /rebuild /cfg=\"debug|x86\" /title=Red02";
            public static final String RED03 = "\"C:\\QA\\Simulation\\IB_ColorMarking_test\\Red\\Red03\\Red03.sln\" /rebuild /cfg=\"debug|x86\" /title=Red03";
            public static final String RED04 = "\"C:\\QA\\Simulation\\IB_ColorMarking_test\\Red\\Red04\\Red04.sln\" /rebuild /cfg=\"debug|x86\" /title=Red04";
            public static final String RED05 = "\"C:\\QA\\Simulation\\IB_ColorMarking_test\\Red\\Red05\\Red05.sln\" /rebuild /cfg=\"debug|x86\" /title=Red05";
            public static final String RED06 = "\"C:\\QA\\Simulation\\IB_ColorMarking_test\\Red\\Red06\\Red06.sln\" /rebuild /cfg=\"debug|x86\" /title=Red06";
            public static final String RED07 = "\"C:\\QA\\Simulation\\IB_ColorMarking_test\\Red\\Red07\\RunMe.bat\"";
            public static final String RED08 = "\"C:\\QA\\Simulation\\IB_ColorMarking_test\\Red\\Red08\\RunMe.bat\"";
            public static final String RED09 = "\"C:\\QA\\Simulation\\IB_ColorMarking_test\\Red\\Red09\\RunMe.bat\"";
        }

        public static class ConsoleAppProj {
            public static final String CONSOLE_APP_SUCCESS = "C:\\QA\\Simulation\\Projects\\ConsoleApplication1\\ConsoleApplication1.sln /%s /cfg=\"Debug|x86\"";
            public static final String CONSOLE_APP_FAIL = "C:\\QA\\Simulation\\Projects\\ConsoleApplication1Fail\\ConsoleApplication1Fail.sln /%s /cfg=\"Debug|x86\"";
            public static final String DEPENDENCY_PROJECT = "C:\\QA\\Simulation\\Projects\\DependencyProject\\DependencyProject.sln /%s /cfg=\"Debug|x86\"";

        }

        public static class CHROME_BATMAN {
            public static final String CHROME_RELEASE_CLEAN = "ninja -C D:\\QA\\Chromium\\src\\out\\Release -t clean";
            public static final String CHROME_RELEASE_BUILD = IbLocations.BUILD_CONSOLE + " /command=\"ninja -C D:\\QA\\Chromium\\src\\out\\Release chrome\" /profile=\"D:\\QA\\Chromium\\chromium_ibprofile.xml\" /Title=ChromiumVsNinja";
        }

        public static class CHROME_ROBIN {
            public static final String CHROME_RELEASE_CLEAN = "ninja -C D:\\Chromium\\src\\out\\Default -t clean";
            public static final String CHROME_RELEASE_BUILD = "buildconsole /command=\"ninja -C D:\\Chromium\\src\\out\\Default chrome\" /profile=\"D:\\Chromium\\chromium_clang.ib_profile.xml\" /minwinver=7 /Title=ChromiumVsCLang";
        }

        public static class VC15_ROBIN {
            public static final String AUDACITY_X32_DEBUG = "\"D:\\Audacity\\Audacity 2.1.0 src\\win\\audacity.sln\" /%s /cfg=\"debug|win32\" /title=\"Audacity 2017 - Debug\" /VSInstallDir=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\" /VSversion=15";
        }
        public static class DOCKER_ROBIN {
            public static final String MONO_X64_DEBUG = "\"C:\\projects\\mono-master\\msvc\\mono.sln\" /rebuild /cfg=\"debug|x64\" /title=\"Mono 2017 - Debug x64\"";
            public static final String AUDACITY_X32_DEBUG = "\"C:\\projects\\Audacity\\Audacity 2.1.0 src\\win\\audacity.sln\" /rebuild  /cfg=\"debug|win32\" /title=\"Audacity 2017 - Debug x32\"";
        }


    }

    //registry keys
    public static class RegistryKeys {
        public static final String MSBUILD = "UseMSBuild";
        public static final String PREDICTED = "PredictedExecutionMode";
        public static final String LOCAL_LOGGING = "ExternalHelperLog";
        public static final String STANDALONE_MODE = "Standalone";
        public static final String AVOID_LOCAL = "AvoidLocalExec";
        public static final String LOGGING_LEVEL = "Level";
        public static final String VERSION = "Version";
        public static final String SAVE_BUILD_PACKET = "SaveBuildPacket";
        public static final String VS_FIRST_ACTIVATION = "FirstActivation";
        public static final String MIN_LOCAL_CORES = "MinLocalCoresPerBuild";
        public static final String MAX_CONCURRENT_BUILDS = "MaxConcurrentBuilds";
        public static final String CUSTOM_STEP_VS10_SUPPORT = "CustomStepVs10Support";
        public static final String KEEP_BUILD_STATUS_ICON = "KeepBuildStatusIcon";
        public static final String FOLDER = "Folder";
        public static final String COORDINATOR_HOST = "CoordHost";
        public static final String ENT_INSTALLATION_REG = "DatabaseFolder";
        public static final String AUTOMATIC_UPDATE_SUBSCRIBED_AGENTS = "UpdateVersionAlways";
        public static final String FLAGS = "Flags";

    }

    public static class IbLicenses {
        public static final String VSTESTS_LIC = "IncrediBuild - Mark Zvuluni - tests predicted SEP 2018.IB_lic";
        public static final String UI_LIC = "IncrediBuild - Mark Zvuluni - personal Coordinator - UI automation.IB_lic";
        public static final String TEST_LIC = "IncrediBuild FreeDev license - Mark Zvuluni - personal Coordinator.IB_lic";
        public static final String NO_ENT_LIC = "IncrediBuild - Mark Zvuluni - personal Coordinator - professional (without Enterprise).IB_lic";
        public static final String AGENT_SETTINGS_LIC = "IncrediBuild - Mark Zvuluni - agent settings.IB_lic";
        public static final String DASHBOARD_LIC = "IncrediBuild FreeDev license - Mark Zvuluni - dashboard tests August 2018.IB_lic";
    }

    public static class WindowsCommands {
        public static final String IB_INSTALL_COMMAND = "%s /install /Components=Coordinator,Agent /ADDTOPATH=ON";
        public static final String IB_UNINSTALL_COMMAND = "%s /uninstall";
        public static final String IB_UPDATE_COMMAND = "%s /update";
        public static final String IB_DOWNGRADE_COMMAND = "%s /downgrade";
        public static final String GET_RUNNING_TASK = "tasklist /fi \"imagename eq %s\"";
        public static final String GET_RUNNING_SERVICE = "tasklist /fi \"services eq %s\"";
        public static final String GET_MEMORY_USAGE = "tasklist /fi \"memusage gt %s\"";
        public static String LOAD_IB_LICENSE = Processes.XLICPROC + "\"" + Locations.QA_ROOT + "\\License\\%s\"";
        public static final String UNLOAD_IB_LICENSE = "\"" + IbLocations.IB_ROOT + "\\xlicproc.exe\" /unloadlicense";
        public static final String INSTALL_VS_WO_IB = "C:\\QA\\Simulation\\VSintallation\\vs_professional --add Microsoft.VisualStudio.Workload.NativeDesktop --includeRecommended -p --norestart";
        public static final String INSTALL_VS_WITH_IB = "C:\\QA\\Simulation\\VSintallation\\vs_professional --add Microsoft.VisualStudio.Workload.NativeDesktop --add Component.Incredibuild --add Microsoft.VisualStudio.Component.Windows10SDK.16299.UWP.Native --includeRecommended -p --norestart --path cache=\"E:\\cache\" --path shared=\"E:\\shared\" --path install=\"E:\\Microsoft Visual Studio\\2017\\Professional\"";
        public static final String UPDATE_VS_WITH_IB = "C:\\QA\\Simulation\\VSintallation\\vs_professional update --add Microsoft.VisualStudio.Workload.NativeDesktop --add Component.Incredibuild --includeRecommended -p --norestart";
        public static final String MODIFY_ADD_INCREDIBUILD = "C:\\QA\\Simulation\\VSintallation\\vs_professional modify --installpath \"C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\" --add Component.Incredibuild --includeRecommended -p";
        public static final String REMOVE_IB_EXTENSION = "C:\\QA\\Simulation\\VSintallation\\vs_professional modify --installpath \"C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\" --remove Component.Incredibuild -p";

        public static final String INSTALL_VSPREVIEW_WO_IB = "C:\\QA\\Simulation\\VSintallation\\vs_professional_preview --add Microsoft.VisualStudio.Workload.NativeDesktop --includeRecommended -p --norestart";
        public static final String INSTALL_VSPREVIEW_WITH_IB = "C:\\QA\\Simulation\\VSintallation\\vs_professional_preview --add Microsoft.VisualStudio.Workload.NativeDesktop --add Component.Incredibuild --add Microsoft.VisualStudio.Component.Windows10SDK.16299.UWP.Native --includeRecommended -p --norestart --path cache=\"E:\\cache\" --path shared=\"E:\\shared\" --path install=\"E:\\Microsoft Visual Studio\\Preview\\Professional\"";
        public static final String UPDATE_VSPREVIEW = "C:\\QA\\Simulation\\VSintallation\\vs_professional_preview update -p --norestart";
        public static final String MODIFY_PREVIEW_ADD_INCREDIBUILD = "C:\\QA\\Simulation\\VSintallation\\vs_professional_preview modify --installpath \"C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\" --add Component.Incredibuild --includeRecommended -p";
        public static final String REMOVE_IB_EXTENSION_VSPREVIEW = "C:\\QA\\Simulation\\VSintallation\\vs_professional_preview modify --installpath \"C:\\Program Files (x86)\\Microsoft Visual Studio\\Preview\\Professional\" --remove Component.Incredibuild -p";
        //public static final String COPY_FROM_LINUX_SERVER = "pscp -pw xoreax xoreax@%s:/home/xoreax/linuxrepo/ib_linux/VERSION " + Locations.QA_ROOT;
    }

    public static class DockerCommands {
        public static final String WIN10_DOC_CONTAINER_EXE ="docker exec -i affectionate_swartz ";
    }

    public static class MemoryThresholds {
        public static final String _20K = "20000";
        public static final String _200K = "200000";
    }

    public static class TestProjects {
        public static final String TEST_PROJ = Locations.QA_ROOT + "\\Projects\\TestProj\\TestProj.sln /%s /cfg=\"Debug|x86\" /title=\"TestProject\"";
        public static final String CUSTOM_PROJECT = Locations.QA_ROOT + "\\Projects\\custom\\custom.sln";
        public static final String VC8PROJECT = Locations.QA_ROOT + "\\projects\\vc8project\\vc8project.sln";
        public static final String VC9PROJECT = Locations.QA_ROOT + "\\projects\\vc9project\\vc9project.sln";
        public static final String VC10PROJECT = Locations.QA_ROOT + "\\projects\\vc10project\\vc10project.sln";
        public static final String VC11PROJECT = Locations.QA_ROOT + "\\projects\\vc11project\\vc11project.sln";
        public static final String VC12PROJECT = Locations.QA_ROOT + "\\projects\\vc12project\\vc12project.sln";
        public static final String VC14PROJECT = Locations.QA_ROOT + "\\projects\\vc14project\\vc14project.sln";
        public static final String VC15PROJECT = Locations.QA_ROOT + "\\projects\\vc15project\\vc15project.sln";
    }

    public static class UIValidationsProjects {
        public static final String GREEN01 = Locations.QA_ROOT + "\\IB_ColorMarking_test\\Green\\Green01\\Green01.sln";
        public static final String YELLOW01 = Locations.QA_ROOT + "\\IB_ColorMarking_test\\Yellow\\Yellow01\\Yellow01.sln";
        public static final String RED01 = Locations.QA_ROOT + "\\IB_ColorMarking_test\\Red\\Red01\\Red01.sln";
        public static final String RED02 = Locations.QA_ROOT + "\\IB_ColorMarking_test\\Red\\Red02\\Red02.sln";
        public static final String RED03 = Locations.QA_ROOT + "\\IB_ColorMarking_test\\Red\\Red03\\Red03.sln";
        public static final String RED04 = Locations.QA_ROOT + "\\IB_ColorMarking_test\\Red\\Red04\\Red04.sln";
        public static final String RED05 = Locations.QA_ROOT + "\\IB_ColorMarking_test\\Red\\Red05\\Red05.sln";
        public static final String RED06 = Locations.QA_ROOT + "\\IB_ColorMarking_test\\Red\\Red06\\Red06.sln";
    }

    public static class LinuxCommands {
        public static final String PLINK = "plink -pw xoreax xoreax@";
        public static final String KILL_IB_DB_CHECK = "ps aux | grep -i ib_db_check.py | awk \'{print $2}\'| xargs sudo kill -9";
        public static final String GET_OS = "cat /proc/version";
        public static final String DELETE_LOGS = "sudo rm -rf /etc/incredibuild/log/20*";
        public static final String CHECK_IB_SERVICES = "\"ps ax --forest | grep ib_server | grep -v \"grep\"\"";
        public static final String START_IB_SERVICES = "sudo /opt/incredibuild/etc/init.d/incredibuild start > /dev/null";
        public static final String STOP_IB_SERVICES = "sudo /opt/incredibuild/etc/init.d/incredibuild stop > /dev/null";
        public static final String RUN_SQLITE_Q = "/opt/incredibuild/bin/sqlite3 /etc/incredibuild/db/incredibuildBuildReport.db \"SELECT %s FROM %s ORDER BY BuildId DESC LIMIT 1\"";
        public static final String BUILD_ID = "BuildId";
        public static final String BUILD_HISTORY = "build_history";
        public static final String DU_TOTAL_ONLY = "du -hs ~/.ccache/ | cut -f 1";
        public static final String HOME_DIR = "/home/xoreax/";
        public static final String COPY_FILE_SCP = "sshpass -p xoreax scp -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null %s xoreax@%s:/home/xoreax";
        public static final String EXTRACT_UPGRADE_FILE = "cd /opt/incredibuild/httpd/htdocs/incredibuild; sudo tar xf ~/%s";
        public static final String EXTRACT_FILE = "tar xvjf %s";
        public static final String GET_IB_VERSION = "ib_console --version";
        public static final String GET_EPOCH_TIME = "date +%s%3N";
    }

    public static class LinuxDB {
        public static final String DB_COORD_REPORT= "incredibuildCoordinatorReport.db";
        public static final String COLUMN_MACHINE = "MachineName";
        public static final String COLUMN_CONNECTED_SINCE = "ConnectedSince";
        public static final String COLUMN_VERSION = "IncredibuildVersion";
        public static final String COLUMN_LICENSED_CORES = "LicensedCores";
        public static final String COLUMN_TIMESTAMP = "TimeStamp";
        public static final String TABLE_HELPER_MACHINES = "HelperMachines_Monitor";
        public static final String TABLE_COORDINATOR_STATUS = "Coordinator_Status_Monitor";
    }

    public static class WindowsMachines {
        public static final String AGENT_SETTINGS_HLPR_IP = "192.168.10.165";
        public static final String AGENT_SETTINGS_HLPR_NAME = "VM-AgntSet-hlp";
        public static final String BABYLON = "babylon";
        public static final String SECOND_INITIATOR = "VM-W7x64-Clean";

    }

    public static class LinuxMachines {
        public static final String LINUX_BUILDER = "192.168.10.44";
    }

    public static class LicTestPrjBuildConsoleCommands {

        public static final String VS2017_CPP = "\\2017\\Cpp\\Cpp.sln /%s /cfg=\"Debug|x86\" /title=\"License Test - VS2017 C++\"";
        public static final String VS2017_CSC = "\\2017\\CSC\\CSC.sln /rebuild /cfg=\"Debug|Any CPU\" /title=\"License Test - VS2017 C#\"";
        public static final String VS2017_PS4_ORBIS = "\"C:\\Program Files (x86)\\SCE\\ORBIS SDKs\\4.500\\target\\samples\\sample_code\\audio_video\\api_libspeech_recognition\\speech_recognition_samples.sln\" /rebuild /cfg=\"Debug|ORBIS\" /title=\"License Test - PS4\" /minwinver=\"7\"";
        public static final String VS2015_XBOX_DURANGO = "\"C:\\Users\\Admin\\Desktop\\Xbox_03_2017_qfe4\\SimpleHDR12\\HDRReconstruction12\\Samples\\Graphics\\HDRReconstruction12\\HDRReconstruction12.sln\" /%s /cfg=\"Debug|Durango\" /title=\"License Test - xBox One\"";
        public static final String MSBUILD_CPP = "/command=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\\Common7\\Tools\\VsDevCmd.bat && msbuild C:\\LicenseTests_projects\\2017\\Cpp\\Cpp.sln /t:rebuild /property:configuration=\"\"debug\"\";platform=\"\"x86\"\" /m:16 /nodeReuse:False /verbosity:normal\" /out=" + Locations.OUTPUT_LOG_FILE + " /showagent /showcmd /showtime /title=\"License test - Make & Build Tools - Msbuild\"";
        public static final String JOM = "/command=\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2017\\Professional\\Common7\\Tools\\VsDevCmd.bat && cd C:\\LicenseTests_projects\\Make_and_Build_Tools && jom clean && jom\" /out=" + Locations.OUTPUT_LOG_FILE + " /showagent /showcmd /showtime /title=\"License test - Make & Build Tools - Jom\"";
        public static final String INTERCEPTION = "\\ibconsole.exe /command=\"cmd /c \"\"C:\\LicenseTests_projects\\Dev_Tools\\Interception\\spawner \"\"C:\\LicenseTests_projects\\Dev_Tools\\Interception\\sleep 5\"\" \"\" \" /profile=C:\\LicenseTests_projects\\Dev_Tools\\Interception\\profile.xml /title=\"License test - Dev Tools - Interception\" /showagent /out=" + Locations.OUTPUT_LOG_FILE;
        public static final String SUBMITION = "\\ibconsole.exe C:\\LicenseTests_projects\\Dev_Tools\\Submition\\Batch.bat /title=\"License test - Dev Tools - Submition\" /showagent /out=" + Locations.OUTPUT_LOG_FILE;
        public static final String XML = "\\xgconsole.exe C:\\LicenseTests_projects\\Dev_Tools\\XML\\XmlSample.xml /title=\"License test - Dev Tools - XML\" /showagent /out=" + Locations.OUTPUT_LOG_FILE;
    }

    public static class LinuxSimulation {

        public static final String CD_KERNEL_DIR = "cd /disk2/projects/linux-2.6.34.14";
        public static final String CD_KERNEL4_DIR = "cd /disk2/projects/linux-4.3.3";
        public static final String CD_KERNEL4_SANITY_DIR = "cd /home/xoreax//projects/linux-4.3.3";
        public static final String CD_32BIT_KERNEL_DIR = "cd /home/xoreax/linux-2.6.34.14";
        public static final String CD_KERNEL4_CCACHE_DIR = "cd /disk2/projects/linux-4.3.3-modified";
        public static final String CD_SAMBA_DIR = "cd /disk2/projects/samba-4.0.7";
        public static final String CD_SAMBA2_DIR = "cd /disk2/projects/samba/samba-4.3.3";
        public static final String CD_CPP_DIR = "cd /disk2/projects/cppunit-1.12.1";
        public static final String CD_APACHE_DIR = "cd /disk2/projects/httpd-2.4.18";
        public static final String CD_MYSQL_DIR = "cd /disk2/projects/mysql-5.6.11/build";
        public static final String CD_MYSQL2_DIR = "cd /disk2/projects/mysql-5.6.11";
        public static final String CD_BOOST_DIR = "cd /home/xoreax/projects/boost_1_60_0";
        public static final String CD_CMAKE_DIR = "cd /home/xoreax/projects/cmake-3.5.2";
        public static final String CD_GDB_DIR = "cd /disk2/projects/gdb-7.11";
        public static final String CD_GIT_DIR = "cd /disk2/projects/git-2.8.1/";
        public static final String CD_QT_DIR = "cd /disk2/projects/qt-everywhere-opensource-src-4.8.6";
        public static final String CD_QT_CCACHE_DIR = "cd /disk2/projects/qt-everywhere-opensource-src-4.8.6-modified";
        public static final String CD_MONGODB_DIR = "cd /disk2/projects/mongodb-src-r3.2.6";
        public static final String CD_CHROMIUM_DIR = "cd /disk2/projects/chromium/src/";
        public static final String CD_GPSD_DIR = "cd /disk2/projects/gpsd-3.10";
        public static final String CD_TENSOR_DIR = "cd /home/xoreax/tensorflow";

        public static final String MAKE_CLEAN = "make clean";
        public static final String SCONS_CLEAN = "scons -c";
        public static final String B2_CLEAN = "./b2 clean";
        public static final String NINJA_CLEAN = "ninja -C out/Release -t clean";
        public static final String BAZEL_CLEAN = " bazel clean --expunge";

        //placement strings: 1)flags 2)caption 3)env 4)processes
        public static final String MAKE_BUILD = "ib_console %s -c %s %s make -j%s";
        public static final String SCONS_BUILD = "ib_console %s -c %s %s scons -j%s";
        public static final String B2_BUILD = "ib_console %s -c %s %s ./b2 -j%s";
        public static final String NINJA_BUILD = "ib_console %s -c %s %s ninja -C out/Release chrome -j%s";
        public static final String BAZEL_BUILD = "ib_console %s -c %s %s  bazel build --jobs=%s --local_resources 4800,24,1 --config=opt //tensorflow/tools/pip_package:build_pip_package";

        public static final String IB_TESTS = "ib_tests-1.0.0.tar.bz2";
        public static final String IB_TESTS_DIR = "/home/xoreax/ib_tests-1.0.0/";
        public static final String RUN_IB_TESTS = "./run_all_tests.bash /tmp/ib_tests/";

        public static final String CHROOT_KERNEL4_DIR = "/projects/linux-4.3.3";

        public static final String LINUX_SIM_NAME_IP_LIST = "C:\\Users\\LP-Neta\\lp-Neta\\Linux\\AutomationDev\\qa_automation\\src\\main\\resources\\Configuration\\linuxSimNameIPList.json";

    }

    public static class InstallationPorts {

        public static final String AGENT_PORT = "31101";
        public static final String HELPER_PORT = "31102";
        public static final String COORDINATOR_PORT = "31100";
        public static final String DASHBOARD_PORT = "8001";
    }
}
