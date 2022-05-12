package com.ep.report.testng;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.ep.report.cukes.CukesReportListener;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class TestNGReporter extends TestNgReportListener {

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

  public static void screenshot(WebDriver webdriver, String message) {
    TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
    File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
    TestNGReporter.instance()
            .log(
                    Status.INFO,
                    message,
                    TestNGReporter.instance()
                            .addScreenCaptureFromBase64String(scrShot.getScreenshotAs(OutputType.BASE64))
                            .getModel()
                            .getMedia()
                            .get(0));
  }

  public static ExtentTest instance() {
    return test.get();
  }
}
