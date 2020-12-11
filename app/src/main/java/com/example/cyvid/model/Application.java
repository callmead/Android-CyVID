package com.example.cyvid.model;

public class Application {

    private String appName, appVersion;

    public Application(String name, String version) {
        this.appName = name;
        this.appVersion = version;
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

    @Override
    public String toString() {
        return appName + " " + appVersion;
    }
}
