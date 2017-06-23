# ZhihuDaily项目笔记

## 问题汇总

- 检测List是否为空，每次检测都不为null，（该list中嵌套了一个子list）
因为该list中每次都使用add()添加了一个由new ArrayList<>()创建的子list。
- 将ZhihuNews对象转换为Json然后存入数据库（因为bean没有设计好）时，不知是由于json字符串太长，
还是因为json中存在特殊字符？
之后采用如下方法进行转换，想以数据库中的BLOB，但还是不行，获取数据时没有反应。（由于手机没有root，不知道是否插入了数据）
  ```
          String json = gson.toJson(zhihuNews);
          //String -> byte[]
          byte[] jsonBytes = json.getBytes(Charset.forName("UTF-8"));
          //byte[] -> String
          String str = new String(jsonBytes,Charset.forName("UTF-8"));
  ```
- 缓存问题。头疼。
- 定义field变量， private SQLiteDatabase mdb = new MyBaseHelper(this).getWritableDatabase();并且直接初始化。
程序异常退出，因为此时传入的this（即当前Activity）还未创建。
- 从豆瓣列表中打开的Activity中返回到知乎日报列表后知乎Fragment中的列表变空，并且刷新失效，
但可以通过按返回键退出程序，再次进入时可以重新显示知乎列表。
- 切换为横屏后，同样知乎Fragment中的列表变空，而豆瓣却可以显示。
- 对知乎日报的API分析的不够透彻；原来当天的信息量会慢慢增长的，也就是每次更新后获取到的当天的list中节点数是会增加的。
并且详情页中的内容都会改变。
- 判断数据表查询返回的游标中是否有数据和使用何种形式的代码来确保游标的关闭。
- RecyclerView中的包含CardView的item的点击事件的处理。
- RecyclerView的深入学习。

- TabLayout切换监听
- WebView状态的保存和恢复：[Android Fragment使用(三) Activity, Fragment, WebView的状态保存和恢复](http://www.cnblogs.com/mengdd/p/5582244.html "Android Fragment使用(三) Activity, Fragment, WebView的状态保存和恢复 - 圣骑士wind - 博客园")



viewpager.setOnPageChangeListener()，从而使得在你应用中如果想监听ViewPager的页面状态改变

RecyclerView设置分隔线：可以给Item的布局去设置margin，当然了这种方式不够优雅，
我们文章开始说了，我们可以自由的去定制它，当然我们的分割线也是可以定制的。

## FloatingActionButton

|||
| --------- | ---------------------------- |
|Position位置	|You can position the floating button by using `layout_gravity` attribute；还可添加`layout_anchor`来将锚定到你想要的View上 |
|Size大小	|FAB supports two sizes ‘normal‘ and ‘mini‘. You can define the size of the button by using `app:fabSize` attribute |
|Background Color颜色	|By default, fab takes `colorAccent` as background color（. If you want to change the background of fab, use `app:backgroundTint` attribute to define your own background color |
| 涟漪（ripple）交互效果 |`android:clickable="true"` 当你使用了backgroundTint属性来改变系统的背景红色，一定要记得设置clickable属性为true，否则也是无法产生涟漪（ripple）交互效果的|
|设置边距 | 底部推荐为 16dp `android:layout_margin="16dp"`|

[Floating Action Buttons](https://guides.codepath.com/android/floating-action-buttons "Floating Action Buttons | CodePath Android Cliffnotes")
FloatingActionButtons在滑动时的隐藏效果，需要进行自定义。

Background Color颜色,不建议直接使用一般的颜色值，这个属性的值是一个ColorStateList类型 [ColorStateList官网介绍](https://developer.android.com/reference/android/content/res/ColorStateList.html "官网介绍")）


## RecyclerView

通知在列表的末尾添加了插入了多个项目：
```
int curSize = adapter.getItemCount();
//新的items位于 newItems这个链表中
contacts.addAll(newItems);
//通知该范围内数据已经变更
adapter.notifyItemRangeInserted(curSize, newItems.size());
```

通知item数据变更不会滚动到相应位置，可以调用RecyclerView的 scrollToPosition() 进行滚动

> 排序现有列表，见codepath的介绍

### 自定义分割线

使用Android自带的分割线：
```
//对于垂直列表可以使用如下DividerItemDecoration
mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
//对于水平列表可以使用
mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.HORIZONTAL));
```

CodePath中介绍了一个源代码示例，该示例在鸿祥的[Android RecyclerView 使用完全解析 体验艺术般的控件](http://blog.csdn.net/lmj623565791/article/details/45059587 "Android RecyclerView 使用完全解析 体验艺术般的控件 - Hongyang - 博客频道 - CSDN.NET")
处有讲解。


### RecycleView无限滚动（上拉加载）

实现滚动所需信息： **列表中最后一个可见Item** 和 **阀值**（以便在到达最后一个item之前开始获取更多数据）

![图片](http://imgur.com/6E7X1pr.png "")


问题：

In order for the pagination system to continue working reliably, you should make sure to clear the adapter of items (or notify adapter after clearing the array) before appending new items to the list.

如果要清除数据，则必须保证在清除工作在追加数据之前完成。

> 比如在选择观看某天的日报时，如果此时列表中已经加载了很多天的的项目，清除工作需要很久才能完成。这时可能会出现问题。还没有发现。

> 我的一个问题是，在选择了日期后，... 无法上拉加载更多。而重启app后又可以加载。后来又没有重现该问题。

上拉加载的日期参考值是当天，导致重选日期后加载的数据是从当天的第二天开始。


### RecycleView其他

- 为Item按下时显示一个“选择”的效果，
我们可以item的布局文件中为根布局设置`android:background`的属性值为`?android:attr/selectableItemBackground`


## SwipeRefreshLayout

[Implementing Pull to Refresh Guide](https://guides.codepath.com/android/Implementing-Pull-to-Refresh-Guide#troubleshooting "Implementing Pull to Refresh Guide | CodePath Android Cliffnotes")

与RecyclerView配合使用，在Adapter中添加如下方法：
```
/* Within the RecyclerView.Adapter class */

// Clean all elements of the recycler
public void clear() {
    items.clear();
    notifyDataSetChanged();
}

// Add a list of items -- change to type used
public void addAll(List<Tweet> list) {
    items.addAll(list);
    notifyDataSetChanged();
}
```


Troubleshooting 故障排查：

- Activity的setContentView()必须在onCreate()方法的第二行调用
- 在数据加载完成后（加载到list后）**才**调用setRefreshing()。
- 如果使用`CoordinatorLayout` to manage scrolling，确保将`app:layout_behavior="@string/appbar_scrolling_view_behavior"` 设置给SwipeRefreshLayout而不是它里面的RecyclerView。
- 更新列表之前先清除旧的items。



## CardView

TODO CardView的点击效果还没有实现。


## 日期的格式化

被一个问题困扰

```
GregorianCalendar todayCalendar = new GregorianCalendar();
todayCalendar.setTimeInMillis(ZhihuNewsLab.get(getActivity()).getToday());
todayCalendar.add(Calendar.DAY_OF_MONTH,-offset);

todayCalendar.get(Calendar.YEAR);
//获取到的月份是没有前导0的，并且是从0开始算起，比如6月它返回的是5，而不是05或6或06。
todayCalendar.get(Calendar.MONTH);
todayCalendar.get(Calendar.DAY_OF_MONTH);

```




## ViewPager

ViewPager的存在原因，《Android权威编程指南》。


RecyclerView与ViewPager的对比：



FragmentStatePagerAdapter 和 FragmentPagerAdapter的区别：
唯一的区别在于，卸载不再需要的fragment时，各自采用的处理方法有所不同。

|FragmentStatePagerAdapter| FragmentPagerAdapter |
|----------------|-----------------|
|会销毁不需要的Fragment|  fragment永远不会被销毁，调用事务的detach()方法来处理它，这样只是销毁Fragment的视图但fragment实例还存在于FragmentManager中 |
|在销毁fragment时可以调用onSaveInstanceState()方法保存信息 | 没有被销毁不必保存 |
| 多个fragment |适用于使用少量固定的fragment|



RecyclerView的Adapter与ViewPager的PagerAdapter的对比：

```
                             {   instantiateItem()
 onBindViewHolder()        --    destroyItem()
                             {   isViewFromObject()
```

isViewFromObject()方法的具体实现： （一行代码）



## Menu菜单

《Android权威编程指南》

### Activity中的菜单

Activity类提供了管理菜单的回到函数。 **需要选项菜单时，**Android会调用Activity的onCreateOptionMenu(Menu)方法。



### Fragment中的菜单

Fragment有一套自己的选项菜单回调函数。


两个回调方法：

- 创建菜单：  `onCreateOptionsMenu()`
- 响应菜单项选择事件： `onOptionsItemSelected()`

`Fragment.onCreateOptionsMenu()`时由FragmentManager负责调用。

使用该`setHasOptionsMenu(boolean hasMenu)`通知FragmentManager，一般在Fragment的onCreate()方法中调用此方法。

回调函数中的`super.***`方法的作用。



### 实现层级式导航

在manifest文件中的子Activity中添加相关属性。

```
android:parentActivityName=".ui.MainActivity"
```


使用toolbar还需要添加如下代码：`getSupportActionBar().setDisplayHomeAsUpEnabled(true);`才能显示该箭头，
然后它就可以自动响应点击事件了。


> 如果在Fragment中呢？


层级式导航的工作原理：

创建Intent，调用startActivity()，然后finish()当前Activity。

层级导航，重建父Activity的问题。而按返回键不会。




## 对话框

对话框都继承至 Dialog 类，而Dialog类直接继承至Object。它与View类没有继承关系。

Dialog并非通过启动一个新Activity实现。

建议将AlertDialog封装在DialogFragment**实例**中使用(理解这句话)。这样可以解决设备配置变更的问题。


## Activity与Fragment之间的实时通信

之前了解的都是在Activity或Fragment在创建或销毁时传递数据的方法，现在要考虑在创建之后如何进行通信。

一般的方法：
```
在activity中获取fragment中的数据

1.通过FragmentManager获取fragment布局
FragmantA fragmantA=(FragmantA) manager.findFragmentById(R.id.fragment1);
2.获取在fragment上的视图
View fragmanView=fragmantA.getView();
3.获取视图上的控件
TextViewtextView=(TextView) fragmanView.findViewById(R.id.frag_tv);
4.更改控件上的数据
textView.setText("hhehehehehheh");
================================================================
在fragment中获取activity中的数据

1. 在Fragment中可以通过getActivity的方法来获取activity中的布局
2.调用Activity中的findViewByBd来获取布局中的控件
TextViewtextView=(TextView) getActivity().findViewById(R.id.main_tv);
3. 改变数据
textView.setText("呵呵呵呵呵呵呵");
```


AlertDialog.Builder. 属于构造器模式用法

Builder是属于AlertDialog的内部类. 负责创建AlertDiglog的构造器. 所以属于链式编程.
正因为是构造器模式, AlertDialog的所有方法你都可以直接忽略, Builder已经实现了所有的功能. 并且AlertDialog是Protected权限无法直接创建对象的.


简单的使用：
```
AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
builder.setTitle("标题");
builder.setMessage("信息主体");
builder.show();
```

详情见印象笔记中的 Android对话框详解

3种用于对话框的按钮：positive按钮(肯定)、negative按钮(否定)、neutral按钮(中立)



### ProgressBar
ProgressBar是作为控件使用的进度条. 且只能写在布局文件中使用. ProgressDialog的实现原理就是创建一个包含了ProgressDialog的View显示.

而INVISIBLE和GONE的主要区别是：当控件visibility属性为INVISIBLE时，界面保留了view控件所占有的空间；而控件属性为GONE时，界面则不保留view控件所占有的空间。

```
//隐藏并不保留progressBar占用的空间
mProgressBar.setVisibility(View.GONE);
```

## Retrofit


自定义Converter来实现更复杂的需求，只需要extends Converter.Factory然后重写


想要使用OkHttp为Retrofit提供更高的定制性，给Retrofit设置自定义的OkHttpClient就可以了

[Retrofit使用指南](http://www.jianshu.com/p/91ac13ed076d "Retrofit使用指南 - 简书")


### (cache)缓存机制

无网络时，也能显示数据

- [Android Retrofit 2.0 使用-补充篇](http://wuxiaolong.me/2016/06/18/retrofits/ "Android Retrofit 2.0 使用-补充篇 | 吴小龙同學")
- [Retrofit2.0+okhttp3缓存机制以及遇到的问题 - Picasso_L的专栏 - 博客频道 - CSDN.NET](http://blog.csdn.net/picasso_l/article/details/50579884 "Retrofit2.0+okhttp3缓存机制以及遇到的问题 - Picasso_L的专栏 - 博客频道 - CSDN.NET")
- [使用Retrofit和Okhttp实现网络缓存。无网读缓存，有网根据过期时间重新请求 - 简书](http://www.jianshu.com/p/9c3b4ea108a7 "使用Retrofit和Okhttp实现网络缓存。无网读缓存，有网根据过期时间重新请求 - 简书")
- [Retrofit2.0+Okhttp不依赖服务端的数据缓存 | 好文](http://dandanlove.com/2016/09/18/retrofit-okhttp-cache-offline/ "Retrofit2.0+Okhttp不依赖服务端的数据缓存 | 下雨天要逛街")



基本原理：

1. 配置Okhttp的Cache
2. 配置请求头中的cache-control或者统一处理所有请求的请求头
3. 云端配合设置响应头或者自己写拦截器修改响应头中cache-control

1. 我们所用的接口服务不支持缓存，所以我不能只修改头信息而让服务端返回的response响应体去实现数据本地缓存。当然在没有网络的情况下我们可以尝试去读取缓存。
2. 因为服务端没有提供response响应体的缓存，所以我们清除response响应体的Pragma、Cache-Control信息，然后根据自己设定的request请求体中的Cache信息去修改response响应体的Cache信息从而达到数据可以缓存。
3. 在开发的过程中遇到如果一个接口在某次请求返回404，那么以后的结果总是请求失败的404页面。所以在请求失败的时候需要初始化OkHttpClient实例。


实现步骤：

1. 开启OKHttp缓存
2. 设置拦截器（缓存）拦截Request
3. 设置Response




## TODO
TODO CardView的点击效果还没有实现。

配置变更后，回到之前的列表位置。

层级导航，重建Activity问题

收藏夹排序问题，数据表中添加自动增长的主键，读取时倒序读取，并在读取时考虑使用分页读取。

豆瓣列表的分页读取，每次读取显示10个项目。



