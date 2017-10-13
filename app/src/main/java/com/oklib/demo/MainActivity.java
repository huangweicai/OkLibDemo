package com.oklib.demo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.bmoblib.BmobQueryHelp;
import com.bmoblib.bean.Notice;
import com.bmoblib.bean.Version;
import com.oklib.AppPaths;
import com.oklib.base.BaseDialogFragment;
import com.oklib.demo.base.BaseAppActivity;
import com.oklib.demo.util.PackageInfoUtil;
import com.oklib.util.Debug;
import com.oklib.util.FileUtil;
import com.oklib.util.IntentUtil;
import com.oklib.util.NetUtil;
import com.oklib.util.http.OkGo;
import com.oklib.util.http.callback.FileCallback;
import com.oklib.util.permission.PermissionFail;
import com.oklib.util.permission.PermissionGen;
import com.oklib.util.permission.PermissionSuccess;
import com.oklib.util.toast.ToastUtil;
import com.oklib.view.CommonToolBar;
import com.oklib.view.TextViewMarquee;
import com.oklib.widget.ConfirmDialog;

import java.io.File;

import cn.bmob.v3.exception.BmobException;
import okhttp3.Call;
import okhttp3.Response;


/**
 * 时间：2017/8/1
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：入口类，功能列表展示
 */
public class MainActivity extends BaseAppActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CommonToolBar toolbar;
    private TabLayout toolbar_tl_tab;
    private TextViewMarquee tvm_textViewMarquee;
    private View marqueeLine;
    private ViewPager vp_container;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariable() {
        ifShowExit = true;
        setStateBarStyle();
    }

    @Override
    protected void initTitle() {
        toolbar = (CommonToolBar) findViewById(R.id.toolbar);
        //toolbar与菜单栏不联系，不会作用到
        //setSupportActionBar(toolbar);
        toolbar.setCenterTitle("OkLib库演示", 17, R.color.app_white_color);
        toolbar.setImmerseState(this);
        toolbar.setRightTitle("资源详细", 14, R.color.app_white_color).setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SupportingDetailsActivity.class);
                intent.putExtra(Common.TITLE, "资源详细");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tvm_textViewMarquee = findView(R.id.tvm_textViewMarquee);
        marqueeLine = findView(R.id.marqueeLine);
        toolbar_tl_tab = findView(R.id.toolbar_tl_tab);
        vp_container = findView(R.id.vp_container);
        toolbar_tl_tab.setupWithViewPager(vp_container);
        vp_container.setOffscreenPageLimit(4);
        vp_container.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", position);
                return MainRvFragment.getInstance(bundle);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return Common.TITLES[position];
            }

            @Override
            public int getCount() {
                return Common.TITLES.length;
            }
        });
    }

    @Override
    protected void initNet() {
        BmobQueryHelp.queryNotice(new BmobQueryHelp.OnNoticeListener() {
            @Override
            public void result(final Notice notice, BmobException e) {
                if (notice.isShowNotice()) {
                    tvm_textViewMarquee.setVisibility(View.VISIBLE);
                    marqueeLine.setVisibility(View.VISIBLE);
                } else {
                    tvm_textViewMarquee.setVisibility(View.GONE);
                    marqueeLine.setVisibility(View.GONE);
                }
                tvm_textViewMarquee.setText(notice.getNoticeText());
                tvm_textViewMarquee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, WebViewActivity.class);
                        intent.putExtra(Common.TITLE, "通知");
                        intent.putExtra(Common.URL, notice.getUrl());
                        intent.putExtra(WebViewActivity.IS_SHOW_WEB_URL, true);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void setStateBarStyle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//全屏
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;//状态栏字体深白色
//                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;//状态栏字体深灰色（6.0以上属性）（注意：1.以最后一个设置为准 2.特定的颜色值才有效果）
            decorView.setSystemUiVisibility(option);
            //最好设置，不设置默认状态栏背景颜色是灰色
            getWindow().setStatusBarColor(0x00ffffff);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int id = item.getItemId();
                if (id == R.id.exceptional_support) {
                    Intent intent = new Intent(context, SupportExceptionalActivity.class);
                    intent.putExtra(Common.TITLE, "打赏界面");
                    startActivity(intent);
                } else if (id == R.id.feedback_to_improve) {
                    Intent intent = new Intent(context, FeedBackActivity.class);
                    intent.putExtra(Common.TITLE, "反馈界面");
                    startActivity(intent);
                } else if (id == R.id.check_updates) {
                    // 手动检查更新
                    versionUpdate();
                } else if (id == R.id.exchange_area) {
                    Intent intent = new Intent(context, ExchangeAreaActivity.class);
                    intent.putExtra(Common.TITLE, "技术交流");
                    startActivity(intent);
                }  else if (id == R.id.load_apk_page) {
                    IntentUtil.localWebOpenUrl(context, getResources().getString(R.string.load_apk_url));
                } else if (id == R.id.jianshu) {//简书
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra(Common.TITLE, "简书");
                    intent.putExtra(Common.URL, getResources().getString(R.string.jianshu_url));
                    intent.putExtra(WebViewActivity.IS_SHOW_WEB_URL, true);
                    startActivity(intent);
                } else if (id == R.id.github) {//GitHub
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra(Common.TITLE, "GitHub");
                    intent.putExtra(Common.URL, getResources().getString(R.string.github_url));
                    intent.putExtra(WebViewActivity.IS_SHOW_WEB_URL, true);
                    startActivity(intent);
                }
            }
        }, 200);
        return true;
    }

    /**
     * 作者：黄伟才
     * 描述：同navigationView.getHeaderView(0).findViewById(R.id.iv_head_portrait);
     */
    private long preKeyBackTime = 0L;
    private int count = 0;

    public void doHeadClick(View view) {
        if (System.currentTimeMillis() - this.preKeyBackTime < 2000L) {
            count++;
            this.preKeyBackTime = System.currentTimeMillis();
            if (count == 3) {
                ToastUtil.show("再按两次打开后台模式");
            } else if (count == 4) {
                ToastUtil.show("再按一次打开后台模式");
            } else if (count >= 5) {
                count = 0;
                this.preKeyBackTime = 0L;
                ToastUtil.show("后台模式启动成功");
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(context, BackgroundActivity.class);
                        intent.putExtra(Common.TITLE, "后台显示界面");
                        startActivity(intent);
                    }
                }, 200);
            }
        } else {
            count++;
            this.preKeyBackTime = System.currentTimeMillis();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    private int curVersionCode;
    private String apkUrl;

    //版本更新
    private void versionUpdate() {
        curVersionCode = PackageInfoUtil.getVersionCode(context);
        BmobQueryHelp.queryUpdate(new BmobQueryHelp.OnUpdateQueryListener() {
            @Override
            public void result(Version versionBean, BmobException e) {
                apkUrl = versionBean.getApkUrl();
                if (curVersionCode < versionBean.getVersionCode()) {
                    if (versionBean.isforce()) {
                        //强制更新
                        PermissionGen.needPermission(MainActivity.this, 201,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    } else {
                        //更新提示窗口
                        showUpdateWin(versionBean);
                    }
                } else {
                    ToastUtil.show("当前版本已是最新版本" + PackageInfoUtil.getVersionName(context));
                }

            }
        });
    }

    //更新提示窗口
    private void showUpdateWin(final Version versionBean) {
        ConfirmDialog dialog = ConfirmDialog.create(getSupportFragmentManager());
        dialog.setTitle("发现新版本：库" + versionBean.getVersionName() + "（" + FileUtil.formatFileSize(versionBean.getTargetSize()) + "）");
        dialog.setContent(versionBean.getUpdateLog());
        dialog.show();
        dialog.setOnConfirmListener(new BaseDialogFragment.OnConfirmListener() {
            @Override
            public void confirm(View v) {
                PermissionGen.needPermission(MainActivity.this, 201,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        });
    }

    @PermissionSuccess(requestCode = 201)
    public void hasPermissionToLoad() {
        Debug.d("isWifi:" + NetUtil.isWifi(context));
        if (NetUtil.isWifi(context)) {
            ToastUtil.show("开始下载...");
            downLoadApk(apkUrl);
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setCancelable(false);
            dialog.setMessage("当前网络是移动网络，确定下载食趣新版本？");
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ToastUtil.show("开始下载...");
                    downLoadApk(apkUrl);
                }
            });
            dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        }
    }

    @PermissionFail(requestCode = 201)
    public void failToLoad() {
        boolean isTip = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (isTip) {
            //用户取消权限请求，并没有彻底禁止弹出权限请求
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setCancelable(false);
            dialog.setMessage("需要该权限保存下载的应用");
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //再次请求权限
                    PermissionGen.needPermission(MainActivity.this, 201,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            });
            dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();

        } else {
            //用户彻底禁止弹出权限请求

            //进入权限设置界面
            Uri packageURI = Uri.parse("package:" + "com.midea.cook");
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
            startActivity(intent);
        }
    }

    private void downLoadApk(String apkUrl) {
        OkGo.get(apkUrl)
                .tag(this)
                .execute(new FileCallback(AppPaths.getInstance().mExternalFilesRootDir, "/食趣.apk") {//文件下载时，需要指定下载的文件目录和文件名
                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        // file 即为文件数据，文件保存在指定目录
                        Debug.d("length:" + file.length());
                        Debug.d("file:" + file.getAbsolutePath());
                        installApk(context, file);
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调下载进度(该回调在主线程,可以直接更新ui)
                        Debug.d("progress:" + progress);
                    }
                });
    }

    //适配Android N权限回收问题
    private void installApk(Context context, File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }


}
