package frequencyDictionary;

public class Main {
    public static void main(String[] args) {
        String[] stringArr = FrequencyDictionary.Reader("folder1/j120-lab2.txt");
        FrequencyDictionary.getReportByAlph(stringArr);
    }
}
