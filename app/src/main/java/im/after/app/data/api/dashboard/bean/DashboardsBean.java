package im.after.app.data.api.dashboard.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DashboardsBean {

    @SerializedName("data")
    @Expose
    private ArrayList<DashboardBean> dashboardList;

    @SerializedName("meta")
    @Expose
    private PaginationBean meta;


    public ArrayList<DashboardBean> getDashboardList() {
        return dashboardList;
    }

    public void setDashboardList(ArrayList<DashboardBean> dashboardList) {
        this.dashboardList = dashboardList;
    }

    public PaginationBean getMeta() {
        return meta;
    }

    public void setMeta(PaginationBean meta) {
        this.meta = meta;
    }

}
