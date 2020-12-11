package com.example.cyvid;

import org.json.JSONException;

public interface AsyncResponse {
    void processFinish(String output) throws JSONException;
}
