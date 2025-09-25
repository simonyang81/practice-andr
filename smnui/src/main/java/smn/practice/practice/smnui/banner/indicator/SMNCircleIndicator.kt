package smn.practice.practice.smnui.banner.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import smn.practice.practice.smn.library.utils.DisplayUtil
import smn.practice.practice.smnui.R

/**
 * Banner 的圆形指示器
 */
class SMNCircleIndicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr), SMNIndicator<FrameLayout> {

    companion object {
        private const val VWC = LayoutParams.WRAP_CONTENT
    }

    @DrawableRes
    private val mPointNormal = R.drawable.shape_point_normal

    @DrawableRes
    private val mPointSelect = R.drawable.shape_point_select

    // 指示点左右间距
    private var mPointLeftRightPadding = 0

    // 指示点上下间距
    private var mPointTopBottomPadding = 0


    init {
        mPointLeftRightPadding = DisplayUtil.dp2px(5.0.toFloat(), getContext().resources)
        mPointTopBottomPadding = DisplayUtil.dp2px(15.toFloat(), getContext().resources)
    }


    override fun get(): FrameLayout? {
        return this
    }

    override fun onInflate(counter: Int) {
        super.removeAllViews()
        if (counter <= 0) {
            return
        }

        val groupView = LinearLayout(context)
        groupView.orientation = LinearLayout.HORIZONTAL

        val imageParams = LinearLayout.LayoutParams(VWC, VWC)
        imageParams.gravity = Gravity.CENTER_VERTICAL

        imageParams.setMargins(mPointLeftRightPadding, mPointTopBottomPadding, mPointLeftRightPadding, mPointTopBottomPadding)

        for (i in 0 until counter) {
            val imageView = ImageView(context)
            imageView.layoutParams = imageParams


            if (i == 0) {
                imageView.setImageResource(mPointSelect)
            } else {
                imageView.setImageResource(mPointNormal)
            }

            groupView.addView(imageView)

        }

        val groupViewParams = LayoutParams(VWC, VWC)
        groupViewParams.gravity = Gravity.CENTER or Gravity.BOTTOM

        addView(groupView, groupViewParams)

    }

    override fun onPointChange(current: Int, counter: Int) {

        val viewGroup: ViewGroup = getChildAt(0) as ViewGroup

        for (i in 0 until viewGroup.childCount) {
            val imageView: ImageView = viewGroup.getChildAt(i) as ImageView
            if (i == current) {
                imageView.setImageResource(mPointSelect)
            } else {
                imageView.setImageResource(mPointNormal)
            }

            imageView.requestLayout()

        }

    }

}