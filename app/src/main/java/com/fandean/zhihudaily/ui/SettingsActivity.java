package com.fandean.zhihudaily.ui;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.fandean.zhihudaily.R;
import com.fandean.zhihudaily.util.CacheUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 为了添加Toolbar，将之前的 public class SettingsActivity extends PreferenceActivity 改为
 */
public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.pref_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        mToolbar.setTitle("设置");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //在这之后才有效
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getFragmentManager().beginTransaction()
                .replace(R.id.pref_fragment_container,new MyPreferenceFragment())
                .commit();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.pref_fragment_container,new MyPreferenceFragment())
//                .commit();
    }



    //如果使用 PreferenceFragmentCompat 界面主题没有弄好，就太丑了
    public static class MyPreferenceFragment extends PreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener {
        private Preference mCityPref;
        private Preference mMovieListPref;
        private Preference mCachePref;
        //        private Preference mHomePagePref;
        private Preference mFeedbackPref;

        private static final int MSG_CLEAR_CACHE_FINISH = 1;
        private final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_CLEAR_CACHE_FINISH:
                        Toast.makeText(getActivity(), "成功清除缓存", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.userpreferences);

            initPreferences();

            //import android.preference.PreferenceManager;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            //注册该监听器
            preferences.registerOnSharedPreferenceChangeListener(this);


            mCityPref.setSummary(preferences.getString(getString(R.string.pref_city_key), "北京"));
            mMovieListPref.setSummary(preferences.getString(getString(R.string.pref_movie_key), "" + 2));

            //点击清除缓存
            mCachePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //清除Glide缓存
                            CacheUtil.clearGlideCache(getActivity());
                            //清除WebView缓存
                            CacheUtil.clearWebWiewDBCache(getActivity());
                            //清除Retrofit缓存
                            CacheUtil.clearOkHttp3Cache(getActivity());

                            handler.sendEmptyMessage(MSG_CLEAR_CACHE_FINISH);
                        }
                    }).start();
                    //设置显示的缓存大小为 0

                    return true;
                }
            });

            mCachePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //清除Glide缓存
                                CacheUtil.clearGlideCache(getActivity());
                                //清除WebView缓存
                                CacheUtil.clearWebWiewDBCache(getActivity());
                                //清除Retrofit缓存
                                CacheUtil.clearOkHttp3Cache(getActivity());

                                handler.sendEmptyMessage(MSG_CLEAR_CACHE_FINISH);
                            }
                        }).start();
                        //设置显示的缓存大小为 0

                        return true;
                }
            });
            //点击feedback
            mFeedbackPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    try {
                        Uri uri = Uri.parse(getString(R.string.pref_feedback_email));
                        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "ZhihuDaily用户反馈");

                        intent.putExtra(Intent.EXTRA_TEXT,
                                "ZhihuDaily用户反馈 \n\n\n\n\n\n\n" + "Build.MODEL = " + Build.MODEL + "\n"
                                        + "Build.VERSION.RELEASE" + Build.VERSION.RELEASE + "\n"
                                        + "客户端版本：" + getString(R.string.pref_version));

                        startActivity(intent);
                    } catch (ActivityNotFoundException ex) {
                        Toast.makeText(getActivity(), "没有发送邮件的应用", Toast.LENGTH_SHORT);
                    }

                    return true;
                }
            });

        }


//        // 如果继承的是 PreferenceFragment ，则下面的onCreatePreferences 中的操作放在 onCreate 中进行
            //import android.support.v7.preference.Preference;
            //import android.support.v7.preference.PreferenceFragmentCompat;

//        @Override
//        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
//            addPreferencesFromResource(R.xml.userpreferences);
//            initPreferences();
//
//
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//            //注册该监听器
//            preferences.registerOnSharedPreferenceChangeListener(this);
//
//
//            mCityPref.setSummary(preferences.getString(getString(R.string.pref_city_key), "北京"));
//            mMovieListPref.setSummary(preferences.getString(getString(R.string.pref_movie_key), "" + 2));
//
//
//            mCachePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//                @Override
//                public boolean onPreferenceClick(Preference preference) {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            //清除Glide缓存
//                            CacheUtil.clearGlideCache(getActivity());
//                            //清除WebView缓存
//                            CacheUtil.clearWebWiewDBCache(getActivity());
//                            //清除Retrofit缓存
//                            CacheUtil.clearOkHttp3Cache(getActivity());
//
//                            handler.sendEmptyMessage(MSG_CLEAR_CACHE_FINISH);
//                        }
//                    }).start();
//                    //设置显示的缓存大小为 0
//                    return true;
//                }
//            });
//
//            mFeedbackPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//                @Override
//                public boolean onPreferenceClick(Preference preference) {
//                    try {
//                        Uri uri = Uri.parse(getString(R.string.pref_feedback_email));
//                        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
//                        intent.putExtra(Intent.EXTRA_SUBJECT, "ZhihuDaily用户反馈");
//
//                        intent.putExtra(Intent.EXTRA_TEXT,
//                                "ZhihuDaily用户反馈 \n\n\n\n\n\n\n" + Build.MODEL + "\n"
//                                        + Build.VERSION.RELEASE + "\n"
//                                        + getString(R.string.pref_version));
//
//                        startActivity(intent);
//                    } catch (ActivityNotFoundException ex) {
//                        Toast.makeText(getActivity(), "没有发送邮件的应用", Toast.LENGTH_SHORT);
//                    }
//
//                    return true;
//                }
//            });
//
//        }


        private void initPreferences() {
            mMovieListPref = findPreference(getString(R.string.pref_movie_key));
            mCachePref = findPreference(getString(R.string.pref_clear_cache_key));
            mCityPref = findPreference(getString(R.string.pref_city_key));
            mFeedbackPref = findPreference(getString(R.string.pref_feedback_key));
        }




        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            //避免在该Fragment还没有附加到Activity上之前执行下面的语句，而造成异常退出
            if (isAdded()) {
                if (key.equals(getString(R.string.pref_movie_key))) {
                    mMovieListPref.setSummary(sharedPreferences.getString(key, "" + 2));
                }
            }
        }
    }
}


