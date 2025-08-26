package smn.practice.practice.andr.logic;

import static java.security.AccessController.getContext;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import smn.practice.practice.andr.R;
import smn.practice.practice.andr.ui.category.CategoryFragment;
import smn.practice.practice.andr.ui.demo.LogDemoFragment;
import smn.practice.practice.andr.ui.favorite.FavoriteFragment;
import smn.practice.practice.andr.ui.home.HomeFragment;
import smn.practice.practice.andr.ui.profile.ProfileFragment;
import smn.practice.practice.andr.ui.recommend.RecommendFragment;
import smn.practice.practice.common.tab.FragmentTabView;
import smn.practice.practice.common.tab.TabViewAdapter;
import smn.practice.practice.smnui.tab.bottom.TabBottomInfo;
import smn.practice.practice.smnui.tab.bottom.TabBottomLayout;

/**
 * MainActivity 逻辑类
 */
public class MainActivityLogic {

    private FragmentTabView fragmentTabView;
    private TabBottomLayout tabBottomLayout;
    private List<TabBottomInfo<?>> infoList;
    private ActivityProvider activityProvider;
    private final static String SAVED_CURRENT_ID = "SAVED_CURRENT_ID";
    private final static String ICON_FONT_PATH = "fonts/iconfont.ttf";
    private int currentItemIndex;

    private static final float TAB_ALPHA = 1f;

    public MainActivityLogic(ActivityProvider activityProvider, Bundle savedInstanceState) {
        this.activityProvider = activityProvider;
        if (savedInstanceState != null) {
            currentItemIndex = savedInstanceState.getInt(SAVED_CURRENT_ID);
        }
        initTabBottom();
    }

    public interface ActivityProvider {
        <T extends View> T findViewById(@IdRes int id);
        Resources getResources();
        FragmentManager getSupportFragmentManager();
        String getString(@StringRes int resId);
    }

    public FragmentTabView getFragmentTabView() {
        return fragmentTabView;
    }

    public TabBottomLayout getTabBottomLayout() {
        return tabBottomLayout;
    }

    public List<TabBottomInfo<?>> getInfoList() {
        return infoList;
    }

    private void initTabBottom() {
        tabBottomLayout = activityProvider.findViewById(R.id.container);
        tabBottomLayout.setTabAlpha(TAB_ALPHA);

        infoList = createTabInfoList();

        tabBottomLayout.inflateInfo(infoList);
        initFragmentTabView();
        tabBottomLayout.addTabSelectedChangeListener((index, preInfo, nextInfo) -> {
            fragmentTabView.setCurrentItem(index);
            MainActivityLogic.this.currentItemIndex = index;
        });
        tabBottomLayout.defaultSelected(infoList.get(currentItemIndex));
    }

    private List<TabBottomInfo<?>> createTabInfoList() {
        // 标签配置数据：{标签名资源ID, 图标资源ID}
        int[][] tabConfigs = {
                {R.string.tab_home, R.string.if_home},
                {R.string.tab_favorite, R.string.if_favorite},
                {R.string.tab_category, R.string.if_category},
                {R.string.tab_recommend, R.string.if_recommend},
                {R.string.tab_profile,  R.string.if_profile}
        };

        String defaultColor = getColorString(R.color.tab_default_color);
        String selectedColor = getColorString(R.color.tab_selected_color);

        List<TabBottomInfo<?>> tabInfoList = new ArrayList<>();

        for (int i = 0; i < tabConfigs.length; i++) {

            TabBottomInfo<String> tabInfo = createTabInfo(
                    activityProvider.getString(tabConfigs[i][0]),  // 标签名
                    activityProvider.getString(tabConfigs[i][1]),  // 图标字符
                    defaultColor,
                    selectedColor
            );

            if (i == 0) {
                tabInfo.fragment = HomeFragment.class;
            } else if (i == 1) {
                tabInfo.fragment = FavoriteFragment.class;
            } else if (i == 2) {
                tabInfo.fragment = CategoryFragment.class;
            } else if (i == 3) {
                tabInfo.fragment = RecommendFragment.class;
            } else {
                tabInfo.fragment = ProfileFragment.class;
            }


            tabInfoList.add(tabInfo);
        }

        return tabInfoList;
    }

    private TabBottomInfo<String> createTabInfo(String name, String iconChar,
                                                String defaultColor, String selectedColor) {
        return new TabBottomInfo<>(
                name,
                ICON_FONT_PATH,
                iconChar,
                null,  // 选中状态使用相同图标
                defaultColor,
                selectedColor
        );
    }

    private void initFragmentTabView() {
        TabViewAdapter tabViewAdapter = new TabViewAdapter(activityProvider.getSupportFragmentManager(), infoList);
        fragmentTabView = activityProvider.findViewById(R.id.fragment_tab_view);
        fragmentTabView.setAdapter(tabViewAdapter);
    }

    private String getColorString(int colorResId) {
        if (getContext() != null) {
            int color = activityProvider.getResources().getColor(colorResId);
            return String.format("#%08X", color);
        }
        return "#ff656667"; // 默认颜色
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SAVED_CURRENT_ID, currentItemIndex);
    }

}
