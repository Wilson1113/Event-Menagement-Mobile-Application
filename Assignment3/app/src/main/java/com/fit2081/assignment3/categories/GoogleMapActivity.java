package com.fit2081.assignment3.categories;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.fit2081.assignment3.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.fit2081.assignment3.databinding.ActivityGoogleMapBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityGoogleMapBinding binding;

    private String countryToFocus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGoogleMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        countryToFocus = getIntent().getExtras().getString("location", "");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            geocoder.getFromLocationName(countryToFocus, 1, addresses -> {
                // if there are results, this condition would return true
                if (!addresses.isEmpty()) {

                    // run on UI thread as the user interface will update once set map location
                    runOnUiThread(() -> {
                        Toast.makeText(this, "API Check "+countryToFocus, Toast.LENGTH_SHORT).show();
                        // define new LatLng variable using the first address from list of addresses
                        LatLng newAddressLocation = new LatLng(
                                addresses.get(0).getLatitude(),
                                addresses.get(0).getLongitude()
                        );

                        // repositions the camera according to newAddressLocation
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(newAddressLocation));

                        // just for reference add a new Marker
                        mMap.addMarker(
                                new MarkerOptions()
                                        .position(newAddressLocation)
                                        .title(countryToFocus)
                        );

                        // set zoom level to 8.5f or any number between range of 2.0 to 21.0
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(10f));
                    });
                }
                else {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Category address not found", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }

        mMap.setOnMapClickListener(point -> {
            //save current location
            String msg;
            String selectedCountry = "";


            List<Address> addresses = new ArrayList<>();
            try {
                //The results of getFromLocation are a best guess and are not guaranteed to be meaningful or correct.
                // It may be useful to call this method from a thread separate from your primary UI thread.
                addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1); //last param means only return the first address object
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            if (addresses.size() == 0) {
                Toast.makeText(this, "No Country at this location!! Sorry", Toast.LENGTH_SHORT).show();
            }
            else {
                Address address = addresses.get(0);
                Toast.makeText(this, "The selected country is "+address.getCountryName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}