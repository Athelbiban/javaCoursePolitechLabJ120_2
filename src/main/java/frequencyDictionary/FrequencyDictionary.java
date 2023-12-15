package frequencyDictionary;

import java.io.*;
import java.util.*;

public class FrequencyDictionary {
    public static void main(String[] args) {
        String path = "c:\\Users\\VostrovSO\\Downloads\\j120\\test.txt";

        File file = new File(path);
        if (file.exists() && file.canRead()) {
            StringBuilder sb = new StringBuilder();
            try (FileReader reader = new FileReader(file)) {
                char[] buff = new char[1024];
                int size;
                while ((size = reader.read(buff)) > -1) {
                    sb.append(new String(buff, 0, size));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(sb);
        }

        File dir = new File("c:\\Users\\VostrovSo\\Downloads\\j120\\myfiles");
        dir.mkdirs();
        File newFile = new File(dir, "myfile1.txt");
        String text = "Hello world!";
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

        example();
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
