package com.fandean.zhihudaily.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fandean.zhihudaily.R;
import com.fandean.zhihudaily.bean.Collection;
import com.fandean.zhihudaily.ui.DoubanActivity;
import com.fandean.zhihudaily.ui.ZhihuActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fan on 17-6-24.
 */

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionHolder> {

    private List<Collection> mCollections;
    private Context mContext;

    public CollectionAdapter(List<Collection> collections, Context context) {
        mCollections = collections;
        mContext = context;
    }

    @Override
    public CollectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.collection_item, parent, false);
        return new CollectionHolder(view);
    }

    @Override
    public void onBindViewHolder(CollectionHolder holder, int position) {
        Collection collection = mCollections.get(position);
        holder.bindCollection(collection);
    }

    @Override
    public int getItemCount() {
        return mCollections.size();
    }

    public class CollectionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.type_imageView)
        ImageView mTypeImageView;
        @BindView(R.id.title_textView)
        TextView mTitleTextView;
        @BindView(R.id.content_imageView)
        ImageView mContentImageView;

        private Collection mCollection;

        public CollectionHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        private void bindCollection(Collection collection) {
            mCollection = collection;
            if (collection.getType()){
                mTypeImageView.setImageResource(R.drawable.ic_zhi);
            } else {
                mTypeImageView.setImageResource(R.drawable.ic_dou);
            }

            mTitleTextView.setText(collection.getTitel());
            Glide.with(mContext)
                    .load(collection.getImageurl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mContentImageView);
        }


        @Override
        public void onClick(View v) {
            if (mCollection.getType()){
                //知乎
                Intent intent = ZhihuActivity.newIntent(mContext,
                        mCollection.getId(),mCollection.getTitel(),mCollection.getImageurl());
                mContext.startActivity(intent);
            } else {
                //豆瓣
                Intent intent = DoubanActivity.newIntent(mContext,mCollection.getId(),
                        mCollection.getImageurl(),mCollection.getTitel());
                mContext.startActivity(intent);
            }
        }
    }
}
