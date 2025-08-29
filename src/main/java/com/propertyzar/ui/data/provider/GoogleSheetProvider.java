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