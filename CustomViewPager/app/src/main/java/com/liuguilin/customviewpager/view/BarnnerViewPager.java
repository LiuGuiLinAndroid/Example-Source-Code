package com.liuguilin.customviewpager.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;

/*
 *  项目名：  CustomViewPager 
 *  包名：    com.liuguilin.customviewpager.view
 *  文件名:   BarnnerViewPager
 *  创建者:   刘某人程序员
 *  创建时间:  2017/3/27 19:48
 *  描述：    自定义View
 */
public class BarnnerViewPager extends ViewGroup {

    private static final int HANDLER_AUTO = 1000;

    //viewgroup子view个数
    private int childSize;
    //子view的宽高
    private int width, height;

    //第一次按下的x
    private int x;
    //当前下标
    private int index;

    //是否点击
    private boolean isClick;

    private onClicklistener onClicklistener;

    public BarnnerViewPager.onClicklistener getOnClicklistener() {
        return onClicklistener;
    }

    public void setOnClicklistener(BarnnerViewPager.onClicklistener onClicklistener) {
        this.onClicklistener = onClicklistener;
    }

    private onSelectorImageViewListener selectorImageViewListener;

    public onSelectorImageViewListener getSelectorImageViewListener() {
        return selectorImageViewListener;
    }

    public void setSelectorImageViewListener(onSelectorImageViewListener selectorImageViewListener) {
        this.selectorImageViewListener = selectorImageViewListener;
    }

    //另一种滑动方式
    private Scroller scroller;

    //是否启动轮播
    private boolean isAuto = true;
    //定时器
    private Timer timer = new Timer();
    private TimerTask timerTask;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_AUTO:
                    //说明是最后一张图，那么切换到第一张
                    if (++index >= childSize) {
                        index = 0;
                    }
                    scrollTo(width * index, 0);
                    selectorImageViewListener.onSelector(index);
                    break;
            }
        }
    };

    public BarnnerViewPager(Context context) {
        super(context);
        init();
    }

    public BarnnerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarnnerViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化
    private void init() {
        scroller = new Scroller(getContext());
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (isAuto) {
                    handler.sendEmptyMessage(HANDLER_AUTO);
                }
            }
        };
        timer.schedule(timerTask, 100, 3000);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //判断是否在滑动
        if (scroller.computeScrollOffset()) {
            //水平滑动 y不需要
            scrollTo(scroller.getCurrX(), 0);
            //刷新
            invalidate();
        }
    }

    //测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取子view的个数
        childSize = getChildCount();
        if (childSize == 0) {
            setMeasuredDimension(0, 0);
        } else {
            //系统测量宽高
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            //获取第一个子view
            View view = getChildAt(0);
            //获取子view的高度
            height = view.getMeasuredHeight();
            //宽度
            width = view.getMeasuredWidth() * childSize;
            setMeasuredDimension(width, height);
        }
    }

    /**
     * 继承ViewGroup所需实现的方法
     *
     * @param changed 当我们viewgroup布局位置发生改变为true,否则为false
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //如果发生改变
        if (changed) {
            //相对子view的宽度
            int leftMargin = 0;
            for (int i = 0; i < childSize; i++) {
                //所有的子view
                View view = getChildAt(i);
                //为子view布局
                view.layout(leftMargin, 0, leftMargin + width, height);
                //累加
                leftMargin += width;
            }
        }
    }

    //触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            //按下
            case MotionEvent.ACTION_DOWN:
                stopAuto();
                isClick = true;
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                x = (int) event.getX();
                break;
            //移动
            case MotionEvent.ACTION_MOVE:
                isClick = false;
                //移动后的坐标
                int moveX = (int) event.getX();
                //移动的距离
                int distance = moveX - x;
                scrollBy(-distance, 0);
                x = moveX;
                break;
            //向上
            case MotionEvent.ACTION_UP:
                //计算移动的位置
                int scrollX = getScrollX();
                index = (scrollX + width / 2) / width;
                if (index < 0) {
                    //此时已经滑动到最左边
                    index = 0;
                } else if (index > childSize - 1) {
                    //此时已经滑动到最右边
                    index = childSize - 1;
                }
                startAuto();
                if (isClick) {
                    //代表点击
                    onClicklistener.imgClick(index);
                } else {
                    //计算滑动的距离
                    int dx = index * width - scrollX;
                    //滑动
                    scroller.startScroll(scrollX, 0, dx, 0);
                    postInvalidate();
                    selectorImageViewListener.onSelector(index);
                }
                break;
        }
        //处理事件
        return true;
    }

    //开始轮播
    private void startAuto() {
        isAuto = true;
    }

    //停止轮播
    private void stopAuto() {
        isAuto = false;
    }

    //点击事件
    public interface onClicklistener {
        void imgClick(int position);
    }
    //切换事件
    public interface onSelectorImageViewListener{
        void onSelector(int position);
    }
}
