package com.propertyzar.ui;

import com.propertyzar.ui.base.WebDriver;
import com.propertyzar.ui.base.playwright.PlaywrightWebDriver;
import com.propertyzar.ui.exception.Exception;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

@Getter
@Slf4j
public class DriverManager {

    private final WebDriver<?> webDriver;

    public DriverManager() {
        webDriver = new PlaywrightWebDriver();
    }

    public String captureScreenshot() {
        try {
            return captureScreenshotForPlaywright();
        } catch (java.lang.Exception e) {
            log.error("Failed to capture screenshot", e);
            throw new Exception(e);
        }
    }

    private String captureScreenshotForPlaywright() {
        String screenshotFileName = Instant.now().toString().replace(":", "_").replace("T", "_").replace("Z", "") + ".png";
        Path screenshotPath = Paths.get("./test-output/Reports/Screenshots", screenshotFileName);
        getWebDriver().captureScreenShot(screenshotPath);
        return "Screenshots/" + screenshotFileName;
    }
}