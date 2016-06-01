package im.after.app.data.api.dashboard.bean.create;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import im.after.app.base.BaseBean;
import im.after.app.data.api.dashboard.bean.DashboardItemBean;

public class DashboardCreateBean extends BaseBean {

    @SerializedName("data")
    @Expose
    private DashboardItemBean dashboardItem;

    public DashboardItemBean getDashboardItem() {
        return dashboardItem;
    }

    public void setDashboardItemItem(DashboardItemBean dashboardItem) {
        this.dashboardItem = dashboardItem;
    }

}

