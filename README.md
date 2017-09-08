![](http://upload-images.jianshu.io/upload_images/2405826-cc0431ee4fe736cc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

>声明：oklib库所有的源码都是项目开发常用代码，库内容包括作者以前项目资源及开源资源，未经作者允许不得以营销为手段用作商业用途，另外如库中存在读者源码可联系作者标明出处。本库将持续更新完善，如果看到该库有吸引你的地方，也欢迎志同道合的朋友加入，一起为开源世界贡献一点力量，壮大我们的队伍。最后如要转载文章，请注明出处，避免一些不必要的纠纷，谢谢！

>[oklib使用文档地址](http://www.jianshu.com/p/87e7392a16ff)
[gitbub代码演示地址](https://github.com/huangweicai/OkLibDemo)
[apk体验下载地址](https://fir.im/tsd6)

#####OkLib介绍：
一个专注于让项目开发更简单的框架，集成了主流的开发框架及常用的工具类，让项目开发更加统一规范，减少功能及方法测试时间，助力于项目稳定、快速、高效开发。

#####OkLib要做的事：
1.技术选型：提供项目开发必备，并且市面上最热门的技术框架并持续更新
2.资源共享：整合以前项目资源及开源代码，重复轮子拿来即用

#####快速使用oklib：
第一步：引用
```
compile 'com.oklib:oklib:latest.release'
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

![库演示图片](http://upload-images.jianshu.io/upload_images/2405826-77d38fd46f940326.GIF?imageMogr2/auto-orient/strip)

#####交流反馈QQ群：（技术交流区）
![](http://upload-images.jianshu.io/upload_images/2405826-d8df702c0ad697d3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#####微信公众号：（用于不定时推送技术干货）
![](http://upload-images.jianshu.io/upload_images/2405826-acaf8624cbbb6e04.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)