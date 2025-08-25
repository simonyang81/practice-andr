package smn.practice.practice.common.tab;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 1. 将Fragment的操作内聚
 * 2. 提供通用的一些API
 *
 */
public class FragmentTabView extends FrameLayout {

    private TabViewAdapter mAdapter;
    private int mCurrentPosition;

    public FragmentTabView(@NonNull Context context) {
        super(context);
    }

    public FragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FragmentTabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TabViewAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(TabViewAdapter adapter) {

        if (this.mAdapter != null || adapter == null) {
            return;
        }

        this.mAdapter = adapter;
        this.mCurrentPosition = -1;
    }

    public void setCurrentItem(int position) {

        if (position < 0 || position >= mAdapter.getCounter()) {
            return;
        }

        if (this.mCurrentPosition != position) {
            this.mCurrentPosition = position;
            mAdapter.instantiateItem(this, position);
        }

    }

    public int getCurrentItem() {
        return mCurrentPosition;
    }

    public Fragment getCurrentFragment() {
        if (this.mAdapter == null) {
            throw new IllegalArgumentException("Please call setAdapter first.");
        }

        return mAdapter.getCurrentFragment();
    }

}
