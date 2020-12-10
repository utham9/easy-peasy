package com.ep.screens;

public class ScreenNavigator {

    private static Screen current = Screen.EMPTY;

    public static Screen getCurrent() {
        return current;
    }

    public static void setCurrent(Screen current) {
        ScreenNavigator.current = current;
    }

    public void clear() {
        current = Screen.EMPTY;
    }
}
