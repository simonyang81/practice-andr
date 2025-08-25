package smn.practice.practice.andr.ui.recommend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import smn.practice.practice.andr.databinding.FragmentRecommendBinding;
import smn.practice.practice.common.ui.component.BaseFragment;

public class RecommendPageFragment extends BaseFragment {

    private FragmentRecommendBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecommendBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

}
