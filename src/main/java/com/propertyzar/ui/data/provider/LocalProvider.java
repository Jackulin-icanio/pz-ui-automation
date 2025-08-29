package com.propertyzar.ui.data.provider;

import com.propertyzar.ui.ConfigManager;
import com.propertyzar.ui.data.reader.DataReader;
import com.propertyzar.ui.exception.Exception;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class LocalProvider extends DataProvider {

    public LocalProvider(String fileName, DataReader.InputReaderType type) {
        super(fileName, type);
    }

    @Override
    public InputStream readFile(String fileName) {
        try {
            return new FileInputStream(ConfigManager.getConfig().getDataFolder() + fileName);
        } catch (FileNotFoundException e) {
            throw new Exception(e);
        }
    }
}