package smn.practice.practice.andr.ui.demo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import smn.practice.practice.andr.databinding.FragmentLogDemoBinding;
import smn.practice.practice.common.ui.component.BaseFragment;
import smn.practice.practice.smn.library.log.SMNLog;
import smn.practice.practice.smn.library.log.LogConfig;
import smn.practice.practice.smn.library.log.LogManager;
import smn.practice.practice.smn.library.log.SMNLogType;
import smn.practice.practice.smn.library.log.ViewPrinter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogDemoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogDemoFragment extends BaseFragment {

    private FragmentLogDemoBinding binding;

    private ViewPrinter viewPrinter;

    private LogDemoFragment() {
    }

    public static LogDemoFragment newInstance() {
        return new LogDemoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLogDemoBinding.inflate(inflater, container, false);
        initViews();
        setupClickListeners();

        return binding.getRoot();
    }

    private void initViews() {
        viewPrinter = new ViewPrinter(this.getActivity());
        viewPrinter.getViewProvider().showFloatingView();
        LogManager.getInstance().addPrinter(viewPrinter);
    }

    private void setupClickListeners() {
        binding.printLogBtn.setOnClickListener(v -> {

            SMNLog.log(new LogConfig() {

                @Override
                public boolean includeThread() {
                    return true;
                }

                @Override
                public int stackTraceDepth() {
                    return 100;
                }

            }, SMNLogType.E, "----", "5566");
//
            SMNLog.a("9900");
        });
    }

}