package im.after.app.contract.dashboard;

public interface DashboardContract {

    void loadDashboards(int page);

    void onRefresh();

    void onMore(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition);

}
