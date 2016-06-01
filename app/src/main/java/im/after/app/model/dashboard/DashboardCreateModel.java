package im.after.app.model.dashboard;

import javax.inject.Inject;

import im.after.app.data.api.dashboard.DashboardApi;
import im.after.app.data.api.dashboard.bean.create.DashboardCreateBean;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DashboardCreateModel {

    DashboardApi mDashboardApi;

    @Inject
    public DashboardCreateModel(DashboardApi dashboardApi) {
        this.mDashboardApi = dashboardApi;
    }

    public Subscription createDashboard(String kind, String subject, String content, Action1<DashboardCreateBean> success, Action1<Throwable> error) {
        return this.mDashboardApi.create(kind, subject, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success, error);
    }


}
