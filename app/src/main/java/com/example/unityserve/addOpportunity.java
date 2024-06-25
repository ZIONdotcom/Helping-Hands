package com.example.unityserve;

import static java.security.AccessController.getContext;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class addOpportunity extends AppCompatActivity {

    // Request code for picking an image from the gallery
    private static final int PICK_IMAGE_REQUEST = 1;

    private Button addimagebtn;
    private ImageView image;
    private Uri imageUri;
    private StorageReference storageReference;
    private FirebaseFirestore db;
    private Button buttonDatePicker;
    private TextView datetext;
    private Button duedatebtn;
    private TextView duedatetxt;
    private Button submit;
    private String dateTimeDisplay;
    private SimpleDateFormat dateFormat;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_opportunity);

        // Initialize date format
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Initialize UI components
        buttonDatePicker = findViewById(R.id.datebtn);
        datetext = findViewById(R.id.tvdate);
        buttonDatePicker.setOnClickListener(view -> showDatePicker(datetext));

        duedatebtn = findViewById(R.id.duedatebtn);
        duedatetxt = findViewById(R.id.tvduedate);
        duedatebtn.setOnClickListener(view -> showDatePicker(duedatetxt));

        // Initialize Firebase components
        storageReference = FirebaseStorage.getInstance().getReference("opportunityImages");
        db = FirebaseFirestore.getInstance();

        addimagebtn = findViewById(R.id.addimagebtn);
        image = findViewById(R.id.image);
        addimagebtn.setOnClickListener(v -> openFileChooser());

        // Initialize input fields
        EditText opportunityTitle = findViewById(R.id.opportunityTitle);
        EditText eventDescription = findViewById(R.id.eventDescription);
        EditText opportunityLocation = findViewById(R.id.opportunityLocation);
        EditText contactInformation = findViewById(R.id.contactInformation);
        Spinner spnnr = findViewById(R.id.category);

        spnnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = "";
            }
        });

        String orgname = "redcross";

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(v -> {
            if (areAllFieldsFilled(opportunityTitle, eventDescription, opportunityLocation, contactInformation, datetext, duedatetxt)) {
                setCurrentDateTime();
                Map<String, Object> opportunity = new HashMap<>();
                opportunity.put("opportunityTitle", Objects.requireNonNull(opportunityTitle.getText()).toString());
                opportunity.put("opportunityLocation", Objects.requireNonNull(opportunityLocation.getText()).toString());
                opportunity.put("orgname", orgname);
                opportunity.put("eventDescription", Objects.requireNonNull(eventDescription.getText()).toString());
                opportunity.put("contactInformation", Objects.requireNonNull(contactInformation.getText()).toString());
                opportunity.put("category", category);
                opportunity.put("duedatetxt", Objects.requireNonNull(duedatetxt.getText()).toString());
                opportunity.put("datetext", Objects.requireNonNull(datetext.getText()).toString());
                opportunity.put("dateTimeDisplay", dateTimeDisplay);

                uploadImageToFirebase(opportunity);
            } else {
                Toast.makeText(addOpportunity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean areAllFieldsFilled(EditText opportunityTitle, EditText eventDescription, EditText opportunityLocation, EditText contactInformation, TextView datetext, TextView duedatetxt) {
        return !opportunityTitle.getText().toString().isEmpty() &&
                !eventDescription.getText().toString().isEmpty() &&
                !opportunityLocation.getText().toString().isEmpty() &&
                !contactInformation.getText().toString().isEmpty() &&
                !datetext.getText().toString().isEmpty() &&
                !duedatetxt.getText().toString().isEmpty();
    }

    private void setCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        dateTimeDisplay = dateFormat.format(calendar.getTime());
    }

    private void showDatePicker(TextView textView) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                addOpportunity.this,
                (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    textView.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebase(final Map<String, Object> opportunity) {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        opportunity.put("imageUrl", uri.toString());
                        saveOpportunityToFirestore(opportunity);
                    }))
                    .addOnFailureListener(e -> Toast.makeText(addOpportunity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            saveOpportunityToFirestore(opportunity);
        }
    }

    private void saveOpportunityToFirestore(Map<String, Object> opportunity) {
        db.collection("opportunity").add(opportunity)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(addOpportunity.this, "Opportunity added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(addOpportunity.this, "Error adding opportunity: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private String getFileExtension(Uri uri) {
        String extension = null;
        String mimeType = getContentResolver().getType(uri);
        if (mimeType != null) {
            String[] parts = mimeType.split("/");
            if (parts.length > 1) {
                extension = parts[1];
            }
        }
        return extension;
    }
}
