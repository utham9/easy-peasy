package com.ep.report.cukes;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class CukesReporter extends CukesReportListener {

  public static void log(String message) {
    step.get().info(message);
  }

  public static void log(String message, String... args) {
    String format = String.format(message, args);
    step.get().info(format);
  }

  public static void codeBlock(String message) {
    step.get().info(MarkupHelper.createCodeBlock(message, CodeLanguage.JSON));
  }

  public static void codeBlock(Object message) {
    step.get().info(MarkupHelper.toTable(message));
  }

  public static void attach(String filePath, String fileName) {
    step.get().info("<a href=\"" + filePath + "\" target=\"_blank\">" + fileName + "</a>");
  }

  public static ExtentTest scenario() {
    return scenario.get();
  }
}
