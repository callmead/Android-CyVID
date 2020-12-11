package com.example.cyvid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cyvid.ItemClickListener;
import com.example.cyvid.NodeDetail;
import com.example.cyvid.R;
import com.example.cyvid.model.Node;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class NodesAdapter extends RecyclerView.Adapter<NodesAdapter.ViewHolder>{

    Context context;
    List<Node> nodeList;

    public NodesAdapter(Context c, List<Node> nodeList) {
        this.context = c;
        this.nodeList = nodeList;
    }

    @NonNull
    @Override
    public NodesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_node, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tvHostName.setText(nodeList.get(position).getHostName());
        holder.tvHostIP.setText(nodeList.get(position).getHostIP());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {

                String hostName = nodeList.get(position).getHostName();
                String hostIP = nodeList.get(position).getHostIP();
                String hostGateway = nodeList.get(position).getHostGateway();
                String hostOS = nodeList.get(position).getHostOS();
                List<String> apps = nodeList.get(position).getApps();

                String strApps = "";
                for (String s : apps) {
                    strApps += s + "\n";
                }

                Intent intent = new Intent(context, NodeDetail.class);

                intent.putExtra("hostName", hostName);
                intent.putExtra("hostIP", hostIP);
                intent.putExtra("hostGateway", hostGateway);
                intent.putExtra("hostOS", hostOS);
                intent.putExtra("apps", strApps);

                String id = nodeList.get(position).getId();
                String rev = nodeList.get(position).getRev();

                saveData(id, rev, hostName, hostIP, hostGateway, hostOS);

                context.startActivity(intent);
            }

        });

    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    private void saveData(String id, String rev, String hostName, String hostIP, String hostGateway, String hostOS) {
        SharedPreferences prefs = context.getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();

        edit.putString("id", id);
        edit.putString("rev", rev);
        edit.putString("hostName", hostName);
        edit.putString("hostIP", hostIP);
        edit.putString("hostGateway", hostGateway);
        edit.putString("hostOS", hostOS);

        edit.apply();
    }

    @Override
    public int getItemCount() {
        return nodeList.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        nodeList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Node> list) {
        nodeList.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvHostName, tvHostIP;
        ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvHostName = itemView.findViewById(R.id.tvNodeName);
            this.tvHostIP = itemView.findViewById(R.id.tvDescription);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClickListener(v, getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }
    }
}
