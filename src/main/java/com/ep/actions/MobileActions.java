package com.ep.actions;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;

public class MobileActions {

    private MobileElement mobileElement;

    public MobileActions(MobileElement mobileElement) {
        this.mobileElement = mobileElement;
    }

    public void enterText(String text) {
        mobileElement.sendKeys(text);
    }
}
