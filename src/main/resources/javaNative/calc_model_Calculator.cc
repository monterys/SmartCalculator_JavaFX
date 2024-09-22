#include "calc_model_Calculator.h"
#include "calcController.h"

#include <locale>
#include <string>

JNIEXPORT jdouble JNICALL Java_calc_model_Calculator_calculate
  (JNIEnv * env, jobject, jstring expression, jdouble x) {
    const char* chars = env->GetStringUTFChars(expression, nullptr);
    s21::calcControl calc(strdup(chars), x);
    env->ReleaseStringUTFChars(expression, chars);
    jdouble result;
    try {
      calc.verification();
      result = calc.calculate();
    } catch (std::out_of_range) {
      jclass exceptionClass = env->FindClass("java/lang/RuntimeException");
        if (exceptionClass != nullptr) {
            jstring message = env->NewStringUTF("на ноль делить нельзя");
            jthrowable exception = (jthrowable) env->NewObject(exceptionClass, env->GetMethodID(exceptionClass, "<init>", "(Ljava/lang/String;)V"), message);
            env->Throw(exception);
        }
    }
    return result;
  }

JNIEXPORT jboolean JNICALL Java_calc_model_Calculator_verification
  (JNIEnv * env, jobject, jstring expression) {
    std::locale::global(std::locale("en_US.UTF-8"));
    const char* chars = env->GetStringUTFChars(expression, nullptr);
    s21::calcControl calc(strdup(chars), 0.0);
    env->ReleaseStringUTFChars(expression, chars);
    int res = calc.verification();
    if (res == 0) {
      return true;
    } else {
      return false;
    }
  }
