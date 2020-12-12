package com.example.cyvid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cyvid.main_fragments.NodesFragment;
import com.example.cyvid.model.Node;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class LogInActivity extends AppCompatActivity implements AsyncResponse {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignup;
    private RequestQueue requestQueue;
    JsonTask jsonTask = new JsonTask();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

               if (username.isEmpty()) {
                   Toast.makeText(LogInActivity.this, "Username cannot be empty.", Toast.LENGTH_SHORT).show();
               } else if (password.isEmpty()) {
                   Toast.makeText(LogInActivity.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
               } else {
                   loginUser(username, password);
               }
            }
        });

        btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(LogInActivity.this, "Username cannot be empty.", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(LogInActivity.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                } else {
                    signUp(username, password);
                    Toast.makeText(LogInActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void signUp(String username, String password) {

        jsonTask.delegate = this;
        final String doc = "{\"user\":\"" + username + "\", \"pass\": \""+ password +"\"}";
        jsonTask.execute("http://70.120.225.91:5000/CyVID_functions/add/cyvid_users/" + doc);

    }

    private void loginUser(String username, String password) {

        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext()));

        final String doc = "{\"user\":\"" + username + "\", \"pass\":\""+ password +"\"}";
        Log.i("LogIn", doc);

        String url = "http://70.120.225.91:5000/Authenticate/" + doc;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Log.i("LogIn", message);

                            if (message.equalsIgnoreCase("invalid user")) {
                                Toast.makeText(LogInActivity.this, "Invalid username.", Toast.LENGTH_SHORT).show();
                            } else if (message.equalsIgnoreCase("invalid password")) {
                                Toast.makeText(LogInActivity.this, "Invalid password.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LogInActivity.this, "Log in Successful!", Toast.LENGTH_SHORT).show();
                                goMainActivity();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void processFinish(String output) {
        goMainActivity();
    }
}