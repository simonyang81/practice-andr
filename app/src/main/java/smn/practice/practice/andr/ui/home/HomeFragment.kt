package smn.practice.practice.andr.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import smn.practice.practice.andr.R;
import smn.practice.practice.andr.databinding.FragmentHomeBinding;
import smn.practice.practice.common.ui.component.BaseFragment;
import smn.practice.practice.smn.library.log.SMNLog;
import smn.practice.practice.smnui.banner.core.BannerModel;
import smn.practice.practice.smnui.banner.indicator.SMNCircleIndicator;
import smn.practice.practice.smnui.banner.indicator.SMNIndicator;
import smn.practice.practice.smnui.banner.indicator.SMNNumberIndicator;

public class HomeFragment extends BaseFragment {

    private final String[] urls = {
            "https://picsum.photos/900/600?random=1",
            "https://picsum.photos/900/600?random=2",
            "https://picsum.photos/900/600?random=3",
            "https://picsum.photos/900/600?random=4",
            "https://picsum.photos/900/600?random=5",
            "https://picsum.photos/900/600?random=6",
            "https://picsum.photos/900/600?random=7",
            "https://picsum.photos/900/600?random=8",
            "https://picsum.photos/900/600?random=9"
    };

    private FragmentHomeBinding binding;

    private boolean mAutoPlay = false;

    private SMNIndicator<?> indicator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SMNLog.i("-->> onCreateView");

        indicator = new SMNCircleIndicator(requireContext());

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initView(indicator, false);
        binding.autoPlay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mAutoPlay = isChecked;
            initView(indicator, mAutoPlay);
        });


        return binding.getRoot();
    }

    private void initView(SMNIndicator<?> indicator, boolean autoPlay) {

        List<BannerModel> bannerModels = new ArrayList<>();

        for (String imgUrl : urls) {
            BannerModel model = new BannerModel();
            model.url = imgUrl;

            bannerModels.add(model);
        }

        binding.banner.setIndicator(this.indicator);
        binding.banner.setAutoPlay(autoPlay);
        binding.banner.setIntercalTime(2000);

        binding.banner.setBannerData(R.layout.banner_item_layout, bannerModels);
        binding.banner.setBindAdapter((viewHolder, model, position) -> {
            ImageView imageView = viewHolder.findViewById(R.id.iv_image);
            Glide.with(HomeFragment.this).load(model.url).into(imageView);

            TextView titleView = viewHolder.findViewById(R.id.tv_title);
            titleView.setText(model.url);

        });

    }


}
