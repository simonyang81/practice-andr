package smn.practice.practice.andr.ui.demo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import smn.practice.practice.andr.R;
import smn.practice.practice.andr.databinding.FragmentLogDemoBinding;
import smn.practice.practice.smn.library.log.SMNLog;
import smn.practice.practice.smn.library.log.SMNLogConfig;
import smn.practice.practice.smn.library.log.SMNLogManager;
import smn.practice.practice.smn.library.log.SMNLogType;
import smn.practice.practice.smn.library.log.SMNViewPrinter;
import smn.practice.practice.smn.library.log.SMNViewPrinterProvider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SMNLogDemoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SMNLogDemoFragment extends Fragment {

    private FragmentLogDemoBinding binding;

    private SMNViewPrinter viewPrinter;

    private SMNLogDemoFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SMNLogDemoFragment.
     */
    public static SMNLogDemoFragment newInstance() {
        return new SMNLogDemoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLogDemoBinding.inflate(inflater, container, false);

        viewPrinter = new SMNViewPrinter(this.getActivity());
        viewPrinter.getViewProvider().showFloatingView();

        setupClickListeners();

        return binding.getRoot();
    }


    private void setupClickListeners() {
        binding.printLogBtn.setOnClickListener(v -> {

            SMNLogManager.getInstance().addPrinter(viewPrinter);

            SMNLog.log(new SMNLogConfig() {

                @Override
                public boolean includeThread() {
                    return true;
                }

                @Override
                public int stackTraceDepth() {
                    return 100;
                }

            }, SMNLogType.E, "----", "5566");

            SMNLog.a("9900");
        });
    }

}