package com.example.cyvid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AddNode extends AppCompatActivity {

    private static final String TAG = "AddNode";
    Button btnSave, btnCancel;
    EditText hostName, hostIP, hostGateway, hostOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_node);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Add Node");

        btnCancel = findViewById(R.id.btn_cancelNode);
        btnSave = findViewById(R.id.btn_saveNode);

        hostName = findViewById(R.id.et_hostName);
        hostIP = findViewById(R.id.et_hostIP);
        hostGateway = findViewById(R.id.et_hostGateway);
        hostOS = findViewById(R.id.et_hostOS);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Creating Background Threads
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                    }
                });

                final String doc = "{\"HostName\":\"" + hostName.getText().toString() + "\", \"HostIP\": \""+ hostIP.getText().toString() +"\", \"HostGateway\": \""+ hostGateway.getText().toString() +"\", \"HostOS\": \""+ hostOS.getText().toString() +"\"}";
                new JsonTask().execute("http://70.120.225.91:5000/CyVID_functions/add/test_db/" + doc);
                finish();
            }
        });
    }

}