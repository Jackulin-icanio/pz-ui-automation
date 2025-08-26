/*
 * Copyright (c) 2025 Trilogy, Inc.
 *   All rights reserved.
 *
 *   This software and its documentation are confidential and proprietary
 *   information of Trilogy, Inc. Unauthorized use, duplication,
 *   or distribution is strictly prohibited.
 */

package com.propertyzar.ui.tests;

//import com.trilogy.dcm.pages.productHierarchy.ProductHierarchyPage;
//import com.trilogy.dcm.pages.productSearch.ProductSearchPage;
//import com.trilogy.dcm.tests.productHierarchy.ProductHierarchyData;
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
        Assert.assertTrue(loginPage.isMessageVisible(), "Login Message is not displayed");
        Assert.assertTrue(loginPage.isHomeScreenVisible(), "Home screen is not displayed");
    }

}
