package com.oklib.mix_lib.qrcode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.oklib.R;
import com.oklib.base.BaseAppActivity;
import com.oklib.zxing.util.GenerateQRCodeUtils;


/**
 * 时间：2017/9/12
 * 作者：蓝天
 * 描述：生成二维码
 */

public class GenerateQRCodeActivity extends BaseAppActivity {
    @Override
    protected int initLayoutId() {
        return R.layout.activity_generate_qrcode;
    }

    private Bitmap mBitmap;
    @Override
    protected void initView() {

        final EditText et_inputContent = (EditText) findViewById(R.id.et_inputContent);
        Button btn_logoQRImage = (Button) findViewById(R.id.btn_logoQRImage);
        Button btn_QRImage = (Button) findViewById(R.id.btn_QRImage);
        final ImageView iv_qr_image = (ImageView) findViewById(R.id.iv_qr_image);

        //生成带logo的二维码
        btn_logoQRImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textContent = et_inputContent.getText().toString();
                if (TextUtils.isEmpty(textContent)) {
                    Toast.makeText(GenerateQRCodeActivity.this, "您的输入为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                et_inputContent.setText("");
                mBitmap = GenerateQRCodeUtils.createImage(textContent, 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                iv_qr_image.setImageBitmap(mBitmap);
            }
        });

        //生成普通二维码
        btn_QRImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textContent = et_inputContent.getText().toString();
                if (TextUtils.isEmpty(textContent)) {
                    Toast.makeText(GenerateQRCodeActivity.this, "您的输入为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                et_inputContent.setText("");
                mBitmap = GenerateQRCodeUtils.createImage(textContent, 400, 400, null);
                iv_qr_image.setImageBitmap(mBitmap);
            }
        });
    }

}
