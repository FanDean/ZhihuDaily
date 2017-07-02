package com.fandean.zhihudaily.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fandean.zhihudaily.R;
import com.fandean.zhihudaily.adapter.ViewPagerAdapter;
import com.fandean.zhihudaily.db.MyBaseHelper;
import com.fandean.zhihudaily.util.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    public static final String NIGHT_PREFERENCE_FILE_NAME = "night.preference.file.name";
    public static final String NIGHT_PREFERENCE_KEY = "night_preference_key";
    public static SQLiteDatabase mdb;
    public static final String FAN_DEAN = "知乎Daily： FanDean";
    public static boolean isNightTheme = false;

    @BindView(R.id.toolbar_main)
    Toolbar mToolbarMain;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager_main)
    ViewPager mViewpager;
    @BindView(R.id.fab_main)
    FloatingActionButton mFabMain;
    @BindView(R.id.coordinator_main)
    CoordinatorLayout mCoordinatorMain;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private ViewPagerAdapter mPagerAdapter;
    private ZhihuFragment mZhihuFragment;
    private DoubanFragment mDoubanFragment;
    private FragmentManager mFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mToolbarMain = (Toolbar) findViewById(R.id.toolbar_main);
        //替换actionbar，为toolbar，
        //对Toolbar的修改必须要在替换actionbar方法之前, 否则会导致某些属性修改无效, 例如setTitle()标题设置
        setSupportActionBar(mToolbarMain);

        mdb = new MyBaseHelper(this).getWritableDatabase();

        mFabMain = (FloatingActionButton) findViewById(R.id.fab_main);
        mFabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                DatePicerFragment dialog = new DatePicerFragment();
                dialog.setTargetFragment(mZhihuFragment,REQUEST_DATE);
                //将该fragment添加给FragmentManager管理并放置在屏幕上，且为其添加tag
                dialog.show(manager,DIALOG_DATE); //tag
            }
        });


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbarMain, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavView = (NavigationView) findViewById(R.id.nav_view);
        mNavView.setNavigationItemSelectedListener(this);

        mViewpager = (ViewPager) findViewById(R.id.viewpager_main);
        setupViewPager(mViewpager);

        //经常性，由于xml中使用是非支持库版本的TabLayout而导致这里转换出错
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        //由于没有创建水平布局，在这里设置一下标签页（但是没用）
        //显示模式固定
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //对齐方式填满
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setupWithViewPager(mViewpager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //当tab被选中时
                if (tab.getPosition() == 0){
                    //隐藏并不保留View所占用的空间
                    mFabMain.setVisibility(View.VISIBLE);
                }
//                Log.d(FAN_DEAN,"TabLayout.getTabGravity(): GRAVITY_FILL = 0；" + mTabLayout.getTabGravity());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //当tab未被选中时
                if (tab.getPosition() == 0){
                    mFabMain.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //当tab重新被选中时
                if (tab.getPosition() == 0){
                    mFabMain.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        mFragmentManager = getSupportFragmentManager();
        mZhihuFragment = new ZhihuFragment();
        mDoubanFragment = new DoubanFragment();
        mPagerAdapter = new ViewPagerAdapter(mFragmentManager);
        mPagerAdapter.addFragment(mZhihuFragment, "知乎日报");
        mPagerAdapter.addFragment(mDoubanFragment, "今日电影");
        viewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //对返回值的描述：You must return true for the menu to be displayed; if you return false it will not be shown.
//        return true;
        //如果需要不显示菜单，可以移除该方法或直接返回false
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home){

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // 选择知乎标签页进行显示
            TabLayout.Tab tab = mTabLayout.getTabAt(0);
            tab.select();
        } else if (id == R.id.nav_bookmarks) {
            //打开收藏夹
            Intent intent = new Intent(this,CollectionActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_setting) {
            //打开设置界面
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_share) {
            Utility.shareApp(this);
        } else if (id == R.id.nav_style) {
            setTheme();
            //重建Activity，使主题生效
            this.recreate();
        }

        //统一在此关闭抽屉
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setTheme() {
        SharedPreferences preferences = getSharedPreferences(NIGHT_PREFERENCE_FILE_NAME,MODE_PRIVATE);
        isNightTheme = preferences.getBoolean(NIGHT_PREFERENCE_KEY,false);
        if (isNightTheme){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            //设置夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        isNightTheme = !isNightTheme;
        preferences.edit().putBoolean(NIGHT_PREFERENCE_KEY,isNightTheme).apply();
        //设置动画优化过度界面
//            getWindow().setWindowAnimations(R.style.Window);
    }

}
