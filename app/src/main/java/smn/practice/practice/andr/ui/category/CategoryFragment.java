package smn.practice.practice.andr.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.*;

import smn.practice.practice.andr.R;
import smn.practice.practice.andr.databinding.FragmentCategoryBinding;
import smn.practice.practice.common.ui.component.BaseFragment;
import smn.practice.practice.smnui.tab.top.TabTopInfo;
import smn.practice.practice.smnui.tab.top.TabTopLayout;

public class CategoryFragment extends BaseFragment {

    String[] tabs = new String[] {
            "热门", "服装", "数码", "鞋子", "零食", "家电", "汽车", "百货", "家居", "装修", "运动"
    };

    private FragmentCategoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);

        initTabTop();

        return binding.getRoot();
    }

    private void initTabTop() {
        TabTopLayout tabTopLayout = binding.tabTopLayout;
        List<TabTopInfo<?>> infoList = new ArrayList<>();
        int defaultColor = getResources().getColor(R.color.tab_default_color);
        int tintColor = getResources().getColor(R.color.tab_selected_color);

        for (String s: tabs) {
            TabTopInfo<Integer> info = new TabTopInfo<>(s, defaultColor, tintColor);
            infoList.add(info);
        }

        tabTopLayout.inflateInfo(infoList);
        tabTopLayout.addTabSelectedChangeListener((index, preInfo, nextInfo)
                -> Toast.makeText(CategoryFragment.this.getContext(), nextInfo.name, Toast.LENGTH_SHORT).show());

        tabTopLayout.defaultSelected(infoList.get(0));

    }

}
