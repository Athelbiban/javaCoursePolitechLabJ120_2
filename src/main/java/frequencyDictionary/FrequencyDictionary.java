package frequencyDictionary;

import jdk.jfr.Frequency;

import java.io.*;
import java.util.*;

public class FrequencyDictionary {
    public static String[] Reader(String path) {

        StringBuilder sb = new StringBuilder();
        File file = new File(path);
        String reg = "[\\s\\d,.!?'\":;<>/\\[\\]{}«»—\n()…*0123456789]";

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

        return sb.toString().toLowerCase().split(reg);
    }

    public static void getReportByAlph(String[] stringArr) {

        File dir = new File("reports");
        if (stringArr != null && dir.mkdirs() && dir.canWrite()) {
            File newFile = new File(dir, "report-by-alph.txt");

            try (FileWriter writer = new FileWriter(newFile)) {
                newFile.createNewFile();
                Arrays.sort(stringArr);
//                System.out.println(Arrays.toString(stringArr));

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