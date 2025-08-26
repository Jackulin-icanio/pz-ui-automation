package com.propertyzar.ui.pages.properties;

import com.propertyzar.ui.base.WebDriver;
import com.propertyzar.ui.pages.BasePage;

public class PropertiesPage extends BasePage {

    private static final String LOCATOR_PROPERTIES_LEFT_NAV = "[data-testid='Properties']";
    private static final String LOCATOR_PROPERTIES_SUB_MENU = "//*[contains(@class, 'MuiCollapse-root')]//*[(@data-testid='Properties')]";
    private static final String LOCATOR_NEW_PROPERTY = "//*[(text()='New Property')]";
    private static final String LOCATOR_PROPERTY_NAME = "[data-testid='propertyName']";
    private static final String LOCATOR_PROPERTY_TYPE = "[id='propertyType']";
    private static final String LOCATOR_PROPERTY_TYPE_VALUE = "//*[@id='propertyType-listbox']";
    private static final String LOCATOR_OWNER = "[id='owners']";
    private static final String LOCATOR_ADDRESS1 = "[data-testid='address1']";
    private static final String LOCATOR_CITY = "[data-testid='city']";
    private static final String LOCATOR_STATE = "[id='state']";
    private static final String LOCATOR_STATE_VALUE = "//*[@id='state-listbox']";
    private static final String LOCATOR_ZIP = "[id='zipCode']";
    private static final String LOCATOR_SAVE = "[name='Save']";

    public PropertiesPage(WebDriver<?> webDriver) {
        super(webDriver);
    }

    public void clickLeftNav(){
        clickElement(LOCATOR_PROPERTIES_LEFT_NAV,"Properties Left Navigation");
        getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
//        clickElement(LOCATOR_PROPERTIES_SUB_MENU,"Properties Left Navigation Sub Menu");
//        getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);
    }
    public void clickNewPropertyButton() {
        clickElement(LOCATOR_NEW_PROPERTY, "New Property");
    }
    public void enterProprtyName(String propertyName){
        setTextElement(LOCATOR_PROPERTY_NAME,propertyName,"Property Name");
    }
    public void selectPropertyType(String propertyType) {
        selectDropdownData(LOCATOR_PROPERTY_TYPE,LOCATOR_PROPERTY_TYPE_VALUE,propertyType );
    }
    public void enterAddress1(String address1){
        setTextElement(LOCATOR_ADDRESS1,address1,"Address1");
    }
    public void enterCity(String city){
        setTextElement(LOCATOR_CITY,city,"City");
    }
    public void selectState(String state){
        selectDropdownData(LOCATOR_STATE,LOCATOR_STATE_VALUE,state);
    }
    public void enterZip(String zip){
        setTextElement(LOCATOR_ZIP,zip,"Zip");
    }
    public void clickSave(){
        clickElement(LOCATOR_SAVE,"Save");
    }
}
