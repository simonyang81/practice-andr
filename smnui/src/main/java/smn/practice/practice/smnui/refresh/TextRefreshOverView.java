package smn.practice.practice.smnui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import smn.practice.practice.smnui.R;

public class TextRefreshOverView extends RefreshOverView {

    private TextView mTextView;
    private View mRotateView;

    public TextRefreshOverView(@NonNull Context context) {
        super(context);
    }

    public TextRefreshOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TextRefreshOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.text_refresh_over_view, this, true);
        mTextView = findViewById(R.id.text);
        mRotateView = findViewById(R.id.iv_rotate);
    }

    @Override
    public void onScroll(int scrollY, int pullRefreshHeight) {
    }

    @Override
    public void onVisible() {
        mTextView.setText("下拉刷新");
    }

    @Override
    public void onOver() {
        mTextView.setText("松开刷新");
    }

    @Override
    public void onRefresh() {
        mTextView.setText("正在刷新...");
        Animation operatingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_anim);
        LinearInterpolator interpolator = new LinearInterpolator();
        operatingAnim.setInterpolator(interpolator);
        mRotateView.startAnimation(operatingAnim);
    }

    @Override
    public void onFinish() {
        mRotateView.clearAnimation();
    }
}
