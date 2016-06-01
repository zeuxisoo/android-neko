package im.after.app.data.api.dashboard.bean.all;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import im.after.app.data.api.dashboard.bean.DashboardItemBean;

public class DashboardAllBean {

    @SerializedName("data")
    @Expose
    private ArrayList<DashboardItemBean> dashboardItems;

    @SerializedName("meta")
    @Expose
    private PaginationBean meta;

    public ArrayList<DashboardItemBean> getDashboardItems() {
        return dashboardItems;
    }

    public void setDashboardItems(ArrayList<DashboardItemBean> dashboardItems) {
        this.dashboardItems = dashboardItems;
    }

    public PaginationBean getMeta() {
        return meta;
    }

    public void setMeta(PaginationBean meta) {
        this.meta = meta;
    }

}
