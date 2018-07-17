# MyLib
> 框架默认集成如下依赖(请勿重复集成)
- NoHttp (网络请求)
- Gson (json工具)
- Luban (图片压缩)
- Glide （3.8.0）图片加载
- CircleImageview (2.2.0)圆形图片

> 框架默认添加如下权限(请勿重复添加)
```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```
> 轮播图的使用

https://www.jianshu.com/p/8e566da74b3e
> Toast的使用
```
ToastUtils.getInstance().shortToast("Toast详细内容");
ToastUtils.getInstance().longToast("Toast详细内容");
```