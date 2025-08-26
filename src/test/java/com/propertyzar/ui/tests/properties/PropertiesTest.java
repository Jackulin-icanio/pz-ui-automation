package com.propertyzar.ui.tests.properties;

import com.propertyzar.ui.base.WebDriver;
import com.propertyzar.ui.pages.login.LoginPage;
import com.propertyzar.ui.pages.properties.PropertiesPage;
import com.propertyzar.ui.tests.BaseTest;
import com.propertyzar.ui.tests.CommonTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class PropertiesTest extends BaseTest {

    private final LoginPage loginPage;
    private final PropertiesPage propertiesPage;

    public PropertiesTest(){
        super("Properties");
        loginPage = new LoginPage(getWebDriver());
        propertiesPage = new PropertiesPage((getWebDriver()));
    }

    @BeforeMethod
    public void setupCurrentTestReport(Method method) {
        setupCurrentTestReport(method != null ? method.getAnnotation(Test.class).testName() : "Default Test Name");
    }

    public void setupCurrentTestReport(String name) {
        setCurrentTestReportNode(name);
        loginPage.setCurrentTestReportNode(getCurrentTestReportNode());
        propertiesPage.setCurrentTestReportNode(getCurrentTestReportNode());
    }

    @BeforeClass
    public void setup() {
        setupCurrentTestReport("Login");
        CommonTest.login(PropertiesData.getLoginData(), loginPage);
    }

    @Test(testName = "Create Property", dataProvider = PropertiesData.CREATE_PROPERTIES_DATA_SHEET, dataProviderClass = PropertiesData.class, priority = 1)
    public void createProperty(String propertyName, String propertyType, String address1, String city, String state, String zip) {
        try {
            propertiesPage.clickLeftNav();
            propertiesPage.clickNewPropertyButton();
            String randomlyPropertyName = propertiesPage.randomName(propertyName, "Property_");
            propertiesPage.enterProprtyName(randomlyPropertyName);
            propertiesPage.selectPropertyType(propertyType);
            propertiesPage.enterAddress1(address1);
            propertiesPage.enterCity(city);
            propertiesPage.selectState(state);
            propertiesPage.enterZip(zip);
            propertiesPage.clickSave();
        } catch (Exception e) {
            fail(e);
        }
    }
}
