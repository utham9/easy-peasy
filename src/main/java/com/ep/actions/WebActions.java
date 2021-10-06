package com.ep.actions;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;


public class WebActions {

    private WebDriver webDriver;


    public WebActions(WebDriver driver) {
        this.webDriver = driver;
        FluentWait wait = new FluentWait(webDriver);
        wait.withTimeout(Duration.ofMillis(5000));
        wait.pollingEvery(Duration.ofMillis(250));
        wait.ignoring(NoSuchElementException.class);
    }



}
