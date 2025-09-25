package smn.practice.practice.smnui.banner.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import smn.practice.practice.smn.library.utils.DisplayUtil
import smn.practice.practice.smnui.R

class SMNNumberIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), SMNIndicator<FrameLayout?> {
    override fun get(): FrameLayout? {
        return this
    }

    override fun onInflate(counter: Int) {
        removeAllViews()

        if (counter <= 0) {
            return
        }

        // 使用WRAP_CONTENT让TextView只占用必要的空间
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.RIGHT or Gravity.BOTTOM
        // 添加一些边距，让文本不贴边显示
        params.setMargins(
            0,
            0,
            DisplayUtil.dp2px(8f, resources),
            DisplayUtil.dp2px(8f, resources)
        )

        val textView = TextView(context)
        textView.text = "1 / $counter"
        textView.setTextColor(context.getColor(android.R.color.white))
        textView.textSize = 16f

        addView(textView, params)
    }

    override fun onPointChange(current: Int, counter: Int) {
        val textView = getChildAt(0) as TextView
        textView.text = "${(current + 1)}/$counter"
    }
}
