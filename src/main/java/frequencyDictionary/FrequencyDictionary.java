package frequencyDictionary;

import jdk.jfr.Frequency;

import java.io.*;
import java.util.*;

public class FrequencyDictionary {

    private String text;

    public void Reader() {

        StringBuilder sb = new StringBuilder();
        String path = "folder1/j120-lab2.txt";
        File file = new File(path);

        if (file.exists() && file.canRead()) {
            try (FileReader reader = new FileReader(file)) {
                char[] buff = new char[1024];
                int size;
                while ((size = reader.read(buff)) > -1) {
                    sb.append(new String(buff, 0, size));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        text = sb.toString();
    }

    public void Writer() {

        File dir = new File("folder2");
        dir.mkdirs();
        File newFile = new File(dir, "file2.txt");
//        String text = "I write this text.";
//        String text = FrequencyDictionary.Reader();

        if (dir.canWrite()) {
            try {
                newFile.createNewFile();
                FileWriter writer = new FileWriter(newFile);
                writer.write(text);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        example();
    }

    public void reportByAlph() {

        File dir = new File("reports");
        dir.mkdirs();
        File newFile = new File(dir, "report-by-alph.txt");
//        String text = "I write this text.";

        if (text != null) {
            String reg = "[\\s,.!?'\":;<>/\\[\\]{}«»—\n()…*]";
            String[] stringArr = text.toLowerCase().split(reg);
//            System.out.println(Arrays.toString(stringArr));
//            System.out.println(stringArr[0]);

            if (dir.canWrite()) {
                try {
                    newFile.createNewFile();
                    FileWriter writer = new FileWriter(newFile);
                    for (String s : stringArr) {
                        if (!s.isEmpty() && !s.equals("-")) {
                            writer.write(s + '\n');
                        }
                    }
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void print() {
        System.out.println(text);
    }

    public static void example() {
        System.out.println("=============ArrayList=============");
        List<Integer> arrList = new ArrayList<>();
        addAll(arrList);
        System.out.println(arrList);

        System.out.println("=============LinkedList=============");
        List<Integer> linkList = new LinkedList<>();
        addAll(linkList);
        System.out.println(linkList);

        System.out.println("=============HashMap=============");
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        System.out.println(map.entrySet());

        System.out.println("===========LinkedHashMap=========");
        Map<String, Integer> hashMap = new LinkedHashMap<>();
        hashMap.put("z", 9);
        hashMap.put("y", 8);
        hashMap.put("x", 7);
        System.out.println(hashMap);
    }

    static void addAll(Collection<? super Integer> list) {
        list.add(1);
        list.add(5);
        list.add(3);
        list.add(17);
        list.add(6);
    }
}