package com.example.unityserve;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Signup2 extends AppCompatActivity {

    EditText email, password, confirmPassword;
    Button signupbtn;

    Button org;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        email = findViewById(R.id.emailtxt);
        password = findViewById(R.id.passwordtxtt);
        confirmPassword = findViewById(R.id.confirmPasswordtxt);
        signupbtn = findViewById(R.id.signupbtnn);

        org = findViewById(R.id.signupOrg);
        org.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup2.this, signupOrganization.class);
                startActivity(intent);
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emaill = email.getText().toString().trim();
                String passwordd = password.getText().toString().trim();
                String confirmPasswordd = confirmPassword.getText().toString().trim();

                // Check if any field is empty
                if (TextUtils.isEmpty(emaill) || TextUtils.isEmpty(passwordd) || TextUtils.isEmpty(confirmPasswordd)) {
                    Toast.makeText(Signup2.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if passwords match
                if (!passwordd.equals(confirmPasswordd)) {
                    Toast.makeText(Signup2.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Register user with Firebase Authentication
                mAuth.createUserWithEmailAndPassword(emaill, passwordd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Upload user email to Firestore with usertype "volunteer"
                                    addUserToFirestore(emaill, "volunteer");

                                    Toast.makeText(Signup2.this, "Sign up Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Signup2.this, login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Signup2.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void addUserToFirestore(String email, String usertype) {
        // Create a new user document in the "user" collection with email and usertype fields
        db.collection("user")
                .document(email)
                .set(new User(email, usertype))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // User added to Firestore successfully
                            Toast.makeText(Signup2.this, "User added to Firestore", Toast.LENGTH_SHORT).show();
                        } else {
                            // Error adding user to Firestore
                            Toast.makeText(Signup2.this, "Failed to add user to Firestore", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}

