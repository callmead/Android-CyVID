package com.example.cyvid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cyvid.main_fragments.DashboardFragment;
import com.example.cyvid.main_fragments.NodesFragment;

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
        actionBar.setDisplayHomeAsUpEnabled(true);

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

                final String doc = "{\"HostName\":\"" + hostName.getText().toString() + "\", \"HostIP\": \""+ hostIP.getText().toString() +"\", \"HostGateway\": \""+ hostGateway.getText().toString() +"\", \"HostOS\": \""+ hostOS.getText().toString() +"\"}";
                new JsonTask().execute("http://70.120.225.91:5000/CyVID_functions/add/cyvid_nodes/" + doc);

//                Tweet tweet = Tweet.fromJson(json.jsonObject);
//                Log.i(TAG, "Published Tweet" + tweet.body);
//                Intent intent = new Intent();
//                intent.putExtra("tweet", Parcels.wrap(tweet));
//                setResult(RESULT_OK, intent);
//                finish();

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