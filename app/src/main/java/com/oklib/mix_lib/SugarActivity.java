package com.oklib.mix_lib;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.bean.Book;

/**
 * 时间：2017/9/22
 * 作者：蓝天
 * 描述：sugar数据库
 * 注意：下面只是提供大概的操作方式，具体使用需要查阅suger数据库使用
 */

public class SugarActivity extends BaseAppActivity implements View.OnClickListener {
    private TextView tv_add_record;
    private TextView tv_delete_record;
    private TextView tv_alter_record;
    private TextView tv_query_record;
    private TextView tv_test;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_sugar;
    }

    @Override
    protected void initView() {
        tv_add_record = (TextView) findViewById(R.id.tv_add_record);
        tv_delete_record = (TextView) findViewById(R.id.tv_delete_record);
        tv_alter_record = (TextView) findViewById(R.id.tv_alter_record);
        tv_query_record = (TextView) findViewById(R.id.tv_query_record);
        tv_test = (TextView) findViewById(R.id.tv_test);
        tv_add_record.setOnClickListener(this);
        tv_delete_record.setOnClickListener(this);
        tv_alter_record.setOnClickListener(this);
        tv_query_record.setOnClickListener(this);
    }

    StringBuilder sb = new StringBuilder();
    int count = 0;
    @Override
    public void onClick(View view) {
        Book book = null;
        switch (view.getId()) {
            case R.id.tv_add_record:
                //单条插入
                count++;
                book = new Book("---", "title"+count, "edition"+count);
                book.save();
                //SugarRecord.save(book); // if using the @Table annotation

                //批量插入，一次性的
//                List<Book> books = new ArrayList<>();
//                books.add(new Book("isbn123", "Title here 1", "1nd edition"));
//                books.add(new Book("isbn456", "Title here 2", "2nd edition"));
//                books.add(new Book("isbn789", "Title here 3", "3nd edition"));
//                SugarRecord.saveInTx(books);
                break;
            case R.id.tv_delete_record:
                //根据id查询
                book = Book.findById(Book.class, 1);
                book.delete();

                //批量删除
                //List<Book> books = Book.listAll(Book.class);
                //Book.deleteAll(Book.class);
                break;
            case R.id.tv_alter_record:
                book = Book.findById(Book.class, 1);
                book.title = "updated title here"; // modify the values
                book.edition = "3rd edition";
                book.save(); // updates the previous entry with new values.
                break;
            case R.id.tv_query_record:
                Book book1 = Book.findById(Book.class, 1);//参数二：1是记录id
                Book book2 = Book.findById(Book.class, 2);
                Book book3 = Book.findById(Book.class, 3);
                Book book4 = Book.findById(Book.class, 4);
                Book book5 = Book.findById(Book.class, 5);
                Log.d("TAG", "bookToString:" + (null == book1 ? "book1表为null" : book1.toString()));
                Log.d("TAG", "bookToString:" + (null == book2 ? "book2表为null" : book2.toString()));
                Log.d("TAG", "bookToString:" + (null == book3 ? "book3表为null" : book3.toString()));
                Log.d("TAG", "bookToString:" + (null == book4 ? "book4表为null" : book4.toString()));
                Log.d("TAG", "bookToString:" + (null == book5 ? "book5表为null" : book5.toString()));

                sb.setLength(0);
//                sb.append("bookToString:" + (null == book1 ? "book1表为null" : book1.toString()) + "\n");
//                sb.append("bookToString:" + (null == book2 ? "book2表为null" : book2.toString()) + "\n");
//                sb.append("bookToString:" + (null == book3 ? "book1表为null" : book3.toString()) + "\n");
//                sb.append("bookToString:" + (null == book4 ? "book1表为null" : book4.toString()) + "\n");
//                sb.append("bookToString:" + (null == book5 ? "book1表为null" : book5.toString()) + "\n");


                //指定条件查询，批量回来
                //List<Book> books = Book.find(Book.class, "title = ? and edition = ?", "Title here", "2nd edition");
                //Log.d("TAG", "bookToString:" + (null == books ? "books表为null" : books.toString()));

                //查询表中所有记录
                Log.d("TAG", "---list---"+Book.listAll(Book.class));
                //Log.d("TAG", "===list==="+Select.from(Book.class).list());
                sb.append("直接获取表全部数据：" + Book.listAll(Book.class)+"\n");

                tv_test.setText(sb.toString());
                break;
        }
    }
}
