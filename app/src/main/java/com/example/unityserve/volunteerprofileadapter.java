package com.example.unityserve;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class volunteerprofileadapter {

    public static class ViewHolder{

        TextView location, interest, name;

        ImageView profilepic;

        Button view;


        public ViewHolder(@NonNull View itemView) {
            super();
            location = itemView.findViewById(R.id.locationtxt);
            interest = itemView.findViewById(R.id.interesttxt);
            profilepic = itemView.findViewById(R.id.profilepic);
            name = itemView.findViewById(R.id.nametxt);
            view = itemView.findViewById(R.id.viewbtn);

        }
    }
}
