package com.example.cyvid;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.cyvid.main_fragments.DashboardFragment;
import com.example.cyvid.main_fragments.NodesFragment;
import com.example.cyvid.node_fragments.NodeAnalysisFragment;
import com.example.cyvid.node_fragments.NodeApplicationsFragment;
import com.example.cyvid.node_fragments.NodeDetailFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NodeDetail extends AppCompatActivity {

    private BottomNavigationView navigationView;

    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_detail);

        ActionBar actionBar = getSupportActionBar();

        // get data from intent
        Intent intent = getIntent();

        String nodeName = intent.getStringExtra("iTitle");

        assert actionBar != null;
        actionBar.setTitle(nodeName);
        actionBar.setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.node_detail_bottom_nav);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_details:
                    default:
                        fragment = new NodeDetailFragment();
                        break;
                    case R.id.action_node_analysis:
                        fragment = new NodeAnalysisFragment();
                        break;
                    case R.id.action_applications:
                        fragment = new NodeApplicationsFragment();
                        break;
                }
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.flContainer2, fragment)
                        .commit();
                return true;
            }
        });
        // Set default selection
        navigationView.setSelectedItemId(R.id.action_details);

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