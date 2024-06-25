package com.example.unityserve;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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


public class orgopportunity extends Fragment {

    ImageButton settings;
    FirebaseFirestore db;
    RecyclerView recyclerView;

    orgOpportunityAdapter adapter; // Added: Define adapter as a class variable
    ArrayList<orgOpportunityModel> arrayList; // Added: Define arrayList as a class variable

    public orgopportunity() {
        // Required empty public constructor
    }

    public static orgopportunity newInstance(String param1, String param2) {
        orgopportunity fragment = new orgopportunity();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orgopportunity, container, false);

        FirebaseApp.initializeApp(getContext());
        db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.orgopportunityRV);

        // Setting the LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        arrayList = new ArrayList<>(); // Added: Initialize arrayList
        adapter = new orgOpportunityAdapter(getContext(), arrayList); // Added: Initialize adapter
        recyclerView.setAdapter(adapter); // Added: Set the adapter to the RecyclerView
        fetchOpportunity();

        settings = view.findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment2(new settings());
            }
        });

        FloatingActionButton addbtn = view.findViewById(R.id.newopportunitybtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), addOpportunity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton refresh = view.findViewById(R.id.refreshbtn);
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
                Intent intent = new Intent(getContext(), editOpportunity.class);
                startActivity(intent);
            }
        });

        return view;
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
                Toast.makeText(getContext(), "Failed to get rows", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void replaceFragment2(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout2,fragment);
        fragmentTransaction.commit();
    }
}