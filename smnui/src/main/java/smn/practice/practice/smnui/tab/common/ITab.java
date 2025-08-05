package smn.practice.practice.smnui.tab.common;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

/**
 * Tab 对外接口
 *
 * @param <D>
 */
public interface ITab<D> extends ITabLayout.OnTabSelectedListener<D> {

    /**
     *
     *
     * @param data
     */
    void setTabInfo(@NonNull D data);

    /**
     * 动图修改某个item大小
     *
     * @param height
     */
    void resetHeight(@Px int height);

}
