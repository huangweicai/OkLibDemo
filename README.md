![](http://upload-images.jianshu.io/upload_images/2405826-cc0431ee4fe736cc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

>前言：由于作者这两天有事外出，这几天把整体框架及流程走通并验证测试，本库已提交到jcenter中央仓库，并且在github有开源的使用样例源码，另外也可在fir测试平台上直接下载apk体验，本库的宗旨是让项目开发更简单，所见即所得，你可以在apk中直接查看演示源码并且发送到手机内应用，并且集成的数据收集功能，可以在反馈界面留言。由于时间关系，暂时文档先简陋的提交入口，等过几天回来继续完善oklib使用文档。有疑问欢迎留言！

>声明：作者郑重申明，oklib库所有的源码都是经过项目实践并且抽取出来的，本库在未来的几年内会持续更新完善，也欢迎志同道合的朋友一起为开源世界贡献一点力量，另外，转载请注明出处，避免一些不必要的纠纷。

#####介绍：
一个专注于让项目开发更简单的框架，集成了主流的开发框架及常用的工具类，让项目开发更加统一规范，减少功能及方法测试时间，助力于项目稳定、快速、高效开发。

#####使用oklib：
第一步：引用
```
compile 'com.oklib:oklib:1.0.6'
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

[简书·使用文档地址](http://www.jianshu.com/p/87e7392a16ff)
[gitbub地址](https://github.com/huangweicai/OkLibDemo)
[apk下载地址](https://fir.im/tsd6)

#####注意：
由于前段时间作者花大量时间去梳理结构和流程上，目前oklib库暂时支持10个左右的使用功能，欢迎大家关注该库，大概一段时间后会补充到大概100个功能左右，绝对囊括市面上有的或者没有的功能及效果，作者将会花费大量时间和精力去完善维护本库，欢迎留言交流！