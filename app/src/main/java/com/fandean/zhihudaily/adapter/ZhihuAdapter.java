package com.fandean.zhihudaily.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fandean.zhihudaily.R;
import com.fandean.zhihudaily.bean.ZhihuNews;
import com.fandean.zhihudaily.ui.ZhihuActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fan on 17-6-16.
 */

public class ZhihuAdapter extends RecyclerView.Adapter<ZhihuAdapter.ZhihuHolder> {
    private List<ZhihuNews.StoriesBean> mStoriesList;
    private Context mContext;

    public ZhihuAdapter(Context context, List<ZhihuNews.StoriesBean> storiesList){
        mContext = context;
        mStoriesList = storiesList;
    }

    @Override
    public ZhihuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_zhihu,parent,false);
        return new ZhihuHolder(view);
    }

    @Override
    public void onBindViewHolder(ZhihuHolder holder, int position) {
        ZhihuNews.StoriesBean story = mStoriesList.get(position);
        holder.bindStoriesBean(story);
    }

    @Override
    public int getItemCount() {
        return mStoriesList.size();
    }


    /**
     * 以下两个方法用于配合SwipeRefreshLayout使用
     * 调用时，先clear()再addAll()
     * SwipeRefreshLayout的setRefreshing方法在这两个之后调用
     */
    public void clear() {
        mStoriesList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<ZhihuNews.StoriesBean> storiesList){
        mStoriesList.addAll(storiesList);
        notifyDataSetChanged();
    }

    public void insertAllToFirst(List<ZhihuNews.StoriesBean> storiesList){
        mStoriesList.addAll(0,storiesList);
        notifyItemRangeInserted(0,storiesList.size());
    }

    public void appendAll(List<ZhihuNews.StoriesBean> storiesList){
        int size = mStoriesList.size();
        mStoriesList.addAll(storiesList); //默认应该就是追加
        notifyItemRangeInserted(size,storiesList.size());
    }



    public class ZhihuHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.zhihu_item_textview) TextView mTextView;
        @BindView(R.id.zhihu_item_imageview) ImageView mImageView;

        private ZhihuNews.StoriesBean mStory;

        public ZhihuHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            //为整个列表项设置监听器
            itemView.setOnClickListener(this);
        }

        //参考《Android权威编程指南》优化绑定过程
        public void bindStoriesBean(ZhihuNews.StoriesBean story){
            mStory = story;
            mTextView.setText(mStory.getTitle());
            Glide.with(mContext)
                    .load(mStory.getImages().get(0))
                    //Glide 4.0.0中已经不能直接使用此方法
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    //加载缩略图时应用于目标尺寸的乘数。看doc
                    .thumbnail(0.1f)
                    .into(mImageView);
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(mContext, mStory.getTitle() + "被点击",Toast.LENGTH_SHORT).show();
            mContext.startActivity(ZhihuActivity.newIntent(mContext,
                    mStory.getId(),mStory.getTitle(),mStory.getImages().get(0)));
//            ZhihuNewsLab.get(mContext).getZhihuNews("20170620");
        }
    }



    //RecyclerView上拉加载操作： 图片解析 ![图片](http://imgur.com/6E7X1pr.png "")
    //用于设置给RecyclerView
    public static abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
        //可见阀值
        private int visibleThreshold = 5;
        //当前页面page，（在知乎News中可以用currentPage对应每天的数据
        private int currentPage = 0;
        //上次加载后数据集中的项目总数
        private int previousTotalItemCount = 0;
        //是否正在加载数据
        private boolean loading = true;
        //设置开始页面page
        private int startingPageIndex = 0;

//        public EndlessRecyclerViewScrollListener(){}

        RecyclerView.LayoutManager mLayoutManager;

        public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
            this.mLayoutManager = layoutManager;
        }

        public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager) {
            this.mLayoutManager = layoutManager;
            visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
        }

        public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager) {
            this.mLayoutManager = layoutManager;
            visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
        }

        public int getLastVisibleItem(int[] lastVisibleItemPositions) {
            int maxSize = 0;
            for (int i = 0; i < lastVisibleItemPositions.length; i++) {
                if (i == 0) {
                    maxSize = lastVisibleItemPositions[i];
                }
                else if (lastVisibleItemPositions[i] > maxSize) {
                    maxSize = lastVisibleItemPositions[i];
                }
            }
            return maxSize;
        }

        // This happens many times a second during a scroll, so be wary of the code you place here.
        // We are given a few useful parameters to help us work out if we need to load some more data,
        // but first we check if we are waiting for the previous load to finish.
        @Override
        public void onScrolled(RecyclerView view, int dx, int dy) {
            int lastVisibleItemPosition = 0;
            int totalItemCount = mLayoutManager.getItemCount();

            if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
                // get maximum element within the list
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
            } else if (mLayoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            } else if (mLayoutManager instanceof LinearLayoutManager) {
                lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            }

            // If the total item count is zero and the previous isn't, assume the
            // list is invalidated and should be reset back to initial state
            if (totalItemCount < previousTotalItemCount) {
                this.currentPage = this.startingPageIndex;
                this.previousTotalItemCount = totalItemCount;
                if (totalItemCount == 0) {
                    this.loading = true;
                }
            }
            // If it’s still loading, we check to see if the dataset count has
            // changed, if so we conclude it has finished loading and update the current page
            // number and total item count.
            if (loading && (totalItemCount > previousTotalItemCount)) {
                loading = false;
                previousTotalItemCount = totalItemCount;
            }

            // If it isn’t currently loading, we check to see if we have breached
            // the visibleThreshold and need to reload more data.
            // If we do need to reload some more data, we execute onLoadMore to fetch the data.
            // threshold should reflect how many total columns there are too
            if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
                currentPage++;
                onLoadMore(currentPage, totalItemCount, view);
                loading = true;
            }
        }

        // Call this method whenever performing new searches
        public void resetState() {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = 0;
            this.loading = true;
        }

        // Defines the process for actually loading more data based on page
        public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);
    }




    //RecyclerView上拉加载操作： 图片解析 ![图片](http://imgur.com/6E7X1pr.png "")
    //用于设置给RecyclerView
    public static abstract class EndlessScrollListener implements AbsListView.OnScrollListener{
        //可见阀值
        private int visibleThreshold = 5;
        //当前页面page，（在知乎News中可以用currentPage对应每天的数据
        private int currentPage = 0;
        //上次加载后数据集中的项目总数
        private int previousTotalItemCount = 0;
        //是否正在加载数据
        private boolean loading = true;
        //设置开始页面page
        private int startingPageIndex = 0;

        public EndlessScrollListener(){}

        public EndlessScrollListener(int visibleThreshold){
            this.visibleThreshold = visibleThreshold;
        }

        public EndlessScrollListener(int visibleThreshold, int startPage){
            this.visibleThreshold = visibleThreshold;
            this.startingPageIndex = startPage;
            this.currentPage = startPage;
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            //totalItemCount小于上次数据集中的数量（totalItemCount数量为0而上次数据集中不为零）
            //让该list无效，并设置其回到初始化状态
            if (totalItemCount < previousTotalItemCount){
                this.currentPage = this.startingPageIndex;
                this.previousTotalItemCount = totalItemCount;
                if (totalItemCount == 0){
                    this.loading = true;
                }
            }

            // If it's still loading, we check to see if the dataset count has
            // changed, if so we conclude(得出结论) it has finished loading and update the current page
            // number and total item count.
            if (loading && (totalItemCount > previousTotalItemCount)) {
                loading = false;
                previousTotalItemCount = totalItemCount;
                currentPage++;
            }

            // If it isn't currently loading, we check to see if we have breached
            // the visibleThreshold（超过了阀值） and need to reload more data.
            // If we do need to reload some more data, we execute onLoadMore to fetch the data
            // （如果需要加载数据，则调用onLoadMore进行加载）.
            if (!loading && (firstVisibleItem + visibleItemCount + visibleThreshold) >= totalItemCount ) {
                loading = onLoadMore(currentPage + 1, totalItemCount);
            }

        }

        //加载更多数据
        //Returns true if more data is being loaded; returns false if there is no more data to load.
        public abstract boolean onLoadMore(int page,int totalItemsCount);

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }
    }
}
