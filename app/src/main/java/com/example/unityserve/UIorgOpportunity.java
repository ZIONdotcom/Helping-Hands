package com.example.unityserve;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UIorgOpportunity extends AppCompatActivity {
    FirebaseFirestore db;
    RecyclerView recyclerView;

    orgOpportunityAdapter adapter; // Added: Define adapter as a class variable
    ArrayList<orgOpportunityModel> arrayList; // Added: Define arrayList as a class variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_uiorg_opportunity);

        FirebaseApp.initializeApp(this);
         db = FirebaseFirestore.getInstance();
         recyclerView = findViewById(R.id.orgopportunityRV);

        // Setting the LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>(); // Added: Initialize arrayList
        adapter = new orgOpportunityAdapter(UIorgOpportunity.this, arrayList); // Added: Initialize adapter
        recyclerView.setAdapter(adapter); // Added: Set the adapter to the RecyclerView
        fetchOpportunity();

        FloatingActionButton addbtn = findViewById(R.id.newopportunitybtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(UIorgOpportunity.this, addOpportunity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton refresh = findViewById(R.id.refreshbtn);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchOpportunity();
            }
        });

        // Set the OnItemClickListener
        adapter.setOnItemClickListener(new orgOpportunityAdapter.OnItemClickListener() {
            @Override
            public void onClick(orgOpportunityModel model) {
                // Handle the item click here
                // For example, start a new activity with details
                App.orgOpportunityModel = model;
                Intent intent = new Intent(UIorgOpportunity.this, editOpportunity.class);
                startActivity(intent);
            }
        });

    }

    private void fetchOpportunity(){
        db.collection("opportunity").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Set<String> existingIds = new HashSet<>(); // Added: Create a set to track existing IDs
                    for (orgOpportunityModel model : arrayList) {
                        existingIds.add(model.getOpportunityID()); // Added: Add existing IDs to the set
                    }

                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        orgOpportunityModel model = doc.toObject(orgOpportunityModel.class);
                        model.setOpportunityID(doc.getId());
                        if (!existingIds.contains(model.getOpportunityID())) { // Added: Check if ID is already in the set
                            arrayList.add(model); // Added: Add model to arrayList if not a duplicate
                        }
                    }

                    adapter.notifyDataSetChanged(); // Added: Notify adapter of data changes
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UIorgOpportunity.this, "Failed to get rows", Toast.LENGTH_SHORT).show();
            }
        });
    }
}