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

package com.propertyzar.ui.tests.login;

import com.propertyzar.ui.ConfigManager;
import com.propertyzar.ui.tests.BaseData;

public class LoginData extends BaseData {

    public static final String LOGIN_DATA = "login";

    @org.testng.annotations.DataProvider(name = LOGIN_DATA)
    public static Object[][] getLoginData() {
        return getTestDataJSON(ConfigManager.getConfig().getLoginFile(),
                LOGIN_DATA);
    }
}