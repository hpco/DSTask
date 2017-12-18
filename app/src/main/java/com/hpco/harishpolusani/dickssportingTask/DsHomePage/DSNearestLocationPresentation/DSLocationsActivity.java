package com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationPresentation;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationModel.DsVenues;
import com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationModel.Location;
import com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationModel.Venue;
import com.hpco.harishpolusani.dickssportingTask.DsHomePage.DsAdapter.DsStoresAdapter;
import com.hpco.harishpolusani.dickssportingTask.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DSLocationsActivity extends AppCompatActivity implements DsLocationsContract.LocationView, DsStoresAdapter.OnItemClickListner, LocationListener {

    private DsLocationsContract.LocationPresenter mPresenter;
    @BindView(R.id.nearby_rview)
    RecyclerView recyclerView;

    @BindView(R.id.api_loading)
    ProgressBar mProgressBar;
    private LocationManager locationManager;
    private DsStoresAdapter dsAdapter;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;
    private static final long MIN_TIME_BW_UPDATES = 6000;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    android.location.Location location = null;
    private  SharedPreferences mPrefs;
    private static  final String FAV_OPTION="favoption";
    private static final String STORE_ID="storeid";
    private List<Venue> mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsnearest_locations);
        ButterKnife.bind(this);
        mPrefs = this.getSharedPreferences(FAV_OPTION, Activity.MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mPresenter = new DsLocationPresenter(this);
        mPresenter.fetchDSApi();
        showLoadingIndicator();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dsnearest_locations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void dsApiSuccess(DsVenues dsVenues) {
        hideLoadingIndicator();
//        updateAdapter(dsVenues);
        Log.d("Api succesful", "" + dsVenues);
        Toast.makeText(this, "success" + dsVenues, Toast.LENGTH_LONG).show();
    }

//    private void updateAdapter(DsVenues dsVenues) {
//        if (dsAdapter == null) {
//            dsAdapter = new DsStoresAdapter(this, dsVenues.getVenues(), this);
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//            recyclerView.setLayoutManager(mLayoutManager);
//            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.recycleranimation_down2up);
//            recyclerView.setLayoutAnimation(animation);
//            recyclerView.setAdapter(dsAdapter);
//        } else {
//            dsAdapter.updateData(dsVenues.getVenues());
//            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.recycleranimation_down2up);
//            recyclerView.setLayoutAnimation(animation);
//            dsAdapter.notifyDataSetChanged();
//            recyclerView.scheduleLayoutAnimation();
//        }
//    }

    @Override
    public void dsApiFailure() {
        hideLoadingIndicator();
        updateAdapter(sortTheStoresByDistance(loadDummyData()));
        Log.d("Api failure", "");
    }

    private List<Map.Entry<Venue, Float>> sortTheStoresByDistance(List<Venue> list) {
        SortedMap<Venue, Float> distanceMap = new TreeMap<>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);

        } else {
            location = getCurrentLocation();
        }

        if (location != null) {
            SortedSet<Map.Entry<Venue, Float>> sortedset = new TreeSet<>(
                    new Comparator<Map.Entry<Venue, Float>>() {
                        @Override
                        public int compare(Map.Entry<Venue, Float> e1,
                                           Map.Entry<Venue, Float> e2) {
                            return e1.getValue().compareTo(e2.getValue());
                        }
                    });
            android.location.Location locationEnd = new android.location.Location("");
            String storeID=mPrefs.getString(STORE_ID,null);
            Venue venue;
            for (int i = 0; i < list.size(); i++) {
                venue = list.get(i);
                locationEnd.setLatitude(venue.getLocation().getLatitude());
                locationEnd.setLatitude(venue.getLocation().getLongitude());
                if(storeID!=null&&venue.getStoreId().equalsIgnoreCase(storeID)){
                    distanceMap.put(venue,0.0f);
                }else {
                    distanceMap.put(venue, location.distanceTo(locationEnd));
                }
            }
            sortedset.addAll(distanceMap.entrySet());
            return new ArrayList<>(sortedset);
        }
        return null;
    }

    private android.location.Location getCurrentLocation() {
        try {
            locationManager = (LocationManager) this
                    .getSystemService(LOCATION_SERVICE);
            if (locationManager != null) {
                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNetworkEnabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                if (!isGPSEnabled && !isNetworkEnabled) {
                } else {
                    if (isNetworkEnabled) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return null;
                        }
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    }
                }
            }}
        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
//                        try {
//                            locationManager.requestLocationUpdates(
//                                    LocationManager.NETWORK_PROVIDER,
//                                    MIN_TIME_BW_UPDATES,
//                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//                            if (locationManager != null) {
//                                location = locationManager
//                                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                            }
//                        }catch(Exception e){
//                            e.printStackTrace();
//                        }
                         location = getCurrentLocation();
                    }


                } else {

                    ///implement if permission got denied

                }
                return;
            }

        }
    }

    private List<Venue> loadDummyData() {

        List<Venue> list= new ArrayList<>();
        Venue venue=new Venue();
        Location location= new Location();
        venue.setRating(10.00);
        venue.setStoreId("0");
        venue.setName("Dicks Store");
        location.setCity("Pitsburg");
        location.setLatitude(40.440625);
        location.setLongitude(-79.995886);
        Venue venue1=new Venue();
        venue.setLocation(location);
        list.add(venue);
        venue1.setRating(10.00);
        venue1.setStoreId("1");
        venue1.setName("Dicks Store2");
        Location location1= new Location();
        location1.setCity("philadelphia");
        location1.setLatitude(39.952584);
        location1.setLongitude(-75.165222);
        venue1.setLocation(location1);

        list.add(venue1);
        Venue venue2=new Venue();
        venue2.setRating(10.00);
        venue2.setStoreId("4");
        venue2.setName("Dicks Store3");

        Location location2= new Location();
        location2.setCity("Allentown");
        location2.setLatitude(40.608430);
        location2.setLongitude(-75.490183);
        venue2.setLocation(location2);
//        venue2.getLocation().setCity("Allentown");
//        venue2.getLocation().setLatitude(40.608430);
//        venue2.getLocation().setLongitude(-75.490183);
        list.add(venue2);
        Venue venue3=new Venue();
        venue3.setRating(10.00);
        venue3.setName("Dicks Store4");
        venue3.setStoreId("2");
        Location location3= new Location();
        location3.setCity("Allentown");
        location3.setLatitude(40.608430);
        location3.setLongitude(-75.490183);
        venue3.setLocation(location3);
//        venue3.getLocation().setCity("Allentown");
//        venue3.getLocation().setLatitude(40.608430);
//        venue3.getLocation().setLongitude(-75.490183);
        list.add(venue3);
        Venue venue4=new Venue();
        venue4.setRating(10.00);
        venue4.setName("Dicks Store5");
        venue4.setStoreId("3");
        Location location4= new Location();
        location4.setCity("CoopersBurg");
        location4.setLatitude(40.511488);
        location4.setLongitude(-75.390458);
        venue4.setLocation(location4);
//        venue4.getLocation().setCity("CoopersBurg");
//        venue4.getLocation().setLatitude(40.511488);
//        venue4.getLocation().setLongitude(-75.390458);
        list.add(venue4);
        mlist=list;
        return list;
    }

    private void updateAdapter(List<Map.Entry<Venue, Float>> dsVenues) {
        if (dsAdapter == null) {
            dsAdapter = new DsStoresAdapter(this, dsVenues, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.recycleranimation_down2up);
            recyclerView.setLayoutAnimation(animation);
            recyclerView.setAdapter(dsAdapter);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.unSubscribe();
        }
    }

    public void showLoadingIndicator(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoadingIndicator(){
        mProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void onclick(View view, int position) {

    }

    @Override
    public void refreshData(String id){
        mPrefs.edit().putString(STORE_ID,id).apply();
        dsAdapter.updateData(sortTheStoresByDistance(mlist));
        dsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLocationChanged(android.location.Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
