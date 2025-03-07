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
    public static String processText(String text) {
        return text.replaceAll("[^\u4e00-\u9fa5]", "");
    }

    public static List<String> generateNGrams(String text, int n) {
        List<String> nGrams = new ArrayList<>();
        int length = text.length();
        for (int i = 0; i <= length - n; i++) {
            nGrams.add(text.substring(i, i + n));
        }
        return nGrams;
    }

    public static Map<String, Integer> getFrequencyMap(List<String> nGrams) {
        Map<String, Integer> freqMap = new HashMap<>();
        for (String gram : nGrams) {
            freqMap.put(gram, freqMap.getOrDefault(gram, 0) + 1);
        }
        return freqMap;
    }

    public static double computeCosineSimilarity(Map<String, Integer> map1, Map<String, Integer> map2) {
        Set<String> allGrams = new HashSet<>();
        allGrams.addAll(map1.keySet());
        allGrams.addAll(map2.keySet());

        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (String gram : allGrams) {
            int freq1 = map1.getOrDefault(gram, 0);
            int freq2 = map2.getOrDefault(gram, 0);
            dotProduct += freq1 * freq2;
            norm1 += Math.pow(freq1, 2);
            norm2 += Math.pow(freq2, 2);
        }

        if (norm1 == 0 || norm2 == 0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
}
