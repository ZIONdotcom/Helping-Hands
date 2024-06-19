package com.example.unityserve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class orgOpportunityAdapter extends RecyclerView.Adapter<orgOpportunityAdapter.ViewHolder> {

    Context context;
    ArrayList<orgOpportunityModel> arraylist;
    OnItemClickListener onItemClickListener;

    public orgOpportunityAdapter(Context context, ArrayList<orgOpportunityModel> arraylist){
        this.context = context;
        this.arraylist = arraylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.uiorgopportunity_row, parent, false);
        return new orgOpportunityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.orgname.setText(arraylist.get(position).getOrgname());
        holder.opportunityTitle.setText(arraylist.get(position).getOpportunityTitle());
        holder.opportunityLocation.setText(arraylist.get(position).getOpportunityLocation());
        holder.eventDescription.setText(arraylist.get(position).getEventDescription());
        holder.contactInformation.setText(arraylist.get(position).getContactInformation());
        holder.category.setText(arraylist.get(position).getCategory());
        holder.datePosted.setText(arraylist.get(position).getDateTimeDisplay());
        holder.registrationDue.setText( arraylist.get(position).getDuedatetxt());
        holder.dateOpportunity.setText( arraylist.get(position).getDatetext());

/* Glide.with(context)
                .load(arraylist.get(position).getImageUrl())  // Make sure this URL is correct
                .into(holder.orgImage);
*/


        Glide.with(context)
                .load(arraylist.get(position).getImageUrl())  // Make sure this URL is correct
                .into(holder.opportunityImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(arraylist.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView orgname, opportunityTitle, opportunityLocation, eventDescription, contactInformation, category, datePosted, registrationDue, dateOpportunity;

        ImageView orgImage, opportunityImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orgname = itemView.findViewById(R.id.orgname);
            opportunityTitle = itemView.findViewById(R.id.opportunityTitle);
            opportunityLocation = itemView.findViewById(R.id.opportunityLocation);
            eventDescription = itemView.findViewById(R.id.eventDescription);
            contactInformation = itemView.findViewById(R.id.contactInformation);
            category = itemView.findViewById(R.id.category);
            datePosted = itemView.findViewById(R.id.datePosted);
            registrationDue = itemView.findViewById(R.id.registrationDue);
            dateOpportunity = itemView.findViewById(R.id.dateOpportunity);
            orgImage = itemView.findViewById(R.id.orgImage);
            opportunityImage = itemView.findViewById(R.id.opportunityImage);

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(orgOpportunityModel model);
    }
}
