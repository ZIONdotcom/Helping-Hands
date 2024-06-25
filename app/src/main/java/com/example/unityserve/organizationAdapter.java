package com.example.unityserve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class organizationAdapter extends RecyclerView.Adapter<organizationAdapter.ViewHolder> {
    Context context;
    ArrayList<organizationmodel> arraylist;
    ViewHolder.OnItemClickListener onItemClickListener;

    public organizationAdapter(Context context, ArrayList<organizationmodel> arraylist){
        this.context = context;
        this.arraylist = arraylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.organizationrow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.OrgName.setText(arraylist.get(position).getOrgName());
        holder.verificationStatus.setText(arraylist.get(position).getVerificationStatus());
        holder.mission.setText(arraylist.get(position).getMission());

        Glide.with(context)
                .load(arraylist.get(position).getProfilepic())
                .into(holder.profilepic);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(arraylist.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public void setOnItemClickListener(ViewHolder.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView OrgName, mission, verificationStatus;

        ImageView profilepic;

        Button view;
        private OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            OrgName = itemView.findViewById(R.id.nametxt);
            verificationStatus = itemView.findViewById(R.id.verifytxt);
            mission = itemView.findViewById(R.id.missiontxt);
            profilepic = itemView.findViewById(R.id.profilepic);
            view = itemView.findViewById(R.id.viewbtn);

        }
        

        public interface OnItemClickListener {
            void onClick(organizationmodel model);
        }
    }
}
