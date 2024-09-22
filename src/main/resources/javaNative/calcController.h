#ifndef CPP3_SMARTCALC_V2_1_CALCCONTROLLER_H
#define CPP3_SMARTCALC_V2_1_CALCCONTROLLER_H

#include "calcModel.h"

namespace s21 {
class calcControl {
 public:
  calcControl(char *str, double x);

  int verification();
  double calculate();

 private:
  calc c_;
};

};  // namespace s21

#endif  // CPP3_SMARTCALC_V2_1_CALCCONTROLLER_H