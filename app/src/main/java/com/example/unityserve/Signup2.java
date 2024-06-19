package com.example.unityserve;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup2 extends AppCompatActivity {

    Button calllogin2;
    EditText email, password;
    Button signupbtn;
    Button signuporg;

    FirebaseAuth mAuth;

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(Signup2.this,HomeUser.class );
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();

        calllogin2 = findViewById(R.id.loginb2);
        calllogin2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup2.this,login.class);
                startActivity(intent);
            }
        });

         signuporg = findViewById(R.id.signupOrg);

         signuporg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(Signup2.this, signupOrganization.class);
                 startActivity(intent);
             }
         });

        email = findViewById(R.id.emailtxt);
        password = findViewById(R.id.passwordtxtt);
        signupbtn = findViewById(R.id.signupbtnn);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emaill, passwordd;
                emaill = String.valueOf(email.getText());
                passwordd = String.valueOf(password.getText());

                //check kung empty yung edittext
                if(TextUtils.isEmpty(emaill)){
                    Toast.makeText(Signup2.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passwordd)){
                    Toast.makeText(Signup2.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                //register
                mAuth.createUserWithEmailAndPassword(emaill, passwordd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Signup2.this, "Sign up Successful",
                                            Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(Signup2.this,login.class );
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(Signup2.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}