package com.propertyzar.ui.tests.invoice;
import com.propertyzar.ui.ConfigManager;
import com.propertyzar.ui.tests.BaseData;

public class InvoiceData extends BaseData {
    public static final String LOGIN_DATA = "login";
    public static final String CREATE_OWNER_DATA ="owner";
    public static final String CREATE_INVOICE_DATA ="invoice";

    @org.testng.annotations.DataProvider(name = LOGIN_DATA)
    public static Object[][] getLoginData() {
        return getTestDataJSON(ConfigManager.getConfig().getInvoiceFile(),
                LOGIN_DATA);
    }
    @org.testng.annotations.DataProvider(name = CREATE_OWNER_DATA)
    public static Object[][] getCreateOwnerData() {
        return getTestDataJSON(ConfigManager.getConfig().getInvoiceFile(),
                CREATE_OWNER_DATA);
    }

    @org.testng.annotations.DataProvider(name = CREATE_INVOICE_DATA)
    public static Object[][] getCreateInvoiceData() {
        return getTestDataJSON(ConfigManager.getConfig().getInvoiceFile(),
                CREATE_INVOICE_DATA);
    }

}
