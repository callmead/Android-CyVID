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
import android.widget.Toast;

import com.example.cyvid.AddNode;
import com.example.cyvid.Node;
import com.example.cyvid.NodesAdapter;
import com.example.cyvid.R;

import java.util.ArrayList;
import java.util.List;


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
    private NodesAdapter adapter;

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

        adapter = new NodesAdapter(getContext(), getList());

        rvNodes.setAdapter(adapter);
        rvNodes.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public List<Node> getList() {

        List<Node> nodes = new ArrayList<>();

        Node node = new Node();
        node.setName("First Node");
        node.setDescription("My first node");
        nodes.add(node);

        node = new Node();
        node.setName("Second Node");
        node.setDescription("My second node");
        nodes.add(node);

        node = new Node();
        node.setName("Third Node");
        node.setDescription("My third node");
        nodes.add(node);

        node = new Node();
        node.setName("Fourth Node");
        node.setDescription("My last node");
        nodes.add(node);

        return nodes;
    }



}