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

package com.propertyzar.ui.pages.properties;

import com.propertyzar.ui.base.WebDriver;
import com.propertyzar.ui.pages.BasePage;

public class PropertiesPage extends BasePage {

    //Messages
    public static final String EXPECTED_MSG_FOR_VALIDATE = "Property Deleted Successfully";

    //Locators
    private static final String LOCATOR_PROPERTIES_LEFT_NAV = "(//*[@data-testid='Properties'])[1]";
    private static final String LOCATOR_PROPERTIES_MENU = "//*[contains(@class,'mui-10egq61')]//*[text()='Properties']";
    private static final String LOCATOR_NEW_PROPERTY = "//*[(text()='New Property')]";
    private static final String LOCATOR_PROPERTY_NAME = "[data-testid='propertyName']";
    private static final String LOCATOR_PROPERTY_TYPE = "[id='propertyType']";
    private static final String LOCATOR_PROPERTY_TYPE_VALUE = "//*[@id='propertyType-listbox']";
    private static final String LOCATOR_OWNER = "[id='owners']";
    private static final String LOCATOR_OWNER_VALUE = "//*[@id='owners-listbox']";
    private static final String LOCATOR_OWNER_PERCENTAGE_SAVE = "//*[@data-testid='dialogContent']//*[text()='Save']";
    private static final String LOCATOR_ADDRESS1 = "[data-testid='address1']";
    private static final String LOCATOR_CITY = "[data-testid='city']";
    private static final String LOCATOR_STATE = "[id='state']";
    private static final String LOCATOR_STATE_VALUE = "//*[@id='state-listbox']";
    private static final String LOCATOR_ZIP = "[id='zipCode']";
    private static final String LOCATOR_SAVE = "[name='Save']";
    private static final String LOCATOR_PROPERTY_CREATED_MESSAGE_VISIBLE = "//*[text()='Property Saved Successfully']";
    private static final String LOCATOR_PROPERTY_NAME_IN_TABLE = "//*[@data-testid='tableRow']//*[text()='%s']";
    private static final String LOCATOR_PROPERTY_MENU = "//tr[.//p[(text()= '%s')]]//button[@id='long-button']";
    private static final String LOCATOR_DELETE = "//*[contains(@class,'MuiPopover-root') and not(@aria-hidden='true')]//li[@role='menuitem' and normalize-space(.)='Delete']";
    private static final String LOCATOR_CONFIRM = "//*[text()='Confirm']";
    private static final String LOCATOR_PROPERTY_DELETED_MESSAGE_VISIBLE = "//*[text()='Property Deleted Successfully']";

    public PropertiesPage(WebDriver<?> webDriver) {
        super(webDriver);
    }

    public void clickLeftNav() {
        getWebDriver().refresh();
        clickElement(LOCATOR_PROPERTIES_LEFT_NAV, "Properties Left Navigation");
        getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
        clickElement(LOCATOR_PROPERTIES_MENU,"Properties Menu");
        getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
        isElementPresent(LOCATOR_NEW_PROPERTY);
    }

    public void clickNewPropertyButton() {
        clickElement(LOCATOR_NEW_PROPERTY, "New Property");
        getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
        isElementPresent(LOCATOR_PROPERTY_NAME);
    }

    public void enterPropertyName(String propertyName) {
        setTextElement(LOCATOR_PROPERTY_NAME, propertyName, "Property Name");
    }

    public void selectPropertyType(String propertyType) {
        selectDropdownData(LOCATOR_PROPERTY_TYPE, LOCATOR_PROPERTY_TYPE_VALUE, propertyType);
    }

    public void selectOwner(String owner) {
        selectDropdownData(LOCATOR_OWNER, LOCATOR_OWNER_VALUE, owner);
    }

    public void clickOwnerPercentageSave() {
        clickElement(LOCATOR_OWNER_PERCENTAGE_SAVE, "Owner Percentage Save");
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
        clickElement(LOCATOR_SAVE, "Save");
    }

    public boolean isPropertyCreatedMessageVisible() {
        return isElementPresent(LOCATOR_PROPERTY_CREATED_MESSAGE_VISIBLE);
    }

    public boolean isCreatedPropertyNameVisible(String propertyName) {
        return isElementPresent(String.format(LOCATOR_PROPERTY_NAME_IN_TABLE, propertyName));
    }

    public void clickPropertiesMenu(String propertyName) {
        clickElement(String.format(LOCATOR_PROPERTY_MENU, propertyName), "Property Menu");
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
        return getText(LOCATOR_PROPERTY_DELETED_MESSAGE_VISIBLE, "Validation message");
    }

    public boolean isPropertyNameVisible(String propertyName) {
        return isElementInvisible(String.format(LOCATOR_PROPERTY_NAME_IN_TABLE, propertyName));
    }
}