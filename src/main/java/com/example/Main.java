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
    public static void main(String[] args) {
        //if (args.length != 3) {
        //    System.err.println("Usage: java -jar main.jar <originalFile> <plagiarizedFile> <answerFile>");
        //    System.exit(1);
        //}

        String[] plagiarizedFilePaths = {
                "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\orig_0.8_add.txt",
                "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\orig_0.8_del.txt",
                "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\orig_0.8_dis_1.txt",
                "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\orig_0.8_dis_10.txt",
                "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\orig_0.8_dis_15.txt",
        };

        for (String plagiarizedFilePath : plagiarizedFilePaths) {
            try {
                String originalFilePath = "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\orig.txt";
                //String plagiarizedFilePath = "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\orig_0.8_add.txt";
                String outputFilePath = "D:\\code\\Java\\PaperPlagiarismCheck\\src\\main\\resources\\ans.txt";

                String originalText = readFile(originalFilePath);
                String plagiarizedText = readFile(plagiarizedFilePath);

                String processedOriginal = processText(originalText);
                String processedPlagiarized = processText(plagiarizedText);

                int n = 3;
                List<String> originalNGrams = generateNGrams(processedOriginal, n);
                List<String> plagiarizedNGrams = generateNGrams(processedPlagiarized, n);

                Map<String, Integer> originalFreq = getFrequencyMap(originalNGrams);
                Map<String, Integer> plagiarizedFreq = getFrequencyMap(plagiarizedNGrams);

                double similarity = computeCosineSimilarity(originalFreq, plagiarizedFreq);
                String result = String.format("%.2f", similarity);

                String plagiarizedTextName = plagiarizedFilePath.substring(originalFilePath.lastIndexOf("\\") + 1);
                System.out.println("文本" + plagiarizedTextName + "查重结果：" + result);

                writeFile(outputFilePath, result);
            } catch (IOException e) {
                System.err.println("Error processing files: " + e.getMessage());
                System.exit(1);
            }
        }
    }
}
