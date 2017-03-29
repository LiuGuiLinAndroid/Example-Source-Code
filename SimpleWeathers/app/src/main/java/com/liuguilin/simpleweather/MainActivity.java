package com.liuguilin.simpleweather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.liuguilin.simpleweather.data.WeatherDataBean;
import com.liuguilin.simpleweather.entity.Constans;
import com.liuguilin.simpleweather.impl.WeatherImpl;
import com.liuguilin.simpleweather.ui.SettingActivity;
import com.liuguilin.simpleweather.utils.SharePreUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnChartGestureListener, OnChartValueSelectedListener {

    private LinearLayout mLlRefresh;
    private ImageView mIvRefresh;
    private TextView mTvNowTime;
    private TextView mTvCity;
    private TextView mTvUpdateTime;
    private TextView mTvWeek;
    private ImageView mIvWeatherIcon;
    private TextView mTvCode;
    private TextView mTvWeather;
    private TextView mTvWind;
    private TextView mTvWindCode;

    //定位服务
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    //提示框
    private ProgressDialog dialog;
    //城市
    private String city = "深圳";
    //动画
    private Animation anim;

    private WeatherImpl impl;
    private LineChart mLineChart;

    private int mColor = 0xFF2AA0E7;

    //指标建议
    private ArrayList<String> mList = new ArrayList<>();

    //数据集
    private ArrayList<Entry> values = new ArrayList<>();

    //星期数组
    private String[] mWeek = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    //天气图标
    private int[] mIcon = {R.mipmap.weather_icon_white_01, R.mipmap.weather_icon_white_02, R.mipmap.weather_icon_white_03,
            R.mipmap.weather_icon_white_04, R.mipmap.weather_icon_white_05, R.mipmap.weather_icon_white_06,
            R.mipmap.weather_icon_white_07, R.mipmap.weather_icon_white_08, R.mipmap.weather_icon_white_09,
            R.mipmap.weather_icon_white_10, R.mipmap.weather_icon_white_11, R.mipmap.weather_icon_white_12,
            R.mipmap.weather_icon_white_13, R.mipmap.weather_icon_white_14, R.mipmap.weather_icon_white_15,
            R.mipmap.weather_icon_white_16, R.mipmap.weather_icon_white_17, R.mipmap.weather_icon_white_18,
            R.mipmap.weather_icon_white_19, R.mipmap.weather_icon_white_20, R.mipmap.weather_icon_white_21,
            R.mipmap.weather_icon_white_22, R.mipmap.weather_icon_white_23, R.mipmap.weather_icon_white_24,
            R.mipmap.weather_icon_white_25};

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //动画停止
                case 1000:
                    if (anim != null) {
                        mIvRefresh.clearAnimation();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去掉ActionBar阴影
        getSupportActionBar().setElevation(0);

        initView();
    }

    //初始化View
    private void initView() {

        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);

        initLocation();
        initDialog();
        initRetrofit();
        initLineChart();
        dialog.show();
        //开启定位
        mLocationClient.start();
        Log.i(Constans.TAG, "开始定位");

        mLlRefresh = (LinearLayout) findViewById(R.id.llRefresh);
        mLlRefresh.setOnClickListener(this);
        mIvRefresh = (ImageView) findViewById(R.id.ivRefresh);
        mTvNowTime = (TextView) findViewById(R.id.tvNowTime);
        mTvCity = (TextView) findViewById(R.id.tvCity);
        mTvUpdateTime = (TextView) findViewById(R.id.tvUpdateTime);
        mTvWeek = (TextView) findViewById(R.id.tvWeek);
        mIvWeatherIcon = (ImageView) findViewById(R.id.ivWeatherIcon);
        mTvCode = (TextView) findViewById(R.id.tvCode);
        mTvWeather = (TextView) findViewById(R.id.tvWeather);
        mTvWind = (TextView) findViewById(R.id.tvWind);
        mTvWindCode = (TextView) findViewById(R.id.tvWindCode);

        mTvNowTime.setText(SharePreUtils.getString(this, "now_time", "刷新"));

    }

    //初始化折线图
    private void initLineChart() {
        mLineChart = (LineChart) findViewById(R.id.mLineChart);
        //设置手势滑动事件
        mLineChart.setOnChartGestureListener(this);
        //设置数值选择监听
        mLineChart.setOnChartValueSelectedListener(this);
        //设置后台绘制
        mLineChart.setDrawGridBackground(false);
        //不需要描述文本
        mLineChart.getDescription().setEnabled(false);
        //支持触摸手势
        mLineChart.setTouchEnabled(true);
        // 设置缩放/滑动
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        mLineChart.setPinchZoom(true);

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        //设置最大值
        xAxis.setAxisMaximum(5);
        //最小
        xAxis.setAxisMinimum(1);
        //设置间隔 1 天
        xAxis.setGranularity(1);

        LimitLine ll1 = new LimitLine(30, "高温");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
        ll1.setLineColor(mColor);

        LimitLine ll2 = new LimitLine(0, "低温");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);
        ll2.setLineColor(mColor);

        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(60);
        leftAxis.setAxisMinimum(-30);
        leftAxis.setGranularity(10);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        leftAxis.setDrawLimitLinesBehindData(true);

        mLineChart.getAxisRight().setEnabled(false);

        mLineChart.animateY(2500);
    }

    //设置数据
    private void setData(ArrayList<Entry> values) {
        Log.i(Constans.TAG, values.toString());
        LineDataSet set1;
        if (mLineChart.getData() != null &&
                mLineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mLineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mLineChart.getData().notifyDataChanged();
            mLineChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "一周天气近况");
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(mColor);
            set1.setCircleColor(mColor);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);
            set1.setFillColor(mColor);
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            mLineChart.setData(data);
        }
    }

    //初始化dialog
    private void initDialog() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在努力定位中...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //屏幕外点击无效
        dialog.setCancelable(false);
    }

    //初始化Retrofit
    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://op.juhe.cn/")
                .build();

        impl = retrofit.create(WeatherImpl.class);
    }

    //请求天气
    private void requstWeather(String city) {
        //异步操作
        impl.getWeather(city, Constans.WEATHER_KEY).enqueue(new Callback<WeatherDataBean>() {
            @Override
            public void onResponse(Call<WeatherDataBean> call, Response<WeatherDataBean> response) {
                //Log.i(Constans.TAG, response.body().getResult().getData().getRealtime().getDate());
                WeatherDataBean bean = response.body();
                //城市
                mTvCity.setText(bean.getResult().getData().getRealtime().getCity_name());
                //更新时间
                mTvUpdateTime.setText(bean.getResult().getData().getRealtime().getTime());
                //农历/星期
                mTvWeek.setText("农历:" + bean.getResult().getData().getRealtime().getMoon() + " | " +
                        mWeek[bean.getResult().getData().getRealtime().getWeek()]);
                //度数
                mTvCode.setText(bean.getResult().getData().getRealtime().getWeather().getTemperature() + "°");
                //天气
                mTvWeather.setText(bean.getResult().getData().getRealtime().getWeather().getInfo());

                //风力
                mTvWindCode.setText("风力: " + bean.getResult().getData().getRealtime().getWind().getPower());
                //风向
                mTvWind.setText("风向: " + bean.getResult().getData().getRealtime().getWind().getDirect());

                //图标
                String img = bean.getResult().getData().getRealtime().getWeather().getImg();
                int imgCode = Integer.parseInt(img);
                if (imgCode <= 25) {
                    mIvWeatherIcon.setBackgroundResource(mIcon[imgCode - 1]);
                } else {
                    mIvWeatherIcon.setBackgroundResource(mIcon[0]);
                }

                //折线图数据
                for (int i = 0; i < bean.getResult().getData().getWeather().size(); i++) {
                    //Log.i(Constans.TAG, bean.getResult().getData().getWeather().size() + "");
                    String weatherCode = bean.getResult().getData().getWeather().get(i).getInfo().getDay().get(2);
                    int code = Integer.parseInt(weatherCode);
                    Log.i(Constans.TAG, i + "");
                    values.add(new Entry(i + 1, code));
                }
                //设置数据
                setData(values);

                mList.clear();

                //添加指标数据
                mList.add("穿衣:" + bean.getResult().getData().getLife().getInfo().getChuanyi());
                mList.add("感冒:" + bean.getResult().getData().getLife().getInfo().getGanmao());
                mList.add("空调:" + bean.getResult().getData().getLife().getInfo().getKongtiao());
                mList.add("洗车:" + bean.getResult().getData().getLife().getInfo().getXiche());
                mList.add("运动:" + bean.getResult().getData().getLife().getInfo().getYundong());
                mList.add("紫外线:" + bean.getResult().getData().getLife().getInfo().getZiwaixian());
            }

            @Override
            public void onFailure(Call<WeatherDataBean> call, Throwable t) {

            }
        });
    }

    //初始化定位
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        int span = 0;
        option.setScanSpan(span);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIsNeedLocationDescribe(true);
        option.setIsNeedLocationPoiList(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llRefresh:
                startAnimation();
                handler.sendEmptyMessageDelayed(1000, 3000);
                //设置当前时间
                mTvNowTime.setText(getNowTime());
                requstWeather(city);
                break;
        }
    }

    //获取当前时间
    private String getNowTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(new Date());
    }

    //开始动画
    private void startAnimation() {
        anim = AnimationUtils.loadAnimation(this, R.anim.iv_rotating);
        LinearInterpolator ll = new LinearInterpolator();
        anim.setInterpolator(ll);
        if (anim != null) {
            mIvRefresh.startAnimation(anim);
        }
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    //定位回调
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            dialog.dismiss();
            switch (location.getLocType()) {
                case BDLocation.TypeGpsLocation:
                case BDLocation.TypeNetWorkLocation:
                case BDLocation.TypeOffLineLocation:
                    city = location.getCity();
                    Log.i(Constans.TAG, "定位成功:" + city);
                    break;
                case BDLocation.TypeServerError:
                case BDLocation.TypeNetWorkException:
                case BDLocation.TypeCriteriaException:
                    Log.i(Constans.TAG, "定位失敗");
                    break;
            }
            //请求
            requstWeather(city);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharePreUtils.putString(this, "now_time", mTvNowTime.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_setting:
                Intent intent = new Intent(this, SettingActivity.class);
                intent.putStringArrayListExtra("info", mList);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

