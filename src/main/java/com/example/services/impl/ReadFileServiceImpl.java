package com.example.services.impl;

import com.example.services.IReadFileService;

/**
 * @author redmi k50 ultra
 * * @date 2025/3/3
 */
public class ReadFileServiceImpl implements IReadFileService {
    public String readFile(String filePath) {
        StringBuilder stringBuffer = new StringBuilder();
        String[] strings = filePath.split("\\\\");
        for (String string : strings) {
            stringBuffer.append(string);
        }
        return stringBuffer.toString();
    }
}
