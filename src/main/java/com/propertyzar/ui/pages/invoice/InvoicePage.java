package com.propertyzar.ui.pages.invoice;
import com.propertyzar.ui.base.WebDriver;
import com.propertyzar.ui.pages.BasePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.junit.Assert.fail;

public class InvoicePage extends BasePage {
    private static final String LOCATOR_ACCOUNTING_LEFT_NAV = "(//*[@data-testid='Accounting'])[1]";

    public InvoicePage(WebDriver<?> webDriver) {
        super(webDriver);
    }
    public void clickLeftNav() {

        getWebDriver().refresh();
        clickElement(LOCATOR_ACCOUNTING_LEFT_NAV, " Accounting Left Navigation");
        getWebDriver().waitForLoad(WebDriver.LoadType.DOMCONTENTLOADED);

    }


}
