package smn.practice.practice.smnui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RefreshLayout extends FrameLayout implements IRefresh {

    private RefreshOverView.RefreshState mState;
    private GestureDetector mGestureDetector;
    private RefreshListener mRefreshListener;
    protected RefreshOverView mRefreshOverView;
    private int mLastY;
    private boolean disableRefreshScroll;
    private AutoScroller mAutoScroller;


    public RefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public RefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mGestureDetector = new GestureDetector(getContext(), gestureDetector);
        mAutoScroller = new AutoScroller();
    }

    @Override
    public void setDisableRefreshScroll(boolean disableRefreshScroll) {
        this.disableRefreshScroll = disableRefreshScroll;
    }

    @Override
    public void refreshFinish() {
        View header = getChildAt(0);
        mRefreshOverView.onFinish();
        mRefreshOverView.setState(RefreshOverView.RefreshState.STATE_INIT);

        int bottom = header.getBottom();
        if (bottom > 0) {
            recover(bottom);
        }

        mState = RefreshOverView.RefreshState.STATE_INIT;
    }

    @Override
    public void setRefreshListener(RefreshListener listener) {
        this.mRefreshListener = listener;
    }

    @Override
    public void setRefreshOverView(RefreshOverView overView) {
        if (this.mRefreshOverView != null) {
            removeView(mRefreshOverView);
        }

        this.mRefreshOverView = overView;

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mRefreshOverView, 0, params);

    }

    SMNGestureDetector gestureDetector = new SMNGestureDetector() {
        @Override
        public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {

            if (Math.abs(distanceX) > Math.abs(distanceY) || mRefreshListener != null && !mRefreshListener.enableRefresh()) {
                // 横向滚动，或者刷新被禁止。则不处理
                return false;
            }

            if (disableRefreshScroll && mState == RefreshOverView.RefreshState.STATE_REFRESH) {
                return true;
            }

            View header = getChildAt(0);
            View child = ScrollUtil.findScrollableChild(RefreshLayout.this);

            if (ScrollUtil.childScrolled(child)) {
                return false;
            }

            // 没有刷新或者没有达到可以刷新的距离，且头部已经画出或下拉
            if ((mState != RefreshOverView.RefreshState.STATE_REFRESH || header.getBottom() < mRefreshOverView.mPullRefreshHeight)
                && (header.getBottom() > 0 || distanceY <= 0.0f)) {

                // 如果还在滑动中
                if (mState != RefreshOverView.RefreshState.STATE_OVER_RELEASE) {
                    int seed;
                    // 速度计算
                    if (child.getTop() < mRefreshOverView.mPullRefreshHeight) {
                        seed = (int) (mLastY /mRefreshOverView.minDamp);
                    } else {
                        seed = (int) (mLastY /mRefreshOverView.maxDamp);
                    }

                    // 如果是正在刷新状态，则不允许在滑动的时候改变状态
                    boolean bool = moveDown(seed, true);
                    mLastY = (int) -distanceY;

                    return bool;
                } else {
                    return false;
                }

            } else {
                return false;
            }

        }
    };

    /**
     * 根据偏移量移动header与child
     *
     * @param offsetY 偏移量
     * @param manualScroll 手动滚动
     * @return
     */
    private boolean moveDown(int offsetY, boolean manualScroll) {

        View header = getChildAt(0);
        View child = getChildAt(1);

        int childTop = child.getTop() + offsetY;

        if (childTop <= 0) {    // 异常情况

            // 移动header 和 child的位置到原始位置
            offsetY = -child.getTop();
            header.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);

            if (mState != RefreshOverView.RefreshState.STATE_REFRESH) {
                mState = RefreshOverView.RefreshState.STATE_INIT;
            }

        } else if (mState == RefreshOverView.RefreshState.STATE_REFRESH && childTop > mRefreshOverView.mPullRefreshHeight) {
            // 如果正在下拉刷新中，禁止继续下拉
            return false;
        } else if (childTop <= mRefreshOverView.mPullRefreshHeight) {
            // 还没有超出设定的刷新距离
            if (mRefreshOverView.getState() != RefreshOverView.RefreshState.STATE_VISIBLE && manualScroll) {
                mRefreshOverView.onVisible();
                mRefreshOverView.setState(RefreshOverView.RefreshState.STATE_VISIBLE);
                mState = RefreshOverView.RefreshState.STATE_VISIBLE;
            }
            header.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);

            if (childTop == mRefreshOverView.mPullRefreshHeight && mState == RefreshOverView.RefreshState.STATE_OVER_RELEASE) {
                refresh();
            }

        } else {

            if (mRefreshOverView.getState() != RefreshOverView.RefreshState.STATE_OVER
                && manualScroll) {  // 超出刷新位置
                mRefreshOverView.onOver();
                mRefreshOverView.setState(RefreshOverView.RefreshState.STATE_OVER);
            }

            header.offsetTopAndBottom(offsetY);
            child.offsetTopAndBottom(offsetY);

        }

        if (mRefreshOverView != null) {
            mRefreshOverView.onScroll(header.getBottom(), mRefreshOverView.mPullRefreshHeight);
        }

        return true;

    }

    /**
     * 刷新
     */
    private void refresh() {
        if (mRefreshListener != null) {
            mState = RefreshOverView.RefreshState.STATE_REFRESH;
            mRefreshOverView.onRefresh();
            mRefreshOverView.setState(RefreshOverView.RefreshState.STATE_REFRESH);
            mRefreshListener.onRefresh();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        View header = getChildAt(0);
        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL
            || ev.getAction() == MotionEvent.ACTION_POINTER_INDEX_MASK) {   // 用户松开手

            if (header.getBottom() > 0) {
                if (mState != RefreshOverView.RefreshState.STATE_REFRESH) { // 非正在刷新
                    recover(header.getBottom());
                    return false;   // 不在处理触摸事件
                }
            }

            mLastY = 0;
        }

        boolean consumed = mGestureDetector.onTouchEvent(ev);

        if ((consumed ||
                (mState != RefreshOverView.RefreshState.STATE_INIT && mState != RefreshOverView.RefreshState.STATE_REFRESH)) && header.getBottom() != 0) {
            ev.setAction(MotionEvent.ACTION_CANCEL);
            return super.dispatchTouchEvent(ev);
        }

        if (consumed) {
            return true;
        } else {
            return super.dispatchTouchEvent(ev);
        }

    }

    private void recover(int dis) {
        if (mRefreshListener != null && dis > mRefreshOverView.mPullRefreshHeight) {
            mAutoScroller.recover(dis - mRefreshOverView.mPullRefreshHeight);
            mState = RefreshOverView.RefreshState.STATE_OVER_RELEASE;
        } else {
            mAutoScroller.recover(dis);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        View header = getChildAt(0);
        View child = getChildAt(1);

        if (header != null && child != null) {
            int childTop = child.getTop();

            if (mState == RefreshOverView.RefreshState.STATE_REFRESH) {
                header.layout(0, mRefreshOverView.mPullRefreshHeight - header.getMeasuredHeight(), right, mRefreshOverView.mPullRefreshHeight);
                child.layout(0, mRefreshOverView.mPullRefreshHeight, right, mRefreshOverView.mPullRefreshHeight + child.getMeasuredHeight());
            } else {
                header.layout(0, childTop - header.getMeasuredHeight(), right, childTop);
                child.layout(0, childTop, right, childTop + child.getMeasuredHeight());
            }

            View other;
            for (int i = 2; i < getChildCount(); i++) {
                other = getChildAt(i);
                other.layout(0, top, right, bottom);
            }

        }

    }

    private class AutoScroller implements Runnable {

        private Scroller mScroller;
        private int mLastY;
        private boolean mIsFinish;

        public AutoScroller() {
            mScroller = new Scroller(getContext(), new LinearInterpolator());
            mIsFinish = true;
        }

        @Override
        public void run() {

            if (mScroller.computeScrollOffset()) {  // 还未开始滚动
                moveDown(mLastY - mScroller.getCurrY(), false);
                mLastY = mScroller.getCurrY();
                post(this);
            } else {
                removeCallbacks(this);
                mIsFinish = true;
            }

        }

        public void recover(int dis) {
            if (dis <= 0) {
                return;
            }

            removeCallbacks(this);
            mLastY = 0;
            mIsFinish = false;
            mScroller.startScroll(0, 0, 0, dis, 300);
            post(this);

        }

        public boolean isFinish() {
            return mIsFinish;
        }

    }

}
