package com.example.cyvid.node_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyvid.AddApplication;
import com.example.cyvid.AddNode;
import com.example.cyvid.R;

import java.util.Objects;

public class NodeApplicationsFragment extends Fragment {

    TextView tvApps;

    public NodeApplicationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_node_applications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvApps = view.findViewById(R.id.tv_applications);

        Intent intent = Objects.requireNonNull(getActivity()).getIntent();

        String apps = intent.getStringExtra("apps");
        tvApps.setText(apps);
    }

}