package smn.practice.practice.andr.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import smn.practice.practice.andr.R
import smn.practice.practice.andr.databinding.FragmentHomeBinding
import smn.practice.practice.common.ui.component.BaseFragment
import smn.practice.practice.smn.library.log.SMNLog
import smn.practice.practice.smnui.banner.core.BannerAdapter.BannerViewHolder
import smn.practice.practice.smnui.banner.core.BannerModel
import smn.practice.practice.smnui.banner.indicator.SMNCircleIndicator
import smn.practice.practice.smnui.banner.indicator.SMNIndicator
import smn.practice.practice.smnui.banner.indicator.SMNNumberIndicator

class HomeFragment : BaseFragment() {
    private val urls = arrayOf(
        "https://picsum.photos/900/600?random=1",
        "https://picsum.photos/900/600?random=2",
        "https://picsum.photos/900/600?random=3",
        "https://picsum.photos/900/600?random=4",
        "https://picsum.photos/900/600?random=5",
        "https://picsum.photos/900/600?random=6",
        "https://picsum.photos/900/600?random=7",
        "https://picsum.photos/900/600?random=8",
        "https://picsum.photos/900/600?random=9"
    )

    private var binding: FragmentHomeBinding? = null

    private var mAutoPlay = false

    private var indicator: SMNIndicator<*>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        SMNLog.i("-->> onCreateView")

//        indicator = SMNCircleIndicator(requireContext())

        indicator = SMNNumberIndicator(requireContext())

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding?.autoPlay?.let {
            Log.e("onCreateView", "${it.width} -- ${it.height}")
            it.post {
                Log.e("onCreateView:post", "${binding?.autoPlay?.width} -- ${binding?.autoPlay?.height}")
            }
            it.viewTreeObserver?.addOnGlobalLayoutListener {
                Log.e("onCreateView:viewTreeObserver", "${binding?.autoPlay?.width} -- ${binding?.autoPlay?.height}")
            }
        }

        initView(indicator, false)
        binding!!.autoPlay.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            mAutoPlay = isChecked
            initView(indicator, mAutoPlay)
        }


        return binding!!.root
    }

    private fun initView(indicator: SMNIndicator<*>?, autoPlay: Boolean) {
        val bannerModels: MutableList<BannerModel> = ArrayList()

        for (imgUrl in urls) {
            val model = BannerModel()
            model.url = imgUrl
            bannerModels.add(model)
        }

        binding?.banner?.apply {

            setIndicator(indicator)
            setAutoPlay(autoPlay)
            setIntercalTime(2000)
            setBannerData(R.layout.banner_item_layout, bannerModels)
            setBindAdapter { viewHolder: BannerViewHolder, model: BannerModel, position: Int ->
                val imageView =
                    viewHolder.findViewById<ImageView>(R.id.iv_image)
                Glide.with(this@HomeFragment).load(model.url).into(imageView)

                val titleView = viewHolder.findViewById<TextView>(R.id.tv_title)
                titleView.text = model.url
            }

        }


    }
}
