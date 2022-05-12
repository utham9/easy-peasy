package com.ep.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class JVMProperties {
    private static final Set<String> KEYS = new HashSet<>();

    public JVMProperties() {
        // No instantiation
    }

    public static void includeKeys(String... keys) {
        KEYS.addAll(Arrays.asList(keys));
    }

    static Properties getValues() {
        Properties properties = new Properties();
        // Add all the system props you need to read here
        KEYS.add("TEST_TYPE");
        KEYS.add("env");
        KEYS.forEach(e -> properties.setProperty(e, System.getProperty(e, "")));
        return properties;
    }
}
