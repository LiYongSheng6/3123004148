package com.example;
import com.example.services.ICheckPoundService;
import com.example.services.impl.CheckPoundServiceImpl;

/**
 * @author redmi k50 ultra
 * * @date 2025/3/3
 */

public class Main {
    public static ICheckPoundService checkPoundService = new CheckPoundServiceImpl();

    public static void main(String[] args) {
        System.out.println("项目初始化成功!");
        checkPoundService.checkPound();
        System.out.println("项目运行成功!");

    }
}
