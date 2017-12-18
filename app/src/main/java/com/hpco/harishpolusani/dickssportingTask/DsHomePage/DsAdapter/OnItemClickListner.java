package com.hpco.harishpolusani.dickssportingTask.DsHomePage.DsAdapter;

import android.view.View;

import com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationModel.Venue;

public interface  OnItemClickListner{
        void onclick(View view, int position, Venue venue);
        void refreshData(String storeID);
    }