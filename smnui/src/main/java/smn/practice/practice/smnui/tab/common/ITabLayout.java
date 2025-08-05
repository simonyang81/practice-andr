package smn.practice.practice.smnui.tab.common;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

public interface ITabLayout<Tab extends ViewGroup, D> {

    Tab findTab(@NonNull D data);

    void addTabSelectedChangeListener(OnTabSelectedListener<D> listener);

    void defaultSelected(D defaultInfo);

    void inflateInfo(List<D> infoList);

    interface OnTabSelectedListener<D> {
        void onTabSelectedChange(int index, @NonNull D preInfo, @NonNull D nextInfo);
    }


}
