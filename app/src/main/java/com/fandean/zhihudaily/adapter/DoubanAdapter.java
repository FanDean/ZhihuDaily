package com.fandean.zhihudaily.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fandean.zhihudaily.R;
import com.fandean.zhihudaily.bean.DoubanMovieInTheaters;
import com.fandean.zhihudaily.ui.DoubanActivity;
import com.fandean.zhihudaily.ui.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fan on 17-6-16.
 */

public class DoubanAdapter extends RecyclerView.Adapter<DoubanAdapter.DoubanHolder> {

    private List<DoubanMovieInTheaters.SubjectsBean> mSubjectsList;
    private Context mContext;

    public DoubanAdapter(List<DoubanMovieInTheaters.SubjectsBean> subjectsList, Context context) {
        mSubjectsList = subjectsList;
        mContext = context;
    }


    @Override
    public DoubanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_douban, parent, false);
//        Log.d("FanDean","豆瓣 onCreateViewHolder()");
        return new DoubanHolder(view);
    }

    @Override
    public void onBindViewHolder(DoubanHolder holder, int position) {
        DoubanMovieInTheaters.SubjectsBean subjectsBean = mSubjectsList.get(position);
        holder.bindSubjectsBean(subjectsBean);
//        Log.d("FanDean","豆瓣 onBindViewHolder");
    }

    @Override
    public int getItemCount() {
//        Log.d("FanDean","豆瓣 链表size: " + mSubjectsList.size());
        return mSubjectsList.size();
    }


    public void clear() {
        mSubjectsList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<DoubanMovieInTheaters.SubjectsBean> subjectsList){
        mSubjectsList.addAll(subjectsList);
        notifyDataSetChanged();
    }



    public class DoubanHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.movie_imageview)
        ImageView mMovieImageview;
        @BindView(R.id.movie_title)
        TextView mMovieTitle;
//        @BindView(R.id.ratingBar)
//        RatingBar mRatingBar;
        @BindView(R.id.number_of_ratings)
        TextView mNumberOfRatings;
        private DoubanMovieInTheaters.SubjectsBean mSubjectsBean;

        public DoubanHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(this);
        }

        private void changeImageHeight() {
            //TODO 弄清除问题的原因
            //mMovieImageview.getLayoutParams().height = (int)( mMovieImageview.getWidth() * 1.5);
            mMovieImageview.getLayoutParams().height = (int)( itemView.getWidth() * 1.5);
            Log.d(MainActivity.FAN_DEAN, "宽度：" + mMovieImageview.getWidth()
                    + "高度: " + mMovieImageview.getHeight());
        }

        public void bindSubjectsBean(DoubanMovieInTheaters.SubjectsBean subjectsBean){
            //更改ImageView的高度
//            changeImageHeight();

            mSubjectsBean = subjectsBean;
            mMovieTitle.setText(mSubjectsBean.getTitle());
//            mRatingBar.setRating((float) mSubjectsBean.getRating().getAverage());
            String str = Double.toString(mSubjectsBean.getRating().getAverage());
//            String.format()
            mNumberOfRatings.setText(str);
//            Log.d("Fandean",mSubjectsBean.getTitle());

            Glide.with(mContext)
                    .load(mSubjectsBean.getImages().getLarge())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.1f)
                    .into(mMovieImageview);
        }

        @Override
        public void onClick(View v) {
            Log.d("FanDean","点击了豆瓣电影：" + mSubjectsBean.getTitle());
            mContext.startActivity(DoubanActivity.newIntent(mContext,
                    Integer.parseInt(mSubjectsBean.getId()),mSubjectsBean.getImages().getLarge(),
                    mSubjectsBean.getTitle()));
        }
    }
}
