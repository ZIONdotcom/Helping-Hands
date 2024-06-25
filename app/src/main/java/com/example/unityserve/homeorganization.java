package com.example.unityserve;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.unityserve.databinding.ActivityHomeorganizationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class homeorganization extends AppCompatActivity {
    private ActivityHomeorganizationBinding binding;


    FirebaseAuth auth;
    FirebaseUser user;

    String useremail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityHomeorganizationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    replaceFragment2(new orgopportunity());

        binding.orgnavigation.setOnItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            removeFragment();
            if (id == R.id.opbtn) {
                replaceFragment2(new orgopportunity());
            } else if (id == R.id.volbtn) {
                replaceFragment2(new volunteerlistfragment());
            } else if (id == R.id.profbtn) {
                //replaceFragment2(new );
            }
            return true;
        });

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        /* if (user == null) {
            Intent intent = new Intent(getApplicationContext(), login.class);
            startActivity(intent);
            finish();
        } else {
            useremail = user.getEmail();
            Toast.makeText(homeorganization.this, "User: " + useremail, Toast.LENGTH_SHORT).show();
        }*/

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }

    private void removeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment1 = fragmentManager.findFragmentById(R.id.framelayout2);
        if (fragment1 != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment1);
            fragmentTransaction.commit();
        }
    }

    private void replaceFragment2(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout2, fragment);
        fragmentTransaction.commit();
    }

    private void addActivityViewToFrameLayout() {
        FrameLayout frameLayout = findViewById(R.id.framelayout2);

        // Inflate the layout from the IncludedActivity
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View includedActivityView = inflater.inflate(R.layout.activity_uiorg_opportunity, null);

        // Add the inflated view to the FrameLayout
        frameLayout.addView(includedActivityView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    // We get all the data from string.xml and add it to our opportunityModel class/arraylist
}
