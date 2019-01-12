package com.oklib.bean;

import com.orm.SugarRecord;

/**
 * 时间：2017/9/19
 * 作者：蓝天
 * 描述：
 */

public class Book extends SugarRecord {
    //注意：该注解会使得该记录唯一，根据id偏移
//    @Unique
    public String isbn="1";
    public String title="测试";
    public String edition="编写";

    // Default constructor is necessary for SugarRecord
    public Book() {

    }

    public Book(String isbn, String title, String edition) {
        this.isbn = isbn;
        this.title = title;
        this.edition = edition;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", edition='" + edition + '\'' +
                '}';
    }
}
