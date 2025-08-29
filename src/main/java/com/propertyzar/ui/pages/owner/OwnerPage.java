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

package com.propertyzar.ui.pages.owner;
import com.propertyzar.ui.base.WebDriver;
import com.propertyzar.ui.pages.BasePage;

public class OwnerPage extends BasePage {

    //Messages
    public static final String EXPECTED_MSG_FOR_VALIDATE = "Owner Deleted Successfully";

    //Locators
    private static final String LOCATOR_OWNERS_SIDE_NAV = "//div[@data-testid='Owners']";
    private static final String LOCATOR_NEW_OWNER = "//span[text()='New Owner']";
    private static final String LOCATOR_OWNER_TYPE = "//*[@id='ownerType' and @value='Individual']";
    private static final String LOCATOR_FIRST_NAME = "//input[@id='firstName']";
    private static final String LOCATOR_LAST_NAME = "//input[@id='lastName'] ";
    private static final String LOCATOR_EMAIL = "//input[@id='email']";
    private static final String LOCATOR_ALTERNATIVE_EMAIL = "//input[@id='alternativeEmail']";
    private static final String LOCATOR_ADDRESS1 = "//input[@id='addressLineOne']";
    private static final String LOCATOR_CITY = "[data-testid='city']";
    private static final String LOCATOR_STATE = "[id='state']";
    private static final String LOCATOR_STATE_VALUE = "//*[@id='state-listbox']";
    private static final String LOCATOR_ZIP = "[id='zip']";
    private static final String LOCATOR_SAVE = "//button[span[text()='Save']]";
    private static final String LOCATOR_CREDENTIAL_POPUP = "//*[contains(@role,'dialog')]";
    private static final String LOCATOR_YES_POPUP = "//button[span[text()='Yes']]";
    private static final String LOCATOR_OWNER_NAME_IN_TABLE = "//*[@data-testid='tableRow']//*[text()='%s']";
    private static final String LOCATOR_OWNER_MENU = "//tr[.//p[(text()= '%s')]]//button[@id='long-button']";
    private static final String LOCATOR_DELETE = "//div[contains(@class,'MuiPopover-root') and @aria-hidden='false']//li[@role='menuitem' and normalize-space()='Delete']";
    private static final String LOCATOR_CONFIRM = "//*[text()='Confirm']";
    private static final String LOCATOR_OWNER_DELETED_MESSAGE_VISIBLE = "//*[text()='Owner Deleted Successfully']";

    public OwnerPage(WebDriver<?> webDriver) {
        super(webDriver);
    }

    public void clickOwnersSideNav() {
        clickElement(LOCATOR_OWNERS_SIDE_NAV, "Owners Side Nav");
        getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
        isElementPresent(LOCATOR_NEW_OWNER);
    }

    public void clickNewOwnerButton() {
        clickElement(LOCATOR_NEW_OWNER, "New Owner Button");
        getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
        isElementPresent(LOCATOR_OWNER_TYPE);
    }

    public void enterFirstName(String firstName) {
        setTextElement(LOCATOR_FIRST_NAME, firstName, "Enter First name");
    }

    public void enterLastName(String lastName) {
        setTextElement(LOCATOR_LAST_NAME, lastName, "Enter Last name");
    }

    public void enterEmail(String email) {
        setTextElement(LOCATOR_EMAIL, email, "Enter Email ");
    }

    public void enterAlternativeEmail(String alternativeEmail) {
        setTextElement(LOCATOR_ALTERNATIVE_EMAIL, alternativeEmail, "Enter Alternative Email ");
    }

    public void enterAddress1(String address1) {
        setTextElement(LOCATOR_ADDRESS1, address1, "Address1");
    }

    public void enterCity(String city) {
        setTextElement(LOCATOR_CITY, city, "City");
    }

    public void selectState(String state) {
        selectDropdownData(LOCATOR_STATE, LOCATOR_STATE_VALUE, state);
    }

    public void enterZip(String zip) {
        setTextElement(LOCATOR_ZIP, zip, "Zip");
    }

    public void clickSave() {
        clickElement(LOCATOR_SAVE, "Click Save button");
        isPopupVisible();
    }

    public boolean isPopupVisible() {
        return isElementPresent(LOCATOR_CREDENTIAL_POPUP);
    }
    public void clickYes() {
        clickElement(LOCATOR_YES_POPUP, "Click Yes button");
        getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
    }

    public boolean isCreatedOwnerNameVisible(String ownerName) {
        return isElementPresent(String.format(LOCATOR_OWNER_NAME_IN_TABLE, ownerName));
    }

    public void clickOwnersMenu(String ownerName) {
        clickElement(String.format(LOCATOR_OWNER_MENU, ownerName), "Owner Menu");
        getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
    }

    public void clickDelete() {
        clickElement(LOCATOR_DELETE, "Delete");
        getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
    }

    public void clickConfirm() {
        getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
        clickElement(LOCATOR_CONFIRM, "Cancel");
    }

    public String retrieveToastMessage() {
        getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
        return getText(LOCATOR_OWNER_DELETED_MESSAGE_VISIBLE, "Validation message");
    }

    public boolean isOwnerNameVisible(String ownerName) {
        return isElementInvisible(String.format(LOCATOR_OWNER_NAME_IN_TABLE, ownerName));
    }
}
