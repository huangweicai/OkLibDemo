package com.oklib.view.letters_nav.bean;


import android.os.Parcel;
import android.os.Parcelable;

import com.oklib.view.letters_nav.Letter;


/**
 * 创建时间 2017/2/13
 * 编写者：黄伟才
 * 功能描述：字母导航列表数据载体，如需要更多属性，请自行匹配
 * （备注：注解排序、序列化)
 */

public class LettersNavBean extends SortModel implements Parcelable {

    private String id;
    @Letter(isSortField = true)
    private String lettersNavTitle;//字母匹配“标题�?�属性，必须

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLettersNavTitle() {
        return lettersNavTitle;
    }

    public void setLettersNavTitle(String lettersNavTitle) {
        this.lettersNavTitle = lettersNavTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.lettersNavTitle);
    }

    public LettersNavBean(String lettersNavTitle) {
        this.lettersNavTitle = lettersNavTitle;
    }

    public LettersNavBean() {
    }

    protected LettersNavBean(Parcel in) {
        this.id = in.readString();
        this.lettersNavTitle = in.readString();
    }

    public static final Creator<LettersNavBean> CREATOR = new Creator<LettersNavBean>() {
        @Override
        public LettersNavBean createFromParcel(Parcel source) {
            return new LettersNavBean(source);
        }

        @Override
        public LettersNavBean[] newArray(int size) {
            return new LettersNavBean[size];
        }
    };
}
