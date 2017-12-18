package com.hpco.harishpolusani.dickssportingTask.DsHomePage.DsAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationModel.Venue;
import com.hpco.harishpolusani.dickssportingTask.R;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by harishpolusani on 12/16/17.
 */

public class DsStoresAdapter extends RecyclerView.Adapter<DsViewHolder> {
private Context context;
    private List<Map.Entry<Venue, Float>> mVenuesList;
    private OnItemClickListner mListner;
    private int position;
    private static  final String FAV_OPTION="favoption";
    private static final String STORE_ID="storeid";
    private SharedPreferences mPrefs;
    private String id;
    private boolean intialSetup;

    public DsStoresAdapter(Context context, List<Map.Entry<Venue, Float>> venueList, OnItemClickListner listner){
      this.context=context;
        mVenuesList=venueList;
        mListner=listner;
        mPrefs = context.getSharedPreferences(FAV_OPTION, Activity.MODE_PRIVATE);
        id=mPrefs.getString(STORE_ID,null);
    }
    @Override
    public DsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return new DsViewHolder(LayoutInflater.from(context).inflate(R.layout.single_item_view,parent,false),mListner);
    }

    @Override
    public void onBindViewHolder(DsViewHolder holder, int position) {

     if(mVenuesList.get(position)!=null&&mVenuesList.get(position).getKey()!=null){
     holder.mName.setText(mVenuesList.get(position).getKey().getName());
         holder.mRating.setText("Store Rating : " +String.valueOf(mVenuesList.get(position).getKey().getRating()));
         holder.mStoreId.setText("Store ID :"+mVenuesList.get(position).getKey().getStoreId());
         if(mVenuesList.get(position).getKey().getLocation()!=null) {
             holder.mCity.setText("City : "+mVenuesList.get(position).getKey().getLocation().getCity());
             holder.mZipcode.setText("Zipcode "+mVenuesList.get(position).getKey().getLocation().getPostalCode());
             holder.mDistance.setText("Distance From Current Location: "+String.valueOf(mVenuesList.get(position).getValue()));

         }
     }
    }

    public void updateData(List<Map.Entry<Venue, Float>> mVenuesList) {
        this.mVenuesList = mVenuesList;
    }

    @Override
    public int getItemCount() {
        if(mVenuesList!=null){
            return mVenuesList.size();
        }else{
            return 0;
        }

    }
    public interface  OnItemClickListner{
        void onclick(View view,int position);
        void refreshData(String storeID);
    }
}
