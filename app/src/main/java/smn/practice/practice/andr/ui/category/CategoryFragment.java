package smn.practice.practice.andr.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import smn.practice.practice.andr.databinding.FragmentCategoryBinding;
import smn.practice.practice.common.ui.component.BaseFragment;

public class CategoryFragment extends BaseFragment {

    private FragmentCategoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

}
