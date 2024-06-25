package com.example.unityserve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class volunteerHistoryadapter extends RecyclerView.Adapter<volunteerHistoryadapter.ViewHolder>{
    private Context context;
    private List<VolunteertHistoryModel> volunteerHistoryList;

    public volunteerHistoryadapter(Context context, List<VolunteertHistoryModel> volunteerHistoryList) {
        this.context = context;
        this.volunteerHistoryList = volunteerHistoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.voluteerhistoryrow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VolunteertHistoryModel model = volunteerHistoryList.get(position);
        holder.organization.setText(model.getOrganization());
        holder.date.setText(model.getDate());
        holder.description.setText(model.getDescription());

    }

    @Override
    public int getItemCount() {
        return volunteerHistoryList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView organization, date, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            organization = itemView.findViewById(R.id.organization);
            date = itemView.findViewById(R.id.date);
            description = itemView.findViewById(R.id.description);
        }
    }
}
