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
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.fandean.zhihudaily.R;
import com.fandean.zhihudaily.bean.Collection;
import com.fandean.zhihudaily.bean.ZhihuStory;
import com.fandean.zhihudaily.util.DbUtil;
import com.fandean.zhihudaily.util.HttpUtil;
import com.fandean.zhihudaily.util.MyApiEndpointInterface;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fandean.zhihudaily.R.id.fab;
import static com.fandean.zhihudaily.ui.MainActivity.FAN_DEAN;
import static com.fandean.zhihudaily.util.HttpUtil.ZHIHU_BASE_URL;

public class ZhihuActivity extends AppCompatActivity {
    private static final String EXTRA_ID = "com.fandean.zhihudaily.newid";
    private static final String EXTRA_TITLE = "com.fandean.zhihudaily.newtitle";
    private static final String EXTRA_IMAGE_URL = "com.fandean.zhihudaily.newimageurl";
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
    private String mTitle = "知乎日报";
    private String mImageUrl;
    private Collection mCollection = new Collection();

    private MyApiEndpointInterface mClient;
    //是否已经收藏
    private static boolean sIsCollected = false;
    //
//    private SharedPreferences mPreferences;
//    private static boolean isNightTheme = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu);
        ButterKnife.bind(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

//        mPreferences = getSharedPreferences(NIGHT_PREFERENCE_FILE_NAME,MODE_PRIVATE);
//        isNightTheme = mPreferences.getBoolean(NIGHT_PREFERENCE_KEY,false);

        //获取传入的数据，设置标题、加载头部图片、mId用于获取新闻
        Intent intent = getIntent();
        if (!TextUtils.isEmpty(intent.getStringExtra(EXTRA_TITLE))){
            mId = intent.getIntExtra(EXTRA_ID, 0);
            mTitle = intent.getStringExtra(EXTRA_TITLE);
            mImageUrl = intent.getStringExtra(EXTRA_IMAGE_URL);
        }
        mCollection.setId(mId);
        mCollection.setTitel(mTitle);
        mCollection.setType(Collection.ZHIHU);
        mCollection.setImageurl(mImageUrl);
        mCollection.setUrl(ZHIHU_BASE_URL);

        mToolbarLayout.setTitle(mTitle);
//        Glide.with(this)
//                .load(mImageUrl)
//                .into(mImageView);
//
        if (sIsCollected = DbUtil.isCollection(MainActivity.mdb,mId)){
//            mFab.setImageState();
            mFab.setImageResource(R.drawable.ic_star_cor_white_200);
            Log.d(MainActivity.FAN_DEAN,"已经收藏");
        }

        mClient = HttpUtil.getRetrofitClient(this,HttpUtil.ZHIHU_BASE_URL);

        setupWebView();


        mFab = (FloatingActionButton) findViewById(fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sIsCollected){
                    //取消收藏
                    mFab.setImageResource(R.drawable.ic_star_cor_grey_200);
                    //从数据库中删除
                    DbUtil.deleteCollection(MainActivity.mdb,mId);
                    sIsCollected = false;
                    Snackbar.make(view, "取消收藏", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else {
                    mFab.setImageResource(R.drawable.ic_star_cor_white_200);
                    //插入数据库
                    DbUtil.insertCollection(MainActivity.mdb,mCollection);
                    sIsCollected = true;
                    Snackbar.make(view, "收藏成功", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }

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
                    mProgressBar.setVisibility(View.GONE);
                    return;
                }
                ZhihuStory story = response.body();


                //优化用户体验，使用Glide的高级略缩图形式
                DrawableRequestBuilder<String> thumbnailRequest = Glide
//                        .with(ZhihuActivity.this)
                        .with(getApplicationContext())
                        .load(mImageUrl);

                Glide
//                        .with(ZhihuActivity.this)
                        .with(getApplicationContext())
                        .load(story.getImage())
                        .thumbnail(thumbnailRequest)
                        .into(mImageView);
                String htmlBody = story.getBody();

                // lets assume we have /assets/style.css file
                String css = "<link rel=\"stylesheet\" type=\"text/css\" href=\"zhihu_daily.css\" />";

                if (MainActivity.isNightTheme){ //应用夜间模式配色
                    String html = "<html><head>" + css + "</head><body class=\"night\">" + htmlBody  + "</body></html>";
                    Log.d(FAN_DEAN,"夜间模式：\n");
                    mWebview.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "UTF-8", null);
                } else { //使用正常配色
                    String html = "<html><head>" + css + "</head><body>" + htmlBody + "</body></html>";
                    Log.d(FAN_DEAN,"正常模式：\n");
                    mWebview.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "UTF-8", null);
                }


                //使用该方法加载中文页面会乱码
//                mWebview.loadData(story.getBody(),"text/html","utf-8");
//                mWebview.loadDataWithBaseURL(null,story.getBody(),"text/html","utf-8",null);
//                Log.d("FanDean","成功获取页面： " + story.getTitle());
                //隐藏并不保留progressBar占用的空间
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ZhihuStory> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public static Intent newIntent(Context pakageContext, int id, String title, String imageUrl) {
        Intent i = new Intent(pakageContext, ZhihuActivity.class);
        i.putExtra(EXTRA_ID, id);
        i.putExtra(EXTRA_TITLE,title);
        i.putExtra(EXTRA_IMAGE_URL,imageUrl);
        return i;
    }
}
