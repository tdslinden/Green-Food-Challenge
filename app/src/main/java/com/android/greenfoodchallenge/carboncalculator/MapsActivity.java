package com.android.greenfoodchallenge.carboncalculator;

//this file has been modified from the orignal

import android.app.ActivityOptions;
import android.content.pm.PackageManager;
import android.location.Address;
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
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.Toast;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements PlaceSelectionListener{

    private static Bundle b;

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, MapsActivity.class);
        return intent;
    }

    private Button LocationButton, backButton;
    public static String nameRestaurant;
    private TextView mPlaceDetailsText;
    private TextView mPlaceAttribution;
    public static String regionResturant;
    private boolean mLocationPermissionGranted;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    public static String locationRestaurant;
    private Location mLastKnownLocation;
    private PlaceAutocompleteFragment autocompleteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        Bundle b = new Bundle();
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ESTABLISHMENT)
                .build();

        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setBoundsBias(new LatLngBounds(
                new LatLng(49.2827,-123.1207),
                new LatLng(49.2827,-123.1207)));
        autocompleteFragment.setOnPlaceSelectedListener(this);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mPlaceDetailsText = (TextView) findViewById(R.id.place_details);
        mPlaceAttribution = (TextView) findViewById(R.id.place_attribution);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        LocationButton = (Button)findViewById(R.id.ButtonLoc);

        getLocationPermission();
        //If location permission is not granted the default GPS location remains in downtown vancouver
//        getDeviceLocation();

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

        Toast.makeText(this, "Unexpected error occurred, input location manually",
                Toast.LENGTH_SHORT).show();

        Intent goToAddMeal = new Intent(MapsActivity.this, AddMeal.class);
        startActivity(goToAddMeal, ActivityOptions.makeSceneTransitionAnimation(MapsActivity.this).toBundle());
    }

    private static Spanned formatPlaceDetails(Resources res, CharSequence name,
                                              CharSequence address, Uri websiteUri) {
        //Adds the restaurant name
        b = new Bundle();
        String restaurantName = name.toString();

        String restaurantAddress = address.toString();
        List<String> addressList = Arrays.asList(restaurantAddress.split(","));
        String formattedAddress = addressList.get(0);
        String formattedRegion = addressList.get(1);
        Log.e("RestName", "name is: " + restaurantName);
        Log.e("RestAddress", "Address is: " + formattedAddress);
        Log.e("RestRegion", "Region is: " + formattedRegion);

        b.putString("restaurantName", restaurantName);
        b.putString("restaurantAddress", formattedAddress);
        b.putString("restaurantRegion", formattedRegion);

        nameRestaurant = restaurantName;
        locationRestaurant = formattedAddress;
        regionResturant = formattedRegion;

        return Html.fromHtml(res.getString(R.string.place_details, name, address,
                websiteUri));
    }
}
