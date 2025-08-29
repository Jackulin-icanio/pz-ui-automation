/*
 *
 * Copyright (c) 2025 Propertyzar, Inc.
 *     All rights reserved.
 *
 *     This software and its documentation are confidential and proprietary
 *     information of Propertyzar, Inc. Unauthorized use, duplication,
 *     or distribution is strictly prohibited.
 *
 */

package com.propertyzar.ui.tests.owner;
import com.propertyzar.ui.ConfigManager;
import com.propertyzar.ui.tests.BaseData;


public class OwnerData extends BaseData {
    public static final String LOGIN_DATA = "login";
    public static final String CREATE_OWNER_DATA ="owner";

    @org.testng.annotations.DataProvider(name = LOGIN_DATA)
    public static Object[][] getLoginData() {
        return getTestDataJSON(ConfigManager.getConfig().getOwnerFile(),
                LOGIN_DATA);
    }
    @org.testng.annotations.DataProvider(name = CREATE_OWNER_DATA)
    public static Object[][] getCreateOwnerData() {
        return getTestDataJSON(ConfigManager.getConfig().getOwnerFile(),
                CREATE_OWNER_DATA);
    }
}
