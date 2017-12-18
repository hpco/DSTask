package com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationPresentation;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.VectorEnabledTintResources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationModel.Venue;
import com.hpco.harishpolusani.dickssportingTask.DsHomePage.DsAdapter.RecyclerImageVIewAdapter;
import com.hpco.harishpolusani.dickssportingTask.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by harishpolusani on 12/16/17.
 */

public class DsLocationInnerFragment extends Fragment{



@BindView(R.id.recylerimageview)
    RecyclerView recyclerView;

@BindView(R.id.storeID)
    TextView storeId;
@BindView(R.id.cit )
TextView city;

private Venue venue;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.ds_location_fragement,container,false);
        ButterKnife.bind(this,rootView);
        if(getArguments()!=null){
            venue=  getArguments().getParcelable("Venue");
        }
        initDatatoRecycler();
        return rootView;

    }

    private void initDatatoRecycler() {
if(venue!=null) {
     RecyclerImageVIewAdapter recyclerImageVIewAdapter;
     recyclerImageVIewAdapter = new RecyclerImageVIewAdapter(getActivity(), venue.getPhotos());
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setAdapter(recyclerImageVIewAdapter);
    storeId.setText("Store Id :"+venue.getStoreId());
    if(venue.getLocation()!=null)
    city.setText("City "+venue.getLocation().getCity());

}

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
