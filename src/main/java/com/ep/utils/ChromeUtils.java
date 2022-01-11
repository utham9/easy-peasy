package com.ep.utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;

public class ChromeUtils {

  public static ArrayList getDownloadedFiles(RemoteWebDriver remoteDriver) {
    ArrayList filesFound = null;
    try {
      if (!remoteDriver.getCurrentUrl().startsWith("chrome://downloads")) {
        remoteDriver.get("chrome://downloads/");
      }
      filesFound =
          (ArrayList)
              remoteDriver.executeScript(
                  "return  document.querySelector('downloads-manager')  "
                      + " .shadowRoot.querySelector('#downloadsList')         "
                      + " .items.filter(e => e.state === 'COMPLETE')          "
                      + " .map(e => e.filePath || e.file_path || e.fileUrl || e.file_url); ",
                  "");
    } catch (Exception e) {
      System.err.println(e);
    }
    return filesFound;
  }

  public static String getFileContent(RemoteWebDriver remoteDriver, String path) {
    String file_content = null;
    try {
      if (!remoteDriver.getCurrentUrl().startsWith("chrome://downloads")) {
        remoteDriver.get("chrome://downloads/");
      }

      WebElement elem =
          (WebElement)
              remoteDriver.executeScript(
                  "var input = window.document.createElement('INPUT'); "
                      + "input.setAttribute('type', 'file'); "
                      + "input.hidden = true; "
                      + "input.onchange = function (e) { e.stopPropagation() }; "
                      + "return window.document.documentElement.appendChild(input); ",
                  "");

      elem.sendKeys(path);

      file_content =
          (String)
              remoteDriver.executeAsyncScript(
                  "var input = arguments[0], callback = arguments[1]; "
                      + "var reader = new FileReader(); "
                      + "reader.onload = function (ev) { callback(reader.result) }; "
                      + "reader.onerror = function (ex) { callback(ex.message) }; "
                      + "reader.readAsDataURL(input.files[0]); "
                      + "input.remove(); ",
                  elem);

      if (!file_content.startsWith("data:")) {
        System.out.println("Failed to get file content");
      }

    } catch (Exception e) {
      System.err.println(e);
    }
    return file_content;
  }
}
