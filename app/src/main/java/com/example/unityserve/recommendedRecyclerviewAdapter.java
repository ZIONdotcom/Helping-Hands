package com.example.unityserve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recommendedRecyclerviewAdapter extends RecyclerView.Adapter<recommendedRecyclerviewAdapter.ViewHolder> {

    Context context;
    ArrayList<recommendedModel> recommendedModel;
    FragmentManager fragmentManager;

    public recommendedRecyclerviewAdapter(Context context, ArrayList<recommendedModel> recommendedModel, FragmentManager fragmentManager) {
        this.context = context;
        this.recommendedModel = recommendedModel;
        this.fragmentManager = fragmentManager;
    }
    @NonNull
    @Override
    public recommendedRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recommendedopportunity, parent, false);
        return new recommendedRecyclerviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recommendedRecyclerviewAdapter.ViewHolder holder, int position) {
        holder.opportunityTitle.setText(recommendedModel.get(position).getOpportunityTitle());
        holder.orgname.setText(recommendedModel.get(position).getOrgName());
        holder.orgimage.setImageResource(recommendedModel.get(position).getOrgImage());
        holder.opoortunityImage.setImageResource(recommendedModel.get(position).getOpportunityImage());


        if (holder.joinButton != null){
            holder.joinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    joinDialogFragment dialog = new joinDialogFragment();
                    dialog.show(fragmentManager, "joinDialogFragment");
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return recommendedModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public Button joinButton;
        ImageView orgimage;
        ImageView opoortunityImage;

        TextView opportunityTitle, orgname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orgimage = itemView.findViewById(R.id.orgimage);
            opoortunityImage = itemView.findViewById(R.id.postPhoto);
            opportunityTitle = itemView.findViewById(R.id.opportunityTitle);
            orgname = itemView.findViewById(R.id.orgName);
            joinButton = itemView.findViewById(R.id.joinbuttonr);
        }
    }
}
