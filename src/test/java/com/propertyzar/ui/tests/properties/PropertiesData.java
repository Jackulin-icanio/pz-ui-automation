package com.propertyzar.ui.tests.properties;

import com.propertyzar.ui.ConfigManager;
import com.propertyzar.ui.tests.BaseData;

public class PropertiesData extends BaseData {

    public static final String LOGIN_DATA = "login";
    public static final String CREATE_PROPERTIES_DATA_SHEET = "createProperty";

    @org.testng.annotations.DataProvider(name = LOGIN_DATA)
    public static Object[][] getLoginData() {
        return getTestDataJSON(ConfigManager.getConfig().getPropertyFile(),
                LOGIN_DATA);
    }

    @org.testng.annotations.DataProvider(name = CREATE_PROPERTIES_DATA_SHEET)
    public static Object[][] getCreatePropertiesData() {
        return getTestDataJSON(ConfigManager.getConfig().getPropertyFile(),
                CREATE_PROPERTIES_DATA_SHEET);
    }
}
