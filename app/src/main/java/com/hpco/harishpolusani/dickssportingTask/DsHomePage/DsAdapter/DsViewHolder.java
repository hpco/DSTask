package com.hpco.harishpolusani.dickssportingTask.DsHomePage.DsAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hpco.harishpolusani.dickssportingTask.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by harishpolusani on 12/16/17.
 */

public class DsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.name)
    TextView mName;

    @BindView(R.id.city)
    TextView mCity;

    @BindView(R.id.zipcode)
    TextView mZipcode;

    @BindView(R.id.rating)
    TextView mRating;

    @BindView(R.id.fav_view)
    ImageView favView;





    private String storeId;
    private OnItemClickListner listner;

    public DsViewHolder(View itemView, OnItemClickListner listner) {
        super(itemView);
        this.listner=listner;
        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);
        getAdapterPosition();
       favView.setOnClickListener(this);
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==favView.getId()){
//            int pos = getAdapterPosition();
//            if (pos != RecyclerView.NO_POSITION) {
                listner.refreshData(storeId);
//            }
        }else {
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                listner.onclick(view, pos,null);
            }
        }
    }
}
