package com.fandean.zhihudaily.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fandean.zhihudaily.R;
import com.fandean.zhihudaily.adapter.DoubanAdapter;
import com.fandean.zhihudaily.bean.DoubanMovieInTheaters;
import com.fandean.zhihudaily.util.HttpUtil;
import com.fandean.zhihudaily.util.MyApiEndpointInterface;
import com.fandean.zhihudaily.util.NetworkState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoubanFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    public static final String FAN_DEAN = MainActivity.FAN_DEAN;
    @BindView(R.id.douban_recyclerview)
    RecyclerView mDoubanRecyclerview;
    @BindView(R.id.douban_swiprefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    Unbinder unbinder;
    private final int COLUMN = 2;

    private DoubanMovieInTheaters mMovieInTheaters;
    private List<DoubanMovieInTheaters.SubjectsBean> mMovieSubjects = new ArrayList<>();
    private MyApiEndpointInterface mClient;

    private DoubanAdapter mAdapter;

    public DoubanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_douban, container, false);
        unbinder = ButterKnife.bind(this, view);


        mClient = HttpUtil.getRetrofitClient(getActivity(),HttpUtil.DOUBSN_BASE_URL);


        mAdapter = new DoubanAdapter(mMovieSubjects,getActivity());
        if (COLUMN > 1){
            GridLayoutManager manager = new GridLayoutManager(getActivity(),COLUMN);
            mDoubanRecyclerview.setLayoutManager(manager);
        } else {
            mDoubanRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mDoubanRecyclerview.setAdapter(mAdapter);

        //设置颜色
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //记得设置监听器，否则不会调用Refresh()
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                fetchDoubanMovieList();
            }
        });

        return view;
    }

    private void fetchDoubanMovieList(){
        //city=北京&start=0&count=100
        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("city","北京");
        queryMap.put("start","0");
        queryMap.put("count","100");
        Call<DoubanMovieInTheaters> call = mClient.getDoubanMovieInTheaters(queryMap);
        call.enqueue(new Callback<DoubanMovieInTheaters>() {
            @Override
            public void onResponse(Call<DoubanMovieInTheaters> call, Response<DoubanMovieInTheaters> response) {
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

                mMovieInTheaters = response.body();
//                mMovieSubjects.clear();
//                mMovieSubjects.addAll(0,mMovieInTheaters.getSubjects());
//                mAdapter.notifyItemInserted(0);
                mAdapter.clear();
                mAdapter.addAll(mMovieInTheaters.getSubjects());

                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<DoubanMovieInTheaters> call, Throwable t) {
                Log.e(FAN_DEAN,"获取Douban数据，onFailure()");
                refreshFail();
            }
        });
    }

    private void refreshFail(){
        NetworkState state = new NetworkState(getActivity());
        if (state.isNetWorkConnected()){
            Toast.makeText(getActivity(),"获取数据失败",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(),"请检查网络连接",Toast.LENGTH_SHORT).show();
            //不保存历史数据
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Log.d(FAN_DEAN,"豆瓣列表的Fragment的onDestroyView()");
    }

    @Override
    public void onRefresh() {
        fetchDoubanMovieList();
    }
}
