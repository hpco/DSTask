package com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationPresentation;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.VectorEnabledTintResources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationModel.Venue;
import com.hpco.harishpolusani.dickssportingTask.R;

/**
 * Created by harishpolusani on 12/16/17.
 */

public class DsLocationInnerFragment extends Fragment{



    public DsLocationInnerFragment(Venue venue){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.ds_location_fragement,container,false);
        return rootView;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
