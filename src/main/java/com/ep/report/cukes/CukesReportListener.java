package com.ep.report.cukes;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Asterisk;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.ep.config.FileOperations;
import com.ep.config.PropertyManager;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import org.testng.TestException;

import java.util.HashMap;
import java.util.Map;

public class CukesReportListener implements ConcurrentEventListener {
  static final ThreadLocal<ExtentTest> scenario = new InheritableThreadLocal();
  static ThreadLocal<ExtentTest> step = new InheritableThreadLocal();
  Map<String, ExtentTest> feature = new HashMap<String, ExtentTest>();
  private ExtentSparkReporter spark;
  private ExtentReports extent;

  public CukesReportListener() {}

  @Override
  public void setEventPublisher(EventPublisher publisher) {
    publisher.registerHandlerFor(TestRunStarted.class, this::runStarted);
    publisher.registerHandlerFor(TestRunFinished.class, this::runFinished);
    publisher.registerHandlerFor(TestSourceRead.class, this::featureRead);
    publisher.registerHandlerFor(TestCaseStarted.class, this::ScenarioStarted);
    publisher.registerHandlerFor(TestStepStarted.class, this::stepStarted);
    publisher.registerHandlerFor(TestStepFinished.class, this::stepFinished);
  }

  private void runStarted(TestRunStarted event) {
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

  private void runFinished(TestRunFinished event) {
    extent.flush();
  }

  private void featureRead(TestSourceRead event) {
    String featureSource = event.getUri().toString();
    String featureName = featureSource.split(".*/")[1];
    if (feature.get(featureSource) == null) {
      feature.putIfAbsent(featureSource, extent.createTest(featureName));
    }
  }

  private void ScenarioStarted(TestCaseStarted event) {
    String featureName = event.getTestCase().getUri().toString();
    scenario.set(feature.get(featureName).createNode(event.getTestCase().getName()));
    scenario
        .get()
        .info(
            MarkupHelper.createOrderedList(
                PropertyManager.getProperties(PropertyManager.PropertyKey.ENVIRONMENT)));
  }

  private void stepStarted(TestStepStarted event) {
    String stepName = " ";
    String keyword = "Triggered the hook :";
    if (event.getTestStep() instanceof PickleStepTestStep) {
      PickleStepTestStep steps = (PickleStepTestStep) event.getTestStep();
      stepName = steps.getStep().getText();
      keyword = steps.getStep().getKeyword();
    } else {
      HookTestStep hoo = (HookTestStep) event.getTestStep();
      stepName = hoo.getHookType().name();
    }
    step.set(scenario.get().createNode(Asterisk.class, keyword + " " + stepName));
  }

  private void stepFinished(TestStepFinished event) {
    if (event.getResult().getStatus().is(io.cucumber.plugin.event.Status.FAILED))
      step.get().log(Status.FAIL, event.getResult().getError());
  }
}
