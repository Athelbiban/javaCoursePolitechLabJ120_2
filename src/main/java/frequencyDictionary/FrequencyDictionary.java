package frequencyDictionary;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FrequencyDictionary {

    private final static String DIRPATH = "reports";
    private final static String REPORTBYALPH = "report-by-alph.txt";
    private final static String REPORTBYALPHREV = "report-by-alph-rev.txt";
    private final static String REPORTBYFREQ = "report-by-freq.txt";
    private static Map<String, Integer> dictionary = new LinkedHashMap<>();

    public static void main(String[] args) {

        FrequencyDictionary.reader();
        List<Map.Entry<String, Integer>> entryList = FrequencyDictionary.getReportByAlph();
        FrequencyDictionary.getReportByFreq(entryList);
        FrequencyDictionary.getReportByAlphRev();
        Map<String, Integer> map = FrequencyDictionary.getDictionary();
//        System.out.println(map);

        System.out.println("Отчёты созданы в директории 'reports/'");

    }

    private static void reader() {

        StringBuilder sb = new StringBuilder();
        String path = "files/j120-lab2.txt";
        File file = new File(path);
        String reg = "[\\s\\d,.!?'\":;<>/\\[\\]{}«»—\n()…*]";
        Map<String, Integer> hashMap = new LinkedHashMap<>();

        if (file.exists() && file.canRead()) {
            try (FileReader reader = new FileReader(file)) {
                char[] buff = new char[1024];
                int size;

                while ((size = reader.read(buff)) > -1) {
                    sb.append(new String(buff, 0, size));
                }

            } catch (IOException e) {
                System.out.printf("%s: Ошибка чтения файла", e);
            }

            String[] arr = sb.toString().toLowerCase().split(reg);

            for (String s : arr) {
                if (!s.isEmpty() && !s.equals("-")) {
                    hashMap.merge(s, 1, Integer::sum);
                }
            }
        }

        dictionary = hashMap;
    }

    private static void writer(List<String> list, String fileName) {

        File dir = new File(DIRPATH);
        dir.mkdirs();

        if (list != null && dir.canWrite()) {
            File newFile = new File(dir, fileName);

            try (FileWriter writer = new FileWriter(newFile)) {
                newFile.createNewFile();

                for (String s : list) {
                    writer.write(s + '\n');
                }
                writer.flush();

            } catch (IOException e) {
                System.out.printf("%s: Ошибка создания или записи файла", e);
            }
        }
    }

    private static List<Map.Entry<String, Integer>> getReportByAlph() {

        List<String> stringList = new LinkedList<>();
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(dictionary.entrySet());
        entryList.sort(Map.Entry.comparingByKey());

        for (Map.Entry<String, Integer> e: entryList) {
            stringList.add(e.getKey());
        }

        writer(stringList, REPORTBYALPH);
        return entryList;
    }

    private static void getReportByAlphRev() {

        List<String> reversedStringList = new LinkedList<>();
        List<String> resultList = new LinkedList<>();

        for (Map.Entry<String, Integer> e : dictionary.entrySet()) {
            StringBuilder sb = new StringBuilder(e.getKey()).reverse();
            reversedStringList.add(sb.toString());
        }

        Collections.sort(reversedStringList);

        // здесь кажется, что можно сделать проще, избавившись от этого цикла, но не пойму как
        for (String s : reversedStringList) {
            StringBuilder sb = new StringBuilder(s).reverse();
            resultList.add(sb.toString());
        }

        writer(resultList, REPORTBYALPHREV);

    }

    private static void getReportByFreq(List<Map.Entry<String, Integer>> entryList) {

        List<String> stringList = new LinkedList<>();
        Collections.reverse(entryList);
        entryList.sort(Map.Entry.comparingByValue());
        Collections.reverse(entryList);

        for (Map.Entry<String, Integer> e: entryList) {
            stringList.add(e.getKey());
        }

        writer(stringList, REPORTBYFREQ);
    }

    // *реализуйте метод, возвращающий словарь найденных слов без возможности изменения словаря
    public static Map<String, Integer> getDictionary() {
        return dictionary.isEmpty() ? null : new LinkedHashMap<>(dictionary);
    }
}