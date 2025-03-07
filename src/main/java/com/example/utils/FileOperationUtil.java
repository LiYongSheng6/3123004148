package com.example.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;

/**
 * @author redmi k50 ultra
 * * @date 2025/3/7
 */
public class FileOperationUtil {
    /**
     * 读取文件内容
     *
     * @param filePath 文件路径
     * @return 文件内容字符串
     * @throws IOException 如果文件读取失败
     */
    public static String readFile(String filePath) throws IOException {
        // 创建一个StringBuilder对象，用于存储文件内容
        StringBuilder content = new StringBuilder();

        // 使用CharsetDecoder检查文件编码是否为UTF-8
        CharsetDecoder utf8Decoder = StandardCharsets.UTF_8.newDecoder();
        utf8Decoder.onMalformedInput(CodingErrorAction.REPORT); // 遇到非UTF-8编码时抛出异常
        utf8Decoder.onUnmappableCharacter(CodingErrorAction.REPORT); // 遇到无法映射的字符时抛出异常

        // 创建一个BufferedReader对象，用于读取文件内容
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            // 循环读取文件内容，直到文件结束
            String line;
            while ((line = reader.readLine()) != null) {
                // 将读取到的行内容添加到StringBuilder对象中
                content.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found: " + filePath);
        } catch (MalformedInputException e) {
            throw new UnsupportedEncodingException("File encoding is not UTF-8: " + filePath);
        } catch (IOException e) {
            // 如果文件读取失败，抛出异常
            throw new IOException("Failed to read file: " + filePath, e);
        }

        // 返回文件内容字符串
        return content.toString();
    }

    /**
     * 写入文件内容
     *
     * @param filePath 文件路径
     * @param content  文件内容字符串
     * @throws IOException 如果文件写入失败
     */
    public static void writeFile(String filePath, String content) throws IOException {
        // 创建一个BufferedWriter对象，用于写入文件内容
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, StandardCharsets.UTF_8))) {
            // 将文件内容写入文件
            writer.write(content);
        } catch (IOException e) {
            // 如果文件写入失败，抛出异常
            throw new IOException("Failed to write file: " + filePath, e);
        }
    }
}
