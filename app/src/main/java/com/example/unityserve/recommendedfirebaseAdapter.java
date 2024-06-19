package com.example.unityserve;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class recommendedfirebaseAdapter extends FirebaseRecyclerAdapter<recommendedModel, recommendedfirebaseAdapter.ViewHolder> {
    public recommendedfirebaseAdapter(@NonNull FirebaseRecyclerOptions<recommendedModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull recommendedfirebaseAdapter.ViewHolder holder, int position, @NonNull recommendedModel model) {
        holder.orgname.setText(model.getOrgName());
        holder.opportunityTitle.setText(model.getOpportunityTitle());

        Glide.with(holder.opoortunityImage.getContext())
                .load(model.getOpportunityImage())
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.opoortunityImage);

    }

    @NonNull
    @Override
    public recommendedfirebaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendedopportunity, parent, false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

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
