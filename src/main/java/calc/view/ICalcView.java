package calc.view;

import calc.presenter.CalcPresenter;
import calc.model.HistoryEntry;

public interface ICalcView {
    void setPresenter(CalcPresenter presenter);
    void updateDisplay(String value);
    void addHistoryEntry(HistoryEntry entry);
}
