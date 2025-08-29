package com.propertyzar.ui.tests;

import com.propertyzar.ui.data.provider.LocalProvider;
import com.propertyzar.ui.data.reader.DataReader;

public class BaseData {
    protected static Object[][] getTestDataJSON(String filePath, String dataKey) {
        LocalProvider provider = new LocalProvider(filePath, DataReader.InputReaderType.JSON);
        return provider.getInputData(dataKey);
    }
}
