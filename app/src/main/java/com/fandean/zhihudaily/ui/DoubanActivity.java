package com.fandean.zhihudaily.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fandean.zhihudaily.R;
import com.fandean.zhihudaily.bean.DoubanMovie;
import com.fandean.zhihudaily.util.HttpUtil;
import com.fandean.zhihudaily.util.MyApiEndpointInterface;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DoubanActivity extends AppCompatActivity {
    private static final String EXTRA_ID = "com.fandean.zhihudaily.douban_id";
    private static final String EXTRA_TITLE = "com.fandean.zhihudaily.douban_title";
    private static final String EXTRA_IMAGE_URL = "com.fandean.zhihudaily.douban_mageurl";
    @BindView(R.id.content_image)
    ImageView mContentImage;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.webview)
    WebView mWebview;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    private String mId;
    private String mImageUrl;
    private String mTitle = "豆瓣电影";

    private Retrofit mRetrofit;
    private MyApiEndpointInterface mClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (!TextUtils.isEmpty(intent.getStringExtra(EXTRA_TITLE))){
            mId = intent.getStringExtra(EXTRA_ID);
            mImageUrl = intent.getStringExtra(EXTRA_IMAGE_URL);
            mTitle = intent.getStringExtra(EXTRA_TITLE);
        }
        mToolbarLayout.setTitle(mTitle);
        Glide.with(this)
                .load(mImageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mContentImage);

        mClient = HttpUtil.getRetrofitClient(this,HttpUtil.DOUBSN_BASE_URL);

        setupWebView();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setupWebView() {
        WebSettings webSettings = mWebview.getSettings();
        //支持JS
        webSettings.setJavaScriptEnabled(true);
        fetchDoubanMovie();
    }

    private void fetchDoubanMovie(){
        //创建接口实例
        Call<DoubanMovie> call = mClient.getDoubanMovie(Integer.parseInt(mId));
        call.enqueue(new Callback<DoubanMovie>() {
            @Override
            public void onResponse(Call<DoubanMovie> call, Response<DoubanMovie> response) {
                if (response.body() == null) {
                    try {
                        Log.e("FanDean", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                        mProgressBar.setVisibility(View.GONE);
                    }
                    return;
                }

                DoubanMovie movie = response.body();
                mWebview.loadUrl(movie.getMobile_url());
                //隐藏并不保留progressBar占用的空间
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<DoubanMovie> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }




    public static Intent newIntent(Context context, String id, String imageUrl,String title) {
        Intent i = new Intent(context, DoubanActivity.class);
        i.putExtra(EXTRA_ID, id);
        i.putExtra(EXTRA_IMAGE_URL, imageUrl);
        i.putExtra(EXTRA_TITLE,title);
        return i;
    }
}
