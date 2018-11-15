package com.android.greenfoodchallenge.carboncalculator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;



public class mapActivity extends AppCompatActivity implements OnMapReadyCallback{

        public static Intent makeIntent(Context context){
            Intent intent =new Intent(context, mapActivity.class);
            return intent;
        }

        //private FusedLocationProviderClient client;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Retrieve the content view that renders the map.
            setContentView(R.layout.activity_map);
            // Get the SupportMapFragment and request notification
            // when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        @Override
        public void onMapReady(GoogleMap googleMap) {
            // Add a marker in Sydney, Australia,
            // and move the map's camera to the same location.
            LatLng vancouver = new LatLng(49.2827, -123.1207);
            googleMap.addMarker(new MarkerOptions().position(vancouver)
                    .title("Vancouver"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(vancouver));
            googleMap.animateCamera( CameraUpdateFactory.zoomTo( 14.5f ) );

        }
}


