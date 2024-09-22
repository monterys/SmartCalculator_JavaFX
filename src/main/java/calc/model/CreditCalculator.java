package calc.model;

import java.util.ArrayList;
import java.util.List;

public class CreditCalculator {

    static {
//        System.loadLibrary("creditcalc");
        System.load("/opt/monterysCalc/monteryscalc/lib/app/libcreditcalc.so");
    }
    private final double sumCred;
    private final int creditPeriod;
    private final double percent;
    private double monthlyPayment;
    private double creditOverpayment;
    private double totalPayment;
    List<CreditTableEntry> tableEntries;

    public CreditCalculator(double sumCred, int creditPeriod, double percent) {
        this.sumCred = sumCred;
        this.creditPeriod = creditPeriod;
        this.percent = percent;
        tableEntries = new ArrayList<>();
    }

    public void annuitent() { calculate(true); }

    public void different() { calculate(false); }

    private native void calculate(boolean bool);

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public double getCreditOverpayment() {
        return creditOverpayment;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public List<CreditTableEntry> getTable() {

        return tableEntries;
    }

    public void setTableEntries(List<CreditTableEntry> tableEntries) {
        this.tableEntries = tableEntries;
    }
}


