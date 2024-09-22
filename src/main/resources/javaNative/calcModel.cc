#include "calcModel.h"

namespace s21 {

calc::calc(char *str, double x) : str_(str), x_(x) {}

calc::lexem::lexem(double value, type_t type) : value_(value), type_(type) {}

int calc::verification() {
  int status = 0, open = 0, point = 0;
  int len = strlen(str_);
  if (len > 255 || len < 1) status = 1;
  for (int i = 0; i < (len) && !status; i++) {
    if (!strchr("0123456789.x()+-*/^mcstalpuEe", str_[i])) status = 1;
    if (strchr("mcstal", str_[i])) {
      status = sinusItd(&i);
      len = strlen(str_);
    }
  }
  if (!strchr("0123456789xcstCSTklg+-(", str_[0])) status = 1;
  for (int i = 0; i < (len - 1) && !status; i++) {
    char now = str_[i], next = str_[i + 1];
    if (strchr("0123456789", now)) {
      if (!strchr("0123456789.+-*/m^Ee)", next)) status = 1;
    } else if (strchr("x", now)) {
      if (!strchr("+-*/m^)", next)) status = 1;
    } else if (strchr("Ee", now)) {
      if (!strchr("+-", next)) status = 1;
    } else if (strchr("+-*/m^", now)) {
      if (!strchr("0123456789xcstCSTklg(", next)) status = 1;
      point = 0;
    } else if (strchr("cstCSTklg", now)) {
      if (!strchr("(", next)) status = 1;
    } else if (strchr("(", now)) {
      if (!strchr("0123456789xcstCSTklg+-(", next)) status = 1;
      open++;
      point = 0;
    } else if (now == '.') {
      if (!strchr("0123456789", next) || point != 0) status = 1;
      point++;
    } else if (strchr(")", now)) {
      if (!strchr(")+-*/m^", next)) status = 1;
      if (open == 0) {
        status = 1;
      } else
        open--;
    }
  }
  if (!strchr("0123456789x)", str_[len - 1])) status = 1;
  if (str_[len - 1] == ')') {
    open--;
  }
  if (open) status = 1;
  return status;
}

int calc::sinusItd(int *ind) {
  int status = 0, i = *ind;
  int len = strlen(str_);
  if (str_[i] == 'c' && str_[i + 1] == 'o' && str_[i + 2] == 's') {
    str_[i] = 'c';  // cos
    shift(&i, len, 2);
  } else if (str_[i] == 's' && str_[i + 1] == 'i' && str_[i + 2] == 'n') {
    str_[i] = 's';  // sin
    shift(&i, len, 2);
  } else if (str_[i] == 't' && str_[i + 1] == 'a' && str_[i + 2] == 'n') {
    str_[i] = 't';  // tan
    shift(&i, len, 2);
  } else if (str_[i] == 'a' && str_[i + 1] == 'c' && str_[i + 2] == 'o' &&
             str_[i + 3] == 's') {
    str_[i] = 'C';  // acos
    shift(&i, len, 3);
  } else if (str_[i] == 'a' && str_[i + 1] == 's' && str_[i + 2] == 'i' &&
             str_[i + 3] == 'n') {
    str_[i] = 'S';  // asin
    shift(&i, len, 3);
  } else if (str_[i] == 'a' && str_[i + 1] == 't' && str_[i + 2] == 'a' &&
             str_[i + 3] == 'n') {
    str_[i] = 'T';  // atan
    shift(&i, len, 3);
  } else if (str_[i] == 's' && str_[i + 1] == 'q' && str_[i + 2] == 'r' &&
             str_[i + 3] == 't') {
    str_[i] = 'k';  // sqrt
    shift(&i, len, 3);
  } else if (str_[i] == 'l' && str_[i + 1] == 'n') {
    str_[i] = 'l';  // ln
    shift(&i, len, 1);
  } else if (str_[i] == 'l' && str_[i + 1] == 'o' && str_[i + 2] == 'g') {
    str_[i] = 'g';  // log
    shift(&i, len, 2);
  } else if (str_[i] == 'm' && str_[i + 1] == 'o' && str_[i + 2] == 'd') {
    str_[i] = 'm';  // mod
    shift(&i, len, 2);
  } else
    status = 1;
  return status;
}

void calc::shift(int *i, int len, int n) {
  for (int j = (*i + 1); j < (len - n); j++) str_[j] = str_[j + n];
  str_[len - n] = '\0';
}

void calc::parcer() {
  int len = strlen(str_);
  int unar = 1;
  for (int i = 0; i < len; i++) {
    if (str_[i] > 47 && str_[i] < 58) {
      double num = 0;
      unar = 0;
      i += writeFloat(str_ + i, &num);
      lexem l(num, NUM);
      infix_.push(l);
    } else {
      operation(str_[i], &unar);
    }
  }
}

void calc::operation(char c, int *unar) {
  lexem l(0, NUM);
  if (c == 'x') {
    l.SetValue(x_);
    *unar = 0;
  } else if (*unar && c == 45) {
    l.SetType(UNAR_MINUS);
  } else if (*unar && c == 43) {
    l.SetType(UNAR_PLUS);
  } else if (c == 43) {
    l.SetType(SUM);
  } else if (c == 45) {
    l.SetType(SUB);
  } else if (c == 42) {
    l.SetType(MULT);
  } else if (c == 47) {
    l.SetType(DIV);
  } else if (c == 'm') {
    l.SetType(MOD);
  } else if (c == 94) {
    l.SetType(ELEVATION);
  } else if (c == 99) {
    l.SetType(COS);
  } else if (c == 115) {
    l.SetType(SIN);
  } else if (c == 116) {
    l.SetType(TAN);
  } else if (c == 67) {
    l.SetType(ACOS);
  } else if (c == 83) {
    l.SetType(ASIN);
  } else if (c == 84) {
    l.SetType(ATAN);
  } else if (c == 107) {
    l.SetType(SQRT);
  } else if (c == 108) {
    l.SetType(LN);
  } else if (c == 103) {
    l.SetType(LOG);
  } else if (c == 40) {
    l.SetType(BRACKET_OPEN);
    *unar = 1;
  } else if (c == 41) {
    l.SetType(BRACKET_CLOSE);
  }
  infix_.push(l);
}

int calc::writeFloat(const char *str, double *res) {
  int i = 0;
  char bufer[256] = {0};
  while (((str[i] > 47 && str[i] < 58) || str[i] == '.') && str[i]) {
    i++;
  }
  if (str[i] == 'e' || str[i] == 'E') {
    i++;
    if (str[i] == '+' || str[i] == '-') i++;
    while ((str[i] > 47 && str[i] < 58) && str[i]) i++;
  }
  int j = 0;
  for (; j < i; j++) {
    bufer[j] = str[j];
  }
  bufer[j + 1] = '\0';
  *res = atof(bufer);
  return i - 1;
}

void calc::operand_out(std::stack<type_t> *operand, type_t p) {
  while (!(*operand).empty() && (*operand).top() > p) {
    lexem l(0, (*operand).top());
    (*operand).pop();
    polish_.push(l);
  }
}

void calc::rpn() {
  std::stack<type_t> operand;
  while (!infix_.empty()) {
    type_t type = infix_.front().GetType();
    if (type == NUM) {
      lexem l(infix_.front().GetValue(), NUM);
      infix_.pop();
      polish_.push(l);
    } else if (type == SUM || type == SUB) {
      operand_out(&operand, BRACKET_CLOSE);
      operand.push(infix_.front().GetType());
      infix_.pop();
    } else if (type == MULT || type == DIV || type == MOD) {
      operand_out(&operand, SUB);
      operand.push(infix_.front().GetType());
      infix_.pop();
    } else if (type > UNAR_MINUS) {
      operand_out(&operand, UNAR_MINUS);
      operand.push(infix_.front().GetType());
      infix_.pop();
    } else if (type == BRACKET_OPEN) {
      operand.push(infix_.front().GetType());
      infix_.pop();
    } else if (type == BRACKET_CLOSE) {
      operand_out(&operand, BRACKET_OPEN);
      infix_.pop();
      operand.pop();
    } else if (type == UNAR_MINUS || type == UNAR_PLUS) {
      operand_out(&operand, UNAR_MINUS);
      operand.push(infix_.front().GetType());
      infix_.pop();
    }
  }
  operand_out(&operand, NUM);
}

void calc::calculate() {
  while (!polish_.empty()) {
    if (polish_.front().GetType() == NUM) {
      lexem l(polish_.front().GetValue(), NUM);
      stack_.push(l);
      polish_.pop();
    } else if (polish_.front().GetType() == SUM) {
      sum();
    } else if (polish_.front().GetType() == SUB) {
      sub();
    } else if (polish_.front().GetType() == MULT) {
      mult();
    } else if (polish_.front().GetType() == DIV) {
      division();
    } else if (polish_.front().GetType() == ELEVATION) {
      elevation();
    } else if (polish_.front().GetType() == MOD) {
      mod();
    } else if (polish_.front().GetType() == UNAR_PLUS) {
      polish_.pop();
    } else if (polish_.front().GetType() == UNAR_MINUS) {
      unar_minus();
    } else if (polish_.front().GetType() == COS) {
      cosinus();
    } else if (polish_.front().GetType() == SIN) {
      sinus();
    } else if (polish_.front().GetType() == TAN) {
      tangens();
    } else if (polish_.front().GetType() == ACOS) {
      arccosinus();
    } else if (polish_.front().GetType() == ASIN) {
      arcsinus();
    } else if (polish_.front().GetType() == ATAN) {
      arctangens();
    } else if (polish_.front().GetType() == SQRT) {
      sqrt_func();
    } else if (polish_.front().GetType() == LN) {
      ln_func();
    } else if (polish_.front().GetType() == LOG)
      log_func();
  }
  result_ = stack_.top().GetValue();
  stack_.pop();
}

void calc::sum() {
  polish_.pop();
  double a = stack_.top().GetValue();
  stack_.pop();
  double b = stack_.top().GetValue();
  stack_.pop();
  lexem l(a + b, NUM);
  stack_.push(l);
}

void calc::sub() {
  polish_.pop();
  double a = stack_.top().GetValue();
  stack_.pop();
  double b = stack_.top().GetValue();
  stack_.pop();
  lexem l(b - a, NUM);
  stack_.push(l);
}

void calc::mult() {
  polish_.pop();
  double a = stack_.top().GetValue();
  stack_.pop();
  double b = stack_.top().GetValue();
  stack_.pop();
  lexem l(b * a, NUM);
  stack_.push(l);
}

void calc::division() {
  polish_.pop();
  double a = stack_.top().GetValue();
  stack_.pop();
  double b = stack_.top().GetValue();
  stack_.pop();
  if (a == 0) {
    throw std::out_of_range("на ноль делить нельзя");
  }
  lexem l(b / a, NUM);
  stack_.push(l);
}

void calc::elevation() {
  polish_.pop();
  double a = stack_.top().GetValue();
  stack_.pop();
  double b = stack_.top().GetValue();
  stack_.pop();
  lexem l(pow(b, a), NUM);
  stack_.push(l);
}

void calc::mod() {
  polish_.pop();
  double a = stack_.top().GetValue();
  stack_.pop();
  double b = stack_.top().GetValue();
  stack_.pop();
  if (a == 0) {
    throw std::out_of_range("на ноль делить нельзя");
  }
  lexem l(fmod(b, a), NUM);
  stack_.push(l);
}

void calc::unar_minus() {
  polish_.pop();
  double a = stack_.top().GetValue();
  stack_.pop();
  lexem l(0 - a, NUM);
  stack_.push(l);
}

void calc::cosinus() {
  polish_.pop();
  double a = stack_.top().GetValue();
  stack_.pop();
  lexem l(cos(a), NUM);
  stack_.push(l);
}

void calc::sinus() {
  polish_.pop();
  double a = stack_.top().GetValue();
  stack_.pop();
  lexem l(sin(a), NUM);
  stack_.push(l);
}

void calc::tangens() {
  polish_.pop();
  double a = stack_.top().GetValue();
  stack_.pop();
  lexem l(tan(a), NUM);
  stack_.push(l);
}

void calc::arccosinus() {
  polish_.pop();
  double a = stack_.top().GetValue();
  stack_.pop();
  lexem l(acos(a), NUM);
  stack_.push(l);
}

void calc::arcsinus() {
  polish_.pop();
  double a = stack_.top().GetValue();
  stack_.pop();
  lexem l(asin(a), NUM);
  stack_.push(l);
}

void calc::arctangens() {
  polish_.pop();
  double a = stack_.top().GetValue();
  stack_.pop();
  lexem l(atan(a), NUM);
  stack_.push(l);
}

void calc::sqrt_func() {
  polish_.pop();
  double a = stack_.top().GetValue();
  stack_.pop();
  lexem l(sqrt(a), NUM);
  stack_.push(l);
}

void calc::ln_func() {
  polish_.pop();
  double a = stack_.top().GetValue();
  stack_.pop();
  lexem l(log(a), NUM);
  stack_.push(l);
}

void calc::log_func() {
  polish_.pop();
  double a = stack_.top().GetValue();
  stack_.pop();
  lexem l(log10(a), NUM);
  stack_.push(l);
}

}  // namespace s21