package com.fandean.zhihudaily.db;

/**
 * 用于存储ZhihuNews的单例
 * Created by fan on 17-6-17.
 */

//public class ZhihuNewsLab {
//    private Context mContext;
//    private SQLiteDatabase mDatabase;
//
//    private static long sRefreshTime;
//
//    //目的是作为上拉加载更多的的偏移量
//    //需在下拉刷新和选择日期并成功获取数据后维护该值
//    private static long sBaseTime = GregorianCalendar.getInstance().getTimeInMillis();
//
//    public static long getBaseTime() {
//        return sBaseTime;
//    }
//    public static void setBaseTime(long base) {
//        sBaseTime = base;}
//
//
//
//    private static ZhihuNewsLab sZhihuNewsLab;
//
////    private List<ZhihuNews>  mZhihuNewsList;
//
//    //预留Content参数，以备之后使用数据库
//    public synchronized static ZhihuNewsLab get(Context context){
//        if (sZhihuNewsLab == null){
//            sZhihuNewsLab = new ZhihuNewsLab(context);
//        }
//        return sZhihuNewsLab;
//    }
//
//
//    //TODO: 不能为静态方法。无法在其他类中通过创建该类的普通变量，来调用这里的静态方法？？
//    public long getRefreshTime() {
//        return sRefreshTime;
//    }
//
//    public void setRefreshTime(long refreshTime) {
//        sRefreshTime = refreshTime;
//    }
//
//
//
//    private ZhihuNewsLab(Context context){
//        mContext = context.getApplicationContext();
//        mDatabase = new MyBaseHelper(mContext).getWritableDatabase();
//    }
//
//
//    public synchronized void insertZhihuNews(ZhihuNews zhihuNews){
//        //TODO: 数据库插入Json的问题
//        ContentValues values = getContentValues(zhihuNews);
//        //插入之前需删除旧数据 TODO: 必须使用事务，或其他办法保证两者为原子操作
////        DbUtil.deleteZhihuNews(zhihuNews,mDatabase);
//        mDatabase.insert(ZhihuNewses.NAME,null,values);
//        Log.d("FanDean","ZhihuNews插入数据库成功");
//    }
//
//    /**
//     * 获取只包含一个Item的链表
//     * @return 返回只包含当天一个节点的List
//     */
//    public List<ZhihuNews> getZhihuNewsList(){
//        List<ZhihuNews> zhihuNewsList = new ArrayList<>();
//        //TODO: 数据库插入Json的问题
//        ZhihuNews zhihuNews = getZhihuNews(DateUtil.getCurrentTimeString(DateUtil.ZHIHU_DATE_FORMAT));
//        if (zhihuNews == null) return null;
//        zhihuNewsList.add(zhihuNews);
//        return zhihuNewsList;
//    }
//
//    /**
//     * 获取数据库中对应日期下的ZhihuNews
//     * @return
//     */
//    public ZhihuNews getZhihuNews(String dateStr){
//        ZhihuNews zhihuNews;
//
//        Cursor cursor =  queryZhihuNews(mDatabase,new String[]{dateStr});
//        ZhihuNewsCursorWrapper cursorWrapper = new ZhihuNewsCursorWrapper(cursor,mDatabase);
//
//        if(cursorWrapper.moveToFirst()){
//            zhihuNews = cursorWrapper.getZhihuNews();
//            return zhihuNews;
//        }
//        cursorWrapper.close();
//        return null;
//    }


//}
