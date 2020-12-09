package com.example.cyvid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AddApplication extends AppCompatActivity {

    Button btnCancel;
    Button btnSave;
    EditText appName, appVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_application);

        appName = findViewById(R.id.et_application_name);
        appVersion = findViewById(R.id.et_appVersion);

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

                final String doc = "{\"applications\":\"" + appName.getText().toString() + " " + appVersion.getText().toString() +"\"}";

                SharedPreferences bb = getSharedPreferences("sharedPrefs", 0);

                String id = bb.getString("id", "");

                DataBase db = new DataBase();

                String test = "http://70.120.225.91:5000/CyVID_functions/add/" + db.db + "/" + id + "/" + doc;
                Log.i("AddApplications", test);
                new JsonTask().execute("http://70.120.225.91:5000/CyVID_functions/add/" + db.db + "/" + id + "/" + doc);

                Log.i("AddApplications", id);
                finish();
            }
        });
    }
}