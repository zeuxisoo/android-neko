package im.after.app.contract.dashboard;

import im.after.app.data.api.dashboard.bean.DashboardItemBean;

public interface DashboardContract {

    void loadDashboards(int page);

    void onRefresh();

    void onMore(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition);

    void appendDashboardItemBean(DashboardItemBean dashboardItemBean);

}
