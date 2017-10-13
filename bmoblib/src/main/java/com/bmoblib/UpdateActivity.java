package com.bmoblib;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;

public class UpdateActivity extends Activity {

	String [] arr = {"自动更新","手动更新","静默下载更新","删除文件"};
	ListView mListview;
	BaseAdapter mAdapter;
	UpdateResponse ur;

	public static String APPID = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		
		Toast.makeText(this, "请记得将MainActivity当中的APPID替换为你的appid", Toast.LENGTH_LONG).show();
		
		// 初始化BmobSDK
		Bmob.initialize(this, APPID);	//
		
		//初始化建表操作
		BmobUpdateAgent.initAppVersion();
		
		mListview = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.tv_item, arr);
		mListview.setAdapter(mAdapter);
		mListview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				testAutoUpdate(position + 1);
			}
		});
		
		BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
			
			@Override
			public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
				// TODO Auto-generated method stub
				if (updateStatus == UpdateStatus.Yes) {
					ur = updateInfo;
				}else if(updateStatus==UpdateStatus.IGNORED){//新增忽略版本更新
					Toast.makeText(UpdateActivity.this, "该版本已经被忽略更新", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}
	
	private void testAutoUpdate(int pos){
		switch (pos) {
		case 1:
			// 自动更新方法通常可以放在应用的启动页
			BmobUpdateAgent.update(this);
			break;
		case 2:
			// 手动检查更新
			BmobUpdateAgent.forceUpdate(this);
			break;
		case 3:
			// 静默下载文件后提示更新
			BmobUpdateAgent.silentUpdate(this);
			break;
		case 4:
			// 删除下载的apk文件
			if(ur != null){
				File file = new File(Environment
						.getExternalStorageDirectory(), ur.path_md5 + ".apk");
				if (file != null) {
					if (file.delete()) {
						Toast.makeText(this, "删除完成",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(this, "删除失败",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(UpdateActivity.this, "删除完成", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

}
