package frameworkInfra.utils.parsers;

import frameworkInfra.utils.SystemActions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlReportingToolParser {
    private static final String GET_NUMBER = "\\d+";

    // INDEX REPORT
    public enum TestStatusInIndex {
        PASSED("test-status text-pass"),
        FAILED("test-status text-fail"),
        SKIPPED("test-status text-skip");

        private final String value;

        TestStatusInIndex(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public static int numberOfPassedTestsInIndex(String filePath) throws InterruptedException {
        return numberOfTestsInIndex(filePath, TestStatusInIndex.PASSED);
    }

    public static int numberOfFailedTestsInIndex(String filePath) throws InterruptedException {
        return numberOfTestsInIndex(filePath, TestStatusInIndex.FAILED);
    }

    public static int numberOfSkippedTestsInIndex(String filePath) throws InterruptedException {
        return numberOfTestsInIndex(filePath, TestStatusInIndex.SKIPPED);
    }

    private static int numberOfTestsInIndex(String filePath, TestStatusInIndex testStatus) throws InterruptedException {
        String fileContent = SystemActions.getFileContent(filePath);
        Matcher matcher = SystemActions.searchPattern(fileContent, testStatus.getValue());
        int numberOfTests = 0;
        while (matcher.find()) {
            numberOfTests++;
        }
        return numberOfTests;
    }

    //---------------------------
    // DASHBOARD REPORT

    public enum TestStatusInDashboard {
        PASSED("(<b>\\d+</b> tests passed)"),
        FAILED("(<b>\\d+</b> tests failed)"),
        SKIPPED("(<b data-tooltip='0%'>\\d+</b> others)");

        private final String value;

        TestStatusInDashboard(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public static int numberOfPassedTestsInDashboard(String filePath) {
        return numberOfTestsInDashboard(filePath, TestStatusInDashboard.PASSED);
    }

    public static int numberOfFailedTestsInDashboard(String filePath) {
        return numberOfTestsInDashboard(filePath, TestStatusInDashboard.FAILED);
    }

    public static int numberOfSkippedTestsInDashboard(String filePath) {
        return numberOfTestsInDashboard(filePath, TestStatusInDashboard.SKIPPED);
    }

    private static int numberOfTestsInDashboard(String filePath, TestStatusInDashboard testStatus) {
        String fileContent = SystemActions.getFileContent(filePath);
        Matcher matcher = SystemActions.searchPattern(fileContent, testStatus.getValue());
        String numberOfTests = "";
        if (matcher.find()) {
            try {
                numberOfTests = matcher.group(0);
            } catch (Exception e) {
                return 0;
            }
        }

        if (numberOfTests.isEmpty()) {
            return 0;
        }

        // Extract number from found string
        Pattern pattern = Pattern.compile(GET_NUMBER);
        matcher = pattern.matcher(numberOfTests);
        if (matcher.find()) {
            try {
                numberOfTests = matcher.group(0);
            } catch (Exception e) {
                return 0;
            }
        }

        if (numberOfTests.isEmpty()) {
            return 0;
        }
        return Integer.valueOf(numberOfTests);
    }

}
