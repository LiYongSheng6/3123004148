package com.example;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.example.utils.FileOperationUtil.readFile;
import static com.example.utils.FileOperationUtil.writeFile;
import static com.example.utils.NGramUtil.computeCosineSimilarity;
import static com.example.utils.NGramUtil.generateNGrams;
import static com.example.utils.NGramUtil.getFrequencyMap;
import static com.example.utils.NGramUtil.processText;

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
        // 检查命令行参数数量是否正确
        //if (args.length != 3) {
        //    System.err.println("Usage: java -jar main.jar <originalFile> <plagiarizedFile> <answerFile>");
        //    System.exit(1);
        //}

        // 存储抄袭版文件路径
        String[] plagiarizedFilePaths = {
                "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\orig_0.8_add.txt",
                "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\orig_0.8_del.txt",
                "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\orig_0.8_dis_1.txt",
                "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\orig_0.8_dis_10.txt",
                "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\orig_0.8_dis_15.txt",
        };

        // 存储原文文件路径
        String originalFilePath = "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\orig.txt";
        // 存储输出文件路径
        String outputFilePath = "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\ans.txt";

        // 遍历抄袭版文件路径，依次查重处理
        StringBuilder stringBuilder = new StringBuilder();
        for (String plagiarizedFilePath : plagiarizedFilePaths) {
            try {
                // 读取原文和抄袭版文件内容
                String originalText = readFile(originalFilePath);
                String plagiarizedText = readFile(plagiarizedFilePath);

                // 预处理文本，去除标点符号和非中文字符
                String processedOriginal = processText(originalText);
                String processedPlagiarized = processText(plagiarizedText);

                // 设置n-gram的长度
                int n = 3;
                // 生成原文和抄袭版的n-gram列表
                List<String> originalNGrams = generateNGrams(processedOriginal, n);
                List<String> plagiarizedNGrams = generateNGrams(processedPlagiarized, n);

                // 统计n-gram的词频
                Map<String, Integer> originalFreq = getFrequencyMap(originalNGrams);
                Map<String, Integer> plagiarizedFreq = getFrequencyMap(plagiarizedNGrams);

                // 计算余弦相似度
                double similarity = computeCosineSimilarity(originalFreq, plagiarizedFreq);
                // 格式化相似度为两位小数
                String result = String.format("%.2f", similarity);

                // 输出查重结果
                String plagiarizedTextName = plagiarizedFilePath.substring(originalFilePath.lastIndexOf("\\") + 1);
                String output = "文本" + plagiarizedTextName + "查重结果：" + result;
                System.out.println(output);

                // 将结果添加到StringBuilder中
                stringBuilder.append(output).append("\n");
            } catch (IOException e) {
                System.err.println("Error processing files: " + e.getMessage());
                System.exit(1);
            }
        }
        // 将结果写入答案文件
        writeFile(outputFilePath, stringBuilder.toString());
        System.out.println("Result written to " + outputFilePath);
    }
}

