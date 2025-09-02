package smn.practice.practice.smnui.banner.core;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import smn.practice.practice.smnui.R;
import smn.practice.practice.smnui.banner.SMNBanner;
import smn.practice.practice.smnui.banner.indicator.SMNCircleIndicator;
import smn.practice.practice.smnui.banner.indicator.SMNIndicator;

/**
 * Banner的控制器
 * 辅助Banner完成功能，将Banner的一些逻辑内聚
 */
public class BannerDelegate implements IBanner, ViewPager.OnPageChangeListener {

    private Context mContext;
    private SMNBanner mBanner;

    private BannerAdapter mAdapter;
    private SMNIndicator<?> mIndicator;
    private boolean mAutoPlay;
    private boolean mLoop;
    private List<? extends BannerModel> mBannerModels;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private int mIntervalTime = 5000;   // 默认切换间隔时间5s
    private IBanner.OnBannerClickListener mOnBannerClickListener;
    private SMNVierPager mVierPager;
    private int mScrollDuration = -1;


    public BannerDelegate(Context context, SMNBanner banner) {
        this.mContext = context;
        this.mBanner = banner;
    }

    @Override
    public void setBannerData(int layoutResId, @NonNull List<? extends BannerModel> models) {
        this.mBannerModels = models;
        this.init(layoutResId);
    }



    @Override
    public void setBannerData(@NonNull List<? extends BannerModel> models) {
        this.setBannerData(R.layout.smn_banner_item_image, models);
    }

    @Override
    public void setIndicator(SMNIndicator<?> indicator) {
        this.mIndicator = indicator;
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;

        if (mAdapter != null) {
            mAdapter.setAutoPlay(autoPlay);
        }

        if (mVierPager != null) {
            mVierPager.setAutoPlay(autoPlay);
        }

    }

    @Override
    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }

    @Override
    public void setIntercalTime(int intervalTime) {
        if (intervalTime > 0) {
            this.mIntervalTime = intervalTime;
        }
    }

    @Override
    public void setBindAdapter(IBindAdapter bindAdapter) {
        mAdapter.setBindAdapter(bindAdapter);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    @Override
    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.mOnBannerClickListener = onBannerClickListener;
    }

    @Override
    public void setScrollDuration(int duration) {
        this.mScrollDuration = duration;
        if (mVierPager != null && duration > 0) {
            mVierPager.setScrollDuration(duration);
        }
    }


    private void init(int layoutResId) {

        if (mAdapter == null) {
            mAdapter = new BannerAdapter(mContext);
        }

        if (mIndicator == null) {
            mIndicator = new SMNCircleIndicator(mContext);
        }

        mIndicator.onInflate(mBannerModels.size());
        mAdapter.setLayoutResId(layoutResId);
        mAdapter.setBannerData(mBannerModels);
        mAdapter.setAutoPlay(mAutoPlay);
        mAdapter.setLoop(mLoop);
        mAdapter.setOnBannerClickListener(mOnBannerClickListener);

        mVierPager = new SMNVierPager(mContext);
        mVierPager.setIntervalTime(mIntervalTime);
        mVierPager.addOnPageChangeListener(this);
        mVierPager.setAutoPlay(mAutoPlay);
        mVierPager.setAdapter(mAdapter);

        if (mScrollDuration > 0) {
            mVierPager.setScrollDuration(mScrollDuration);
        }

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );

        if ((mLoop || mAutoPlay) && mAdapter.getRealCount() != 0) {

            int firstItem = mAdapter.getFirstItem();
            mVierPager.setCurrentItem(firstItem, false);

        }

        // 清除所有的View
        mBanner.removeAllViews();

        mBanner.addView(mVierPager);
        mBanner.addView(mIndicator.get(), layoutParams);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null && mAdapter.getRealCount() != 0) {
            mOnPageChangeListener.onPageScrolled(
                    position % mAdapter.getRealCount(),
                    positionOffset,
                    positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mAdapter.getRealCount() == 0) {
            return;
        }

        position = position % mAdapter.getRealCount();

        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }

        if (mIndicator != null) {
            mIndicator.onPointChange(position, mAdapter.getRealCount());
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }
}
