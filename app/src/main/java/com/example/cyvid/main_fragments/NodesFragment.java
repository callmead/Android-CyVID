package com.example.cyvid.main_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cyvid.AddNode;
import com.example.cyvid.DataBase;
import com.example.cyvid.model.Node;
import com.example.cyvid.NodesAdapter;
import com.example.cyvid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class NodesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String TAG = "NodesFragment";
    private RecyclerView rvNodes;
    private static NodesAdapter adapter;
    protected List<Node> allNodes;
    private RequestQueue requestQueue;


    public NodesFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NodesFragment newInstance(String param1, String param2) {
        NodesFragment fragment = new NodesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        parseJson();
    }

    public static void updateList() {
        adapter.notifyDataSetChanged();
    }

    private void parseJson() {
        DataBase db = new DataBase();

        String url = "http://70.120.225.91:5000/CyVID_functions/query/test_db2" + "/{\"all\":\"docs\"}";
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

                                Node node = new Node();
                                node.setHostName(hostName);
                                node.setHostIP(hostIP);
                                node.setHostGateway(hostGateway);
                                node.setHostOS(hostOS);
                                node.setId(id);
                                node.setRev(rev);

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

}