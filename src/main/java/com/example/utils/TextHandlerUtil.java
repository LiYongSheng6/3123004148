package com.example.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.example.utils.FileOperationUtil.readFile;
import static com.example.utils.NGramUtil.computeCosineSimilarity;
import static com.example.utils.NGramUtil.generateNGrams;
import static com.example.utils.NGramUtil.getFrequencyMap;

/**
 * @author redmi k50 ultra
 * * @date 2025/3/7
 */
public class TextHandlerUtil {
    /**
     * 预处理文本，去除标点符号和非中文字符
     * @param text 原始文本
     * @return 处理后的纯中文字符串
     */
    public static String processText(String text) {
        // 使用正则表达式去除非中文字符
        return text.replaceAll("[^\u4e00-\u9fa5]", "");
    }

    /**
     * 检查抄袭版文本与原文的相似度
     * @param plagiarizedFilePath 抄袭版文本路径
     * @param originalFilePath 原文路径
     * @param stringBuilder 用于存储输出结果的StringBuilder
     */
    public static void checkSimilarity(String plagiarizedFilePath, String originalFilePath, StringBuilder stringBuilder) {
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
}
