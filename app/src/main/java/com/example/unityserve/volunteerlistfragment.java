package com.example.unityserve;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class volunteerlistfragment extends Fragment {

    FirebaseFirestore db;
    RecyclerView recyclerView;

    volunteerAdapter adapter;
    ArrayList<volunteermodel> arrayList;

    public volunteerlistfragment() {
        // Required empty public constructor
    }

    public static volunteerlistfragment newInstance(String param1, String param2) {
        volunteerlistfragment fragment = new volunteerlistfragment();
        Bundle args = new Bundle();
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
        View view = inflater.inflate(R.layout.fragment_volunteerlistfragment, container, false);
        FirebaseApp.initializeApp(getContext());
        db = FirebaseFirestore.getInstance();

        recyclerView = view.findViewById(R.id.recyclerviewv);

        // Setting the LayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        arrayList = new ArrayList<>();
        adapter = new volunteerAdapter(getContext(), arrayList);
        recyclerView.setAdapter(adapter);


        fetchVolunteer();

        adapter.setOnItemClickListener(new volunteerAdapter.OnItemClickListener() {
            @Override
            public void onClick(volunteermodel model) {
                    App.volunteermodel = model;
                    Intent intent = new Intent(getContext(), profileFragment.class);
                    startActivity(intent);

            }
        });
        return view;
    }

    private void fetchVolunteer(){
        db.collection("volunteer").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Set<String> existingIds = new HashSet<>(); // Added: Create a set to track existing IDs
                    for (volunteermodel modell : arrayList) {
                        existingIds.add(modell.getVolunteerid()); // Added: Add existing IDs to the set
                    }

                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        volunteermodel modell = doc.toObject(volunteermodel.class);
                        modell.setVolunteerid(doc.getId());
                        if (!existingIds.contains(modell.getVolunteerid())) { // Added: Check if ID is already in the set
                            arrayList.add(modell); // Added: Add model to arrayList if not a duplicate
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
}