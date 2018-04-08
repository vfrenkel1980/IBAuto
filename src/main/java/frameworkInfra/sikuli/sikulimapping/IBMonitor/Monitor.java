package frameworkInfra.sikuli.sikulimapping.IBMonitor;

import org.sikuli.script.Pattern;

public class Monitor {

    public static class Bars {
        public static final Pattern VSGreenBar = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\BuildMonitor\\vs_green_bar.png");
        public static final Pattern VSYellowBar = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\BuildMonitor\\vs_yellow_bar.png");
        public static final Pattern VSRedBar = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\BuildMonitor\\vs_red_bar.png");
        public static final Pattern IBGreenBar = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\BuildMonitor\\ib_mon_green_bar.png");
        public static final Pattern IBYellowBar = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\BuildMonitor\\ib_mon_yellow_bar.png");
        public static final Pattern IBRedBar = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\BuildMonitor\\ib_mon_red_bar.png");
    }

    public static class Tabs {
        public static final Pattern Projects = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\BuildMonitor\\projects_tab.png");
    }

    public static class Progress {
        public static final Pattern Red = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\BuildMonitor\\red_progress.png");
        public static final Pattern Yellow = new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\BuildMonitor\\yellow_progress.png");
        public static final Pattern Green= new Pattern(System.getProperty("user.dir") + "\\src\\main\\java\\frameworkInfra\\sikuli\\testscreenshots\\BuildMonitor\\green_progress.png");
    }


}

