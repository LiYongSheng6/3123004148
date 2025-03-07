package com.example.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

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
        // 创建一个空的n-gram列表
        List<String> nGrams = new ArrayList<>();
        // 获取文本的长度
        int length = text.length();
        // 使用并行流生成n-gram
        IntStream.range(0, length - n + 1).parallel().forEach(i -> {
            synchronized (nGrams) {
                nGrams.add(text.substring(i, i + n));
            }
        });
        // 返回n-gram列表
        return nGrams;
    }

    /**
     * 统计n-gram的词频
     * @param nGrams n-gram列表
     * @return n-gram词频映射表
     */
    public static Map<String, Integer> getFrequencyMap(List<String> nGrams) {
        // 创建一个空的n-gram词频映射表
        Map<String, Integer> freqMap = new ConcurrentHashMap<>();
        // 使用并行流统计词频
        nGrams.parallelStream().forEach(gram -> {
            // 更新n-gram词频
            freqMap.put(gram, freqMap.getOrDefault(gram, 0) + 1);
        });
        // 返回n-gram词频映射表
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

        // 使用流计算点积和向量模
        dotProduct = allGrams.parallelStream()
                .mapToDouble(gram -> map1.getOrDefault(gram, 0) * map2.getOrDefault(gram, 0))
                .sum();
        norm1 = map1.values().parallelStream().mapToDouble(freq -> Math.pow(freq, 2)).sum();
        norm2 = map2.values().parallelStream().mapToDouble(freq -> Math.pow(freq, 2)).sum();

        // 处理分母为零的情况
        if (norm1 == 0 || norm2 == 0) {
            return 0.0;
        }

        // 返回余弦相似度
        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
}
