package com.example.locationfinder;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DBHandler dbHandler;
    private List<Location> locationList;
    private ListView locationListView;

    @Override

    //reload the locations when the app is resumed
    protected void onResume() {
        super.onResume();
        loadLocations();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //initialize the dbhandler and database to be used
        dbHandler = new DBHandler(this);
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.close();

        //initialize the views to be edited later on in teh code
        locationListView = findViewById(R.id.locationListView);
        Button button = findViewById(R.id.buttonAdd);
        SearchView locationSearch = findViewById(R.id.searchViewLocations);


        //populate the database and load the locations
        dbHandler.populateData();
        loadLocations();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //this listner waits for the user to press the "+" button and then opens the
        //AddNewLocation Page

        button.setOnClickListener(v -> {
            Intent intent;
            intent = new Intent(MainActivity.this, AddNewLocation.class);
            startActivity(intent);

        });


        //this listner waits for the user to enter a string in the searchview and will call a
        //function to filter out the results to the user

        locationSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                //do nothing for now
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterSearch(newText);
                return true;
            }
        });




    }

    //this function filters the location list and generates a result of locations that match the
    //search query string

    private void filterSearch(String search){

        //create a new string array list and location list that holds all the locations in the
        // data base

        List<String> searchedLocations = new ArrayList<>();
        List<Location> locations = dbHandler.getDBLocations(); // Fetch locations from the database

        //search through each location in the database and then only add the ones that match the
        //query string and add it to the string array list

        for (Location location : locations) {

            if (location.getAddress().toLowerCase().contains(search.toLowerCase())){
                String LocationListItem =
                        location.getAddress()
                                + "\nLatitude: "
                                + location.getLatitude()
                                + "\nLongitude: "
                                + location.getLongitude();
                searchedLocations.add(LocationListItem);
            }



        }

        //create an array adapter from the queryried locations and display it on the list view
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchedLocations);
        locationListView.setAdapter(adapter);
    }

    private void loadLocations() {

        //create a new string array list and location list that holds all the locations in the
        // data base
        List<Location> locations = dbHandler.getDBLocations();
        List<String> locationDetails = new ArrayList<>();

        //store each location as a string into the string arraylist

        for (Location location : locations) {
            String LocationListItem =
                    location.getAddress()
                            + "\nLatitude: "
                            + location.getLatitude()
                            + "\nLongitude: "
                            + location.getLongitude();
            locationDetails.add(LocationListItem);
        }

        //create an array adapter from the string locations and display it on the list view
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locationDetails);
        locationListView.setAdapter(adapter);

        // Set an item click listener for if a location is pressed so we can take it to the
        //update and delete page
        locationListView.setOnItemClickListener((parent, view, position, id) -> {
                //get the location from the list item you click on
                Location selectedLocation = locations.get(position);

                //make an intent to go to the update and delete page and send the data over aswell
                Intent intent = new Intent(MainActivity.this, DeleteAndUpdateNote.class);
                intent.putExtra("location_id", selectedLocation.getId()); // Pass the ID as an int
                intent.putExtra("location_address", selectedLocation.getAddress());
                intent.putExtra("location_latitude", selectedLocation.getLatitude());
                intent.putExtra("location_longitude", selectedLocation.getLongitude());
                startActivity(intent);

        });
    }



}