package smn.practice.practice.andr.ui.refresh;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import smn.practice.practice.andr.databinding.FragmentRefreshBinding;
import smn.practice.practice.common.ui.component.BaseFragment;
import smn.practice.practice.smnui.refresh.IRefresh;
import smn.practice.practice.smnui.refresh.RefreshLayout;
import smn.practice.practice.smnui.refresh.TextRefreshOverView;

public class RefreshFragment extends BaseFragment {

    private FragmentRefreshBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRefreshBinding.inflate(inflater, container, false);

        RefreshLayout refreshLayout = binding.refreshLayout;
        refreshLayout.setRefreshOverView(new TextRefreshOverView(getContext()));
        refreshLayout.setRefreshListener(new IRefresh.RefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(refreshLayout::refreshFinish, 1000);
            }

            @Override
            public boolean enableRefresh() {
                return true;
            }
        });


        return binding.getRoot();
    }

}
