package com.example.cyvid.main_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cyvid.AddNode;
import com.example.cyvid.JsonTask;
import com.example.cyvid.model.Node;
import com.example.cyvid.Adapters.NodesAdapter;
import com.example.cyvid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class NodesFragment extends Fragment {

    public static final String TAG = "NodesFragment";
    private final int REQUEST_CODE = 20;
    private RecyclerView rvNodes;
    private NodesAdapter adapter;
    public List<Node> allNodes;
    private RequestQueue requestQueue;

    public NodesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_node, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_node) {
            Intent intent = new Intent(getActivity(), AddNode.class);
            startActivityForResult(intent, REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nodes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvNodes = view.findViewById(R.id.rvNodes);
        allNodes = new ArrayList<>();

        adapter = new NodesAdapter(getContext(), allNodes);

        rvNodes.setAdapter(adapter);
        rvNodes.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.notifyDataSetChanged();

        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        Log.i("NodesFragment", String.valueOf(requestQueue));

        parseJson();
    }

    private void parseJson() {
        String url = "http://70.120.225.91:5000/CyVID_functions/query/cyvid_nodes/{\"all\":\"docs\"}";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("doc(s)");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject doc = jsonArray.getJSONObject(i);

                                String hostName = doc.getString("HostName");
                                String hostIP = doc.getString("HostIP");
                                String hostGateway = doc.getString("HostGateway");
                                String hostOS = doc.getString("HostOS");
                                String id = doc.getString("_id");
                                String rev = doc.getString("_rev");
                                JSONArray apps = doc.getJSONArray("applications");
                                List<String> applications = extractApps(apps);

                                Node node = new Node();

                                node.setHostName(hostName);
                                node.setHostIP(hostIP);
                                node.setHostGateway(hostGateway);
                                node.setHostOS(hostOS);
                                node.setId(id);
                                node.setRev(rev);
                                node.setApps(applications);

                                allNodes.add(node);
                                adapter.notifyDataSetChanged();
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

    private List<String> extractApps(JSONArray apps) throws JSONException {

        List<String> applications = new ArrayList<>();

        for (int i = 0; i < apps.length(); i++) {
            applications.add(apps.get(i).toString());
        }

        return applications;
    }

}