package com.hwc.oklib;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bmoblib.BmobQueryHelp;
import com.bmoblib.bean.Notice;
import com.bmoblib.bean.UserBean;
import com.bmoblib.bean.Version;
import com.hwc.oklib.base.BaseAppActivity;
import com.hwc.oklib.camera.AppPaths;
import com.hwc.oklib.camera.MultiImageSelectorActivity;
import com.hwc.oklib.camera.help.CameraManager;
import com.hwc.oklib.camera.help.CameraUtil;
import com.hwc.oklib.fragment.MainRvFragment;
import com.hwc.oklib.http.HttpUtil;
import com.hwc.oklib.http.UrlConfig;
import com.hwc.oklib.http.okgo.OkGo;
import com.hwc.oklib.http.okgo.callback.FileCallback;
import com.hwc.oklib.http.okgo.model.HttpParams;
import com.hwc.oklib.imageloader.gimage.GlideImageView;
import com.hwc.oklib.login.SignInActivity;
import com.hwc.oklib.login.UserManager;
import com.hwc.oklib.util.CountUtil;
import com.hwc.oklib.util.Debug;
import com.hwc.oklib.util.FileUtil;
import com.hwc.oklib.util.IntentUtil;
import com.hwc.oklib.util.NetUtil;
import com.hwc.oklib.util.PackageInfoUtil;
import com.hwc.oklib.util.SPUtils;
import com.hwc.oklib.util.active_permission.PermissionFail;
import com.hwc.oklib.util.active_permission.PermissionGen;
import com.hwc.oklib.util.active_permission.PermissionSuccess;
import com.hwc.oklib.util.toast.ToastUtil;
import com.hwc.oklib.view.CommonToolBar;
import com.hwc.oklib.view.ImmersedStatusbarUtils;
import com.hwc.oklib.view.TextViewMarquee;
import com.hwc.oklib.window.ConfirmDialog;
import com.hwc.oklib.window.EditDialog;
import com.hwc.oklib.window.base.BaseDialogFragment;

import java.io.File;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import okhttp3.Call;
import okhttp3.Response;

import static com.hwc.oklib.util.CommonUtils.installAPK;


/**
 * 时间：2017/8/1
 * 作者：黄伟才
 * 简书：http://www.jianshu.com/p/87e7392a16ff
 * github：https://github.com/huangweicai/OkLibDemo
 * 描述：入口类，功能列表展示
 */
public class MainActivity extends BaseAppActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private CommonToolBar tb_toolbar;
    private TabLayout toolbar_tl_tab;
    private TextViewMarquee tvm_textViewMarquee;
    private View marqueeLine;
    private ViewPager vp_container;
    private GlideImageView iv_headPortrait;
    private TextView tv_nickName;
    private TextView tv_intro;
    private LinearLayout ll_background;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariable() {
        ifShowExit = true;
        ImmersedStatusbarUtils.initAfterSetContentView(this, null);
    }

    @Override
    protected void initTitle() {
        tb_toolbar = (CommonToolBar) findViewById(R.id.tb_toolbar);
        //toolbar与菜单栏不联系，不会作用到
        //setSupportActionBar(tb_toolbar);
        tb_toolbar.setCenterTitle("OkLib工具库", 17, R.color.app_white_color);
        tb_toolbar.setImmerseState(this);
        tb_toolbar.setRightTitle("资源详细", 14, R.color.app_white_color).setRightTitleListener(new View.OnClickListener() {
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
                this, drawer, tb_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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

        iv_headPortrait = navigationView.getHeaderView(0).findViewById(R.id.iv_headPortrait);
        tv_nickName = navigationView.getHeaderView(0).findViewById(R.id.tv_nickName);
        tv_intro = navigationView.getHeaderView(0).findViewById(R.id.tv_intro);
        ll_background = navigationView.getHeaderView(0).findViewById(R.id.ll_background);
        iv_headPortrait.setOnClickListener(this);
        tv_nickName.setOnClickListener(this);
        tv_intro.setOnClickListener(this);
        ll_background.setOnClickListener(this);
        iv_headPortrait.loadImage(UserManager.getUserBean().getHeadPicture(), R.mipmap.head_icon);
        tv_nickName.setText(TextUtils.isEmpty(UserManager.getUserBean().getNickName())?"昵称":UserManager.getUserBean().getNickName());
        tv_intro.setText(TextUtils.isEmpty(UserManager.getUserBean().getIntro())?"暂无介绍":UserManager.getUserBean().getIntro());
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

        //检查安装更新包
        checkUpdate();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_headPortrait:
                if (TextUtils.isEmpty(UserManager.getUserBean().getToken())) {
                    ConfirmDialog dialog = ConfirmDialog.create(getSupportFragmentManager());
                    dialog.show();
                    dialog.setTitle("登录");
                    dialog.setContent("修改头像需要登录，是否去登录界面？");
                    dialog.setOnConfirmListener(new BaseDialogFragment.OnConfirmListener() {
                        @Override
                        public void confirm(View v) {
                            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else {
                    PermissionGen.with(MainActivity.this)
                            .addRequestCode(100)
                            .permissions(
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                            .request();
                }
                break;
            case R.id.tv_nickName:
                if (TextUtils.isEmpty(UserManager.getUserBean().getToken())) {
                    ConfirmDialog dialog = ConfirmDialog.create(getSupportFragmentManager());
                    dialog.show();
                    dialog.setTitle("登录");
                    dialog.setContent("修改昵称需要登录，是否去登录界面？");
                    dialog.setOnConfirmListener(new BaseDialogFragment.OnConfirmListener() {
                        @Override
                        public void confirm(View v) {
                            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else {
                    final EditDialog editDialog = EditDialog.create(getSupportFragmentManager());
                    editDialog.setTitle("修改昵称");
                    editDialog.setExplain("昵称：");
                    editDialog.setLimit("最多12个文字");
                    editDialog.setContent(UserManager.getUserBean().getNickName());
                    editDialog.show();
                    editDialog.setOnConfirmListener(new BaseDialogFragment.OnConfirmListener() {
                        @Override
                        public void confirm(View v) {
                            UserBean userBean = new UserBean();
                            userBean.setNickName(editDialog.getContent());
                            userBean.setIntro(UserManager.getUserBean().getIntro());
                            userBean.setPhoneNum(UserManager.getUserBean().getPhoneNum());
                            userBean.setPassword(UserManager.getUserBean().getPassword());
                            userBean.setHeadPicture(UserManager.getUserBean().getHeadPicture());
                            BmobQueryHelp.updateUserBean(userBean, UserManager.getUserBean().getUid(), new BmobQueryHelp.OnEditListener() {
                                @Override
                                public void before() {
                                    showWaitDialog();
                                }

                                @Override
                                public void success() {
                                    dismissWaitDialog();
                                    ToastUtil.show("修改昵称成功");
                                    tv_nickName.setText(editDialog.getContent());
                                    //更新缓存
                                    UserManager.getUserBean().setNickName(tv_nickName.getText().toString());
                                    UserManager.cacheUserBean(context, UserManager.getUserBean());
                                }

                                @Override
                                public void onError(String errorMessage, int code) {
                                    dismissWaitDialog();
                                    ToastUtil.show(errorMessage);
                                }
                            });
                        }
                    });
                }
                break;
            case R.id.tv_intro:
                if (TextUtils.isEmpty(UserManager.getUserBean().getToken())) {
                    ConfirmDialog dialog = ConfirmDialog.create(getSupportFragmentManager());
                    dialog.show();
                    dialog.setTitle("登录");
                    dialog.setContent("修改介绍需要登录，是否去登录界面？");
                    dialog.setOnConfirmListener(new BaseDialogFragment.OnConfirmListener() {
                        @Override
                        public void confirm(View v) {
                            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else {
                    final EditDialog editDialog = EditDialog.create(getSupportFragmentManager());
                    editDialog.setTitle("修改介绍");
                    editDialog.setExplain("介绍：");
                    editDialog.setLimit("");
                    editDialog.setContent(UserManager.getUserBean().getIntro());
                    editDialog.show();
                    editDialog.setOnConfirmListener(new BaseDialogFragment.OnConfirmListener() {
                        @Override
                        public void confirm(View v) {
                            UserBean userBean = new UserBean();
                            userBean.setIntro(editDialog.getContent());
                            userBean.setNickName(UserManager.getUserBean().getNickName());
                            userBean.setPhoneNum(UserManager.getUserBean().getPhoneNum());
                            userBean.setPassword(UserManager.getUserBean().getPassword());
                            userBean.setHeadPicture(UserManager.getUserBean().getHeadPicture());
                            BmobQueryHelp.updateUserBean(userBean, UserManager.getUserBean().getUid(), new BmobQueryHelp.OnEditListener() {
                                @Override
                                public void before() {
                                    showWaitDialog();
                                }

                                @Override
                                public void success() {
                                    dismissWaitDialog();
                                    ToastUtil.show("修改介绍成功");
                                    tv_intro.setText(editDialog.getContent());
                                    //更新缓存
                                    UserManager.getUserBean().setIntro(tv_intro.getText().toString());
                                    UserManager.cacheUserBean(context, UserManager.getUserBean());
                                }

                                @Override
                                public void onError(String errorMessage, int code) {
                                    dismissWaitDialog();
                                    ToastUtil.show(errorMessage);
                                }
                            });
                        }
                    });
                }
                break;
            case R.id.ll_background:
                CountUtil.setOnCountListener(4, new CountUtil.OnCountListener() {
                    @Override
                    public void successCount() {
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(context, BackgroundActivity.class);
                                intent.putExtra(Common.TITLE, "后台界面");
                                startActivity(intent);
                            }
                        }, 200);
                    }
                }, "再按两次打开后台模式", "再按一次打开后台模式", "后台模式启动成功");
                break;
        }
    }

    @PermissionSuccess(requestCode = 100)
    public void openCamera() {
        //请求码100，请求成功
        CameraManager.getInstance().showAlbumAction(MainActivity.this, true, 1, CameraManager.MODE_SINGLE);
    }

    @PermissionFail(requestCode = 100)
    public void failOpenCamera() {
        //请求码100，请求失败
        Toast.makeText(context, "权限请求失败", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CameraManager.REQUEST_CAMERA) {
            //请求拍照
            if (resultCode == RESULT_OK) {
                if (CameraManager.mTmpFile != null) {
                    Uri o = CameraManager.getInstance().getUriForFile(context, CameraManager.mTmpFile);
                    CameraManager.mNewPhotoUri = CameraUtil.getOutputMediaFileUri(context, CameraUtil.MEDIA_TYPE_IMAGE);
                    CameraManager.getInstance().cropImage(this, o, CameraManager.mNewPhotoUri, CameraManager.REQUEST_CODE_CROP_PHOTO);
                }
            } else {
                while (CameraManager.mTmpFile != null && CameraManager.mTmpFile.exists()) {
                    boolean success = CameraManager.mTmpFile.delete();
                    if (success) {
                        CameraManager.mTmpFile = null;
                    }
                }
            }
        } else if (requestCode == CameraManager.REQUEST_IMAGE) {
            //请求图片
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                Uri o = CameraManager.getInstance().getUriForFile(context, new File(path.get(0)));
                CameraManager.mNewPhotoUri = CameraUtil.getOutputMediaFileUri(context, CameraUtil.MEDIA_TYPE_IMAGE);
                CameraManager.getInstance().cropImage(this, o, CameraManager.mNewPhotoUri, CameraManager.REQUEST_CODE_CROP_PHOTO);
            }

        } else if (requestCode == CameraManager.REQUEST_CODE_CROP_PHOTO) {
            //裁剪
            if (resultCode == RESULT_OK) {
                if (CameraManager.mNewPhotoUri != null) {
                    CameraManager.mCurrentFile = new File(CameraManager.mNewPhotoUri.getPath());
                    CameraManager.mlocitionFileUrl = FileUtil.getRealFilePath(context, CameraManager.mNewPhotoUri);

                    Log.d("TAG", "CameraManager.mlocitionFileUrl：" + CameraManager.mlocitionFileUrl);
                    //iv_app_icon.setImageBitmap(BitmapFactory.decodeFile(CameraManager.mlocitionFileUrl));
                    iv_headPortrait.loadImage(CameraManager.mlocitionFileUrl, R.mipmap.head_icon);

                    //文件上传下载存在问题
                    HttpParams params = new HttpParams();
                    params.put("file", new File(CameraManager.mlocitionFileUrl));
                    HttpUtil.getInstance().postRequest(UrlConfig.PORTRAIT_MODIFY, params, new HttpUtil.OnHttpListener() {
                        @Override
                        public void onBefore() {
                            showWaitDialog();
                        }

                        @Override
                        public void onSuccess(Object result) {
                            //iv_headPortrait.loadImage(result.toString(), R.mipmap.head_icon);
                            //更新缓存
                            UserManager.getUserBean().setHeadPicture(result.toString());
                            UserManager.cacheUserBean(context, UserManager.getUserBean());

                            //更新后端数据
                            UserBean userBean = new UserBean();
                            userBean.setIntro(UserManager.getUserBean().getIntro());
                            userBean.setNickName(UserManager.getUserBean().getNickName());
                            userBean.setPhoneNum(UserManager.getUserBean().getPhoneNum());
                            userBean.setPassword(UserManager.getUserBean().getPassword());
                            userBean.setHeadPicture(UserManager.getUserBean().getHeadPicture());
                            BmobQueryHelp.updateUserBean(userBean, UserManager.getUserBean().getUid(), new BmobQueryHelp.OnEditListener() {
                                @Override
                                public void before() {
                                }

                                @Override
                                public void success() {
                                    ToastUtil.show("修改头像成功");
                                    dismissWaitDialog();
                                }

                                @Override
                                public void onError(String errorMessage, int code) {
                                    ToastUtil.show("修改头像失败，请稍后再试");
                                    dismissWaitDialog();
                                }
                            });
                        }

                        @Override
                        public void onError(int code, Exception e) {
                            dismissWaitDialog();
                        }
                    });
                }
            }
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
                if (id == R.id.collect) {
                    Intent intent = new Intent(context, CollectActivity.class);
                    intent.putExtra(Common.TITLE, "我的收藏");
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
                } else if (id == R.id.load_apk_page) {
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
                } else if (id == R.id.logout) {//退出登录
                    UserManager.logout(context);
                }
            }
        }, 200);
        return true;
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
            Uri packageURI = Uri.parse("package:" + "com.hwc.oklib.util");
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
            startActivity(intent);
        }
    }

    private void downLoadApk(String apkUrl) {
        OkGo.get(apkUrl)
                .tag(this)
                .execute(new FileCallback(AppPaths.getInstance().mExternalFilesRootDir, "/oklib工具库.apk") {//文件下载时，需要指定下载的文件目录和文件名
                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        // file 即为文件数据，文件保存在指定目录
                        Debug.d("length:" + file.length());
                        Debug.d("file:" + file.getAbsolutePath());
                        installAPK(context, file);
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        //这里回调下载进度(该回调在主线程,可以直接更新ui)
                        Debug.d("progress:" + progress);
                    }
                });
    }

    //--------------------------------------------------------------
    //判断本地是否有新版本apk包，没有wifi自动下载，下次直接提示安装即可，默认进入就检查
    private void checkUpdate() {
        boolean needUpdate = (boolean) SPUtils.get(context, "needUpdate", false);
        if (needUpdate) {
            //存在apk包
            //提示安装
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setCancelable(false);
            dialog.setMessage("oklib工具库新版本已经准备好了，是否立即体验？");
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SPUtils.put(context, "needUpdate", false);
                    installAPK(context, new File(AppPaths.getInstance().mExternalFilesRootDir, "/oklib工具库.apk"));
                }
            });
            dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        }else{
            //不存在
            //检测，后台下载
            curVersionCode = PackageInfoUtil.getVersionCode(context);
            BmobQueryHelp.queryUpdate(new BmobQueryHelp.OnUpdateQueryListener() {
                @Override
                public void result(Version versionBean, BmobException e) {
                    apkUrl = versionBean.getApkUrl();
                    if (curVersionCode < versionBean.getVersionCode()) {
                        if (NetUtil.isWifi(context)) {
                            //wifi网络情况下，自动下载
                            OkGo.get(apkUrl)
                                    .tag(this)
                                    .execute(new FileCallback(AppPaths.getInstance().mExternalFilesRootDir, "/oklib工具库.apk") {//文件下载时，需要指定下载的文件目录和文件名
                                        @Override
                                        public void onSuccess(File file, Call call, Response response) {
                                            // file 即为文件数据，文件保存在指定目录
                                            Debug.d("length:" + file.length());
                                            Debug.d("file:" + file.getAbsolutePath());
                                            Debug.d("currentThread:" + Thread.currentThread());
                                            SPUtils.put(context, "needUpdate", true);

                                            //提示安装
                                            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                                            dialog.setCancelable(false);
                                            dialog.setMessage("oklib工具库新版本已经准备好了，是否立即体验？");
                                            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    SPUtils.put(context, "needUpdate", false);
                                                    installAPK(context, new File(AppPaths.getInstance().mExternalFilesRootDir, "/oklib工具库.apk"));
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

                                        @Override
                                        public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                                            //这里回调下载进度(该回调在主线程,可以直接更新ui)
                                            Debug.d("progress:" + progress);
                                        }

                                        @Override
                                        public void onError(Call call, Response response, Exception e) {
                                            super.onError(call, response, e);
                                            SPUtils.put(context, "needUpdate", false);
                                        }
                                    });
                        }
                    }
                }
            });
        }

    }

}
