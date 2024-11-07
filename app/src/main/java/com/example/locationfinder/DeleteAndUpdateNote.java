package com.example.locationfinder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DeleteAndUpdateNote extends AppCompatActivity {
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);


        //get the intent from the main and store the extra values from the selected location
        Intent getIntentFromMain = getIntent();
        int id = getIntentFromMain.getIntExtra("location_id", -1);
        String address = getIntentFromMain.getStringExtra("location_address");
        double latitude = getIntentFromMain.getDoubleExtra("location_latitude", 0.0);
        double longitude = getIntentFromMain.getDoubleExtra("location_longitude", 0.0);

        //convert longitude and latitude to string to be stored in the values
        String latString = Double.toString(latitude);
        String longString = Double.toString(longitude);


        setContentView(R.layout.activity_delete_and_update_note);

        //initializes the views and button
        Button updateButton = findViewById(R.id.updateButton);
        FloatingActionButton deleteButton = findViewById(R.id.deleteFab);

        EditText editTextAddress = findViewById(R.id.editTextAddress);
        EditText editTextLatitude = findViewById(R.id.editTextLongitude);
        EditText editTextLongitude = findViewById(R.id.editTextLatitude);
        Button homeButton = findViewById(R.id.homeButton);


        //set the edittext views to contain the values from the previous location
        editTextAddress.setText(address);
        editTextLatitude.setText(latString);
        editTextLongitude.setText(longString);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //if the home -> button is pressed redirect the user to main activity
        homeButton.setOnClickListener(v -> finish());


        //this listens for when the delete button is presed and deletes the location that was
        //selected and then return to the main activity
        deleteButton.setOnClickListener(v -> {
            dbHandler = new DBHandler(this);
            dbHandler.deleteLocation(id);
            Toast.makeText(this, "Location Deleted", Toast.LENGTH_SHORT).show();
            finish();
        });

        //this listens for when the update button is presed and updates the location that was
        //selected based on the users inputted values and  then return to the main activity
        //if they leave any field empty they will recieve a message from the system
        updateButton.setOnClickListener(v -> {
            dbHandler = new DBHandler(this);
            String newAddress = editTextAddress.getText().toString();
            String newLatitudeString = editTextLatitude.getText().toString();
            String newLongitudeString = editTextLongitude.getText().toString();

            if (address.isEmpty() || newLatitudeString.isEmpty() || newLongitudeString.isEmpty()) {
                Toast.makeText(this, "Enter Correct Information in the fields to update", Toast.LENGTH_SHORT).show();
                return;
            }


            double newLatitude = Double.parseDouble(newLatitudeString);
            double newLongitude = Double.parseDouble(newLongitudeString);



            dbHandler.updateLocation(id, newAddress, newLatitude, newLongitude);
            Toast.makeText(this, "Location updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        });

    }
}