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

package com.propertyzar.ui.base.playwright;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.propertyzar.ui.ConfigManager;
import com.propertyzar.ui.base.Element;
import com.propertyzar.ui.base.WebDriver;
import com.propertyzar.ui.exception.Exception;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Slf4j
public class PlaywrightWebDriver extends WebDriver<PlaywrightWebDriver> {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;
    private FrameLocator frameLocator;


    public PlaywrightWebDriver() {
        super();
        this.driver = this;
    }

    @Override
    public void init() {
        initializePlaywright();
    }

    private void setPlaywright() {
        this.playwright = Playwright.create();
    }

    private void setBrowser() {
        String binary = ConfigManager.getConfig().getPlaywrightChromeBinary();
        String channel = ConfigManager.getConfig().getPlaywrightChromeChannel();
        boolean headless = Boolean.parseBoolean(ConfigManager.getConfig().getPlaywrightIsHeadless());

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setChannel(channel)
                .setHeadless(headless);

        switch (binary.toLowerCase()) {
            case "webkit":
                browser = playwright.webkit().launch(launchOptions);
                break;
            case "firefox":
                browser = playwright.firefox().launch(launchOptions);
                break;
            default:
                // Enhanced Chrome options for headless execution
                List<String> args = new java.util.ArrayList<>(List.of(
                    "--no-sandbox",
                    "--disable-setuid-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu",
                    "--window-size=1920,1080",
                    "--start-maximized",
                    "--disable-web-security",
                    "--disable-features=VizDisplayCompositor",
                    "--disable-popup-blocking",
                    "--disable-notifications",
                    "--disable-application-cache",
                    "--disable-offline-load-stale-cache",
                    "--disk-cache-size=0",
                    "--memory-pressure-off",
                    "--disable-background-networking",
                    "--disable-background-timer-throttling",
                    "--disable-client-side-phishing-detection",
                    "--disable-default-apps",
                    "--disable-extensions",
                    "--disable-sync",
                    "--disable-translate",
                    "--no-first-run",
                    "--no-default-browser-check",
                    "--disable-backgrounding-occluded-windows",
                    "--disable-renderer-backgrounding",
                    "--disable-blink-features=AutomationControlled"
                ));
                if (headless) {
                    args.add("--headless=new");
                }
                launchOptions.setArgs(args);
                browser = playwright.chromium().launch(launchOptions);
                break;
        }
    }

    private BrowserContext setBrowserContext() {
        Path videoDir = Path.of("videos/");
        if (!videoDir.toFile().exists()) {
            videoDir.toFile().mkdirs();
        }
        BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(null)
                .setIgnoreHTTPSErrors(true)
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .setExtraHTTPHeaders(Map.of(
                        "Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8",
                        "Accept-Language", "en-US,en;q=0.5",
                        "Accept-Encoding", "gzip, deflate",
                        "Connection", "keep-alive",
                        "Upgrade-Insecure-Requests", "1"
                ))
                .setRecordVideoDir(videoDir)
                .setRecordVideoSize(1280, 720)
        );
        log.info("Video recording directory: {}", videoDir.toAbsolutePath());
        return browserContext;
    }

    private void setPage() {
        this.browserContext = setBrowserContext();
        this.page = this.browserContext.newPage();

        //Log JavaScript console errors
        page.onConsoleMessage(msg -> {
            if ("error".equalsIgnoreCase(msg.type())) {
                log.error("JS Console Error: {}", msg.text());
            }
        });

        //Log uncaught JavaScript exceptions
        page.onPageError(error -> {
            log.error("Uncaught JS Error: {}", error);
        });

        //(Optional) Log HTTP response errors like 4xx or 5xx
        page.onResponse(response -> {
            if (response.status() >= 400) {
                log.warn("HTTP Error Response: {} - {}", response.status(), response.url());
            }
        });
    }

    private Page getPageInstance() {
        if (page == null) {
            setPage();
        }
        return page;
    }

    private void initializePlaywright() {
        try {
            log.info("Initializing Playwright...");
            setPlaywright();
            setBrowser();
            setPage();
        } catch (java.lang.Exception e) {
            log.error("Failed to initialize Playwright: ", e);
            throw new Exception("Failed to initialize Playwright", e);
        }
    }

    public Page getPage() {
        Page currentPage = getPageInstance();
        if (currentPage == null) {
            log.error("Page instance is null. Ensure Playwright is initialized correctly.");
            throw new IllegalStateException("Page instance is null");
        }
        return currentPage;
    }

    @Override
    public void navigateURL(String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        getPage().navigate(url);
    }

    @Override
    public Element<?, ?> getElement(ExtentTest etTest, String xpath, Element.ExpectedConditions expConditions, int timeOut) {
        if (xpath == null || xpath.isEmpty()) {
            throw new IllegalArgumentException("XPath cannot be null or empty");
        }
        return new PlaywrightElement(this, etTest, xpath, expConditions, timeOut);
    }

    @Override
    public List<Element<?, ?>> getElements(ExtentTest etTest, String xpath, int timeOut) {
        if (xpath == null || xpath.isEmpty()) {
            throw new IllegalArgumentException("XPath cannot be null or empty");
        }
        return PlaywrightElement.getElements(this, etTest, xpath, timeOut);
    }

    @Override
    public boolean isElementPresent(String locator) {
        if (locator == null || locator.isEmpty()) {
            return false;
        }
        return PlaywrightElement.isElementPresent(this, locator, Integer.parseInt(ConfigManager.getConfig().getDefaultTimeOut()));
    }

    @Override
    public void refresh() {
        getPage().reload();
    }

    @Override
    public void tearDown() {
        try {
            if (browserContext != null) {
                page.evaluate("sessionStorage.clear()");
                page.evaluate("localStorage.clear()");
                browserContext.clearCookies();
                browserContext.close();
            }
            if (browser != null) {
                page.evaluate("sessionStorage.clear()");
                page.evaluate("localStorage.clear()");
                browserContext.clearCookies();
                browser.close();
            }
            if (playwright != null) {
                page.evaluate("sessionStorage.clear()");
                page.evaluate("localStorage.clear()");
                browserContext.clearCookies();
                playwright.close();
            }
        } catch (java.lang.Exception e) {
            log.warn("Exception during teardown: ", e);
        }
    }

    @Override
    public String getCurrentUrl() {
        return getPage().url();
    }

    public Locator getByLocator(String selector) {
        if (selector == null || selector.isEmpty()) {
            throw new IllegalArgumentException("Selector cannot be null or empty");
        }
        
        FrameLocator frame = frameLocator;
        if (frame != null) {
            return frame.locator(selector);
        }
        return getPage().locator(selector);
    }

    @Override
    public void waitForLoad(LoadType loadType) {
        if (loadType == null) {
            throw new IllegalArgumentException("LoadType cannot be null");
        }
        try {
            getPage().waitForLoadState(LoadState.valueOf(loadType.name()));
        } catch (IllegalArgumentException e) {
            log.error("Invalid LoadType specified: {}", loadType);
            throw e;
        }
    }

    @Override
    public void sendKeysByPassingKeyboardShortcutKeys(String shortcutKey) {
        if (shortcutKey == null || shortcutKey.isEmpty()) {
            throw new IllegalArgumentException("Shortcut key cannot be null or empty");
        }
        getPage().keyboard().press(shortcutKey);
    }

    @Override
    public Object evaluate(String expression, Object element) {
        if (expression == null || expression.isEmpty()) {
            throw new IllegalArgumentException("JavaScript expression cannot be null or empty");
        }
        return getPage().evaluate(expression, element);
    }

    @Override
    public void captureScreenShot(Path screenshotPath) {
        if (screenshotPath == null) {
            throw new IllegalArgumentException("Screenshot path cannot be null");
        }
        getPage().screenshot(new Page.ScreenshotOptions()
                .setPath(screenshotPath)
                .setFullPage(true));
    }

    public void resetSession() {
        if (browserContext != null) {
            browserContext.close();
        }
        this.browserContext = setBrowserContext();
        this.page = this.browserContext.newPage();
    }
}