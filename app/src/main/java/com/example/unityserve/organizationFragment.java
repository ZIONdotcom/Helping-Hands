package com.example.unityserve;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unityserve.App;
import com.example.unityserve.R;
import com.example.unityserve.organizationAdapter;
import com.example.unityserve.organizationmodel;
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

public class organizationFragment extends Fragment {
    FirebaseFirestore db;
    RecyclerView recyclerView;

    organizationAdapter adapter;
    ArrayList<organizationmodel> arrayList;

    public organizationFragment() {

    }

    public static organizationFragment newInstance(String param1, String param2) {
        organizationFragment fragment = new organizationFragment();
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
        View view = inflater.inflate(R.layout.fragment_organization, container, false);

        FirebaseApp.initializeApp(getContext());
        db = FirebaseFirestore.getInstance();

        recyclerView = view.findViewById(R.id.orgrv);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        arrayList = new ArrayList<>();
        adapter = new organizationAdapter(getContext(), arrayList);
        recyclerView.setAdapter(adapter);

        fetchOrganization(); // Call the method to fetch data

        adapter.setOnItemClickListener(new organizationAdapter.ViewHolder.OnItemClickListener() {
            @Override
            public void onClick(organizationmodel model) {
                App.organizationmodel = model;
                //  Intent intent = new Intent(getContext(), .class);
                // startActivity(intent);

            }
        });

        return view;
    }

    private void fetchOrganization() {
        db.collection("organization").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Set<String> existingIds = new HashSet<>();
                    for (organizationmodel model : arrayList) {
                        existingIds.add(model.getOrgid());
                    }

                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        organizationmodel model = doc.toObject(organizationmodel.class);
                        model.setOrgid(doc.getId());
                        if (!existingIds.contains(model.getOrgid())) {
                            arrayList.add(model);
                        }
                    }

                    adapter.notifyDataSetChanged();
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
