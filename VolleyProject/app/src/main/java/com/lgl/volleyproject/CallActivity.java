package com.lgl.volleyproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 归属地查询
 * Created by LGL on 2016/6/4.
 */
public class CallActivity extends AppCompatActivity {

    //输入框
    private EditText et_number;
    //查询按钮
    private Button btn_call;
    //输入结果
    private TextView tv_resule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        initView();

    }

    /**
     * 初始化View
     */
    private void initView() {

        et_number = (EditText) findViewById(R.id.et_number);
        btn_call = (Button) findViewById(R.id.btn_call);
        tv_resule = (TextView) findViewById(R.id.tv_resule);

        //点击查询
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = et_number.getText().toString();
                //判断不能为空
                if (!TextUtils.isEmpty(phone)) {
                    //解析查询
                    Volley_Get(phone);
                } else {
                    Toast.makeText(CallActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 解析接口
     *
     * @param phone 输入的号码
     */
    private void Volley_Get(String phone) {

        String url = "http://apis.juhe.cn/mobile/get?phone=" + phone + "&key=22a6ba14995ce26dd0002216be51dabb";

        //定义网络请求队列
        RequestQueue queue = Volley.newRequestQueue(this);
        //创建对象
        StringRequest resuest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            //请求成功
            @Override
            public void onResponse(String s) {
                Log.i("json", s);
                getJson(s);
            }
            //请求失败
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(CallActivity.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        //添加到队列,开始执行网络请求
        queue.add(resuest);
    }

    /**
     * 解析json
     *
     * @param s json
     */
    private void getJson(String s) {
        try {
            JSONObject json = new JSONObject(s);
            JSONObject json1 = json.getJSONObject("result");

            tv_resule.setText("归属地：" + json1.getString("province") + "-" + json1.getString("city") + "\n"
                    + "区号：" + json1.getString("areacode") + "\n"
                    + "邮编：" + json1.getString("zip") + "\n"
                    + "运营商：" + json1.getString("company") + "\n"
                    + "类型：" + json1.getString("card"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
