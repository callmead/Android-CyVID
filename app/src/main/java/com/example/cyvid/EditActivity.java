package com.example.cyvid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText hostName, hostIP, hostGateway, hostOS;
    Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Edit Node");

        hostName = findViewById(R.id.et_editHostName);
        hostIP = findViewById(R.id.et_editHostIP);
        hostGateway = findViewById(R.id.et_editHostGateway);
        hostOS = findViewById(R.id.et_editHostOS);

        btnSave = findViewById(R.id.btn_saveNode);
        btnCancel = findViewById(R.id.btn_cancelNodeEdit);

        SharedPreferences bb = getSharedPreferences("sharedPrefs", 0);

        String orgHostName = bb.getString("hostName", "");
        String orgHostIP = bb.getString("hostIP", "");
        String orgHostGateway = bb.getString("hostGateway", "");
        String orgHostOS = bb.getString("hostOS", "");

        hostName.setText(orgHostName);
        hostIP.setText(orgHostIP);
        hostGateway.setText(orgHostGateway);
        hostOS.setText(orgHostOS);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               updateNode(hostName, hostIP, hostGateway, hostOS);
            }
        });

    }

    private void updateNode(EditText hostName, EditText hostIP, EditText hostGateway, EditText hostOS) {

        SharedPreferences bb = getSharedPreferences("sharedPrefs", 0);

        String id = bb.getString("id", "");
        String rev = bb.getString("rev", "");

        String doc = "{\"_id\":\"" + id + "\", \"HostName\":\"" + hostName + "\", \"HostIP\":\"" + hostIP + "\", \"HostGateway\":\""
                + hostGateway + "\", \"HostOS\":\"" + hostOS + "\", \"_rev\": \"" + rev + "\"}";

        new JsonTask().execute("http://70.120.225.91:5000/CyVID_functions/update/test_db/" + doc);

        finish();

        // http://70.120.225.91:5000/CyVID_functions/update/test_db/{"_id":"11", "data":"new value", "_rev": "3-cf6bfa49a8a8665f7e90580b7dd85cba"}
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