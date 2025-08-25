/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.propertyzar.ui.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.propertyzar.ui.ConfigManager;
import com.propertyzar.ui.base.Element;
import com.propertyzar.ui.base.WebDriver;
import com.propertyzar.ui.exception.DCMException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Base class for all page objects in the test automation framework.
 * Provides common functionality for interacting with web elements.
 */
@Slf4j
public class BasePage {

    public static final int MID_LEVEL_TIMEOUT = Integer.parseInt(ConfigManager.getConfig().getMidLevelTimeout());
    public static final int MAX_RETRY_ATTEMPTS = Integer.parseInt(ConfigManager.getConfig().getMaxElementAttempts());
    private static final String LOCATOR_DROPDOWN_DATA = "%s//*[@title='%s']";
    private static final String VERIFY_CHECKED_STATE = "checked";
    private static final int DEFAULT_TIMEOUT = Integer.parseInt(ConfigManager.getConfig().getDefaultTimeOut());
    private static final int TIMEOUT = Integer.parseInt(ConfigManager.getConfig().getTimeOut());

    public static final String LOCATOR_PARTY_BUTTON = "ul.navbar-nav.top-nav li a#Party span";
    public static final String LOCATOR_PARTY_IFRAME = "frame[name='navbar']";
    public static final String LOCATOR_NAVBAR_MENUS = "//ul[@id='navigation']//li/a[@id='Party']";

    public static final String FRAME_CONTAINER = "frame[name='container']";
    public static final String FRAME_CACHE = "iframe[name='cacheframe0']";
    public static final String FRAME_NOCACHE_FRAME = "iframe[name='nocacheframe']";
    public static final String FRAME_SUBPAGE = "frame[name='subpage']";
    public static final String LOCATOR_CREATE_PERSON_LINK = "//a[@id= 'Button_Person_Main_NewPerson']";
    public static final String LOCATOR_HIERARCHY_BUTTON = "//ul[@id='navigation']//span[text()='Hierarchy']";
    public static final String LOCATOR_NEW_HIERARCHY = "//*[@id='Button_HierarchySearch_NewHierarchy']";
    public static final String LOCATOR_DCM_ADMIN_BUTTON = "//ul[@id='navigation']//span[text()='DCM Admin']";
    public static final String LOCATOR_DCM_ADMIN_SEARCH_BUTTON = "//*[@id= 'Search_Audit_AuditInfo_SearchFormDiv']//*[text()= 'Search']";
    public static final String LOCATOR_COMPENSATION_BUTTON = "//ul[@id='navigation']//span[text()='Compensation']";
    public static final String LOCATOR_COMPENSATION_SEARCH_BUTTON = "//*[@id= 'Search_Schedules_Main_SearchFormDiv']//*[text()= 'Search']";
    private static final String FRAME_SIDE_BAR = "frame[name='sidebar']";
    private static final String LOCATOR_CONTRACT_KIT = "//a[@id='Contracts_sub']";
    private static final String LOCATOR_SUB_SUPPORTING_DATA = "//a[@id= 'SupportingData_sub']";
    private static final String LOCATOR_LEFT_SIDE_NAVE_BAR = "//*[@id=\"sideMenu\"]//li";
    private static final String LOCATOR_CONTRACT_KIT_INFO = "//ul[@id= 'sideMenu']//a[@id= 'Tab_Contracts_Main_BasicInfo_link']";
    private static final String LOCATOR_SUB_PRODUCT_SEARCH_FRAME = "frame[name='sidebar']";
    private static final String LOCATOR_SUB_PRODUCT_SEARCH = "//a[@id='ProductHierarchySearch_sub']";
    private static final String LOCATOR_VALIDATE_BUTTON = "//*[@id='validate']";
    private static final String LOCATOR_VALIDATE_BUTTON_LI = "//ul[@class='nav nav-pills btn-nav navbar-left']/li[3]";
    private static final String LOCATOR_CANCEL_BUTTON = "//a[@id='cancel']";
    private static final String LOCATOR_SAVE_BUTTON = "//a[@id= 'save']";
    public static final String FRAME_PROP_PAGE = "frame[name='proppage']";
    public static final String FRAME_CACHE2 = "iframe[name='cacheframe2']";
    public static final String FRAME_SEARCH = "iframe[name='NewSchedule_Product_Picker_search_div_frame']";



    @Getter
    private final WebDriver<?> webDriver;

    @Getter
    @Setter
    private ExtentTest currentTestReportNode;

    /**
     * Constructor for BasePage
     *
     * @param webDriver WebDriver instance to use for this page
     * @throws IllegalArgumentException if webDriver is null
     */
    public BasePage(WebDriver<?> webDriver) {
        if (webDriver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        this.webDriver = webDriver;
    }

    /**
     * Converts a field name to a locator by replacing spaces with underscores and
     * converting to lowercase.
     *
     * @param fieldName Field name to convert
     * @return Converted locator string
     * @throws DCMException if conversion fails
     */
    public static String convertIntoLocator(String fieldName) {
        if (fieldName == null || fieldName.isEmpty()) {
            throw new IllegalArgumentException("Field name cannot be null or empty");
        }

        try {
            return fieldName.toLowerCase().replace(" ", "_");
        } catch (Exception e) {
            log.error("Failed to convert field name to locator: {}", fieldName, e);
            throw new DCMException("Locator conversion failed", e);
        }
    }

    /**
     * Converts a field name to a locator by replacing spaces with hyphens and
     * converting to lowercase.
     *
     * @param fieldName Field name to convert
     * @return Converted locator string
     * @throws DCMException if conversion fails
     */
    public static String convertIntoLocatorUsingHyphen(String fieldName) {
        if (fieldName == null || fieldName.isEmpty()) {
            throw new IllegalArgumentException("Field name cannot be null or empty");
        }

        try {
            return fieldName.toLowerCase().replace(" ", "-");
        } catch (Exception e) {
            log.error("Failed to convert field name to hyphenated locator: {}", fieldName, e);
            throw new DCMException("Hyphenated locator conversion failed", e);
        }
    }

    /**
     * Gets an element with default wait type, expected conditions, and timeout
     *
     * @param xpath XPath locator for the element
     * @return Element instance
     */
    public Element<?, ?> getElement(String xpath) {
        validateXpath(xpath);
        return getElement(xpath, DEFAULT_TIMEOUT);
    }

    public Element<?, ?> getElement(String xpath, int timeout) {
        validateXpath(xpath);
        return getElement(xpath, Element.ExpectedConditions.VISIBILITY_OF_ALL_ELEMENTS_LOCATED, timeout);
    }

    /**
     * Gets an element with specified expected conditions and default wait type and timeout
     *
     * @param xpath         XPath locator for the element
     * @param expConditions Expected conditions for waiting
     * @return Element instance
     */
    public Element<?, ?> getElement(String xpath, Element.ExpectedConditions expConditions) {
        validateXpath(xpath);
        validateExpConditions(expConditions);
        return getElement(xpath, expConditions, TIMEOUT);
    }

    /**
     * Gets an element with specified parameters
     *
     * @param xpath         XPath locator for the element
     * @param expConditions Expected conditions for waiting
     * @param timeout       Timeout in seconds
     * @return Element instance
     * @throws DCMException if element retrieval fails
     */
    public Element<?, ?> getElement(String xpath, Element.ExpectedConditions expConditions,
                                    int timeout) {
        validateXpath(xpath);
        validateExpConditions(expConditions);
        validateTimeout(timeout);

        try {
            ExtentTest testNode = Optional.ofNullable(getCurrentTestReportNode())
                    .orElseThrow(() -> new IllegalStateException("Test report node is not set"));

            return webDriver.getElement(testNode, xpath, expConditions, timeout);
        } catch (Exception e) {
            log.error("Failed to get element with xpath: {}", xpath, e);
            throw new DCMException("Element retrieval failed for xpath: " + xpath, e);
        }
    }

    /**
     * Gets multiple elements with default wait type and timeout
     *
     * @param xpath XPath locator for the elements
     * @return List of Element instances
     */
    public List<Element<?, ?>> getElements(String xpath) {
        validateXpath(xpath);
        return getElements(xpath, TIMEOUT);
    }

    /**
     * Gets multiple elements with specified wait type and timeout
     *
     * @param xpath   XPath locator for the elements
     * @param timeout Timeout in seconds
     * @return List of Element instances
     * @throws DCMException if elements retrieval fails
     */
    public List<Element<?, ?>> getElements(String xpath,
                                           int timeout) {
        validateXpath(xpath);
        validateTimeout(timeout);

        try {
            ExtentTest testNode = Optional.ofNullable(getCurrentTestReportNode())
                    .orElseThrow(() -> new IllegalStateException("Test report node is not set"));

            return webDriver.getElements(testNode, xpath, timeout);
        } catch (Exception e) {
            log.error("Failed to get elements with xpath: {}", xpath, e);
            throw new DCMException("Elements retrieval failed for xpath: " + xpath, e);
        }
    }

    /**
     * Checks if an element is present
     *
     * @param xpath XPath locator for the element
     * @return true if the element is present, false otherwise
     */
    public boolean isElementPresent(String xpath) {
        if (xpath == null || xpath.isEmpty()) {
            log.warn("XPath is null or empty in isElementPresent");
            return false;
        }

        try {
            return webDriver.isElementPresent(xpath);
        } catch (Exception e) {
            log.error("Failed to check element presence: {}", xpath, e);
            return false;
        }
    }

    /**
     * Navigates to the specified URL
     *
     * @param url URL to navigate to
     * @throws DCMException if navigation fails
     */
    public void navigateURL(String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        try {
            webDriver.navigateURL(url);
            log.info("Navigated to URL: {}", url);
        } catch (Exception e) {
            log.error("Failed to navigate to URL: {}", url, e);
            throw new DCMException("Navigation failed to URL: " + url, e);
        }
    }

    /**
     * Logs an info message to the test report
     *
     * @param infoMsg Message to log
     */
    public void info(String infoMsg) {
        if (infoMsg == null) {
            infoMsg = ""; // Set to empty string if null
        }

        try {
            ExtentTest testNode = getCurrentTestReportNode();
            if (testNode != null) {
                testNode.info(infoMsg);
            }
            log.info(infoMsg);
        } catch (Exception e) {
            log.error("Failed to log info message: {}", infoMsg, e);
        }
    }

    /**
     * Logs a message with the specified status to the test report
     *
     * @param status Status of the message
     * @param msg    Message to log
     */
    public void log(Status status, String msg) {
        if (status == null) {
            status = Status.INFO; // Default to INFO if null
        }

        if (msg == null) {
            msg = ""; // Set to empty string if null
        }

        try {
            ExtentTest testNode = getCurrentTestReportNode();
            if (testNode != null) {
                testNode.log(status, msg);
            }
            log.info("Logged message with status {}: {}", status, msg);
        } catch (Exception e) {
            log.error("Failed to log message: {}", msg, e);
        }
    }

    /**
     * Clicks an element with default wait type, expected conditions, and timeout
     *
     * @param xpath XPath locator for the element
     * @param field Field name for logging
     */
    public void clickElement(String xpath, String field) {
        validateXpath(xpath);
        validateField(field);

        clickElement(xpath,
                Element.ExpectedConditions.ELEMENT_TO_BE_CLICKABLE,
                DEFAULT_TIMEOUT,
                field);
    }

    /**
     * Clicks an element with specified parameters
     *
     * @param xpath         XPath locator for the element
     * @param expConditions Expected conditions for waiting
     * @param timeout       Timeout in seconds
     * @param field         Field name for logging
     * @throws DCMException if element click fails
     */
//    public void clickElement(String xpath,
//                             Element.ExpectedConditions expConditions,
//                             int timeout,
//                             String field) {
//        validateXpath(xpath);
//        validateExpConditions(expConditions);
//        validateTimeout(timeout);
//        validateField(field);
//
//        try {
//            ExtentTest testNode = Optional.ofNullable(getCurrentTestReportNode())
//                    .orElseThrow(() -> new IllegalStateException("Test report node is not set"));
//
//            webDriver.clickElement(testNode, xpath,
//                    expConditions,
//                    timeout,
//                    field
//            );
//            log.info("Clicked element: {}", field);
//        } catch (Exception e) {
//            log.error("Failed to click element: {} with xpath: {}", field, xpath, e);
//            throw new DCMException("Element click failed for: " + field, e);
//        }
//    }

    public void clickElement(String xpath,
                             Element.ExpectedConditions expConditions,
                             int timeout,
                             String field) {
        validateXpath(xpath);
        validateExpConditions(expConditions);
        validateTimeout(timeout);
        validateField(field);

        try {
            log.info("Current URL before click: {}", webDriver.getCurrentUrl());
            boolean isVisible = webDriver.isElementPresent(xpath);
            if (!isVisible) {
                log.error("Element with xpath [{}] is not visible after waiting {} ms. Field: {}", xpath, timeout, field);
//            log.error("Page source at failure:\n{}", webDriver.getPageSource());
            }

            ExtentTest testNode = Optional.ofNullable(getCurrentTestReportNode())
                    .orElseThrow(() -> new IllegalStateException("Test report node is not set"));

            webDriver.clickElement(testNode, xpath, expConditions, timeout, field);
            log.info("Clicked element: {}", field);

            // 💡 Added logs after click
            String afterClickUrl = webDriver.getCurrentUrl();
            log.info("Current URL after click: {}", afterClickUrl);

        } catch (Exception e) {
            log.error("Failed to click element: {} with xpath: {}", field, xpath, e);
            throw new DCMException("Element click failed for: " + field, e);
        }
    }
    /**
     * Sets text in an element with default wait type, expected conditions, and timeout
     *
     * @param xpath     XPath locator for the element
     * @param text      Text to set
     * @param fieldName Field name for logging
     * @return true if text was set successfully, false otherwise
     */
    public boolean setTextElement(String xpath,
                                  String text,
                                  String fieldName) {
        validateXpath(xpath);
        validateField(fieldName);

        return setTextElement(xpath,
                Element.ExpectedConditions.VISIBILITY_OF_ALL_ELEMENTS_LOCATED,
                DEFAULT_TIMEOUT,
                text,
                fieldName
        );
    }

    /**
     * Sets text in an element with specified parameters
     *
     * @param xpath         XPath locator for the element
     * @param expConditions Expected conditions for waiting
     * @param timeout       Timeout in seconds
     * @param text          Text to set
     * @param fieldName     Field name for logging
     * @return true if text was set successfully, false otherwise
     * @throws DCMException if text setting fails
     */
    public boolean setTextElement(String xpath,
                                  Element.ExpectedConditions expConditions,
                                  int timeout,
                                  String text,
                                  String fieldName) {
        validateXpath(xpath);
        validateExpConditions(expConditions);
        validateTimeout(timeout);
        validateField(fieldName);

        try {
            ExtentTest testNode = Optional.ofNullable(getCurrentTestReportNode())
                    .orElseThrow(() -> new IllegalStateException("Test report node is not set"));

            boolean isSet = webDriver.setTextElement(testNode, xpath,
                    expConditions,
                    timeout,
                    text,
                    fieldName
            );
            if (isSet) {
                log.info("Set text [{}] in element: {}", text, fieldName);
            } else {
                log.warn("Failed to set text [{}] in element: {}", text, fieldName);
            }
            return isSet;
        } catch (Exception e) {
            log.error("Failed to set text [{}] in element: {} with xpath: {}", text, fieldName, xpath, e);
            throw new DCMException("Text setting failed for: " + fieldName, e);
        }
    }

    public boolean setTextElement(String xpath,
                                  int timeout,
                                  String text,
                                  String fieldName) {
        validateXpath(xpath);
        validateTimeout(timeout);
        validateField(fieldName);

        try {
            ExtentTest testNode = Optional.ofNullable(getCurrentTestReportNode())
                    .orElseThrow(() -> new IllegalStateException("Test report node is not set"));

            boolean isSet = webDriver.setTextElement(testNode, xpath,
                    Element.ExpectedConditions.PRESENCE_OF_ELEMENT_LOCATED,
                    timeout,
                    text,
                    fieldName
            );
            if (isSet) {
                log.info("Set text [{}] in element: {}", text, fieldName);
            } else {
                log.warn("Failed to set text [{}] in element: {}", text, fieldName);
            }
            return isSet;
        } catch (Exception e) {
            log.error("Failed to set text [{}] in element: {} with xpath: {}", text, fieldName, xpath, e);
            throw new DCMException("Text setting failed for: " + fieldName, e);
        }
    }

    /**
     * Gets text from an element with default wait type, expected conditions, and timeout
     *
     * @param xpath             XPath locator for the element
     * @param validationMessage
     * @return Text from the element
     */
    public String getText(String xpath, String validationMessage) {
        validateXpath(xpath);
        return getText(xpath, DEFAULT_TIMEOUT);
    }

    public String getText(String xpath, int timeout) {
        return getText(xpath,
                Element.ExpectedConditions.VISIBILITY_OF_ALL_ELEMENTS_LOCATED,
                timeout
        );
    }

    /**
     * Gets text from an element with specified expected conditions and default wait type
     *
     * @param xpath         XPath locator for the element
     * @param expConditions Expected conditions for waiting
     * @return Text from the element
     */
    public String getText(String xpath,
                          Element.ExpectedConditions expConditions) {
        validateXpath(xpath);
        validateExpConditions(expConditions);

        return getText(xpath,
                expConditions,
                TIMEOUT
        );
    }

    /**
     * Gets text from an element with specified parameters
     *
     * @param xpath         XPath locator for the element
     * @param expConditions Expected conditions for waiting
     * @param timeout       Timeout in seconds
     * @return Text from the element
     * @throws DCMException if text retrieval fails
     */
    public String getText(String xpath,
                          Element.ExpectedConditions expConditions,
                          int timeout) {
        validateXpath(xpath);
        validateExpConditions(expConditions);
        validateTimeout(timeout);

        try {
            ExtentTest testNode = Optional.ofNullable(getCurrentTestReportNode())
                    .orElseThrow(() -> new IllegalStateException("Test report node is not set"));

            String retrieveText = webDriver.getText(testNode, xpath,
                    expConditions,
                    timeout
            );
            info("Fetched text: " + retrieveText);
            return retrieveText;
        } catch (Exception e) {
            log.error("Failed to get text from element with xpath: {}", xpath, e);
            throw new DCMException("Text retrieval failed for xpath: " + xpath, e);
        }
    }

    /**
     * Clicks an element
     *
     * @param element Element to click
     * @param field   Field name for logging
     * @throws DCMException if element click fails
     */
    public void clickElement(Element<?, ?> element, String field) {
        validateElement(element);
        validateField(field);

        try {
            webDriver.clickElement(element, field);
            log.info("Clicked element: {}", field);
        } catch (Exception e) {
            log.error("Failed to click element: {}", field, e);
            throw new DCMException("Element click failed for: " + field, e);
        }
    }

    /**
     * Sets text in an element
     *
     * @param element   Element to set text in
     * @param text      Text to set
     * @param fieldName Field name for logging
     * @throws DCMException if text setting fails
     */
    public void setTextElement(Element<?, ?> element, String text, String fieldName) {
        validateElement(element);
        validateField(fieldName);

        try {
            webDriver.setTextElement(element, text, fieldName);
            log.info("Set text [{}] in element: {}", text, fieldName);
        } catch (Exception e) {
            log.error("Failed to set text [{}] in element: {}", text, fieldName, e);
            throw new DCMException("Text setting failed for: " + fieldName, e);
        }
    }

    /**
     * Checks if an element is not present
     *
     * @param xpath XPath locator for the element
     * @return true if the element is not present, false otherwise
     */
    public boolean isElementNotPresent(String xpath) {
        if (xpath == null || xpath.isEmpty()) {
            log.warn("XPath is null or empty in isElementNotPresent");
            return false;
        }

        try {
            boolean isDisplayedElement = isElementInvisible(xpath);

            if (isDisplayedElement) {
                String message = String.format("PASS: Checked element [%s] is not present", xpath);
                log(Status.INFO, message);
                return true;
            } else {
                String message = String.format("FAIL: Element [%s] is present on the screen!", xpath);
                log(Status.FAIL, message);
                return false;
            }
        } catch (Exception e) {
            log.error("Failed to check element absence: {}", xpath, e);
            return false;
        }
    }

    /**
     * Checks if an element is enabled with default wait type, expected conditions, and timeout
     *
     * @param xpath XPath locator for the element
     * @return true if the element is enabled, false otherwise
     */
    public boolean isEnabled(String xpath) {
        validateXpath(xpath);
        return isEnabled(xpath,
                DEFAULT_TIMEOUT
        );
    }

    public boolean isEnabled(String xpath, int timeout) {
        validateXpath(xpath);

        return isEnabled(xpath,
                timeout
        );
    }

    /**
     * Checks if an element is enabled with specified wait type and expected conditions
     *
     * @param xpath         XPath locator for the element
     * @param expConditions Expected conditions for waiting
     * @return true if the element is enabled, false otherwise
     */
    public boolean isEnabled(String xpath,
                             Element.ExpectedConditions expConditions) {
        validateXpath(xpath);
        validateExpConditions(expConditions);

        return isEnabled(xpath,
                expConditions,
                DEFAULT_TIMEOUT
        );
    }

    /**
     * Checks if an element is enabled with specified parameters
     *
     * @param xpath         XPath locator for the element
     * @param expConditions Expected conditions for waiting
     * @param timeout       Timeout in seconds
     * @return true if the element is enabled, false otherwise
     */
    public boolean isEnabled(String xpath,
                             Element.ExpectedConditions expConditions,
                             int timeout) {
        validateXpath(xpath);
        validateExpConditions(expConditions);
        validateTimeout(timeout);

        try {
            ExtentTest testNode = Optional.ofNullable(getCurrentTestReportNode())
                    .orElseThrow(() -> new IllegalStateException("Test report node is not set"));

            boolean enabled = webDriver.isEnabled(testNode, xpath,
                    expConditions,
                    timeout
            );
            info("Element enabled state: " + enabled);
            return enabled;
        } catch (Exception e) {
            log.error("Failed to check element enabled state for xpath: {}", xpath, e);
            return false;
        }
    }

    /**
     * Checks if an element is invisible
     *
     * @param xpath XPath locator for the element
     * @return true if the element is invisible, false otherwise
     */
    public boolean isElementInvisible(String xpath) {
        if (xpath == null || xpath.isEmpty()) {
            log.warn("XPath is null or empty in isElementInvisible");
            return false;
        }

        try {
            getElement(xpath,

                    MID_LEVEL_TIMEOUT);
            log(Status.INFO, "Element is not visible");
            return true;
        } catch (Exception e) {
            log(Status.INFO, "Element is still visible");
            return false;
        }
    }

    /**
     * Refreshes the page
     *
     * @throws DCMException if page refresh fails
     */
    public void refresh() {
        try {
            webDriver.refresh();
            log.info("Page refreshed successfully");
        } catch (Exception e) {
            log.error("Failed to refresh page", e);
            throw new DCMException("Page refresh failed", e);
        }
    }

    /**
     * Selects data from a dropdown
     *
     * @param dropdownXpath XPath locator for the dropdown
     * @param dropdownData  Data to select
     * @throws DCMException if dropdown selection fails
     */
    public void selectDropdownData(String dropdownXpath, String dropdownData) {
        validateXpath(dropdownXpath);
        if (dropdownData == null || dropdownData.isEmpty()) {
            throw new IllegalArgumentException("Dropdown data cannot be null or empty");
        }

        try {
            clickElement(dropdownXpath, "Dropdown");
            String xPath = String.format(LOCATOR_DROPDOWN_DATA, dropdownXpath, dropdownData);
            clickElement(xPath, "Dropdown Data");
            log.info("Selected dropdown data: {}", dropdownData);
        } catch (Exception e) {
            log.error("Failed to select dropdown data: {} from dropdown: {}", dropdownData, dropdownXpath, e);
            throw new DCMException("Dropdown selection failed for: " + dropdownData, e);
        }
    }

    /**
     * Selects an element (checks if not already checked)
     *
     * @param selectElementLocator XPath locator for the element
     * @throws DCMException if element selection fails
     */
    public void selectElement(String selectElementLocator) {
        validateXpath(selectElementLocator);

        try {
            Element<?, ?> selectElement = getElement(selectElementLocator);
            clickElement(selectElementLocator, "Select Element");

            // Check if element is selected after first click
            String elementState = selectElement.getAttribute("checked");
            if (!Objects.equals(elementState, VERIFY_CHECKED_STATE)) {
                // If not selected, click again
                clickElement(selectElementLocator, "Select Element");

                // Verify selection after second click
                elementState = selectElement.getAttribute("checked");
                if (!Objects.equals(elementState, VERIFY_CHECKED_STATE)) {
                    log.warn("Element still not selected after second click: {}", selectElementLocator);
                }
            }

            log.info("Selected element: {}", selectElementLocator);
        } catch (Exception e) {
            log.error("Failed to select element: {}", selectElementLocator, e);
            throw new DCMException("Element selection failed for: " + selectElementLocator, e);
        }
    }

    /**
     * Gets the current URL and logs it
     *
     * @return The current URL
     * @throws DCMException if URL retrieval fails
     */
    public String getCurrentUrl() {
        try {
            String url = webDriver.getCurrentUrl();
            log.info("Current URL: {}", url);
            return url;
        } catch (Exception e) {
            log.error("Failed to get current URL", e);
            throw new DCMException("URL retrieval failed", e);
        }
    }

    /**
     * Validates that an XPath locator is not null or empty
     *
     * @param xpath XPath locator to validate
     * @throws IllegalArgumentException if XPath is null or empty
     */
    private void validateXpath(String xpath) {
        if (xpath == null || xpath.isEmpty()) {
            throw new IllegalArgumentException("XPath cannot be null or empty");
        }
    }

    /**
     * Validates that expected conditions are not null
     *
     * @param expConditions Expected conditions to validate
     * @throws IllegalArgumentException if expected conditions are null
     */
    private void validateExpConditions(Element.ExpectedConditions expConditions) {
        if (expConditions == null) {
            throw new IllegalArgumentException("Expected conditions cannot be null");
        }
    }

    /**
     * Validates that a timeout value is positive
     *
     * @param timeout Timeout value to validate
     * @throws IllegalArgumentException if timeout is not positive
     */
    private void validateTimeout(int timeout) {
        if (timeout <= 0) {
            throw new IllegalArgumentException("Timeout must be greater than zero");
        }
    }

    /**
     * Validates that a field name is not null or empty
     *
     * @param field Field name to validate
     * @throws IllegalArgumentException if field name is null or empty
     */
    private void validateField(String field) {
        if (field == null || field.isEmpty()) {
            throw new IllegalArgumentException("Field name cannot be null or empty");
        }
    }

    /**
     * Validates that an element is not null
     *
     * @param element Element to validate
     * @throws IllegalArgumentException if element is null
     */
    private void validateElement(Element<?, ?> element) {
        if (element == null) {
            throw new IllegalArgumentException("Element cannot be null");
        }
    }

    /**
     * Generates a random name by appending a UUID-based suffix to the given prefix.
     *
     * @param name   The base name (not used for generation, kept for compatibility).
     * @param prefix The prefix to be attached before the random suffix.
     * @return A randomly generated name with the given prefix.
     */
    public String randomName(String name, String prefix) {
        name = prefix + UUID.randomUUID().toString().substring(0, 6);
        log.info("RandomName: " + name);
        return name;
    }

    /**
     * Generates a random 9-digit Tax ID.
     *
     * @return A randomly generated integer representing a Tax ID.
     */
    public int randomTaxID() {
        Random random = new Random();
        int taxId = 100_000_000 + random.nextInt(900_000_000);
        log.info("TaxId: " + taxId);
        return taxId;
    }

    public void scrollIntoView(String scrollElementLocator) {
        Element<?, ?> selectElement = getElement(scrollElementLocator);
        selectElement.scrollIntoView();
    }


    public boolean isPartyPageDisplayed() {
        return isElementPresent(LOCATOR_CREATE_PERSON_LINK);
    }

    public boolean isNavBarMenuVisible() {
        getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
        getWebDriver().switchToFrames(LOCATOR_PARTY_IFRAME);
        return isElementPresent(LOCATOR_NAVBAR_MENUS);
    }

    public boolean isLeftNaveBarMenuVisible() {
        getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
        getWebDriver().switchToFrames(FRAME_SIDE_BAR);
        return isElementPresent(LOCATOR_LEFT_SIDE_NAVE_BAR);
    }

    public boolean isDcmAdminPageDisplayed(){
        return isElementPresent(LOCATOR_DCM_ADMIN_SEARCH_BUTTON);
    }


    private void performNavigation(String iframeLocator, String buttonLocator, String buttonName, String subpageLocator) {
        int attempts = 0;
        while (attempts < MAX_RETRY_ATTEMPTS) {
            try {
                getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
                getWebDriver().switchToFrames(iframeLocator);
                isNavBarMenuVisible();
                getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
                clickElement(buttonLocator, buttonName);
                getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
                isLeftNaveBarMenuVisible();
                getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHE, FRAME_SUBPAGE);
                isElementPresent(subpageLocator);
                break;
            } catch (Exception e) {
                log.error("Error occurred in performNavigation method for {}. Attempting refresh. Attempt: {}", buttonName, attempts + 1, e);
                refresh();
                attempts++;
            }
        }
        if (attempts == MAX_RETRY_ATTEMPTS) {
            throw new RuntimeException("Failed to execute navigation logic for " + buttonName + " after " + MAX_RETRY_ATTEMPTS + " attempts.");
        }
    }

    public void clickParty() {
        performNavigation(LOCATOR_PARTY_IFRAME, LOCATOR_PARTY_BUTTON, "Party", LOCATOR_CREATE_PERSON_LINK);
    }

    public void clickHierarchy() {
        performNavigation(LOCATOR_PARTY_IFRAME, LOCATOR_HIERARCHY_BUTTON, "Hierarchy", LOCATOR_NEW_HIERARCHY);
    }

    public void clickDcmAdmin() {
        performNavigation(LOCATOR_PARTY_IFRAME, LOCATOR_DCM_ADMIN_BUTTON, "Dcm admin", LOCATOR_DCM_ADMIN_SEARCH_BUTTON);
    }

    public void clickCompensation(){
        performNavigation(LOCATOR_PARTY_IFRAME, LOCATOR_COMPENSATION_BUTTON, "Compensation", LOCATOR_COMPENSATION_SEARCH_BUTTON);
    }

    public void clickContractKit() {
        int attempts = 0;
        while (attempts < MAX_RETRY_ATTEMPTS) {
            try {
                getWebDriver().switchToFrames(FRAME_SIDE_BAR);
                clickElement(LOCATOR_CONTRACT_KIT, "Click contract kit");
                getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
                clickElement(LOCATOR_CONTRACT_KIT_INFO, "Click contract kit info");
                break;
            } catch (Exception e) {
                log.error("Error occurred in clickContractKit method. Attempting refresh. Attempt: {}", attempts + 1, e);
                refresh();
                attempts++;
            }
        }
        if (attempts == MAX_RETRY_ATTEMPTS) {
            throw new RuntimeException("Failed to execute clickContractKit logic after " + MAX_RETRY_ATTEMPTS + " attempts.");
        }
    }

    public void clickSubProductSearch() {
        int attempts = 0;
        while (attempts < MAX_RETRY_ATTEMPTS) {
            try {
                getWebDriver().switchToFrames(LOCATOR_SUB_PRODUCT_SEARCH_FRAME);
                clickElement(LOCATOR_SUB_PRODUCT_SEARCH, "Sub product search");
                getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
                break;
            } catch (Exception e) {
                log.error("Error occurred in clickSubProductSearch method. Attempting refresh. Attempt: {}", attempts + 1, e);
                refresh();
                attempts++;
            }
        }
        if (attempts == MAX_RETRY_ATTEMPTS) {
            throw new RuntimeException("Failed to execute clickSubProductSearch logic after " + MAX_RETRY_ATTEMPTS + " attempts.");
        }
    }

    public void clickValidateButton() {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_NOCACHE_FRAME, FRAME_PROP_PAGE);
        clickElement(LOCATOR_VALIDATE_BUTTON, "Validate button");
        getWebDriver().waitForLoad();
    }

    public void clickCancelButton() {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_NOCACHE_FRAME, FRAME_PROP_PAGE);
        clickElement(LOCATOR_CANCEL_BUTTON, "Cancel button");
        getWebDriver().waitForLoad();
    }

    public void clickSaveButton() {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_NOCACHE_FRAME, FRAME_PROP_PAGE);
        clickElement(LOCATOR_SAVE_BUTTON, "Save button");
    }

    public void clickValidateButtonWithCache() {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHE2, FRAME_PROP_PAGE);
        clickElement(LOCATOR_VALIDATE_BUTTON, "Validate button");
        getWebDriver().waitForLoad();
    }

    public void clickSaveButtonWithCache() {
        getWebDriver().switchToFrames(FRAME_CONTAINER, FRAME_CACHE2, FRAME_PROP_PAGE);
        clickElement(LOCATOR_SAVE_BUTTON, "Save button");
    }
}