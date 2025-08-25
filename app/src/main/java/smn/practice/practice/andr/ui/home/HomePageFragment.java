package smn.practice.practice.andr.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import smn.practice.practice.andr.databinding.FragmentHomeBinding;
import smn.practice.practice.common.ui.component.BaseFragment;
import smn.practice.practice.smn.library.log.SMNLog;

public class HomePageFragment extends BaseFragment {

    private FragmentHomeBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SMNLog.i("-->> onCreateView");

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }


}
