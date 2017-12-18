package com.hpco.harishpolusani.dickssportingTask.DsHomePage.DsAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
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

    @BindView(R.id.distance)
    TextView mDistance;
    @BindView(R.id.city)
    TextView mCity;

    @BindView(R.id.zipcode)
    TextView mZipcode;

    @BindView(R.id.rating)
    TextView mRating;

    private DsStoresAdapter.OnItemClickListner listner;

    public DsViewHolder(View itemView, DsStoresAdapter.OnItemClickListner listner) {
        super(itemView);
        this.listner=listner;
        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);
        getAdapterPosition();
    }


    @Override
    public void onClick(View view) {
        int pos= getAdapterPosition();
        if (pos != RecyclerView.NO_POSITION){
            listner.onclick(view,pos);
        }
    }
}
