package im.after.neko.presenter.dashboard;

import javax.inject.Inject;

import im.after.neko.base.BasePresenter;
import im.after.neko.base.BaseView;
import im.after.neko.view.dashboard.DashboardActivity;

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
