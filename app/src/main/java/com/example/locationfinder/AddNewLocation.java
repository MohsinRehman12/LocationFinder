package com.example.locationfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddNewLocation extends AppCompatActivity {
    private DBHandler dbHandler;
    private EditText editTextAddress, editTextLatitude, editTextLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_location);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextLatitude = findViewById(R.id.editTextLatitude);
        editTextLongitude = findViewById(R.id.editTextLongitude);
        Button addLocationButton = findViewById(R.id.buttonSubmit);
        Button homeButton = findViewById(R.id.homeButton);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //if the home -> button is pressed redirect the user to main activity
        homeButton.setOnClickListener(v -> finish());


        //listener for when the add new location button is pressed
        addLocationButton.setOnClickListener(v -> addNewLocation());
    }

    private void addNewLocation() {

        dbHandler = new DBHandler(this);
        String address = editTextAddress.getText().toString();
        String latitudeString = editTextLatitude.getText().toString();
        String longitudeString = editTextLongitude.getText().toString();

        if (address.isEmpty() || latitudeString.isEmpty() || longitudeString.isEmpty()) {
            Toast.makeText(this, "Enter Correct Information in the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double latitude = Double.parseDouble(latitudeString);
        double longitude = Double.parseDouble(longitudeString);



        dbHandler.addNewLocation(address, latitude, longitude);
        Toast.makeText(this, "Location added successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}