package com.example.cyvid.model;

public class Application {

    private String appName, appVersion;
    private Node node;

    public Application(Node node) {
        this.node = node;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}
