package com.example;

import com.example.utils.FileOperationUtil;
import com.example.utils.NGramUtil;
import com.example.utils.TextHandlerUtil;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlagiarismCheckerTest {

    @Test
    void testProcessText() {
        String text = "今天是星期天，天气晴，今天晚上我要去看电影。";
        String expected = "今天是星期天天气晴今天晚上我要去看电影";
        String result = TextHandlerUtil.processText(text);
        assertEquals(expected, result, "文本预处理结果不符合预期");
    }

    @Test
    void testGenerateNGramsParallel() {
        String text = "今天是星期天";
        List<String> expected = Arrays.asList("是星期", "星期天", "天是星", "今天是");
        List<String> result = NGramUtil.generateNGramsParallel(text, 3);
        assertEquals(expected, result, "n-gram生成结果不符合预期");
    }

    @Test
    void testComputeCosineSimilarity() {
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("今天是", 1);
        map1.put("星期天", 1);

        Map<String, Integer> map2 = new HashMap<>();
        map2.put("今天是", 1);
        map2.put("星期天", 1);

        double expected = 1.0;
        double result = NGramUtil.computeCosineSimilarity(map1, map2);
        assertEquals(expected,  result,0.01, "余弦相似度计算结果不符合预期");
    }

    @Test
    void testFileNotFound() {
        Exception exception = assertThrows(FileNotFoundException.class, () -> {
            FileOperationUtil.readFileWithEncodingCheck("non_existent.txt");
        });
        assertTrue(exception.getMessage().contains("File not found"), "文件未找到异常提示信息不正确");
    }

    @Test
    void testUnsupportedEncoding() {
        Exception exception = assertThrows(UnsupportedEncodingException.class, () -> {
            FileOperationUtil.readFileWithEncodingCheck("non_utf8.txt");
        });
        assertTrue(exception.getMessage().contains("File encoding is not UTF-8"), "文件编码格式异常提示信息不正确");
    }

    @Test
    void testIOException() {
        Exception exception = assertThrows(IOException.class, () -> {
            FileOperationUtil.writeFileWithBuffer("/root/protected_file.txt", "test");
        });
        assertTrue(exception.getMessage().contains("Failed to write to file"), "文件写入异常提示信息不正确");
    }
}