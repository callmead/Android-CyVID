package com.example.cyvid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cyvid.main_fragments.NodesFragment;
import com.example.cyvid.model.Application;
import com.example.cyvid.model.Node;

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

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add Application");

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

                SharedPreferences bb = getSharedPreferences("sharedPrefs", 0);

                String id = bb.getString("id", "");
                String rev = bb.getString("rev", "");

                final String doc = "{\"_id\":\"" + id + "\", \"_rev\": \"" + rev + "\", \"applications\": \"" + appName.getText().toString() + " " +
                        appVersion.getText().toString() + "\"}";
                Log.i("AddApplications", doc);

                new JsonTask().execute("http://70.120.225.91:5000/CyVID_functions/addapps/cyvid_nodes/" + doc);

                finish();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}