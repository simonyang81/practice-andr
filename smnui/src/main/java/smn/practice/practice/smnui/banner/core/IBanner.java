package smn.practice.practice.smnui.banner.core;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import smn.practice.practice.smnui.banner.indicator.SMNIndicator;

public interface IBanner {

    void setBannerData(@LayoutRes int layoutResId, @NonNull List<? extends BannerModel> models);

    void setBannerData(@NonNull List<? extends BannerModel> models);

    void setIndicator(SMNIndicator<?> indicator);

    void setAutoPlay(boolean autoPlay);

    void setLoop(boolean loop);

    void setIntercalTime(int intervalTime);

    void setBindAdapter(IBindAdapter bindAdapter);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);

    void setOnBannerClickListener(OnBannerClickListener onBannerClickListener);

    void  setScrollDuration(int duration);

    interface OnBannerClickListener {

        void onBannerClick(@NonNull BannerAdapter.BannerViewHolder viewHolder, @NonNull BannerModel bannerModel, int position);

    }

}
