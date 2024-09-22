package calc.view;

import calc.model.HistoryEntry;
import calc.presenter.CalcPresenter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.chart.LineChart;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.util.List;
import java.util.function.UnaryOperator;

public class CalcController implements ICalcView {

    private CalcPresenter presenter;
    @FXML
    private TextField showLabel;
    @FXML
    private TextField xequalsText;
    @FXML
    private Button cos;
    @FXML
    private Button sin;
    @FXML
    private Button tan;
    @FXML
    private Button creditButton;
    @FXML
    private Button acos;
    @FXML
    private Button asin;
    @FXML
    private Button atan;
    @FXML
    private Button number1;
    @FXML
    private Button number2;
    @FXML
    private Button sqrt;
    @FXML
    private Button scale;
    @FXML
    private Button number4;
    @FXML
    private Button number5;
    @FXML
    private Button openBracket;
    @FXML
    private Button closeBracket;
    @FXML
    private Button mod;
    @FXML
    private Button number7;
    @FXML
    private Button number8;
    @FXML
    private Button ac;
    @FXML
    private Button log;
    @FXML
    private Button ln;
    @FXML
    private Button x;
    @FXML
    private Button number0;
    @FXML
    private Button backspace;
    @FXML
    private Button number3;
    @FXML
    private Button number6;
    @FXML
    private Button number9;
    @FXML
    private Button point;
    @FXML
    private Button division;
    @FXML
    private Button mult;
    @FXML
    private Button sub;
    @FXML
    private Button sum;
    @FXML
    private Button equals;
    @FXML
    private TableView<HistoryEntry> historyTable;
    @FXML
    private TableColumn<HistoryEntry, String> historyColumn;
    @FXML
    private Button deleteHistory;
    @FXML
    private Button plot;
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private TextField xmaxValue;
    @FXML
    private TextField xminValue;
    @FXML
    private TextField ymaxValue;
    @FXML
    private TextField yminValue;
    @FXML
    private Button help;
    @FXML
    private Button Ee;

    @FXML
    public void initialize() {
        this.presenter = new CalcPresenter(this);
        creditButton.setOnAction(event -> openCreditWindow());
        number0.setOnAction(event -> showLabel.setText(showLabel.getText() + "0"));
        number1.setOnAction(event -> showLabel.setText(showLabel.getText() + "1"));
        number2.setOnAction(event -> showLabel.setText(showLabel.getText() + "2"));
        number3.setOnAction(event -> showLabel.setText(showLabel.getText() + "3"));
        number4.setOnAction(event -> showLabel.setText(showLabel.getText() + "4"));
        number5.setOnAction(event -> showLabel.setText(showLabel.getText() + "5"));
        number6.setOnAction(event -> showLabel.setText(showLabel.getText() + "6"));
        number7.setOnAction(event -> showLabel.setText(showLabel.getText() + "7"));
        number8.setOnAction(event -> showLabel.setText(showLabel.getText() + "8"));
        number9.setOnAction(event -> showLabel.setText(showLabel.getText() + "9"));
        cos.setOnAction(event -> showLabel.setText(showLabel.getText() + "cos("));
        sin.setOnAction(event -> showLabel.setText(showLabel.getText() + "sin("));
        tan.setOnAction(event -> showLabel.setText(showLabel.getText() + "tan("));
        acos.setOnAction(event -> showLabel.setText(showLabel.getText() + "acos("));
        asin.setOnAction(event -> showLabel.setText(showLabel.getText() + "asin("));
        atan.setOnAction(event -> showLabel.setText(showLabel.getText() + "atan("));
        sqrt.setOnAction(event -> showLabel.setText(showLabel.getText() + "sqrt("));
        scale.setOnAction(event -> showLabel.setText(showLabel.getText() + "^"));
        openBracket.setOnAction(event -> showLabel.setText(showLabel.getText() + "("));
        closeBracket.setOnAction(event -> showLabel.setText(showLabel.getText() + ")"));
        mod.setOnAction(event -> showLabel.setText(showLabel.getText() + "mod"));
        log.setOnAction(event -> showLabel.setText(showLabel.getText() + "log("));
        ln.setOnAction(event -> showLabel.setText(showLabel.getText() + "ln("));
        x.setOnAction(event -> showLabel.setText(showLabel.getText() + "x"));
        point.setOnAction(event -> showLabel.setText(showLabel.getText() + "."));
        division.setOnAction(event -> showLabel.setText(showLabel.getText() + "/"));
        mult.setOnAction(event -> showLabel.setText(showLabel.getText() + "*"));
        sub.setOnAction(event -> showLabel.setText(showLabel.getText() + "-"));
        sum.setOnAction(event -> showLabel.setText(showLabel.getText() + "+"));
        ac.setOnAction(event -> showLabel.setText(""));
        Ee.setOnAction(event -> showLabel.setText(showLabel.getText() + "E"));
        backspace.setOnAction(event -> backspaceFoo());
        equals.setOnAction(event -> onEqualsClicked());
        deleteHistory.setOnAction(event -> historyTable.getItems().clear());
        plot.setOnAction(event -> plotFoo());
        help.setOnAction(event -> openHelpWindow());

        xmaxValue.setText("10");
        xminValue.setText("-10");
        ymaxValue.setText("10");
        yminValue.setText("-10");

        historyColumn.setCellValueFactory(new PropertyValueFactory<>("history"));
        retrieveValueFromTable();
        formaterFoo();
        loadHistoryFromFile();

        Platform.runLater(() -> {
            Stage stage = (Stage) showLabel.getScene().getWindow();
            stage.setOnCloseRequest(this::onClose);
        });

    }

    @Override
    public void setPresenter(CalcPresenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void updateDisplay(String value) {
        showLabel.setText(value);
    }

    @Override
    public void addHistoryEntry(HistoryEntry entry) {
        historyTable.getItems().add(entry);
    }

    @FXML
    private void openHelpWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Help.fxml"));
            Parent root = loader.load();
            Stage helpStage = new Stage();
            helpStage.setTitle("HELP");
            helpStage.initModality(Modality.APPLICATION_MODAL);
            helpStage.setScene(new Scene(root, 800, 450)); // Установите размеры по мере необходимости
            helpStage.showAndWait();
        } catch (Exception e) {
            System.out.println("Не удалось открыть окно помощи: " + e.getMessage());
        }
    }

    private void onClose(WindowEvent event) {
        List<HistoryEntry> historyEntries = historyTable.getItems();
        presenter.saveHistoryToFile(historyEntries);
    }

    private void loadHistoryFromFile() {
        List<HistoryEntry> historyEntries = presenter.loadHistory();
        historyTable.getItems().addAll(historyEntries);
    }

    private void formaterFoo() {
        System.out.println(System.getProperty("java.library.path"));

        UnaryOperator<TextFormatter.Change> numericFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?\\d*.?\\d*")) {
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(numericFilter);
        xequalsText.setTextFormatter(textFormatter);
        TextFormatter<String> textFormatter1 = new TextFormatter<>(numericFilter);
        xminValue.setTextFormatter(textFormatter1);
        TextFormatter<String> textFormatter2 = new TextFormatter<>(numericFilter);
        xmaxValue.setTextFormatter(textFormatter2);
        TextFormatter<String> textFormatter3 = new TextFormatter<>(numericFilter);
        ymaxValue.setTextFormatter(textFormatter3);
        TextFormatter<String> textFormatter4 = new TextFormatter<>(numericFilter);
        yminValue.setTextFormatter(textFormatter4);
    }

    private void plotFoo() {
        String expression = showLabel.getText();
        lineChart.getData().clear();
        NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) lineChart.getYAxis();

        double x_max = Double.parseDouble(xmaxValue.getText());
        if (x_max > 1000000) x_max = 1000000;
        double x_min = Double.parseDouble(xminValue.getText());
        if (x_min > 1000000) x_min = 1000000;
        double y_max = Double.parseDouble(ymaxValue.getText());
        if (y_max > 1000000) y_max = 1000000;
        double y_min = Double.parseDouble(yminValue.getText());
        if (y_min > 1000000) y_min = 1000000;
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(x_min);
        xAxis.setUpperBound(x_max);
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(y_min);
        yAxis.setUpperBound(y_max);

        XYChart.Series<Number, Number> series = presenter.plotFoo(expression, x_min, x_max);
        series.setName(expression);

        for (XYChart.Data<Number, Number> data : series.getData()) {
            data.nodeProperty().addListener((obs, oldNode, newNode) -> {
                if (newNode != null) {
                    newNode.setVisible(false); // Скрыть точки
                }
            });
        }

        lineChart.getData().add(series);
    }

    private void retrieveValueFromTable() {
        TableView.TableViewSelectionModel<HistoryEntry> selectionModel = historyTable.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((val, oldVal, newVal) -> {
            if (newVal != null) showLabel.setText(newVal.getHistory());
        });
    }

    private void onEqualsClicked() {
        String expression = showLabel.getText();
        double x;
            try {
                x = Double.parseDouble(xequalsText.getText());
            } catch (NumberFormatException e) {
                x = 0;
                xequalsText.setText("0");
            }
        presenter.onEqualsClicked(expression, x);
    }

    private void backspaceFoo() {
        String currentText = showLabel.getText();
        if (!currentText.isEmpty()) {
            showLabel.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    private void openCreditWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Credit.fxml"));
            Parent root = loader.load();

            Stage creditStage = new Stage();
            creditStage.setTitle("Credit Calc");
            creditStage.initModality(Modality.APPLICATION_MODAL);
            creditStage.setScene(new Scene(root));
            creditStage.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
