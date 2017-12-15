![](http://upload-images.jianshu.io/upload_images/2405826-cc0431ee4fe736cc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

>[oklib使用文档地址](http://www.jianshu.com/p/87e7392a16ff)
[gitbub代码演示地址](https://github.com/huangweicai/OkLibDemo)
[apk体验下载地址](http://fir.im/ud6g)

>**OkLib要做的事：**
1.技术选型：提供项目开发必备，并且市面上最热门的技术框架并持续更新
2.资源共享：整合以前项目资源及开源代码，快速定位功能代码，重复轮子拿来即用
**最佳实践：**
通过库app查阅相关功能，然后选择需要功能代码，发送到微信或者QQ，然后在电脑的微信端或者QQ端打开，最后直接复制粘贴。

**使用准备：**
github下载项目，主要使用oklibhelp依赖库（集成了多个工具包）
```
//aar形式，帮助库
compile project(':oklibhelp')

//源码形式
compile project(':expressionlib')
compile project(':loadsir')
compile project(':videoplaylib')
```
![](http://upload-images.jianshu.io/upload_images/2405826-8fadd5d7b7f0a049.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**快速使用：**
1.在Application入口初始化每个工具包，工具包根据需要初始化，命名规则都是工具包名.init();
```
//初始化插件库
private void initLib() {
    UtilEntry.init(this, true);
    HttpEntry.init(this);
    CameraEntry.init(this, BuildConfig.APPLICATION_ID);
    WindowEntry.init(this);
    DataBaseEntry.onCreate(this);
    LoadSirEntry.init();
}
```
补充：对于使用到数据库工具包，还需要加入如下方法：
```
@Override
public void onTerminate(){
    super.onTerminate();
    DataBaseEntry.onTerminate();
}
```
2.根据演示apk，查阅相关的功能，发送连接到微信电脑端或者QQ电脑的，然后在电脑端打开复制即可。下图介绍首页的四大模块入口，也可以根据入口和类命名查阅相关功能的使用样例。
![项目结构](http://upload-images.jianshu.io/upload_images/2405826-a72105fff095af15.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**下图是界面功能的相关演示，具体以apk演示为准**
![库演示图片](http://upload-images.jianshu.io/upload_images/2405826-77d38fd46f940326.GIF?imageMogr2/auto-orient/strip)

**关于oklib工具库使用交流反馈区（QQ群）**
![](http://upload-images.jianshu.io/upload_images/2405826-d8df702c0ad697d3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**欢迎关注作者公众号（招聘微讯，更便捷的招聘信息渠道~）**
![招聘微讯](http://upload-images.jianshu.io/upload_images/2405826-abb6014d0a66065b.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


