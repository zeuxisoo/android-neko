package im.after.app.presenter.dashboard;

import javax.inject.Inject;

import im.after.app.base.BasePresenter;
import im.after.app.base.BaseView;
import im.after.app.view.dashboard.DashboardActivity;

public class DashboardPresenter extends BasePresenter {

    DashboardActivity mDashboardActivity;

    @Inject
    public DashboardPresenter() {
    }

    // Implementation for BasePresenter
    @Override
    public void attachView(BaseView view) {
        this.mDashboardActivity = (DashboardActivity) view;
    }

    @Override
    public void detachView() {
        this.mDashboardActivity = null;
    }

}
