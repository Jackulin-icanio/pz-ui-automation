package com.propertyzar.ui.security;

import java.util.logging.Logger;

public class SecurityApplication {

    private static final Logger logger = Logger.getLogger(SecurityApplication.class.getName());

    public static void main(String[] args) {
        SecurityManager securityManager = SecurityManager.getInstance();
        logger.info("Program started.");

        securityManager.updateNonEncryptedData();

        logger.info("Program finished.");
    }
}
