package smn.practice.practice.smnui.banner.core;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class BannerAdapter extends PagerAdapter {

    private Context mContext;
    private SparseArray<BannerViewHolder> mCachedViews = new SparseArray<>();
    private IBanner.OnBannerClickListener mOnBannerClickListener;
    private IBindAdapter mBindAdapter;
    private List<? extends BannerModel> models;


    // 默认自动切换
    private boolean mAutoPlay = true;

    // 非自动轮播状态下是否可以循环切换，默认不循环切换
    private boolean mLoop = false;

    private int mLayoutResId = -1;

    public BannerAdapter(Context context) {
        this.mContext = context;
    }

    public void setBannerData(@NonNull List<? extends BannerModel> models) {
        this.models = models;
        initCachedView();
        notifyDataSetChanged();
    }

    public void setBindAdapter(IBindAdapter bindAdapter) {
        this.mBindAdapter = bindAdapter;
    }

    public void setOnBannerClickListener(IBanner.OnBannerClickListener onBannerClickListener) {
        this.mOnBannerClickListener = onBannerClickListener;
    }

    public void setLayoutResId(int layoutResId) {
        this.mLayoutResId = layoutResId;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
    }

    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }

    @Override
    public int getCount() {
        return mAutoPlay ? Integer.MAX_VALUE : (mLoop ? Integer.MAX_VALUE : getRealCount());
    }

    public int getRealCount() {
        return models == null ? 0 : models.size();
    }

    public int getFirstItem() {
        return Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % getRealCount();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        int realPosition = position;

        if (getRealCount() > 0) {
            realPosition = position % getRealCount();
        }

        BannerViewHolder viewHolder = mCachedViews.get(realPosition);

        if (container.equals(viewHolder.rootView.getParent())) {
            container.removeView(viewHolder.rootView);
        }

        // 数据绑定
        onBind(viewHolder, models.get(realPosition), realPosition);

        if (viewHolder.rootView.getParent() != null) {
            ((ViewGroup) viewHolder.rootView.getParent()).removeView(viewHolder.rootView);
        }

        container.addView(viewHolder.rootView);

        return viewHolder.rootView;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        // 让 item 每次都会刷新
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    }

    protected void onBind(final BannerViewHolder viewHolder, final BannerModel bannerModel, final int position) {
        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBannerClickListener != null) {
                    mOnBannerClickListener.onBannerClick(viewHolder, bannerModel, position);
                }
            }
        });

        if (mBindAdapter != null) {
            mBindAdapter.onBind(viewHolder, bannerModel, position);
        }

    }

    private void initCachedView() {
        mCachedViews = new SparseArray<>();

        for (int i = 0; i < models.size(); i++) {

            BannerViewHolder viewHolder = new BannerViewHolder(
                    createView(LayoutInflater.from(mContext), null)
            );

            mCachedViews.put(i, viewHolder);

        }

    }

    private View createView(LayoutInflater layoutInflater, ViewGroup parent) {

        if (mLayoutResId == -1) {
            throw new IllegalArgumentException("You must be set layoutResId first");
        }

        return layoutInflater.inflate(mLayoutResId, parent, false);

    }

    public static class BannerViewHolder {

        private SparseArray<View> views;

        View rootView;

        public BannerViewHolder(View rootView) {
            this.rootView = rootView;
        }

        public View getRootView() {
            return rootView;
        }

        public <V extends View> V findViewById(int id) {
            if (!(rootView instanceof ViewGroup)) {
                return (V) rootView;
            }

            if (this.views == null) {
                this.views = new SparseArray<>(1);
            }

            V childView = (V) this.views.get(id);
            if (childView == null) {
                childView = rootView.findViewById(id);
                this.views.put(id, childView);
            }

            return childView;

        }

    }

}
