package smn.practice.practice.smn.library.utils;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import java.util.ArrayDeque;

public class ViewUtil {

    /**
     * 获取指定类型的子View
     *
     * @param group ViewGroup
     * @param cls 如 RecyclerView.class
     * @return 指定类型的View
     */
    public static <T> T findTypeView(@Nullable ViewGroup group, Class<T> cls) {
        if (group == null) {
            return null;
        }

        ArrayDeque<View> deque = new ArrayDeque<>();
        deque.add(group);

        while (!deque.isEmpty()) {
            View node = deque.removeFirst();
            if (cls.isInstance(node)) {
                return cls.cast(node);
            } else if (node instanceof ViewGroup) {
                ViewGroup container = (ViewGroup) node;
                for (int i = 0, count = container.getChildCount(); i < count; i++) {
                    deque.add(container.getChildAt(i));
                }
            }
        }

        return null;
    }


}
