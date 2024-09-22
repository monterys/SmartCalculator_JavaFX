#include "calc_model_CreditCalculator.h"
#include "creditModel.h"
#include <iostream>

JNIEXPORT void JNICALL Java_calc_model_CreditCalculator_calculate(
    JNIEnv *env, jobject obj, jboolean choice) {
  jclass clazz = env->GetObjectClass(obj);

  jfieldID fidSumCred = env->GetFieldID(clazz, "sumCred", "D");
  jfieldID fidCreditPeriod = env->GetFieldID(clazz, "creditPeriod", "I");
  jfieldID fidPercent = env->GetFieldID(clazz, "percent", "D");

  double sumCred = env->GetDoubleField(obj, fidSumCred);
  int creditPeriod = env->GetIntField(obj, fidCreditPeriod);
  double percent = env->GetDoubleField(obj, fidPercent);

  s21::credit myCredit(sumCred, percent, creditPeriod);
  if (choice) {
    myCredit.annuitantCreditCalc();
  } else {
    myCredit.differentialCreditCalc();
  }

  double overpayment = myCredit.GetOverpayment();
  double monthlyPayment = myCredit.GetMonthlyPayment();
  double totalPayment = myCredit.GetTotalPayment();

  jfieldID fidMonthlyPayment = env->GetFieldID(clazz, "monthlyPayment", "D");
  jfieldID fidOverpayment = env->GetFieldID(clazz, "creditOverpayment", "D");
  jfieldID fidTotalPayment = env->GetFieldID(clazz, "totalPayment", "D");

  env->SetDoubleField(obj, fidMonthlyPayment, monthlyPayment);
  env->SetDoubleField(obj, fidOverpayment, overpayment);
  env->SetDoubleField(obj, fidTotalPayment, totalPayment);

  jclass difClass = env->FindClass("calc/model/CreditTableEntry");

  std::queue<s21::credit::dif> tableEntries = myCredit.GetTable();

  jclass arrayListClass = env->FindClass("java/util/ArrayList");
  jmethodID arrayListConstructor =
      env->GetMethodID(arrayListClass, "<init>", "()V");
  jobject listObject = env->NewObject(arrayListClass, arrayListConstructor);
  jmethodID addMethod =
      env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");
  for (; !tableEntries.empty(); tableEntries.pop()) {
    s21::credit::dif &currentDif = tableEntries.front();
    jmethodID constructor = env->GetMethodID(difClass, "<init>", "(IDDD)V");

    jobject difObject =
        env->NewObject(difClass, constructor, currentDif.GetMonth(),
                       currentDif.GetMonthlyPayment(),
                       currentDif.GetTempAmount(), currentDif.GetOverpayment());

    env->CallBooleanMethod(listObject, addMethod, difObject);
  }

  jmethodID setTableEntriesMethod =
      env->GetMethodID(clazz, "setTableEntries", "(Ljava/util/List;)V");

  env->CallVoidMethod(obj, setTableEntriesMethod, listObject);
}
