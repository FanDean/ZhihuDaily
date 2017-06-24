package com.fandean.zhihudaily.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fan on 17-6-18.
 */

public class MyBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "MyBaseHelper";
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "dailyBase.db";


    /*
     * 参数较少的构造函数
     * @param context
     * @param name 数据库名称
     * @param factory 自定义的Cursor，一般传入null
     * @param version 数据库的版本号
     */
//    public MyBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }

    //封装构造函数
    public MyBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }

    //在使用ZhihuBaseHelper对象调用get****Database()方法时如果数据库没有创建，则调用onCreate()方法
    @Override
    public void onCreate(SQLiteDatabase db) {

/*
        db.execSQL("create table " + ZhihuNewsDbSchema.ZhihuNewses.NAME + "(" +
//                        ZhihuNewsDbSchema.ZhihuNewses.Cols.ID + "integer primary key autoincrement" +
                        ZhihuNewsDbSchema.ZhihuNewses.Cols.CREATETIME + " integer primary key," +
                        ZhihuNewsDbSchema.ZhihuNewses.Cols.NEWSDATE + "," +
                        ZhihuNewsDbSchema.ZhihuNewses.Cols.JSON + " blob" +
                ")"
        );

        db.execSQL("create table " + ZhihuNewsDbSchema.ZhihuStories.NAME + "(" +
//                ZhihuNewsDbSchema.ZhihuStories.Cols.ID
                ZhihuNewsDbSchema.ZhihuStories.Cols.CREATETIME + " integer primary key," +
                ZhihuNewsDbSchema.ZhihuStories.Cols.NEWSDATE + "," +
                ZhihuNewsDbSchema.ZhihuStories.Cols.JSON + " blob" +
                ")"
        );
*/


        db.execSQL("create table " + ZhihuNewsDbSchema.Favorites.NAME + "(" +
                ZhihuNewsDbSchema.Favorites.Cols.ID + " integer primary key autoincrement," +
                ZhihuNewsDbSchema.Favorites.Cols.CONTENT_ID + " integer," +
                ZhihuNewsDbSchema.Favorites.Cols.TYPE + " integer," +
                ZhihuNewsDbSchema.Favorites.Cols.URL + "," +
                ZhihuNewsDbSchema.Favorites.Cols.TITEL+ "," +
                ZhihuNewsDbSchema.Favorites.Cols.IMAGEURL +
                ")"
        );


    }

    //在创建ZhihuBaseHelper对象时传入的数据库版本号大于当前版本时，会调用此方法。
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO： 数据库升级
    }
}
