#ifndef CPP3_SMARTCALC_V2_1_CALCMODEL_H
#define CPP3_SMARTCALC_V2_1_CALCMODEL_H

#include <math.h>
#include <string.h>

#include <iostream>
#include <queue>
#include <stack>

namespace s21 {

typedef enum value_type {
  NUM,  // 0123456789
  VAR,  // x

  BRACKET_OPEN,   // (
  BRACKET_CLOSE,  // )

  SUM,  // +
  SUB,  // -

  MULT,  // *
  DIV,   // /
  MOD,   // m

  UNAR_PLUS,   // p
  UNAR_MINUS,  // u

  ELEVATION,  // ^
  COS,        // c
  SIN,        // s
  TAN,        // t
  ACOS,       // C
  ASIN,       // S
  ATAN,       // T
  SQRT,       // k
  LN,         // l
  LOG,        // g

} type_t;

class calc {
 public:
  class lexem {
   public:
    lexem(double value, type_t type);

    void SetType(type_t t) { type_ = t; }
    void SetValue(double v) { value_ = v; }
    double GetValue() { return value_; }
    type_t GetType() { return type_; }

   private:
    double value_;
    type_t type_;
  };

  calc(char *str, double x);

  void calculate();
  void rpn();
  void parcer();
  int verification();
  double Get() { return result_; }

 private:
  void shift(int *i, int len, int n);
  int sinusItd(int *ind);
  void operation(char c, int *unar);
  int writeFloat(const char *str, double *res);
  void operand_out(std::stack<type_t> *operand, type_t p);
  void sum();
  void sub();
  void mult();
  void division();
  void elevation();
  void mod();
  void unar_minus();
  void cosinus();
  void sinus();
  void tangens();
  void arccosinus();
  void arcsinus();
  void arctangens();
  void sqrt_func();
  void ln_func();
  void log_func();

  char *str_;
  double x_;
  std::queue<lexem> infix_;
  std::queue<lexem> polish_;
  std::stack<lexem> stack_;
  double result_;
};

}  // namespace s21

#endif  // CPP3_SMARTCALC_V2_1_CALCMODEL_H