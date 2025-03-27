package com.example.web.session;

import java.util.HashMap;
import java.util.Map;


public class SessionContext {
    private static SessionContext instance;
    private final Map<String, Object> sessionData = new HashMap<>();

    private SessionContext() {
    }

    public static synchronized SessionContext getInstance() {
        if (instance == null) {
            instance = new SessionContext();
        }
        return instance;
    }

    public void setValue(String key, Object value) {
        sessionData.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(String key) {
        return (T) sessionData.get(key);
    }

    public void clearValue(String key) {
        sessionData.remove(key);
    }

    public void clearAll() {
        sessionData.clear();
    }
} 