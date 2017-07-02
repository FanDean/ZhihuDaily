package com.fandean.zhihudaily.ui;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fandean.zhihudaily.R;
import com.fandean.zhihudaily.adapter.ZhihuAdapter;
import com.fandean.zhihudaily.bean.ZhihuNews;
import com.fandean.zhihudaily.db.MyBaseHelper;
import com.fandean.zhihudaily.util.DateUtil;
import com.fandean.zhihudaily.util.HttpUtil;
import com.fandean.zhihudaily.util.MyApiEndpointInterface;
import com.fandean.zhihudaily.util.NetworkState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhihuFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final int REQUEST_DATE = 0;
    public static final String FAN_DEAN = MainActivity.FAN_DEAN;

    @BindView(R.id.zhihu_swiprefresh) SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.zhihu_recyclerview) RecyclerView mRecyclerView;
    Unbinder mUnbinder;
    private ZhihuAdapter mAdapter;
    private List<ZhihuNews> mZhihuNewsList = new ArrayList<>();
    private List<ZhihuNews.StoriesBean> mStoriesBeanList = new ArrayList<>();
    private SQLiteDatabase mdb;
    MyApiEndpointInterface mClient;

    private static String sLatestDate = DateUtil.getCurrentTimeString(DateUtil.ZHIHU_DATE_FORMAT);
    private static String sOldDate = sLatestDate;

    public ZhihuFragment() {
        // Required empty public constructor。为什么？
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu, container, false);
        mUnbinder = ButterKnife.bind(this,view);

        mdb = new MyBaseHelper(getActivity()).getWritableDatabase();
        //通过设置retainInstance属性为true，来保留fragment实例
        setRetainInstance(true);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        RecyclerView.OnScrollListener scrollListener = new ZhihuAdapter.EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                fetchBeforZhihuNews(page);
            }
        };

        mRecyclerView.addOnScrollListener(scrollListener);
        //添加分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        mAdapter = new ZhihuAdapter(getActivity(),mStoriesBeanList);
        mRecyclerView.setAdapter(mAdapter);


        mClient = HttpUtil.getRetrofitClient(getActivity().getApplicationContext(),
                HttpUtil.ZHIHU_BASE_URL);


        //设置颜色
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRefreshLayout.setOnRefreshListener(this);
        //下拉刷新
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
                fetchLatestZhihuNews();
            }
        });
        return view;
    }

    /**
     * 获取最新的知乎日报
     * 无需检测数据是否过期
     * 直接从此获取最新消息即可：https://news-at.zhihu.com/api/4/news/latest
     */
    private void fetchLatestZhihuNews() {
        mRefreshLayout.setRefreshing(true);
        Log.d(FAN_DEAN, "latest mclient: " + mClient.toString());
        Call<ZhihuNews> call = mClient.getLatestZhihuNews();

        call.enqueue(new Callback<ZhihuNews>() {
            //以下两个方法已经回到UI线程中执行
            @Override
            public void onResponse(Call<ZhihuNews> call, Response<ZhihuNews> response) {
                //onResponse总是会被调用，需进行如下判断
                if (response.body() == null) {
                    try {
                        Log.e(FAN_DEAN,response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    refreshFail();
                    return;
                }

                ZhihuNews zhihuNews = response.body();
                //刷新成功
                Log.d(FAN_DEAN, "获取最新知乎数据：" + zhihuNews.getDate());
                mAdapter.clear();
                mAdapter.addAll(zhihuNews.getStories());
                //停止刷新
                mRefreshLayout.setRefreshing(false);
                sLatestDate = DateUtil.getCurrentTimeString(DateUtil.ZHIHU_DATE_FORMAT);
                sOldDate = sLatestDate;
            }

            @Override
            public void onFailure(Call<ZhihuNews> call, Throwable t) {
                //call在这里又有何用，Throwable 如何使用
                refreshFail();
            }
        });
    }

    //刷新失败
    private void refreshFail(){
        NetworkState state = new NetworkState(getActivity());
        if (state.isNetWorkConnected()){
            Toast.makeText(getActivity(),"获取数据失败",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(),"请检查网络连接",Toast.LENGTH_SHORT).show();

        }
        mRefreshLayout.setRefreshing(false);
    }


    /*
     * 下拉，获取非最新日期
     */
    private void fetchAfterZhihuNews(){
        mRefreshLayout.setRefreshing(true);
        Call<ZhihuNews> call = mClient.getBeforeZhihuNews(DateUtil.dateOnePlus(sLatestDate));

        call.enqueue(new Callback<ZhihuNews>() {
            //以下两个方法已经回到UI线程中执行
            @Override
            public void onResponse(Call<ZhihuNews> call, Response<ZhihuNews> response) {
                //onResponse总是会被调用，需进行如下判断
                if (response.body() == null) {
                    try {
                        Log.e(FAN_DEAN,response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    refreshFail();
                    return;
                }

                ZhihuNews zhihuNews = response.body();

                mAdapter.insertAllToFirst(zhihuNews.getStories());
                //滚动列表，垂直方向下移90
//                mRecyclerView.scrollBy(0,90);
                //只有LinearLayoutManager才可
//                LinearLayoutManager linearLayoutManager=
//                (LinearLayoutManager)mRecyclerView.getLayoutManager();
//                int last = linearLayoutManager.findLastCompletelyVisibleItemPosition();
//                int first = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
////                mRecyclerView.scrollToPosition(last);
                int size = zhihuNews.getStories().size();
                mRecyclerView.scrollToPosition(size-2);
                //下拉要保证 sLatestDate 递增
                Log.d(FAN_DEAN,"下拉： sLatestDate = " + sLatestDate  + "  返回的数据中的日期: " + zhihuNews.getDate());
                sLatestDate = DateUtil.dateOnePlus(sLatestDate);
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ZhihuNews> call, Throwable t) {
                //call在这里又有何用，Throwable 如何使用
                refreshFail();
            }
        });
    }



    /**
     * 上拉
     * 获取往日知乎日报，传入相对于当天的偏移量
     * （注意：在每天0点的时候，当天可能并没有数据产生，获取的数据可能是昨天的）
     * 上拉：附加数据到链表，并提示数据更新
     * offset为正数
     */
    private void fetchBeforZhihuNews(int offset){
        String date = sOldDate;
        Log.d(FAN_DEAN, "往日知乎日报，日期： " + date + " -1天\n偏移量offset：" + offset);

        Call<ZhihuNews> call = mClient.getBeforeZhihuNews(date);

        call.enqueue(new Callback<ZhihuNews>() {
            @Override
            public void onResponse(Call<ZhihuNews> call, Response<ZhihuNews> response) {
                if (response.body() == null) {
                    try {
                        Log.e(FAN_DEAN,response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    refreshFail();
                    return;
                }
                ZhihuNews zhihuNews = response.body();

                mAdapter.appendAll(zhihuNews.getStories());
                //更新时间
                sOldDate = zhihuNews.getDate();
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ZhihuNews> call, Throwable t) {
                refreshFail();
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        Log.d(FAN_DEAN,"ZhihuFragment onDestroyView() \n");
    }

    @Override
    public void onRefresh() {
        if (sLatestDate.equals(DateUtil.getCurrentTimeString(DateUtil.ZHIHU_DATE_FORMAT))){
            fetchLatestZhihuNews();
            Log.d(FAN_DEAN,"下拉获取最新数据");
        } else {
            Log.d(FAN_DEAN,"下拉获取往日数据");
            fetchAfterZhihuNews();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_DATE){
            GregorianCalendar calendar = (GregorianCalendar) data.getSerializableExtra(DatePicerFragment.EXTRA_DATE);
            fetchBeforZhihuNews(calendar);
        }
    }


    /**
     *  获取某日的知乎数据
     *  清除当前List
     *  更新两个全局变量
     */
    private void fetchBeforZhihuNews(final GregorianCalendar calendar) {
        final String dateStr = DateUtil.calendarToStr(calendar, DateUtil.ZHIHU_DATE_FORMAT);
        Log.d(FAN_DEAN, "DataPicer选择的日期：" + dateStr);
        //如果选择的是当天
        if (calendar.compareTo(GregorianCalendar.getInstance()) == 0){
                //获取最新数据
                fetchLatestZhihuNews();
                return;
            }

            //改变主题，重建Activity后，点击选择日期，mClient居然会变为null
        if (mClient == null){
//                mClient = HttpUtil.getRetrofitClient(getActivity(),
//                        HttpUtil.ZHIHU_BASE_URL);
                return;
            }
        Log.d(FAN_DEAN, "mclient: " + mClient.toString());

        Call<ZhihuNews> call = mClient.getBeforeZhihuNews(DateUtil.dateOnePlus(dateStr));

        call.enqueue(new Callback<ZhihuNews>() {
            @Override
            public void onResponse(Call<ZhihuNews> call, Response<ZhihuNews> response) {
                if (response.body() == null) {
                    try {
                        Log.e(FAN_DEAN, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    refreshFail();
                    return;
                }
                ZhihuNews zhihuNews = response.body();
                mAdapter.clear();
                mAdapter.addAll(zhihuNews.getStories());
                sLatestDate = DateUtil.dateOnePlus(zhihuNews.getDate());
                sOldDate = dateStr;
                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ZhihuNews> call, Throwable t) {
                refreshFail();
            }
        });
//    }
    }

}
