package com.android.greenfoodchallenge.carboncalculator;

//this file has been changed

import android.app.ActivityOptions;
import android.content.pm.PackageManager;
import android.location.Location;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.Toast;

public class MapsActivity extends AppCompatActivity implements PlaceSelectionListener{

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, MapsActivity.class);
        return intent;
    }

    private Button LocationButton;
    private TextView mPlaceDetailsText;
    private TextView mPlaceAttribution;
    private boolean mLocationPermissionGranted;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private PlaceAutocompleteFragment autocompleteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        Bundle b = new Bundle();
        // Retrieve the PlaceAutocompleteFragment.
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        //Filters out non-establishments, only shows businesses
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ESTABLISHMENT)
                .build();

        autocompleteFragment.setFilter(typeFilter);
        //Default location is downtown vancouver
        autocompleteFragment.setBoundsBias(new LatLngBounds(
                new LatLng(49.2827,-123.1207),
                new LatLng(49.2827,-123.1207)));
        // Register a listener to receive callbacks when a place has been selected or an error has occurred
        autocompleteFragment.setOnPlaceSelectedListener(this);

        // Retrieve the TextViews that will display details about the selected place.
        mPlaceDetailsText = (TextView) findViewById(R.id.place_details);
        mPlaceAttribution = (TextView) findViewById(R.id.place_attribution);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        LocationButton = (Button)findViewById(R.id.ButtonLoc);
        getLocationPermission();
        //If location permission is not granted the default GPS location remains in downtown vancouver
        getDeviceLocation();

        //Menu Button
        LocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddMeal = new Intent(MapsActivity.this, AddMeal.class);
                goToAddMeal.putExtras(b);
                startActivity(goToAddMeal, ActivityOptions.makeSceneTransitionAnimation(MapsActivity.this).toBundle());
            }
        });
    }


    //Prompts pop up to give location permission
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    private void getDeviceLocation() {

        try {

            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                Log.d("PERM_GRANTED", "Permission granted");
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            Log.d("GPS_SUCCESS", "Setting GPS location to user location.");
                            mLastKnownLocation = task.getResult();
                            autocompleteFragment.setBoundsBias(new LatLngBounds(
                                    new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude()),
                                    new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude())));

                        } else {
                            Log.d("GPS_ERROR", "Current location is null. Using defaults.");
                        }
                    }
                });
            }
            //Use defaults if task fails
            else{
                Log.d("GPS_ERROR_2", "Task Unsuccessful.");
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /*
     * Callback invoked when a place has been selected from the PlaceAutocompleteFragment.
     */
    @Override
    public void onPlaceSelected(Place place) {

        // Format the returned place's details and display them in the TextView.
        mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                place.getAddress(), place.getWebsiteUri()));

        CharSequence attributions = place.getAttributions();
        if (!TextUtils.isEmpty(attributions)) {
            mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
        } else {
            mPlaceAttribution.setText("");
        }
    }

    /*
     * Callback invoked when PlaceAutocompleteFragment encounters an error.
     */
    @Override
    public void onError(Status status) {

        Toast.makeText(this, "Unexpected error occurred, input location manually" + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

    private static Spanned formatPlaceDetails(Resources res, CharSequence name,
                                              CharSequence address, Uri websiteUri) {
        //Adds the restaurant name
        Bundle b = new Bundle();
        String restauranttName = name.toString();
        Log.e("RestName", "name is: " + restauranttName);
        b.putString("restaurantName", restauranttName);
        return Html.fromHtml(res.getString(R.string.place_details, name, address,
                websiteUri));
    }
}
