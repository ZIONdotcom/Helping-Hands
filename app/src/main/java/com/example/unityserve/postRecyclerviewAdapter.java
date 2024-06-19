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

public class postRecyclerviewAdapter extends RecyclerView.Adapter<postRecyclerviewAdapter.ViewHolder> {

    Context context;
    ArrayList<opportunityModel> opportunityModels;
    FragmentManager fragmentManager;

    public postRecyclerviewAdapter(Context context, ArrayList<opportunityModel> opportunityModels,  FragmentManager fragmentManager) {
        this.context = context;
        this.opportunityModels = opportunityModels;
        this.fragmentManager = fragmentManager;
    }
    @NonNull
    @Override
    public postRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate recyclerview
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerviewrow, parent, false);
        return new postRecyclerviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //assign values to the views
        //position of recycler view
        holder.opportunityTitle.setText(opportunityModels.get(position).getOpportunityTitle());
        holder.opportunityDateTime.setText(opportunityModels.get(position).getOpportunityDateTime());
        holder.opportunityLocation.setText(opportunityModels.get(position).getOpportunityLocation());
        holder.opportunityDescription.setText(opportunityModels.get(position).getOpportunityDescription());
        holder.contact.setText(opportunityModels.get(position).getContact());
        holder.deadline.setText(opportunityModels.get(position).getDeadline());
        holder.orgname.setText(opportunityModels.get(position).getOrgName());
        holder.orgimage.setImageResource(opportunityModels.get(position).getOrgimage());
        holder.opoortunityImage.setImageResource(opportunityModels.get(position).getPostPhoto());

        if (holder.join != null){
            holder.join.setOnClickListener(new View.OnClickListener() {
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
        //items total
        return opportunityModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //lik onCreate method
        Button join;

        ImageView orgimage;
        ImageView opoortunityImage;
        TextView opportunityTitle, opportunityDateTime, opportunityLocation, opportunityDescription, contact, deadline, orgname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orgimage = itemView.findViewById(R.id.orgimage);
            opoortunityImage = itemView.findViewById(R.id.postPhoto);
            opportunityTitle = itemView.findViewById(R.id.opportunityTitle);
            opportunityDateTime = itemView.findViewById(R.id.OpportunityDateTime);
            opportunityLocation = itemView.findViewById(R.id.OpportunityLocation);
            opportunityDescription = itemView.findViewById(R.id.OpportunityDescription);
            contact = itemView.findViewById(R.id.contact);
            deadline = itemView.findViewById(R.id.deadline);
            orgname = itemView.findViewById(R.id.orgName);
            join = itemView.findViewById(R.id.joinbutton);

        }
    }
}
