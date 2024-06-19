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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.type.DateTime;

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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_opportunity);
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

        //submit----------------------------------------------------------------------------------------------------------------------------------
        TextView opportunityTitle = findViewById(R.id.opportunityTitle);
        TextView eventDescription = findViewById(R.id.eventDescription);
        TextView opportunityLocation = findViewById(R.id.opportunityLocation);
        TextView contactInformation = findViewById(R.id.contactInformation);
        Spinner spnnr = findViewById(R.id.category);

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

        String orgname = "redcross";

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setCurrentDateTime();
                Map<String, Object> opportunity = new HashMap<>();
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
                addOpportunity.this,
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
                addOpportunity.this,
                (DatePicker view1, int selectedYear, int selectedMonth, int selectedDay) -> {
                    // Update TextView with selected date
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    datetext.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    //imageupload firebase and firestore --------------------------------------------------------------------------------------------------------------------------
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*"); // Only allow image files
        intent.setAction(Intent.ACTION_GET_CONTENT); // Allows user to pick an image from the gallery
        startActivityForResult(intent, PICK_IMAGE_REQUEST); // Start the activity and expect a result with the given request code
    }

    private void saveOpportunityToFirestore(Map<String, Object> opportunity) {
        db.collection("opportunity").add(opportunity)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Handle the success case
                        Toast.makeText(addOpportunity.this, "Opportunity added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addOpportunity.this, "Error adding opportunity: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    .addOnFailureListener(e -> Toast.makeText(addOpportunity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
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

}