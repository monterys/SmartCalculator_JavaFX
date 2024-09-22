package calc.model;

import java.util.List;

public interface ICalculator {
    double calculate(String expression, double x);
    boolean verification(String expression);
    List<HistoryEntry> loadHistory();
    void saveHistory(List<HistoryEntry> history);
}
