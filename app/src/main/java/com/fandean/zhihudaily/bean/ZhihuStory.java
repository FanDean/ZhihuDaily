package com.fandean.zhihudaily.bean;

import java.util.List;

/**
 * Created by fan on 17-6-17.
 */

public class ZhihuStory {

    /**
     * body : <div class="main-wrap content-wrap">
     <div class="headline">
     <div class="img-place-holder"></div>
     </div>

     <div class="content-inner">

     <div class="question">
     <h2 class="question-title">如何看待现在越来越多的微信用户只展示三天的朋友圈状态？</h2>

     <div class="answer">

     <div class="meta">
     <img class="avatar" src="http://pic2.zhimg.com/51c584fb0918672b70d03e5b32619cbd_is.jpg">
     <span class="author">邹昕，</span><span class="bio">互联网数据分析</span>
     </div>

     <div class="content">
     <p>一点不成熟的想法以抛砖引玉。</p>
     <p>我们先假设这个陈述是对的。</p>
     <p>这可以说是微信或者说几乎所有社交产品都会面临的困局之一，当用户达到一定数量之后，原创内容开始减少。</p>
        ...
     <p><strong>1.3 假如我的好友里，只展示三天朋友圈的人的确越来越多了。</strong></p>
     <p>为什么？我一个一个查过了呀，没毛病了吧？然而，「<strong>我</strong>」作为微信用户之一，可能并不具备代表意义，。</p>
     </div>
     </div>

     <div class="view-more"><a href="http://www.zhihu.com/question/60522025">查看知乎讨论<span class="js-question-holder"></span></a></div>

     </div>


     </div>
     </div>
     * image_source : Public Domain
     * title : 分组分组再分组，算了，朋友圈还是「三天可见」吧
     * image : https://pic2.zhimg.com/v2-10d823ae2d95368bf23d684a9d43edf5.jpg
     * share_url : http://daily.zhihu.com/story/9477262
     * js : []
     * ga_prefix : 061515
     * images : ["https://pic1.zhimg.com/v2-44d997b1f122fe0390643214332edbe8.jpg"]
     * type : 0
     * id : 9477262
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     */

    private String body;
    private String image_source;
    private String title;
    //大图
    private String image;
    private String share_url;
    private String ga_prefix;
    private int type;
    private int id;
    private List<?> js;
    //小图
    private List<String> images;
    private List<String> css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<?> getJs() {
        return js;
    }

    public void setJs(List<?> js) {
        this.js = js;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }
}
