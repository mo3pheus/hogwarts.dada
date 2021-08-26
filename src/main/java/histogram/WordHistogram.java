package histogram;

import sun.misc.IOUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class WordHistogram {
    public static void createHistogram(String inputFile, String outputFile) throws Exception {
        System.out.println("This is generating a word histogram.");

        String fileName = inputFile;
        String histogramFile = outputFile;

        Map<String, Integer> histogram = generateHistogram(fileName);
        saveHistogram(histogram, histogramFile);
    }

    static String extractContent(String fileName) throws Exception {
        byte[] content = IOUtils.readAllBytes(new FileInputStream(new File(fileName)));
        return new String(content);
    }

    static Map<String, Integer> buildHistogram(String content) {
        Map<String, Integer> histogram = new HashMap<>();
        String[] words = content.split(" ");
        for (int i = 0; i < words.length; i++) {
            if (histogram.containsKey(words[i])) {
                Integer previousCount = histogram.get(words[i]);
                Integer newCount = previousCount + 1;
                histogram.put(words[i], new Integer(newCount));
            } else {
                histogram.put(words[i], new Integer(1));
            }
        }
        return histogram;
    }

    static Map<String, Integer> generateHistogram(String inputFileName) throws Exception {
        String content = extractContent(inputFileName);
        return buildHistogram(content);
    }

    static void saveHistogram(Map<String, Integer> histogram, String histogramFile) throws Exception {
        String convertedHistogram = convertMapToString(histogram);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(histogramFile)));
        bufferedWriter.write(convertedHistogram);
        bufferedWriter.close();
    }

    static String convertMapToString(Map<String, Integer> histogram) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String word : histogram.keySet()) {
            String contentLine = word + ":" + histogram.get(word) + "\n";
            stringBuilder.append(contentLine);
        }

        return stringBuilder.toString();
    }
}

