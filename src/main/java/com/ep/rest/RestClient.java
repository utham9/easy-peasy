package com.ep.rest;

import com.ep.config.PropertyManager;
import com.ep.models.Exclude;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.ProxySpecification;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.tuple.Pair;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RestClient {

  private static RestClient restClient = null;
  private final ExclusionStrategy EXCLUSION_STRATEGY =
      new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
          return fieldAttributes.getAnnotation(Exclude.class) != null;
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
          return false;
        }
      };
  private final Gson GSON =
      new GsonBuilder()
          .addSerializationExclusionStrategy(EXCLUSION_STRATEGY)
          .excludeFieldsWithModifiers(Modifier.STATIC)
          .setPrettyPrinting()
          .create();
  public String PROXY_HOST =
      PropertyManager.getProperty(PropertyManager.PropertyKey.GLOBAL, "proxy.host");
  public Integer PROXY_PORT =
      Integer.parseInt(
          PropertyManager.getProperty(PropertyManager.PropertyKey.GLOBAL, "proxy.port"));
  public String PROXY_USER =
      PropertyManager.getProperty(PropertyManager.PropertyKey.GLOBAL, "proxy.user");
  public String PROXY_PASSWORD =
      PropertyManager.getProperty(PropertyManager.PropertyKey.GLOBAL, "proxy.password");
  private RequestSpecification requestSpecification;

  public RestClient() {
    clearFilters();
  }

  public static RestClient getInstance() {
    if (restClient == null) {
      restClient = new RestClient();
    }
    return restClient;
  }

  public static RestClient reset() {
    getInstance().requestSpecification = null;
    restClient = null;
    return getInstance();
  }

  public static void clearFilters() {
    List<Filter> filters = RestAssured.filters();
    if (filters.size() >= 1) {
      RestAssured.filters().clear();
    }
  }

  public ProxySpecification getProxySpecification() {
    return ProxySpecification.auth(PROXY_USER, PROXY_PASSWORD)
        .withHost(PROXY_HOST)
        .withPort(PROXY_PORT);
  }

  public RequestSpecification given() {
    LogConfig logConfig =
        LogConfig.logConfig()
            .enablePrettyPrinting(true)
            .enableLoggingOfRequestAndResponseIfValidationFails();
    RestAssuredConfig restAssuredConfig = RestAssuredConfig.config().logConfig(logConfig);
    RequestSpecification given =
        requestSpecification
            .config(restAssuredConfig)
            .relaxedHTTPSValidation()
            .log()
            .ifValidationFails();
    return given;
  }

  public List<Filter> logFilter(Pair<ByteArrayOutputStream, PrintStream> pair) {
    PrintStream ps = pair.getRight();
    return Stream.of(
            new RequestLoggingFilter(LogDetail.ALL, ps),
            new ResponseLoggingFilter(LogDetail.ALL, ps))
        .collect(Collectors.toList());
  }

  public Gson gson() {
    return GSON;
  }
}
