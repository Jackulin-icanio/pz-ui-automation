package com.propertyzar.ui.pages.Ownerpage;
import com.epam.ta.reportportal.ws.model.Page;
import com.propertyzar.ui.base.WebDriver;
import com.propertyzar.ui.pages.BasePage;
import com.microsoft.playwright.Keyboard;




public class OwnerPage extends BasePage {
    private static final String LOCATOR_OWNERS_SIDE_NAV = "//div[@data-testid='Owners']";
    private static final String LOCATOR_NEW_OWNER = "//span[text()='New Owner']";
    private static final String LOCATOR_FIRST_NAME = "//input[@id='firstName']";
    private static final String LOCATOR_LAST_NAME = "//input[@id='lastName'] ";
    private static final String LOCATOR_EMAIL ="//input[@id='email']";
    private static final String LOCATOR_ALTERNATIVE_EMAIL = "//input[@id='alternativeEmail']";
    private static final String LOCATOR_LOCATION ="//input[contains(@class, 'MuiInputBase-input') and @placeholder='Enter a Location']";
    private static final String LOCATOR_SAVE ="//button[span[text()='Save']]";
    private static final String LOCATOR_CREDENTIAL_POPUP = "//*[contains(@role,'dialog')]";
    private static final String LOCATOR_YES_POPUP = "//button[span[text()='Yes']]";
    private static final String LOCATOR_TOAST_MESSAGE ="//*[text()='Login Successful']";

    public OwnerPage(WebDriver<?> webDriver) {
        super(webDriver);


    }
    public void clickOwnersSideNav() {
        clickElement(LOCATOR_OWNERS_SIDE_NAV, "Owners Side Nav");
    }
    public void clickNewOwnerButton() {
        clickElement(LOCATOR_NEW_OWNER, "New Owner Button");
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

    public void enterLocation(String location) {
        clickElement(LOCATOR_LOCATION, " Location Button");
        setTextElement(LOCATOR_LOCATION, location, "Enter location ");
    }
    public void clickSave() {
        clickElement(LOCATOR_SAVE, "Click Save button");
    }
    public boolean isPopupVisible() {
        return isElementPresent(LOCATOR_CREDENTIAL_POPUP);
    }

    public void clickYes() {
        clickElement(LOCATOR_YES_POPUP, "Click Yes button");
    }
    public boolean isMessageVisible() {
        return isElementPresent(LOCATOR_TOAST_MESSAGE);
    }


}
