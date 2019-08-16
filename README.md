# MyLib
> 框架默认集成如下依赖(请勿重复集成)
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
<uses-permission android:name="android.permission.CAMERA" />
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
    implementation 'com.github.MyAndroidStore:MyLib:4.7.3'
}
```
`如果报错，如下：v4、v7包冲突`
![error.png](https://github.com/MyAndroidStore/MyLib/blob/master/pictures/20180720_103110.png?raw=true)
```
implementation ('com.github.MyAndroidStore:MyLib:4.7.3'){
    exclude group: 'com.android.support'
}
```
> Base64ConvertUtils的使用(文件转换成base64)
```
Base64ConvertUtils.file2Base64();
```