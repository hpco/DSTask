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


/**
 * Created by harishpolusani on 12/16/17.
 */

public class DsStoresAdapter extends RecyclerView.Adapter<DsViewHolder> implements OnItemClickListner {
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


        return new DsViewHolder(LayoutInflater.from(context).inflate(R.layout.single_item_view,parent,false),this);
    }

    @Override
    public void onBindViewHolder(DsViewHolder holder, int position) {

     if(mVenuesList.get(position)!=null&&mVenuesList.get(position).getKey()!=null){
         if(position==0){
             holder.favView.setImageDrawable(context.getResources().getDrawable(R.drawable.selected_icon));
         }
     holder.mName.setText(mVenuesList.get(position).getKey().getName());
         holder.mRating.setText(context.getResources().getString(R.string.placeholdertextrating) +String.valueOf(mVenuesList.get(position).getKey().getRating()));

         holder.setStoreId(mVenuesList.get(position).getKey().getStoreId());
         if(mVenuesList.get(position).getKey().getLocation()!=null) {
             holder.mCity.setText(context.getResources().getString(R.string.placeholdertextcity)+mVenuesList.get(position).getKey().getLocation().getCity());
             holder.mZipcode.setText(context.getResources().getString(R.string.placeholdetextzipcode)+mVenuesList.get(position).getKey().getLocation().getPostalCode());
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

    @Override
    public void onclick(View view, int position,Venue venue) {
        mListner.onclick(view, position,mVenuesList.get(position).getKey());
    }

    @Override
    public void refreshData(String storeID) {

        mListner.refreshData(storeID);
    }


}
