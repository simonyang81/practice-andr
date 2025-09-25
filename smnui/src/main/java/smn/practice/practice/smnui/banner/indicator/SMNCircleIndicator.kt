package smn.practice.practice.smnui.banner.indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import smn.practice.practice.smn.library.utils.DisplayUtil;
import smn.practice.practice.smnui.R;

/**
 * Banner 的圆形指示器
 */
public class SMNCircleIndicator extends FrameLayout implements SMNIndicator<FrameLayout> {

    private static final int VWC = ViewGroup.LayoutParams.WRAP_CONTENT;

    private @DrawableRes int mPointNormal = R.drawable.shape_point_normal;
    private @DrawableRes int mPointSelect = R.drawable.shape_point_select;

    // 指示点左右间距
    private int mPointLeftRightPadding;

    // 指示点上下间距
    private int mPointTopBottomPadding;

    public SMNCircleIndicator(@NonNull Context context) {
        this(context, null);
    }

    public SMNCircleIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SMNCircleIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        mPointLeftRightPadding = DisplayUtil.dp2px(5, getContext().getResources());
        mPointTopBottomPadding = DisplayUtil.dp2px(15, getContext().getResources());
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

        LinearLayout groupView = new LinearLayout(getContext());
        groupView.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(VWC, VWC);
        imageParams.gravity = Gravity.CENTER_VERTICAL;
        imageParams.setMargins(mPointLeftRightPadding, mPointTopBottomPadding, mPointLeftRightPadding, mPointTopBottomPadding);

        for (int i = 0; i < counter; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(imageParams);

            if (i == 0) {
                imageView.setImageResource(mPointSelect);
            } else {
                imageView.setImageResource(mPointNormal);
            }

            groupView.addView(imageView);

        }

        LayoutParams groupViewParams = new LayoutParams(VWC, VWC);
        groupViewParams.gravity = Gravity.CENTER | Gravity.BOTTOM;

        addView(groupView, groupViewParams);

    }

    @Override
    public void onPointChange(int current, int counter) {

       ViewGroup viewGroup = (ViewGroup) getChildAt(0);

        for (int i = 0; i < viewGroup.getChildCount(); i++) {

            ImageView imageView = (ImageView) viewGroup.getChildAt(i);

            if (i == current) {
                imageView.setImageResource(mPointSelect);
            } else {
                imageView.setImageResource(mPointNormal);
            }

            imageView.requestLayout();

        }

    }


}
