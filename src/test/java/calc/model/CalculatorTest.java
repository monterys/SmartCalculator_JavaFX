package calc.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @AfterEach
    public void tearDown() {
        File historyFile = new File("history.txt");
        if (historyFile.exists()) {
            historyFile.delete();
        }
    }

    @Test
    void Test1() {
        String expression  = "2051+311.22+1.32-1.8+0.036";
        double x = 0;
        double benchmark = 2051+311.22+1.32-1.8+0.036;
        assertTrue(calculator.verification(expression));
        assertEquals(benchmark, calculator.calculate(expression, x));
    }

    @Test
    void Test2() {
        String expression  = "1/((1/3)+1)";
        double x = 0;
        double res = 0;
        if (calculator.verification(expression)) {
            res = calculator.calculate(expression, x);
        }
        double benchmark = 1. / ((1. / 3) + 1);
        assertEquals(benchmark, res);
    }

    @Test
    void Test3() {
        String expression  = "1/sin(3)+1.4-asin(1)";
        double x = 0;
        double res = 0;
        if (calculator.verification(expression)) {
            res = calculator.calculate(expression, x);
        }
        double benchmark = 1 / Math.sin(3) + 1.4 - Math.asin(1);
        assertEquals(benchmark, res);
    }

    @Test
    void Test4() {
        String expression  = "(2^3+(tan(2)/(2.5+3)))";
        double x = 0;
        double res = 0;
        if (calculator.verification(expression)) {
            res = calculator.calculate(expression, x);
        }
        double benchmark = Math.pow(2, 3) + Math.tan(2) / (2.5 +3);
        assertEquals(benchmark, res);
        assertTrue(calculator.verification(expression));
    }

    @Test
    public void testLoadHistory_EmptyFile() {
        List<HistoryEntry> history = calculator.loadHistory();
        assertTrue(history.isEmpty());
    }


    @Test
    public void testSaveHistory() {
        HistoryEntry entry1 = new HistoryEntry("2+2");
        HistoryEntry entry2 = new HistoryEntry("3*3");
        List<HistoryEntry> historyToSave = new ArrayList<>();
        historyToSave.add(entry1);
        historyToSave.add(entry2);
        calculator.saveHistory(historyToSave);
        List<HistoryEntry> loadedHistory = calculator.loadHistory();
        assertEquals(2, loadedHistory.size());
        assertEquals("2+2", loadedHistory.get(0).getHistory());
        assertEquals("3*3", loadedHistory.get(1).getHistory());
    }


    @Test
    public void testLoadHistory_NonEmptyFile() {
        HistoryEntry entry1 = new HistoryEntry("5-3");
        List<HistoryEntry> historyToSave = new ArrayList<>();
        historyToSave.add(entry1);
        calculator.saveHistory(historyToSave);
        List<HistoryEntry> history = calculator.loadHistory();
        assertEquals(1, history.size());
        assertEquals("5-3", history.get(0).getHistory());
    }


    @Test
    public void testSaveHistory_FileCreation() {
        HistoryEntry entry = new HistoryEntry("1+1");
        List<HistoryEntry> historyToSave = new ArrayList<>();
        historyToSave.add(entry);
        calculator.saveHistory(historyToSave);
        File historyFile = new File("history.txt");
        assertTrue(historyFile.exists());
    }
}
