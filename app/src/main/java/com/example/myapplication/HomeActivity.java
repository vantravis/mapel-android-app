package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.fragments.AkunFragment;
import com.example.myapplication.fragments.BantuanFragment;
import com.example.myapplication.fragments.BerandaFragment;
import com.example.myapplication.fragments.CariFragment;
import com.example.myapplication.fragments.NotifikasiFragment;
import com.example.myapplication.utils.PopupUtil;
import com.example.myapplication.utils.SharedPrefManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements LocationListener, BerandaFragment.OnFragmentInteractionListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    @BindView(R.id.main_nav)
    BottomNavigationView mMainNav;
    LocationManager mLocationManager;
    String kota;
    double lat, lng;
    List<String> city;
    SharedPrefManager sharedPrefManager;


    AkunFragment akunFragment;
    BantuanFragment bantuanFragment;
    BerandaFragment berandaFragment;
    CariFragment cariFragment;
    NotifikasiFragment notifikasiFragment;
    Location myLocation;

    private GoogleApiClient googleApiClient;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        akunFragment = new AkunFragment();
        bantuanFragment = new BantuanFragment();
        berandaFragment = new BerandaFragment();
        cariFragment = new CariFragment();
        notifikasiFragment = new NotifikasiFragment();

        city = new ArrayList<>();

        sharedPrefManager = new SharedPrefManager(this);

        sharedPrefManager.setNavSelected(SharedPrefManager.NAV_SELECTED, 1);
        sharedPrefManager.setHave_search_loc(SharedPrefManager.have_search_loc, true);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.beranda_nav:

                        sharedPrefManager.setNavSelected(SharedPrefManager.NAV_SELECTED, 1);
                        setFragment(berandaFragment);

/*
                        if (!sharedPrefManager.getHave_search_loc()) {
                            System.out.println("item id");
                            kota = sharedPrefManager.getCity();
                            updateView();
                        }
*/

                        return true;

                    case R.id.bantuan_nav:
                        //startActivity(new Intent(getApplicationContext(), ChartActivity.class));
                        //startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        sharedPrefManager.setNavSelected(SharedPrefManager.NAV_SELECTED, 2);
                        setFragment(bantuanFragment);

                        return true;

                    case R.id.cari_nav:

                        sharedPrefManager.setNavSelected(SharedPrefManager.NAV_SELECTED, 3);
                        setFragment(cariFragment);
                        return true;

                    case R.id.notifikasi_nav:

                        sharedPrefManager.setNavSelected(SharedPrefManager.NAV_SELECTED, 4);
                        setFragment(notifikasiFragment);
                        return true;


                    case R.id.akun_nav:

                        sharedPrefManager.setNavSelected(SharedPrefManager.NAV_SELECTED, 5);
                        setFragment(akunFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });

            setUpGClient();



    }

    @Override
    public void onConnected(Bundle bundle) {
        isGpsEnabled();
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Do whatever you need
        //You can display a message here
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //You can display a message here
    }

    private void isGpsEnabled() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //All location services are disabled
            checkPermissions();
        } else {
            checkPermissions();
        }
    }


    private void updateView() {
        //PopupUtil.showMsg(getApplicationContext(), "Kota Main" + kota, Toast.LENGTH_SHORT);
        //CommonFunction.customToast(this,"Kota Main"+ kota);
        Bundle bundle = new Bundle();
        bundle.putString("kota", kota);
        berandaFragment.setArguments(bundle);
        setFragment(berandaFragment);

    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onLocationChanged(Location mLocation) {
        PopupUtil.showMsg(this, "Location Change", Toast.LENGTH_SHORT);

        myLocation = mLocation;

        lat = mLocation.getLatitude();
        lng = mLocation.getLongitude();
        sharedPrefManager.setHave_search_loc(SharedPrefManager.have_search_loc, false);
        LatLng currentLoc = new LatLng(lat, lng);

            convertToAddress(lat, lng);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        } else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {

            PopupUtil.showMsg(this, "Go to Settings and Grant the permission to use this feature.", Toast.LENGTH_SHORT);
            Intent i = new Intent();
            i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            i.addCategory(Intent.CATEGORY_DEFAULT);
            i.setData(Uri.parse("package:" + getPackageName()));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            startActivity(i);
            // User selected the Never Ask Again Option
        } else {
            checkPermissions();
            PopupUtil.showMsg(this, "Permission Denied", Toast.LENGTH_SHORT);


        }
    }

    private void checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(HomeActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        } else {
            getMyLocation();
        }

    }



    @Override
    public void onFragmentInteraction(String city) {

    }


    private synchronized void setUpGClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    private void convertToAddress(Double lat, Double lng) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());


        try {
            addresses = geocoder.getFromLocation(lat, lng, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only

            //Tanggal
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
            String time = "Waktu : " + mdformat.format(c.getTime());
            //waktuSekarang = mdformat.format(c.getTime());
            //String formattedDate = df.format(c);
            //String tanggal[] = getSplitData(df.format(c));
            //String hari = new LocalDate(Integer.parseInt(tanggal[2]), Integer.parseInt(tanggal[1]), Integer.parseInt(tanggal[0])).dayOfWeek().getAsText(Locale.ENGLISH);

            kota = city;
            sharedPrefManager.setCity(SharedPrefManager.CITY, kota);
            sharedPrefManager.setHaveLocation(SharedPrefManager.HAVE_LOCATION, true);
            updateView();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getMyLocation() {
        if (googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(HomeActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    myLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    //locationRequest.setInterval(3000);
                    //locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(HomeActivity.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        myLocation = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(HomeActivity.this,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    // finish();
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }


}