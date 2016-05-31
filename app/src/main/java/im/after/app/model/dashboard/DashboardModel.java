package im.after.app.model.dashboard;

import javax.inject.Inject;

import im.after.app.data.api.dashboard.DashboardApi;
import im.after.app.data.api.dashboard.bean.DashboardBean;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DashboardModel {

    DashboardApi mDashboardApi;

    @Inject
    public DashboardModel(DashboardApi dashboardApi) {
        this.mDashboardApi = dashboardApi;
    }

    public Subscription loadDashboards(int page, Action1<DashboardBean> success, Action1<Throwable> error) {
        return this.mDashboardApi.all(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success, error);
    }

}
