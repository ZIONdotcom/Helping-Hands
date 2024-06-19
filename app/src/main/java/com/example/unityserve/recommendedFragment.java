package com.example.unityserve;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class recommendedFragment extends Fragment {

ArrayList <opportunityModel> models = new ArrayList<>();;

    int[] postImages = {R.drawable.post1, R.drawable.post2, R.drawable.post3};

    int[] orgimage = {R.drawable.logobg, R.drawable.orglogo1, R.drawable.orglogo2};
    public recommendedFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static recommendedFragment newInstance(String param1, String param2) {
        recommendedFragment fragment = new recommendedFragment();
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
      View view =  inflater.inflate(R.layout.fragment_recommended, container, false);

      RecyclerView recyclerview = view.findViewById(R.id.recyclerview);

      setupModels();
        postRecyclerviewAdapter adapter = new postRecyclerviewAdapter(getContext(), models, getActivity().getSupportFragmentManager());
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    onBackPressed();
                    return true;
                }
                return false;
            }
        });


        return view;
    }

    public void setupModels (){
        String[] opportunityTitle = getResources().getStringArray(R.array.opportunityTitle);
        String[] OpportunityDateTime = getResources().getStringArray(R.array.OpportunityDateTime);
        String[] OpportunityLocation = getResources().getStringArray(R.array.OpportunityLocation);
        String[] OpportunityDescription = getResources().getStringArray(R.array.OpportunityDescription);
        String[] contact = getResources().getStringArray(R.array.contact);
        String[] deadline = getResources().getStringArray(R.array.deadline);
        String[] orgName = getResources().getStringArray(R.array.orgName);
        String[] category = getResources().getStringArray(R.array.category);

        //based sa category yung idedesplay
       String categoryif = "category 1";
        //length ng opportunity title kasi same lang naman sila lahat ng length(dapat)
        for (int i = 0; i < opportunityTitle.length; i++) {
            // opportunityModel opportunityModel = new opportunityModel(opportunityTitle[i],OpportunityDateTime[i],OpportunityLocation[i],OpportunityDescription[i],contact[i],deadline[i]);
            if (category[i].equals(categoryif)) {
                models.add(new opportunityModel(opportunityTitle[i], OpportunityDateTime[i], OpportunityLocation[i], OpportunityDescription[i], contact[i], deadline[i], orgName[i], postImages[i], orgimage[i], category[i]));
            }
        }
    }




    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout2, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void onBackPressed() {
        // Handle the back press here
        // For example, replace the fragment with another fragment
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout2, new OpportunityFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }




}