package com.fandean.zhihudaily.bean;

/**
 * 代表收藏
 * Created by fan on 17-6-19.
 */

public class Collection {
    public static final boolean ZHIHU = true; //1
    public static final boolean DOUBN = false; //0

    //值知乎或豆瓣对应的id值
    private int id;
    //int类型 0代表知乎，1代表豆瓣
    private boolean type;
    //详细信息的url，用于跳转到详情页面
    private String url;
    //以下为显示信息
    //标题
    private String titel;
    //只保存一条图片地址
    private String imageurl;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type==1;
    }
    public void setType(boolean type){this.type = type;}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
