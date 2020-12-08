package com.example.cyvid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddApplication extends AppCompatActivity {

    Button btnCancel;
    Button btnSave;
    EditText appName, appVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_application);

        appName = findViewById(R.id.et_application_name);
        appName = findViewById(R.id.et_appVersion);

        btnCancel = findViewById(R.id.btnCancelApp);
        btnSave = findViewById(R.id.btn_saveApp);

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

                final String doc = "{\"AppName\":\"" + appName.getText().toString() + "\", \"AppVersion\": \""+ appVersion.getText().toString() +"\"}";
//                new JsonTask().execute("http://70.120.225.91:5000/CyVID_functions/add/test_db/" + doc);
//                finish();

            }
        });
    }
}