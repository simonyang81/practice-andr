package smn.practice.practice.common.tab;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import smn.practice.practice.smnui.tab.bottom.TabBottomInfo;

public class TabViewAdapter {

    private List<TabBottomInfo<?>> mInfoList;
    private Fragment mCurrentFragment;
    private FragmentManager mFragmentManager;

    public TabViewAdapter(FragmentManager mFragmentManager, List<TabBottomInfo<?>> mInfoList) {
        this.mFragmentManager = mFragmentManager;
        this.mInfoList = mInfoList;
    }

    /**
     * 实例化以及显示指定位置的fragment
     *
     * @param container
     * @param position
     */
    public void instantiateItem(View container, int position) {

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (mCurrentFragment != null) {
            transaction.hide(mCurrentFragment);
        }

        String name = container.getId() + ":" + position;
        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        if (fragment != null) {
            transaction.show(fragment);
        } else {
            fragment = getItem(position);
            if (fragment != null && !fragment.isAdded()) {
                transaction.add(container.getId(), fragment, name);
            }
        }

        mCurrentFragment = fragment;
        transaction.commitAllowingStateLoss();

    }

    private Fragment getItem(int position) {

        try {
            return mInfoList.get(position).fragment.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    public int getCounter() {
        return mInfoList == null ? 0 : mInfoList.size();
    }

}
