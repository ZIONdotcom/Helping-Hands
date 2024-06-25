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

public class volunteerAdapter extends RecyclerView.Adapter<volunteerAdapter.ViewHolder>{

    Context context;
    ArrayList<volunteermodel> arraylist;
    OnItemClickListener onItemClickListener;

    public volunteerAdapter(Context context, ArrayList<volunteermodel> arraylist){
        this.context = context;
        this.arraylist = arraylist;
    }
    @NonNull
    @Override
    public volunteerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.volunteerrow, parent, false);
        return new volunteerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull volunteerAdapter.ViewHolder holder, int position) {
        //  holder.orgname.setText(arraylist.get(position).getOrgname());
        holder.location.setText(arraylist.get(position).getLocation());
        holder.interest.setText(arraylist.get(position).getInterest());
        holder.name.setText(arraylist.get(position).getName());

        Glide.with(context)
                .load(arraylist.get(position).getProfilepic())
                .into(holder.profilepic);

        holder.view.setOnClickListener(new View.OnClickListener() {
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

        TextView location, interest, name;

        ImageView profilepic;

        Button view;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.locationtxt);
            interest = itemView.findViewById(R.id.interesttxt);
            profilepic = itemView.findViewById(R.id.profilepic);
            name = itemView.findViewById(R.id.nametxt);
            view = itemView.findViewById(R.id.viewbtn);

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(volunteermodel model);
    }
}
