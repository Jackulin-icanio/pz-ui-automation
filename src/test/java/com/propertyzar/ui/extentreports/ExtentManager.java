/*
 *
 * Copyright (c) 2025 Propertyzar, Inc.
 *     All rights reserved.
 *
 *     This software and its documentation are confidential and proprietary
 *     information of Propertyzar, Inc. Unauthorized use, duplication,
 *     or distribution is strictly prohibited.
 *
 */

package com.propertyzar.ui.extentreports;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.propertyzar.ui.ConfigManager;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@Slf4j
public class ExtentManager {

    public static final String ENVIRONMENT = System.getProperty("environment", "default");
    private static final String SUITE_XML_FILE = System.getProperty("suiteXmlFile", "default-suite").replaceFirst("^suite/", "");
    public static final String BASE_SUITE_NAME = extractBaseName(SUITE_XML_FILE);
    public static final String REPORT_PATH = String.format("%s/%s_Report_%s_%s.html",
            ConfigManager.getConfig().getSparkReportPath(), BASE_SUITE_NAME, ENVIRONMENT, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss")));

    public static String extractBaseName(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot == -1) {
            return fileName;
        }
        return fileName.substring(0, lastIndexOfDot);
    }

    public synchronized static ExtentReports createExtentReports() {
        String reportFileName = "Propertyzar Automation Testing Report";
        ExtentReports extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(REPORT_PATH);
        reporter.config().setReportName(reportFileName);

        // JavaScript Injection for UI Fix (Optional)
        String jsScript = "var firstElem = document.evaluate(\"//h6[text()='Test'] //following::div\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;"
                + "var text = document.evaluate(\"//a[@id='nav-test']\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;"
                + "firstElem.addEventListener('click', function() { text.focus(); });";

        reporter.config().setJs(jsScript);
        extentReports.attachReporter(reporter);
        extentReports.setAnalysisStrategy(AnalysisStrategy.SUITE);

        return extentReports;
    }
}
