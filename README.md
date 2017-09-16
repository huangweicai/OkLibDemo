![](http://upload-images.jianshu.io/upload_images/2405826-cc0431ee4fe736cc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

>声明：oklib库所有的源码都是项目开发常用代码，库内容包括作者以前项目资源及开源资源，未经作者允许不得以营销为手段用作商业用途，另外如库中存在读者源码可联系作者标明出处。本库将持续更新完善，如果看到该库有吸引你的地方，也欢迎志同道合的朋友加入，一起为开源世界贡献一点力量，壮大我们的队伍。最后如要转载文章，请注明出处，避免一些不必要的纠纷，谢谢！

>[oklib使用文档地址](http://www.jianshu.com/p/87e7392a16ff)
[gitbub代码演示地址](https://github.com/huangweicai/OkLibDemo)
[apk体验下载地址](https://fir.im/tsd6)

#####OkLib介绍：
一个专注于让项目开发更简单的框架，集成了主流的开发框架及常用的工具类，让项目开发更加统一规范，减少功能及方法测试时间，助力于项目稳定、快速、高效开发。

#####OkLib要做的事：
1.技术选型：提供项目开发必备，并且市面上最热门的技术框架并持续更新
2.资源共享：整合以前项目资源及开源代码，快速定位功能代码，重复轮子拿来即用

#####快速使用oklib：
第一步：引用（如下地址会随着更新库而升高，向前兼容，不影响以前版本）
```
compile 'com.oklib:oklib:1.0.12'
```
第二部：在项目的入口，如：Application初始化oklib库
```
//使用OkLib库必须先调用初始化方法
new OkLib.Builder()
        .setApplication(this)
        .setPackageName(BuildConfig.APPLICATION_ID)
        .isDebug(true)
        .isShowToast(true)
        .build();
```

#####注意：
1.三方依赖，引入oklib时和项目主工程是否存在一致的包，会冲突
- compile 'com.github.bumptech.glide:glide:4.0.0-RC0'
- compile 'com.github.bumptech.glide:okhttp3-integration:4.0.0-RC0'
- compile 'com.alibaba:fastjson:1.2.35'
- compile 'com.orhanobut:logger:2.1.1'
- compile 'jp.co.cyberagent.android.gpuimage:gpuimage-library:1.4.1'
- compile 'com.sdsmdg.tastytoast:tastytoast:0.1.1'
- compile 'com.squareup.picasso:picasso:2.4.0'
- compile 'com.squareup.okhttp3:okhttp:3.4.1'

2.如下图，首页的四大模块入口，可以在apk中查看类找到相应入口，进入复制即可
![项目结构](http://upload-images.jianshu.io/upload_images/2405826-a72105fff095af15.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

>补充：在拉取库项目使用演示代码后，如果有引入不存在的情况，请直接注释掉，不影响代码主功能使用演示。另外，部分新功能如果无法进入体验，请随时点击更新检查并等待作者更新apk。如若使用过程中，遇到问题，请扫码加入QQ交流群或者直接在简书下评论留言。对于功能代码的快速定位，可以通过apk查看功能并把该功能的类发送到QQ或者微信，然后电脑端复制粘贴；也可以查阅github的库使用演示，进入到相应类并复制粘贴。

![库演示图片](http://upload-images.jianshu.io/upload_images/2405826-77d38fd46f940326.GIF?imageMogr2/auto-orient/strip)

#####交流反馈QQ群：（技术交流区）
![](http://upload-images.jianshu.io/upload_images/2405826-d8df702c0ad697d3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#####微信公众号：（用于不定时推送技术干货）
![](http://upload-images.jianshu.io/upload_images/2405826-acaf8624cbbb6e04.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)