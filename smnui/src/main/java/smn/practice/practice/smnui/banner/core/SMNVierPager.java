package smn.practice.practice.smnui.banner.core;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;


public class SMNVierPager extends ViewPager {

    private int mIntervalTime;
    private boolean mAutoPlay = true;

    // 是否有调用过onLayout方法
    private boolean isLayout;

    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            next();
            // 切换到下一个
            mHandler.postDelayed(this, mIntervalTime);
        }
    };

    public SMNVierPager(@NonNull Context context) {
        super(context);
    }

    public SMNVierPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAutoPlay(boolean autoPlay) {

        this.mAutoPlay = autoPlay;
        if (!mAutoPlay) {
            mHandler.removeCallbacks(mRunnable);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                start();
                break;

            default:
                stop();
                break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        isLayout = true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (isLayout && getAdapter() != null && getAdapter().getCount() > 0) {
            try {

                // 解决使用 RecyclerView  + View的问题
                Field scroll = ViewPager.class.getDeclaredField("mFirstLayout");
                scroll.setAccessible(true);
                scroll.set(this, false);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        start();
    }


    @Override
    protected void onDetachedFromWindow() {

        // 解决使用 RecyclerView  + View的问题
        if (((Activity) getContext()).isFinishing()) {
            super.onDetachedFromWindow();
        }

        stop();

    }

    /**
     * 设置ViewPager的滚动速度
     *
     * @param duration
     */
    public void setScrollDuration(int duration) {

        try {
            Field scrollField = ViewPager.class.getDeclaredField("mScroller");
            scrollField.setAccessible(true);
            scrollField.set(this, new SMNBannerScroller(getContext(), duration));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setIntervalTime(int intervalTime) {
        this.mIntervalTime = intervalTime;
    }

    public void start() {

        mHandler.removeCallbacksAndMessages(null);

        if (mAutoPlay) {
            mHandler.postDelayed(mRunnable, mIntervalTime);
        }

    }

    public void stop() {
        mHandler.removeCallbacksAndMessages(null);
    }

    private int next() {

        int nextPosition = -1;
        if (getAdapter() == null || getAdapter().getCount() == 1) {
            stop();
            return nextPosition;
        }

        nextPosition = getCurrentItem() + 1;
        if (nextPosition >= getAdapter().getCount()) {
            nextPosition = ((BannerAdapter) getAdapter()).getFirstItem();
        }

        setCurrentItem(nextPosition, true);

        return nextPosition;

    }

}
