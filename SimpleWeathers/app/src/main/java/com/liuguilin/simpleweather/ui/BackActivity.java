package com.liuguilin.simpleweather.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liuguilin.simpleweather.R;

/*
 *项目名： SimpleWeather
 *包名：   com.liuguilin.simpleweather.ui
 *文件名:  BackActivity
 *创建者:  LGL
 *创建时间:2016/11/251:11
 *描述:    意见反馈
 */
public class BackActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEt_phone;
    private EditText mEt_content;
    private Button mBtn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);

        initView();
    }

    private void initView() {
        mEt_phone = (EditText) findViewById(R.id.et_phone);
        mEt_content = (EditText) findViewById(R.id.et_content);
        mBtn_send = (Button) findViewById(R.id.btn_send);
        mBtn_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                String phone = mEt_phone.getText().toString().trim();
                String context = mEt_content.getText().toString().trim();

                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(context)) {
                    Toast.makeText(this, "反馈成功", Toast.LENGTH_SHORT).show();
                    mEt_phone.setText("");
                    mEt_content.setText("");

                    //这里就直接反馈
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
