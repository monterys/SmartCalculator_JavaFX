#ifndef CPP3_SMARTCALC_V2_1_CREDITMODEL_H
#define CPP3_SMARTCALC_V2_1_CREDITMODEL_H

#include <math.h>

#include <queue>

namespace s21 {
class credit {
 public:
  class dif {
   public:
    dif(double overpayment, double monthly_payment, double temp_amount,
        int month);

    double GetOverpayment() { return overpayment_; }
    double GetMonthlyPayment() { return monthly_payment_; }
    double GetTempAmount() { return temp_amount_; }
    int GetMonth() { return month_; }

   private:
    double overpayment_;
    double monthly_payment_;
    double temp_amount_;
    int month_;
  };

  credit(double amount, double percent, int period);

  void annuitantCreditCalc();
  void differentialCreditCalc();
  void dmp(double *overpayment, double *monthly_payment, double *temp_amount,
           int *month);

  double GetOverpayment() { return overpayment_; }
  double GetMonthlyPayment() { return monthly_payment_; }
  double GetTotalPayment() { return total_payment_; }
  bool tableEmpty() { return table_.empty(); }
  std::queue<dif> GetTable() { return table_; }

 private:
  std::queue<dif> table_;
  double amount_;
  double percent_;
  int period_;
  double monthly_payment_;
  double overpayment_;
  double total_payment_;
};

}  // namespace s21

#endif  // CPP3_SMARTCALC_V2_1_CREDITMODEL_H