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

package com.propertyzar.ui.tests;

import com.propertyzar.ui.data.provider.LocalProvider;
import com.propertyzar.ui.data.reader.DataReader;

public class BaseData {
    protected static Object[][] getTestDataJSON(String filePath, String dataKey) {
        LocalProvider provider = new LocalProvider(filePath, DataReader.InputReaderType.JSON);
        return provider.getInputData(dataKey);
    }
}
