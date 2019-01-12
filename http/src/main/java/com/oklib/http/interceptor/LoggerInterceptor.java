package com.oklib.http.interceptor;

import android.text.TextUtils;
import android.util.Log;

import com.oklib.http.utils.HttpMD5Util;
import com.oklib.http.utils.OkLogger;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;


/**
 * 拦截请求、响应（请求头、请求体）打印处理
 */
public class LoggerInterceptor implements Interceptor {
    public static final String TAG = "OkGo";
    private String tag;
    private boolean showResponse;

    public LoggerInterceptor(String tag) {
        this(tag, false);
    }

    public LoggerInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }


    HashMap<String, Request> requestHashMap = new HashMap<>();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        requestHashMap.put(HttpMD5Util.getMD5(request.url().toString()), request);
        //logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            Log.e(tag, "===>request");
            Log.e(tag, "method : " + request.method());
            Log.e(tag, "url : " + url);
            if (headers != null && headers.size() > 0) {
                Log.e(tag, "headers : \n");
                //Log.e(tag, headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    Log.e(tag, "contentType : " + mediaType.toString());
                    if (isText(mediaType)) {
                        Log.e(tag, "content : " + bodyToString(request));
                    } else {
                        Log.e(tag, "content : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
        } catch (Exception e) {
            OkLogger.e(e);
        } finally {
        }
    }

    private Response logForResponse(Response response) {
        try {
            Log.e(tag, "---------------------http log start---------------------");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();

            logForRequest(requestHashMap.get(HttpMD5Util.getMD5(clone.request().url().toString())));

            Log.e(tag, "===>response");
            //Log.e(tag, "url : " + clone.request().url().toString());
            //Log.e(tag, "code : " + clone.code());
            //Log.e(tag, "protocol : " + clone.protocol());
            //if (!TextUtils.isEmpty(clone.message())) Log.e(tag, "message : " + clone.message());

            if (showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        //Log.e(tag, "contentType : " + mediaType.toString());
                        if (isText(mediaType)) {
                            String resp = body.string();
                            //Log.e(tag, "content : " + resp);
                            Logger.json(resp);
                            //Logger.e(resp);
                            body = ResponseBody.create(mediaType, resp);
                            return response.newBuilder().body(body).build();
                        } else {
                            Log.e(tag, "content : " + " maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }
        } catch (Exception e) {
            OkLogger.e(e);
        } finally {
            Log.e(tag, "---------------------http log end-----------------------");
        }

        return response;
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.toString().equals("application/x-www-form-urlencoded") ||
                    mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")) //
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
