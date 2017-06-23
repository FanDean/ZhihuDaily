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
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fandean.zhihudaily.R;
import com.fandean.zhihudaily.bean.ZhihuStory;
import com.fandean.zhihudaily.util.HttpUtil;
import com.fandean.zhihudaily.util.MyApiEndpointInterface;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.fandean.zhihudaily.R.id.fab;

public class ZhihuActivity extends AppCompatActivity {
    private static final String EXTRA_ID = "com.fandean.zhihudaily.newid";
    @BindView(R.id.content_image)
    ImageView mImageView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.webview)
    WebView mWebview;
    @BindView(fab)
    FloatingActionButton mFab;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    private int mId;


    private Retrofit mRetrofit;
    private MyApiEndpointInterface mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu);
        ButterKnife.bind(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mId = getIntent().getIntExtra(EXTRA_ID, 0);

        mClient = HttpUtil.getRetrofitClient(this,HttpUtil.ZHIHU_BASE_URL);

        setupWebView();


        mFab = (FloatingActionButton) findViewById(fab);
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
        fetchZhihuStory();
    }


    private void fetchZhihuStory() {
//        mRetrofit = new Retrofit.Builder() //新建一个构建器来设置参数
//                .baseUrl("https://news-at.zhihu.com/api/4/news/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build(); //构建器的此方法生成Retrofit对象
//        mClient = mRetrofit.create(MyApiEndpointInterface.class);

        Call<ZhihuStory> call = mClient.getZhihuStory(mId);
        call.enqueue(new Callback<ZhihuStory>() {
            @Override
            public void onResponse(Call<ZhihuStory> call, Response<ZhihuStory> response) {
                if (response.body() == null) {
                    try {
                        Log.e("FanDean", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                ZhihuStory story = response.body();

                Glide.with(ZhihuActivity.this)
                        .load(story.getImage())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mImageView);

                //使用该方法加载中文页面会乱码
//                mWebview.loadData(story.getBody(),"text/html","utf-8");
                mWebview.loadDataWithBaseURL(null,story.getBody(),"text/html","utf-8",null);
//                Log.d("FanDean","成功获取页面： " + story.getTitle());
                //隐藏并不保留progressBar占用的空间
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ZhihuStory> call, Throwable t) {

            }
        });
    }


    public static Intent newIntent(Context pakageContext, int id) {
        Intent i = new Intent(pakageContext, ZhihuActivity.class);
        i.putExtra(EXTRA_ID, id);
        return i;
    }
}
