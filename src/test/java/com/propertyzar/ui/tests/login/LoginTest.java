package com.propertyzar.ui.tests.login;
import com.propertyzar.ui.pages.login.LoginPage;
import com.propertyzar.ui.security.SecurityManager;
import com.propertyzar.ui.tests.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.TestListenerAdapter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.lang.reflect.Method;

@Listeners({TestListenerAdapter.class})
@Slf4j
public class LoginTest extends BaseTest {

    private final LoginPage loginPage;

    public LoginTest() {
        super("Login");
        loginPage = new LoginPage(getWebDriver());
    }

    @BeforeMethod
    public void setupCurrentTestReport(Method method) {
        setCurrentTestReportNode(method != null ? method.getAnnotation(Test.class).testName() : "Default Test Name");
        loginPage.setCurrentTestReportNode(getCurrentTestReportNode());
    }

    @Test(testName = "Successful Login with PZ User", dataProvider = LoginData.LOGIN_DATA, dataProviderClass = LoginData.class, priority = 3)
    public void testSuccessfulLoginWithPZUser(String email, String password) {
        try {
            loginPage.setUserName(email);
            loginPage.setPassword(SecurityManager.getInstance().getValue(password));
            loginPage.clickLogin();
            Assert.assertTrue(loginPage.isMessageVisible(), "Login Message is not displayed");
            Assert.assertTrue(loginPage.isHomeScreenVisible(), "Home screen is not displayed");
        } catch (Exception e) {
            fail(e);
        }
    }
}