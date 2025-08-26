package com.propertyzar.ui.tests.Owner;
import com.propertyzar.ui.base.WebDriver;
import com.propertyzar.ui.pages.Ownerpage.OwnerPage;
import com.propertyzar.ui.pages.login.LoginPage;
import com.propertyzar.ui.tests.BaseTest;
import com.propertyzar.ui.tests.CommonTest;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;



public class OwnerTest extends BaseTest {
    private final LoginPage loginPage;
    private final OwnerPage ownerPage;
    public OwnerTest() {
        super("Owner");
        ownerPage = new OwnerPage(getWebDriver());
        loginPage = new LoginPage(getWebDriver());
    }
    @BeforeMethod
    public void setupCurrentTestReport(Method method) {
        setupCurrentTestReport(method != null ? method.getAnnotation(Test.class).testName() : "Default Test Name");
    }

    private void setupCurrentTestReport(String name) {
        setCurrentTestReportNode(name);
        loginPage.setCurrentTestReportNode(getCurrentTestReportNode());
        ownerPage.setCurrentTestReportNode(getCurrentTestReportNode());
    }
    @BeforeClass
    public void setup() {
        setupCurrentTestReport("Login");
        CommonTest.login(OwnerData.getLoginData(),loginPage);
    }

    @Test(testName = "Owner Creation",dataProvider = OwnerData.CREATE_DATA, dataProviderClass = OwnerData.class,  priority = 1)
    public void createOwner(String firstName,String lastName,String email,String alternativeEmail,String address1,String city,String state,String zip) {
        try {
            ownerPage.clickOwnersSideNav();
            ownerPage.clickNewOwnerButton();
            String randomlyFirstName = ownerPage.randomName(firstName, "First Name");
            ownerPage.enterFirstName(randomlyFirstName);
            String randomlyLastName = ownerPage.randomName(lastName, "Last Name");
            ownerPage.enterLastName(randomlyLastName);
            String randomEmail = ownerPage.randomEmail(email, "yopmail.com");
            ownerPage.enterEmail(randomEmail);
            String randomAlternativeEmail = ownerPage.randomEmail(alternativeEmail, "yopmail.com");
            ownerPage.enterAlternativeEmail(randomAlternativeEmail);
            ownerPage.enterAddress1(address1);
            ownerPage.enterCity(city);
            ownerPage.selectState(state);
            ownerPage.enterZip(zip);
            ownerPage.clickSave();
            Assert.assertTrue(ownerPage.isPopupVisible(), "Credential Popup is not displayed");
            ownerPage.clickYes();
            Assert.assertTrue(ownerPage.isMessageVisible(), "Toast Message is not displayed");


        } catch (Exception e) {
            fail(e);
        }
    }
}
