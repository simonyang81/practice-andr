package smn.practice.practice.smnui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import smn.practice.practice.smn.library.utils.DisplayUtil;

/**
 * 下拉刷新的Overlay视图，可以重载这个类来自定义Overlay
 */
public abstract class RefreshOverView extends FrameLayout {

    public enum RefreshState {
        // 初始态
        STATE_INIT,
        // Header展示的状态
        STATE_VISIBLE,
        // 刷新中的状态
        STATE_REFRESH,
        // 超出可刷新距离的状态
        STATE_OVER,
        // 超出刷新位置松开手后的状态
        STATE_OVER_RELEASE
    }

    protected RefreshState mState = RefreshState.STATE_INIT;

    // 触发下拉刷新需要的最小高度
    public int mPullRefreshHeight;

    // 最小阻尼
    public float minDamp = 1.6f;

    // 最大阻尼
    public float maxDamp = 2.2f;

    public RefreshOverView(@NonNull Context context) {
        this(context, null);
    }

    public RefreshOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        preInit();
    }

    protected void preInit() {
        mPullRefreshHeight = DisplayUtil.dp2px(76, getResources());
        init();
    }

    public abstract void init();

    public abstract void onScroll(int scrollY, int pullRefreshHeight);

    /**
     * 显示Overlay
     */
    public abstract void onVisible();

    /**
     * 超过Overlay，释放就会加载
     */
    public abstract void onOver();

    /**
     * 开始刷新
     */
    public abstract void onRefresh();

    /**
     * 刷新完成
     */
    public abstract void onFinish();

    public void setState(RefreshState state) {
        this.mState = state;
    }

    public RefreshState getState() {
        return mState;
    }
}
