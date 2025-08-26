/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.propertyzar.ui.pages.login;

import com.propertyzar.ui.base.WebDriver;
import com.propertyzar.ui.pages.BasePage;

public class LoginPage extends BasePage {
    private static final String LOCATOR_USER_NAME = "[data-testid='emailId']";
    private static final String LOCATOR_PASSWORD = "[data-testid='password']";
    private static final String LOCATOR_LOGIN_BUTTON = "[data-testid='loginBtn']";
    private static final String LOCATOR_HOMESCREEN_VISIBLE = "//*[text()='Open Work Orders']";
    private static final String LOCATOR_LOGIN_MESSAGE_VISIBLE = "//*[text()='Login Successful']";

    public LoginPage(WebDriver<?> webDriver) {
        super(webDriver);
    }

    public void clickLogin() {
        clickElement(LOCATOR_LOGIN_BUTTON, "Login");
        getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
    }

    public void setPassword(String password) {
        setTextElement(LOCATOR_PASSWORD, password, "Password");
    }

    public void setUserName(String email) {
        setTextElement(LOCATOR_USER_NAME, email, "Email");
    }

    public boolean isMessageVisible() {
        return isElementPresent(LOCATOR_LOGIN_MESSAGE_VISIBLE);
    }

    public boolean isHomeScreenVisible() {
        return isElementPresent(LOCATOR_HOMESCREEN_VISIBLE);
    }
}