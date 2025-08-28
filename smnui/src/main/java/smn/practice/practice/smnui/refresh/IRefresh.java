package smn.practice.practice.smnui.refresh;

public interface IRefresh {

    /**
     * 刷新时是否禁止滚动
     *
     * @param disableRefreshScroll 禁止
     */
    void setDisableRefreshScroll(boolean disableRefreshScroll);


    /**
     * 刷新完成
     */
    void refreshFinish();

    /**
     * 设置下拉刷新的监听器
     *
     * @param listener 刷新的监听器
     */
    void setRefreshListener(RefreshListener listener);

    /**
     *
     */
    void setRefreshOverView(RefreshOverView overView);

    interface RefreshListener {
        void onRefresh();
        boolean enableRefresh();
    }

}
