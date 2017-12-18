package com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationPresentation;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hpco.harishpolusani.dickssportingTask.DsHomePage.CommonUtils;
import com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationModel.DsVenues;
import com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationModel.Location;
import com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationModel.Venue;
import com.hpco.harishpolusani.dickssportingTask.DsHomePage.DsAdapter.DsStoresAdapter;
import com.hpco.harishpolusani.dickssportingTask.DsHomePage.DsAdapter.OnItemClickListner;
import com.hpco.harishpolusani.dickssportingTask.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hpco.harishpolusani.dickssportingTask.DsHomePage.CommonUtils.mlist;

public class DSLocationsActivity extends AppCompatActivity implements DsLocationsContract.LocationView,OnItemClickListner, LocationListener {

    private DsLocationsContract.LocationPresenter mPresenter;
    @BindView(R.id.nearby_rview)
    RecyclerView recyclerView;
    @BindView(R.id.container_layout)
    ConstraintLayout constraintLayoutcontainer;
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

    private  FragmentTransaction ft;
    private static final String fragmentTag="DSinnerfragment";
    private Fragment innerfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(getFragmentManager().findFragmentByTag(fragmentTag)!=null){
            if(recyclerView!=null){
                recyclerView.setVisibility(View.GONE);
            }
            innerfragment=getFragmentManager().findFragmentByTag(fragmentTag);
            getFragmentManager().beginTransaction().add(R.id.container_layout, innerfragment,fragmentTag).commit();
        }else {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dsnearest_locations);
            ButterKnife.bind(this);
            mPrefs = this.getSharedPreferences(FAV_OPTION, Activity.MODE_PRIVATE);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            if (CommonUtils.mlist == null){
                mPresenter = new DsLocationPresenter(this);
            mPresenter.fetchDSApi();
                showLoadingIndicator();
        }else{
                updateAdapter(sortTheStoresByDistance(CommonUtils.mlist));
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void dsApiSuccess(DsVenues dsVenues) {
        hideLoadingIndicator();
        if(dsVenues!=null)
        CommonUtils.mlist=dsVenues.getVenues();
        if(CommonUtils.mlist!=null){
            updateAdapter(sortTheStoresByDistance(CommonUtils.mlist));
        }
//        updateAdapter(dsVenues);
        Log.d("Api succesful", "" + dsVenues);
        Toast.makeText(this, "success" + dsVenues, Toast.LENGTH_LONG).show();
    }


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
                if (venue.getLocation() != null) {
                    locationEnd.setLatitude(venue.getLocation().getLatitude());
                    locationEnd.setLatitude(venue.getLocation().getLongitude());

                    if (storeID != null && venue.getStoreId().equals(storeID)) {

                        distanceMap.put(venue, 0.0f);
                    } else {
                        distanceMap.put(venue, location.distanceTo(locationEnd));
                    }
                }
            }
            sortedset.addAll(distanceMap.entrySet());
            return new LinkedList<>(sortedset);
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
                        updateAdapter(sortTheStoresByDistance(mlist));
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
        list.add(venue2);
        Venue venue4=new Venue();
        venue4.setRating(10.00);
        venue4.setName("Dicks Store5");
        venue4.setStoreId("3");
        Location location4= new Location();
        location4.setCity("CoopersBurg");
        location4.setLatitude(40.511488);
        location4.setLongitude(-75.390458);

        venue4.setLocation(location4);
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
        }else{
            recyclerView.setAdapter(dsAdapter);
            dsAdapter.updateData(dsVenues);
            dsAdapter.notifyDataSetChanged();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.unSubscribe();
            CommonUtils.mlist=null;
            dsAdapter=null;
            recyclerView=null;
            locationManager=null;
            ft=null;
        }
    }

    public void showLoadingIndicator(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoadingIndicator(){
        mProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void onclick(View view, int position,Venue venue) {
        recyclerView.setVisibility(View.GONE);
         innerfragment = new DsLocationInnerFragment();
        Bundle bundle=  new Bundle();
        bundle.putParcelable("Venue",venue);
        innerfragment.setArguments(bundle);
        ft = getFragmentManager().beginTransaction();
        ft.add(R.id.container_layout, innerfragment,fragmentTag);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void refreshData(String id){
        mPrefs.edit().putString(STORE_ID,id).apply();
        dsAdapter.updateData(sortTheStoresByDistance(CommonUtils.mlist));
        dsAdapter.notifyDataSetChanged();
//        dsAdapter.notifyItemMoved(calculateToandFromPosition(id),0);
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

    @Override
    public void onBackPressed() {
        if(getFragmentManager().findFragmentByTag(fragmentTag)!=null&&getFragmentManager().findFragmentByTag(fragmentTag).isVisible()) {
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag(fragmentTag)).commit();
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getFragmentManager().findFragmentByTag(fragmentTag) != null ) {
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag(fragmentTag)).commit();
        }
    }
}
