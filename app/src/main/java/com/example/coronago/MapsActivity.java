package com.example.coronago;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.telecom.Call;
import android.widget.SearchView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;

    public static SearchView searchView;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;

    public static float currentUserLatitude,currentUserLongitude;

    public static double latitude,longitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        searchView = (SearchView)findViewById(R.id.togo);
        //listening the text query in the search view
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String location = searchView.getQuery().toString();//getting what user enters
                List<Address> addressList = null;
                if(location!=null || location!=""){

                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("this is where you want to go"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    float zoomto =  16.0f;
                    mMap.moveCamera(CameraUpdateFactory.zoomTo(zoomto));

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


//        requestPermission();
//        client = LocationServices.getFusedLocationProviderClient(this);

//        ((FusedLocationProviderClient) client).getLastLocation().addOnSuccessListener(MapsActivity.this, new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//
//                if(location!=null){
//                    latitude =location.getLatitude();
//                    longitude =location.getLongitude();
//                    Toast.makeText(MapsActivity.this,location.toString(),Toast.LENGTH_LONG).show();
//                    System.out.println("latitude is "+latitude+ " longitude is "+longitude);
//                }
//
//            }
//        });
//

    }

//    private void requestPermission(){
//        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
//    }



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

        if(ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            return;
        }

        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //to zoom to our current location
        final float zoomLevel = 16.0f; //This goes up to 21

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

        //getting the latitude and longitude of all the available drivers and showing it in our map
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Shops");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    data.getKey();
                    Double latitude = (Double) dataSnapshot.child(data.getKey()).child("latitude").getValue();
                    System.out.println("latitude is " +latitude);

                    Double longitude = (Double) dataSnapshot.child(data.getKey()).child("longitude").getValue();
                    System.out.println("longitude is "+longitude);

                    LatLng hospitalLocation = new LatLng(latitude,longitude);

                    mMap.addMarker(new MarkerOptions().position(hospitalLocation).title(data.getKey()).icon(bitmapDescriptorFromVector(getApplicationContext(), R.drawable.ic_local_hospital_black_24dp)));

                    //requestUber.setText("Driver at: "+String.valueOf(distance));
                    if(data.getKey().equals("Gunjur Hospital")) {

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hospitalLocation, zoomLevel));
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        };
        myRef.addValueEventListener(postListener);


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String markerTitle = marker.getTitle();
                System.out.println(markerTitle);

                Intent i = new Intent(MapsActivity.this,DetailsActivity.class);
                i.putExtra("title",markerTitle);
                startActivity(i);

                return false;
            }
        });

    }


    protected synchronized void buildGoogleApiClient(){


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    //to add out own marker in the google map application

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId){

        Drawable vectorDrawable = ContextCompat.getDrawable(context,vectorResId);
        vectorDrawable.setBounds(0,0,vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());

        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        //when the map is connected and is ready to get started

        //getting location second to second

        mLocationRequest = new LocationRequest();
        mLocationRequest.setFastestInterval(1000); //1000 = 1second
//        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //This helps in getting the best location accuracy that mobile can get

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        currentUserLatitude = (float) location.getLatitude();
        currentUserLongitude = (float) location.getLongitude();

        System.out.println("current latitude is "+currentUserLatitude+" longitude is "+currentUserLongitude);
//        moving the map/ camera in the same pace in which the user moves so that user always stays in the center of the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15)); //ITS VALUE RANGES FROM (1-21) you can play around with these indices i.e 11


    }

    //This onStop() method is when the user closes the application then SUCH drives are regarded as inactive driver and removing its location values

    @Override
    protected void onStop() {
        super.onStop();


    }
}
