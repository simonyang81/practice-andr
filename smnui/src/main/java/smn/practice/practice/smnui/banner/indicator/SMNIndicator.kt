package smn.practice.practice.smnui.banner.indicator

import android.view.View

interface SMNIndicator<T : View?> {
    fun get(): T?

    /**
     * 初始化Indicator
     *
     * @param counter 幻灯片的数量
     */
    fun onInflate(counter: Int)

    /**
     * 幻灯片切换的回调
     *
     * @param current 切换到的幻灯片的位置
     * @param counter 幻灯片的数量
     */
    fun onPointChange(current: Int, counter: Int)
}
