package smn.practice.practice.andr.ui.demo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import smn.practice.practice.andr.R;
import smn.practice.practice.andr.databinding.FragmentTabBottomDemoBinding;
import smn.practice.practice.smnui.tab.bottom.TabBottomInfo;
import smn.practice.practice.smnui.tab.bottom.TabBottomLayout;

/**
 * Tab底部导航演示Fragment
 * 展示如何使用TabBottomLayout创建底部导航栏
 */
public class TabBottomDemoFragment extends Fragment {

    private FragmentTabBottomDemoBinding binding;
    
    // 常量定义
    private static final String ICON_FONT_PATH = "fonts/iconfont.ttf";
    private static final float TAB_ALPHA = 1f;


    private TabBottomDemoFragment() {
    }

    public static TabBottomDemoFragment newInstance() {
        return new TabBottomDemoFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTabBottomDemoBinding.inflate(inflater, container, false);
        initViews();
        setupClickListeners();

        return binding.getRoot();
    }

    private void initViews() {
        initTabBottom();
    }

    /**
     * 初始化底部导航栏
     * 使用数据驱动的方式创建标签
     */
    private void initTabBottom() {
        TabBottomLayout tabLayout = binding.smnTabLayout;
        tabLayout.setTabAlpha(TAB_ALPHA);

        List<TabBottomInfo<?>> bottomInfoList = createTabInfoList();
        
        tabLayout.inflateInfo(bottomInfoList);
        tabLayout.addTabSelectedChangeListener((index, preInfo, nextInfo) -> {
            Toast.makeText(getContext(), nextInfo.name, Toast.LENGTH_SHORT).show();
        });
        tabLayout.defaultSelected(bottomInfoList.get(0));
    }

    /**
     * 创建标签信息列表
     * 使用数据驱动的方式，将标签配置集中管理
     * @return 标签信息列表
     */
    private List<TabBottomInfo<?>> createTabInfoList() {
        // 标签配置数据：{标签名资源ID, 图标资源ID}
        int[][] tabConfigs = {
                {R.string.tab_home, R.string.if_home},
                {R.string.tab_favorite, R.string.if_favorite},
                {R.string.tab_category, R.string.if_category},
                {R.string.tab_recommend, R.string.if_recommend},
                {R.string.tab_profile, R.string.if_profile}
        };
        
        String defaultColor = getColorString(R.color.tab_default_color);
        String selectedColor = getColorString(R.color.tab_selected_color);
        
        List<TabBottomInfo<?>> tabInfoList = new ArrayList<>();
        
        for (int[] config : tabConfigs) {
            TabBottomInfo<String> tabInfo = createTabInfo(
                    getString(config[0]),  // 标签名
                    getString(config[1]),  // 图标字符
                    defaultColor,
                    selectedColor
            );
            tabInfoList.add(tabInfo);
        }

        return tabInfoList;
    }
    
    /**
     * 创建单个标签信息
     * @param name 标签名称
     * @param iconChar 图标字符
     * @param defaultColor 默认颜色
     * @param selectedColor 选中颜色
     * @return 标签信息对象
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
     * 获取颜色字符串
     * @param colorResId 颜色资源ID
     * @return 颜色字符串
     */
    private String getColorString(int colorResId) {
        if (getContext() != null) {
            int color = getContext().getColor(colorResId);
            return String.format("#%08X", color);
        }
        return "#ff656667"; // 默认颜色
    }

    private void setupClickListeners() {

    }
}