package com.propertyzar.ui.tests;
import com.propertyzar.ui.pages.owner.OwnerPage;
import com.propertyzar.ui.pages.properties.PropertiesPage;
import lombok.extern.slf4j.Slf4j;
import com.propertyzar.ui.pages.login.LoginPage;
import com.propertyzar.ui.security.SecurityManager;
import org.testng.Assert;

@Slf4j
public class CommonTest {

    public static void login(Object[][] loginData, LoginPage loginPage) {
        loginPage.setUserName(loginData[0][0].toString());
        loginPage.setPassword(SecurityManager.getInstance().getValue(loginData[0][1].toString()));
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isLoginSuccessMessageVisible(), "Login Message is not displayed");
        Assert.assertTrue(loginPage.isHomeScreenVisible(), "Home screen is not displayed");
    }

    public static String createOwner(Object[][] ownerData, OwnerPage ownerPage) {
        ownerPage.clickOwnersSideNav();
        ownerPage.clickNewOwnerButton();
        String firstName = ownerData[0][0].toString();
        ownerPage.enterFirstName(firstName);
        String lastName = ownerData[0][1].toString();
        ownerPage.enterLastName(lastName);
        String ownerName = firstName + " " + lastName;
        ownerPage.enterEmail(ownerData[0][2].toString());
        ownerPage.enterAlternativeEmail(ownerData[0][3].toString());
        ownerPage.enterAddress1(ownerData[0][4].toString());
        ownerPage.enterCity(ownerData[0][5].toString());
        ownerPage.selectState(ownerData[0][6].toString());
        ownerPage.enterZip(ownerData[0][7].toString());
        ownerPage.clickSave();
        Assert.assertTrue(ownerPage.isPopupVisible(), "Credential Popup is not displayed");
        ownerPage.clickYes();
        Assert.assertTrue(ownerPage.isCreatedOwnerNameVisible(ownerName),"Created owner name is not visible");
        return ownerName;
    }

    public static void deleteProperty(PropertiesPage propertiesPage, String propertyName) {
        propertiesPage.clickPropertiesMenu(propertyName);
        propertiesPage.clickDelete();
        propertiesPage.clickConfirm();
        String actualSuccessMsg = propertiesPage.retrieveToastMessage();
        Assert.assertEquals(actualSuccessMsg, PropertiesPage.EXPECTED_MSG_FOR_VALIDATE, "Validation...Errors/Warnings Occurred");
        Assert.assertTrue(propertiesPage.isPropertyNameVisible(propertyName));
    }

    public static void deleteOwner(OwnerPage ownerPage, String ownerName) {
        ownerPage.clickOwnersMenu(ownerName);
        ownerPage.clickDelete();
        ownerPage.clickConfirm();
        String actualSuccessMsg = ownerPage.retrieveToastMessage();
        Assert.assertEquals(actualSuccessMsg, OwnerPage.EXPECTED_MSG_FOR_VALIDATE, "Validation...Errors/Warnings Occurred");
        Assert.assertTrue(ownerPage.isOwnerNameVisible(ownerName));
    }
}
