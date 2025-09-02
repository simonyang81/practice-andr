package smn.practice.practice.smnui.banner.core;

import android.content.Context;
import android.widget.Scroller;

public class SMNBannerScroller extends Scroller {

    private int mDuration = 1000;

    public SMNBannerScroller(Context context, int duration) {
        super(context);
        this.mDuration = duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
