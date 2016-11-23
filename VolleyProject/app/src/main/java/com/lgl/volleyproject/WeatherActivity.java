package com.lgl.volleyproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 天气预报
 * Created by LGL on 2016/6/4.
 */
public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {

    //输入框
    private EditText et_city;
    //点击按钮
    private Button btn_weather;

    /**
     * 今天 明天  后天  日期 天气  温度
     */

    private TextView tv_today_date, tv_today_weather, tv_today_tmp;
    private TextView tv_tomorrow_date, tv_tomorrow_weather, tv_tomorrow_tmp;
    private TextView tv_after_date, tv_after_weather, tv_after_tmp;


    //数据源
    private List<String> DateList = new ArrayList<>();
    private List<String> WeatherList = new ArrayList<>();
    private List<String> TmpList = new ArrayList<>();


    //天气状态
    private ImageView iv_today, iv_tomotrrow, iv_after;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {

        et_city = (EditText) findViewById(R.id.et_city);

        btn_weather = (Button) findViewById(R.id.btn_weather);
        btn_weather.setOnClickListener(this);

        tv_today_date = (TextView) findViewById(R.id.tv_today_date);
        tv_today_weather = (TextView) findViewById(R.id.tv_today_weather);
        tv_today_tmp = (TextView) findViewById(R.id.tv_today_tmp);

        tv_tomorrow_date = (TextView) findViewById(R.id.tv_tomorrow_date);
        tv_tomorrow_weather = (TextView) findViewById(R.id.tv_tomorrow_weather);
        tv_tomorrow_tmp = (TextView) findViewById(R.id.tv_tomorrow_tmp);

        tv_after_date = (TextView) findViewById(R.id.tv_after_date);
        tv_after_weather = (TextView) findViewById(R.id.tv_after_weather);
        tv_after_tmp = (TextView) findViewById(R.id.tv_after_tmp);

        iv_today = (ImageView) findViewById(R.id.iv_today);
        iv_tomotrrow = (ImageView) findViewById(R.id.iv_tomotrrow);
        iv_after = (ImageView) findViewById(R.id.iv_after);

    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_weather:
                String city = et_city.getText().toString();
                Volley_Get(city);
                break;
        }
    }

    /**
     * 解析接口
     *
     * @param city 城市
     */
    private void Volley_Get(String city) {
        String url = "https://api.heweather.com/x3/weather?city=" + city + "&key=cd6a56ab6f4544a4b5d8206064551f85";

        //创建网络请求队列
        RequestQueue queue = Volley.newRequestQueue(this);
        //创建对象
        StringRequest string = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                getJson(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        //把对象添加到请求队列中去
        queue.add(string);
    }

    /**
     * 解析json
     *
     * @param s
     */
    private void getJson(String s) {
        try {
            JSONObject json = new JSONObject(s);
            JSONArray jsonarray = json.getJSONArray("HeWeather data service 3.0");

            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject json1 = (JSONObject) jsonarray.get(i);
                JSONArray jsonarray1 = json1.getJSONArray("daily_forecast");
                for (int j = 0; j < jsonarray1.length(); j++) {
                    JSONObject json2 = (JSONObject) jsonarray1.get(j);

                    //收集数据
                    DateList.add(json2.getString("date"));
                    WeatherList.add(json2.getJSONObject("cond").getString("txt_d"));

                    JSONObject json3 = json2.getJSONObject("tmp");

                    TmpList.add(json3.getString("min") + "°-" + json3.getString("max") + "°");

                }
            }

            //设置参数


            tv_today_date.setText(DateList.get(0));
            tv_tomorrow_date.setText(DateList.get(1));
            tv_after_date.setText(DateList.get(2));

            tv_today_weather.setText(WeatherList.get(0));
            tv_tomorrow_weather.setText(WeatherList.get(1));
            tv_after_weather.setText(WeatherList.get(2));

            tv_today_tmp.setText(TmpList.get(0));
            tv_tomorrow_tmp.setText(TmpList.get(1));
            tv_after_tmp.setText(TmpList.get(2));


            if (WeatherList.get(0).equals("多云")) {
                iv_today.setImageResource(R.mipmap.duoyun);
            } else if (WeatherList.get(0).equals("晴")) {
                iv_today.setImageResource(R.mipmap.qing);
            } else if (WeatherList.get(0).equals("暴雨")) {
                iv_today.setImageResource(R.mipmap.baoyu);
            } else {
                iv_today.setImageResource(R.mipmap.other);
            }


            if (WeatherList.get(1).equals("多云")) {
                iv_tomotrrow.setImageResource(R.mipmap.duoyun);
            } else if (WeatherList.get(1).equals("晴")) {
                iv_tomotrrow.setImageResource(R.mipmap.qing);
            } else if (WeatherList.get(1).equals("暴雨")) {
                iv_tomotrrow.setImageResource(R.mipmap.baoyu);
            } else {
                iv_tomotrrow.setImageResource(R.mipmap.other);
            }



            if (WeatherList.get(2).equals("多云")) {
                iv_after.setImageResource(R.mipmap.duoyun);
            } else if (WeatherList.get(2).equals("晴")) {
                iv_after.setImageResource(R.mipmap.qing);
            } else if (WeatherList.get(2).equals("暴雨")) {
                iv_after.setImageResource(R.mipmap.baoyu);
            } else {
                iv_after.setImageResource(R.mipmap.other);
            }

            //清除数据源
            DateList.clear();
            WeatherList.clear();
            TmpList.clear();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

