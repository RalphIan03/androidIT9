package com.example.demoappit6;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class googleMaps extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_google_maps);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.googleMaps);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap gglMap){
        gmap = gglMap;

        gglMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng myhome = new LatLng(6.746204543504433, 125.33919180488708);
        LatLng mySchool = new LatLng(6.7501878627016305, 125.35071347402774);
        gmap.addMarker(new MarkerOptions().position(myhome).title("My Home"));
        gmap.addMarker(new MarkerOptions().position(mySchool).title("My School"));
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(myhome, 10));

    }
}