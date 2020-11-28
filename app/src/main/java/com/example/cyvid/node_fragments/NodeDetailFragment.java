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
import com.example.cyvid.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NodeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NodeDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView tvNodeName, tvNodeDetail;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NodeDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NodeDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NodeDetailFragment newInstance(String param1, String param2) {
        NodeDetailFragment fragment = new NodeDetailFragment();
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
        inflater.inflate(R.menu.menu_edit_node, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit_node) {
            Toast.makeText(getContext(), "Edit Node", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getActivity(), EditNode.class);
//            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
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

        tvNodeName = view.findViewById(R.id.tvNodeNameDetail);
        tvNodeDetail = view.findViewById(R.id.tvDescriptionDetail);

        // get data from intent
        Intent intent = Objects.requireNonNull(getActivity()).getIntent();

        String nodeName = intent.getStringExtra("iTitle");
        String nodeDescription = intent.getStringExtra("iDescription");

        tvNodeName.setText(nodeName);
        tvNodeDetail.setText(nodeDescription);
    }
}