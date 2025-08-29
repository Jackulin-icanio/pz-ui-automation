package com.propertyzar.ui.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.propertyzar.ui.ConfigManager;
import com.propertyzar.ui.DriverManager;
import com.propertyzar.ui.extentreports.ExtentManager;
import com.propertyzar.ui.extentreports.ExtentTestManager;
import lombok.extern.slf4j.Slf4j;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class TestListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

    private final List<ITestNGMethod> passedTests = new ArrayList<>();
    private final List<ITestNGMethod> failedTests = new ArrayList<>();
    private final List<ITestNGMethod> skippedTests = new ArrayList<>();
    private Instant suiteStartTime;

    private static String getTestMethodName(ITestResult iTestResult) {
        String testName = iTestResult.getMethod().getConstructorOrMethod().getMethod()
                .getAnnotation(org.testng.annotations.Test.class).testName();
        return testName.isEmpty() ? iTestResult.getMethod().getConstructorOrMethod().getName() : testName;
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        try {
            suiteStartTime = Instant.now();
            Files.createDirectories(Paths.get("./Screenshots"));
            Files.createDirectories(Paths.get("./test-output/Reports/Screenshots"));
        } catch (IOException e) {
            log.error("Failed to initialize the Automation", e);
            throw new RuntimeException(e);
        }
        log.info("I am in onStart method {}", iTestContext.getName());
        iTestContext.setAttribute("Contentstack", "Automation testing");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        log.info("I am in onFinish method {}", iTestContext.getName());
        ExtentTestManager.endTest();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        log.info("{} test is starting.", getTestMethodName(iTestResult));
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        log.info("{} test is succeeded.", getTestMethodName(iTestResult));
        passedTests.add(iTestResult.getMethod());
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        log.info("{} test has failed.", getTestMethodName(iTestResult));
        try {
            Object testInstance = iTestResult.getInstance();
            ExtentTest extentTest = (ExtentTest) iTestResult.getTestClass()
                    .getRealClass()
                    .getMethod("getCurrentTestReportNode")
                    .invoke(testInstance);
            DriverManager driverManager = ExtentTestManager.getTestDriverManager();
            if (extentTest != null && driverManager != null) {
                String screenshotPath = driverManager.captureScreenshot();
                extentTest.log(Status.FAIL, "Test case failed at " + iTestResult.getThrowable(),
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath.replaceFirst(".", "")).build());
            }
            failedTests.add(iTestResult.getMethod());
        } catch (Exception e) {
            log.error("Failed to log the onTestFailure entry", e);
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        String testName = getTestMethodName(iTestResult);
        log.info("{} test skipped.", testName);
        ExtentTest parentTest = ExtentTestManager.getTest();
        if (parentTest != null) {
            ExtentTest skippedTest = parentTest.createNode(testName);
            skippedTest.log(Status.SKIP, "Test skipped: " + testName);
        }
        skippedTests.add(iTestResult.getMethod());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        log.info("Test failed but within defined success ratio {}", getTestMethodName(iTestResult));
    }

    @Override
    public void onFinish(ISuite suite) {
        String suiteName = ExtentManager.BASE_SUITE_NAME;
        String BaseUrl = ConfigManager.getBaseUrl();

        if (Objects.isNull(suiteStartTime))
            suiteStartTime = Instant.now();
        Instant suiteEndTime = Instant.now();

        long totalTimeInSeconds = Duration.between(suiteStartTime, suiteEndTime).getSeconds();

        String formattedTotalTime = String.format("%02d:%02d",
                totalTimeInSeconds / 60, totalTimeInSeconds % 60);  // Format as mm:ss
        int totalPassed = 0;
        int totalFailed = 0;
        int totalSkipped = 0;

        Map<String, ISuiteResult> suiteResults = suite.getResults();
        JSONReport jsonReport = new JSONReport();
        jsonReport.setName(suiteName);
        
        for (ISuiteResult sr : suiteResults.values()) {
            ITestContext tc = sr.getTestContext();
            totalPassed += tc.getPassedTests().getAllResults().size();
            totalFailed += tc.getFailedTests().getAllResults().size();
            totalSkipped += tc.getSkippedTests().getAllResults().size();
            
            // Add passed tests
            for (ITestResult result : tc.getPassedTests().getAllResults()) {
                JSONReport.TestCase testCase = new JSONReport.TestCase();
                testCase.setClassname(result.getTestClass().getName());
                testCase.setName(getTestMethodName(result));
                testCase.setStatus("PASS");
                testCase.setTime((result.getEndMillis() - result.getStartMillis()) / 1000.0);
                jsonReport.getTestCases().add(testCase);
            }
            
            // Add failed tests
            for (ITestResult result : tc.getFailedTests().getAllResults()) {
                JSONReport.TestCase testCase = new JSONReport.TestCase();
                testCase.setClassname(result.getTestClass().getName());
                testCase.setName(getTestMethodName(result));
                testCase.setStatus("FAIL");
                testCase.setTime((result.getEndMillis() - result.getStartMillis()) / 1000.0);
                jsonReport.getTestCases().add(testCase);
            }
            
            // Add skipped tests
            for (ITestResult result : tc.getSkippedTests().getAllResults()) {
                JSONReport.TestCase testCase = new JSONReport.TestCase();
                testCase.setClassname(result.getTestClass().getName());
                testCase.setName(getTestMethodName(result));
                testCase.setStatus("SKIP");
                testCase.setTime((result.getEndMillis() - result.getStartMillis()) / 1000.0);
                jsonReport.getTestCases().add(testCase);
            }
        }

        int totalTestRun = totalPassed + totalFailed + totalSkipped;
        jsonReport.setTests(totalTestRun);
        jsonReport.setErrors(0); // TestNG doesn't distinguish between failures and errors
        jsonReport.setSkipped(totalSkipped);
        jsonReport.setFailures(totalFailed);

        log.info("Environment: {}", ExtentManager.ENVIRONMENT);
        log.info("Application URL: {}", BaseUrl);
        log.info("Suite Name: {}", suiteName);
        log.info("Total Test Run: {}", totalTestRun);
        log.info("Total Passed: {}", totalPassed);
        log.info("Total Failed: {}", totalFailed);
        log.info("Total Skipped: {}", totalSkipped);
        log.info("Total Time: {}", formattedTotalTime);

        // Create JSON report
        String jsonReportPath = "./test-output/report.json";
        try {
            Files.createDirectories(Paths.get("./test-output"));
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(jsonReportPath), jsonReport);
        } catch (Exception e) {
            log.error("Failed to create the JSON report file", e);
        }
    }


    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        try {
            if (method.isConfigurationMethod() && !testResult.isSuccess()) {
                ExtentTest extentTest = (ExtentTest) testResult.getTestClass()
                        .getRealClass()
                        .getMethod("getCurrentTestReportNode")
                        .invoke(testResult.getInstance());
                DriverManager driverManager = ExtentTestManager.getTestDriverManager();
                if (extentTest != null && driverManager != null) {
                    if (testResult.getThrowable() != null) {
                        String screenshotPath = driverManager.captureScreenshot();
                        extentTest.log(Status.FAIL, "Test case failed at " + testResult.getThrowable(),
                                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath.replace("/test-output/Reports", "")).build());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Failed to log the configuration method failure in Extent Report.", e);
        }
    }
}