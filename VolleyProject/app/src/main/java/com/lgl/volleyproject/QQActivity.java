package com.lgl.volleyproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * QQ测试吉凶
 * Created by LGL on 2016/6/4.
 */
public class QQActivity extends AppCompatActivity {


    //输入框
    private EditText et_number;
    //测试按钮
    private Button btn_qq;
    //输出结果
    private TextView tv_result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {

        et_number = (EditText) findViewById(R.id.et_number);
        btn_qq = (Button) findViewById(R.id.btn_qq);
        tv_result = (TextView) findViewById(R.id.tv_result);

        //点击事件
        btn_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拿到输入的内容
                String qq = et_number.getText().toString();
                Volley_Get(qq);
            }
        });
    }

    /**
     * 解析接口
     *
     * @param qq 你输入的QQ号码
     */
    private void Volley_Get(String qq) {
        //接口
        String url = "http://japi.juhe.cn/qqevaluate/qq?key=8d9160d4a96f2a6b5316de5b9d14d09d&qq=" + qq;

        //定义网络请求队列
        RequestQueue queue = Volley.newRequestQueue(this);
        //建立对象
        StringRequest string = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("json", s);
                getJson(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        //把对象添加到队列中去
        queue.add(string);

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
            JSONObject json2 = json1.getJSONObject("data");

            //设置数据
            tv_result.setText("测试结果：" + json2.getString("conclusion") + "\n"
                    + "分析结果：" + json2.getString("analysis"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
