#include "creditModel.h"

namespace s21 {

credit::credit(double amount, double percent, int period)
    : amount_(amount),
      percent_(percent),
      period_(period),
      overpayment_(0),
      total_payment_(0) {}

credit::dif::dif(double overpayment, double monthly_payment, double temp_amount,
                 int month)
    : overpayment_(overpayment),
      monthly_payment_(monthly_payment),
      temp_amount_(temp_amount),
      month_(month) {}

void credit::differentialCreditCalc() {
  double monthly_percent = percent_ / 1200;
  double base_monthly_payment = amount_ / period_;
  double temp_amount = amount_;
  monthly_payment_ = 0;
  for (int i = 0; i < period_; i++) {
    double temp_over = temp_amount * monthly_percent;
    temp_amount -= base_monthly_payment;
    dif d(temp_over, base_monthly_payment, temp_amount, i + 1);
    table_.push(d);
    overpayment_ += temp_over;
  }
  total_payment_ = amount_ + overpayment_;
}

void credit::annuitantCreditCalc() {
  double monthly_percent = percent_ / 1200;
  monthly_payment_ =
      amount_ * (monthly_percent * (pow((1 + monthly_percent), period_)) /
                 (pow((1 + monthly_percent), period_) - 1));
  double temp_amount = amount_;
  for (int i = 0; i < period_; i++) {
    double temp_over = temp_amount * monthly_percent;
    double base_payment = monthly_payment_ - temp_over;
    temp_amount -= base_payment;
    dif d(temp_over, base_payment, temp_amount, i + 1);
    table_.push(d);
    overpayment_ += temp_over;
  }
  total_payment_ = overpayment_ + amount_;
}

void credit::dmp(double *overpayment, double *monthly_payment,
                 double *temp_amount, int *month) {
  *overpayment = table_.front().GetOverpayment();
  *monthly_payment = table_.front().GetMonthlyPayment();
  *temp_amount = table_.front().GetTempAmount();
  *month = table_.front().GetMonth();
  table_.pop();
}

}  // namespace s21