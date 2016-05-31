package im.after.app.presenter.dashboard;

import javax.inject.Inject;

import im.after.app.base.BasePresenter;
import im.after.app.base.BaseView;
import im.after.app.contract.dashboard.DashboardCreateContract;
import im.after.app.view.dashboard.DashboardCreateActivity;

public class DashboardCreatePresenter extends BasePresenter implements DashboardCreateContract {

    private DashboardCreateActivity mDashboardCreateActivity;

    @Inject
    public DashboardCreatePresenter() {
    }

    @Override
    public void attachView(BaseView view) {
        this.mDashboardCreateActivity = (DashboardCreateActivity) view;
    }

    @Override
    public void detachView() {
        this.mDashboardCreateActivity = null;
    }

}
