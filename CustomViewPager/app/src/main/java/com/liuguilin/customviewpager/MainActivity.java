package com.liuguilin.customviewpager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.liuguilin.customviewpager.entity.Constans;
import com.liuguilin.customviewpager.view.PointView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PointView mPointView;
    private int[] ids = {R.drawable.img1, R.drawable.img2, R.drawable.img3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Constans.WINDOW_WIDTH = getPhoneWidth();
        List<Bitmap> mList = new ArrayList<>();
        mPointView = (PointView) findViewById(R.id.mPointView);
        for (int i = 0; i < ids.length; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), ids[i]);
            mList.add(bitmap);
        }
        mPointView.addBitMap(mList);

        mPointView.setListener(new PointView.onclicklistener() {
            @Override
            public void imgClick(int position) {
                Toast.makeText(MainActivity.this, "我点击了第" + (position + 1) + "张图片", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //获取手机宽度
    private int getPhoneWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
}
