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

import com.example.cyvid.AddNode;
import com.example.cyvid.EditActivity;
import com.example.cyvid.NodeDetail;
import com.example.cyvid.R;

import java.util.Objects;

public class NodeDetailFragment extends Fragment {

    TextView tvHostName, tvHostIP, tvHostGateway, tvHostOS;

    public NodeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_node_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvHostName = view.findViewById(R.id.tv_hostName);
        tvHostIP = view.findViewById(R.id.tv_hostIP);
        tvHostGateway = view.findViewById(R.id.tv_hostGateway);
        tvHostOS = view.findViewById(R.id.tv_hostOS);

        // get data from intent
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();

        String hostName = intent.getStringExtra("hostName");
        String hostIP = intent.getStringExtra("hostIP");
        String hostGateway = intent.getStringExtra("hostGateway");
        String hostOS = intent.getStringExtra("hostOS");

        tvHostName.setText(hostName);
        tvHostIP.setText(hostIP);
        tvHostGateway.setText(hostGateway);
        tvHostOS.setText(hostOS);
    }

}