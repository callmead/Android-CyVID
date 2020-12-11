package com.example.cyvid.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cyvid.R;
import com.example.cyvid.model.Analysis;

import java.util.List;

public class AnalysisAdapter extends RecyclerView.Adapter<AnalysisAdapter.ViewHolder> {

    public static final String TAG = "AnalysisAdapter";
    Context context;
    List<Analysis> analysisList;

    public AnalysisAdapter(Context c, List<Analysis> analysis) {
        this.context = c;
        this.analysisList = analysis;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_analysis, parent, false);
        return new AnalysisAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(analysisList.get(position).getTitle());
        Log.i(TAG, analysisList.get(position).getTitle());
        holder.tvAnalysis.setText(analysisList.get(position).getAnalysis());
        Log.i(TAG, analysisList.get(position).getAnalysis());

    }

    @Override
    public int getItemCount() {
        return analysisList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle, tvAnalysis;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvTitle = itemView.findViewById(R.id.tvAnalysisTitle);
            this.tvAnalysis = itemView.findViewById(R.id.tvAnalysis);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
