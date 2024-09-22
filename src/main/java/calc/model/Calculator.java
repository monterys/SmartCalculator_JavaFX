package calc.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Calculator implements ICalculator {

    private static final String HISTORY_FILE = "history.txt";

    static {
//        System.loadLibrary("calc");
        System.load("/opt/monterysCalc/monteryscalc/lib/app/libcalc.so");
    }

    public native double calculate(String expression, double x);

    public native boolean verification(String expression);

    public List<HistoryEntry> loadHistory() {
        List<HistoryEntry> historyEntryList = new ArrayList<>();
        try {
            Path path = Paths.get(HISTORY_FILE);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            System.out.println("Не удалось загрузить историю: " + e.getMessage());
        }
        try (BufferedReader br = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                HistoryEntry historyEntry = new HistoryEntry(line);
                historyEntryList.add(historyEntry);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return historyEntryList;
    }

    public void saveHistory(List<HistoryEntry> history) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(HISTORY_FILE))) {
            for (HistoryEntry historyEntry : history) {
                bw.write(historyEntry.getHistory());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
