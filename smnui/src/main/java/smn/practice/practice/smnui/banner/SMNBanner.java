package smn.practice.practice.smnui.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import smn.practice.practice.smnui.R;
import smn.practice.practice.smnui.banner.core.BannerDelegate;
import smn.practice.practice.smnui.banner.core.BannerModel;
import smn.practice.practice.smnui.banner.core.IBanner;
import smn.practice.practice.smnui.banner.core.IBindAdapter;
import smn.practice.practice.smnui.banner.indicator.SMNIndicator;

public class SMNBanner extends FrameLayout implements IBanner {

    private final BannerDelegate delegate;

    public SMNBanner(@NonNull Context context) {
        this(context, null);
    }

    public SMNBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SMNBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.delegate = new BannerDelegate(context, this);
        this.initCustomAttrs(context, attrs);
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SMNBanner);
        boolean autoPlay = typedArray.getBoolean(R.styleable.SMNBanner_autoPlay, true);
        boolean loop = typedArray.getBoolean(R.styleable.SMNBanner_loop, true);
        int intervalTime = typedArray.getInteger(R.styleable.SMNBanner_intervalTime, -1);

        setAutoPlay(autoPlay);
        setLoop(loop);
        setIntercalTime(intervalTime);

        // 回收 TypedArray
        typedArray.recycle();

    }

    @Override
    public void setBannerData(int layoutResId, @NonNull List<? extends BannerModel> models) {
        this.delegate.setBannerData(layoutResId, models);
    }

    @Override
    public void setBannerData(@NonNull List<? extends BannerModel> models) {
        this.delegate.setBannerData(models);
    }

    @Override
    public void setIndicator(SMNIndicator<?> indicator) {
        this.delegate.setIndicator(indicator);
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        this.delegate.setAutoPlay(autoPlay);
    }

    @Override
    public void setLoop(boolean loop) {
        this.delegate.setLoop(loop);
    }

    @Override
    public void setIntercalTime(int intervalTime) {
        this.delegate.setIntercalTime(intervalTime);
    }

    @Override
    public void setBindAdapter(IBindAdapter bindAdapter) {
        this.delegate.setBindAdapter(bindAdapter);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.delegate.setOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.delegate.setOnBannerClickListener(onBannerClickListener);
    }

    @Override
    public void setScrollDuration(int duration) {
        this.delegate.setScrollDuration(duration);
    }

}
