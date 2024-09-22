package calc.view;

import calc.model.CreditCalculator;
import calc.model.CreditTableEntry;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.List;
import java.util.function.UnaryOperator;

public class CreditController {

    private CreditCalculator creditCalculator;
    @FXML
    private TextField sumCredShow;
    @FXML
    private TextField creditPeriodShow;
    @FXML
    private TextField percentShow;
    @FXML
    private RadioButton annuitent;
    @FXML
    private RadioButton different;
    @FXML
    private Label monthlyPaymentShow;
    @FXML
    private Label creditOverpaymentShow;
    @FXML
    private Label totalPaymentShow;
    @FXML
    private TableView<CreditTableEntry> table;
    @FXML
    private TableColumn<CreditTableEntry, Integer> monthColumn;
    @FXML
    private TableColumn<CreditTableEntry, Double> monthlyPaymentColumn;
    @FXML
    private TableColumn<CreditTableEntry, Double> tempAmountColumn;
    @FXML
    private TableColumn<CreditTableEntry, Double> overpaymentColumn;
    @FXML
    private TableColumn<CreditTableEntry, Double> basicDebtColumn;
    @FXML
    private Button calculate;

    @FXML
    private void initialize() {
        calculate.setOnAction(event -> calculatePayments());
        formaterFooInt();
        formaterFooDouble();
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));

        monthlyPaymentColumn.setCellValueFactory(new PropertyValueFactory<>("monthlyPayment"));
        monthlyPaymentColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return String.format("%.2f", object);
            }
            @Override
            public Double fromString(String string) {
                return Double.parseDouble(string);
            }
        }));

        tempAmountColumn.setCellValueFactory(new PropertyValueFactory<>("tempAmount"));
        tempAmountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return String.format("%.2f", object);
            }

            @Override
            public Double fromString(String string) {
                return Double.parseDouble(string);
            }
        }));

        overpaymentColumn.setCellValueFactory(new PropertyValueFactory<>("overpayment"));
        overpaymentColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return String.format("%.2f", object);
            }

            @Override
            public Double fromString(String string) {
                return Double.parseDouble(string);
            }
        }));


        basicDebtColumn.setCellValueFactory(new PropertyValueFactory<>("basicDebt"));
        basicDebtColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return String.format("%.2f", object);
            }

            @Override
            public Double fromString(String string) {
                return Double.parseDouble(string);
            }
        }));
        sumCredShow.setText("100000");
        percentShow.setText("10");
        creditPeriodShow.setText("10");
        annuitent.fire();

        ToggleGroup toggleGroup = new ToggleGroup();
        annuitent.setToggleGroup(toggleGroup);
        different.setToggleGroup(toggleGroup);
    }

    private void calculatePayments() {
        String sumCredString = sumCredShow.getText();
        String creditPeriodString = creditPeriodShow.getText();
        String percentString = percentShow.getText();
        if (sumCredString.isEmpty()) { sumCredString = "0"; }
        if (creditPeriodString.isEmpty() || creditPeriodString.equals("0")) {
            creditPeriodString = "1";
            creditPeriodShow.setText("1");
        }
        if (percentString.isEmpty() || percentString.equals("0")) {
            percentString = "0.01";
            percentShow.setText("0.01");
        }

        double sumCred = Double.parseDouble(sumCredString);
        int creditPeriod = Integer.parseInt(creditPeriodString);
        double percent = Double.parseDouble(percentString);

        creditCalculator = new CreditCalculator(sumCred, creditPeriod, percent);
        if (annuitent.isSelected()) {
            creditCalculator.annuitent();
        } else if (different.isSelected()) {
            creditCalculator.different();
        }

        List<CreditTableEntry> creditTableEntryList = creditCalculator.getTable();
        Double paymentMin = 0.;
        Double paymentMax = 0.;
        int size = creditTableEntryList.size();
        System.out.println("size = " + size);

        table.getItems().clear();
        for (int i = 0; i < size; i++) {
            CreditTableEntry tmp = creditTableEntryList.get(i);
            table.getItems().add(tmp);
            if (i == 0) {
                paymentMax = tmp.getMonthlyPayment();
            }
            paymentMin = tmp.getMonthlyPayment();
        }

        creditOverpaymentShow.setText(String.format("%.2f", creditCalculator.getCreditOverpayment()));
        totalPaymentShow.setText(String.format("%.2f", creditCalculator.getTotalPayment()));
        if (annuitent.isSelected()) {
            monthlyPaymentShow.setText(String.format("%.2f", creditCalculator.getMonthlyPayment()));
        } else if (different.isSelected()) {
            monthlyPaymentShow.setText("от " + String.format("%.2f", paymentMin) + " до " + String.format("%.2f", paymentMax));
        }
    }

    private void formaterFooInt() {
        UnaryOperator<TextFormatter.Change> numericFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter1 = new TextFormatter<>(numericFilter);
        creditPeriodShow.setTextFormatter(textFormatter1);
    }

    private void formaterFooDouble() {
        UnaryOperator<TextFormatter.Change> numericFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*.?\\d{0,2}")) {
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(numericFilter);
        sumCredShow.setTextFormatter(textFormatter);
        TextFormatter<String> textFormatter2 = new TextFormatter<>(numericFilter);
        percentShow.setTextFormatter(textFormatter2);
    }
}

