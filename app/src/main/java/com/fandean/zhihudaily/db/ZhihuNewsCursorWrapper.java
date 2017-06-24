//package com.fandean.zhihudaily.db;

/**
 * Created by fan on 17-6-19.
 */
//
//public class ZhihuNewsCursorWrapper extends CursorWrapper {
//    SQLiteDatabase mDatabase;
//    /**
//     * Creates a cursor wrapper.
//     *
//     * @param cursor The underlying cursor to wrap.
//     */
//    public ZhihuNewsCursorWrapper(Cursor cursor,SQLiteDatabase db) {
//        super(cursor);
//        mDatabase = db;
//    }
//
//    public ZhihuNews getZhihuNews(){
////        String jsonStr = getString(getColumnIndex(ZhihuNewsDbSchema.ZhihuNewses.Cols.JSON));
//        byte[] jsonBytes = getBlob(getColumnIndex(ZhihuNewsDbSchema.ZhihuNewses.Cols.JSON));
//        String jsonStr = new String(jsonBytes, Charset.forName("UTF-8"));
//
//        long createTime = getLong(getColumnIndex(ZhihuNewsDbSchema.ZhihuNewses.Cols.CREATETIME));
//        Log.e("FanDean",jsonStr);
//        //不应该在此做
////        if (Utility.isExpired(createTime)){
////            //如果已经过期，删除该行，返回null
////            deleteZhihuNews(createTime);
////            Date date = new Date(createTime);
////            Log.d("FanDean","从数据库获取的ZhihuNews已经过期，其创建时间为：" + date.toString());
////            return null;
////        }
////        Log.d("FanDean","从数据库获取ZhihuNews成功");
//        Gson gson = new Gson();
//        return gson.fromJson(jsonStr,ZhihuNews.class);
//    }
//
//    //内存中没法删除，没法判断是否已经过期，模型bean没有处理好
//    private synchronized void deleteZhihuNews(long create_time){
//        mDatabase.delete(ZhihuNewsDbSchema.ZhihuNewses.NAME,
//                ZhihuNewsDbSchema.ZhihuNewses.Cols.NEWSDATE + " = ?",new String[]{Long.toString(create_time)});
//    }
//
//
//
//}
