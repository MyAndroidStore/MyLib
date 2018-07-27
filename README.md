# MyLib
> 框架默认集成如下依赖(请勿重复集成)
- NoHttp (1.1.10)网络请求[包含okhttp]
- Gson (1.1.3)json工具
- Luban (2.8.2)图片压缩
- Glide （3.8.0）图片加载
- CircleImageview (2.2.0)圆形图片
- Matisse (0.5.1)知乎图片选择框架
- AndPermission (2.0.0-rc11)权限处理

> 框架默认添加如下权限(请勿重复添加)
```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```
> 添加依赖

`root build.gradle `
```
allprojects {
    repositories {
        ...
        maven {
            url 'https://jitpack.io'
        }
    }
}
```
`module build.gradle `
```
dependencies {
    implementation 'com.github.MyAndroidStore:MyLib:2.3.5'
}
```
`如果报错，如下：v4、v7包冲突`
![error.png](https://github.com/MyAndroidStore/MyLib/blob/master/pictures/20180720_103110.png?raw=true)
```
implementation ('com.github.MyAndroidStore:MyLib:2.0.9'){
    exclude group: 'com.android.support'
}
```
> 框架初始化(Application进行初始化)
```
// NoHttp全局初始化
NoHttp.initialize(this);
// 开启NoHttp调试模式
Logger.setDebug(true);
// 设置NoHttp打印Log的TAG
Logger.setTag("NoHttpSample");
// ToastUtils全局初始化
ToastUtils.init(this);
```
> 轮播图的使用

https://www.jianshu.com/p/8e566da74b3e
> Toast的使用
```
ToastUtils.getInstance().shortToast("Toast详细内容");
ToastUtils.getInstance().longToast("Toast详细内容");
```
> Base64ConvertUtils的使用(文件转换成base64)
```
Base64ConvertUtils.file2Base64();
```