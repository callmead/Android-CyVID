package com.example.cyvid.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Node {

    private String hostName, hostIP, hostGateway, hostOS, id, rev;
    private List<Application> apps;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostGateway) {
        this.hostName = hostGateway;
    }

    public String getHostIP() {
        return hostIP;
    }

    public void setHostIP(String hostGateway) {
        this.hostIP = hostGateway;
    }

    public String getHostGateway() {
        return hostGateway;
    }

    public void setHostGateway(String hostGateway) {
        this.hostGateway = hostGateway;
    }

    public String getHostOS() {
        return hostOS;
    }

    public void setHostOS(String hostOS) {
        this.hostOS = hostOS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public List<Application> getApps() {
        return apps;
    }

    public void addApp(Application app) {
        apps.add(app);
    }
}
