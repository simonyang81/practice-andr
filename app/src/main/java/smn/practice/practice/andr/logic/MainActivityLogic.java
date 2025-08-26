package smn.practice.practice.andr.logic;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import smn.practice.practice.andr.R;
import smn.practice.practice.andr.ui.category.CategoryFragment;
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
    private final static String DEFAULT_COLOR = "#ff656667"; // 默认灰色
    private int currentItemIndex;

    private static final float TAB_ALPHA = 1f;

    /**
     * Tab配置数据类
     * 提供类型安全的配置管理，替代原来的二维数组
     */
    private static class TabConfig {
        @StringRes
        final int nameResId;
        @StringRes 
        final int iconResId;
        final Class<? extends Fragment> fragmentClass;

        /**
         * 构造Tab配置
         * @param nameResId 标签名资源ID
         * @param iconResId 图标资源ID  
         * @param fragmentClass Fragment类
         */
        TabConfig(@StringRes int nameResId, @StringRes int iconResId, Class<? extends Fragment> fragmentClass) {
            this.nameResId = nameResId;
            this.iconResId = iconResId;
            this.fragmentClass = fragmentClass;
        }
    }

    private static final List<TabConfig> TAB_CONFIGS = Arrays.asList(
            new TabConfig(R.string.tab_home, R.string.if_home, HomeFragment.class),
            new TabConfig(R.string.tab_favorite, R.string.if_favorite, FavoriteFragment.class),
            new TabConfig(R.string.tab_category, R.string.if_category, CategoryFragment.class),
            new TabConfig(R.string.tab_recommend, R.string.if_recommend, RecommendFragment.class),
            new TabConfig(R.string.tab_profile, R.string.if_profile, ProfileFragment.class)
    );

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
        if (activityProvider == null) {
            throw new IllegalStateException("ActivityProvider不能为空");
        }

        // 预先获取颜色字符串，避免在循环中重复获取
        final String defaultColor = getColorString(R.color.tab_default_color);
        final String selectedColor = getColorString(R.color.tab_selected_color);
        
        // 使用ArrayList并预设容量，减少扩容开销
        List<TabBottomInfo<?>> tabInfoList = new ArrayList<>(TAB_CONFIGS.size());

        for (TabConfig config : TAB_CONFIGS) {
            TabBottomInfo<String> tabInfo = createTabInfoFromConfig(
                    config, 
                    defaultColor, 
                    selectedColor
            );
            
            // 直接从配置中设置Fragment类，无需if-else判断
            tabInfo.fragment = config.fragmentClass;
            tabInfoList.add(tabInfo);
        }

        return tabInfoList;
    }

    /**
     * 根据配置创建TabBottomInfo对象
     * 
     * @param config Tab配置对象
     * @param defaultColor 默认颜色
     * @param selectedColor 选中颜色
     * @return TabBottomInfo对象
     */
    private TabBottomInfo<String> createTabInfoFromConfig(TabConfig config, 
                                                         String defaultColor, 
                                                         String selectedColor) {
        try {
            return createTabInfo(
                    activityProvider.getString(config.nameResId),    // 标签名
                    activityProvider.getString(config.iconResId),    // 图标字符
                    defaultColor,
                    selectedColor
            );
        } catch (Resources.NotFoundException e) {
            throw new RuntimeException("资源未找到: nameResId=" + config.nameResId + 
                                     ", iconResId=" + config.iconResId, e);
        }
    }

    /**
     * 创建TabBottomInfo对象
     * 
     * @param name 标签名
     * @param iconChar 图标字符
     * @param defaultColor 默认颜色
     * @param selectedColor 选中颜色
     * @return TabBottomInfo对象
     */
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

    /**
     * 初始化Fragment标签视图
     */
    private void initFragmentTabView() {
        TabViewAdapter tabViewAdapter = new TabViewAdapter(activityProvider.getSupportFragmentManager(), infoList);
        fragmentTabView = activityProvider.findViewById(R.id.fragment_tab_view);
        fragmentTabView.setAdapter(tabViewAdapter);
    }

    /**
     * 获取颜色字符串
     *
     * @param colorResId 颜色资源ID
     * @return 颜色字符串（格式：#AARRGGBB）
     */
    private String getColorString(int colorResId) {
        try {
            if (activityProvider == null || activityProvider.getResources() == null) {
                return DEFAULT_COLOR;
            }

            int color = activityProvider.getResources().getColor(colorResId);
            return String.format("#%08X", color);
        } catch (Resources.NotFoundException e) {
            // 记录错误但不崩溃，返回默认颜色
            android.util.Log.w("MainActivityLogic", "颜色资源未找到: " + colorResId, e);
            return DEFAULT_COLOR;
        } catch (Exception e) {
            // 处理其他可能的异常
            android.util.Log.e("MainActivityLogic", "获取颜色时发生错误: " + colorResId, e);
            return DEFAULT_COLOR;
        }
    }

    /**
     * 保存实例状态
     * 
     * @param outState 输出状态Bundle
     */
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(SAVED_CURRENT_ID, currentItemIndex);
    }
}
