package smn.practice.practice.smn.library.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public class DisplayUtil {

    /**
     * dp 转 dx
     *
     * @param dp
     * @param resources
     * @return
     */
    public static int dp2dx(float dp, Resources resources) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getDisplayWidthInPx(@NonNull Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        if (wm != null) {

            Display display = wm.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            return point.x;

        }

        return 0;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getDisplayHeightInPx(@NonNull Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        if (wm != null) {

            Display display = wm.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            return point.y;

        }

        return 0;
    }

}
