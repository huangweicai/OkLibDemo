package com.oklib.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * 时间：2017/9/5
 * 作者：蓝天
 * 描述：
 */

public class MainBean extends SugarRecord implements Parcelable {
    private String title;
    private Class cls;
    private String url;

    public MainBean() {
    }
    public MainBean(String title, Class cls) {
        this.title = title;
        this.cls = cls;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getCls() {
        return cls;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeSerializable(this.cls);
        dest.writeString(this.url);
    }

    protected MainBean(Parcel in) {
        this.title = in.readString();
        this.cls = (Class) in.readSerializable();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<MainBean> CREATOR = new Parcelable.Creator<MainBean>() {
        @Override
        public MainBean createFromParcel(Parcel source) {
            return new MainBean(source);
        }

        @Override
        public MainBean[] newArray(int size) {
            return new MainBean[size];
        }
    };
}
