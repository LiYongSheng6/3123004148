package com.example;

import java.io.IOException;

import static com.example.utils.FileOperationUtil.writeFile;
import static com.example.utils.TextHandlerUtil.checkSimilarity;

/**
 * @author redmi k50 ultra
 * * @date 2025/3/3
 */

public class Main {
    /**
     * 主函数，程序入口
     * 命令行参数，依次为：原文文件路径、抄袭版文件路径、答案文件路径
     */
    public static void main(String[] args) throws IOException {
        // 创建一个StringBuilder用于存储查重结果
        StringBuilder stringBuilder = new StringBuilder();

        // 存储原文文件路径和输出文件路径
        String originalFilePath, outputFilePath;

        // 检查命令行参数数量是否正确
        if (args.length != 3) {
            //System.err.println("Usage: java -jar main.jar <originalFile> <plagiarizedFile> <answerFile>");
            //System.exit(1);

            // 存储原文文件路径
            originalFilePath = "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\orig.txt";
            // 存储输出文件路径
            outputFilePath = "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\ans.txt";
            // 存储抄袭版文件路径
            String[] plagiarizedFilePaths = {
                    "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\orig_0.8_add.txt",
                    "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\orig_0.8_del.txt",
                    "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\orig_0.8_dis_1.txt",
                    "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\orig_0.8_dis_10.txt",
                    "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\orig_0.8_dis_15.txt",
            };

            // 遍历抄袭版文件路径，依次查重处理
            for (String plagiarizedFilePath : plagiarizedFilePaths) {
                checkSimilarity(plagiarizedFilePath, originalFilePath, stringBuilder);
            }
        }else {
            // 从命令行参数中获取文件路径
            originalFilePath = args[0];
            outputFilePath = args[2];
            String plagiarizedFilePath = args[1];

            // 查重处理
            checkSimilarity(plagiarizedFilePath, originalFilePath, stringBuilder);
        }

        // 将结果写入答案文件
        writeFile(outputFilePath, stringBuilder.toString());
        System.out.println("Result written to " + outputFilePath);
    }

}

