package smn.practice.practice.andr.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import smn.practice.practice.andr.databinding.FragmentFavoriteBinding;
import smn.practice.practice.common.ui.component.BaseFragment;

public class FavoriteFragment extends BaseFragment {

    private FragmentFavoriteBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

}
