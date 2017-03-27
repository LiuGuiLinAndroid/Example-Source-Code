package com.liuguilin.customviewpager.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.liuguilin.customviewpager.R;
import com.liuguilin.customviewpager.entity.Constans;

import java.util.List;

/*
 *  项目名：  CustomViewPager 
 *  包名：    com.liuguilin.customviewpager.view
 *  文件名:   PointView
 *  创建者:   刘某人程序员
 *  创建时间:  2017/3/27 23:07
 *  描述：    轮播圆
 */
public class PointView extends FrameLayout {

    private BarnnerViewPager barnnerViewPager;
    private LinearLayout linear;

    private onclicklistener listener;

    public onclicklistener getListener() {
        return listener;
    }

    public void setListener(onclicklistener listener) {
        this.listener = listener;
    }

    public PointView(Context context) {
        super(context);
        initViewGroup();
        initDot();
    }

    public PointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewGroup();
        initDot();
    }

    public PointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewGroup();
        initDot();
    }

    //初始化图片轮播
    private void initViewGroup() {
        barnnerViewPager = new BarnnerViewPager(getContext());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        barnnerViewPager.setLayoutParams(lp);
        addView(barnnerViewPager);

        //轮播监听
        barnnerViewPager.setSelectorImageViewListener(new BarnnerViewPager.onSelectorImageViewListener() {
            @Override
            public void onSelector(int position) {
                int count = linear.getChildCount();
                for (int i = 0; i < count; i++) {
                    ImageView iv = (ImageView) linear.getChildAt(i);
                    if (i == position) {
                        iv.setBackgroundResource(R.drawable.dot_point_selector);
                    } else {
                        iv.setBackgroundResource(R.drawable.dot_point_normel);
                    }
                }
            }
        });

        //点击事件
        barnnerViewPager.setOnClicklistener(new BarnnerViewPager.onClicklistener() {
            @Override
            public void imgClick(int position) {
                listener.imgClick(position);
            }
        });
    }

    //初始化小圆点
    private void initDot() {
        linear = new LinearLayout(getContext());
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 80);
        linear.setLayoutParams(lp);
        linear.setOrientation(LinearLayout.HORIZONTAL);
        linear.setGravity(Gravity.CENTER);
        linear.setBackgroundColor(Color.BLACK);
        addView(linear);

        FrameLayout.LayoutParams lParams = (LayoutParams) linear.getLayoutParams();
        linear.setLayoutParams(lParams);
        lParams.gravity = Gravity.BOTTOM;

        //设置透明度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            linear.setAlpha(0.3f);
        } else {
            linear.getBackground().setAlpha(100);
        }
    }

    //添加图片
    public void addBitMap(List<Bitmap> mList) {
        for (int i = 0; i < mList.size(); i++) {
            Bitmap bitmap = mList.get(i);
            addBitmapToViewPager(bitmap);
            addDotToPointView();
        }
    }

    private void addDotToPointView() {
        ImageView imageview = new ImageView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5, 5, 5, 5);
        imageview.setLayoutParams(lp);
        imageview.setBackgroundResource(R.drawable.dot_point_normel);
        linear.addView(imageview);
    }

    private void addBitmapToViewPager(Bitmap bitmap) {
        ImageView imageview = new ImageView(getContext());
        imageview.setLayoutParams(new ViewGroup.LayoutParams(Constans.WINDOW_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageview.setImageBitmap(bitmap);
        barnnerViewPager.addView(imageview);
    }

    //点击事件
    public interface onclicklistener {
        void imgClick(int position);
    }
}
