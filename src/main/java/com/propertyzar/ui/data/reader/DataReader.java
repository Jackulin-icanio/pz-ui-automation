package com.propertyzar.ui.data.reader;

import java.io.InputStream;

public interface DataReader {
    Object[][] getInputData(InputStream inputStream);


    enum InputReaderType {
        EXCEL, JSON, CSV
    }

    class Factory {

        public static DataReader getInputReader(InputReaderType type) {
            return new ExcelReader();
        }
    }
}