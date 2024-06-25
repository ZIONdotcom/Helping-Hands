package com.example.unityserve;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.unityserve.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class profileFragment extends Fragment {

    private FirebaseFirestore db;
    private FragmentProfileBinding binding;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private volunteerHistoryadapter adapter;
    private List<VolunteertHistoryModel> volunteerHistoryList;

    public profileFragment() {
        // Required empty public constructor
    }

    public static profileFragment newInstance(String param1, String param2) {
        profileFragment fragment = new profileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize binding
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        FirebaseApp.initializeApp(getContext());
        db = FirebaseFirestore.getInstance();

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        volunteerHistoryList = new ArrayList<>();
        adapter = new volunteerHistoryadapter(getContext(), volunteerHistoryList);
        binding.volunteerhistoryrv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.volunteerhistoryrv.setAdapter(adapter);

        if (user != null) {
            // Fetch user profile
            fetchUserProfile();
            fetchVolunteerHistory(user.getEmail());
        } else {
            Toast.makeText(getContext(), "No user logged in", Toast.LENGTH_SHORT).show();
        }

        return binding.getRoot();
    }

    private void fetchUserProfile() {
        String userEmail = user.getEmail();
        db.collection("volunteer").whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    volunteermodel model = document.toObject(volunteermodel.class);
                                    displayUserProfile(model);
                                    break; // Assuming email is unique and we only get one document
                                }
                            } else {
                                // No document found for the given email
                                Toast.makeText(getContext(), "User profile not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Task failed
                            Toast.makeText(getContext(), "Failed to retrieve profile", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void displayUserProfile(volunteermodel model) {
        binding.name.setText(model.getName());
        binding.email.setText(model.getEmail());
        binding.about.setText(model.getAbout());
        binding.location.setText(model.getLocation());
        binding.interest.setText(model.getInterest());

        Glide.with(this)
                .load(model.getProfilepic())
                .into(binding.ppic);
    }

    private void fetchVolunteerHistory(String email) {
        db.collection("volunteerhistory").whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            volunteerHistoryList.clear();
                            for (DocumentSnapshot document : task.getResult()) {
                                VolunteertHistoryModel model = document.toObject(VolunteertHistoryModel.class);
                                volunteerHistoryList.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Failed to fetch volunteer history", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
