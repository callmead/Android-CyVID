package com.example.cyvid.main_fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cyvid.Adapters.AnalysisAdapter;
import com.example.cyvid.AddNode;
import com.example.cyvid.AsyncResponse;
import com.example.cyvid.JsonTask;
import com.example.cyvid.R;
import com.example.cyvid.model.Analysis;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DashboardFragment extends Fragment implements AsyncResponse {

    JsonTask jsonTask = new JsonTask();
    public static final String TAG = "NodesAnalysis";
    private RecyclerView rvDashboard;
    private AnalysisAdapter adapter;
    public List<Analysis> allDashboardAnalyses;
    ProgressDialog dialog;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialog = ProgressDialog.show(getContext(), "Loading analysis", "Please wait...", true);

        rvDashboard = view.findViewById(R.id.rvDashboard);

        allDashboardAnalyses = new ArrayList<>();

        adapter = new AnalysisAdapter(getContext(), allDashboardAnalyses);

        rvDashboard.setAdapter(adapter);
        rvDashboard.setLayoutManager(new LinearLayoutManager(getContext()));

        jsonTask.delegate = this;
        execute();

    }

    public void execute() {
        jsonTask.execute("http://70.120.225.91:5000/CyVID_analysis/{\"all\":\"nodes\"}");
    }

    @Override
    public void processFinish(String output) {
        output = output.substring(7, output.length()-2).replace("\\},", "").replace("\\}", "");

        extractAnalyses(output);
    }

    private void extractAnalyses(String analysis) {
        String[] outputs = analysis.split("\\{");

        String[] names = {"Severity of Vulnerabilities", "Top 10 CVEs (Vulnerabilities)", "Top 10 CWEs (Weaknesses)"};
        int i = 0;
        for (String s : outputs) {
            String newStr = s.substring(0, s.length()-7);

            Analysis nodeAnalysis = new Analysis();

            nodeAnalysis.setAnalysis(newStr);
            nodeAnalysis.setTitle(names[i]);
            i++;

            allDashboardAnalyses.add(nodeAnalysis);
            adapter.notifyDataSetChanged();
        }

        dialog.dismiss();

    }

}