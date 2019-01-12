package com.oklib.http.z_help;


import com.oklib.http.OklibHttp;
import com.oklib.http.cache.CacheEntity;
import com.oklib.http.cache.CacheMode;
import com.oklib.http.callback.StringCallback;
import com.oklib.http.cookie.store.PersistentCookieStore;
import com.oklib.http.model.HttpHeaders;
import com.oklib.http.model.HttpParams;
import com.oklib.http.request.BaseRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 时间：2017/10/23
 * 作者：蓝天
 * 描述：http请求工具类
 */

public class HttpUtil {

    static {
        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OklibHttp.getInstance()

                    //打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行
                    .debug("http")

                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(OklibHttp.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(OklibHttp.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OklibHttp.DEFAULT_MILLISECONDS)    //全局的写入超时时间

                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.NO_CACHE)

                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                    //如果不想让框架管理cookie,以下不需要
//                .setCookieStore(new MemoryCookieStore())                //cookie使用内存缓存（app退出后，cookie消失）
                    .setCookieStore(new PersistentCookieStore());          //cookie持久化存储，如果cookie不过期，则一直有效

            //可以设置https的证书,以下几种方案根据需要自己设置,不需要不用设置
//                    .setCertificates()                                  //方法一：信任所有证书
//                    .setCertificates(getAssets().open("srca.cer"))      //方法二：也可以自己设置https证书
//                    .setCertificates(getAssets().open("aaaa.bks"), "123456", getAssets().open("srca.cer"))//方法三：传入bks证书,密码,和cer证书,支持双向加密

            //可以添加全局拦截器,不会用的千万不要传,错误写法直接导致任何回调不执行
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return chain.proceed(chain.request());
//                    }
//                })

            //这两行同上,不需要就不要传
//                    .addCommonHeaders(headers)                                         //设置全局公共头
//                    .addCommonParams(params);                                          //设置全局公共参数
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消请求
     *
     * @param url
     */
    public void cancelRequest(String url) {
        //根据 Tag 取消请求
        OklibHttp.getInstance().cancelTag(getURLEncoderStr(url));
    }

    public HttpHeaders getHttpHeaders() {
        //请求头，header不支持中文
        HttpHeaders headers = new HttpHeaders();
        headers.put("X-Wellness-AppId", "1016");
        headers.put("X-Wellness-Token", "9f750404-6b38-47a7");
        headers.put("X-Wellness-AppVersion", "1.1.4");
        headers.put("X-Wellness-platform", "2");
        return headers;
    }

    public String getURLEncoderStr(String url) {
        String cacheKey = "";
        try {
            cacheKey = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cacheKey;
    }

    /**
     * post请求
     *
     * @param url
     * @param params
     */
    public void postRequest(String url, HttpParams params, final OnHttpListener onHttpListener) {
        OklibHttp.post(url)
                .headers(getHttpHeaders())      // 头部信息
                .params(params)                 // 上行参数
                .tag(getURLEncoderStr(url))     // 请求的tag, 主要用于取消对应的请求
                .cacheKey(getURLEncoderStr(url))// 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)   // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    /** 请求网络开始前，UI线程 */
                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        if (null != onHttpListener) {
                            onHttpListener.onBefore();
                        }
                    }

                    /** 对返回数据进行操作的回调， UI线程 */
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //{"ret":0,"enMsg":null,"zhMsg":null,"detailMsg":null,"result":{"token":"45dfa31b-61af-43dc-996b-95bbf1e44331","refreshToken":"8e86f0bcf4adf99d2a3ac6fe1600e354","uid":"e3725c7fbd2d4ef484fb4a50fe3b077b"}}
                        //暂时处理0的状态码，后面与后台配合补上其他的
                        HttpBaseBean baseBean = JsonUtil.json2Bean(s, HttpBaseBean.class);
                        if (baseBean.getRet() == 0) {
                            //0是成功状态码，其余的都是业务失败
                            if (null != onHttpListener) {
                                onHttpListener.onSuccess(baseBean.getResult());
                            }
                        } else {
                            if (null != onHttpListener) {
                                if (StateCode.TOKEN_EXPIRED == baseBean.getRet()) {
                                    //Token失效，去登陆界面
                                    //SignInActivity.intentToLogin();
                                }

                                if ("暂无匹配的码值".equals(StateCode.getCodeMsg(baseBean.getRet()))) {
                                    //ToastUtil.show(baseBean.getZhMsg());
                                }else{
                                    onHttpListener.onError(baseBean.getRet(), null);//业务失败false
                                }
                            }
                        }
                    }

                    /** 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程 */
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (null != onHttpListener) {
                            onHttpListener.onError(StateCode.NO_NET, e);//网络失败false
                        }
                    }

                    /**Post执行上传过程中的进度回调，get请求不回调，UI线程*/
//                    @Override
//                    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
//                        if (null != onHttpListener) {
//                            try {
//                                OnHttpProgressListener onHttpProgressListener = (OnHttpProgressListener) onHttpListener;
//                                onHttpProgressListener.upProgress(currentSize, totalSize, progress, networkSpeed);
//                            } catch (Exception e) {//捕捉其他不是OnHttpProgressListener的情况
//                                e.printStackTrace();
//                            }
//                        }
//                    }
                });
    }

    /**
     * get请求
     *
     * @param url
     */
    public void getRequest(String url, HttpParams params, final OnHttpListener onHttpListener) {
        OklibHttp.get(url)                           // 请求方式和请求url
                .headers(getHttpHeaders())
                .params(params)// 头部信息
                .tag(getURLEncoderStr(url))     // 请求的 tag, 主要用于取消对应的请求
                .cacheKey(getURLEncoderStr(url))// 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)   // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    /** 请求网络开始前，UI线程 */
                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        if (null != onHttpListener) {
                            onHttpListener.onBefore();
                        }
                    }

                    /** 对返回数据进行操作的回调， UI线程 */
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //{"ret":0,"enMsg":null,"zhMsg":null,"detailMsg":null,"result":{"token":"45dfa31b-61af-43dc-996b-95bbf1e44331","refreshToken":"8e86f0bcf4adf99d2a3ac6fe1600e354","uid":"e3725c7fbd2d4ef484fb4a50fe3b077b"}}
                        //暂时处理0的状态码，后面与后台配合补上其他的
                        HttpBaseBean baseBean = JsonUtil.json2Bean(s, HttpBaseBean.class);
                        if (baseBean.getRet() == 0) {
                            //0是成功状态码，其余的都是业务失败
                            if (null != onHttpListener) {
                                onHttpListener.onSuccess(baseBean.getResult());
                            }
                        } else {
                            if (null != onHttpListener) {
                                if (StateCode.TOKEN_EXPIRED == baseBean.getRet()) {
                                    //Token失效，去登陆界面
                                    //SignInActivity.intentToLogin();
                                }

                                if ("暂无匹配的码值".equals(StateCode.getCodeMsg(baseBean.getRet()))) {
                                    //ToastUtil.show(baseBean.getZhMsg());
                                }else{
                                    onHttpListener.onError(baseBean.getRet(), null);//业务失败false
                                }
                            }
                        }
                    }

                    /** 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程 */
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (null != onHttpListener) {
                            onHttpListener.onError(StateCode.NO_NET, e);//网络失败false
                        }
                    }
                });
    }

    /**
     * get请求
     *
     * @param url
     */
    public void getRequest(String url, final OnHttpListener onHttpListener) {
        OklibHttp.get(url)                           // 请求方式和请求url
                .headers(getHttpHeaders())      // 头部信息
                .tag(getURLEncoderStr(url))     // 请求的 tag, 主要用于取消对应的请求
                .cacheKey(getURLEncoderStr(url))// 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)   // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    /** 请求网络开始前，UI线程 */
                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        if (null != onHttpListener) {
                            onHttpListener.onBefore();
                        }
                    }

                    /** 对返回数据进行操作的回调， UI线程 */
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        //{"ret":0,"enMsg":null,"zhMsg":null,"detailMsg":null,"result":{"token":"45dfa31b-61af-43dc-996b-95bbf1e44331","refreshToken":"8e86f0bcf4adf99d2a3ac6fe1600e354","uid":"e3725c7fbd2d4ef484fb4a50fe3b077b"}}
                        //暂时处理0的状态码，后面与后台配合补上其他的
                        HttpBaseBean baseBean = JsonUtil.json2Bean(s, HttpBaseBean.class);
                        if (baseBean.getRet() == 0) {
                            //0是成功状态码，其余的都是业务失败
                            if (null != onHttpListener) {
                                onHttpListener.onSuccess(baseBean.getResult());
                            }
                        } else {
                            if (null != onHttpListener) {
                                onHttpListener.onError(baseBean.getRet(), null);//业务失败false
                            }
                        }
                    }

                    /** 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程 */
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if (null != onHttpListener) {
                            onHttpListener.onError(StateCode.NO_NET, e);//网络失败false
                        }
                    }
                });
    }

    public interface OnHttpListener {
        void onBefore();

        void onSuccess(Object result);

        void onError(int code, Exception e);
    }

    public abstract static class OnHttpProgressListener implements OnHttpListener {
        public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
        }
    }

    private HttpUtil() {
    }

    private static class SingletonFactory {
        private static HttpUtil instance = new HttpUtil();
    }

    public static HttpUtil getInstance() {
        return SingletonFactory.instance;
    }

    public Object readResolve() {
        return getInstance();
    }
}
