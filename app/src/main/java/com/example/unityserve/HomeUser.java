package com.example.unityserve;

import static com.example.unityserve.R.id.organizationbtn;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.unityserve.databinding.ActivityHomeUserBinding;
import com.example.unityserve.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeUser extends AppCompatActivity {

    private ActivityHomeUserBinding binding;
    FirebaseAuth auth;
    FirebaseUser user;

    String useremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeUserBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        replaceFragment(new OpportunityFragment());

     binding.bottomNavigationView2.setOnItemSelectedListener(menuItem -> {
         int id = menuItem.getItemId();
         removeFragment();
         if (id == R.id.opportunitybtn) {
             replaceFragment(new OpportunityFragment());
         }
        else if(id == R.id.organizationbtn){
             replaceFragment2(new organizationFragment());
         }
         else if(id == R.id.profilebtn){
             replaceFragment2(new profileFragment());
         }

         return true;
     });

     binding.settings.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             replaceFragment2(new settings());
         }
     });

     auth = FirebaseAuth.getInstance();
     user = auth.getCurrentUser();

     if(user == null){
         Intent intent = new Intent(getApplicationContext(), login.class);
         startActivity(intent);
         finish();
     }
     else{
         useremail = user.getEmail();
         Toast.makeText(HomeUser.this, "User: " + useremail, Toast.LENGTH_SHORT).show();
     }

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }

    private void removeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment1 = fragmentManager.findFragmentById(R.id.framelayout2);
        if(fragment1 !=null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment1);
            fragmentTransaction.commit();
        }else{

        }

    }

    private void replaceFragment2(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout2,fragment);
        fragmentTransaction.commit();
    }


}