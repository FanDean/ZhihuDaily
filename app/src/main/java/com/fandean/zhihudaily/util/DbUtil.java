package com.fandean.zhihudaily.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.fandean.zhihudaily.bean.Collection;
import com.fandean.zhihudaily.db.ZhihuNewsDbSchema;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fan on 17-6-18.
 */
public class DbUtil {

//    SQLiteDatabase mDatabase;
//    Context mContext;
//    public DbUtil(Context context){
//        mContext = context;
//        mDatabase= new MyBaseHelper(mContext.getApplicationContext()).getWritableDatabase();
//    }
//
//    public static ContentValues getContentValues(ZhihuNews zhihuNews){
//        ContentValues values = new ContentValues();
//        GregorianCalendar now = new GregorianCalendar();
//        Gson gson = new Gson();
//        String json = gson.toJson(zhihuNews);
//        byte[] jsonBytes = json.getBytes(Charset.forName("UTF-8"));
////        String str = new String(jsonBytes,Charset.forName("UTF-8"));
//
//        values.put(ZhihuNewses.Cols.CREATETIME,now.getTimeInMillis());
//        values.put(ZhihuNewses.Cols.NEWSDATE,zhihuNews.getDate());
//        //TODO: 数据库插入Json的问题
//        values.put(ZhihuNewses.Cols.JSON, jsonBytes);
//
//        return values;
//    }
//
//    /**
//     * 获取ZhihuStory的ContentValues
//     * @param story
//     * @param dateStr 该story在知乎上的生成日期
//     * @return
//     */
//    public static ContentValues getContentValues(ZhihuStory story, String dateStr){
//        ContentValues values = new ContentValues();
//        GregorianCalendar now = new GregorianCalendar();
//        Gson gson = new Gson();
//        values.put(ZhihuStories.Cols.CREATETIME,now.getTimeInMillis());
//        values.put(ZhihuStories.Cols.NEWSDATE,dateStr);
//        values.put(ZhihuStories.Cols.JSON,gson.toJson(story));
//
//        return values;
//    }
//
//
//
//
//    //返回的Cursor一定要记得关闭
//    public static ZhihuNewsCursorWrapper queryZhihuNews(SQLiteDatabase db,  String[] whereArgs){
//        Cursor cursor = db.query(ZhihuStories.NAME,
//                null, // null表示select *
//                ZhihuNewses.Cols.NEWSDATE + " = ?", //where子句
//                whereArgs,
//                null, //groupBy
//                null, //having
//                null //orderby
//                );
//        return new ZhihuNewsCursorWrapper(cursor,db);
//    }
//
//    //内存中没法删除，没法判断是否已经过期，模型bean没有处理好
//    public static void deleteZhihuNews(ZhihuNews zhihuNews, SQLiteDatabase db){
//        db.delete(ZhihuNewses.NAME,ZhihuNewses.Cols.NEWSDATE + " = ?",new String[]{zhihuNews.getDate()});
//    }
//
//
//    public static void insertZhihuNews(SQLiteDatabase db, ZhihuNews zhihuNews){
//        ContentValues values = getContentValues(zhihuNews);
//        db.insert(ZhihuNewses.NAME,null,values);
//        Log.d("FanDean","ZhihuNews插入数据库成功");
//    }
//
//    //已过期旧更新数据库中的数据
//    //db.update("Book", values, "name = ?", new String[] { "The DaVinci Code" }
//    public static void updateZhihuNews(SQLiteDatabase db,ZhihuNews zhihuNews){
//
//    }
//

    /*
        收藏的数据
     */

    public static ContentValues getContentValues(Collection collection){
        ContentValues values = new ContentValues();
        values.put(ZhihuNewsDbSchema.Favorites.Cols.CONTENT_ID,collection.getId());
        values.put(ZhihuNewsDbSchema.Favorites.Cols.TYPE,collection.getType()?1:0); //boolean转int
        values.put(ZhihuNewsDbSchema.Favorites.Cols.TITEL,collection.getTitel());
        values.put(ZhihuNewsDbSchema.Favorites.Cols.URL,collection.getUrl());
        values.put(ZhihuNewsDbSchema.Favorites.Cols.IMAGEURL,collection.getImageurl());
        return values;
    }

    public static void insertCollection(SQLiteDatabase db,Collection collection){
        ContentValues values = getContentValues(collection);
        db.insert(ZhihuNewsDbSchema.Favorites.NAME,null,values);
    }

    public static void deleteCollection(SQLiteDatabase db,int id){
        String idStr = "" + id;
        db.delete(ZhihuNewsDbSchema.Favorites.NAME,
                ZhihuNewsDbSchema.Favorites.Cols.CONTENT_ID + " = ?", new String[]{idStr});
    }

    public static CollectionCursorWrapper queryCollection(SQLiteDatabase db,String where,String[] whereArg){
        //排序，id递减 order by id desc
        Cursor cursor =   db.query(ZhihuNewsDbSchema.Favorites.NAME,
                null, where, whereArg, null, null,
                        ZhihuNewsDbSchema.Favorites.Cols.ID + " desc");
        return new CollectionCursorWrapper(cursor);
    }

    public static List<Collection> getCollections(SQLiteDatabase db){
        List<Collection> collectionList = new ArrayList<>();
        CollectionCursorWrapper cursorWrapper = queryCollection(db,null,null);
        try{
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                collectionList.add(cursorWrapper.getCollection());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }
        return collectionList;
    }

    public static boolean isCollection(SQLiteDatabase db, int id){
        CollectionCursorWrapper cursor = queryCollection(db,
                ZhihuNewsDbSchema.Favorites.Cols.CONTENT_ID + " = ?",
                new String[] {Integer.toString(id)});
        try{
            if (cursor.getCount() == 0){
                return false;
            }
        }finally {
            cursor.close();
        }
        return true;
    }


    public static class CollectionCursorWrapper extends CursorWrapper {
        public CollectionCursorWrapper(Cursor cursor) {
            super(cursor);
        }
        public Collection getCollection(){
            Collection collection = new Collection();
            collection.setId(getInt(getColumnIndex(ZhihuNewsDbSchema.Favorites.Cols.CONTENT_ID)));
            collection.setType(getInt(getColumnIndex(ZhihuNewsDbSchema.Favorites.Cols.TYPE)));
            collection.setTitel(getString(getColumnIndex(ZhihuNewsDbSchema.Favorites.Cols.TITEL)));
            collection.setUrl(getString(getColumnIndex(ZhihuNewsDbSchema.Favorites.Cols.URL)));
            collection.setImageurl(getString(getColumnIndex(ZhihuNewsDbSchema.Favorites.Cols.IMAGEURL)));
            return collection;
        }
    }

}
