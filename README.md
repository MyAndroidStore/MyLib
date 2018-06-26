# MyLib
> 框架默认集成如下依赖(请勿重复集成)
- NoHttp (网络请求)
- Gson (json工具)
- Luban (图片压缩)
- butterknife (黄油刀)
- glide （3.8.0）图片加载

> 框架默认添加如下权限(请勿重复添加)
```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```
> 轮播图的使用
- butterknife (布局)
`rect(方块), oval(圆点)`
```
<org.jbase.yxt.tj.ts.widget.BannerLayout
    android:id="@+id/banner"
    android:layout_width="match_parent"
    android:layout_height="176dp"
    app:autoPlayDuration="4000"
    app:indicatorMargin="10dp"
    app:indicatorPosition="rightBottom"
    app:indicatorShape="oval"
    app:indicatorSpace="3dp"
    app:isAutoPlay="true"
    app:scrollDuration="600"
    app:selectedIndicatorColor="@color/tab_content"
    app:selectedIndicatorHeight="6dp"
    app:selectedIndicatorWidth="6dp"
    app:unSelectedIndicatorColor="#99ffffff"
    app:unSelectedIndicatorHeight="6dp"
    app:unSelectedIndicatorWidth="6dp" />
```
```
// 设置加载图片的方式
bannerLayout.setImageLoader(new BannerImageLoader());
// 设置图片url
bannerLayout.setViewUrls(url集合);
// 添加监听事件
bannerLayout.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
    @Override
    public void onItemClick(int position) {
        ...
    }
});
```
