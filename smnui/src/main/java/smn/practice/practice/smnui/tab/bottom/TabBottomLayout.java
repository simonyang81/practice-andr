package smn.practice.practice.smnui.tab.bottom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import smn.practice.practice.smn.library.utils.DisplayUtil;
import smn.practice.practice.smn.library.utils.ViewUtil;
import smn.practice.practice.smnui.R;
import smn.practice.practice.smnui.tab.common.ITabLayout;

public class TabBottomLayout extends FrameLayout implements ITabLayout<TabBottom, TabBottomInfo<?>> {

    private final List<OnTabSelectedListener<TabBottomInfo<?>>> tabSelectedChangeListeners = new ArrayList<>();
    private TabBottomInfo<?> selectedInfo;
    private float bottomAlpha = 1f;
    private float tabBottomHeight = 50;
    private float bottomLineHeight = 0.5f;
    private String bottomLineColor = "#dfe0e1";
    private List<TabBottomInfo<?>> infoList;

    private static final String TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM";


    public TabBottomLayout(@NonNull Context context) {
        super(context);
    }

    public TabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public TabBottom findTab(@NonNull TabBottomInfo<?> info) {

        ViewGroup frameLayout = findViewWithTag(TAG_TAB_BOTTOM);

        for (int i = 0; i < frameLayout.getChildCount(); i++) {
            View child = frameLayout.getChildAt(i);

            if (child instanceof TabBottom) {
                TabBottom tab = (TabBottom) child;
                if (tab.getTabInfo() == info) {
                    return tab;
                }
            }

        }
        
        
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<TabBottomInfo<?>> listener) {
        tabSelectedChangeListeners.add(listener);
    }

    @Override
    public void defaultSelected(TabBottomInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    @Override
    public void inflateInfo(@NonNull List<TabBottomInfo<?>> infoList) {

        if (infoList.isEmpty()) {
            return;
        }

        this.infoList = infoList;

        // 移除之前已经添加的View
        for (int i = getChildCount() - 1; i > 0; i--) {
            removeViewAt(i);
        }

        selectedInfo = null;
        addBackground();
        tabSelectedChangeListeners.removeIf(tabBottomInfoOnTabSelectedListener -> tabBottomInfoOnTabSelectedListener instanceof TabBottom);

        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setTag(TAG_TAB_BOTTOM);
        int width = DisplayUtil.getDisplayWidthInPx(getContext()) / infoList.size();
        int height = DisplayUtil.dp2px(tabBottomHeight, getResources());

        for (int i = 0; i < infoList.size(); i++) {

            TabBottomInfo<?> info = infoList.get(i);
            LayoutParams params = new LayoutParams(width, height);
            params.gravity = Gravity.BOTTOM;
            params.leftMargin = i * width;

            TabBottom tabBottom = new TabBottom(getContext());
            tabSelectedChangeListeners.add(tabBottom);

            tabBottom.setTabInfo(info);

            frameLayout.addView(tabBottom, params);
            tabBottom.setOnClickListener(v -> {
                onSelected(info);
            });
        }

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;

        addBottomLine();

        addView(frameLayout, params);


    }

    private void addBottomLine() {

        View bottomLine = new View(getContext());
        bottomLine.setBackgroundColor(Color.parseColor(bottomLineColor));
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dp2px(bottomLineHeight));
        params.gravity = Gravity.BOTTOM;
        params.bottomMargin = DisplayUtil.dp2px(tabBottomHeight - bottomLineHeight, getResources());
        addView(bottomLine, params);
        bottomLine.setAlpha(bottomAlpha);

    }

    private void onSelected(@NonNull TabBottomInfo<?> nextInfo) {

        tabSelectedChangeListeners.forEach(listener -> {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo);
        });

        this.selectedInfo = nextInfo;

    }

    private void addBackground() {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_layout_background, null);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dp2px(tabBottomHeight, getResources()));
        params.gravity = Gravity.BOTTOM;

        view.setAlpha(bottomAlpha);
        addView(view, params);
        adjustContentPadding();

    }

    public void setTabAlpha(float alpha) {
        this.bottomAlpha = alpha;
    }

    public void setTabHeight(float tabHeight) {
        this.tabBottomHeight = tabHeight;
    }

    public void setBottomLineHeight(float bottomLineHeight) {
        this.bottomLineHeight = bottomLineHeight;
    }

    public void setBottomLineColor(String bottomLineColor) {
        this.bottomLineColor = bottomLineColor;
    }

    /**
     * 获取Tab的高度（dp值）
     * @return Tab高度的dp值
     */
    public float getTabHeight() {
        return tabBottomHeight;
    }

    /**
     * 获取Tab的高度（px值）
     * @return Tab高度的px值  
     */
    public int getTabHeightPx() {
        return DisplayUtil.dp2px(tabBottomHeight, getResources());
    }

    /**
     * 增加底部padding
     */
    private void adjustContentPadding() {

        if (!(getChildAt(0) instanceof ViewGroup)) {    // 单节点
            return;
        }

        ViewGroup rootView = (ViewGroup) getChildAt(0);

        ViewGroup targetView = ViewUtil.findTypeView(rootView, RecyclerView.class);

        if (targetView == null) {
            targetView = ViewUtil.findTypeView(rootView, ScrollView.class);
        }

        if (targetView == null) {
            targetView = ViewUtil.findTypeView(rootView, AbsListView.class);
        }

        if (targetView != null) {
            targetView.setPadding(0, 0, 0, DisplayUtil.dp2px(tabBottomHeight, getResources()));
            targetView.setClipToPadding(false);
        }


    }


}
