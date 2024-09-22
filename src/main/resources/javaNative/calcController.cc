#include "calcController.h"

namespace s21 {

calcControl::calcControl(char *str, double x) : c_(str, x) {}

double calcControl::calculate() {
  c_.parcer();
  c_.rpn();
  c_.calculate();
  return c_.Get();
}

int calcControl::verification() { return c_.verification(); }

}  // namespace s21