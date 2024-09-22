package calc.model;

import javafx.beans.property.SimpleStringProperty;

public class HistoryEntry {
    private SimpleStringProperty history;

    public HistoryEntry(String history) {
        this.history = new SimpleStringProperty(history);
    }

    public String getHistory() {
        return history.get();
    }
}

