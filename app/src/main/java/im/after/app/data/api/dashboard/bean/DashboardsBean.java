package im.after.app.data.api.dashboard.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DashboardsBean {

    @SerializedName("data")
    @Expose
    private ArrayList<DashboardBean> dashboard;

    @SerializedName("meta")
    @Expose
    private PaginationBean meta;

}
