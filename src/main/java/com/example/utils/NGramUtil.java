package com.example.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author redmi k50 ultra
 * * @date 2025/3/7
 */
public class NGramUtil {
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
     * 生成n-gram列表
     * @param text 处理后的文本
     * @param n n-gram的长度
     * @return n-gram列表
     */
    public static List<String> generateNGrams(String text, int n) {
        List<String> nGrams = new ArrayList<>();
        int length = text.length();
        // 遍历文本，生成连续的n-gram
        for (int i = 0; i <= length - n; i++) {
            nGrams.add(text.substring(i, i + n));
        }
        return nGrams;
    }

    /**
     * 统计n-gram的词频
     * @param nGrams n-gram列表
     * @return n-gram词频映射表
     */
    public static Map<String, Integer> getFrequencyMap(List<String> nGrams) {
        Map<String, Integer> freqMap = new HashMap<>();
        // 遍历n-gram列表，统计每个n-gram的出现次数
        for (String gram : nGrams) {
            freqMap.put(gram, freqMap.getOrDefault(gram, 0) + 1);
        }
        return freqMap;
    }

    /**
     * 计算余弦相似度
     * @param map1 原文的n-gram词频映射表
     * @param map2 抄袭版的n-gram词频映射表
     * @return 余弦相似度值
     */
    public static double computeCosineSimilarity(Map<String, Integer> map1, Map<String, Integer> map2) {
        // 获取所有唯一的n-gram
        Set<String> allGrams = new HashSet<>();
        allGrams.addAll(map1.keySet());
        allGrams.addAll(map2.keySet());

        double dotProduct = 0.0; // 点积
        double norm1 = 0.0;      // 原文向量的模
        double norm2 = 0.0;      // 抄袭版向量的模

        // 计算点积和向量模
        for (String gram : allGrams) {
            int freq1 = map1.getOrDefault(gram, 0);
            int freq2 = map2.getOrDefault(gram, 0);
            dotProduct += freq1 * freq2;
            norm1 += Math.pow(freq1, 2);
            norm2 += Math.pow(freq2, 2);
        }

        // 处理分母为零的情况
        if (norm1 == 0 || norm2 == 0) {
            return 0.0;
        }

        // 返回余弦相似度
        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
}
