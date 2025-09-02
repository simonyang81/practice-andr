package smn.practice.practice.smnui.banner.indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import smn.practice.practice.smn.library.utils.DisplayUtil;

public class SMNNumberIndicator extends FrameLayout implements SMNIndicator<FrameLayout> {

    public SMNNumberIndicator(@NonNull Context context) {
        this(context, null);
    }

    public SMNNumberIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SMNNumberIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public FrameLayout get() {
        return this;
    }

    @Override
    public void onInflate(int counter) {
        removeAllViews();

        if (counter <= 0) {
            return;
        }

        // 使用WRAP_CONTENT让TextView只占用必要的空间
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        // 添加一些边距，让文本不贴边显示
        params.setMargins(0, 0, DisplayUtil.dp2px(8, getResources()), DisplayUtil.dp2px(8, getResources()));

        TextView textView = new TextView(getContext());
        textView.setText("1 / " + counter);
        textView.setTextColor(getContext().getColor(android.R.color.white));
        textView.setTextSize(16);

        addView(textView, params);
    }

    @Override
    public void onPointChange(int current, int counter) {
        TextView textView = (TextView) getChildAt(0);
        textView.setText(current + 1 + " / " + counter);
    }
}
