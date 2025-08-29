package com.propertyzar.ui.tests.invoice;
import com.propertyzar.ui.pages.owner.OwnerPage;
import com.propertyzar.ui.pages.invoice.InvoicePage;
import com.propertyzar.ui.pages.login.LoginPage;
import com.propertyzar.ui.tests.BaseTest;
import com.propertyzar.ui.tests.CommonTest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;


public class InvoiceTest extends BaseTest {
    private final LoginPage loginPage;
    private final OwnerPage ownerPage;
    private final InvoicePage invoicePage;
    String ownerName;

    public InvoiceTest() {
        super( "Invoice");
        ownerPage = new OwnerPage(getWebDriver());
        loginPage = new LoginPage(getWebDriver());
        invoicePage = new InvoicePage(getWebDriver());
    }
    @BeforeMethod
    public void setupCurrentTestReport(Method method) {
        setupCurrentTestReport(method != null ? method.getAnnotation(Test.class).testName() : "Default Test Name");
    }

    private void setupCurrentTestReport(String name) {
        setCurrentTestReportNode(name);
        loginPage.setCurrentTestReportNode(getCurrentTestReportNode());
        ownerPage.setCurrentTestReportNode(getCurrentTestReportNode());
        invoicePage.setCurrentTestReportNode(getCurrentTestReportNode());
    }
    @BeforeClass
    public void setup() {
        setupCurrentTestReport("Login ,Owner Creation");
        CommonTest.login(InvoiceData.getLoginData(),loginPage);
        ownerName = CommonTest.createOwner(InvoiceData.getCreateOwnerData(),ownerPage);
    }
    @Test(testName = "Create Invoice", priority = 1)

    public void createInvoice() {
        try {

         invoicePage.clickLeftNav();

        } catch (Exception e) {
            fail(e);
        }
    }
    @AfterClass
    public void tearDown() {


    }
}