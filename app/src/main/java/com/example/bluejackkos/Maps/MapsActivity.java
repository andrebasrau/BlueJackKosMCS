package com.example.bluejackkos.Maps;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.TextView;

import com.example.bluejackkos.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private final  long MIN_TIME = 1000;
    private final  long MIN_DIST = 5;

    private LatLng latLng;

    TextView kostNameTv;
    TextView mapLat;
    TextView mapLng;
    Double lat;
    Double lng;
    String kosName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat", -1);
        lng = intent.getDoubleExtra("lng", -1);
        kosName = intent.getStringExtra("name");

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

         kostNameTv = findViewById(R.id.kost_Name_tv_maps);
         kostNameTv.setText(kosName);
//        mapLat = findViewById(R.id.latMapTextView);
//        mapLng = findViewById(R.id.lngMapTextView);
//        mapLat.setText(lat.toString());
//        mapLng.setText(lng.toString());
        latLng = new LatLng(-34, 151);
        //getLocation();
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
        LatLng sydney = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Your Kost"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                try {
//                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                    mMap.addMarker(new MarkerOptions().position(latLng).title("kost Position")); //bisa ganti nnti stringnya jadi variable nama kost
//
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//
//                    String userPhone = "999999";
//
//                    String kostLat = String.valueOf(location.getLatitude());
//                    String kostLng = String.valueOf(location.getLongitude());
//
//                    String position = "Latitude = " + kostLat + "Longitude = " + kostLng;
////                    SmsManager smsManager   = SmsManager.getDefault();
////                    smsManager.sendTextMessage(userPhone, null, position,null,null);
//
//                } catch (Exception error){
//                    error.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };
//
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        try {
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, locationListener);
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME, MIN_DIST,locationListener);
//        }catch (SecurityException error){
//            error.printStackTrace();
//        }

    }
    public void  getLocation(){
    double latitude = latLng.latitude;
    double longitude =latLng.longitude;

    latitude = Double.parseDouble(mapLat.getText().toString());
    longitude = Double.parseDouble(mapLng.getText().toString());

    latLng = new LatLng(latitude, longitude);

//        Geocoder geocoder;
//        List<Address> addresses;
//        geocoder = new Geocoder(this, Locale.getDefault());
//
//        String address = null;
//        String city = null;
//        String state = null;
//        String country = null;
//        String postalcode = null;
//        String knonname = null;
//
//        try {
//            addresses= geocoder.getFromLocation(latitude,longitude, 1);
//        address= addresses.get(0).getAddressLine(0);
//        city = addresses.get(0).getLocality();
//        state = addresses.get(0).getAdminArea();
//        country =addresses.get(0).getCountryName();
//        postalcode = addresses.get(0).getPostalCode();
//        knonname = addresses.get(0).getFeatureName();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in :" +address + city + state + country +postalcode+ knonname));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}
