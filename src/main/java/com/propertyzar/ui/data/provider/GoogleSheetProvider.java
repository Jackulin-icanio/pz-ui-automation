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

package com.propertyzar.ui.data.provider;

import com.propertyzar.ui.data.reader.DataReader;

import java.io.InputStream;

public class GoogleSheetProvider extends DataProvider {

    protected GoogleSheetProvider(String fileName, DataReader.InputReaderType type) {
        super(fileName, type);
    }

    @Override
    public InputStream readFile(String fileName) {
        return null;
    }
}