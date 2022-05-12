package com.ep.report.testng;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.ep.config.FileOperations;
import com.ep.config.PropertyManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TestNgReportListener implements ITestListener, ISuiteListener {

  Map<String, ExtentTest> feature = new HashMap<String, ExtentTest>();
  private ExtentSparkReporter spark;
  public ExtentReports extent;

  static final ThreadLocal<ExtentTest> test = new InheritableThreadLocal();
  static ThreadLocal<ExtentTest> step = new InheritableThreadLocal();

  @Override
  public void onTestSuccess(ITestResult result) {
    test.get().log(Status.PASS, "Test completed successfully");
  }

  @Override
  public void onTestFailure(ITestResult result) {
    test.get().log(Status.FAIL, result.getThrowable());
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    test.get().log(Status.SKIP, result.getMethod().getMethodName());
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    test.get().log(Status.FAIL, result.getThrowable());
  }

  @Override
  public void onTestFailedWithTimeout(ITestResult result) {
    test.get().log(Status.FAIL, result.getThrowable());
  }

  @Override
  public void onTestStart(ITestResult result) {
    test.set(extent.createTest(result.getMethod().getMethodName()));
  }

  @Override
  public void onStart(ISuite suite) {
    String reportFolder = FileOperations.generateReportFolder();
    String file =
        PropertyManager.getProperty(
            PropertyManager.PropertyKey.EXTENT, "extent.reporter.spark.out");
    spark = new ExtentSparkReporter(reportFolder + "/" + file);
    extent = new ExtentReports();
    try {
      spark.loadXMLConfig(
          PropertyManager.getProperty(
              PropertyManager.PropertyKey.EXTENT, "extent.reporter.spark.config"));
    } catch (Exception e) {
      throw new TestException("Unable to load extent config");
    }
    extent.attachReporter(spark);
  }

  @Override
  public void onFinish(ISuite suite) {
    extent.flush();
  }
}
