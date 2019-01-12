package com.oklib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;
 /**
   * 时间：2017/11/21
   * 作者：蓝天
   * 描述：验证码，倒数恢复view
   */
@SuppressLint("AppCompatCustomView")
public class RecodeView extends Button {

	private static final int TIME_OUT = 60;
	private Timer timer;

	private int count = TIME_OUT;

	private View root;

	private String str;

	public RecodeView(Context context) {
		super(context);
		str=getText().toString();
		// keepGoing();
	}

	public RecodeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
			str=getText().toString();
		// keepGoing();
	}

	public void startTime() {

		// if (timer != null) {
		// return;
		// }
		this.setClickable(false);

		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				handler.sendEmptyMessage(0);
				count--;
				if (count < 1) {
					count = TIME_OUT;
					timer.cancel();
					timer = null;
					handler.sendEmptyMessage(-1);
				}

			}
		}, 0, 1000);
	}

	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 0:
				if (count <= 0) {

					RecodeView.this.setText(str);
					RecodeView.this.setClickable(true);
				} else {
					RecodeView.this.setClickable(false);
					RecodeView.this.setText(count + " 秒");

				}
				break;

			case -1:
				RecodeView.this.setClickable(true);
				RecodeView.this.setText(str);
				break;
			}

		}

	};


	public void reset() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		count = TIME_OUT;
		this.setText(str);
		this.setClickable(true);
	}


}
