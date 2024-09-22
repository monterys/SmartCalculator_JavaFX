package calc.presenter;

import calc.model.HistoryEntry;
import calc.model.Calculator;
import calc.model.ICalculator;
import calc.view.ICalcView;
import javafx.scene.chart.XYChart;

import java.util.List;

public class CalcPresenter {
    private ICalcView view;
    private ICalculator calculator;

    public CalcPresenter(ICalcView view) {
        this.view = view;
        this.calculator = new Calculator();
        this.view.setPresenter(this);
    }

    public XYChart.Series<Number, Number> plotFoo(String expression, double xMin, double xMax) {
        view.addHistoryEntry(new HistoryEntry(expression));
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        if (calculator.verification(expression)) {
            double xCoordinate;
            double h = (xMax - xMin) / 1000;
            for (xCoordinate = xMin; xCoordinate <= xMax; xCoordinate += h) {
                series.getData().add(new XYChart.Data<>(xCoordinate, calculator.calculate(expression, xCoordinate)));
            }
        } else {
            view.updateDisplay("Invalid Expression");
        }
        return series;
    }

    public void onEqualsClicked(String expression, double x) {
        view.addHistoryEntry(new HistoryEntry(expression));
        if (calculator.verification(expression)) {
            double result = calculator.calculate(expression, x);
            view.updateDisplay(String.valueOf(result));
        } else {
            view.updateDisplay("Invalid Expression");
        }
    }

    public void saveHistoryToFile(List<HistoryEntry> history) {
        calculator.saveHistory(history);
    }

    public List<HistoryEntry> loadHistory() {
        return calculator.loadHistory();
    }




}

