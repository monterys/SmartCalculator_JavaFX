package calc.model;

public class CreditTableEntry {

    private final int month;
    private final double monthlyPayment;
    private final double tempAmount;
    private final double overpayment;
    private final double basicDebt;

    public CreditTableEntry(int month, double basicDebt, double tempAmount, double overpayment) {
        this.month = month;
        this.monthlyPayment = basicDebt + overpayment;
        this.tempAmount = tempAmount;
        this.overpayment = overpayment;
        this.basicDebt = basicDebt;
    }

    public int getMonth() {
        return month;
    }

    public double getOverpayment() {
        return overpayment;
    }

    public double getBasicDebt() {
        return basicDebt;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public double getTempAmount() {
        return tempAmount;
    }
}
