package com.example.unityserve;

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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
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

public class editOpportunity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    public orgOpportunityModel model;

    private Button addimagebtn;

    private ImageView image;
    private Uri imageUri;

    private StorageReference storageReference;

    // Reference to Firebase Firestore
    private FirebaseFirestore db;

    private Button buttonDatePicker;

    private  TextView datetext;

    Button duedatebtn;
    TextView duedatetxt;

    Button submit;

    private String dateTimeDisplay;
    private SimpleDateFormat dateFormat;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_opportunity);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //date of opportunity-------------------------------------------------------------------------------------------------------------------------
        buttonDatePicker = findViewById(R.id.datebtn);
        datetext = findViewById(R.id.tvdate);
        buttonDatePicker.setOnClickListener(view -> {
            // Get current date
            duedatePicker();
        });

        //date of evvent-------------------------------------------------------------------------------------------------------------------------
        duedatebtn = findViewById(R.id.duedatebtn);
        duedatetxt = findViewById(R.id.tvduedate);
        duedatebtn.setOnClickListener(view -> {
            // Get current date
            datePicker();
        });

        // Image upload-----------------------------------------------------------------------------------------------------------------------------
        storageReference = FirebaseStorage.getInstance().getReference("opportunityImages");
        db = FirebaseFirestore.getInstance();
        addimagebtn = findViewById(R.id.addimagebtn);
        image = findViewById(R.id.image);

        addimagebtn.setOnClickListener(v -> openFileChooser());
        TextView opportunityTitle = findViewById(R.id.opportunityTitle);
        TextView eventDescription = findViewById(R.id.eventDescription);
        TextView opportunityLocation = findViewById(R.id.opportunityLocation);
        TextView contactInformation = findViewById(R.id.contactInformation);
        Spinner spnnr = findViewById(R.id.category);

        String orgname = "redcross";
        Button edit = findViewById(R.id.edit);
        spnnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItems = parent.getItemAtPosition(position).toString();
                category = selectedItems;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String selectedItem = App.orgOpportunityModel.getCategory().toString(); // The string to select
        setSpinnerSelectionByText(spnnr, selectedItem);
        opportunityTitle.setText(App.orgOpportunityModel.getOpportunityTitle());
        eventDescription.setText(App.orgOpportunityModel.getEventDescription());
        opportunityLocation.setText(App.orgOpportunityModel.getOpportunityLocation());
        contactInformation.setText(App.orgOpportunityModel.getContactInformation());
        duedatetxt.setText(App.orgOpportunityModel.getDuedatetxt());
        datetext.setText(App.orgOpportunityModel.getDatetext());
       // image.setImageURI(Uri.parse(App.orgOpportunityModel.getImageUrl()));

        // Loading the image from URL using an external library like Glide or Picasso
        loadImage(App.orgOpportunityModel.getImageUrl());

        Button delete = findViewById(R.id.deletebtn);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("opportunity").document(App.orgOpportunityModel.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(editOpportunity.this, "Opportunity deleted successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(editOpportunity.this, "Error deleting opportunity: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> opportunity = new HashMap<>();
                setCurrentDateTime();
                opportunity.put("opportunityTitle", Objects.requireNonNull(opportunityTitle.getText()).toString());
                opportunity.put("opportunityLocation", Objects.requireNonNull(opportunityLocation.getText()).toString());
                opportunity.put("orgname", Objects.requireNonNull(orgname.toString()));
                opportunity.put("eventDescription", Objects.requireNonNull(eventDescription.getText()).toString());
                opportunity.put("contactInformation", Objects.requireNonNull(contactInformation.getText()).toString());
                opportunity.put("category", Objects.requireNonNull(category.toString()));
                opportunity.put("duedatetxt", Objects.requireNonNull(duedatetxt.getText()).toString());
                opportunity.put("datetext", Objects.requireNonNull(datetext.getText()).toString());
                opportunity.put("dateTimeDisplay", Objects.requireNonNull(dateTimeDisplay.toString()));

                uploadImageToFirebase(opportunity);
            }
        });

    }
    private void setSpinnerSelectionByText(Spinner spinner, String text) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(text)) {
                spinner.setSelection(i);
                return;
            }
        }
        Toast.makeText(this, "Item not found in spinner", Toast.LENGTH_SHORT).show();
    }
    private void setCurrentDateTime() {
        // Get the current date and time
        Calendar calendar = Calendar.getInstance();
        String currentDateTime = dateFormat.format(calendar.getTime());

        // Display the current date and time
        dateTimeDisplay = currentDateTime;
    }

    public void datePicker(){
        // Get current date
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create and show DatePickerDialog
        DatePickerDialog datePicker = new DatePickerDialog(
                editOpportunity.this,
                (DatePicker view1, int selectedYear, int selectedMonth, int selectedDay) -> {
                    // Update TextView with selected date
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    duedatetxt.setText(selectedDate);
                },
                year, month, day
        );
        datePicker.show();
    }

    public void duedatePicker(){
        // Get current date
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create and show DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                editOpportunity.this,
                (DatePicker view1, int selectedYear, int selectedMonth, int selectedDay) -> {
                    // Update TextView with selected date
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    datetext.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*"); // Only allow image files
        intent.setAction(Intent.ACTION_GET_CONTENT); // Allows user to pick an image from the gallery
        startActivityForResult(intent, PICK_IMAGE_REQUEST); // Start the activity and expect a result with the given request code
    }

    private void saveOpportunityToFirestore(Map<String, Object> opportunity) {
        db.collection("opportunity").document(App.orgOpportunityModel.getId()).set(opportunity)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Handle the success case
                        Toast.makeText(editOpportunity.this, "Opportunity added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(editOpportunity.this, "Error adding opportunity: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    // Handle the result of the image picker activity
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData(); // Get the URI of the selected image
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
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Add the image URL to the opportunity details
                        opportunity.put("imageUrl", uri.toString());
                        saveOpportunityToFirestore(opportunity);
                    }))
                    .addOnFailureListener(e -> Toast.makeText(editOpportunity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            // Proceed without the image URL if no file is selected
            saveOpportunityToFirestore(opportunity);
        }
    }



    // Get the file extension from the image URI
    private String getFileExtension(Uri uri) {
        String extension = null;
        // Get the MIME type of the file
        String mimeType = getContentResolver().getType(uri);
        if (mimeType != null) {
            // Split the MIME type and get the extension part
            String[] parts = mimeType.split("/");
            if (parts.length > 1) {
                extension = parts[1];
            }
        }
        return extension; // Return the file extension
    }
    // Load image from URL using Glide
    private void loadImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .into(image);
        }
    }

}