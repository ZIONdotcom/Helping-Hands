package com.example.unityserve;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link organizationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class organizationFragment extends Fragment {

    public organizationFragment() {

    }
    String selectedItems;

    // TODO: Rename and change types and number of parameters
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
        Spinner spinnr = view.findViewById(R.id.Orgspinner1);
        spinnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItems = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(), selectedItems, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }
}