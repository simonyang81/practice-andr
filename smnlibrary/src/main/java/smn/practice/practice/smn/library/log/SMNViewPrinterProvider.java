package smn.practice.practice.smn.library.log;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import smn.practice.practice.smn.library.utils.DisplayUtil;

public class SMNViewPrinterProvider {

    private FrameLayout rootView;
    private View floatingView;
    private boolean isOpen;
    private FrameLayout logView;
    private RecyclerView recyclerView;

    private static final String TAG_FLOATING_VIEW = "TAG_FLOATING_VIEW";
    private static final String TAG_LOG_VIEW = "TAG_LOG_VIEW";

    public SMNViewPrinterProvider(FrameLayout rootView, RecyclerView recyclerView) {
        this.rootView = rootView;
        this.recyclerView = recyclerView;
    }

    public void showFloatingView() {
        if (rootView.findViewWithTag(TAG_FLOATING_VIEW) != null) {
            return;
        }

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.END;

        View floatingView = genFloatingView();
        floatingView.setTag(TAG_FLOATING_VIEW);
        floatingView.setBackgroundColor(Color.BLACK);
        floatingView.setAlpha(0.7f);
        params.bottomMargin = DisplayUtil.dp2dx(100, recyclerView.getResources());

        rootView.addView(genFloatingView(), params);
    }

    private View genFloatingView() {

        if (floatingView != null) {
            return floatingView;
        }

        TextView textView = new TextView(rootView.getContext());
        textView.setOnClickListener(v -> {
            if (!isOpen) {
                showLogView();
            }
        });

        textView.setText("Log");

        return floatingView = textView;

    }

    public void closeFloatingView() {
        rootView.removeView(genFloatingView());
    }

    private void showLogView() {
        if (rootView.findViewWithTag(TAG_LOG_VIEW) != null) {
            return;
        }

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dp2dx(160, rootView.getResources()));
        params.gravity = Gravity.BOTTOM;

        View logView = genLogView();
        logView.setTag(TAG_LOG_VIEW);

        rootView.addView(genLogView(), params);
        isOpen = true;

    }


    private View genLogView() {

        if (logView != null) {
            return logView;
        }

        FrameLayout frameLayout = new FrameLayout(rootView.getContext());
        frameLayout.setBackgroundColor(Color.BLACK);
        frameLayout.addView(recyclerView);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.END;

        TextView closeView = new TextView(rootView.getContext());
        closeView.setText("Close");
        closeView.setOnClickListener(v -> {
            closeLogView();
        });

        frameLayout.addView(closeView, params);

        return logView = frameLayout;
    }

    private void closeLogView() {
        isOpen = false;
        rootView.removeView(genLogView());
    }

}
