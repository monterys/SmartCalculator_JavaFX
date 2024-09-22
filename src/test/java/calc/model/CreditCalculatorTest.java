package calc.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreditCalculatorTest {

    private CreditCalculator creditCalculator;

    @BeforeEach
    public void setUp() {
        double sumCred = 100000;
        int creditPeriod = 10;
        double percent = 10;
        creditCalculator = new CreditCalculator(sumCred, creditPeriod, percent);
    }

    @Test
    public void testAnnuitentPaymentCalculation() {
        creditCalculator.annuitent();

        double expectedMonthlyPayment = 10464.04;
        double expectedOverpayment = 4640.38;
        double expectedTotalPayment = 104640.38;
        assertEquals(expectedMonthlyPayment, creditCalculator.getMonthlyPayment(), 0.01);
        assertEquals(expectedOverpayment, creditCalculator.getCreditOverpayment(), 0.01);
        assertEquals(expectedTotalPayment, creditCalculator.getTotalPayment(), 0.01);
    }

    @Test
    public void testDifferentPaymentCalculation() {
        creditCalculator.different();

        double expectedOverpayment = 4583.33;
        double expectedTotalPayment = 104583.33;
        assertEquals(expectedOverpayment, creditCalculator.getCreditOverpayment(), 0.01);
        assertEquals(expectedTotalPayment, creditCalculator.getTotalPayment(), 0.01);
    }

    @Test
    public void testTableEntriesInitialization() {
        assertNotNull(creditCalculator.getTable());
        assertTrue(creditCalculator.getTable().isEmpty());
    }

    @Test
    public void testSetTableEntries() {
        CreditTableEntry entry = new CreditTableEntry(1, 10000, 90000, 833.33);
        List<CreditTableEntry> newEntries = new ArrayList<>();
        newEntries.add(entry);
        creditCalculator.setTableEntries(newEntries);

        assertEquals(1, creditCalculator.getTable().size());
        assertEquals(newEntries.get(0), creditCalculator.getTable().get(0));
    }
}

