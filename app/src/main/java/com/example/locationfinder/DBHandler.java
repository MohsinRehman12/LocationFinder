package com.example.locationfinder;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DBHandler extends SQLiteOpenHelper{

    //initialze strings for databse values, columns and tables.
    private static final String DB_NAME = "locationdb";

    private static final int DB_VERSION = 2;

    private static final String TABLE_NAME = "location";

    private static final String COL_ID = "id";
    private static final String COL_ADDRESS = "address";
    private static final String COL_LATITUDE = "latitude";
    private static final String COL_LONGITUDE = "longitude";


    //constructior for DBhandler
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    //creates the table in SQLite
    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ADDRESS + " TEXT, "
                + COL_LATITUDE + " REAL, "
                + COL_LONGITUDE + " REAL)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    //this method is used to add a new location to the db
    public void addNewLocation(String address, double latitude, double longitude) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ADDRESS, address);
        values.put(COL_LONGITUDE, longitude);
        values.put(COL_LATITUDE, latitude);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //this method is used to update a location in the db
    public void updateLocation(int id, String address, double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(COL_ADDRESS, address);
        values.put(COL_LONGITUDE, longitude);
        values.put(COL_LATITUDE, latitude);
        db.update(TABLE_NAME, values, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    //this method is used to remove a  location from the db
    public void deleteLocation(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }


    //this is the function used to populate the database with the 100 toronto addresses, but will only do so if the database is empty
    public void populateData(){

        SQLiteDatabase db = this.getWritableDatabase();

        String[] addresses = {
                "123 Queen St W", "1000 Finch Ave W", "5000 Yonge St", "2050 Brimley Rd", "3500 Derry Rd E",
                "1 Yonge St", "2000 Credit Valley Rd", "5000 Finch Ave E", "2500 Eglinton Ave E", "20 Bay St",
                "600 University Ave", "1 Dundas St W", "2050 Lawrence Ave E", "1750 Steeles Ave E", "1770 Finch Ave W",
                "1300 Finch Ave E", "1350 York Mills Rd", "2750 Victoria Park Ave", "50 Bloor St W", "3001 Markham Rd",
                "1300 Eglinton Ave E", "1688 Dundas St W", "2250 Bloor St W", "2001 Lawrence Ave E", "45 King St E",
                "3000 Steeles Ave W", "3750 Bloor St W", "3350 Bloor St W", "1010 Dundas St E", "4300 Steeles Ave E",
                "2700 Victoria Park Ave", "555 Richmond St W", "4001 Finch Ave E", "2200 Eglinton Ave E", "9800 Jane St",
                "3001 Lawrence Ave W", "70 East Liberty St", "1010 Eglinton Ave W", "1444 Queen St W", "5500 Yonge St",
                "2560 Victoria Park Ave", "1230 Eglinton Ave E", "1450 Lawrence Ave W", "1280 Finch Ave E",
                "2500 Lawrence Ave W", "8600 McCowan Rd", "5255 Yonge St", "2000 Lawrence Ave E", "1450 Queen St E",
                "2220 Finch Ave E", "85 The Queensway", "1650 Dupont St", "1750 Kingston Rd", "1100 Eglinton Ave W",
                "3900 Bloor St W", "30 Elm St", "4500 Yonge St", "2100 McCowan Rd", "2700 Bloor St W",
                "550 Bloor St W", "1005 Gerrard St E", "2700 Victoria Park Ave", "5005 Steeles Ave E",
                "1111 Richmond St W", "10 York St", "1600 Steeles Ave W", "100 Front St W", "2310 Eglinton Ave W",
                "1050 Brimley Rd", "1500 Kingston Rd", "4400 Kingston Rd", "4800 Yonge St", "7500 Woodbine Ave",
                "6700 McCowan Rd", "5 Wingold Ave", "7600 Markham Rd", "8800 Weston Rd", "4500 Lawrence Ave E",
                "1400 Bayview Ave", "1600 Dupont St", "2000 Lawrence Ave W", "70 Eglinton Ave W", "1250 Lawrence Ave E",
                "2501 Lawrence Ave W", "1770 Eglinton Ave W", "2700 Dufferin St", "1980 Queen St W", "1280 Finch Ave W",
                "2 Queen St W", "3300 Yonge St", "1450 Don Mills Rd", "3 Thornhill Woods Dr", "6100 Kingston Rd",
                "2100 Lawrence Ave E", "1200 Markham Rd", "5050 Yonge St", "2200 Lawrence Ave W", "1100 Finch Ave W",
                "1450 Steeles Ave W", "1900 Eglinton Ave E"
        };

        double[] latitudes = {
                43.6426, 43.7576, 43.7634, 43.7692, 43.6243,
                43.6426, 43.5806, 43.7797, 43.7384, 43.6489,
                43.6539, 43.6572, 43.7625, 43.8234, 43.7692,
                43.7668, 43.7584, 43.7804, 43.6700, 43.7740,
                43.7104, 43.6554, 43.6459, 43.7471, 43.6491,
                43.8138, 43.6163, 43.6162, 43.6620, 43.8181,
                43.7794, 43.6468, 43.7785, 43.7412, 43.8183,
                43.6610, 43.6445, 43.6855, 43.6528, 43.7611,
                43.7779, 43.7096, 43.7175, 43.7599, 43.6570,
                43.8726, 43.7620, 43.7441, 43.6705, 43.7563,
                43.6404, 43.6625, 43.6855, 43.6946, 43.6139,
                43.6532, 43.7663, 43.7756, 43.6411, 43.6631,
                43.6682, 43.7790, 43.7804, 43.7785, 43.7695,
                43.7501, 43.6880, 43.7280, 43.6690, 43.6180,
                43.6510, 43.7050, 43.6230, 43.7780, 43.6880,
                43.6610, 43.7630, 43.7400, 43.6710, 43.7020,
                43.6550, 43.6780, 43.6950, 43.6890, 43.7090,
                43.6350, 43.6150, 43.8000, 43.7780, 43.7700,
                43.8020, 43.7800, 43.7500, 43.7700, 43.9000,
                43.6880, 43.6750, 43.6530, 43.6850, 43.6150
        };

        double[] longitudes = {
                -79.3813, -79.4933, -79.4107, -79.2634, -79.6462,
                -79.3813, -79.6584, -79.2869, -79.2802, -79.3764,
                -79.3853, -79.3777, -79.2734, -79.5228, -79.4731,
                -79.3784, -79.3453, -79.3131, -79.3863, -79.2293,
                -79.3501, -79.4515, -79.4790, -79.2650, -79.3754,
                -79.5290, -79.5328, -79.5230, -79.3467, -79.3190,
                -79.2870, -79.3993, -79.2848, -79.2799, -79.5225,
                -79.5138, -79.4261, -79.4564, -79.4302, -79.4148,
                -79.2899, -79.3525, -79.4763, -79.3715, -79.5232,
                -79.2754, -79.4184, -79.2834, -79.3153, -79.2858,
                -79.4634, -79.4556, -79.2702, -79.4614, -79.5302,
                -79.3802, -79.4140, -79.2609, -79.4900, -79.4268,
                -79.3293, -79.2865, -79.2935, -79.2985, -79.3500,
                -79.4200, -79.4700, -79.4600, -79.3600, -79.4200,
                -79.3900, -79.2800, -79.3800, -79.3700, -79.4800,
                -79.5600, -79.6000, -79.6200, -79.6300, -79.6500,
                -79.6800, -79.7000, -79.7300, -79.7600, -79.7900,
                -79.8000, -79.8100, -79.8200, -79.8300, -79.8400,
                -79.8500, -79.8600, -79.8700, -79.8800, -79.8900,
                -79.9000, -79.9100, -79.9200, -79.9300, -79.9400
        };

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        cursor.moveToFirst();

        //this checks if the database is empty and then populates then
        if (cursor.getInt(0) == 0) {
            for (int i = 0; i < addresses.length; i++) {
                ContentValues values = new ContentValues();
                values.put(COL_ADDRESS, addresses[i]);
                values.put(COL_LATITUDE, latitudes[i]);
                values.put(COL_LONGITUDE, longitudes[i]);
                db.insert(TABLE_NAME, null, values);
            }
        }
        cursor.close();


    }





    //this method gets all the locations in the database
    public List<Location> getDBLocations(){

        List<Location> locationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {

            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
                    @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(COL_ADDRESS));
                    @SuppressLint("Range") double longitude = cursor.getDouble(cursor.getColumnIndex(COL_LONGITUDE));
                    @SuppressLint("Range") double latitude = cursor.getDouble(cursor.getColumnIndex(COL_LATITUDE));
                    @SuppressLint("Range") Location location = new Location(id, address, latitude, longitude);
                    locationList.add(location);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        cursor.close();
        db.close();
        return locationList;



    }

}
