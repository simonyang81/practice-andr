package smn.practice.practice.andr.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import smn.practice.practice.andr.R;
import smn.practice.practice.andr.databinding.FragmentMainBinding;
import smn.practice.practice.andr.ui.demo.LogDemoFragment;
import smn.practice.practice.andr.ui.demo.TabBottomDemoFragment;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private FragmentMainBinding binding;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);

        setupClickListeners();

        return binding.getRoot();
    }

    private void setupClickListeners() {
        binding.message.setOnClickListener(v -> {

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.container, LogDemoFragment.newInstance())
                    .addToBackStack("SMNLogDemoFragment")
                    .commit();

        });

        binding.tabDemo.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.container, TabBottomDemoFragment.newInstance())
                    .addToBackStack("TabBottomDemoFragment")
                    .commit();
        });
    }

    private void onMessageTextClicked() {
        mViewModel.gotoNextScreen();
    }

}