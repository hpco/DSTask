package com.hpco.harishpolusani.dickssportingTask.DsHomePage.DsAdapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hpco.harishpolusani.dickssportingTask.DsHomePage.DSNearestLocationModel.Photo;
import com.hpco.harishpolusani.dickssportingTask.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by harishpolusani on 12/18/17.
 */

public class RecyclerImageVIewAdapter extends RecyclerView.Adapter<RecyclerImageVIewAdapter.ImageHolder> {
    private Context mContext;
    private List<Photo> mList;
    public RecyclerImageVIewAdapter(Context context, List<Photo> list){
        mContext=context;
        mList=list;
    }
    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageHolder(LayoutInflater.from(mContext).inflate(R.layout.single_item_image_view,parent,false));
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        if(mList.get(position).getUrl()!=null) {
            Glide.with(mContext).load(mList.get(position).getUrl()).override(600,350).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {

        if(mList!=null){
            return mList.size();
        }else {
            return 0;
        }

    }

    public    static class ImageHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imageView)
        ImageView imageView;
        public ImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
