package smn.practice.practice.smnui.banner.indicator;

import android.view.View;

public interface SMNIndicator<T extends View> {

    T get();

    /**
     * 初始化Indicator
     *
     * @param counter 幻灯片的数量
     */
    void onInflate(int counter);

    /**
     * 幻灯片切换的回调
     *
     * @param current 切换到的幻灯片的位置
     * @param counter 幻灯片的数量
     */
    void onPointChange(int current, int counter);


}
