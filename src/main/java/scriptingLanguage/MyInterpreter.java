package scriptingLanguage;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyInterpreter {

    private final static String FILEPATH = "files/script.txt";
    private static final Pattern PATTERN_COMMENT = Pattern.compile("^#");
    private static final Pattern PATTERN_PRINT = Pattern.compile("^print\\s+");
    private static final Pattern PATTERN_PRINT1 = Pattern.compile("^\"(.*?)\"");
    private static final Pattern PATTERN_PRINT2 = Pattern.compile("^\\$(\\w+)");
    private static final Pattern PATTERN_PRINT3 = Pattern.compile("^,\\s*(.+)");
    private static final Pattern PATTERN_SET = Pattern.compile("^set\\s+");
    private static final Pattern PATTERN_SET1 = Pattern.compile("^\\$(\\w+)\\s*=\\s*(\\$?-?\\w+)");
    private static final Pattern PATTERN_SET2 = Pattern.compile("\\s*.\\s*\\$?\\w+");
    private static final Pattern PATTERN_CALC = Pattern.compile("^\\$?(\\w+)");
    private static final Pattern PATTERN_CALC1 = Pattern.compile("\\$?(\\w+)$");
    private static final Pattern PATTERN_CALC2 = Pattern.compile("[+-]");
    private static final String REG_CALC = "-?\\d+";
    private static final Map<String, Integer> VARIABLES = new LinkedHashMap<>();
    private static int LINE_COUNTER = 0;

    public static void main(String[] args) {
        read();
    }

    private static void read() {

        File file = new File(FILEPATH);

        if (file.exists() && file.canRead()) {
            try (Scanner sc = new Scanner(file)) {
                while (sc.hasNextLine()) {
                    String s = sc.nextLine();
                    execute(s);
                }
            }catch (IOException e) {
                System.out.printf("%s: Ошибка чтения файла", e);
            }
        }
    }

    private static void execute(String str) {

        LINE_COUNTER++;
        Matcher matcherSet = PATTERN_SET.matcher(str);
        Matcher matcherPrint = PATTERN_PRINT.matcher(str);
        Matcher matcherComment = PATTERN_COMMENT.matcher(str);

        if (matcherSet.find()) { set(str.substring(matcherSet.end())); }

        else if (matcherPrint.find()) { print(str.substring(matcherPrint.end())); }

        else if (str.isEmpty() || matcherComment.find()) { }

        else { throw new IllegalArgumentException("Неизвестная ошибка в строке " + LINE_COUNTER); }

    }

    private static void set(String str) {

        Matcher setMatcher1 = PATTERN_SET1.matcher(str);

        if (setMatcher1.find()) {

            if (setMatcher1.group(2).charAt(0) == '$') {
                VARIABLES.put(setMatcher1.group(1), getValue(setMatcher1.group(2).substring(1)));
            }
            else {
                VARIABLES.put(setMatcher1.group(1), Integer.parseInt(setMatcher1.group(2)));
            }

            Matcher setMatcher3 = PATTERN_SET2.matcher(str.substring(setMatcher1.end()));

            while (setMatcher3.find()) {
                VARIABLES.put(setMatcher1.group(1), calculate(setMatcher1.group(1) + setMatcher3.group()));
            }

        }
        else {
            throw new IllegalArgumentException("Неизвестная ошибка в строке " + LINE_COUNTER);
        }

    }

    private static void print(String str) {

        Matcher printMatcher1 = PATTERN_PRINT1.matcher(str);
        Matcher printMatcher2 = PATTERN_PRINT2.matcher(str);
        Matcher printMatcher3;

        if (printMatcher1.find()) {
            System.out.print(printMatcher1.group(1));
            printMatcher3 = PATTERN_PRINT3.matcher(str.substring(printMatcher1.end()));
        }
        else if (printMatcher2.find()) {
            System.out.print(getValue(printMatcher2.group().substring(1)));
            printMatcher3 = PATTERN_PRINT3.matcher(str.substring(printMatcher2.end()));
        }
        else {
            throw new IllegalArgumentException("Неизвестная ошибка в строке " + LINE_COUNTER);
        }

        if (printMatcher3.find()) { print(printMatcher3.group(1)); }
        else { System.out.println(); }

    }

    private static Integer getValue(String key) {

        Integer result = VARIABLES.get(key);

        if (result == null) {
            throw new NullPointerException ("Обращение к неизвестной переменной в строке " + LINE_COUNTER);
        }

        return result;
    }

    private static Integer calculate(String str) {

        Integer a, b;
        Matcher calcMatcher = PATTERN_CALC.matcher(str);
        Matcher calcMatcher1 = PATTERN_CALC1.matcher(str);
        Matcher calcMatcher2 = PATTERN_CALC2.matcher(str);

        if (calcMatcher.find()) {
            a = (calcMatcher.group(1)).matches(REG_CALC)
                    ? Integer.parseInt(calcMatcher.group(1))
                    : getValue(calcMatcher.group(1));
        }
        else {
            throw new RuntimeException("Неверное значение первой переменной в строке " + LINE_COUNTER);
        }

        if (calcMatcher1.find()) {
            b = calcMatcher1.group().charAt(0) != '$'
                    ? Integer.parseInt(calcMatcher1.group(1))
                    : getValue(calcMatcher1.group(1));
        }
        else {
            throw new RuntimeException("Неверное значение второй переменной в строке " + LINE_COUNTER);
        }

        if (calcMatcher2.find()) {

            if (calcMatcher2.group().equals("+")) {
                return a + b;
            }
            else {
                return a - b;
            }

        }
        else {
            throw new IllegalArgumentException("Неизвестный математический оператор в строке " + LINE_COUNTER);
        }

    }
}
