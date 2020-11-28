package com.example.cyvid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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

        holder.tvNodeName.setText(nodeList.get(position).getName());
        holder.tvDescription.setText(nodeList.get(position).getDescription());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {


                String gNodeName = nodeList.get(position).getName();
                String gDescription = nodeList.get(position).getDescription();

                Intent intent = new Intent(context, NodeDetail.class);
                intent.putExtra("iTitle", gNodeName);
                intent.putExtra("iDescription", gDescription);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nodeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvNodeName;
        TextView tvDescription;
        ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvNodeName = itemView.findViewById(R.id.tvNodeName);
            this.tvDescription = itemView.findViewById(R.id.tvDescription);

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
