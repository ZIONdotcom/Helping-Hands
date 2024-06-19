package com.example.unityserve;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;


public class OpportunityFragment extends Fragment {


    ArrayList<opportunityModel> opportunityModels = new ArrayList<>();

    ArrayList<recommendedModel> recommendedModels = new ArrayList<>();
    recommendedfirebaseAdapter adapterfirebase;

    int[] postImages = {R.drawable.post1, R.drawable.post2, R.drawable.post3};

    int[] orgimage = {R.drawable.logobg, R.drawable.orglogo1, R.drawable.orglogo2};



    public OpportunityFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_opportunity, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        RecyclerView recommendedRecyclerView = view.findViewById(R.id.orgopportunityRV);

  setUpRecommendedModel();
        recommendedRecyclerviewAdapter adapter2 = new recommendedRecyclerviewAdapter(getContext(), recommendedModels, getActivity().getSupportFragmentManager());
        recommendedRecyclerView.setAdapter(adapter2);
        recommendedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        setUpOpportunityModel();
        postRecyclerviewAdapter adapter = new postRecyclerviewAdapter(getContext(), opportunityModels, getActivity().getSupportFragmentManager());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

/*FirebaseRecyclerOptions<recommendedModel> options =
                new FirebaseRecyclerOptions.Builder<recommendedModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("OpportunityTable"), recommendedModel.class)
                        .build();

        adapterfirebase = new recommendedfirebaseAdapter(options);
        recommendedRecyclerView.setAdapter(adapterfirebase);
        recommendedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));*/


       ImageButton viewmoreButton = view.findViewById(R.id.viewmore);
       viewmoreButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               /*
               Intent intent = new Intent(getActivity(), recommendedFragment.class);
               startActivity(intent);
               */
               replaceFragment2(new recommendedFragment());

           }
       });

       ImageButton settings = view.findViewById(R.id.settings);
       settings.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               replaceFragment2(new settings());
           }
       });


        return view;
    }

    public void onStart() {
        super.onStart();
      //  adapterfirebase.startListening();
    }

    public void onStop() {
        super.onStop();
       // adapterfirebase.stopListening();
    }

    private void setUpOpportunityModel() {
        String[] opportunityTitle = getResources().getStringArray(R.array.opportunityTitle);
        String[] OpportunityDateTime = getResources().getStringArray(R.array.OpportunityDateTime);
        String[] OpportunityLocation = getResources().getStringArray(R.array.OpportunityLocation);
        String[] OpportunityDescription = getResources().getStringArray(R.array.OpportunityDescription);
        String[] contact = getResources().getStringArray(R.array.contact);
        String[] deadline = getResources().getStringArray(R.array.deadline);
        String[] orgName = getResources().getStringArray(R.array.orgName);
        String[] category = getResources().getStringArray(R.array.category);

        //length ng opportunity title kasi same lang naman sila lahat ng length(dapat)
        for (int i = 0; i < opportunityTitle.length; i++) {
            // opportunityModel opportunityModel = new opportunityModel(opportunityTitle[i],OpportunityDateTime[i],OpportunityLocation[i],OpportunityDescription[i],contact[i],deadline[i]);
            opportunityModels.add(new opportunityModel(opportunityTitle[i], OpportunityDateTime[i], OpportunityLocation[i], OpportunityDescription[i], contact[i], deadline[i], orgName[i],postImages[i], orgimage[i], category[i]));
        }
    }

    private void setUpRecommendedModel() {
        String[] opportunityTitle = getResources().getStringArray(R.array.opportunityTitle);
        String[] orgName = getResources().getStringArray(R.array.orgName);
        String[] category = getResources().getStringArray(R.array.category);

        String categoryif = "category 1";
        for (int i = 0; i < opportunityTitle.length; i++) {
            if (category[i].equals(categoryif)) {
                recommendedModels.add(new recommendedModel(opportunityTitle[i], orgName[i], orgimage[i], postImages[i], categoryif));
            }
        }

    }


    private void replaceFragment2(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout2,fragment);
        fragmentTransaction.commit();
    }





}