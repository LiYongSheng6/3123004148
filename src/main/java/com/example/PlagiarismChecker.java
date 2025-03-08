package com.example;

import java.io.IOException;

import static com.example.utils.FileOperationUtil.writeFileWithBuffer;
import static com.example.utils.TextHandlerUtil.checkSimilarity;

/**
 * @author redmi k50 ultra
 * * @date 2025/3/3
 */

public class PlagiarismChecker {
    /**
     * 主函数，程序入口
     * 命令行参数，依次为：原文文件路径、抄袭版文件路径、答案文件路径
     */
    public static void main(String[] args) throws IOException {
        // 检查命令行参数数量是否正确
        if (args.length != 3) {
            System.err.println("Usage: java -jar main.jar <originalFilePath> <plagiarizedFilePath> <answerFilePath>");
            System.exit(1);
        } else {
            // 创建一个StringBuilder用于存储查重结果
            StringBuilder stringBuilder = new StringBuilder();
            // 从命令行参数中获取文件路径
            String originalFilePath = args[0];
            String outputFilePath = args[2];
            String plagiarizedFilePath = args[1];
            // 查重处理
            checkSimilarity(plagiarizedFilePath, originalFilePath, stringBuilder);
            // 将结果写入答案文件
            writeFileWithBuffer(outputFilePath, stringBuilder.toString());
            System.out.println("Result written to " + outputFilePath);
        }
    }

}

